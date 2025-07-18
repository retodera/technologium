package technologium.world.blocks.multi;

import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.world.Block;
import technologium.util.Pair;

/**
 * pattern of multiblock
 */
public class Pattern {
    public boolean useRotation;
    private final Pair<BlockPart,Point2>[] data;
    public final BlockPart main;
    @SafeVarargs
    public Pattern(boolean useRotation, Pair<BlockPart,Point2>... data){
        this.useRotation = useRotation;
        this.data = data;
        for (Pair<BlockPart, Point2> pair : this.data) {
            if()
        }
    }
    /// for visual things (retodera delete if not needed)
    public float calculatePercentage(BlockPart.BuildPart main){
        if(!main.isMain())return 0;
        float result=0;
        for (Pair<Block, Point2> offset : data) {
            if
        }
    }
}
