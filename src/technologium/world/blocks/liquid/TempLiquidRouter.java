package technologium.world.blocks.liquid;

import static mindustry.Vars.tilesize;

import arc.math.Mathf;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.content.Fx;
import mindustry.entities.Effect;

//same feature as TempConduit
public class TempLiquidRouter extends LiquidRouter{
    public float maxTemp = 0.65f, baseChance = 0.06f;
    /**how much damage will be dealt per tick */
    public float damage = 2 / 60f;
    public Effect explodeEffect = Fx.generatespark;

    public TempLiquidRouter(String name) {
        super(name);
        placeableLiquid = true;
    }

    public class TempLiquidRouterBuild extends LiquidRouterBuild {
        @Override
        public void updateTile(){
            super.updateTile();
            if(liquids.current().temperature > maxTemp) damage(damage * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity);
            if(Mathf.chance(this.delta() * baseChance * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity)) {
                explodeEffect.at(x + Mathf.range(block.size * tilesize / 2f), y + Mathf.range(block.size * tilesize / 2f));
            }
        }
    }
}
