package technologium.world.meta;

import mindustry.world.meta.Attribute;

public class TAttributes {
    public static Attribute
    goldAttr,
    neoplasmAttr;

    public static void load() {
        goldAttr = Attribute.add("gold");
        neoplasmAttr = Attribute.add("neoplasm");
    }
}
