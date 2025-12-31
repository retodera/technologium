package technologium.world.blocks.defense;

import mindustry.world.draw.*;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.ForceProjector;

public class DrawerForceProjector extends ForceProjector {
    public DrawBlock drawer = new DrawDefault();

    public DrawerForceProjector(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    public class DrawerForceBuild extends ForceBuild {
        @Override
        public void draw(){
            drawer.draw(this);

            if(buildup > 0f){
                Draw.alpha(buildup / shieldHealth * 0.75f);
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.rect(topRegion, x, y);
                Draw.blend();
                Draw.z(Layer.block);
                Draw.reset();
            }

            drawShield();
        }
    }
}
