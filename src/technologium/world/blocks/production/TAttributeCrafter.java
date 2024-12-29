package technologium.world.blocks.production;

import arc.math.Mathf;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import arc.util.Time;
import mindustry.type.LiquidStack;

public class TAttributeCrafter extends AttributeCrafter {
    public float optionalBoostIntensity = 2.5f;

    public TAttributeCrafter(String name) {
        super(name);
        maxBoost = 2.5f;
    }
    
    @Override

    public void setStats() {
        super.setStats();

        stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attribute, floating, boostScale * size * size, !displayEfficiency);

        if(optionalBoostIntensity != 1 && findConsumer(f -> f instanceof ConsumeLiquidBase && f.booster) instanceof ConsumeLiquidBase consBase){
            stats.remove(Stat.booster);
            stats.add(Stat.booster,
                StatValues.speedBoosters("{0}" + StatUnit.timesSpeed.localized(),
                consBase.amount, optionalBoostIntensity, false,
                l -> (consumesLiquid(l) && (findConsumer(f -> f instanceof ConsumeLiquid).booster || ((ConsumeLiquid)findConsumer(f -> f instanceof ConsumeLiquid)).liquid != l)))
            );
        }
    }

    public class TAttributeCrafterBuild extends AttributeCrafterBuild {

        public void updateTile(){
            if(efficiency > 0){
    
                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget() + optionalEfficiency * optionalBoostIntensity, warmupSpeed);
    
                //continuously output based on efficiency
                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f);
                    for(LiquidStack output : outputLiquids){
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }
    
                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;
    
            if(progress >= 1f){
                craft();
            }
    
            dumpOutputs();
        }

        @Override

        public float efficiencyMultiplier() {
            return (baseEfficiency + Math.min(maxBoost, boostScale * attrsum) + attribute.env()) + optionalEfficiency * optionalBoostIntensity;
        }
    }
}
