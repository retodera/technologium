//fun fact: the mod was originally made on .hjson, but then i decided to add one block type and now i make the mod in java. btw VSCodium is the best coding app as in my opinion

package technologium.content;

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
import technologium.world.*;
import technologium.world.blocks.*;
import multicraft.*;

import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;

public class TBlocks {

    public static Block

    // environment - floor
    volcanicStone, thermalStone, acidFloor, neoplasticFloor, neoplasticLiquid, hydrochloricAcidLiquid,

    // environment - ore
    hematiteOre, tinWallOre, bauxiteOre,

    // environment - wall
    volcanicWall, acidWall, neoplasticWall, neoplasticTree,

    // turrets - kudol
    spite,

    // production - kudol
    darkPlasmaBore, miniPlasmaBore, darkDrill, goldExtractor,

    // distribution - kudol
    darkConveyor, darkJunction, darkRouter, darkDistributor, darkBridgeConveyor, plasmaDriver,

    // liquds - kudol
    improvedConduit, thermoConduit, improvedLiquidJunction, improvedLiquidRouter, improvedBridgeConduit,
    improvedLiquidContainer,

    // power - kudol
    thermalPlate, darkPowerNode, darkPowerNodeLarge, lithiumBattery,

    // crafting - kudol
    arcFurnace, arcMelter, atmosphericConcentrator, trainingCenter, acidElectrolyzer, constructor,

    // defense - kudol
    darkWall, darkWallLarge,

    // units - kudol
    unitFabricator,

    // effect - kudol
    coreTorch, coreBlaze, darkUnloader, darkContainer, darkVault, miniMender, miniShieldProjector, buildTurret,

    // logic - kudol
    switchBlock, message, energeticProcessor, plasmaProcessor, cell,
    bank, miniDisplay, display, stringMemoryBlock
    ;

    public static final Attribute goldA = Attribute.add("goldA"),
            neoplasmA = Attribute.add("neoplasmA");

    /* now i'll tell you the story of me creating this
 most of this is copied from other mods, but mostly from the mindustry itself
 (as if i didn't do that while the mod was on .hjson)
 i knew some basics of javascript
 btw i always thought that js and java are the same thing. i was horribly wrong. */

