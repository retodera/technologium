// instead of adding a dependency, i took the whole MultiCrafter and changed it.
// credits to the creator of the mod, liplum
// 08.11.2025: the most recent update of MultiCrafter Lib was 8 months ago and it's still .gitignore.
// other things were last updated 1, 2 and 3 years ago. they abandoned the development.
package multicraft;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import multicraft.ui.*;

import static mindustry.Vars.*;

public class MultiCrafter extends PayloadBlock {
    public boolean hasHeat = false;
    public boolean hasPayloads = false;

    public float powerCapacity = 0f;
    /** maximum payloads this block can carry */
    public int payloadCapacity = 1;

    public float itemCapacityMultiplier = 1f;
    public float fluidCapacityMultiplier = 1f;
    public float powerCapacityMultiplier = 1f;
    public float payloadCapacityMultiplier = 2f;
    /*
    [ ==> Seq
      { ==> ObjectMap
        // String ==> Any --- Value may be a Seq, Map or String
        input:{
          // String ==> Any --- Value may be a Seq<String>, Seq<Map>, String or Map
          items:["mod-id-item/1","mod-id-item2/1"],
          fluids:["mod-id-liquid/10.5","mod-id-gas/10"]
          power: 3 pre tick
        },
        output:{
          items:["mod-id-item/1","mod-id-item2/1"],
          fluids:["mod-id-liquid/10.5","mod-id-gas/10"]
          heat: 10
        },
        craftTime: 120
      }
    ]
     */
    /** For Json and Javascript to configure. */
    public Object recipes;
    /** The resolved recipes. */
    @Nullable
    public Seq<Recipe> resolvedRecipes = null;
    /** For Json and Javascript to configure.*/
    public String menu = "transform";
    /** The resolved menu. */
    @Nullable
    public RecipeSwitchStyle switchStyle = null;
    public Effect craftEffect = Fx.none;
    public Effect updateEffect = Fx.none;
    public Effect changeRecipeEffect = Fx.rotateBlock;
    public int[] fluidOutputDirections = {-1};
    public float updateEffectChance = 0.04f;
    public float warmupSpeed = 1/60f;
    /**
     * Whether stop production when the fluid is full.
     * Turn off this to ignore fluid output, for instance, the fluid is only by-product.
     */
    public boolean ignoreLiquidFullness = false;
    
    /** If true, the crafter with multiple fluid outputs will dump excess, when there's still space for at least one fluid type. */
    public boolean dumpExtraFluid = true;
    public DrawBlock drawer = new DrawDefault();

    protected boolean outputsItems = false;
    protected boolean consumesItems = false;
    protected boolean outputsLiquids = false;
    protected boolean consumesLiquids = false;
    protected boolean outputsPower = false;
    protected boolean consumesPower = false;
    protected boolean outputsHeat = false;
    protected boolean consumesHeat = false;
    protected boolean outputsPayloads = false;
    protected boolean consumesPayloads = false;
    /** What color of heat for recipe selector. */
    public Color heatColor = new Color(1f, 0.22f, 0.22f, 0.8f);
    /**
     * For {@linkplain HeatConsumer},
     * it's used to display something of block or initialize the recipe index.
     */
    public int defaultRecipeId = 0;
    /**
     * For {@linkplain HeatConsumer},
     * after heat meets this requirement, excess heat will be scaled by this number.
     */
    public float overheatScale = 1f;
    /**
     * For {@linkplain HeatConsumer},
     * maximum possible efficiency after overheat. */
    public float maxEfficiency = 1f;
    /** * For {@linkplain HeatBlock} */
    public float warmupRate = 0.15f;
    /** Whether to show name tooltip in {@link MultiCrafterBuild#buildStats(Table)} */
    protected boolean showNameTooltip = false;

    /** craft effect except it is shown not only when it crafted */
    public Effect staticCraftEffect = Fx.none;
    /** static craft effect interval */
    public float staticCraftEffectInterval = 30f;
    /** by how much will efficiency be multiplied */
    public float optionalIntensity = 1f;
    /** whether the current recipe could be null / whether the id of the current recipe could be -1 */
    public boolean nullableRecipe = false;

