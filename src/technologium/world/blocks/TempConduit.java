package technologium.world.blocks;

import static mindustry.Vars.tilesize;

import arc.math.Mathf;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;

//a conduit that breaks if the temp is higher than the maxTemp
public class TempConduit extends Conduit {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    public Effect explodeEffect = Fx.generatespark;

    public TempConduit(String name) {
        super(name);
        group = BlockGroup.liquids;
        rotate = true;
        placeableLiquid = true;
    }

    public class TempConduitBuild extends ConduitBuild {
        @Override
        public void updateTile() {
            if(Mathf.chance(this.delta() * baseChance * Mathf.clamp(liquids.current().temperature - maxTemp))) {
                this.damage(4f);
                explodeEffect.at(this.x + Mathf.range(this.block.size * tilesize / 2f), this.y + Mathf.range(this.block.size * tilesize / 2f));
            }
            smoothLiquid = Mathf.lerpDelta(smoothLiquid, liquids.currentAmount() / liquidCapacity, 0.05f);

            if(liquids.currentAmount() > 0.0001f && timer(timerFlow, 1)){
                moveLiquidForward(leaks, liquids.current());
                noSleep();
            }else{
                sleep();
            }
        }
    }
}
