package technologium.entities.unit;

import arc.struct.FloatSeq;
import arc.util.io.Reads;
import arc.util.io.Writes;

import java.util.HashMap;

public interface UnitModification {
    /// adds modification to unit
    default void add(ModificatorType mt, float value){
        FloatSeq values= getModifiers().get(mt);
        if(values==null){
            getModifiers().put(mt,new FloatSeq(new float[]{value}));
        }else{
            values.add(value);
        }
    }

    /** removes modification from unit
      *@return if value was removed */
    default boolean remove(ModificatorType mt,float value) {
        FloatSeq values = getModifiers().get(mt);
        if(values==null)return false;
        return values.removeValue(value);
    }

    HashMap<ModificatorType,FloatSeq> getModifiers();

    static void writeModifiers(Writes w, HashMap<ModificatorType,FloatSeq> mds) {
        //pairs' count
        w.i(mds.size());
        mds.forEach((mt,fs)->{
            w.i(mt.id);
            w.i(fs.size);
            for (float v : fs.items) {
                w.f(v);
            }
        });
    }
    static HashMap<ModificatorType, FloatSeq> readModifiers(Reads r) {
        int pairs = r.i();
        HashMap<ModificatorType,FloatSeq> mds = new HashMap<>(pairs);
        for (int i = 0; i < pairs; i++) {
            ModificatorType mt = ModificatorType.getByID(r.i());
            int size = r.i();
            FloatSeq fs = new FloatSeq(size);
            for (int j = 0; j < size; j++) {
                fs.add(r.f());
            }
            mds.put(mt,fs);
        }
        return mds;
    }
}
