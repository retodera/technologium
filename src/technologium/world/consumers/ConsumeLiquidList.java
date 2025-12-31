package technologium.world.consumers;

import arc.struct.ObjectFloatMap;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeLiquidFilter;

public class ConsumeLiquidList extends ConsumeLiquidFilter {
    public ObjectFloatMap<Liquid> liquidMultipliers = new ObjectFloatMap<>();

    public ConsumeLiquidList(Object... objects){
        this();
        setMultipliers(objects);
    }

    public ConsumeLiquidList(Liquid... liquids){
        this();
        for(Liquid i : liquids){
            liquidMultipliers.put(i, 1f);
        }
    }

    public ConsumeLiquidList(){
        filter = liquidMultipliers::containsKey;
    }

    public void setMultipliers(Object... objects){
        for(int i = 0; i < objects.length; i += 2)
            liquidMultipliers.put((Liquid)objects[i], (Float)objects[i + 1]);
    }

    @Override
    public float efficiencyMultiplier(Building build){
        var liquid = getConsumed(build);
        return liquidMultipliers.get(liquid, 1f);
    }
}
