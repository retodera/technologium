package technologium.world.blocks.production;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.world.blocks.production.AttributeCrafter;

public class TAttributeCrafter extends AttributeCrafter {
    public float optionalBoostIntensity = 1;
    public TAttributeCrafter(String name) {
        super(name);
    }

    public class TAttributeCrafterBuild extends AttributeCrafterBuild {
        @Override
        public void updateTile(){
            if(efficiency > 0){

                progress += getProgressIncrease(craftTime);
                float speed = Mathf.lerp(1, optionalBoostIntensity, optionalEfficiency) * efficiency;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);

                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f);
                    for(var output : outputLiquids)
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                }

                if(wasVisible && Mathf.chanceDelta(updateEffectChance))
                    updateEffect.at(x + Mathf.range(size * updateEffectSpread), y + Mathf.range(size * updateEffectSpread));
            }
            else warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            
            totalProgress += warmup * Time.delta;
            if(progress >= 1f) craft();

            dumpOutputs();
        }

        @Override
        public float efficiencyMultiplier(){
            return baseEfficiency + Math.min(maxBoost, boostScale * attrsum) * Mathf.lerp(1, optionalBoostIntensity, optionalEfficiency) * efficiency + attribute.env();
        }
    }
}
