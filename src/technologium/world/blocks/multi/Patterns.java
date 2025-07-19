package technologium.world.blocks.multi;

import arc.Events;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.gen.Building;
import technologium.content.TBlocks;

public class Patterns {
    public static final Seq<Pattern> patterns = new Seq<>();

    /** @apiNote should be called after {@link TBlocks#load()}*/
    public static void load() {
        Events.on(EventType.BlockBuildEndEvent.class, bb ->Pattern.cache.clear());
        Events.on(EventType.BlockDestroyEvent.class, bd ->Pattern.cache.clear());
        //idk why i added that, patterns doesn't support individual block rotations
        Events.on(EventType.BuildRotateEvent.class, br ->Pattern.cache.clear());
        Events.on(EventType.PickupEvent.class, p ->Pattern.cache.clear());
        Events.on(EventType.WorldLoadBeginEvent.class,wlb->Pattern.cache.clear());
        //patterns.addUnique(thing);
    }

    /**
     * expected pattern based on surroundings
     * @param building main build
     * @return pattern that most likely to be here or null
     * @apiNote i assume that will be used for visuals
     */
    public static Pattern expectedPattern(Building building) {
        float max = 0;
        Pattern pat = null;
        for (Pattern pattern : patterns) {
            float calc = pattern.calculatePercentage(building);
            if(calc>1.0f/pattern.data[0].length&&calc>max){
                max=calc;
                pat=pattern;
            }
        }
        return pat;
    }
}
