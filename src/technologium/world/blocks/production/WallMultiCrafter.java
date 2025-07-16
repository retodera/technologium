package technologium.world.blocks.production;

import mindustry.world.*;
import mindustry.world.meta.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.struct.ObjectMap.Entry;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.ui.Bar;
import arc.math.*;
import arc.math.geom.Geometry;
import arc.scene.ui.Image;
import arc.Core;

import static mindustry.Vars.*;

public class WallMultiCrafter extends Block {
    static int idx;
    public TextureRegion topRegion, rotatorBottomRegion, rotatorRegion;
    public float drillTime = 150f;
    public Effect updateEffect = Fx.mineWallSmall;
    public float updateEffectChance = 0.02f;
    public float rotateSpeed = 2f;
    /**Map of item outputs and multipliers for each attribute */
    public ObjectMap<Attribute, Entry<Item, Float>> recipes = new ObjectMap<>();

    public WallMultiCrafter(String name) {
        super(name);
        hasItems = true;
        rotate = true;
        update = true;
        solid = true;
        regionRotated1 = 1;
        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }

    @Override
    public void load() {
        super.load();
        topRegion = Core.atlas.find(name + "-top");
        rotatorBottomRegion = Core.atlas.find(name + "-rotator-bottom");
        rotatorRegion = Core.atlas.find(name + "-rotator");
    }

    protected void addRecipe(Attribute a, Item i, float ef) {
        Entry<Item, Float> e = new Entry<>();
        e.key = i;
        e.value = ef;
        recipes.put(a,e);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("drillspeed", (WallMultiCrafterBuild e) ->
            new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastEfficiency * 60 / drillTime, 2)), () -> Pal.ammo, () -> e.warmup));
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.tiles, table -> {
            table.left().row();
            recipes.each((a, i) -> {
                table.table(t -> {
                    t.left();
                    t.add(new Image(i.key.uiIcon)).size(64).scaling(Scaling.fit).left();
                    t.add(" \ue802 ");
                    StatValues.blocks(a, floating, 1, true, false).display(t);
                });
                table.row();
            });
        });
        stats.add(Stat.drillSpeed, 60f / drillTime * size, StatUnit.itemsSecond);
    }

    @Override
    public void init() {
        super.init();
        recipes.each((a, i) -> {
            if(Mathf.zero(i.value) || i.key == null) recipes.remove(a);
        });
        if(recipes.isEmpty()) throw new ArcRuntimeException("WallMultiCrafter [" + name + "] has no recipes! It might be because you put nonexistent items or nulls.");
    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, topRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(topRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    Entry<Item, Float> getefficiency(int tx, int ty, int rotation, @Nullable Cons<Tile> ctile, @Nullable Intc2 cpos){
        ObjectFloatMap<Item> eff = new ObjectFloatMap<>();
        int cornerX = tx - (size-1)/2, cornerY = ty - (size-1)/2, s = size;

        for(int i = 0; i < size; i++){
            int rx = 0, ry = 0;

            switch(rotation){
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }
            if(cpos != null) cpos.get(rx, ry);
            
            Tile other = world.tile(rx, ry);
            if(other != null && other.solid()){
                recipes.each((a, it) -> {
                    float at = other.block().attributes.get(a);
                    float ef = eff.get(it.key, 0);
                    eff.put(it.key, ef + at * it.value);
                    if(at > 0 && ctile != null) ctile.get(other);
                });
            }
        }
        if(eff.isEmpty()) return null;
        // don't bother about choosing between multiple attributes, just choose the most efficient one
        Entry<Item, Float> ef = new Entry<>();
        ef.value = 0f;
        eff.each(e -> {
            if(e.value > ef.value) {
                ef.value = e.value;
                ef.key = e.key;
            }
        });
        return ef;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        var eff = getefficiency(x, y, rotation, null, null);
        float ef = eff == null ? 0 : eff.value;
        drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / drillTime * ef, 2), x, y, valid);
    }
    
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        var eff = getefficiency(tile.x, tile.y, rotation, null, null);
        return eff == null ? false : eff.value > 0;
    }

    public class WallMultiCrafterBuild extends Building {
        public Item mineItem;
        public float warmup, lastEfficiency, time, totalTime;

        @Override
        public void updateTile(){
            super.updateTile();

            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 40f);
            float dx = Geometry.d4x(rotation) * 0.5f, dy = Geometry.d4y(rotation) * 0.5f;

            var ef = getefficiency(tile.x, tile.y, rotation, dest -> {
                if(wasVisible && shouldConsume() && Mathf.chanceDelta(updateEffectChance * warmup)){
                    updateEffect.at(
                        dest.worldx() + Mathf.range(3f) - dx * tilesize,
                        dest.worldy() + Mathf.range(3f) - dy * tilesize,
                        dest.block().mapColor
                    );
                }
            }, null);
            float eff = 0;
            if(ef != null) {
                mineItem = ef.key;
                eff = ef.value;
            }
            else {
                mineItem = null;
                eff = 0;
            }
            lastEfficiency = eff * timeScale * efficiency;

            if(shouldConsume() && (time += edelta() * eff) >= drillTime){
                offload(mineItem);
                time %= drillTime;
            }

            totalTime += edelta() * warmup * (eff <= 0f ? 0f : 1f);

            if(timer(timerDump, dumpTime)){
                dump(mineItem);
            }
        }

        @Override
        public boolean shouldConsume() {
            return mineItem == null ? false : items.get(mineItem) < itemCapacity;
        }

        @Override
        public void draw(){
            Draw.rect(block.region, x, y);
            Draw.rect(topRegion, x, y, rotdeg());
            float ds = 0.6f, dx = Geometry.d4x(rotation) * ds, dy = Geometry.d4y(rotation) * ds;

            int bs = (rotation == 0 || rotation == 3) ? 1 : -1;
            idx = 0;
            getefficiency(tile.x, tile.y, rotation, null, (cx, cy) -> {
                int sign = idx++ >= size/2 && size % 2 == 0 ? -1 : 1;
                float vx = (cx - dx) * tilesize, vy = (cy - dy) * tilesize;
                Draw.z(Layer.blockOver);
                Draw.rect(rotatorBottomRegion, vx, vy, totalTime * rotateSpeed * sign * bs);
                Draw.rect(rotatorRegion, vx, vy);
            });
        }
    }
}
