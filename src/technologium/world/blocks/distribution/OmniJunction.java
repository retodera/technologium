package technologium.world.blocks.distribution;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.meta.Stat;

public class OmniJunction extends Junction {
    public OmniJunction(String name) {
        super(name);
        hasLiquids = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.liquidCapacity);
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("liquid");
    }

    public class OmniJunctionBuild extends JunctionBuild {
        @Override
        public Building getLiquidDestination(Building source, Liquid liquid){
            if(!enabled) return this;

            int dir = (source.relativeTo(tile.x, tile.y) + 4) % 4;
            Building next = nearby(dir);
            if(next == null || (!next.acceptLiquid(this, liquid) && !(next.block instanceof LiquidJunction))){
                return this;
            }
            return next.getLiquidDestination(this, liquid);
        }
    }
}
