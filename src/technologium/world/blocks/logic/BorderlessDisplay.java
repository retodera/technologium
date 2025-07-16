package technologium.world.blocks.logic;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.FrameBuffer;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.world.blocks.logic.LogicDisplay;

public class BorderlessDisplay extends LogicDisplay{

    public BorderlessDisplay(String name){
        super(name);
    }

    public class BorderlessDisplayBuild extends LogicDisplayBuild {
        @Override
        public void draw(){
            Draw.rect(region, x, y);
            drawTeamTop();

            if(!Vars.renderer.drawDisplays) return;

            Draw.draw(Draw.z(), () -> {
                if(buffer == null){
                    buffer = new FrameBuffer(displaySize, displaySize);
                    buffer.begin(Pal.darkerMetal);
                    buffer.end();
                }
            });

            processCommands();

            Draw.blend(Blending.disabled);
            Draw.draw(Draw.z(), () -> {
                if(buffer != null){
                    Draw.rect(Draw.wrap(buffer.getTexture()), x, y, size*Vars.tilesize * scaleFactor * Draw.scl*4, size*Vars.tilesize * scaleFactor * Draw.scl*4);
                }
            });
            Draw.blend();
        }
    }
}
