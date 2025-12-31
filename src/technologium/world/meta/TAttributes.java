package technologium.world.meta;

import mindustry.world.meta.Attribute;

public class TAttributes {
    public static Attribute
    goldAttr,
    neoplasmLiquid,
    neoplasmWall,
    pegmatiteWall,
    carbonAttr,
    sirinAttr;

    public static void load() {
        goldAttr = Attribute.add("gold");
        neoplasmLiquid = Attribute.add("neoplasm");
        neoplasmWall = Attribute.add("neoplasm-wall");
        pegmatiteWall = Attribute.add("pegmatite-wall");
        carbonAttr = Attribute.add("carbon");
        sirinAttr = Attribute.add("sirin");
    }
}
