//fun fact: the mod was originally made on .hjson, but then i decided to add one block type and now i make the mod in java.

package technologium.content;

//yea, there are alot of unused imports, but soon they will be used
import arc.graphics.*;
import arc.struct.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import technologium.graphics.TPal;
import technologium.world.*;
import technologium.world.draw.*;
import technologium.world.blocks.liquid.*;
import technologium.world.blocks.logic.*;
import technologium.world.blocks.storage.*;
import technologium.world.blocks.production.*;
import multicraft.*;

import static mindustry.Vars.*;
import static technologium.TVars.*;
import static mindustry.type.ItemStack.*;

public class TBlocks {

    public static Block

    //environment - floor
    volcanicStone, thermalStone, acidFloor, neoplasticFloor, neoplasticLiquid, hydrochloricAcidLiquid, pegmatiteStone,
    mitaStone,

    //environment - ore
    hematiteOre, tinWallOre, bauxiteOre,

    //environment - wall
    volcanicWall, acidWall, neoplasticWall, neoplasticTree, pegmatiteWall,
    mitaWall, mitaHide,

    //turrets - kudol
    spite,

    //production - kudol
    darkPlasmaBore, miniPlasmaBore, darkDrill, metallicDrill, goldExtractor,

    //distribution - kudol
    darkConveyor, darkJunction, darkRouter, darkDistributor, darkBridgeConveyor, plasmaDriver,

    //liquds - kudol
    improvedConduit, thermoConduit, improvedLiquidJunction, improvedLiquidRouter, improvedBridgeConduit,
    improvedLiquidContainer,

    //power - kudol
    thermalPlate, energeticNode, energeticNodeLarge, lithiumBattery,

    //crafting - kudol
    arcFurnace, arcSmelter, atmosphericCondenser, trainingCenter, acidElectrolyzer, constructor, enricher,

    //defense - kudol
    darkWall, darkWallLarge,

    //units - kudol
    unitFabricator,

    //effect - kudol
    coreTorch, coreBlaze, darkUnloader, darkContainer, darkVault, miniMender, miniShieldProjector, buildTurret, radar,

    //logic - kudol
    switchBlock, message, energeticProcessor, plasmaProcessor, cell,
    bank, miniDisplay, display, stringMemoryBlock
    ;

    public static final Attribute goldA = Attribute.add("goldA"), neoplasmA = Attribute.add("neoplasmA");

