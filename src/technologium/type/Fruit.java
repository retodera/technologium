package technologium.type;

import arc.graphics.*;
import mindustry.type.Item;
import technologium.world.meta.TStats;
import mindustry.world.Block;
import mindustry.content.Blocks;

public class Fruit extends Item {
    /** yea, i dunno what to do with these for now */
    public float juiciness = 0f, seedChance = 0f;
    /** growing time in ticks */
    public float growTimeMin = 600f, growTimeMax = 900f;
    /** the tree that will be growing (drawn) when planted in a pot */
    public Block tree = Blocks.pine;
    /** whether this can be planted */
    public boolean plantable = true;
    /** the item that will result from the tree being harvested */
    public Item result = this;

    public Fruit(String name, Color color) {
        super(name, color);
    }

    public Fruit(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.addPercent(TStats.juiciness, juiciness);
        stats.addPercent(TStats.seedChance, seedChance);
    }
}
