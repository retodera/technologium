package technologium.world.blocks.environment;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TreeBlock;
import mindustry.world.meta.BuildVisibility;
import technologium.type.Fruit;

import static mindustry.Vars.*;

public class GrowingVine extends TreeBlock {
    /**fruit that this tree will drop upon full growth */
    public @Nullable Fruit fruit;
    public float growTimeMin = 3200f, growTimeMax = 9600f;
    public float multTimeMin = 3200f, multTimeMax = 9600f;
    public Rand random = new Rand();
    public float damage = 1 / 60f;

    public GrowingVine(String name) {
        super(name);
        update = true;
        drawTeamOverlay = drawDisabled = false;
        hasItems = hasLiquids = hasPower = false;
        buildVisibility = BuildVisibility.editorOnly;
        breakable = destructible = targetable = configurable = false;
    }

    @Override
    public void setBars() {}

    @Override
    public void setStats() {}

    @Override
    public void drawBase(Tile tile) {
        tile.build.draw();
    }

    @Override
    public boolean isAccessible() {
        return state.isEditor();
    }

    @Override
    public boolean canBreak(Tile tile) {
        return ((GrowingVineBuild)tile.build).parent != null;
    }

    public class GrowingVineBuild extends Building {
        public @Nullable Building parent;
        protected int parentPos = -1;
        public float grow = 0;
        public float growTime = 0;
        public float mult = 0;
        public float multTime = 0;

        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public boolean collision(Bullet other) {
            return false;
        }

        @Override
        public void damage(float damage) {}

        @Override
        public void damage(Bullet bullet, Team source, float damage) {}

        @Override
        public void damage(float amount, boolean withEffect) {}

        @Override
        public void damage(Team source, float damage) {}

        @Override
        public void updateTile() {
            if(team != Team.derelict) {
                indexer.removeIndex(tile);
                team = Team.derelict;
                indexer.addIndex(tile);
            }
            parent = world.build(parentPos);
            parent = parent instanceof GrowingVineBuild ? parent : null;
            if(growTime <= 0) growTime = Mathf.random(growTimeMin, growTimeMax);
            if(grow < growTime) {
                grow += Time.delta;
                grow = Math.min(grow, growTime);
            }
            else {
                Seq<Tile> availableTiles = new Seq<>();
                for(int x = 0; x < 3; x++) 
                    for(int y = 0; y < 3; y++) {
                        Tile t = world.tile(tile.x + x - 1, tile.y + y - 1);
                        if(t == null) continue;
                        if(t.build == null) availableTiles.addUnique(t);
                        else availableTiles.remove(t);
                    }
                
                if(multTime == 0) multTime = Mathf.random(multTimeMin, multTimeMax);
                mult += Time.delta;
                mult = Math.min(mult, multTime);
                if(mult == multTime) {
                    mult = multTime = 0;
                    if(!availableTiles.isEmpty()) {
                        int rand = random.nextInt(availableTiles.size);
                        int i = 0;
                        for(Tile t : availableTiles) {
                            if(rand == i && t.build == null) {
                                t.setNet(this.block);
                                if (t.build != null)
                                    ((GrowingVineBuild)t.build).parentPos = parent == null ? pos() : parent.pos();
                            }
                            i++;
                        }
                    }
                    
                }
                for(int x = 0; x < 3; x++) 
                    for(int y = 0; y < 3; y++) {
                        Tile t = world.tile(tile.x + x - 1, tile.y + y - 1);
                        if(t == null) continue;
                        if(t.build != null && (team == Team.derelict || team != t.build.team)) t.build.damage(damage);
                    }
            }
        }

        @Override
        public void draw() {
            float x = tile.worldx();
            float y = tile.worldy();
            float rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50, 0.5f) + Mathf.sin(Time.time - y, 65, 0.9f) + Mathf.sin(Time.time + y - x, 85, 0.9f);
            float w = region.width * region.scl() * growPercent();
            float h = region.height * region.scl() * growPercent();
            float scl = 30;
            float mag = 0.2f;
            TextureRegion shad = variants == 0 ? customShadowRegion : variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))];
            if (shad.found()) {
                Draw.z(69);
                Draw.rect(shad, tile.worldx() + shadowOffset * growPercent(), tile.worldy() + shadowOffset * growPercent(), shad.width * shad.scl() * growPercent(), shad.height * shad.scl() * growPercent(), rot);
            }

            TextureRegion reg = variants == 0 ? region : variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))];
            Draw.z(71);
            Draw.rectv(reg, x, y, w, h, rot, (vec) -> {
                vec.add(Mathf.sin(vec.y * 3 + Time.time, scl, mag) + Mathf.sin(vec.x * 3 - Time.time, 70, 0.8f), Mathf.cos(vec.x * 3 + Time.time + 8, scl + 6, mag * 1.1f) + Mathf.sin(vec.y * 3 - Time.time, 50, 0.2f));
            });
        }

        @Override
        public String getDisplayName() {
            return block.localizedName + (team == player.team() || team == Team.derelict || team.emoji.isEmpty() ? "" : " " + team.emoji);
        }

        @Override
        public boolean interactable(Team team) {
            return false;
        }

        public float growPercent() {
            return grow / growTime;
        }
        
        @Override
        public boolean canPickup() {
            return false;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(grow);
            write.f(growTime);
            write.f(mult);
            write.f(multTime);
            write.i(parent == null ? -1 : parent.pos());
        }
        
        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            grow = read.f();
            growTime = read.f();
            mult = read.f();
            multTime = read.f();
            parentPos = read.i();
        }
    }
}