    public static void load() {

        //region environment - wall

        volcanicWall = new StaticWall("volcanic-wall") {{
            variants = 3;
        }};

        acidWall = new StaticWall("acid-wall") {{
            variants = 3;
        }};

        neoplasticWall = new StaticWall("neoplastic-wall") {{
            variants = 3;
        }};

        pegmatiteWall = new StaticWall("pegmatite-wall") {{
            variants = 3;
        }};

        neoplasticTree = new TreeBlock("neoplastic-tree");

        mitaWall = new StaticWall("mita-wall") {{
            variants = 3; 
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
        }};

        mitaHide = new StaticWall("mita-hide") {{
            variants = 1;
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
        }};

        //region environment - floor

        volcanicStone = new Floor("volcanic-stone", 4) {{
            attributes.set(goldA, 0.25f);
            wall = volcanicWall;
        }};

        thermalStone = new Floor("thermal-stone", 4) {{
            attributes.set(Attribute.heat, 1f);
            blendGroup = volcanicStone;
            wall = volcanicWall;
        }};

        acidFloor = new Floor("acid-floor", 3) {{
            wall = acidWall;
        }};

        neoplasticFloor = new Floor("neoplastic-floor", 3) {{
            attributes.set(neoplasmA, 0.25f);
            wall = neoplasticWall;
        }};

        neoplasticLiquid = new Floor("neoplastic-liquid") {{
            isLiquid = true;
            liquidDrop = Liquids.neoplasm;
            cacheLayer = CacheLayer.water;
            drownTime = 200f;
            albedo = 0.9f;
            speedMultiplier = 0.2f;
            variants = 0;
            supportsOverlay = false;
            shallow = false;
            status = TStatusEffects.neoplasmCovered;
            statusDuration = 720f;
        }};

        hydrochloricAcidLiquid = new Floor("hydrochloric-acid-liquid") {{
            isLiquid = true;
            liquidDrop = TLiquids.hydrochloricAcid;
            cacheLayer = CacheLayer.water;
            drownTime = 200f;
            albedo = 0.9f;
            speedMultiplier = 0.2f;
            variants = 0;
            supportsOverlay = false;
            shallow = false;
            status = TStatusEffects.corrosion;
            statusDuration = 360f;
        }};

        pegmatiteStone = new Floor("pegmatite-stone", 4) {{
            itemDrop = TItems.pegmatite;
            playerUnmineable = true;
            wall = pegmatiteWall;
        }};

        mitaStone = new Floor("mita-stone", 3) {{
            wall = mitaWall;
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
        }};

        //region environment - ore

        hematiteOre = new OreBlock("hematite-ore", TItems.hematite);

        tinWallOre = new OreBlock("tin-wall-ore", TItems.tin) {{
            wallOre = true;
        }};

        bauxiteOre = new OreBlock("bauxite-ore", TItems.bauxite);

        //region turrets - kudol

        spite = new PowerTurret("spite") {{
            requirements(Category.turret, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 5));
            envEnabled |= Env.space;
            range = 80f;
            health = 180;
            size = 1;
            recoil = 2f;
            reload = 60f;
            consumePower(0.25f);
            shootSound = Sounds.blaster;
            drawer = new DrawTurret("kudol-");
            shootType = new LaserBulletType(12) {{
                buildingDamageMultiplier = 0.25f;
                length = 80f;
                colors = new Color[] { Color.valueOf("05f5e9"), Color.valueOf("00fff2"), Color.white };
                ammoMultiplier = 1f;
            }};
        }};

        //region production - kudol

        darkPlasmaBore = new BeamDrill("dark-plasma-bore") {{
            requirements(Category.production, with(TItems.hematite, 25, TItems.tin, 10));
            consumePower(12 / 60f);
            health = 120;
            drillTime = 400f;
            size = 2;
            tier = 1;
            range = 6;
            fogRadius = 3;
            researchCost = with(TItems.hematite, 10);
            optionalBoostIntensity = 1f;
        }};

        miniPlasmaBore = new BeamDrill("mini-plasma-bore") {{
            requirements(Category.production, with(TItems.darkMetal, 10, TItems.tin, 10));
            consumePower(8 / 60f);
            health = 80;
            drillTime = 300f;
            size = 1;
            tier = 1;
            range = 6;
            fogRadius = 2;
            optionalBoostIntensity = 1f;
        }};

        darkDrill = new Drill("dark-drill") {{
            requirements(Category.production, with(TItems.hematite, 20, TItems.tin, 15));
            consumePower(5 / 60f);
            health = 110;
            tier = 1;
            drillTime = 720f;
            size = 1;
            liquidBoostIntensity = 1f;
        }};

        metallicDrill = new Drill("metallic-drill") {{
            requirements(Category.production, with(TItems.darkMetal, 25, TItems.tin, 15, TItems.lithium, 10));
            consumePower(35 / 60f);
            health = 240;
            tier = 2;
            drillTime = 440f;
            size = 2;
            liquidBoostIntensity = 1f;
        }};

