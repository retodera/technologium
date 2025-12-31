package technologium.world.blocks.defense;

import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import technologium.world.meta.TStatValues;
import mindustry.world.draw.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;

import static mindustry.Vars.*;

/**RegenProjector but damages enemy units and in waves like MendProjector */
public class SupressionProjector extends Block {
    public float range = 10;
    public float damage = 10f;
    public float damageInterval = 30f;
    public float optionalDamage = 20f;
    public float optionalDamageInterval = 20f;
    public float optionalUseTime = 120f;
    public StatusEffect status = StatusEffects.none;
    public float statusDuration;

    public Color baseColor = Color.valueOf("ff007f");
    public Color optionalColor = Color.valueOf("ff003f");

    public DrawBlock drawer = new DrawDefault();

    public SupressionProjector(String name) {
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
        hasPower = true;
        hasItems = true;
        emitLight = true;
        envEnabled |= Env.space;
    }

    @Override
    public void init() {
        super.init();
        clipSize = Math.max(range*tilesize*4, size);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Drawf.dashSquare(baseColor, x, y, range*tilesize*2);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public void setStats(){
        stats.timePeriod = optionalUseTime;
        super.setStats();

        stats.add(Stat.damage, damage / damageInterval, StatUnit.seconds);
        stats.add(Stat.range, range, StatUnit.blocks);

        if(findConsumer(c -> c instanceof ConsumeItems) instanceof ConsumeItems cons){
            stats.remove(Stat.booster);
            stats.add(Stat.booster, TStatValues.itemBoosters(
                "{0}" + StatUnit.timesSpeed.localized(),
                stats.timePeriod, damageInterval / optionalDamageInterval, 0f, optionalDamage / damage,
                cons.items)
            );
        }
    }

    public class SupressionProjectorBuild extends Building {
        public float warmup, totalTime, charge = Mathf.random(damageInterval), optionalTimer, smoothOpacity, smoothOptional;

        @Override
        public void updateTile() {
            warmup = Mathf.approachDelta(warmup, efficiency, 0.019f);
            smoothOptional = Mathf.approachDelta(smoothOptional, optionalEfficiency, 0.05f);
            totalTime += warmup * Time.delta;
            smoothOpacity = Mathf.approachDelta(smoothOpacity, anyTargets() ? 1f : 0f, 0.05f);
            if(!anyTargets()) return;
            charge += warmup * Time.delta;

            if((optionalTimer += Time.delta * optionalEfficiency) >= optionalUseTime) {
                consume();
                optionalTimer = 0f;
            }

            if(charge >= Mathf.lerp(damageInterval, optionalDamageInterval, smoothOptional)) {
                charge = 0f;
                Units.nearby(x-range*tilesize, y-range*tilesize, range*tilesize*2, range*tilesize*2, u -> {
                    if(u.team() != team && u.health > 0f) {
                        u.damagePierce(Mathf.lerp(damage, optionalDamage, smoothOptional));
                        u.apply(status, statusDuration);
                    }
                });
            }
        }

        @Override
        public boolean shouldConsume(){
            return anyTargets();
        }

        public boolean anyTargets() {
            return Units.any(x-range*tilesize, y-range*tilesize, range*tilesize*2, range*tilesize*2, u -> u.team() != team && u.health > 0f && u.hittable());
        }

        @Override
        public void drawSelect(){
            super.drawSelect();
            Drawf.dashSquare(baseColor, x, y, range*tilesize*2);
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public float totalProgress(){
            return totalTime;
        }

        @Override
        public void draw(){
            drawer.draw(this);

            Draw.z(Layer.overlayUI-2.5f);
            Draw.color(baseColor.cpy().lerp(optionalColor, smoothOptional));
            Lines.stroke(1.5f);
            Draw.alpha(0.19f * Mathf.clamp(warmup+0.3f));
            Fill.rect(x, y, range*tilesize*2, range*tilesize*2);
            Draw.alpha(0.65f * Mathf.clamp(warmup+0.3f));
            Lines.rect(x-range*tilesize, y-range*tilesize, range*tilesize*2, range*tilesize*2);
            Draw.reset();
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }
    }
}
