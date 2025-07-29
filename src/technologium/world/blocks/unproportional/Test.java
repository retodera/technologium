package technologium.world.blocks.unproportional;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.util.Eachable;
import arc.util.Log;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.BlockRenderer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BuildVisibility;
import technologium.util.Math;

import java.io.File;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import static mindustry.Vars.player;
import static mindustry.Vars.tilesize;
import static mindustry.content.Blocks.air;
import static technologium.LoggingUtils.um_custom_log_why;

//TODO no rotation sadly

/**
* {@link technologium.util.VerySafe} is safer to use than this garbage
*/
public class Test extends Block {
    //region trash
    public final int w;
    final public int h;

    public Test(String name, int w, int h) {
        super(name);
        this.w = w;
        this.h = h;
        buildVisibility = BuildVisibility.shown;
        size = 1;
        region = Core.atlas.find("test" + w + "x" + h);
        update = true;
        buildType = idk_how_this_works::new;
        solid = true;
        destructible = true;
        int gcd = Math.gcd(w, h);
        description = "test block, proportions: " + (w / gcd) + ":" + (h / gcd) + "\nyou surely shouldn't place this garbage"+(new File("/").exists()?"\n[#ff007f]hi! only visible to users with '/' dir":"");
    }

    /**
     * @return vertical (y) offset in world units
     */
    public float verticalOffset() {
        return (h - 1) * tilesize / 2f;
    }

    /**
     * @return horizontal (x) offset in world units
     */
    public float horizontalOffset() {
        return (w - 1) * tilesize / 2f;
    }

    /**@return world width*/
    public float w() {
        return w * tilesize;
    }

    /**@return world height*/
    public float h() {
        return h * tilesize;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        //no potential links
        drawOverlay(x * tilesize + horizontalOffset(), y * tilesize + verticalOffset(), rotation);
    }

    public void drawDefaultPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        TextureRegion reg = getPlanRegion(plan, list);
        Draw.rect(reg, plan.x * tilesize + horizontalOffset(), plan.y * tilesize + verticalOffset(), 0);

        if(plan.worldContext && player != null && teamRegion != null && teamRegion.found()){
            if(teamRegions[player.team().id] == teamRegion) Draw.color(player.team().color);
            Draw.rect(teamRegions[player.team().id], plan.x*tilesize+horizontalOffset(), plan.y*tilesize+verticalOffset());
            Draw.color();
        }

