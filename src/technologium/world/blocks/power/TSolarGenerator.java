package technologium.world.blocks.power;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.world.blocks.power.SolarGenerator;
import arc.Core;
import arc.math.Mathf;

import static technologium.graphics.TPal.*;

public class TSolarGenerator extends SolarGenerator {
    public TextureRegion top;

    /** productionEfficiency = 0f */
    public Color dark = dark5;
    /** productionEfficiency = 1f */
    public Color light = Color.white;
    /** productionEfficiency >= 2f */
    public Color overpower = Color.valueOf("7fbfbf");

    public TSolarGenerator(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        top = Core.atlas.find(name + "-top");
    }

    public class TSolarGeneratorBuild extends SolarGeneratorBuild {
        public float warmup;

        @Override
        public void updateTile() {
            super.updateTile();
            warmup = Mathf.approach(warmup, productionEfficiency, 0.005f);
        }

        @Override
        public void draw() {
            super.draw();
            if(productionEfficiency <= 1f) Draw.color(dark.cpy().lerp(light, Mathf.clamp(warmup)));
            else Draw.color(light.cpy().lerp(overpower, Mathf.clamp(warmup - 1f)));
            Draw.rect(top, x, y);
            Draw.color();
        }
    }
}
