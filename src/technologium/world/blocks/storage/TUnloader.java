package technologium.world.blocks.storage;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.*;
import mindustry.world.blocks.storage.Unloader;

public class TUnloader extends Unloader {
    public TUnloader(String name) {
        super(name);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list) {
        Color color = plan.config instanceof Item i ? i.color : plan.config instanceof Liquid l ? l.color : null;
        if(color == null) return;
        Draw.color(color);
        Draw.rect(centerRegion, plan.drawx(), plan.drawy(), plan.rotation);
        Draw.color();
    }
}
