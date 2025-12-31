package technologium.world.blocks.production.boost;

import technologium.world.blocks.production.boost.ProductionBooster.ProductionBoosterBuild;

public interface BoostableBlock {
    void addBoost(ProductionBoosterBuild build);
    void removeBoost(ProductionBoosterBuild build);
}