    public MultiCrafter(String name) {
        super(name);
        update = true;
        solid = true;
        sync = true;
        flags = EnumSet.of(BlockFlag.factory);
        ambientSound = Sounds.loopMachine;
        configurable = true;
        saveConfig = true;
        ambientSoundVolume = 0.03f;
        config(Integer.class, MultiCrafterBuild::setRecipeRemote);
        configClear((MultiCrafterBuild b) -> b.recipeId = -1);
    }

    @Override
    public void init() {
        hasItems = false;
        hasLiquids = false;
        hasPower = false;
        hasHeat = false;
        hasPayloads = false;
        outputsPower = false;
        outputsPayload = false;

        final MultiCrafterParser parser = new MultiCrafterParser();
        // if the recipe is already set in another way, don't analyze it again.
        if (resolvedRecipes == null && recipes != null) resolvedRecipes = parser.parse(this, recipes);
        if (resolvedRecipes == null || resolvedRecipes.isEmpty())
            throw new ArcRuntimeException(MultiCrafterParser.genName(this) + ": hey bro i got no recipes. why? but if i do have them, they haven't got any items nor liquids. shame on you.");
        if (switchStyle == null) switchStyle = RecipeSwitchStyle.get(menu);
        decorateRecipes();
        setup();
        defaultRecipeId = Mathf.clamp(defaultRecipeId, 0, resolvedRecipes.size - 1);
        recipes = null; // free the recipe Seq, it's useless now.
        setupConsumers();
        super.init();
    }

    @Nullable
    protected static Table hoveredInfo;

    public class MultiCrafterBuild extends PayloadBlockBuild<Payload> implements HeatBlock, HeatConsumer {
        /** For {@linkplain HeatConsumer}, only enabled when the multicrafter requires heat input */
        public float[] sideHeat = new float[4];
        /**
         * For {@linkplain HeatConsumer} and {@linkplain HeatBlock},
         * only enabled when the multicrafter requires heat as input or can output heat.
         */
        public float heat = 0f;
        public float craftingTime;
        public float totalProgress;
        public float warmup;
        public int recipeId = defaultRecipeId;

        // added by Technologium
        public float sceTime;
        public float speed;

        public PayloadSeq payloads = new PayloadSeq();
        public @Nullable Vec2 commandPos;

        public void setRecipeRemote(int index) {
            int newIndex = Mathf.clamp(index, nullableRecipe ? -1 : 0, resolvedRecipes.size - 1);
            if (newIndex != recipeId) {
                recipeId = newIndex;
                createEffect(changeRecipeEffect);
                craftingTime = 0f;
                if (!Vars.headless) rebuildHoveredInfo();
            }
        }

        public Recipe curRecipe() {
            if(nullableRecipe && recipeId == -1) return null;
            return resolvedRecipes.get(Mathf.clamp(recipeId, 0, resolvedRecipes.size - 1));
        }

        public Seq<Item> allAvailableItems() {
            Seq<Item> allItems = new Seq<>();
            for(Recipe r : resolvedRecipes)
                for(Item i : r.input.itemsUnique)
                    allItems.add(i);
            return allItems;
        }

        public Seq<Liquid> allAvailableLiquids() {
            Seq<Liquid> allLiquids = new Seq<>();
            for(Recipe r : resolvedRecipes)
                for(Liquid l : r.input.fluidsUnique)
                    allLiquids.add(l);
            return allLiquids;
        }

        public Seq<UnlockableContent> allAvailablePayloads() {
            Seq<UnlockableContent> allPayloads = new Seq<>();
            for(Recipe r : resolvedRecipes)
                for(UnlockableContent c : r.input.payloadsUnique)
                    allPayloads.add(c);
            return allPayloads;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if(curRecipe() == null) return allAvailableItems().contains(item);
            return hasItems && 
                (curRecipe().input.itemsUnique.contains(item)
                //consume liquids from optional consumers, because liplum and others didn't think about them
                || consumesItem(item))
                && items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            if(curRecipe() == null) return allAvailableLiquids().contains(liquid);
            return hasLiquids &&
                (curRecipe().input.fluidsUnique.contains(liquid)
                //consume liquids from optional consumers, because liplum and others didn't think about them
                || consumesLiquid(liquid)) &&
                liquids.get(liquid) < liquidCapacity;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            if(curRecipe() == null) return allAvailablePayloads().contains(payload.content());
            return hasPayloads && this.payload == null &&
                curRecipe().input.payloadsUnique.contains(payload.content()) &&
                payloads.get(payload.content()) < payloadCapacity;
        }

