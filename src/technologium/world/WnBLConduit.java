package technologium.world;

import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;
import arc.util.*;
import arc.struct.*;

//WnBL means White & Black List
public class WnBLConduit extends Conduit {

    public @Nullable Seq<Liquid> whitelist = new Seq<>();
    public @Nullable Seq<Liquid> blacklist = new Seq<>();

    public WnBLConduit(String name) {
        super(name);
    }

    public class WnBLConduitBuild extends ConduitBuild {

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            noSleep();
            if (whitelist != null) {
                for (Liquid wl : whitelist) {
                    if ((liquid == wl && liquids.currentAmount() < 0.2) && (tile == null || source == this
                            || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation)) {
                        return true;
                    }
                }
                for (Liquid bl : blacklist) {
                    if ((liquid != bl && liquids.currentAmount() < 0.2) && (tile == null || source == this
                            || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