        drawPlanConfig(plan, list);
    }

    // on next day idk why i made this
    public int prefSize() {
        return min(w, h);
    }

    //maybe i'll implement this
    @Override
    public void drawShadow(Tile tile) {
        if (customShadowRegion == null) return;
        Draw.color(BlockRenderer.shadowColor);
        Draw.rect(
                customShadowRegion,
                tile.worldx(), tile.worldy() - h / 2f, w, h);
        Draw.color();
    }

    // check todo todo todo todo todo todo todo todo todo todo todo
    @Override
    public boolean canPlaceOn(Tile t, Team team, int rotation) {
        boolean b = true;
        if (t == null) {
            return true;
        } else {
            if (t.block() != air && t.block() != null) b = false;
            else for (int dx = 0; dx < w; dx++) {
                for (int dy = 0; dy < h; dy++) {
                    Tile tmp = Vars.world.tiles.get(t.x + dx, t.y + dy);
                    if (tmp.block() != air && t.block() != null /*|| floor check*/) {
                        return false;
                    }
                }
            }
        }
        return b;
    }

    @Override
    public boolean canReplace(Block other) {
        return other == air || other == null || other instanceof Test t && t.w == w && t.h == h;
    }
    //endregion

    // i am bad dev, i should not write anything
    // nebula arcanum
    public class idk_how_this_works extends Building {
        public Tile[][] spreadTo = new Tile[w][h];

        /**
         * follow given advice
         */
        public idk_how_this_works() {
            super();
            um_custom_log_why(0xff007fff,"?", "listen to me");
            um_custom_log_why(0xff007f7f,"?", "please");
            um_custom_log_why(0xff007f3f,"?", "this block is poison to your game");
            um_custom_log_why(0xff007f1f,"?", "please write: 'java.lang.System.exit(0x69)'");
            um_custom_log_why(0xff007f0f,"?", "bye:(");
        }

        //region kill me plz
        /**
         * Sets build of all tiles in region {@link Test#w}x{@link Test#h} to this, and set this's coordinates
         */
        public void spread() {
            if (isValid()||!isRoot()) return;
            Tile t = tileOn();
            Log.debug("TestBuild.spread >");
            if (t == null) return;
            Log.debug("TestBuild.spread =");
            StringBuilder sb = new StringBuilder(w * h * 5);
            float px = x, py = y;
            for (int dx = 0; dx < w; dx++) {
                for (int dy = 0; dy < h; dy++) {
                    Tile tmp = Vars.world.tiles.get(t.x + dx, t.y + dy);
                    if(tmp==t){
                        sb.append("(---)");
                        continue;
                    }
                    sb.append("(").append(dx).append("-").append(dy).append(")");
                    tmp.setBlock(Test.this, this.team, rotation, () -> this);
                    spreadTo[dx][dy] = (tmp);
                }
            }
            Log.debug("TestBuild.spread < " + sb);
            x = px;
            y = py;
        }

        public boolean isRoot() {
            return tileOn() !=null && tileOn().build == this;
        }

        /**undo*/
        public void unspread() {
            if (!isRoot()) return;
            Log.debug("TestBuild.unspread >");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < spreadTo.length; j++) {
                Tile[] column = spreadTo[j];
                for (int i = 0; i < column.length; i++) {
                    Tile tmp = column[i];
                    if (tmp != null) {
                        if (tmp.build == null || (tmp.build.x() == this.x() && tmp.build.y() == this.y())) {
                            sb.append("(///)");
                            continue;
                        }
                        tmp.setAir();
                        sb.append('(').append(j).append('|').append(i).append(')');
                    } else sb.append("(---)");
                }
            }
            Log.debug("TestBuild.unspread < " + sb);
        }
        //endregion

        @Override
        public boolean isValid() {
            for (Tile[] tiles : spreadTo) {
                for (Tile t : tiles) {
                    if(t==null||t.build!=this)return false;
                }
            }
            return super.isValid();
        }

        //region
        @SuppressWarnings("UnnecessarySemicolon")
        @Override
        public void afterDestroyed() {
            super.afterDestroyed();
            unspread();
            ;;;;   ;;;;   ;;;;;;;;;;;   ;;;;          ;;;;          ;;;;;;;;;;;
            ;;;;   ;;;;   ;;;;          ;;;;          ;;;;          ;;;;   ;;;;
            ;;;;;;;;;;;   ;;;;;;;;;;;   ;;;;          ;;;;          ;;;;   ;;;;
            ;;;;   ;;;;   ;;;;          ;;;;          ;;;;          ;;;;   ;;;;
            ;;;;   ;;;;   ;;;;;;;;;;;   ;;;;;;;;;;;   ;;;;;;;;;;;   ;;;;;;;;;;;
        }

        public boolean removed=false;
        @Override
        public void removeFromProximity() {
            if(removed)return;
            removed=true;
            super.removeFromProximity();
            Log.debug("TestBuild.removeFromProximity");
            unspread();
        }

        @Override
        public void onDestroyed() {
            super.onDestroyed();
            Log.debug("TestBuild.onDestroyed");
            unspread();
        }

        @Override
        public void afterReadAll() {
            super.afterReadAll();
            Log.debug("TestBuild.afterReadAll");
            spread();
        }

        @Override
        public void placed() {
            super.placed();
            Log.debug("TestBuild.placed");
            spread();
        }
        //endregion

        @Override
        public void draw() {
            if(isRoot()) {
                Draw.rect(this.block.region, this.x + horizontalOffset(), this.y + verticalOffset(), 0);

                drawTeamTop();
            }
        }

        @Override
        public float hitSize() {
            return max(w(), h());
        }

        @Override
        public void hitbox(Rect out) {
            out.set(this.x, this.y, w(), h());
        }

        @Override
        public void drawCracks() {
            //stretching, should be normal cracks
            if (this.block.drawCracks && this.damaged() && prefSize() <= 7) {
                TextureRegion region = Vars.renderer.blocks.cracks[prefSize() - 1][Mathf.clamp((int) ((1.0F - this.healthf()) * 8.0F), 0, 7)];
                Draw.colorl(0.2F, 0.1F + (1.0F - this.healthf()) * 0.6F);
                Draw.rect(region, this.x, this.y, w(), h());
                Draw.color();
            }
        }
    }
}
