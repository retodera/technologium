package technologium.world.blocks.environment;

import arc.Events;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TreeBlock;
import mindustry.world.meta.BuildVisibility;
import technologium.game.TEventType.TTrigger;
import technologium.type.Fruit;

import static mindustry.Vars.*;

public class GrowingTreeBlock extends TreeBlock {
    public Fruit fruit;
    public float growTimeMin = 6400f, growTimeMax = 14400f;
    public Rand random = new Rand();

    public GrowingTreeBlock(String name) {
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
        return false;
    }

    public class GrowingTreeBuild extends Building {
        public float grow = 0;
        public float growTime = 0;
        public boolean grown = false;

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
            if(growTime <= 0) {
                if(fruit.seed != null) growTime = Mathf.random(((Fruit)fruit.seed).growTimeMin, ((Fruit)fruit.seed).growTimeMax);
                else if(fruit != null) growTime = Mathf.random(fruit.growTimeMin, fruit.growTimeMax);
                else growTime = Mathf.random(growTimeMin, growTimeMax);
            }
            if(grow < growTime) {
                grow += Time.delta;
                grow = Math.min(grow, growTime);
            }
            else if(!grown) {
                grown = true;
                Events.fire(TTrigger.treeGrown);
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
                Draw.z(69); //nice
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
        }
        
        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            grow = read.f();
            growTime = read.f();
        }
    }
}
