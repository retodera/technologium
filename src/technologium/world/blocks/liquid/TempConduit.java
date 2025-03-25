package technologium.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.content.Fx;
import mindustry.entities.Effect;

import static mindustry.Vars.tilesize;

//a conduit that breaks if the temp is higher than the maxTemp
public class TempConduit extends Conduit {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    /**how much damage will be dealt per tick */
    public float damage = 2 / 60f;
    public Effect explodeEffect = Fx.generatespark;

    public TempConduit(String name) {
        super(name);
        rotate = true;
        placeableLiquid = true;
    }

    public class TempConduitBuild extends ConduitBuild {
        @Override
        public void updateTile() {
            if(liquids.current().temperature > maxTemp) damage(damage * (maxTemp - liquids.current().temperature) * liquids.currentAmount() / liquidCapacity);
            if(Mathf.chance(delta() * baseChance * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity)) {
                explodeEffect.at(x + Mathf.range(block.size * tilesize / 2f), y + Mathf.range(block.size * tilesize / 2f));
            }
            super.updateTile();
        }
    }
}
