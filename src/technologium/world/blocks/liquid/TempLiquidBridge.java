package technologium.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.world.blocks.liquid.LiquidBridge;
import technologium.world.meta.TStats;

import static mindustry.Vars.*;

//same feature as TempConduit
public class TempLiquidBridge extends LiquidBridge {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    /**how much damage will be dealt per tick */
    public float damage = 2 / 60f;
    public Effect explodeEffect = Fx.generatespark;

    public TempLiquidBridge(String name) {
        super(name);
        placeableLiquid = true;
    }
    
    @Override
    public void setStats() {
        super.setStats();
        stats.addPercent(TStats.maxTemp, maxTemp);
    }

    public class TempLiquidBridgeBuild extends LiquidBridgeBuild {
        @Override
        public void updateTile(){
            super.updateTile();
            if(liquids.current().temperature > maxTemp) damage(damage * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity);
            if(Mathf.chance(delta() * baseChance * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity)) {
                explodeEffect.at(x + Mathf.range(block.size * tilesize / 2f), y + Mathf.range(block.size * tilesize / 2f));
            }
        }
    }
}
