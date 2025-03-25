package technologium.world.blocks.distribution;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import arc.util.Time;
import mindustry.entities.units.BuildPlan;
import mindustry.world.blocks.distribution.Conveyor;

public class TConveyor extends Conveyor {
    public TextureRegion[][] topRegions;
    public boolean drawTop = false, animatedTop = false, rotateTop = true;

    public TConveyor(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        if(!drawTop) return;
        topRegions = new TextureRegion[5][4];
        for (int i = 0; i < 5; i++) {
            if(animatedTop) for (int j = 0; j < 4; j++) {
                topRegions[i][j] = Core.atlas.find(name + "-top-" + i + "-" + j);
            }
            else topRegions[i][0] = Core.atlas.find(name + "-top-" + i);
        }
    }

    @Override
    public TextureRegion[] icons() {
        return drawTop && topExists(topRegions[0][0]) ? new TextureRegion[]{regions[0][0], topRegions[0][0]} : new TextureRegion[]{regions[0][0]};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        super.drawPlanRegion(plan, list);
        if(!drawTop) return;
        int[] bits = getTiling(plan, list);
        if(bits != null) {
            TextureRegion region = topRegions[bits[0]][0];
            Draw.z(29.9f);
            if(topExists(region)) Draw.rect(region, plan.drawx(), plan.drawy(), region.width * bits[1] * region.scl(), region.height * bits[2] * region.scl(), (rotateTop ? plan.rotation * 90f : 0));
            Draw.z();
        }
    }

    boolean topExists(TextureRegion region) {
        return region != null && region != Core.atlas.find("error");
    }

    public class TConveyorBuild extends ConveyorBuild {
        @Override
        public void draw() {
            super.draw();
            if(!drawTop) return;
            int frame = enabled && clogHeat <= 0.5 ? (int)(Time.time * speed * 8 * timeScale * efficiency % 4) : 0;
            TextureRegion region = topRegions[blendbits][animatedTop ? frame : 0];
            Draw.z(30f);
            if(topExists(region)) Draw.rect(region, x, y, blendsclx * 8, blendscly * 8, (rotateTop ? rotation * 90f : 0));
            Draw.z();
        }
    }
}
