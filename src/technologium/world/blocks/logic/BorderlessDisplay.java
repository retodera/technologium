package technologium.world.blocks.logic;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.FrameBuffer;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.DisplayCmd;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import mindustry.world.blocks.logic.LogicDisplay;

public class BorderlessDisplay extends LogicDisplay{

    public BorderlessDisplay(String name){
        super(name);
    }

    public class BorderlessDisplayBuild extends LogicDisplayBuild {

        @Override
        public void draw() {
            if (Core.settings.getBool("drawdisplayborder")) super.draw();

            if (Vars.renderer.drawDisplays) {
                Draw.draw(Draw.z(), () -> {
                    if (buffer == null) {
                        buffer = new FrameBuffer(displaySize, displaySize);
                        buffer.begin(Pal.darkerMetal);
                        buffer.end();
                    }
                });
                if (!commands.isEmpty()) {
                Draw.draw(Draw.z(), () -> {
                    Tmp.m1.set(Draw.proj());
                    Draw.proj(0, 0, displaySize, displaySize);
                    buffer.begin();
                    Draw.color(color);
                    Lines.stroke(stroke);

                    while(!commands.isEmpty()) {
                        long c = commands.removeFirst();
                        byte type = DisplayCmd.type(c);
                        int x = unpackSign(DisplayCmd.x(c));
                        int y = unpackSign(DisplayCmd.y(c));
                        int p1 = unpackSign(DisplayCmd.p1(c));
                        int p2 = unpackSign(DisplayCmd.p2(c));
                        int p3 = unpackSign(DisplayCmd.p3(c));
                        int p4 = unpackSign(DisplayCmd.p4(c));
                        switch (type) {
                            case 0:
                                Core.graphics.clear(x / 255.0F, y / 255.0F, p1 / 255.0F, 1.0F);
                                break;
                            case 1:
                                Draw.color(color = Color.toFloatBits(x, y, p1, p2));
                            case 2:
                            case 11:
                            default:
                                break;
                            case 3:
                                Lines.stroke(stroke = x);
                                break;
                            case 4:
                                Lines.line(x, y, p1, p2);
                                break;
                            case 5:
                                Fill.crect(x, y, p1, p2);
                                break;
                            case 6:
                                Lines.rect(x, y, p1, p2);
                                break;
                            case 7:
                                Fill.poly(x, y, Math.min(p1, maxSides), p2, p3);
                                break;
                            case 8:
                                Lines.poly(x, y, Math.min(p1, maxSides), p2, p3);
                                break;
                            case 9:
                                Fill.tri(x, y, p1, p2, p3, p4);
                                break;
                            case 10:
                                TextureRegion icon = Fonts.logicIcon(p1);
                                Draw.rect(Fonts.logicIcon(p1), x, y, p2, p2 / icon.ratio(), p3);
                            }
                        }
                        buffer.end();
                        Draw.proj(Tmp.m1);
                        Draw.reset();
                    });
                }
                Draw.blend(Blending.disabled);
                Draw.draw(Draw.z(), () -> {
                    if (buffer != null) {
                        Draw.rect(Draw.wrap(buffer.getTexture()), x, y, 32 * (float)size * Draw.scl, -32 * (float)size * Draw.scl);
                    }
                });
                Draw.blend();
            }
        }
    }
    static int unpackSign(int value){
        return (value & 0b0111111111) * ((value & (0b1000000000)) != 0 ? -1 : 1);
    }
}
