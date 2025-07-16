package technologium.world.blocks.distribution;

import mindustry.Vars;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.Router;

public class SorterRouter extends Router {
    public SorterRouter(String name) {
        super(name);

        config(Integer[].class, (SorterRouterBuild b, Integer[] config) -> {
            if(config.length != 4) return;
            for(int i = 0; i < 4; i++) {
                b.config[i] = Vars.content.item(config[i]);
            }
        });
    }
    


    public class SorterRouterBuild extends RouterBuild {
        public Item[] config = new Item[4];
    }
}