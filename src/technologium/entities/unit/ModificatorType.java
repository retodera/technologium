package technologium.entities.unit;

import arc.struct.FloatSeq;
import arc.struct.Seq;

public class ModificatorType {
    private static int count = 0;
    private static final Seq<ModificatorType> all = new Seq<>();
    public final int id;
    public final String name;

    public ModificatorType(String name) {
        if(all.contains(mt->mt.name.equals(name)))throw new IllegalArgumentException("modifier '"+name+"' already exists");
        this.name = name;
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
            damageMultiplicative = new ModificatorType("damage");
    //TODO implement other modifiers
}
