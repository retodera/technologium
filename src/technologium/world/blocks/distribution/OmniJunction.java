package technologium.world.blocks.distribution;

import arc.util.Time;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.DirectionalItemBuffer;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class OmniJunction extends LiquidJunction {
    public float speed = 26;
    public int capacity = 6;

    public OmniJunction(String name) {
        super(name);
        hasLiquids = true;
        update = true;
        solid = false;
        underBullets = true;
        group = BlockGroup.transportation;
        unloadable = false;
        floating = true;
        noUpdateDisabled = true;
    }
    
    @Override
    public boolean outputsItems(){
        return true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.liquidCapacity);
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("liquid");
    }

    public class OmniJunctionBuild extends LiquidJunctionBuild {
        
        @Override
        public Building getLiquidDestination(Building source, Liquid liquid){
            if(!enabled) return this;

            int dir = (source.relativeTo(tile.x, tile.y) + 4) % 4;
            Building next = nearby(dir);
            if(next == null || (!next.acceptLiquid(this, liquid) && !(next.block instanceof LiquidJunction))){
                return this;
            }
            return next.getLiquidDestination(this, liquid);
        }
        public DirectionalItemBuffer buffer = new DirectionalItemBuffer(capacity);

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return 0;
        }

        @Override
        public void updateTile(){
            for(int i = 0; i < 4; i++){
                if(buffer.indexes[i] > 0){
                    if(buffer.indexes[i] > capacity) buffer.indexes[i] = capacity;
                    long l = buffer.buffers[i][0];
                    float time = BufferItem.time(l);

                    if(Time.time >= time + speed / timeScale || Time.time < time){

                        Item item = content.item(BufferItem.item(l));
                        Building dest = nearby(i);

                        if(item == null || dest == null || !dest.acceptItem(this, item) || dest.team != team)
                            continue;

                        dest.handleItem(this, item);
                        System.arraycopy(buffer.buffers[i], 1, buffer.buffers[i], 0, buffer.indexes[i] - 1);
                        buffer.indexes[i] --;
                    }
                }
            }
        }

        @Override
        public void handleItem(Building source, Item item){
            int relative = source.relativeTo(tile);
            buffer.accept(relative, item);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            int relative = source.relativeTo(tile);

            if(relative == -1 || !buffer.accepts(relative)) return false;
            Building to = nearby(relative);
            return to != null && to.team == team;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            buffer.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            buffer.read(read);
        }
    }
}
