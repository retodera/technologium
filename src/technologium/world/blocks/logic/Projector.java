package technologium.world.blocks.logic;

import arc.Graphics.Cursor;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.scene.ui.TextField.TextFieldFilter;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.*;
import mindustry.world.Tile;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

public class Projector extends LogicDisplay {

    public Projector(String name) {
        super(name);
        configurable = true;

        config(Float[].class, (ProjectorBuild build, Float[] values) -> {
            build.projSize = Mathf.clamp(values[0], 1, 16);
            build.displaySize =  Mathf.clamp(values[1].intValue(), 1, 256);
            build.targetX =  Mathf.clamp(values[2], build.projSize / 2, world.width() - build.projSize / 2);
            build.targetY =  Mathf.clamp(values[3], build.projSize / 2, world.height() - build.projSize / 2);
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
        public float projSize = 1, targetX, targetY;
        public Table table = new Table();

        @Override
        public void created() {
            targetX = x / tilesize;
            targetY = y / tilesize;
            clipSize = Float.MAX_VALUE; // "Should be as large as the block will draw."
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            drawTeamTop();

            if(!renderer.drawDisplays) return;

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
                    Draw.rect(Draw.wrap(buffer.getTexture()), targetX * tilesize, targetY * tilesize, size*tilesize * projSize * scaleFactor * Draw.scl*4, size*tilesize * projSize * scaleFactor * Draw.scl*4);
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
            return !accessible() ? Cursor.SystemCursor.arrow : super.getCursor();
        }

        @Override
        public Object config() {
            return new Float[]{Float.parseFloat(""+projSize), Float.parseFloat(""+displaySize), targetX, targetY};
        }

        // i cant even START TO imagine what would happen if you pick it up
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
