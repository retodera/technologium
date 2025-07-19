package technologium.world.blocks.multi;

import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.world.Block;
import technologium.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * pattern of multiblock
 * @author nadocd
 */
public class Pattern {
    // colorful javadocs yay!
    /**
     * determines if this patter should rotate with <a color='#ff7f00'>main</a>
     */
    public boolean useRotation;
    /** block-offset data, default (data[0]) direction is --> */
    final Pair<BlockPart,Point2>[][] data;
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
            if(pair.first.isMain()&&pair.second.equals(0,0)){
                main= pair.first;
            }
        }
        if(useRotation){
            this.data[1]=rotate(1);
            this.data[2]=rotate(2);
            this.data[3]=rotate(3);
        }
    }

    /**
     * Fully rotates all offsets around (0,0)
     * @param val amount of steps, step == pi/2
     * @return rotated data[0]
     */
    @SuppressWarnings({"DataFlowIssue", "unchecked"})
    private Pair<BlockPart,Point2>[] rotate(int val){
        Pair<BlockPart, Point2>[] base = data[0];
        Pair<BlockPart, Point2>[] rotated = new Pair[base.length];
        for (int i = 0; i < base.length; i++) {
            rotated[i]=new Pair<>(base[i].first,base[i].second.cpy().rotate(val));
        }
        return rotated;
    }

    // for visual things
    // unreadable code

    /**
     * Counts how many blocks match pattern and divides by their count
     * @param build main build ({@link BlockPart#isMain()})
     * @return progress in range [0;1]
     */
    public float calculatePercentage(Building build) {
        if (cache.containsKey(build)){
            Float val = cache.get(build).second;
            if (val != null && val >= 0) {
                return val;
            }
        }else{
            cache.computeIfAbsent(build,k->new Pair<>()).second=-1.0f;
        }
        if(!(build.block instanceof BlockPart bp) || !bp.isMain() || bp != this.main)return 0;
        float result=0;
        for (Pair<BlockPart, Point2> offset : data[useRotation?build.rotation:0]) {
            Block nearby = build.nearby(offset.second.x,offset.second.y).block;
            if(nearby instanceof BlockPart bpN && bpN == offset.first)result++;
        }
        return cache.get(build).second = result/ data.length;
    }

    /**
     * Checks all surroundings, if at least 1 doesn't match returns false, else true
     * @param build main build ({@link BlockPart#isMain()})
     * @return if blocks placed in this pattern
     */
    public boolean matches(Building build){
        if (cache.containsKey(build) &&cache.get(build).first != null) {
            return cache.get(build).first;
        }
        boolean val=true;
        if(!(build.block instanceof BlockPart bp) || !bp.isMain() || bp != this.main)val = false;
        else for (Pair<BlockPart, Point2> offset : data[useRotation?build.rotation:0]) {
            Block nearby = build.nearby(offset.second.x,offset.second.y).block;
            if (!(nearby instanceof BlockPart bpN) || bpN != offset.first) {
                val = false;
                break;
            }
        }
        if(build.block instanceof BlockPart )cache.computeIfAbsent(build,k->new Pair<>()).first=val;
        return val;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pattern pattern)) return false;
        return useRotation == pattern.useRotation && Objects.deepEquals(data, pattern.data) && Objects.equals(main, pattern.main);
    }

    // calculating patterns every tick is very bad
    /**
     * {@code HM<Building,P<matches,percentage>>}
     */
    public static final HashMap<Building,Pair<Boolean,Float>> cache=new HashMap<>(20);
}
