package technologium.type;

import arc.struct.Seq;

public class BoostType {
    public static final Seq<BoostType> allTypes = new Seq<>();
    public String name;

    public BoostType(String name) {this.name = name;}

    public static BoostType add(String name) {
        BoostType type = new BoostType(name);
        allTypes.add(type);
        return type;
    }

    public static BoostType
        power = add("power"),
        productivity = add("productivity"),
        resourceEfficiency = add("resource-efficiency"),
        speed = add("speed");
}
