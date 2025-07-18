package technologium.world.blocks.multi;

import arc.math.geom.Point2;
import technologium.util.Pair;

/**
 * pattern of multiblock
 */
public class Pattern {
    public boolean useRotation;
    /// non-rotated data, default direction is ->
    private final Pair<BlockPart,Point2>[][] data;

    {
        //noinspection unchecked
        data = new Pair[4][];
    }

    private BlockPart main;
    @SafeVarargs
    public Pattern(boolean useRotation, Pair<BlockPart,Point2>... data){
        this.useRotation = useRotation;
        this.data[0]=data;
        for (Pair<BlockPart, Point2> pair : this.data[0]) {
            if(pair.first.isMain&&pair.second.equals(0,0)){
                main= pair.first;
            }
        }
        if(useRotation){
            this.data[1]=rotate(1);
            this.data[2]=rotate(2);
            this.data[3]=rotate(3);
        }
    }

    @SuppressWarnings({"DataFlowIssue", "unchecked"})
    private Pair<BlockPart,Point2>[] rotate(int val){
        Pair<BlockPart, Point2>[] base = data[0];
        Pair<BlockPart, Point2>[] rotated = new Pair[base.length];
        for (int i = 0; i < base.length; i++) {
            rotated[i]=new Pair<>(base[i].first,base[i].second.cpy().rotate(val));
        }
        return rotated;
    }

    /// for visual things (retodera delete if not needed)
    public float calculatePercentage(BlockPart.BuildPart build){
        if(!build.isMain() || build.block != this.main)return 0;
        float result=0;
        for (Pair<BlockPart, Point2> offset : data[useRotation?build.rotation:0]) {
            if(build.nearby(offset.second.x,offset.second.y).block == offset.first)result++;
        }
        return result/data.length;
    }

    public boolean matches(BlockPart.BuildPart build){
        if(!build.isMain() || build.block != this.main)return false;
        for (Pair<BlockPart, Point2> offset : data[useRotation?build.rotation:0]) {
            if(build.nearby(offset.second.x,offset.second.y).block != offset.first)return false;
        }
        return true;
    }
}
