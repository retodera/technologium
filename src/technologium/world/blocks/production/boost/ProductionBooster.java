package technologium.world.blocks.production.boost;

import arc.struct.*;
import mindustry.gen.Building;
import mindustry.world.Block;
import technologium.type.BoostType;

// TODO
public class ProductionBooster extends Block {
    /**amount of blocks that this block can boost. best used when the block is non-rotatable. */
    public int maxAffectedBlocks = 1;
    public ObjectFloatMap<BoostType> boostTypes = new ObjectFloatMap<>();

    public ProductionBooster(String name) {
        super(name);
    }

    public class ProductionBoosterBuild extends Building {
    }
}
