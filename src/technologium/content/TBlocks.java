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
import mindustry.content.StatusEffects;
import technologium.world.*;
import technologium.world.draw.*;
import technologium.world.blocks.distribution.*;
import technologium.world.blocks.liquid.*;
import technologium.world.blocks.logic.*;
import technologium.world.blocks.storage.*;
import technologium.world.blocks.production.*;
import technologium.world.blocks.power.*;
import technologium.world.blocks.environment.*;
import technologium.type.*;
import multicraft.*;

import static mindustry.Vars.*;
import static technologium.TVars.*;
import static mindustry.type.ItemStack.*;
import static technologium.graphics.TPal.*;

public class TBlocks {

    public static Block

    //environment - floors
    volcanicStone, volcanicSand, volcanicCrater, thermalStone, acidFloor, neoplasticFloor,
    pegmatiteStone, miStone,

    neoplasticLiquid, hydrochloricAcidLiquid, lavaLiquid,

    //environment - ores
    hematiteOre, tinWallOre, bauxiteOre,

    //environment - walls
    volcanicWall, volcanicSandWall, acidWall, neoplasticWall, neoplasticTree, neoplasticTreeBloom, pegmatiteWall,
    miWall, mitaHide,

    leptineTree,

    //environment - props
    volcanicBoulder, volcanicSandBoulder, pegmatiteBoulder,

    leptineItemBlock,

    //turrets - kudol
    comet, constellation,

    //production - kudol
    metallicPlasmaBore, miniPlasmaBore, metallicDrill, advancedDrill, pot, agriculturalCrane,

    //distribution - kudol
    metallicConveyor, metallicJunction, metallicRouter, metallicDistributor, metallicBridgeConveyor,
    metallicSorter, metallicOverflowGate, metallicUnderflowGate, mechanicalDriver, fusedJunction,

    //liquds - kudol
    improvedConduit, thermoConduit, improvedLiquidJunction, improvedLiquidRouter, improvedLiquidBridge, improvedLiquidSorter,
    improvedLiquidContainer,

    //power - kudol
    thermalPlate, thermalGenerator, energeticNode, energeticNodeLarge, lithiumBattery, largeLithiumBattery, solarPanel,
    lithiumCombustionChamber,

    //crafting - kudol
    arcFurnace, arcSmelter, atmosphericCondenser, trainingCenter, acidElectrolyzer, itemConstructor, mixer, filter,
    blockCrafter,

    //defense - kudol
    metallicWall, metallicWallLarge, armoredWall, armoredWallLarge,

    //units - kudol
    unitFabricator, metallicPayloadConveyor,

    //effect - kudol
    coreTorch, coreBlaze, metallicUnloader, metallicContainer, metallicVault,
    
    miniMender, miniShieldProjector, buildTurret, radar, longRangeRadar,

    //logic - kudol
    switchBlock, message, energeticProcessor, plasmaProcessor, gammaProcessor, omegaProcessor, cell, bank, borderlessDisplayMini, borderlessDisplay, stringCell, projector;

    public static final Attribute
    goldAttr = Attribute.add("gold"),
    neoplasmAttr = Attribute.add("neoplasm");