        @Override
        public PayloadSeq getPayloads() {
            return payloads;
        }

        public void yeetPayload(Payload payload) {
            payloads.add(payload.content(), 1);
        }

        @Override
        public Vec2 getCommandPosition() {
            if(curRecipe() != null && curRecipe().outputsPayloads())
                return commandPos;
            else return null;
        }

        @Override
        public void onCommand(Vec2 target) {
            if (curRecipe() != null && curRecipe().outputsPayloads())
                commandPos = target;    
        }

        @Override
        public float edelta() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            if (cur.input.power > 0f) return Mathf.clamp(powerStored() / cur.input.power) * super.edelta(); //wow, they really didn't know about existense of super
            else return super.edelta();                                                                     //they just used efficiency * delta() when they could
        }                                                                                                   //still use the original method - super.delta()

        @Override
        public void updateTile() {
            Recipe cur = curRecipe();
            float timeRequired = 0;
            if(cur != null) {
                timeRequired = cur.craftTime;
                // As HeatConsumer
                if (cur.consumesHeat()) heat = calculateHeat(sideHeat);
                if (cur.outputsHeat()) 
                    heat = Mathf.approachDelta(heat, cur.output.heat * efficiency, warmupRate * edelta());
                // cool down
                if (efficiency > 0 && (!hasPower || powerStored() >= cur.input.power)) {
                    // if <= 0, instantly produced
                    if (timeRequired > 0f) craftingTime += edelta() * warmup;
                    speed = Mathf.lerp(1, optionalIntensity, optionalEfficiency) * efficiency;
                    warmup = Mathf.approachDelta(warmup, warmupTarget() * speed, warmupSpeed);
                    if (hasPower) {
                        float powerChange = (cur.output.power - cur.input.power) * delta();
                        if (!Mathf.zero(powerChange))
                            setPowerStored((powerStored() + powerChange));
                    }

                    //continuously output fluid based on efficiency
                    if (cur.outputsLiquids()) 
                        for (LiquidStack output : cur.output.fluids)                 //VVVVVVVVVVVVVV getProgressIncrease is not overriden, so i guess this was another way to call super.edelta()
                            handleLiquid(this, output.liquid, Math.min(output.amount * super.edelta(), liquidCapacity - liquids.get(output.liquid)));
                    // particle fx
                    if (wasVisible && Mathf.chanceDelta(updateEffectChance)) 
                        updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                    // added by Technologium
                    if(wasVisible && sceTime >= staticCraftEffectInterval) {
                        staticCraftEffect.at(x, y);
                        sceTime = 0;
                    }
                } else warmup = Mathf.approachDelta(warmup, 0, warmupSpeed);
            } else warmup = Mathf.approachDelta(warmup, 0, warmupSpeed);

            totalProgress += warmup * Time.delta;
            sceTime += warmup * Time.delta;
            
            if (moveInPayload()) {
                yeetPayload(payload);
                payload = null;
            }

            if (timeRequired <= 0 && efficiency > 0)
                craft();
            else if (craftingTime >= timeRequired)
                craft();

            updateBars();
            dumpOutputs();
        }

        public void updateBars() {
            barMap.clear();
            setBars();
        }

        @Override
        public boolean shouldConsume() {
            Recipe cur = curRecipe();
            if(cur == null) return false;
            if (hasItems) for (ItemStack output : cur.output.items)
                if (items.get(output.item) + output.amount > itemCapacity)
                    return false;

            if (hasLiquids && cur.outputsLiquids() && !ignoreLiquidFullness) {
                boolean allFull = true;
                for (LiquidStack output : cur.output.fluids)
                    if (liquids.get(output.liquid) >= liquidCapacity - 0.001f){
                        if (!dumpExtraFluid) return false;
                    }
                    else allFull = false; //if there's still space left, it's not full for all fluids
                //if there is no space left for any fluid, it can't reproduce
                if (allFull) return false;
            }
            if (hasPayloads) for (PayloadStack output : cur.output.payloads)
                if (payloads.get(output.item) + output.amount > payloadCapacity)
                    return false;
            return enabled;
        }

