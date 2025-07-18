package technologium.entities.unit;

import arc.struct.Seq;

/**Modifies something in a unit. The value to modify is specified by name.
 * @author nadocd
 */
public class ModificatorType {
    private static int count = 0;
    /// all created MTs, used for checking duplicates and finding by name or id
    private static final Seq<ModificatorType> all = new Seq<>();
    /// used for I/O
    public final int id;
    public final String name;
    public final boolean isBool;

    private ModificatorType(String name, boolean isBool) {
        if(all.contains(mt->name.equals(mt.name)))throw new IllegalArgumentException("modifier '" + name + "' already exists");
        this.name = name;
        this.isBool = isBool;
        id = count++;
        all.add(this);
    }

    public static ModificatorType getByName(String name) {
        return all.find(mt->mt.name.equals(name));
    }
    public static ModificatorType getByID(int id) {
        return all.find(mt->mt.id==id);
    }

    public static int total() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ModificatorType mt && mt.id == id;
    }

    @Override
    public String toString() {
        return "Modifier#" + id + ":" + name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static final ModificatorType
    damage = new ModificatorType("damage", false),
    speed = new ModificatorType("speed", false),
    forceCanBuild = new ModificatorType("can-build", true)
    ;
    //TODO implement other modifiers
}