    public static void load() {
        
        // TODO figure out how to add a shader (without it yelling that it can't find it in internal files)
        // CacheLayer.add(new CacheLayer.ShaderLayer(new Shaders.SurfaceShader("lava")));

        //region environment - walls

        volcanicWall = new StaticWall("volcanic-wall") {{
            variants = 3;
            attributes.set(goldAttr, 0.5f);
        }};

        volcanicSandWall = new StaticWall("volcanic-sand-wall") {{
            variants = 3;
            attributes.set(Attribute.sand, 2);
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

        neoplasticTreeBloom = new TreeBlock("neoplastic-tree-bloom");

        miWall = new StaticWall("mi-wall") {{
            variants = 3; 
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
        }};

        mitaHide = new StaticWall("mita-hide") {{
            variants = 1;
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
        }};

        leptineTree = new GrowingTreeBlock("leptine-tree") {{
            // items are loaded before blocks
            ((Fruit)TItems.leptineSeed).tree = this;
            fruit = (Fruit)TItems.leptine;
        }};

        //region environment - floors

        volcanicStone = new Floor("volcanic-stone", 4) {{
            attributes.set(goldAttr, 0.25f);
            wall = volcanicWall;
        }};

        volcanicSand = new Floor("volcanic-sand-floor", 4) {{
            attributes.set(goldAttr, 0.15f);
            wall = volcanicSandWall;
            itemDrop = TItems.volcanicSand;
        }};

        volcanicCrater = new Floor("volcanic-crater", 4) {{
            attributes.set(goldAttr, 0.05f);
            blendGroup = volcanicStone;
            wall = volcanicWall;
        }};

        thermalStone = new Floor("thermal-stone", 4) {{
            attributes.set(goldAttr, 0.05f);
            attributes.set(Attribute.heat, 1f);
            blendGroup = volcanicStone;
            wall = volcanicWall;
        }};

        acidFloor = new Floor("acid-floor", 3) {{
            wall = acidWall;
        }};

        neoplasticFloor = new Floor("neoplastic-floor", 3) {{
            attributes.set(neoplasmAttr, 0.25f);
            wall = neoplasticWall;
        }};

        pegmatiteStone = new Floor("pegmatite-stone", 4) {{
            itemDrop = TItems.pegmatite;
            playerUnmineable = true;
            wall = pegmatiteWall;
        }};

        miStone = new Floor("mi-stone", 3) {{
            wall = miWall;
            buildVisibility = misideRelease ? BuildVisibility.hidden : BuildVisibility.debugOnly;
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

        lavaLiquid = new Floor("lava-liquid") {{
            attributes.set(Attribute.heat, 4f);
            isLiquid = true;
            liquidDrop = TLiquids.lava;
            cacheLayer = CacheLayer.slag;
            drownTime = 280f;
            albedo = 0.9f;
            speedMultiplier = 0.1f;
            variants = 0;
            supportsOverlay = false;
            shallow = false;
            status = StatusEffects.melting;
            statusDuration = 360f;
        }};

        //region environment - ores

        hematiteOre = new OreBlock("hematite-ore", TItems.hematite);

        tinWallOre = new OreBlock("tin-wall-ore", TItems.tin) {{
            wallOre = true;
        }};

        bauxiteOre = new OreBlock("bauxite-ore", TItems.bauxite);

        //region environment - props

        volcanicBoulder = new Prop("volcanic-boulder") {{
            variants = 2;
        }};

        volcanicSandBoulder = new Prop("volcanic-sand-boulder") {{
            variants = 2;
        }};

        pegmatiteBoulder = new Prop("pegmatite-boulder") {{
            variants = 2;
        }};

        leptineItemBlock = new ItemBlock("itemblock-leptine", TItems.leptine);

        //region turrets - kudol

        comet = new PowerTurret("comet") {{
            requirements(Category.turret, with(TItems.hematite, 30, TItems.tin, 15));
            researchCost = with(TItems.hematite, 50, TItems.tin, 40);
            envEnabled |= Env.space;
            range = 80f;
            health = 180;
            size = 1;
            recoil = 2f;
            reload = 60f;
            consumePower(0.25f);
            shootSound = Sounds.blaster;
            drawer = new DrawTurret("kudol-");
            outlineColor = darkerOutline;
            shootType = new LaserBulletType(24) {{
                buildingDamageMultiplier = 0.25f;
                length = 80f;
                colors = new Color[] { purple1, purple2, purple3 };
                ammoMultiplier = 1f;
            }};
        }};

        constellation = new PowerTurret("constellation") {{
            requirements(Category.turret, with(TItems.darkMetal, 75, TItems.tin, 55, TItems.lithium, 30));
            envEnabled |= Env.space;
            health = 240;
            size = 1;
            recoil = 1f;
            reload = 15f;
            range = 175f;
            consumePower(25 / 60f);
            shootSound = Sounds.lasershoot;
            outlineColor = darkerOutline; 
            minWarmup = 0.96f;
            shootWarmupSpeed = 0.1f;
            drawer = new DrawTurret("kudol-"){{
                parts.add(new RegionPart("-side"){{
                    mirror = true;
                    under = true;
                    moveX = 1.2f;
                    moveY = 0f;
                    moveRot = -5;
                    heatProgress = PartProgress.warmup;
                    heatColor = tin1.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0.5f, -0.5f, -10));
                }},
                new RegionPart("-mid"){{
                    under = true;
                }},
                new RegionPart("-gun"){{
                    mirror = true;
                    under = true;
                    moveX = moveY = 1.25f;
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = tin1.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0, -2f, 0));
                }});
            }};
            shoot = new ShootAlternate(5f){{
                shots = 2;
            }};
            shootType = new BasicBulletType(5f, 25f){{
                buildingDamageMultiplier = 0.25f;
                lifetime = 35f;
                frontColor = tin1;
                backColor = hitColor = trailColor = tin2;
                trailWidth = 1.1f;
                trailLength = 5;
                shootEffect = Fx.lightningShoot;
                hitEffect = Fx.colorSpark;
                trailEffect = Fx.disperseTrail;
            }};
        }};

        //region production - kudol

        metallicPlasmaBore = new BeamDrill("metallic-plasma-bore") {{
            requirements(Category.production, with(TItems.hematite, 25, TItems.tin, 10));
            researchCost = with(TItems.hematite, 25, TItems.tin, 10);
            consumePower(12 / 60f);
            health = 120;
            drillTime = 300f;
            size = 2;
            tier = 1;
            range = 6;
            boostHeatColor = heatColor = lithium3;
            consumeLiquid(TLiquids.lava, 0.5f / 60f).boost();
        }};

        miniPlasmaBore = new BeamDrill("mini-plasma-bore") {{
            requirements(Category.production, with(TItems.darkMetal, 10, TItems.tin, 10, TItems.lithium, 5));
            researchCost = with(TItems.darkMetal, 110, TItems.tin, 60, TItems.lithium, 35);
            consumePower(8 / 60f);
            health = 80;
            drillTime = 250f;
            size = 1;
            tier = 1;
            range = 6;
            boostHeatColor = heatColor = lithium3;
            consumeLiquid(TLiquids.lava, 0.4f / 60f).boost();
        }};

        metallicDrill = new Drill("metallic-drill") {{
            requirements(Category.production, with(TItems.hematite, 10, TItems.tin, 8));
            researchCost = with(TItems.hematite, 20, TItems.tin, 16);
            consumePower(5 / 60f);
            health = 110;
            tier = 1;
            drillTime = 210f;
            size = 1;
            hasLiquids = false;
            liquidBoostIntensity = 1f;
        }};

        advancedDrill = new Drill("advanced-drill") {{
            requirements(Category.production, with(TItems.darkMetal, 25, TItems.tin, 15, TItems.lithium, 10));
            consumePower(35 / 60f);
            health = 240;
            tier = 2;
            drillTime = 130f;
            size = 2;
            liquidBoostIntensity = 1f;
        }};

        pot = new Pot("pot") {{
            requirements(Category.production, with(TItems.darkMetal, 20, TItems.aluminium, 15, TItems.tin, 15, TItems.volcanicSand, 40));
            health = 50;
            size = 1;
            squareSprite = false;
        }};

        agriculturalCrane = new ACCrane("agricultural-crane") {{
            requirements(Category.production, with(TItems.darkMetal, 130, TItems.aluminium, 85, TItems.tin, 120, TItems.lithium, 64, TItems.cog, 40));
            health = 460;
            size = 3;
            hasPower = true;
            liquidCapacity = 240f;
            squareSprite = false;
            consumePower(90 / 60f);
            consumeLiquid(Liquids.water, 40 / 60f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(Liquids.water, 1f),
                new DrawDefault()
            );
        }};

        //region distribution - kudol

        metallicConveyor = new TConveyor("metallic-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 1));
            researchCost = with(TItems.hematite, 10);
            drawTop = true;
            health = 40;
            speed = 0.075f;
            displayedSpeed = 10.4f;
            junctionReplacement = metallicJunction;
            bridgeReplacement = metallicBridgeConveyor;
        }};

