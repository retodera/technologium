package technologium.world.blocks.production;

import mindustry.content.Blocks;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.environment.TreeBlock;
import technologium.graphics.TPal;
import technologium.type.Fruit;
import mindustry.ui.*;
import technologium.world.blocks.production.ACCrane.ACCraneBuild;
import arc.math.Mathf;
import arc.graphics.g2d.*;
import arc.Core;
import arc.graphics.Color;
import arc.util.io.*;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;

import static mindustry.Vars.*;

public class Pot extends Block {

    public Pot(String name) {
        super(name);
        update = true;
        configurable = true;
        solid = true;
        rotate = false;
        hasItems = hasLiquids = hasPower = false;

        config(Item.class, (PotBuild entity, Item item) -> {
            entity.prefere = item instanceof Fruit ? (Fruit)item : null;
            if(entity.prefere != null && entity.fruit != entity.prefere) entity.fruit = null;
        });
        configClear((PotBuild entity) -> entity.prefere = null);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("growth", entity -> {
            PotBuild b = (PotBuild)entity;

            return new Bar(() -> {
                if(b.crane == null) return Core.bundle.get("bar.growconnect");
                if(b.crane.links.find(l -> l.x == entity.tileX() && l.y == entity.tileY()) == null)
                    return Core.bundle.get("bar.growconnect");
                else if(b.fruit == null) return Core.bundle.get("bar.growinput");
                else if(!b.done()) return Core.bundle.format("bar.growleft",
                    Math.floor((b.growTime - b.curGrowTime) / 60));
                else return Core.bundle.get("bar.growdone");
            },
            () -> {
                if(b.crane == null) return Color.red;
                if(b.crane.links.find(l -> l.x == entity.tileX() && l.y == entity.tileY()) == null) return TPal.red3;
                else if(b.fruit == null) return TPal.hematite3;
                else if(!b.done()) return new Color((1 - b.growPercent()) / 2, b.growPercent(), 0);
                else return TPal.green3;
            },
            () -> {
                if(b.crane == null) return 1;
                else if(b.crane.links.find(l -> l.x == entity.tileX() && l.y == entity.tileY()) == null || b.fruit == null || b.done()) return 1;
                else return b.growPercent();
            });
        });
    }

    public class PotBuild extends Building {
        protected float curGrowTime = 0f, growTime = 0f;
        protected ACCraneBuild crane;
        // had to add cause the crane isn't loaded yet while reading
        protected int cranePos;
        protected Fruit fruit;
        private Fruit prefere;

        public void updateTile() {
            Building build = world.build(cranePos);
            if(build instanceof ACCraneBuild) crane = (ACCraneBuild)build;
            if(crane == null) {
                crane = null;
                fruit = null;
                curGrowTime = 0f;
                growTime = 0f;
            }
            if(fruit == null) curGrowTime = growTime = 0f;
            else if(!state.isPaused()) {
                if(growTime == 0) random();
                curGrowTime = Mathf.clamp(curGrowTime + edelta() * crane.warmup, 0, growTime);
            }
        }

        public void draw() {
            Draw.rect(region, x, y);
            if(fruit != null) {
                TreeBlock tree = fruit.tree == null ? (TreeBlock)Blocks.pine : (TreeBlock)fruit.tree;
                float
                    x = tile.worldx(),
                    y = tile.worldy(),
                    rot = Mathf.randomSeed(tile.pos(), 0, 4) * 90 + Mathf.sin(Time.time + x, 50f, 0.5f) + Mathf.sin(Time.time - y, 65f, 0.9f) + Mathf.sin(Time.time + y - x, 85f, 0.9f),
                    w = tree.region.width * tree.region.scl() * growPercent(),
                    h = tree.region.height * tree.region.scl() * growPercent(),
                    scl = 30f,
                    mag = 0.2f;

                TextureRegion shad = tree.variants == 0 ? tree.customShadowRegion : tree.variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, tree.variantShadowRegions.length - 1))];

                if(shad.found()){
                    Draw.z(Layer.power - 1);
                    Draw.rect(shad, tile.worldx() + tree.shadowOffset * growPercent(), tile.worldy() + tree.shadowOffset * growPercent(), shad.width / 4 * growPercent(), shad.height / 4 * growPercent(), rot);
                }

                TextureRegion reg = tree.variants == 0 ? tree.region : tree.variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, tree.variantRegions.length - 1))];
                
                Draw.z(Layer.power + 1);
                Draw.rectv(reg, x, y, w, h, rot, vec -> vec.add(
                    Mathf.sin(vec.y*3 + Time.time, scl, mag) + Mathf.sin(vec.x*3 - Time.time, 70, 0.8f) * growPercent(),
                    Mathf.cos(vec.x*3 + Time.time + 8, scl + 6f, mag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50, 0.2f) * growPercent()
                ));
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(Pot.this, table,
            Seq.with(content.items()).removeAll(i -> !(i instanceof Fruit))
                .removeAll(i -> !((Fruit)i).plantable),
                () -> prefere, this::configure, selectionRows, selectionColumns
            );
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(prefere == null ? -1 : prefere.id);
            write.s(fruit == null ? -1 : fruit.id);
            write.f(curGrowTime);
            write.f(growTime);
            write.i(crane == null ? -1 : crane.pos());
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            Item i = content.item(read.s());
            prefere = i instanceof Fruit ? (Fruit)i : null;
            i = content.item(read.s());
            fruit = i instanceof Fruit ? (Fruit)i : null;
            curGrowTime = read.f();
            growTime = read.f();
            cranePos = read.i();
        }

        public boolean done() {
            return fruit != null && curGrowTime == growTime;
        }

        protected void random() {
            if(growTime == 0) growTime = fruit != null ? Mathf.random(fruit.growTimeMin, fruit.growTimeMax) : 0f;
        }

        public float growPercent() {
            return fruit == null ? 0f : curGrowTime / growTime;
        }

        public Fruit fruit() {
            return fruit;
        }

        public Fruit prefere() {
            return prefere;
        }

        public int crane() {
            return cranePos;
        }
    }
}
