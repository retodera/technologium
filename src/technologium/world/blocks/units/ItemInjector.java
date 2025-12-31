package technologium.world.blocks.units;

import mindustry.gen.Building;
import mindustry.type.Item;

public class ItemInjector extends PlatformModuleBlock {
    public ItemInjector(String name) {
        super(name);
        hasItems = true;
    }

    public class ItemInjectorBuild extends PlatformModuleBuild {
        
        @Override
        public boolean acceptItem(Building source, Item item) {
            return link == null ? false : link.requiredItems().has(item) && items.get(item) < itemCapacity;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(link != null) items.each((i, a) -> {
                if(a > 0 && link.requiredItems().has(i)) items.remove(i, link.injectItem(i, a));
            });
        }
    }
}