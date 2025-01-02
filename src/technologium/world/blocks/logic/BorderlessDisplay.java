package technologium.world.blocks.logic;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
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

    public class TDisplayBuild extends LogicDisplayBuild {

        @Override

        public void draw() {
            if (Core.settings.getBool("drawdisplayborder")) super.draw();

            if (Vars.renderer.drawDisplays) {
                Draw.draw(Draw.z(), () -> {
                    if (this.buffer == null) {
                        this.buffer = new FrameBuffer(displaySize, displaySize);
                        this.buffer.begin(Pal.darkerMetal);
                        this.buffer.end();
                    }
                });
                if (!this.commands.isEmpty()) {
                Draw.draw(Draw.z(), () -> {
                    Tmp.m1.set(Draw.proj());
                    Draw.proj(0.0F, 0.0F, (float)displaySize, (float)displaySize);
                    this.buffer.begin();
                    Draw.color(this.color);
                    Lines.stroke(this.stroke);

                    while(!this.commands.isEmpty()) {
                        long c = this.commands.removeFirst();
                        byte type = DisplayCmd.type(c);
                        int x = unpackSign(DisplayCmd.x(c));
                        int y = unpackSign(DisplayCmd.y(c));
                        int p1 = unpackSign(DisplayCmd.p1(c));
                        int p2 = unpackSign(DisplayCmd.p2(c));
                        int p3 = unpackSign(DisplayCmd.p3(c));
                        int p4 = unpackSign(DisplayCmd.p4(c));
                        switch (type) {
                            case 0:
                                Core.graphics.clear((float)x / 255.0F, (float)y / 255.0F, (float)p1 / 255.0F, 1.0F);
                                break;
                            case 1:
                                Draw.color(this.color = Color.toFloatBits(x, y, p1, p2));
                            case 2:
                            case 11:
                            default:
                                break;
                            case 3:
                                Lines.stroke(this.stroke = (float)x);
                                break;
                            case 4:
                                Lines.line((float)x, (float)y, (float)p1, (float)p2);
                                break;
                            case 5:
                                Fill.crect((float)x, (float)y, (float)p1, (float)p2);
                                break;
                            case 6:
                                Lines.rect((float)x, (float)y, (float)p1, (float)p2);
                                break;
                            case 7:
                                Fill.poly((float)x, (float)y, Math.min(p1, maxSides), (float)p2, (float)p3);
                                break;
                            case 8:
                                Lines.poly((float)x, (float)y, Math.min(p1, maxSides), (float)p2, (float)p3);
                                break;
                            case 9:
                                Fill.tri((float)x, (float)y, (float)p1, (float)p2, (float)p3, (float)p4);
                                break;
                            case 10:
                                TextureRegion icon = Fonts.logicIcon(p1);
                                Draw.rect(Fonts.logicIcon(p1), (float)x, (float)y, (float)p2, (float)p2 / icon.ratio(), (float)p3);
                            }
                        }
                        this.buffer.end();
                        Draw.proj(Tmp.m1);
                        Draw.reset();
                    });
                }
                Draw.blend(Blending.disabled);
                Draw.draw(Draw.z(), () -> {
                    if (this.buffer != null) {
                        Draw.rect(Draw.wrap((Texture)this.buffer.getTexture()), this.x, this.y, (float)(32 * size) * Draw.scl, (float)(-(32 * size)) * Draw.scl);
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