        public void craft() {
            consume();
            Recipe cur = curRecipe();
            if(cur == null) return;
            if (cur.outputsItems())
                for (ItemStack output : cur.output.items) for (int i = 0; i < output.amount; i++) offload(output.item);

            if (wasVisible) createEffect(curRecipe().craftEffect != Fx.none ? curRecipe().craftEffect : craftEffect);;
            if (cur.craftTime > 0f)
                craftingTime %= cur.craftTime;
            else
                craftingTime = 0f;
        }

        public void dumpOutputs() {
            Recipe cur = curRecipe();
            if(cur == null) return;
            if (cur.outputsItems())
                for (ItemStack output : cur.output.items) dump(output.item);

            if (cur.outputsPayloads()) {
                for (PayloadStack output : cur.output.payloads) {
                    Payload payloadOutput = null;
                    if (output.item instanceof Block)
                        payloadOutput = new BuildPayload((Block) output.item, this.team);
                    else if (output.item instanceof UnitType)
                        payloadOutput = new UnitPayload(((UnitType) output.item).create(this.team));
                    
                    if (payloadOutput != null)
                        dumpPayload(payloadOutput);
                }
            }

            if (cur.outputsLiquids()) {
                LiquidStack[] fluids = cur.output.fluids;
                for (int i = 0; i < fluids.length; i++) 
                    dumpLiquid(fluids[i].liquid, 2f, fluidOutputDirections.length > i ? fluidOutputDirections[i] : -1);
            }            
        }

        /** As {@linkplain HeatBlock }*/
        @Override
        public float heat() {
            return heat;
        }

        /** As {@linkplain HeatBlock} */
        @Override
        public float heatFrac() {
            Recipe cur = curRecipe();
            if(cur != null && outputsHeat && cur.outputsHeat()) return heat / cur.output.heat;
            else if (consumesHeat && cur.consumesHeat()) return heat / cur.input.heat;
            return 0f;
        }

        /** As {@linkplain HeatConsumer}
         * Only for visual effects */
        @Override
        public float[] sideHeat() {
            return sideHeat;
        }