    public static void load() {

        // region environment - floor
        volcanicStone = new Floor("volcanic-stone", 3) {{
            attributes.set(goldA, 0.25f);
        }};

        thermalStone = new Floor("thermal-stone", 3) {{
            attributes.set(Attribute.heat, 1f);
        }};

        acidFloor = new Floor("acid-floor", 3);

        neoplasticFloor = new Floor("neoplastic-floor") {{
            attributes.set(neoplasmA, 0.25f);
        }};

        neoplasticTree = new TreeBlock("neoplastic-tree");

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
            placeableOn = false;
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
            placeableOn = false;
            status = TStatusEffects.corrosion;
            statusDuration = 360f;
        }};

        // endregion
        // region environment - ore

        hematiteOre = new OreBlock("hematite-ore", TItems.hematite);

        tinWallOre = new OreBlock("tin-wall-ore", TItems.tin) {{
            wallOre = true;
        }};

        bauxiteOre = new OreBlock("bauxite-ore", TItems.bauxite);

        // endregion
        // region environment - wall

        volcanicWall = new StaticWall("volcanic-wall");

        acidWall = new StaticWall("acid-wall");

        neoplasticWall = new StaticWall("neoplastic-wall");

        // endregion
        // region turrets - kudol

        spite = new PowerTurret("spite") {{
            requirements(Category.turret, with(TItems.hematite, 110, TItems.tin, 80, TItems.lithium, 5));
            envEnabled |= Env.space;
            range = 80f;
            health = 180;
            size = 1;
            recoil = 2f;
            reload = 8f;
            consumePower(0.25f);
            shootSound = Sounds.blaster;
            shoot = new ShootBarrel() {{
                barrels = new float[] {
                    -2.5f, 0f, 0f,
                    -1f, 0f, 0f,
                    1f, 0f, 0f,
                    2.5f, 0f, 0f,
                };}};
            drawer = new DrawTurret("kudol-");
            shootType = new LaserBulletType(12) {{
                buildingDamageMultiplier = 0.25f;
                length = 80f;
                colors = new Color[] { Color.valueOf("05f5e9"), Color.valueOf("00fff2"), Color.white };
                ammoMultiplier = 1f;
            }};
        }};

        // endregion
        // region production - kudol

        darkPlasmaBore = new BeamDrill("dark-plasma-bore") {{
            requirements(Category.production, with(TItems.hematite, 25, TItems.tin, 10));
            consumePower(0.2f);
            health = 120;
            drillTime = 400f;
            size = 2;
            tier = 1;
            range = 6;
            fogRadius = 3;
            researchCost = with(TItems.hematite, 10);
        }};

        miniPlasmaBore = new BeamDrill("mini-plasma-bore") {{
            requirements(Category.production, with(TItems.darkMetal, 10, TItems.tin, 10));
            consumePower(0.1f);
            health = 80;
            drillTime = 300f;
            size = 1;
            tier = 1;
            range = 6;
            fogRadius = 2;
        }};

        darkDrill = new Drill("dark-drill") {{
            requirements(Category.production, with(TItems.hematite, 20, TItems.tin, 15));
            consumePower(0.25f);
            health = 110;
            tier = 1;
            drillTime = 720f;
            size = 1;
        }};

        goldExtractor = new AttributeCrafter("gold-extractor") {{
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
            maxBoost = 3f;
            attribute = goldA;
            outputItem = new ItemStack(TItems.gold, 1);
            baseEfficiency = 0;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(Liquids.water),
                new DrawRegion("-rotator") {{
                    spinSprite = true;
                    rotateSpeed = 1;
                }},
                new DrawDefault()
                );
        }};

        // endregion
        // region distribution - kudol

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
        }};

        // endregion
        // region liquid - kudol

        improvedConduit = new TempConduit("improved-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 2, TItems.goldGlass, 2));
            health = 60;
        }};

        thermoConduit = new TempConduit("thermo-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.goldGlass, 4, TItems.stalinium, 2));
            health = 160;
            maxTemp = 5f;
        }};

        improvedLiquidJunction = new LiquidJunction("improved-liquid-junction") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.goldGlass, 4));
            health = 90;
            solid = false;
            placeableLiquid = true;
        }};

        improvedLiquidRouter = new LiquidRouter("improved-liquid-router") {{
            requirements(Category.liquid, with(TItems.darkMetal, 6, TItems.goldGlass, 6));
            liquidCapacity = 30f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        improvedBridgeConduit = new LiquidBridge("improved-liquid-bridge") {{
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
            placeableLiquid = true;
        }};

        improvedLiquidContainer = new LiquidRouter("improved-liquid-container") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.goldGlass, 12));
            liquidCapacity = 400f;
            placeableLiquid = true;
            size = 2;
            solid = true;
        }};

        // endregion
        // region power - kudol

        thermalPlate = new ThermalGenerator("thermal-plate") {{
            requirements(Category.power, with(TItems.hematite, 10, TItems.tin, 10));
            powerProduction = 0.15f;
            floating = true;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};

        darkPowerNode = new PowerNode("dark-power-node") {{
            requirements(Category.power, with(TItems.hematite, 5, TItems.tin, 5));
            maxNodes = 5;
            laserRange = 10;
            consumePowerBuffered(800f);
        }};

        darkPowerNodeLarge = new PowerNode("dark-power-node-large") {{
            requirements(Category.power, with(TItems.hematite, 5, TItems.tin, 5));
            size = 2;
            maxNodes = 5;
            laserRange = 10;
            consumePowerBuffered(800f);
        }};

        lithiumBattery = new Battery("lithium-battery") {{
            requirements(Category.power, with(TItems.hematite, 35, TItems.lithium, 10));
            consumePowerBuffered(10000f);
            baseExplosiveness = 3;
        }};

        // endregion
        // region crafting - kudol

        // why isn't it thinking that MultiCrafter is a type? there's an import up there
        // added the multicraft folder from the repository of multicraft and it got fixed
        arcFurnace = new MultiCrafter("arc-furnace") {{
            requirements(Category.crafting, with(TItems.hematite, 50, TItems.tin, 40, TItems.lithium, 25));
            health = 140;
            size = 2;
            itemCapacity = 10;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDefault(),
                new DrawFlame()
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
                    )
                );
            }
        };

        constructor = new MultiCrafter("constructor") {{
            requirements(Category.crafting, with(TItems.darkMetal, 50, TItems.tin, 30, TItems.lithium, 15));
            size = 4;
            itemCapacity = 30;
            resolvedRecipes = Seq.with(
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

        // i tried to place {{ and }}, when saving it changes to { { and } }
        // i hate vscode for this

        //vscodium doesn't have this feature, another reason why vscodium is better than vscode

        // endregion
        // region defense - kudol

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

        // endregion
        // region units - kudol

        unitFabricator = new UnitFactory("unit-fabricator") {{
            requirements(Category.units, with(TItems.darkMetal, 100, TItems.tin, 40, TItems.lithium, 25));
            size = 2;
            health = 260;
            consumePower(3f);
            plans = Seq.with(
                new UnitPlan(TUnitTypes.cobra, 900f, with(TItems.darkMetal, 30, TItems.tin, 15, TItems.bioProcessor, 1))
            );
        }};

        // endregion
        // region effect - kudol

        coreTorch = new TCoreBlock("core-torch") {{
            requirements(Category.effect, with(TItems.darkMetal, 1200, TItems.tin, 900, TItems.gold, 200));
            size = 2;
            isFirstTier = true;
            health = 2000;
            itemCapacity = 3000;
            unitType = TUnitTypes.quant;
        }};

        coreBlaze = new TCoreBlock("core-blaze") {{
            requirements(Category.effect, with(TItems.darkMetal, 3000, TItems.tin, 2000, TItems.aluminium, 1600, TItems.gold, 800));
            size = 3;
            health = 4500;
            itemCapacity = 6000;
            unitType = TUnitTypes.quant;
        }};

        darkContainer = new TStorageBlock("dark-container") {{
            requirements(Category.effect, with(TItems.darkMetal, 100, TItems.tin, 30, TItems.aluminium, 20));
            size = 2;
            scaledHealth = 80;
            itemCapacity = 400;
        }};

        darkVault = new TStorageBlock("dark-vault") {{
            requirements(Category.effect, with(TItems.darkMetal, 400, TItems.tin, 150, TItems.aluminium, 80, TItems.gold, 60));
            size = 3;
            scaledHealth = 90;
            itemCapacity = 1500;
        }};

        darkUnloader = new Unloader("dark-unloader") {{
            requirements(Category.effect, with(TItems.darkMetal, 20, TItems.tin, 5, TItems.aluminium, 10));
            speed = 60 / 15f;
        }};

        // endregion
        // region logic

        switchBlock = new SwitchBlock("switch") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 5));
        }};

        message = new MessageBlock("message") {{
            requirements(Category.logic, with(TItems.darkMetal, 15, TItems.tin, 10));
            maxTextLength = 600;
            maxNewlines = 50;
        }};

        cell = new MemoryBlock("cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 20, TItems.tin, 15, TItems.memoryCard, 4));
            memoryCapacity = 256;
        }};

        bank = new MemoryBlock("bank") {{
            requirements(Category.logic, with(TItems.darkMetal, 100, TItems.tin, 70, TItems.lithium, 45, TItems.memoryCard, 16));
            size = 2;
            memoryCapacity = 1024;
        }};

        energeticProcessor = new LogicBlock("energetic-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 15, TItems.bioProcessor, 1));
            instructionsPerTick = 10;
            size = 1;
        }};

        plasmaProcessor = new LogicBlock("plasma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 120, TItems.aluminium, 80, TItems.lithium, 60, TItems.bioProcessor, 5));
            instructionsPerTick = 24;
            size = 2;
        }};

        display = new LogicDisplay("display") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 10, TItems.bioProcessor, 1));
            size = 4;
            displaySize = 200;
        }};

        miniDisplay = new LogicDisplay("mini-display") {{
            requirements(Category.logic, with(TItems.darkMetal, 20, TItems.tin, 15, TItems.lithium, 5, TItems.bioProcessor, 1));
            size = 2;
            displaySize = 100;
        }};

        stringMemoryBlock = new StringMemoryBlock("string-memory-block") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 20, TItems.memoryCard, 32));
            memoryCapacity = 64;
        }};
    }
}