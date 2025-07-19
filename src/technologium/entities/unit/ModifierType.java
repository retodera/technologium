package technologium.entities.unit;

import arc.struct.Seq;

/**Modifies something in a unit. The value to modify is specified by name.
 * @author nadocd
 */
public class ModifierType {
    private static int count = 0;
    /// all created MTs, used for checking duplicates and finding by name or id
    private static final Seq<ModifierType> all = new Seq<>();
    /// used for I/O
    public final int id;
    public final String name;
    public final boolean isBool;

    private ModifierType(String name, boolean isBool) {
        if(all.contains(mt->name.equals(mt.name)))throw new IllegalArgumentException("modifier '" + name + "' already exists");
        this.name = name;
        this.isBool = isBool;
        id = count++;
        all.add(this);
    }

    public static ModifierType getByName(String name) {
        return all.find(mt->mt.name.equals(name));
    }
    public static ModifierType getByID(int id) {
        return all.find(mt->mt.id==id);
    }

    /**
     * @return count of registered MTs
     */
    public static int total() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ModifierType mt && mt.id == id;
    }

    @Override
    public String toString() {
        return "Modifier#" + id + ":" + name;
    }

    // +1 because null.hashcode() == 0
    @Override
    public int hashCode() {
        return id+1;
    }

    public static final ModifierType
    damage = new ModifierType("damage", false),
    speed = new ModifierType("speed", false),
    forceCanBuild = new ModifierType("can-build", true),
    toughness = new ModifierType("toughness",false),
    maxHealth = new ModifierType("max-health",false),
    armor = new ModifierType("armor",false),
    reload = new ModifierType("reload",false),
    noDrowning = new ModifierType("drowning-immunity",true)
    ;
    //implement other modifiers?
}