        metallicJunction = new Junction("metallic-junction") {{
            requirements(Category.distribution, with(TItems.hematite, 2));
            researchCost = with(TItems.hematite, 15);
            health = 60;
            speed = 13;
            capacity = 8;
        }};

        metallicRouter = new Router("metallic-router") {{
            requirements(Category.distribution, with(TItems.hematite, 3));
            researchCost = with(TItems.hematite, 25);
            health = 80;
            speed = 14f;
        }};

        metallicDistributor = new Router("metallic-distributor") {{
            requirements(Category.distribution, with(TItems.hematite, 12, TItems.tin, 8));
            health = 160;
            speed = 14f;
            size = 2;
        }};

        metallicBridgeConveyor = new BufferedItemBridge("metallic-bridge-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 22, TItems.tin, 12));
            health = 130;
            speed = 60f;
            range = 5;
        }};

        metallicSorter = new TSorter("metallic-sorter") {{
            requirements(Category.distribution, with(TItems.hematite, 5, TItems.tin, 5));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 60;
        }};

        metallicOverflowGate = new OverflowGate("metallic-overflow-gate") {{
            requirements(Category.distribution, with(TItems.hematite, 8, TItems.tin, 8));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 70;
        }};

        metallicUnderflowGate = new OverflowGate("metallic-underflow-gate") {{
            requirements(Category.distribution, with(TItems.hematite, 8, TItems.tin, 8));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 70;
            invert = true;
        }};

        mechanicalDriver = new MassDriver("mechanical-driver") {{
            requirements(Category.distribution, with(TItems.darkMetal, 50, TItems.aluminium, 75, TItems.lithium, 40, TItems.cog, 50));
            health = 350;
            size = 2;
            itemCapacity = 60;
            reload = 100f;
            range = 500f;
            consumePower(1.5f);
            outlineColor = darkerOutline;
        }};

        fusedJunction = new OmniJunction("fused-junction") {{ 
            requirements(Category.distribution, with(TItems.hematite, 5, TItems.tin, 5, TItems.goldGlass, 5));
            health = 120;
        }};

        //region liquid - kudol

        improvedConduit = new TempConduit("improved-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 2, TItems.tin, 4, TItems.goldGlass, 2));
            health = 60;
        }};

        thermoConduit = new TempConduit("thermo-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.tin, 8, TItems.goldGlass, 4, TItems.rethium, 2));
            health = 160;
            maxTemp = 5f;
        }};

        improvedLiquidJunction = new TempLiquidJunction("improved-liquid-junction") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.tin, 8, TItems.goldGlass, 4));
            health = 90;
            ((Conduit)improvedConduit).junctionReplacement = this;
            ((Conduit)thermoConduit).junctionReplacement = this;
        }};

        improvedLiquidRouter = new TempLiquidRouter("improved-liquid-router") {{
            requirements(Category.liquid, with(TItems.darkMetal, 6, TItems.tin, 12, TItems.goldGlass, 6));
            health = 120;
            liquidCapacity = 30f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        improvedLiquidBridge = new TempLiquidBridge("improved-liquid-bridge") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.tin, 30, TItems.goldGlass, 20));
            health = 140;
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
            ((Conduit)improvedConduit).bridgeReplacement = this;
            ((Conduit)thermoConduit).bridgeReplacement = this;
        }};

        improvedLiquidContainer = new TempLiquidRouter("improved-liquid-container") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.tin, 40, TItems.goldGlass, 12));
            health = 540;
            liquidCapacity = 800f;
            size = 2;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};

        improvedLiquidSorter = new LiquidSorter("improved-liquid-sorter") {{
            requirements(Category.liquid, with(TItems.darkMetal, 10, TItems.tin, 10, TItems.goldGlass, 40));
            health = 80;    
        }};

        //region power - kudol

        thermalPlate = new ThermalGenerator("thermal-plate") {{
            requirements(Category.power, with(TItems.hematite, 10, TItems.tin, 10));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 120;
            powerProduction = 8 / 60f;
            floating = true;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion()
            );
        }};

        thermalGenerator = new ThermalGenerator("thermal-generator") {{
            requirements(Category.power, with(TItems.darkMetal, 50, TItems.tin, 30, TItems.lithium, 40));
            size = 2;
            health = 230;
            powerProduction = 24 / 60f;
            floating = true;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion()
            );
        }};

        solarPanel = new TSolarGenerator("solar-panel") {{
            requirements(Category.power, with(TItems.darkMetal, 40, TItems.tin, 30, TItems.goldGlass, 50, TItems.lithium, 60));
            size = 2;
            health = 220;
            powerProduction = 40 / 60f;
        }};

        lithiumCombustionChamber = new ConsumeGenerator("lithium-combustion-chamber") {{
            requirements(Category.power, with(TItems.darkMetal, 110, TItems.armorPlate, 30, TItems.tin, 60, TItems.goldGlass, 80, TItems.lithium, 40));
            size = 2;
            health = 370;
            powerProduction = 110 / 60f;
            squareSprite = false;
            liquidCapacity = 100f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(Liquids.water, 1f),
                new DrawDefault()
            );
            generateEffect = Fx.fuelburn;
            consumeItem(TItems.lithium, 1);
            consumeLiquid(Liquids.water, 20 / 60f);
        }};

        energeticNode = new DrawerPowerNode("energetic-node") {{
            requirements(Category.power, with(TItems.hematite, 25, TItems.tin, 15));
            researchCost = with(TItems.hematite, 25, TItems.tin, 15);
            health = 60;
            maxNodes = 5;
            laserRange = 10;
            squareSprite = false;
            laserColor1 = lithium3;
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium1;
                    fullLightColor = lithium3;
                }}
            );
            consumePowerBuffered(400f);
        }};

        energeticNodeLarge = new DrawerPowerNode("energetic-node-large") {{
            requirements(Category.power, with(TItems.darkMetal, 30, TItems.lithium, 12, TItems.tin, 25));
            health = 190;
            size = 2;
            maxNodes = 15;
            laserRange = 25;
            squareSprite = false;
            laserColor1 = lithium3;
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium1;
                    fullLightColor = lithium3;
                }}
            );
            consumePowerBuffered(2400f);
        }};

        lithiumBattery = new Battery("lithium-battery") {{
            requirements(Category.power, with(TItems.darkMetal, 35, TItems.lithium, 15));
            researchCost = with(TItems.darkMetal, 35, TItems.tin, 15);
            researchCostMultiplier = 1f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium1;
                    fullLightColor = lithium3;
                }}
            );
            health = 80;
            baseExplosiveness = 3;
            consumePowerBuffered(2000f);
        }};

        largeLithiumBattery = new Battery("large-lithium-battery") {{
            requirements(Category.power, with(TItems.darkMetal, 45, TItems.lithium, 20, TItems.accumulator, 10));
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium1;
                    fullLightColor = lithium3;
                }}
            );
            health = 270;
            size = 2;
            squareSprite = false;
            baseExplosiveness = 8;
            consumePowerBuffered(15000f);
        }};

        //region crafting - kudol

        arcFurnace = new MultiCrafter("arc-furnace") {{
            requirements(Category.crafting, with(TItems.hematite, 50, TItems.tin, 40, TItems.pegmatite, 20));
            researchCost = with(TItems.hematite, 100, TItems.tin, 80, TItems.pegmatite, 40);
            health = 320;
            size = 2;
            itemCapacity = 10;
            squareSprite = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDefault(),
                new DrawCrucibleFlame()
            );
            resolvedRecipes = Seq.with(
                // hematite -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            TItems.hematite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            TItems.darkMetal, 1
                        );
                    }};
                    craftTime = 120f;
                }},
                // bauxite -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            TItems.bauxite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            TItems.aluminium, 1
                        );
                    }};
                    craftTime = 120f;
                }},
                // pegmatite -> lithium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            TItems.pegmatite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            TItems.lithium, 1
                        );    
                    }};
                    craftTime = 120f;
                }},
                // enriched metal -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            TItems.enrichedMetal, 2
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            TItems.darkMetal, 1
                        );
                    }};
                    craftTime = 120f;
                }},
                // enriched aluminium -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            TItems.enrichedAluminium, 2
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            TItems.aluminium, 1
                        );
                    }};
                    craftTime = 120f;
                }}
            );
        }};

        itemConstructor = new ItemConstructor("item-constructor") {{
            requirements(Category.crafting, with(TItems.darkMetal, 80, TItems.aluminium, 50, TItems.tin, 40, TItems.lithium, 35));
            health = 460;
            size = 4;
            itemCapacity = 30;
            squareSprite = false;
            menu = "detailed";
            armFx = new Effect[] {
                Fx.generatespark,
                Fx.generatespark,
                Fx.generatespark,
                Fx.generatespark
            };
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDefault(),
                new DrawRegion("-top")
            );
            resolvedRecipes = Seq.with(
                // dark metal & aluminium -> cog
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 2, TItems.aluminium, 2);
                        power = 32 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.cog, 3);
                    }};
                }},
                // dark metal & aluminium -> armor plate
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 4, TItems.aluminium, 2);
                        power = 40 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.armorPlate, 2);
                    }};
                }},
                // dark metal & aluminium & tin & lithium & trained neoplasm -> bioprocessor
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 6, TItems.aluminium, 4, TItems.tin, 4, TItems.lithium, 2, TItems.trainedNeoplasm, 1);
                        power = 80 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.bioprocessor, 1);
                    }};
                }},
                // dark metal & aluminium & tin & lithium -> accumulator
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 6, TItems.aluminium, 4, TItems.tin, 4, TItems.lithium, 4);
                        power = 80 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.accumulator, 1);                        
                    }};
                }}
            );
        }};

        mixer = new MultiCrafter("mixer") {{ 
            requirements(Category.crafting, with(TItems.darkMetal, 50, TItems.aluminium, 40, TItems.lithium, 25));
            health = 350;
            size = 3;
            itemCapacity = 10;
            squareSprite = false;
            rotate = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRecipe() {{
                    drawers = new DrawBlock[] {
                        new DrawRegionColored("-powder", 1f, true, TItems.hematite, false),
                        new DrawRegionColored("-powder", 1f, true, TItems.bauxite, false),
                        new DrawRegionColored("-powder", 1f, true, TItems.pegmatite, false)
                    };
                }},
                new DrawLiquidMulti(1f, 0.5f),
                new DrawRegion("-rotator", 1f, true),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                // hematite -> enriched metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.hematite, 3);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.enrichedMetal, 2);
                    }};
                    craftTime = 240f;
                }},
                // bauxite -> enriched aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.bauxite, 3);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.enrichedAluminium, 2);
                    }};
                    craftTime = 240f;
                }},
                // uranium -> enriched uranium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.uranium, 4);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.enrichedUranium, 1);
                    }};
                    craftTime = 360f;
                }},
                // pegmatite -> lithium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.pegmatite, 4);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.lithium, 1);
                    }};
                    craftTime = 300f;
                }}
            );
        }};

        filter = new MultiCrafter("filter"){{
            requirements(Category.crafting, with(TItems.darkMetal, 85, TItems.aluminium, 65, TItems.lithium, 40));
            health = 260;
            hasItems = hasLiquids = hasPower = true;
            size = 2;
            itemCapacity = 30;
            liquidCapacity = 100;
            rotate = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(),
                new DrawRegion("-rotator2", 1f),
                new DrawRegion("-rotator", -1.1f),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.volcanicSand, 15);
                        power = 35/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.gold, 1);
                    }};
                    craftTime = 480f;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.volcanicSand, 10);
                        fluids = LiquidStack.with(Liquids.water, 30 / 60f);
                        power = 35/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.gold, 1);
                    }};
                    craftTime = 480f;
                }}
            );
        }};

        //region defense - kudol

        metallicWall = new Wall("metallic-wall") {{
            requirements(Category.defense, with(TItems.hematite, 8, TItems.tin, 4));
            health = 360;
            researchCostMultiplier = 0.2f;
        }};

        metallicWallLarge = new Wall("metallic-wall-large") {{
            requirements(Category.defense, with(TItems.hematite, 32, TItems.tin, 16));
            scaledHealth = 360;
            size = 2;
        }};

        armoredWall = new Wall("armored-wall") {{
            requirements(Category.defense, with(TItems.darkMetal, 12, TItems.armorPlate, 8));
            health = 700;
        }};

        armoredWallLarge = new Wall("armored-wall-large") {{
            requirements(Category.defense, with(TItems.darkMetal, 48, TItems.armorPlate, 32));
            scaledHealth = 700;
            size = 2;
        }};

        //region units - kudol

        unitFabricator = new UnitFactory("unit-fabricator") {{
            requirements(Category.units, with(TItems.darkMetal, 260, TItems.aluminium, 110, TItems.lithium, 85));
            size = 3;
            health = 440;
            consumePower(3f);
            plans = Seq.with(
                new UnitPlan(TUnitTypes.cobra, 900f, with(TItems.darkMetal, 60, TItems.tin, 40, TItems.bioprocessor, 1)),
                new UnitPlan(TUnitTypes.blade, 1200f, with(TItems.darkMetal, 140, TItems.aluminium, 90, TItems.lithium, 75, TItems.accumulator, 24, TItems.bioprocessor, 1)),
                new UnitPlan(TUnitTypes.mercury, 750f, with(TItems.darkMetal, 55, TItems.aluminium, 70, TItems.lithium, 40, TItems.bioprocessor, 1))
            );
        }};

        metallicPayloadConveyor = new PayloadConveyor("metallic-payload-conveyor") {{ 
            requirements(Category.units, with(TItems.darkMetal, 50, TItems.tin, 35, TItems.lithium, 25));
            health = 160;
            moveTime = 50f;
            size = 3;
        }};

        //region effect - kudol

        coreTorch = new TCoreBlock("core-torch") {{
            requirements(Category.effect, with(TItems.hematite, 1200, TItems.tin, 900));
            size = 2;
            alwaysUnlocked = true;
            isFirstTier = true;
            health = 2000;
            itemCapacity = 3000;
            unitType = TUnitTypes.quant;
            squareSprite = false;
        }};

        coreBlaze = new TCoreBlock("core-blaze") {{
            requirements(Category.effect, with(TItems.darkMetal, 3000, TItems.tin, 2000, TItems.aluminium, 1600, TItems.gold, 500));
            size = 3;
            health = 4500;
            itemCapacity = 6000;
            unitType = TUnitTypes.lonter;
            squareSprite = false;
        }};

        metallicContainer = new TStorageBlock("metallic-container") {{
            requirements(Category.effect, with(TItems.darkMetal, 110, TItems.tin, 50, TItems.aluminium, 40));
            researchCost = with(TItems.darkMetal, 200, TItems.tin, 140, TItems.aluminium, 60);
            size = 2;
            scaledHealth = 80;
            itemCapacity = 400;
            squareSprite = false;
        }};

        metallicVault = new TStorageBlock("metallic-vault") {{
            requirements(Category.effect, with(TItems.darkMetal, 450, TItems.tin, 180, TItems.aluminium, 105, TItems.gold, 60));
            size = 3;
            scaledHealth = 80;
            itemCapacity = 1500;
            squareSprite = false;
        }};

        metallicUnloader = new Unloader("metallic-unloader") {{
            requirements(Category.effect, with(TItems.darkMetal, 20, TItems.tin, 5, TItems.aluminium, 10));
            speed = 60 / 15f;
        }};

        miniMender = new MendProjector("mini-mender") {{
            requirements(Category.effect, with(TItems.darkMetal, 40, TItems.tin, 35, TItems.lithium, 30));
            size = 1;
            health = 110;
            consumePower(48 / 60f);
            range = 4 * 8f;
            phaseRangeBoost = 2 * 8f;
            reload = 180f;
            phaseBoost = 12f;
            healPercent = 8f;
            consumeItem(TItems.darkMetal).boost();
        }};

        miniShieldProjector = new ForceProjector("mini-shield-projector") {{
            requirements(Category.effect, with(TItems.darkMetal, 130, TItems.tin, 60, TItems.lithium, 55, TItems.accumulator, 30, TItems.shieldGen, 10, TItems.bioprocessor, 10));
            size = 2;
            health = 310;
            consumePower(96 / 60f);
            radius = 8 * 8f;
            phaseRadiusBoost = 4 * 8f;
            sides = 16;
            shieldHealth = 900f;
            itemConsumer = consumeItem(TItems.shieldGen).boost();
        }};
        
        radar = new Radar("radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(TItems.hematite, 60, TItems.tin, 15));
            researchCost = with(TItems.hematite, 90, TItems.tin, 30);
            size = 1;
            health = 210;
            consumePower(40 / 60f);
            fogRadius = 13;
            outlineColor = darkerOutline;
        }};

        longRangeRadar = new Radar("long-range-radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(TItems.darkMetal, 45, TItems.tin, 35, TItems.lithium, 30));
            size = 2;
            scaledHealth = 85f;
            consumePower(88 / 60f);
            fogRadius = 25;
            outlineColor = darkerOutline;
        }};

        //region logic

        switchBlock = new SwitchBlock("switch") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 5));
            health = 80;
        }};

        message = new MessageBlock("message") {{
            requirements(Category.logic, with(TItems.darkMetal, 15, TItems.tin, 10));
            maxTextLength = 600;
            maxNewlines = 200;
            health = 80;
        }};

        cell = new MemoryBlock("cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 70, TItems.tin, 55, TItems.memoryCard, 40));
            memoryCapacity = 256;
            health = 120;
        }};

        bank = new MemoryBlock("bank") {{
            requirements(Category.logic, with(TItems.darkMetal, 220, TItems.tin, 150, TItems.lithium, 95, TItems.memoryCard, 160));
            size = 2;
            memoryCapacity = 2048;
            health = 275;
        }};

        energeticProcessor = new LogicBlock("energetic-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 50, TItems.tin, 40, TItems.lithium, 25, TItems.bioprocessor, 5));
            instructionsPerTick = 10;
            hasPower = true;
            squareSprite = false;
            range = 16 * 8;
            health = 240;
            consumePower(35 / 60f);
        }};

        plasmaProcessor = new LogicBlock("plasma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 120, TItems.aluminium, 80, TItems.lithium, 60, TItems.bioprocessor, 25, TItems.memoryCard, 20));
            instructionsPerTick = 24;
            size = 2;
            hasPower = true;
            squareSprite = false;
            range = 48 * 8;
            health = 550;
            consumePower(100 / 60f);
        }};

        gammaProcessor = new LogicBlock("gamma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 250, TItems.aluminium, 155, TItems.rethium, 20, TItems.lithium, 120, TItems.advBioprocessor, 40, TItems.memoryCard, 60));
            instructionsPerTick = 50;
            size = 3;
            hasPower = true;
            squareSprite = false;
            range = 108 * 8;
            health = 820;
            consumePower(250 / 60f);
        }};

        omegaProcessor = new LogicBlock("omega-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 500, TItems.aluminium, 300, TItems.rethium, 100, TItems.lithium, 400, TItems.advBioprocessor, 100, TItems.memoryCard, 120));
            instructionsPerTick = 90;
            size = 1;
            hasPower = true;
            squareSprite = false;
            range = 250 * 8;
            health = 1400;
            consumePower(1250 / 60f);
        }};

        borderlessDisplay = new BorderlessDisplay("borderless-display") {{
            requirements(Category.logic, with(TItems.darkMetal, 130, TItems.tin, 90, TItems.lithium, 80, TItems.bioprocessor, 40));
            size = 4;
            displaySize = 200;
            health = 400;
        }};

        borderlessDisplayMini = new BorderlessDisplay("borderless-display-mini") {{
            requirements(Category.logic, with(TItems.darkMetal, 70, TItems.tin, 55, TItems.lithium, 45, TItems.bioprocessor, 25));
            size = 2;
            displaySize = 100;
            health = 250;
        }};

        stringCell = new StringMemoryBlock("string-cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 20, TItems.memoryCard, 32));
            memoryCapacity = 64;
            health = 100;    
        }};

        projector = new Projector("projector") {{
            requirements(Category.logic, with(TItems.darkMetal, 90, TItems.lithium, 65, TItems.advBioprocessor, 40));
            size = 1;
            health = 280;
            consumePower(0.75f);
        }};
    }
}