package technologium.world;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;
import technologium.content.TLiquids;

//feel free to copy this code, just change the "plasmConduit" and "Tliquids.liquidPlasm" to whatever you want

public class PlasmConduit extends Conduit {

    public PlasmConduit(String name) {
        super(name);
    }

    public class PlasmConduitBuild extends ConduitBuild {

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            noSleep();
            if (liquids.current() == TLiquids.liquidPlasm) {
                return (liquids.current() == liquid || liquids.currentAmount() < 0.2f)
                        && (tile == null || source == this || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation);
            } else {
                return false;
            }
        }
    }
}
