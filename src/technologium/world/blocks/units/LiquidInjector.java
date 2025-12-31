package technologium.world.blocks.units;

import mindustry.gen.Building;
import mindustry.type.Liquid;

public class LiquidInjector extends PlatformModuleBlock {
    public LiquidInjector(String name) {
        super(name);
        hasLiquids = true;
    }

    public class LiquidInjectorBuild extends PlatformModuleBuild {
        
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return link == null ? false : link.requiredLiquids().has(liquid) && liquids.get(liquid) < liquidCapacity;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(link != null) liquids.each((l, a) -> {
                if(a > 0.001f && link.requiredLiquids().has(l)) liquids.remove(l, link.injectLiquid(l, a));
            });
        }
    }
}
