package technologium.world.blocks.liquid;

import static mindustry.Vars.tilesize;

import arc.math.Mathf;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.content.Fx;
import mindustry.entities.Effect;

//same feature as TempConduit
public class TempLiquidRouter extends LiquidRouter{
    public float maxTemp = 0.65f, baseChance = 0.06f;
    public Effect explodeEffect = Fx.generatespark;

    public TempLiquidRouter(String name) {
        super(name);
        placeableLiquid = true;
    }

    public class TempLiquidRouterBuild extends LiquidRouterBuild {

        @Override

        public void updateTile(){
            if(Mathf.chance(this.delta() * baseChance * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity)) {
                this.damage(4f);
                explodeEffect.at(this.x + Mathf.range(this.block.size * tilesize / 2f), this.y + Mathf.range(this.block.size * tilesize / 2f));
            }

            if(liquids.currentAmount() > 0.01f){
                dumpLiquid(liquids.current());
            }
        }
    }
}
