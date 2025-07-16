package technologium.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.LiquidJunction;
import technologium.world.meta.TStats;

import static mindustry.Vars.tilesize;

public class TempLiquidJunction extends LiquidJunction {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    /**how much damage will be dealt per tick */
    public float damage = 2 / 60f;
    public Effect explodeEffect = Fx.generatespark;

    public TempLiquidJunction(String name) {
        super(name);
        solid = false;
        placeableLiquid = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.addPercent(TStats.maxTemp, maxTemp);
    }

    public class TempLiquidJunctionBuild extends LiquidJunctionBuild {
        @Override
        public Building getLiquidDestination(Building source, Liquid liquid){
            if(liquid.temperature > maxTemp) damage(damage * (liquids.current().temperature - maxTemp));
            if(Mathf.chance(delta() * baseChance * (liquid.temperature - maxTemp))) {
                explodeEffect.at(x + Mathf.range(block.size * tilesize / 2f), y + Mathf.range(block.size * tilesize / 2f));
            }
            return super.getLiquidDestination(source, liquid);
        }
    }
}
