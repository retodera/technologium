package technologium.world.blocks.logic;

import arc.util.io.*;
import mindustry.world.meta.*;
import mindustry.world.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class StringMemoryBlock extends Block{
    public int memoryCapacity = 32;

    public StringMemoryBlock(String name) {
        super(name);
        destructible = true;
        solid = true;
        group = BlockGroup.logic;
        drawDisabled = false;
        envEnabled = Env.any;
        canOverdrive = false;

        config(String[].class, (StringMemoryBuild build, String[] values) -> {
           build.memory = values;
        });
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.memoryCapacity, memoryCapacity, StatUnit.none);
    }

    public boolean accessible(){
        return !privileged || state.rules.editor;
    }

    @Override
    public boolean canBreak(Tile tile){
        return accessible();
    }

    public class StringMemoryBuild extends Building{
        public String[] memory = new String[memoryCapacity];

        @Override
        public void created() {
            super.created();
            for(int i = 0; i < memoryCapacity; i++) memory[i] = "";
        }

        @Override
        public boolean canPickup(){
            return false;
        }

        @Override
        public boolean collide(Bullet other){
            return !privileged;
        }

        @Override
        public boolean displayable(){
            return accessible();
        }

        @Override
        public void damage(float damage){
            if(privileged) return;
            super.damage(damage);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.i(memory.length);
            for(String v : memory){
                write.str(v == null ? "" : v);
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            int amount = read.i();
            for(int i = 0; i < amount; i++){
                String val = read.str();
                if(i < memory.length) memory[i] = val;
            }
        }
    }
}