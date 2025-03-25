package technologium.world.blocks.power;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Eachable;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.world.blocks.power.PowerNode;
import mindustry.core.*;
import mindustry.entities.units.BuildPlan;
import mindustry.world.draw.*;
import mindustry.world.meta.BlockStatus;

import static mindustry.Vars.*;

public class DrawerPowerNode extends PowerNode {
    public DrawBlock drawer = new DrawDefault();
    
    public DrawerPowerNode(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    @Override
    public void init() {
        //before the init, make sure so consumePowerBuffered will work normally
        if(consPower != null && consPower.buffered) 
            consumesPower = outputsPower = true;
        
        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out){
        drawer.getRegionsToOutline(this, out);
    }

    public class TPowerNodeBuild extends PowerNodeBuild {
        @Override
        public void created() {
            super.created();
            //when placing a node with consumePowerBuffered, the graph doesn't see that until the block is updated, so update right when placed
            power.graph.update();
        }
        
        @Override
        public void draw() {
            drawer.draw(this);    

            if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            Draw.z(Layer.power);
            setupColor(power.graph.getSatisfaction());

            for(int i = 0; i < power.links.size; i++){
                Building link = world.build(power.links.get(i));
                if(linkValid(this, link) && !(link.block instanceof PowerNode && link.id >= id)) drawLaser(x, y, link.x, link.y, size, link.block.size);
            }

            Draw.reset();
        }

        @Override
        public float warmup() {
            return power.status;
        }
            
        @Override
        public void overwrote(Seq<Building> previous){
            for(Building other : previous){
                if(other.power != null && other.block.consPower != null && other.block.consPower.buffered){
                    float amount = other.block.consPower.capacity * other.power.status;
                    power.status = Mathf.clamp(power.status + amount / consPower.capacity);
                }
            }
        }

        @Override
        public BlockStatus status(){
            if(Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if(Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }
    }
}
