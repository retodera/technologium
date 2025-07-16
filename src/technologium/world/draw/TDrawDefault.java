package technologium.world.draw;

import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.world.draw.DrawDefault;

public class TDrawDefault extends DrawDefault {
    @Override
    public void draw(Building build) {
        Draw.rect(build.block.region, build.x, build.y);
    }
}