        goldExtractor = new TAttributeCrafter("gold-extractor") {{
            requirements(Category.production, with(TItems.darkMetal, 30, TItems.aluminium, 50, TItems.lithium, 20));
            consumePower(1f);
            consumeLiquid(Liquids.water, 5f / 60f).boost();
            health = 320;
            craftTime = 300f;
            size = 2;
            hasLiquids = true;
            hasPower = true;
            hasItems = true;
            itemCapacity = 20;
            liquidCapacity = 200;
            attribute = goldA;
            minEfficiency = 0.01f;
            optionalBoostIntensity = maxBoost = 2.5f;
            outputItem = new ItemStack(TItems.gold, 1);
            baseEfficiency = 0;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(Liquids.water),
                new DrawRegion("-rotator", 1, true),
                new DrawDefault()
            );
        }};

        //region distribution - kudol

        darkConveyor = new Conveyor("dark-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 1));
            health = 40;
            speed = 0.075f;
            displayedSpeed = 10.4f;
            junctionReplacement = darkJunction;
            bridgeReplacement = darkBridgeConveyor;
            researchCostMultiplier = 5f;
        }};

        darkJunction = new Junction("dark-junction") {{
            requirements(Category.distribution, with(TItems.hematite, 2));
            health = 80;
            speed = 13;
            capacity = 8;
            researchCostMultiplier = 5f;
        }};

        darkRouter = new Router("dark-router") {{
            requirements(Category.distribution, with(TItems.hematite, 3));
            health = 120;
            speed = 14f;
            researchCostMultiplier = 5f;
        }};

        darkDistributor = new Router("dark-distributor") {{
            requirements(Category.distribution, with(TItems.hematite, 12));
            health = 240;
            speed = 14f;
            size = 2;
            researchCostMultiplier = 5f;
        }};

        darkBridgeConveyor = new BufferedItemBridge("dark-bridge-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 12));
            health = 180;
            speed = 60f;
            range = 5;
            researchCostMultiplier = 5f;
        }};

        plasmaDriver = new MassDriver("plasma-driver") {{
            requirements(Category.distribution, with(TItems.darkMetal, 50, TItems.aluminium, 75, TItems.lithium, 40));
            health = 350;
            size = 2;
            itemCapacity = 60;
            reload = 100f;
            range = 500f;
            consumePower(1.5f);
            outlineColor = TPal.darkerOutline;
        }};

        //region liquid - kudol

        improvedConduit = new TempConduit("improved-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 2, TItems.goldGlass, 2));
            health = 60;
        }};

        thermoConduit = new TempConduit("thermo-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.goldGlass, 4, TItems.stalinium, 2));
            health = 160;
            maxTemp = 5f;
        }};

        improvedLiquidJunction = new TempLiquidJunction("improved-liquid-junction") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.goldGlass, 4));
            health = 90;
            solid = false;
            placeableLiquid = true;
        }};

        improvedLiquidRouter = new TempLiquidRouter("improved-liquid-router") {{
            requirements(Category.liquid, with(TItems.darkMetal, 6, TItems.goldGlass, 6));
            health = 120;
            liquidCapacity = 30f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        improvedBridgeConduit = new TempLiquidBridge("improved-liquid-bridge") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.goldGlass, 20));
            health = 140;
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
            placeableLiquid = true;
        }};

        improvedLiquidContainer = new TempLiquidRouter("improved-liquid-container") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.goldGlass, 12));
            health = 540;
            liquidCapacity = 800f;
            placeableLiquid = true;
            size = 2;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};

        //region power - kudol

        thermalPlate = new ThermalGenerator("thermal-plate") {{
            requirements(Category.power, with(TItems.hematite, 10, TItems.tin, 10));
            powerProduction = 0.15f;
            floating = true;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawGlowRegion()
            );
        }};

        energeticNode = new PowerNode("energetic-node") {{
            requirements(Category.power, with(TItems.hematite, 5, TItems.tin, 5));
            maxNodes = 5;
            laserRange = 10;
            consumePowerBuffered(800f);
            squareSprite = false;
        }};

        energeticNodeLarge = new PowerNode("energetic-node-large") {{
            requirements(Category.power, with(TItems.darkMetal, 20, TItems.lithium, 12, TItems.tin, 10));
            size = 2;
            maxNodes = 15;
            laserRange = 25;
            consumePowerBuffered(800f);
            squareSprite = false;
        }};

        lithiumBattery = new Battery("lithium-battery") {{
            requirements(Category.power, with(TItems.hematite, 35, TItems.lithium, 10));
            consumePowerBuffered(2000f);
            baseExplosiveness = 3;
        }};

        //region crafting - kudol

        arcFurnace = new MultiCrafter("arc-furnace") {{
            requirements(Category.crafting, with(TItems.hematite, 50, TItems.tin, 40, TItems.lithium, 25));
            health = 140;
            size = 2;
            itemCapacity = 10;
            squareSprite = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDefault(),
                new DrawCrucibleFlame()
            );
            resolvedRecipes = Seq.with(
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.hematite, 5)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.darkMetal, 2)),
                        Seq.with()
                    ),
                    120f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.bauxite, 5)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.aluminium, 2)),
                        Seq.with()
                    ),
                    120f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.pegmatite, 5)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.lithium, 1)),
                        Seq.with()
                    ),
                    120f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.enrichedMetal, 2)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.darkMetal, 1)),
                        Seq.with()
                    ),
                    120f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.enrichedAluminium, 2)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.aluminium, 1)),
                        Seq.with()
                    ),
                    120f
                )
            );
        }};

        constructor = new TConstructor("constructor") {{
            requirements(Category.crafting, with(TItems.darkMetal, 50, TItems.tin, 30, TItems.lithium, 15));
            size = 4;
            itemCapacity = 30;
            squareSprite = false;
            rotate = false;
            resolvedRecipes = Seq.with(
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.darkMetal, 2, TItems.aluminium, 2)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.cog, 8)),
                        Seq.with()
                    ),
                    120f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.darkMetal, 15, TItems.aluminium, 8)),
                        Seq.with(),
                        0.5f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.armorPlate, 2)),
                        Seq.with()
                    ),
                    120f
                )
            );
        }};

        enricher = new MultiCrafter("enricher") {{
            requirements(Category.crafting, with(TItems.darkMetal, 80, TItems.tin, 40, TItems.lithium, 15));
            size = 3;
            itemCapacity = 10;
            squareSprite = false;
            rotate = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRecipe() {{
                    drawers = new DrawBlock[] {
                        new DrawRegionColored("-powder", 1f, false, TItems.hematite),
                        new DrawRegionColored("-powder", 1f, false, TItems.bauxite),
                        new DrawRegionColored("-powder", 1f, false, TItems.pegmatite)
                    };
                }},
                new DrawRegion("-rotator", 1f, false),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.hematite, 1)),
                        Seq.with(),
                        40/60f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.enrichedMetal, 1)),
                        Seq.with()
                    ),
                    240f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.bauxite, 1)),
                        Seq.with(),
                        40/60f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.enrichedAluminium, 1)),
                        Seq.with()
                    ),
                    240f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.pegmatite, 2)),
                        Seq.with(),
                        40/60f
                    ),
                    new IOEntry(
                        Seq.with(ItemStack.with(TItems.lithium, 1)),
                        Seq.with()
                    ),
                    240f
                )  
            );
        }};

        //region defense - kudol

        darkWall = new Wall("dark-wall") {{
            requirements(Category.defense, with(TItems.hematite, 4, TItems.tin, 4));
            health = 360;
            researchCostMultiplier = 0.2f;
        }};

        darkWallLarge = new Wall("dark-wall-large") {{
            requirements(Category.defense, with(TItems.hematite, 16, TItems.tin, 16));
            health = 1440;
            size = 2;
        }};

        //region units - kudol

        unitFabricator = new UnitFactory("unit-fabricator") {{
            requirements(Category.units, with(TItems.darkMetal, 100, TItems.tin, 40, TItems.lithium, 25));
            size = 2;
            health = 260;
            consumePower(3f);
            plans = Seq.with(
                new UnitPlan(TUnitTypes.cobra, 900f, with(TItems.darkMetal, 30, TItems.tin, 15, TItems.bioprocessor, 1)),
                new UnitPlan(TUnitTypes.blade, 1200f, with(TItems.darkMetal, 50, TItems.aluminium, 35, TItems.lithium, 25, TItems.accumulator, 4, TItems.bioprocessor, 1))
            );
        }};
        //region effect - kudol

        coreTorch = new TCoreBlock("core-torch") {{
            requirements(Category.effect, with(TItems.darkMetal, 1200, TItems.tin, 900, TItems.gold, 200));
            size = 2;
            isFirstTier = true;
            health = 2000;
            itemCapacity = 3000;
            unitType = TUnitTypes.quant;
            squareSprite = false;
        }};

        coreBlaze = new TCoreBlock("core-blaze") {{
            requirements(Category.effect, with(TItems.darkMetal, 3000, TItems.tin, 2000, TItems.aluminium, 1600, TItems.gold, 800));
            size = 3;
            health = 4500;
            itemCapacity = 6000;
            unitType = TUnitTypes.quant;
            squareSprite = false;
        }};

        darkContainer = new TStorageBlock("dark-container") {{
            requirements(Category.effect, with(TItems.darkMetal, 100, TItems.tin, 30, TItems.aluminium, 20));
            size = 2;
            scaledHealth = 80;
            itemCapacity = 400;
            squareSprite = false;
        }};

        darkVault = new TStorageBlock("dark-vault") {{
            requirements(Category.effect, with(TItems.darkMetal, 400, TItems.tin, 150, TItems.aluminium, 80, TItems.gold, 60));
            size = 3;
            scaledHealth = 90;
            itemCapacity = 1500;
            squareSprite = false;
        }};

        darkUnloader = new Unloader("dark-unloader") {{
            requirements(Category.effect, with(TItems.darkMetal, 20, TItems.tin, 5, TItems.aluminium, 10));
            speed = 60 / 15f;
        }};
        
        radar = new Radar("radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(TItems.darkMetal, 40, TItems.tin, 32, TItems.lithium, 25));
            size = 1;
            health = 120;
            consumePower(1f);
            fogRadius = 26;
            outlineColor = TPal.darkerOutline;
        }};

        //region logic

        switchBlock = new SwitchBlock("switch") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 5));
        }};

        message = new MessageBlock("message") {{
            requirements(Category.logic, with(TItems.darkMetal, 15, TItems.tin, 10));
            maxTextLength = 600;
            maxNewlines = 200;
        }};

        cell = new MemoryBlock("cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 20, TItems.tin, 15, TItems.memoryCard, 4));
            memoryCapacity = 256;
        }};

        bank = new MemoryBlock("bank") {{
            requirements(Category.logic, with(TItems.darkMetal, 100, TItems.tin, 70, TItems.lithium, 45, TItems.memoryCard, 16));
            size = 2;
            memoryCapacity = 2048;
        }};

        energeticProcessor = new LogicBlock("energetic-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 15, TItems.bioprocessor, 1));
            instructionsPerTick = 10;
            size = 1;
            squareSprite = false;
            range = 16 * 8;
        }};

        plasmaProcessor = new LogicBlock("plasma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 120, TItems.aluminium, 80, TItems.lithium, 60, TItems.bioprocessor, 5));
            instructionsPerTick = 24;
            size = 2;
            squareSprite = false;
            range = 48 * 8;
        }};

        display = new BorderlessDisplay("display") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 10, TItems.bioprocessor, 1));
            size = 4;
            displaySize = 200;
        }};

        miniDisplay = new BorderlessDisplay("mini-display") {{
            requirements(Category.logic, with(TItems.darkMetal, 20, TItems.tin, 15, TItems.lithium, 5, TItems.bioprocessor, 1));
            size = 2;
            displaySize = 100;
        }};

        stringMemoryBlock = new StringMemoryBlock("string-memory-block") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 20, TItems.memoryCard, 32));
            memoryCapacity = 64;
        }};
    }
}