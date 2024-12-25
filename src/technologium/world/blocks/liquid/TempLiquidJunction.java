package technologium.world.blocks;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.LiquidJunction;
import static mindustry.Vars.tilesize;

public class TempLiquidJunction extends LiquidJunction {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    public Effect explodeEffect = Fx.generatespark;

    public TempLiquidJunction(String name) {
        super(name);
        placeableLiquid = true;
    }

    public class TempLiquidJunctionBuild extends LiquidJunctionBuild {

        @Override

        public Building getLiquidDestination(Building source, Liquid liquid){
            if(!enabled) return this;

            if(Mathf.chance(this.delta() * baseChance * (liquid.temperature - maxTemp))) {
                this.damage(4f);
                explodeEffect.at(this.x + Mathf.range(this.block.size * tilesize / 2f), this.y + Mathf.range(this.block.size * tilesize / 2f));
            }
            
            int dir = (source.relativeTo(tile.x, tile.y) + 4) % 4;
            Building next = nearby(dir);
            if(next == null || (!next.acceptLiquid(this, liquid) && !(next.block instanceof LiquidJunction))){
                return this;
            }
            return next.getLiquidDestination(this, liquid);
        }
    }
}
