package technologium.world.blocks.production;

//at first i wanted to make a DrawArmsAssemble, but the arms were all messed up when you placed two blocks with it, so i just made a new block with this
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Log;
import mindustry.gen.Sounds;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.meta.BlockFlag;
import multicraft.MultiCrafter;
import multicraft.Recipe;
import arc.util.Time;
import arc.struct.Seq;
import arc.Core;
import mindustry.entities.units.BuildPlan;
import arc.util.Eachable;
import arc.graphics.g2d.Draw;
import arc.math.Rand;

import static mindustry.Vars.*;

public class TConstructor extends MultiCrafter {
    public int armCount = 4;
    public TextureRegion[] armsTex;
    public TextureRegion bottomRegion, iconRegion;

    //arms rotation multiplier
    public float rotateSpeed = 1f,
    //offset relative to the center of the block
    offset = 8f,
    //arms movement length towards the center multiplier
    move = 2f,
    //internal use, do not change
    prog = 0,
    progn = 0,
    r = 0;

    public TConstructor(String name) {
        super(name);
        update = true;
        solid = true;
        sync = true;
        flags = EnumSet.of(BlockFlag.factory);
        ambientSound = Sounds.machine;
        configurable = true;
        saveConfig = true;
        ambientSoundVolume = 0.03f;
        config(Integer.class, MultiCrafterBuild::setCurRecipeIndexFromRemote);
        Log.info("ConstructorMultiCrafter[" + this.name + "] loaded.");
    }

    @Override
    public void load() {
        super.load();
        bottomRegion = Core.atlas.find(name + "-bottom");
        iconRegion = Core.atlas.find(name + "-arms-icon");
        armsTex = new TextureRegion[armCount];
        for(int i = 0; i < armCount; i++)
        armsTex[i] = Core.atlas.find(name + "-arm" + (i+1));
    }

    public TextureRegion[] icons() {
        return new TextureRegion[]{bottomRegion, iconRegion, region};
    }

    @Override

    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(bottomRegion, plan.drawx(), plan.drawy());
        Draw.rect(iconRegion, plan.drawx(), plan.drawy());
        Draw.rect(region, plan.drawx(), plan.drawy());
    }

    public class ConstructorBuild extends MultiCrafterBuild {
        public float totalProgress;
        public int armC = Mathf.clamp(armCount, 1, 16);
        public Seq<AssembleArm> arms = new Seq<>(armC);
        public Rand rand = new Rand();

        @Override
        public void updateTile() {
            Recipe cur = getCurRecipe();
            float craftTimeNeed = cur.craftTime;
            // As HeatConsumer
            if (cur.isConsumeHeat()) heat = calculateHeat(sideHeat);
            if (cur.isOutputHeat()) {
                float heatOutput = cur.output.heat;
                heat = Mathf.approachDelta(heat, heatOutput * efficiency, warmupRate * edelta());
            }
            // cool down
            if (efficiency > 0 && (!hasPower || getCurPowerStore() >= cur.input.power)) {
                // if <= 0, instantly produced
                if (craftTimeNeed > 0f) craftingTime += edelta();
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);
                if (hasPower) {
                    float powerChange = (cur.output.power - cur.input.power) * delta();
                    if (!Mathf.zero(powerChange))
                        setCurPowerStore((getCurPowerStore() + powerChange));
                }

                //continuously output fluid based on efficiency
                if (cur.isOutputFluid()) {
                    float increment = getProgressIncrease(1f);
                    for (LiquidStack output : cur.output.fluids) {
                        Liquid fluid = output.liquid;
                        handleLiquid(this, fluid, Math.min(output.amount * increment, liquidCapacity - liquids.get(fluid)));
                    }
                }
                // particle fx
                if (wasVisible && Mathf.chanceDelta(updateEffectChance))
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
            } else warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);

            //the only change
            totalProgress += warmup * Time.delta;

            if (craftTimeNeed <= 0f) {
                if (efficiency > 0f)
                    craft();
            } else if (craftingTime >= craftTimeNeed)
                craft();

            updateBars();
            dumpOutputs();
        }

        public void created() {
            for(int i = 0; i < armC; i++) {
                arms.add(new AssembleArm());
                arms.get(i).tex = armsTex[i];
            }
        }

        public void draw() {
            Draw.rect(bottomRegion, x, y);
            //used this instead of just totalProgress() because the arms stop and will have to continue moving normally
            prog = totalProgress() - progn;
            progn = totalProgress();
            if(prog < 0.01 && !state.isPaused()) prog = 0.22f;

            for(int i = 0; i < armCount; i++) {

                //randomly cause the assembly arms to stop & move forward
                if(rand.ints().findAny().getAsInt() % 200 / (i + 1) == 1 && arms.get(i).dir != 0 && warmup() != 0 && !state.isPaused()) {
                    arms.get(i).ndir = 0 - arms.get(i).dir;
                    arms.get(i).dir = 0;
                }

                //rotation
                if(arms.get(i).dir != 0) {
                    arms.get(i).r = (arms.get(i).r + prog * rotateSpeed * 5 * arms.get(i).dir * warmup()) % 360;
                    r = (arms.get(i).r + 360 / armCount * i) % 360;
                    arms.get(i).x = (float)(x + Mathf.cosDeg(r) * offset);
                    arms.get(i).y = (float)(y + Mathf.sinDeg(r) * offset);
                    Draw.rect(arms.get(i).tex, arms.get(i).x, arms.get(i).y, r + 90);
                }

                //movement forwards
                else {
                    arms.get(i).r2 += prog * 10 * warmup();
                    Draw.rect(arms.get(i).tex, (float)(arms.get(i).x - Mathf.cosDeg(arms.get(i).r + 360 / armCount * i) * Mathf.sinDeg(arms.get(i).r2) * move), (float)(arms.get(i).y - Mathf.sinDeg(arms.get(i).r + 360 / armCount * i) * Mathf.sinDeg(arms.get(i).r2) * move), arms.get(i).r + 360 / armCount * i + 90);
                    if(arms.get(i).r2 >= 180) {
                        arms.get(i).dir = arms.get(i).ndir;
                        arms.get(i).r2 = 0;
                    }
                }
            //if(!state.isPaused()) {Log.info("(" + x + ", " + y + ")" + i + " x=" + arms.get(i).x + " y=" + arms.get(i).y + " dir=" + arms.get(i).dir + " ndir=" + arms.get(i).ndir + " r=" + arms.get(i).r + " r2=" + arms.get(i).r2);
            //    Log.info("prog=" + prog + " progn=" + progn);
            //};
            }
            Draw.rect(region, x, y);
        }
    }

    public class AssembleArm {
        public float x = 0, y = 0, r = 0, r2 = 0;
        public int dir = 1, ndir = -1;
        public TextureRegion tex;
        public AssembleArm(){}
    }
}
