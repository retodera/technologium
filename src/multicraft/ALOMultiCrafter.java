package multicraft;

import arc.struct.*;

// - алло
// - да да мультикрафтер

/**
 * Advanced Liquid Output MultiCrafter.
 * basically is a cooler version of an electrolyzer
 */
public class ALOMultiCrafter extends MultiCrafter {
    public Seq<FloatPoint2> points = new Seq<>();

    public ALOMultiCrafter(String name) {
        super(name);
    }

    public class ALOMultiCrafterBuild extends MultiCrafterBuild {
        
    }

    public class FloatPoint2 {
        public float x, y;
        public FloatPoint2(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
