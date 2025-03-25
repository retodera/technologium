package technologium.world.blocks.logic;

import static mindustry.Vars.state;

import arc.util.*;
import arc.Core;
import arc.Graphics.Cursor;
import arc.Graphics.Cursor.SystemCursor;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.Mathf;
import arc.scene.ui.TextField.TextFieldFilter;
import arc.scene.ui.layout.*;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.*;
import mindustry.world.Tile;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.meta.Stat;

public class Projector extends LogicDisplay {

    public Projector(String name) {
        super(name);
        configurable = true;

        config(Float[].class, (ProjectorBuild build, Float[] values) -> {
            build.projSize = Mathf.clamp(values[0], 1, 16);
            build.displaySize =  Mathf.clamp(values[1].intValue(), 1, 256);
            build.targetX =  Mathf.clamp(values[2], build.projSize / 2, Vars.world.width() - build.projSize / 2);
            build.targetY =  Mathf.clamp(values[3], build.projSize / 2, Vars.world.height() - build.projSize / 2);
        });
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.displaySize);
    }

        public boolean accessible(){
        return !privileged || state.rules.editor;
    }

        @Override
    public boolean canBreak(Tile tile){
        return accessible();
    }

    public class ProjectorBuild extends LogicDisplayBuild {
        public int displaySize = 64;
        public float projSize = 1, targetX, targetY, eff;
        public Table table = new Table();
        public float color;

        @Override
        public void created() {
            targetX = x / Vars.tilesize;
            targetY = y / Vars.tilesize;
            // "Should be as large as the block will draw." well, it wont now.
            // just to fix the problem that projection unloads with the block texture (anuke is the !best coder)
            // because of this it might not be the best idea to place lots of projectors
            clipSize = Math.max(
                Vars.world.width() + Math.abs(x - Vars.world.width() / 2f),
                Vars.world.height() + Math.abs(y - Vars.world.height() / 2f)
            ) * Vars.tilesize;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y, drawrot());

            eff = Mathf.approachDelta(eff, efficiency, 0.019f);
            if(!Vars.renderer.drawDisplays || !accessible() || eff == 0) return;

            color = Color.valueOf("ffffff").a(eff).toFloatBits();

            Draw.draw(Draw.z(), () -> {
                if(buffer == null){
                    buffer = new FrameBuffer(displaySize, displaySize);
                    // the alpha value doesn't actually change anything because anuke is the !best coder, or maybe someone else
                    // TODO make it transparent too, somehow
                    buffer.begin(Pal.darkerMetal);
                    buffer.end();
                }
            });

            if(!commands.isEmpty()){
                Draw.draw(Draw.z(), () -> {
                    Tmp.m1.set(Draw.proj());
                    Draw.proj(0, 0, displaySize, displaySize);
                    buffer.begin();
                    Draw.color(color);
                    Lines.stroke(stroke);

                    while(!commands.isEmpty()){
                        long c = commands.removeFirst();
                        byte type = DisplayCmd.type(c);
                        int x = unpackSign(DisplayCmd.x(c)), y = unpackSign(DisplayCmd.y(c)),
                        p1 = unpackSign(DisplayCmd.p1(c)), p2 = unpackSign(DisplayCmd.p2(c)), p3 = unpackSign(DisplayCmd.p3(c)), p4 = unpackSign(DisplayCmd.p4(c));

                        switch(type){
                            // the alpha value doesn't actually change anything because anuke is the !best coder, or maybe someone else
                            // TODO make it transparent too, somehow
                            case commandClear -> Core.graphics.clear(x / 255f, y / 255f, p1 / 255f, 1f);
                            case commandLine -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Lines.line(x, y, p1, p2);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandRect -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Fill.crect(x, y, p1, p2);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandLineRect -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Lines.rect(x, y, p1, p2);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandPoly -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Fill.poly(x, y, Math.min(p1, maxSides), p2, p3);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandLinePoly -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Lines.poly(x, y, Math.min(p1, maxSides), p2, p3);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandTriangle -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Fill.tri(x, y, p1, p2, p3, p4);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandColor -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Draw.color(this.color = Color.toFloatBits(x, y, p1, p2));
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandStroke -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                Lines.stroke(this.stroke = x);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandImage -> {
                                Draw.alpha(Draw.getColor().a * eff);
                                var icon = Fonts.logicIcon(p1);
                                Draw.rect(Fonts.logicIcon(p1), x, y, p2, p2 / icon.ratio(), p3);
                                Draw.alpha(Draw.getColor().a / eff);
                            }
                            case commandCharacter -> {
                            }
                        }
                    }
                    buffer.end();
                    Draw.proj(Tmp.m1);
                    Draw.reset();
                });
                
            }

            Draw.blend(Blending.disabled);
            Draw.draw(Draw.z(), () -> {
                if(buffer != null){
                    Draw.rect(Draw.wrap(buffer.getTexture()), targetX * Vars.tilesize, targetY * Vars.tilesize, (float)(32 * projSize) * Draw.scl, (float)(-32 * projSize) * Draw.scl);
                }
            });
            Draw.blend();
        }

        @Override
        public void buildConfiguration (Table table) {
            table.clearChildren();
            rebuild();
            table.add(this.table);
        }

        void rebuild() {
            table.clear();
            table.background(Styles.black6);
            table.add("@proj-projsize");
            table.field(""+projSize, TextFieldFilter.floatsOnly, v -> {
                if(v != "") configure(new Float[]{
                        Float.parseFloat(v),
                        Float.parseFloat(""+displaySize),
                        targetX, targetY
                    });
                else rebuild();
            });
            table.row().add("@proj-dispsize");
            table.field(""+displaySize, TextFieldFilter.digitsOnly, v -> {
                if(v != "") configure(new Float[]{
                        projSize,
                        Float.parseFloat(v),
                        targetX, targetY
                    });
                else rebuild();
            });
            table.row().add("@proj-x");
            table.field(""+targetX, TextFieldFilter.floatsOnly, v -> {
                if(v != "") configure(new Float[]{
                        projSize,
                        Float.parseFloat(""+displaySize),
                        Float.parseFloat(v), targetY
                    });
                else rebuild();
            });
            table.row().add("@proj-y");
            table.field(""+targetY, TextFieldFilter.floatsOnly, v -> {
                if(v != "") configure(new Float[]{
                        projSize,
                        Float.parseFloat(""+displaySize),
                        targetX,  Float.parseFloat(v)
                    });
                else rebuild();
            });
        }

        @Override
        public boolean shouldShowConfigure(Player player){
            return accessible();
        }

        @Override
        public Cursor getCursor(){
            return !accessible() ? SystemCursor.arrow : super.getCursor();
        }

        @Override
        public Object config() {
            return new Float[]{Float.parseFloat(""+projSize), Float.parseFloat(""+displaySize), targetX, targetY};
        }

        // i cant even imagine what would happen if you pick it up
        @Override
        public boolean canPickup(){
            return false;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other || !accessible()){
                deselect();
                return false;
            }

            return true;
        }
    }

    static int unpackSign(int value){
        return (value & 0b0111111111) * ((value & (0b1000000000)) != 0 ? -1 : 1);
    }
}
