package technologium.entities.unit;

import arc.struct.FloatSeq;
import arc.util.io.Reads;
import arc.util.io.Writes;

import java.util.HashMap;

/**
 * Class for giving units support for modifications.
 *
 * @author nadocd
 */
@SuppressWarnings("unchecked")
public interface UnitModification {
    /// adds modification to unit
    default void add(ModifierType mt, float value) {
        getModifiers().computeIfAbsent(mt, k -> new FloatSeq()).add(value);
    }
    /// adds boolean modification to unit
    default void add(ModifierType mt, boolean value) {
        add(mt,value?1:0);
    }

    /**
     * removes modification from unit
     *
     * @return was value removed
     */
    default boolean remove(ModifierType mt, float value) {
        FloatSeq values = getModifiers().get(mt);
        if (values == null) return false;
        return values.removeValue(value);
    }

    /**
     * removes boolean modification from unit
     *
     * @return was value removed
     */
    default boolean remove(ModifierType mt, boolean value) {
        return remove(mt,value?1:0);
    }

    /** multiplies all values corresponding to given MT
     * in booleans {@code false} has higher priority
     */
    default <T> T compute(ModifierType mt) {
        Object result=null;
        FloatSeq values = getModifiers().get(mt);
        float calc = 1;
        if(values==null)result=1;
        else {
            for (float v : values.items) {
                if (v == 0) {
                    result = 0;
                    break;
                }
                calc *= v;
            }
        }
        // noinspection unchecked
        return (T)(result!=null?result:mt.isBool?calc:calc != 0);
    }

    /** @implNote if returns null, {@link NullPointerException} will be thrown*/
    HashMap<ModifierType, FloatSeq> getModifiers();

    /// just read method's name
    static void writeModifiers(Writes w, HashMap<ModifierType, FloatSeq> mds) {
        //pairs' count
        w.i(mds.size());
        mds.forEach((mt, fs) -> {
            w.i(mt.id);
            w.i(fs.size);
            for (float v : fs.items) {
                w.f(v);
            }
        });
    }

    /** reverse of {@link UnitModification#writeModifiers(Writes, HashMap)}*/
    static HashMap<ModifierType, FloatSeq> readModifiers(Reads r) {
        int pairs = r.i();
        HashMap<ModifierType, FloatSeq> mds = new HashMap<>(pairs);
        for (int i = 0; i < pairs; i++) {
            ModifierType mt = ModifierType.getByID(r.i());
            int size = r.i();
            FloatSeq fs = new FloatSeq(size);
            for (int j = 0; j < size; j++) {
                fs.add(r.f());
            }
            mds.put(mt, fs);
        }
        return mds;
    }
}