        /** As {@linkplain HeatConsumer}
         * Only for visual effects */
        @Override
        public float heatRequirement() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            // When As HeatConsumer
            if (consumesHeat && cur.consumesHeat()) return cur.input.heat;
            return 0;
        }

        @Override
        public float calculateHeat(float[] sideHeat) {
            Point2[] edges = this.block.getEdges();
            int length = edges.length;
            for (int i = 0; i < length; ++i) {
                Point2 edge = edges[i];
                Building build = this.nearby(edge.x, edge.y);
                if (build != null && build.team == this.team && build instanceof HeatBlock) {
                    HeatBlock heater = (HeatBlock) build;
                    if (heater instanceof MultiCrafterBuild b && b.curRecipe() != null) {
                        if (b.curRecipe().outputsHeat()) return this.calculateHeat(sideHeat, (IntSet) null);
                    } else return this.calculateHeat(sideHeat, (IntSet) null);
                }
            }

            return 0;
        }

        @Override
        public float getPowerProduction() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            if (outputsPower && cur.outputsPower()) return cur.output.power * efficiency;
            else return 0f;
        }

        @Override
        public void buildConfiguration(Table table) {
            switchStyle.build(MultiCrafter.this, this, table);
        }

        public float powerStored() {
            if (power == null) return 0f;
            return power.status * powerCapacity;
        }

        public void setPowerStored(float powerStore) {
            if (power == null) return;
            power.status = Mathf.clamp(powerStore / powerCapacity);
        }

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public Object config() {
            return recipeId;
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0;
        }

        @Override
        public double sense(LAccess sensor) {
            if (sensor == LAccess.progress) return progress();
            if (sensor == LAccess.heat) return warmup();
            //attempt to prevent wild total fluid fluctuation, at least for crafter
            //if(sensor == LAccess.totalLiquids && outputLiquid != null) return liquids.get(outputLiquid.liquid);
            return super.sense(sensor);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(craftingTime);
            write.f(warmup);
            write.i(recipeId);
            write.f(heat);
            if(curRecipe() == null) return;
            if(curRecipe().consumesPayloads())
                payloads.write(write);
            if (curRecipe().outputsPayloads())
                TypeIO.writeVecNullable(write, commandPos);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            craftingTime = read.f();
            warmup = read.f();
            recipeId = Mathf.clamp(read.i(), 0, resolvedRecipes.size - 1);
            heat = read.f();
            if(curRecipe() == null) return;
            if(curRecipe().consumesPayloads())
                payloads.read(read);
            if (revision >= 1 && curRecipe().outputsPayloads())
                commandPos = TypeIO.readVecNullable(read);
        }

        public float warmupTarget() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            // When As HeatConsumer
            if (consumesHeat && cur.consumesHeat()) return Mathf.clamp(heat / cur.input.heat);
            else return 1f;
        }

        @Override
        public void updateEfficiencyMultiplier() {
            // changed by Technologium
            // it was not needed to put there a check for a heat consumer:
            //
            // Recipe cur = getCurRecipe();
            // // When As HeatConsumer
            // if (isConsumeHeat && cur.isConsumeHeat()) {
            //     efficiency *= efficiencyScale();
            //     potentialEfficiency *= efficiencyScale();
            // }
            //
            // the check already exists in efficiencyScale() method

            efficiency *= efficiencyScale();
            potentialEfficiency *= efficiencyScale();
        }

        public float efficiencyScale() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            float heatRequirement = cur.input.heat;
            float over = Math.max(heat - heatRequirement, 0f);
            return consumesHeat && cur.consumesHeat() ? Math.min(Mathf.clamp(heat / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency) : 1f;
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float progress() {
            Recipe cur = curRecipe();
            if(cur == null) return 0;
            return Mathf.clamp(cur.craftTime > 0f ? craftingTime / cur.craftTime : 1f);
        }

        @Override
        public float totalProgress(){
            return totalProgress;
        }

        @Override
        public void display(Table table) {
            super.display(table);
            hoveredInfo = table;
        }

        public void rebuildHoveredInfo() {
            try {
                Table info = hoveredInfo;
                if (Vars.ui.hudfrag.blockfrag.hover() == this) { // if(info != null)            won't work as intended.
                    info.clear();                                // if(Vars.ui.hudfrag.blockfrag.hover() == this) will.
                    display(info);
                }
            } catch (Exception ignored) {}
        }

        public void createEffect(Effect effect) {
            if (effect == Fx.none) return;
            if (effect == Fx.placeBlock) effect.at(x, y, block.size);
            else if (effect == Fx.coreBuildBlock) effect.at(x, y, 0f, block);
            else if (effect == Fx.upgradeCore) effect.at(x, y, 0f, block);
            else if (effect == Fx.upgradeCoreBloom) effect.at(x, y, block.size);
            else if (effect == Fx.rotateBlock) effect.at(x, y, block.size);
            else effect.at(x, y, 0, this);
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, t -> {
            showNameTooltip = true;
            buildStats(t);
            showNameTooltip = false;
        });
    }

    public void buildStats(Table stat) {
        stat.row();
        for (Recipe recipe : resolvedRecipes) {
            Table t = new Table();
            t.background(Tex.whiteui);
            t.setColor(Pal.darkestGray);
            // Input
            buildIOEntry(t, recipe, true);
            // Time
            Table time = new Table();
            final float[] duration = {0f};
            float visualCraftTime = recipe.craftTime;
            time.update(() -> {
                duration[0] += Time.delta;
                if (duration[0] > visualCraftTime) duration[0] = 0f;
            });
            String craftTime = recipe.craftTime == 0 ? "0" : String.format("%.2f", recipe.craftTime / 60f);
            Cell<Bar> barCell = time.add(new Bar(() -> craftTime,
                            () -> Pal.accent,
                            () -> Interp.smooth.apply(duration[0] / visualCraftTime)))
                    .height(45f);
            barCell.width(Vars.mobile ? 220f : 250f);
            Cell<Table> timeCell = t.add(time).pad(12f);
            if (showNameTooltip)
                timeCell.tooltip(Stat.productionTime.localized() + ": " + craftTime + " " + StatUnit.seconds.localized());
            // Output
            buildIOEntry(t, recipe, false);
            stat.add(t).pad(10f).grow();
            stat.row();
        }
        stat.row();
        stat.defaults().grow();
    }

    protected void buildIOEntry(Table table, Recipe recipe, boolean isInput) {
        Table t = new Table();
        if (isInput) t.left();
        else t.right();
        Table mat = new Table();
        IOEntry entry = isInput ? recipe.input : recipe.output;
        int i = 0;
        for (ItemStack stack : entry.items) {
            Cell<TItemImage> iconCell = mat.add(new TItemImage(stack.item.uiIcon, stack.amount))
                    .pad(2f);
            if (isInput) iconCell.left();
            else iconCell.right();
            if (showNameTooltip)
                iconCell.tooltip(stack.item.localizedName);
            if (i != 0 && i % 2 == 0) mat.row();
            i++;
        }
        for (LiquidStack stack : entry.fluids) {
            Cell<FluidImage> iconCell = mat.add(new FluidImage(stack.liquid.uiIcon, stack.amount * 60f))
                    .pad(2f);
            if (isInput) iconCell.left();
            else iconCell.right();
            if (showNameTooltip)
                iconCell.tooltip(stack.liquid.localizedName);
            if (i != 0 && i % 2 == 0) mat.row();
            i++;
        }
        // No redundant ui
        // Power
        if (entry.power > 0f) {
            Cell<PowerImage> iconCell = mat.add(new PowerImage(entry.power * 60f))
                    .pad(2f);
            if (isInput) iconCell.left();
            else iconCell.right();
            if (showNameTooltip)
                iconCell.tooltip(entry.power + " " + StatUnit.powerSecond.localized());
            i++;
            if (i != 0 && i % 2 == 0) mat.row();
        }
        //Heat
        if (entry.heat > 0f) {
            Cell<HeatImage> iconCell = mat.add(new HeatImage(entry.heat))
                    .pad(2f);
            if (isInput) iconCell.left();
            else iconCell.right();
            if (showNameTooltip)
                iconCell.tooltip(entry.heat + " " + StatUnit.heatUnits.localized());
            i++;
            if (i != 0 && i % 2 == 0) mat.row();
        }
        for (PayloadStack stack : entry.payloads) {
            Cell<PayloadImage> iconCell = mat.add(new PayloadImage(stack.item.uiIcon, stack.amount))
                    .pad(2f);
            if (showNameTooltip)
                iconCell.tooltip(stack.item.localizedName);
            if (isInput) iconCell.left();
            else iconCell.right();
            if (i != 0 && i % 2 == 0) mat.row();
            i++;
        }
        Cell<Table> matCell = t.add(mat);
        if (isInput) matCell.left();
        else matCell.right();
        Cell<Table> tCell = table.add(t).pad(12f).fill();
        tCell.width(Vars.mobile ? 90f : 120f);
    }

    @Override
    public void setBars() {
        super.setBars();

        if (hasPower)
            addBar("power", (MultiCrafterBuild b) -> new Bar(
                    b.curRecipe() != null && b.curRecipe().outputsPower() ? Core.bundle.format("bar.poweroutput", Strings.fixed(b.getPowerProduction() * 60f * b.timeScale(), 1)) : "bar.power",
                    Pal.powerBar,
                    () -> b.efficiency
            ));
        if (consumesHeat || outputsHeat)
            addBar("heat", (MultiCrafterBuild b) -> new Bar(
                    b.curRecipe() != null && b.curRecipe().consumesHeat() ? Core.bundle.format("bar.heatpercent", (int) (b.heat + 0.01f), (int) (b.efficiencyScale() * 100 + 0.01f)) : "bar.heat",
                    Pal.lightOrange,
                    b::heatFrac
            ));
        addBar("progress", (MultiCrafterBuild b) -> new Bar(
                "bar.loadprogress",
                Pal.accent,
                b::progress
        ));
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out) {
        drawer.getRegionsToOutline(this, out);
    }

    @Override
    public boolean outputsItems() {
        return outputsItems;
    }

    @Override
    public void drawOverlay(float x, float y, int rotation) {
        Recipe firstRecipe = resolvedRecipes.get(defaultRecipeId);
        LiquidStack[] fluids = firstRecipe.output.fluids;
        for (int i = 0; i < fluids.length; i++) {
            int dir = fluidOutputDirections.length > i ? fluidOutputDirections[i] : -1;

            if (dir != -1) Draw.rect(
                    fluids[i].liquid.fullIcon,
                    x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                    y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                    8f, 8f
            );
        }
    }

    protected void decorateRecipes() {
        resolvedRecipes.shrink();
        for (Recipe recipe : resolvedRecipes)
            recipe.cacheUnique();
    }

    protected void setup() {
        int maxItemAmount = 0;
        float maxFluidAmount = 0f;
        float maxPower = 0f;
        float maxHeat = 0f;
        int maxPayloadAmount = 0;

        for (Recipe recipe : resolvedRecipes) {
            hasItems |= recipe.hasItems();
            hasLiquids |= recipe.hasLiquids();
            conductivePower = hasPower |= recipe.hasPower();
            hasHeat |= recipe.hasHeat();
            hasPayloads |= recipe.hasPayloads();

            maxItemAmount = Math.max(recipe.maxItemAmount(), maxItemAmount);
            maxFluidAmount = Math.max(recipe.maxFluidAmount(), maxFluidAmount);
            maxPower = Math.max(recipe.maxPower(), maxPower);
            maxHeat = Math.max(recipe.maxHeat(), maxHeat);
            maxPayloadAmount = Math.max(recipe.maxPayloadAmount(), maxPayloadAmount);

                             outputsItems |= recipe.outputsItems();
            acceptsItems =   consumesItems |= recipe.consumesItems();
            outputsLiquid =  outputsLiquids |= recipe.outputsLiquids();
                             consumesLiquids |= recipe.consumesLiquids();
            outputsPower =   outputsPower |= recipe.outputsPower();
            consumesPower =  consumesPower |= recipe.consumesPower();
                             outputsHeat |= recipe.outputsHeat();
                             consumesHeat |= recipe.consumesHeat();
            outputsPayload = outputsPayloads |= recipe.outputsPayloads();
            acceptsPayload = consumesPayloads |= recipe.consumesPayloads();
        }

        itemCapacity = Math.max((int) (maxItemAmount * itemCapacityMultiplier), itemCapacity);
        liquidCapacity = Math.max((int) (maxFluidAmount * 60f * fluidCapacityMultiplier), liquidCapacity);
        powerCapacity = Math.max(maxPower * 60f * powerCapacityMultiplier, powerCapacity);
        payloadCapacity = Math.max((int) (maxPayloadAmount * payloadCapacityMultiplier), payloadCapacity);
        if (outputsHeat) {
            rotate = true;
            rotateDraw = false;
            canOverdrive = false;
            drawArrow = true;
        }
    }

    protected void setupConsumers() {
        if (consumesItems) consume(new ConsumeItemDynamic(
            (MultiCrafterBuild b) -> b.curRecipe() == null ? ItemStack.empty : b.curRecipe().input.items
        ));
        if (consumesLiquids) consume(new ConsumeFluidDynamic(
            (MultiCrafterBuild b) -> b.curRecipe() == null ? LiquidStack.empty : b.curRecipe().input.fluids
        ));
        if (consumesPower) consume(new ConsumePowerDynamic(
            b -> ((MultiCrafterBuild)b).curRecipe() == null ? 0 :((MultiCrafterBuild)b).curRecipe().input.power
        ));
        if (consumesPayloads) consume(new CustomConsumePayloadDynamic(
            (MultiCrafterBuild b) -> b.curRecipe() == null ? new PayloadStack[]{} : b.curRecipe().input.payloads
        ));
    }
}
