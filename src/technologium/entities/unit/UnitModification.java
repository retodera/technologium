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
public interface UnitModification {
    /// adds modification to unit
    default void add(ModificatorType mt, float value) {
        getModifiers().computeIfAbsent(mt, k -> new FloatSeq()).add(value);
    }
    /// adds boolean modification to unit
    default void add(ModificatorType mt, boolean value) {
        add(mt,value?1:0);
    }

    /**
     * removes modification from unit
     *
     * @return was value removed
     */
    default boolean remove(ModificatorType mt, float value) {
        FloatSeq values = getModifiers().get(mt);
        if (values == null) return false;
        return values.removeValue(value);
    }

    /**
     * removes boolean modification from unit
     *
     * @return was value removed
     */
    default boolean remove(ModificatorType mt, boolean value) {
        return remove(mt,value?1:0);
    }

    /// multiplies all values corresponding to given MT
    default <T> T compute(ModificatorType mt) {
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

    /// if returns null, {@link NullPointerException} will be thrown
    HashMap<ModificatorType, FloatSeq> getModifiers();

    /// just read method's name
    static void writeModifiers(Writes w, HashMap<ModificatorType, FloatSeq> mds) {
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

    /// reverse of {@link UnitModification#writeModifiers(Writes, HashMap)}
    static HashMap<ModificatorType, FloatSeq> readModifiers(Reads r) {
        int pairs = r.i();
        HashMap<ModificatorType, FloatSeq> mds = new HashMap<>(pairs);
        for (int i = 0; i < pairs; i++) {
            ModificatorType mt = ModificatorType.getByID(r.i());
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
