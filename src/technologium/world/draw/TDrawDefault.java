package technologium.world.draw;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawDefault;

import static mindustry.Vars.*;

public class TDrawDefault extends DrawDefault {
    @Override
    public void draw(Building build) {
        Draw.rect(build.block.region, build.x, build.y);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        //why does it even uses fullIcon.
        Draw.rect(block.region, plan.drawx(), plan.drawy(), !block.rotate || !block.rotateDraw ? 0 : plan.rotation * 90);

        if(plan.worldContext && player != null && block.teamRegion != null && block.teamRegion.found()){
            if(block.teamRegions[player.team().id] == block.teamRegion) Draw.color(player.team().color);
            Draw.rect(block.teamRegions[player.team().id], plan.drawx(), plan.drawy());
            Draw.color();
        }

        block.drawPlanConfig(plan, list);
    }
}
