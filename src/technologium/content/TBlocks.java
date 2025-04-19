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
import technologium.graphics.TPal;
import technologium.type.*;
import multicraft.*;

import static multicraft.RecipeSwitchStyle.*;
import static mindustry.Vars.*;
import static technologium.TVars.*;
import static mindustry.type.ItemStack.*;
import static technologium.graphics.TPal.*;
import static technologium.world.meta.TAttributes.*;

public class TBlocks {

    public static Block

    //environment - floors
    volcanicStone, volcanicSand, volcanicCrater, thermalStone, acidFloor, neoplasticFloor,
    pegmatiteStone, miStone,

    neoplasticLiquid, shallowNeoplasm, hydrochloricAcidLiquid, lavaLiquid,

    darkMetalFloor1, darkMetalFloor2, darkMetalFloor3, darkMetalFloor4, darkMetalFloor5,

    //environment - ores
    hematiteOre, tinWallOre, bauxiteOre,

    //environment - walls
    volcanicWall, volcanicSandWall, acidWall, neoplasticWall, neoplasticTree, neoplasticTreeBloom, pegmatiteWall,
    miWall, mitaHide, darkWall, oldDarkWall,

    leptineTree, neoplasticVine,

    //environment - props
    volcanicBoulder, volcanicSandBoulder, pegmatiteBoulder,

    leptineItemBlock,

    //turrets - kudol
    comet, constellation, meteor, strike, needle,

    //production - kudol
    metallicPlasmaBore, miniPlasmaBore, manualDrill, metallicDrill, advancedDrill, pot, agriculturalCrane,

    //distribution - kudol
    metallicConveyor, metallicJunction, metallicRouter, metallicDistributor, metallicBridgeConveyor,
    metallicSorter, metallicOverflowGate, metallicUnderflowGate, mechanicalDriver, fusedJunction,

    //liquds - kudol
    improvedConduit, thermoConduit, improvedLiquidJunction, improvedLiquidRouter, improvedLiquidBridge, improvedLiquidSorter,
    improvedLiquidContainer, liquidPump,

    //power - kudol
    thermalPlate, thermalGenerator, energeticNode, energeticNodeLarge, lithiumBattery, largeLithiumBattery, solarPanel,
    lithiumCombustionChamber,

    //crafting - kudol
    arcFurnace, arcSmelter, atmosphericCondenser, trainingCenter, acidElectrolyzer, itemConstructor, mixer, filter,
    blockCrafter, packer,

    //defense - kudol
    metallicWall, metallicWallLarge, armoredWall, armoredWallLarge,

    //units - kudol
    unitFabricator, unitRefabricator,
    
    metallicPayloadConveyor, largePayloadConveyor,

    //effect - kudol
    coreTorch, coreBlaze, metallicUnloader, metallicContainer, metallicVault,
    
    miniMender, miniShieldProjector, buildTurret, radar, longRangeRadar,

    //logic - kudol
    switchBlock, message, energeticProcessor, plasmaProcessor, gammaProcessor, omegaProcessor, cell, bank, borderlessDisplayMini, borderlessDisplay, stringCell, projector;

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
            buildVisibility = debug ? BuildVisibility.hidden : BuildVisibility.debugOnly;
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

        darkWall = new StaticWall("dark-wall") {{
            variants = 5; 
        }};

        oldDarkWall = new StaticWall("dark-wall-old") {{
            variants = 5; 
        }};

        //endregion

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
            buildVisibility = debug ? BuildVisibility.hidden : BuildVisibility.debugOnly;
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

        shallowNeoplasm = new ShallowLiquid("shallow-neoplasm") {{
            speedMultiplier = 0.5f;
            albedo = 0.9f;
            supportsOverlay = true;
            statusDuration = 240f;
            set(neoplasticLiquid, neoplasticFloor);
        }};

        hydrochloricAcidLiquid = new Floor("hydrochloric-acid-liquid") {{
            buildVisibility = debug ? BuildVisibility.hidden : BuildVisibility.debugOnly;
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
            attributes.set(Attribute.heat, 1.75f);
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

        neoplasticVine = new GrowingVine("neoplastic-vine") {{
            variants = 3;
        }};

        darkMetalFloor1 = new Floor("dark-metal-floor-1", 0);

        darkMetalFloor2 = new Floor("dark-metal-floor-2", 0);

        darkMetalFloor3 = new Floor("dark-metal-floor-3", 0);

        darkMetalFloor4 = new Floor("dark-metal-floor-4", 0);

        darkMetalFloor5 = new Floor("dark-metal-floor-5", 0);

        //endregion

        //region environment - ores

        hematiteOre = new OreBlock("hematite-ore", TItems.hematite);

        tinWallOre = new OreBlock("tin-wall-ore", TItems.tin) {{
            wallOre = true;
        }};

        bauxiteOre = new OreBlock("bauxite-ore", TItems.bauxite);

        //endregion

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

        leptineItemBlock = new ItemBlock("itemblock-leptine", TItems.leptine){{
            ((GrowingTreeBlock)leptineTree).dropBlock = this;
        }};

        //endregion

        //region turrets - kudol

        comet = new PowerTurret("comet") {{
            requirements(Category.turret, with(TItems.hematite, 30, TItems.tin, 15));
            researchCost = with(TItems.hematite, 50, TItems.tin, 40);
            envEnabled |= Env.space;
            range = 80f;
            health = 380;
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
            health = 650;
            size = 2;
            recoil = 1f;
            reload = 15f;
            range = 175f;
            shootY = 4f;
            consumePower(50 / 60f);
            shootSound = Sounds.lasershoot;
            outlineColor = darkerOutline;
            squareSprite = false; 
            minWarmup = 0.96f;
            shootWarmupSpeed = 0.1f;
            drawer = new DrawTurret("kudol-"){{
                parts.add(new RegionPart("-side"){{
                    mirror = true;
                    under = true;
                    moveX = 2.75f;
                    moveY = -0.5f;
                    moveRot = -45;
                    heatProgress = PartProgress.warmup;
                    heatColor = tin1.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, -0.5f, -0.5f, -10));
                }},
                new RegionPart("-mid"){{
                    under = true;
                }},
                new RegionPart("-gun"){{
                    mirror = true;
                    under = true;
                    moveX = 1.25f;
                    moveY = -1.25f;
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = tin1.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0, -2f, 0));
                }});
            }};
            shoot = new ShootAlternate(5f){{
                shots = 2;
            }};
            shootType = new BasicBulletType(5f, 15f){{
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

        meteor = new ItemTurret("meteor") {{
            requirements(Category.turret, with(TItems.darkMetal, 60, TItems.aluminium, 45, TItems.lithium, 40, TItems.cog, 30));
            size = 2;
            health = 890;
            recoil = 2f;
            reload = 120f;
            range = 245f;
            shootSound = Sounds.artillery;
            shootEffect = Fx.shootBig;
            outlineColor = darkerOutline;
            squareSprite = false;
            ammoPerShot = 3;
            maxAmmo = 20;
            targetAir = false;
            shake = 2f;
            minWarmup = 0.86f;
            rotateSpeed = 1.8f;
            drawer = new DrawTurret("kudol-"){{
                parts.addAll(
                    new RegionPart("-bottom"){{
                        mirror = false;
                    }},
                    new RegionPart("-gun"){{
                        heatProgress = PartProgress.recoil;
                        heatColor = TPal.red1;
                        progress = PartProgress.recoil;
                        moveY = -3f;
                        mirror = false;
                    }}
                );
            }};
            ammo(
                TItems.lithium, new ArtilleryBulletType(2.5f, 140, "shell") {{
                    lifetime = 98f;
                    height = 9f;
                    width = 7f;
                    splashDamageRadius = 24f;
                    splashDamage = 140f;
                    scaledSplashDamage = true;
                    status = StatusEffects.blasted;
                    smokeEffect = Fx.shootSmallSmoke;
                    frontColor = TPal.lithium3;
                    backColor = TPal.lithium2;
                    hitEffect = new MultiEffect(Fx.explosion, Fx.smoke);
                    hitSound = Sounds.explosion;
                }}
            );
        }};

        strike = new ItemTurret("strike") {{
            requirements(Category.turret, with(TItems.hematite, 45, TItems.tin, 25)); 
            researchCost = with(TItems.hematite, 70, TItems.tin, 50);
            health = 410;
            recoil = 2f;
            reload = 20f;
            range = 160f;
            shootSound = Sounds.shootSnap;
            shootEffect = Fx.shootSmall;
            outlineColor = darkerOutline;
            ammoPerShot = 2;
            maxAmmo = 30;
            shootY = 6f;
            targetAir = false;
            shoot = new ShootAlternate(5f);
            drawer = new DrawTurret("kudol-"){{
                parts.addAll(
                    new RegionPart("-guns"){{
                        heatProgress = PartProgress.recoil;
                        heatColor = TPal.red1;
                        progress = PartProgress.recoil;
                        moveY = -1f;
                        mirror = false;
                    }},
                    new RegionPart("-top"){{
                        mirror = false;
                    }}
                );
            }};
            ammo(TItems.tin, new BasicBulletType(3f, 5f){{
                lifetime = 50f;
                height = 9f;
                width = 7f;
                ammoMultiplier = 3f;
                frontColor = TPal.tin3;
                backColor = TPal.tin2;
                hitEffect = Fx.explosion;
                fragBullets = 3;
                fragVelocityMin = 0.75f;
                fragVelocityMax = 2f;
                fragLifeMin = 0.25f;
                fragLifeMax = 0.75f;
                fragBullet = new BasicBulletType(){{
                    damage = 5f;
                    height = 7f;
                    width = 5f;
                    frontColor = TPal.brown5;
                    backColor = TPal.brown3;
                    hitEffect = Fx.explosion;
                }};
            }},
            TItems.darkMetal, new BasicBulletType(3f, 15f){{
                lifetime = 50f;
                height = 9f;
                width = 7f;
                ammoMultiplier = 5f;
                frontColor = TPal.dark5;
                backColor = TPal.dark3;
                hitEffect = Fx.explosion;
                fragBullets = 5;
                fragVelocityMin = 0.75f;
                fragVelocityMax = 2f;
                fragLifeMin = 0.25f;
                fragLifeMax = 0.75f;
                fragBullet = new BasicBulletType(){{
                    damage = 8f;
                    height = 7f;
                    width = 5f;
                    frontColor = TPal.dark5;
                    backColor = TPal.dark3;
                    hitEffect = Fx.explosion;
                }};
            }});
        }};

        needle = new LiquidTurret("needle") {{
            requirements(Category.turret, with(TItems.darkMetal, 65, TItems.tin, 40, TItems.goldGlass, 30, TItems.lithium, 25));
            health = 720;
            size = 2;
            recoil = 0f;
            reload = 2f;
            liquidCapacity = 240f;
            range = 150f;
            outlineColor = darkerOutline;
            shootEffect = Fx.shootLiquid;
            shootCone = 50f;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            drawer = new DrawTurret("kudol-"){{
                parts.addAll(
                    new RegionPart("-gun"){{
                        mirror = false;
                    }},
                    new RegionPart("-side"){{
                        mirror = true;
                        progress = PartProgress.warmup;
                        moveX = 1f;
                        moveY = -3f;
                        moveRot = -10f;
                    }}
                );
            }};
            ammo(
                Liquids.neoplasm, new LiquidBulletType(Liquids.neoplasm){{
                    knockback = 1f;
                    damage = 0.4f;
                    drag = 0.01f;
                }},
                Liquids.water, new LiquidBulletType(Liquids.water){{
                    knockback = 1f;
                    damage = 0.4f;
                    drag = 0.01f;
                }},
                TLiquids.lava, new LiquidBulletType(TLiquids.lava){{
                    knockback = 1f;
                    damage = 6f;
                    drag = 0.01f;
                }}
            );
        }};

        //endregion

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
            consumeLiquid(Liquids.water, 0.5f / 60f).boost();
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
            consumeLiquid(Liquids.water, 0.4f / 60f).boost();
        }};

        manualDrill = new ManualDrill("manual-drill") {{
            requirements(Category.production, with(TItems.hematite, 6, TItems.tin, 2));
            alwaysUnlocked = true;
            health = 110;
            tier = 2;
            drillTime = 130f;
            size = 1;
            hasLiquids = false;
            liquidBoostIntensity = 1f;
        }};

        metallicDrill = new Drill("metallic-drill") {{
            requirements(Category.production, with(TItems.hematite, 10, TItems.tin, 8));
            researchCost = with(TItems.hematite, 20, TItems.tin, 16);
            consumePower(5 / 60f);
            health = 160;
            tier = 1;
            drillTime = 210f;
            size = 1;
            hasLiquids = false;
            liquidBoostIntensity = 1f;
        }};

        advancedDrill = new Drill("advanced-drill") {{
            requirements(Category.production, with(TItems.darkMetal, 25, TItems.tin, 15, TItems.lithium, 10));
            consumePower(35 / 60f);
            health = 330;
            tier = 2;
            drillTime = 130f;
            size = 2;
            consumeLiquid(Liquids.water, 5 / 60f).boost();
        }};

        pot = new Pot("pot") {{
            requirements(Category.production, with(TItems.darkMetal, 20, TItems.aluminium, 15, TItems.tin, 15, TItems.volcanicSand, 40));
            health = 120;
            size = 1;
            squareSprite = false;
        }};

        agriculturalCrane = new ACCrane("agricultural-crane") {{
            requirements(Category.production, with(TItems.darkMetal, 130, TItems.aluminium, 85, TItems.tin, 120, TItems.lithium, 64, TItems.cog, 40));
            health = 850;
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

        //endregion

        //region distribution - kudol

        metallicConveyor = new TConveyor("metallic-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 1));
            researchCost = with(TItems.hematite, 10);
            drawTop = true;
            health = 60;
            speed = 0.075f;
            displayedSpeed = 10.4f;
        }};

        metallicJunction = new Junction("metallic-junction") {{
            requirements(Category.distribution, with(TItems.hematite, 2));
            researchCost = with(TItems.hematite, 15);
            health = 90;
            speed = 13;
            capacity = 8;
            ((TConveyor)metallicConveyor).junctionReplacement = this;
        }};

        metallicRouter = new Router("metallic-router") {{
            requirements(Category.distribution, with(TItems.hematite, 3));
            researchCost = with(TItems.hematite, 25);
            health = 120;
            speed = 14f;
        }};

        metallicDistributor = new Router("metallic-distributor") {{
            requirements(Category.distribution, with(TItems.hematite, 12, TItems.tin, 8));
            health = 180;
            speed = 14f;
            size = 2;
        }};

        metallicBridgeConveyor = new BufferedItemBridge("metallic-bridge-conveyor") {{
            requirements(Category.distribution, with(TItems.hematite, 22, TItems.tin, 12));
            health = 160;
            speed = 60f;
            range = 5;
            ((TConveyor)metallicConveyor).bridgeReplacement = this;
        }};

        metallicSorter = new TSorter("metallic-sorter") {{
            requirements(Category.distribution, with(TItems.hematite, 5, TItems.tin, 5));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 100;
        }};

        metallicOverflowGate = new OverflowGate("metallic-overflow-gate") {{
            requirements(Category.distribution, with(TItems.hematite, 8, TItems.tin, 8));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 100;
        }};

        metallicUnderflowGate = new OverflowGate("metallic-underflow-gate") {{
            requirements(Category.distribution, with(TItems.hematite, 8, TItems.tin, 8));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 100;
            invert = true;
        }};

        mechanicalDriver = new MassDriver("mechanical-driver") {{
            requirements(Category.distribution, with(TItems.darkMetal, 50, TItems.aluminium, 75, TItems.lithium, 40, TItems.cog, 50));
            health = 560;
            size = 2;
            itemCapacity = 60;
            reload = 100f;
            range = 500f;
            consumePower(1.5f);
            outlineColor = darkerOutline;
        }};

        fusedJunction = new OmniJunction("fused-junction") {{ 
            requirements(Category.distribution, with(TItems.hematite, 4, TItems.tin, 4, TItems.goldGlass, 4));
            health = 120;
        }};

        //endregion

        //region liquid - kudol

        improvedConduit = new TempConduit("improved-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 1, TItems.tin, 2, TItems.goldGlass, 1));
            health = 110;
        }};

        thermoConduit = new TempConduit("thermo-conduit") {{
            requirements(Category.liquid, with(TItems.darkMetal, 2, TItems.tin, 4, TItems.goldGlass, 2, TItems.rethium, 1));
            health = 250;
            maxTemp = 5f;
        }};

        improvedLiquidJunction = new TempLiquidJunction("improved-liquid-junction") {{
            requirements(Category.liquid, with(TItems.darkMetal, 2, TItems.tin, 4, TItems.goldGlass, 2));
            health = 130;
            ((Conduit)improvedConduit).junctionReplacement = this;
            ((Conduit)thermoConduit).junctionReplacement = this;
        }};

        improvedLiquidRouter = new TempLiquidRouter("improved-liquid-router") {{
            requirements(Category.liquid, with(TItems.darkMetal, 3, TItems.tin, 6, TItems.goldGlass, 3));
            health = 150;
            liquidCapacity = 30f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        improvedLiquidBridge = new TempLiquidBridge("improved-liquid-bridge") {{
            requirements(Category.liquid, with(TItems.darkMetal, 10, TItems.tin, 20, TItems.goldGlass, 10));
            health = 170;
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
            ((Conduit)improvedConduit).bridgeReplacement = this;
            ((Conduit)thermoConduit).bridgeReplacement = this;
        }};

        improvedLiquidContainer = new TempLiquidRouter("improved-liquid-container") {{
            requirements(Category.liquid, with(TItems.darkMetal, 20, TItems.tin, 50, TItems.goldGlass, 15));
            health = 540;
            liquidCapacity = 800f;
            size = 2;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};

        improvedLiquidSorter = new LiquidSorter("improved-liquid-sorter") {{
            requirements(Category.liquid, with(TItems.darkMetal, 4, TItems.tin, 8, TItems.goldGlass, 4));
            health = 160;    
        }};

        liquidPump = new Pump("liquid-pump") {{
            requirements(Category.liquid, with(TItems.darkMetal, 30, TItems.tin, 25, TItems.goldGlass, 15, TItems.lithium, 10));
            size = 2;
            pumpAmount = 7.5f / 60f;
            liquidCapacity = 200;
            consumePower(55 / 60f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile() {{
                    padding = 1f;
                }},
                new DrawRegion("-rotator", 7f, true),
                new DrawDefault()
            );
        }};

        //endregion

        //region power - kudol

        thermalPlate = new ThermalGenerator("thermal-plate") {{
            requirements(Category.power, with(TItems.hematite, 10, TItems.tin, 10));
            researchCost = with(TItems.hematite, 10, TItems.tin, 10);
            health = 230;
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
            requirements(Category.power, with(TItems.darkMetal, 30, TItems.tin, 30, TItems.lithium, 20));
            size = 2;
            health = 480;
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
            requirements(Category.power, with(TItems.darkMetal, 40, TItems.accumulator, 20, TItems.goldGlass, 30, TItems.lithium, 30));
            size = 2;
            health = 440;
            powerProduction = 25 / 60f;
        }};

        lithiumCombustionChamber = new ConsumeGenerator("lithium-combustion-chamber") {{
            requirements(Category.power, with(TItems.darkMetal, 75, TItems.armorPlate, 10, TItems.tin, 40, TItems.goldGlass, 20, TItems.lithium, 20));
            size = 2;
            health = 570;
            powerProduction = 280 / 60f;
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
            requirements(Category.power, with(TItems.hematite, 15, TItems.tin, 10));
            researchCost = with(TItems.hematite, 30, TItems.tin, 20);
            health = 110;
            maxNodes = 5;
            laserRange = 10;
            squareSprite = false;
            laserColor1 = lithium3.cpy().mul(1.5f);
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }}
            );
            consumePowerBuffered(400f);
        }};

        energeticNodeLarge = new DrawerPowerNode("energetic-node-large") {{
            requirements(Category.power, with(TItems.darkMetal, 30, TItems.lithium, 12, TItems.tin, 25));
            health = 370;
            size = 2;
            maxNodes = 15;
            laserRange = 25;
            squareSprite = false;
            laserColor1 = lithium3.cpy().mul(1.5f);
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }}
            );
            consumePowerBuffered(2400f);
        }};

        lithiumBattery = new Battery("lithium-battery") {{
            requirements(Category.power, with(TItems.darkMetal, 25, TItems.lithium, 10));
            researchCost = with(TItems.darkMetal, 250, TItems.lithium, 100);
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }}
            );
            health = 220;
            baseExplosiveness = 3;
            consumePowerBuffered(2000f);
        }};

        largeLithiumBattery = new Battery("large-lithium-battery") {{
            requirements(Category.power, with(TItems.darkMetal, 45, TItems.lithium, 20, TItems.accumulator, 10));
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }}
            );
            health = 530;
            size = 2;
            squareSprite = false;
            baseExplosiveness = 8;
            consumePowerBuffered(24000f);
        }};

        //endregion

        //region crafting - kudol

        arcFurnace = new MultiCrafter("arc-furnace") {{
            requirements(Category.crafting, with(TItems.hematite, 50, TItems.tin, 40, TItems.pegmatite, 20));
            researchCost = with(TItems.hematite, 100, TItems.tin, 80, TItems.pegmatite, 40);
            health = 320;
            size = 2;
            squareSprite = false;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.4f;
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

        arcSmelter = new MultiCrafter("arc-smelter") {{
            requirements(Category.crafting, with(TItems.darkMetal, 60, TItems.armorPlate, 50, TItems.lithium, 50, TItems.cog, 30, TItems.gold, 10));
            health = 750;
            size = 4;
            squareSprite = false;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.9f;
            staticCraftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 6f) {{
                rotationOffset = 45f;
            }};
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawCrucibleFlame() {{
                    flameRad = 5;
                    circleSpace = 3;
                }},
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                // enriched metal -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.enrichedMetal, 1);
                        power = 75 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.darkMetal, 1); 
                    }};
                    craftTime = 60f;
                }},
                // enriched aluminium -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.enrichedAluminium, 1);
                        power = 75 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.aluminium, 1); 
                    }};
                    craftTime = 60f;
                }},
                // gold & aluminium -> gold glass
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.aluminium, 1, TItems.gold, 1);
                        power = 95 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.goldGlass, 2); 
                    }};
                    craftTime = 120f;
                }}
            );
        }};

        itemConstructor = new ItemConstructor("item-constructor") {{
            requirements(Category.crafting, with(TItems.darkMetal, 80, TItems.aluminium, 50, TItems.tin, 40, TItems.lithium, 35));
            health = 730;
            size = 4;
            itemCapacity = 30;
            liquidCapacity = 160;
            squareSprite = false;
            switchStyle = detailed;
            consumeLiquid(Liquids.water, 40 / 60f).boost();
            armFx = new Effect[] {
                Fx.absorb,
                Fx.generate,
                Fx.generatespark,
                Fx.formsmoke
            };
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(2f, 0.75f),
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
                        items = with(TItems.darkMetal, 3, TItems.aluminium, 2, TItems.tin, 2, TItems.lithium, 1, TItems.trainedNeoplasm, 1);
                        power = 80 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.bioprocessor, 1);
                    }};
                }},
                // dark metal & aluminium & tin & lithium -> accumulator
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 2, TItems.aluminium, 1, TItems.lithium, 2);
                        power = 80 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.accumulator, 1);                        
                    }};
                }},
                // dark metal & tin -> tin can
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.darkMetal, 1, TItems.tin, 1);
                        power = 80 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.tinCan, 1);                        
                    }};
                }}
            );
        }};

        mixer = new MultiCrafter("mixer") {{ 
            requirements(Category.crafting, with(TItems.cog, 90, TItems.darkMetal, 50, TItems.aluminium, 40, TItems.lithium, 25));
            health = 530;
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
                        items = with(TItems.enrichedMetal, 6);
                    }};
                    craftTime = 180f;
                }},
                // bauxite -> enriched aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.bauxite, 3);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.enrichedAluminium, 6);
                    }};
                    craftTime = 180f;
                }},
                // uranium -> enriched uranium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.uranium, 6);
                        power = 50/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.enrichedUranium, 1);
                    }};
                    craftTime = 360f;
                }},
                // pegmatite -> lithium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.pegmatite, 6);
                        power = 50/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.lithium, 3);
                    }};
                    craftTime = 120f;
                }}
            );
        }};

        filter = new MultiCrafter("filter"){{
            requirements(Category.crafting, with(TItems.darkMetal, 85, TItems.aluminium, 65, TItems.cog, 40, TItems.lithium, 40));
            health = 460;
            size = 2;
            itemCapacity = 30;
            liquidCapacity = 100;
            rotate = false;
            menu = "detailed";
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(),
                new DrawRegion("-rotator2", 1f),
                new DrawRegion("-rotator", -1.1f),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                // volcanic sand -> gold
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.volcanicSand, 15);
                        power = 35 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.gold, 1);
                    }};
                    craftTime = 480f;
                }},
                // volcanic sand & water -> gold
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.volcanicSand, 15);
                        fluids = LiquidStack.with(Liquids.water, 30 / 60f);
                        power = 35 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.gold, 2);
                    }};
                    craftTime = 300f;
                }},
                // lithium & neoplasm -> water
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.lithium, 1);
                        fluids = LiquidStack.with(Liquids.neoplasm, 45 / 60f);
                        power = 50 / 60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(Liquids.water, 25 / 60f);
                    }};
                    craftTime = 90f;
                }}
            );
        }};

        packer = new MultiCrafter("packer"){{
            requirements(Category.crafting, with(TItems.darkMetal, 50, TItems.cog, 50, TItems.goldGlass, 35, TItems.lithium, 25));
            health = 420;
            size = 2;
            rotate = false;
            squareSprite = false;
            liquidCapacity = 200;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(1.8f, 1f),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                // neoplasm -> canned neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.tinCan, 1);
                        fluids = LiquidStack.with(Liquids.neoplasm, 50 / 60f);
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.cannedNeoplasm, 1);
                    }};
                    craftTime = 60f;
                }},
                // canned neoplasm -> neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.cannedNeoplasm, 1);
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.tinCan, 1);
                        fluids = LiquidStack.with(Liquids.neoplasm, 50 / 60f);
                    }};
                    craftTime = 60f;
                }}
            );
        }};

        trainingCenter = new MultiCrafter("training-center") {{
            requirements(Category.crafting, with(TItems.darkMetal, 210, TItems.aluminium, 140, TItems.cog, 50, TItems.accumulator, 20));
            health = 1160;
            size = 5;
            rotate = false;
            squareSprite = false;
            liquidCapacity = 250;
            switchStyle = detailed;
            consumeLiquid(Liquids.water, 80 / 60f).boost();
            optionalMultiplier = 1.5f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion("-heat"){{
                    layer = 30.5f;
                }},
                new DrawLiquidMulti(16f, 1f),
                new DrawRegion("-rotator", 0.25f, true){{
                    layer = 30.75f;
                }},
                new TDrawGlowRegion("-rotator-heat"){{
                    rotate = true;
                    rotateSpeed = 0.25f;
                    color = TPal.neoplasm4;
                    layer = 30.8f;
                }},
                new DrawRegion("-top"){{
                    layer = 30.9f;
                }},
                new TDrawGlowRegion("-top-heat"){{
                    layer = 31f;
                }}
            );
            resolvedRecipes = Seq.with(
                // canned neoplasm -> trained neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(TItems.cannedNeoplasm, 1, TItems.gold, 1, TItems.lithium, 1);
                        power = 75 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(TItems.trainedNeoplasm, 1);
                    }};
                    craftTime = 900f;
                }} 
            );
        }};

        //endregion

        //region defense - kudol

        metallicWall = new Wall("metallic-wall") {{
            requirements(Category.defense, with(TItems.hematite, 8, TItems.tin, 4));
            health = 440;
            researchCostMultiplier = 0.2f;
        }};

        metallicWallLarge = new Wall("metallic-wall-large") {{
            requirements(Category.defense, with(TItems.hematite, 32, TItems.tin, 16));
            scaledHealth = 440;
            size = 2;
        }};

        armoredWall = new Wall("armored-wall") {{
            requirements(Category.defense, with(TItems.darkMetal, 12, TItems.armorPlate, 8));
            health = 720;
        }};

        armoredWallLarge = new Wall("armored-wall-large") {{
            requirements(Category.defense, with(TItems.darkMetal, 48, TItems.armorPlate, 32));
            scaledHealth = 720;
            size = 2;
        }};

        //endregion

        //region units - kudol

        unitFabricator = new UnitFactory("unit-fabricator") {{
            requirements(Category.units, with(TItems.darkMetal, 70, TItems.aluminium, 55, TItems.lithium, 30));
            size = 3;
            health = 640;
            consumePower(150 / 60f);
            plans = Seq.with(
                new UnitPlan(TUnitTypes.cobra, 25 * 60f, with(TItems.darkMetal, 30, TItems.tin, 20, TItems.bioprocessor, 1)),
                new UnitPlan(TUnitTypes.blade, 45 * 60f, with(TItems.darkMetal, 70, TItems.aluminium, 60, TItems.lithium, 60, TItems.accumulator, 20, TItems.bioprocessor, 1)),
                new UnitPlan(TUnitTypes.mercury, 15 * 60f, with(TItems.darkMetal, 50, TItems.aluminium, 50, TItems.lithium, 40, TItems.bioprocessor, 1))
            );
        }};

        unitRefabricator = new Reconstructor("unit-refabricator") {{
            requirements(Category.units, with(TItems.darkMetal, 95, TItems.aluminium, 70, TItems.accumulator, 40, TItems.gold, 10));
            size = 3;
            health = 870;
            consumePower(270 / 60f);
            consumeLiquid(Liquids.water, 20 / 60f);
            consumeItems(with(TItems.armorPlate, 45, TItems.accumulator, 30));
            constructTime = 30 * 60f;
            upgrades.addAll(
                new UnitType[]{TUnitTypes.blade, TUnitTypes.saber},
                new UnitType[]{TUnitTypes.mercury, TUnitTypes.mars},
                new UnitType[]{TUnitTypes.cobra, TUnitTypes.python}
            );
        }};

        metallicPayloadConveyor = new PayloadConveyor("metallic-payload-conveyor") {{ 
            requirements(Category.units, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 5));
            health = 160;
            moveTime = 40f;
            size = 3;
        }};

        largePayloadConveyor = new PayloadConveyor("large-payload-conveyor") {{
            requirements(Category.units, with(TItems.darkMetal, 30, TItems.tin, 15, TItems.lithium, 10));
            health = 160;
            moveTime = 45f;
            payloadLimit = size = 5;
        }};

        //endregion

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
            health = 5500;
            itemCapacity = 8000;
            unitType = TUnitTypes.lonter;
            squareSprite = false;
        }};

        metallicContainer = new TStorageBlock("metallic-container") {{
            requirements(Category.effect, with(TItems.darkMetal, 50, TItems.tin, 30, TItems.aluminium, 40));
            size = 2;
            scaledHealth = 80;
            itemCapacity = 400;
            squareSprite = false;
        }};

        metallicVault = new TStorageBlock("metallic-vault") {{
            requirements(Category.effect, with(TItems.darkMetal, 120, TItems.tin, 80, TItems.aluminium, 95, TItems.gold, 10));
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
            requirements(Category.effect, with(TItems.darkMetal, 20, TItems.tin, 15, TItems.lithium, 5));
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
            requirements(Category.effect, with(TItems.darkMetal, 90, TItems.tin, 60, TItems.lithium, 55, TItems.accumulator, 30, TItems.shieldGen, 10, TItems.bioprocessor, 5));
            size = 2;
            health = 310;
            consumePower(96 / 60f);
            radius = 10 * 8f;
            phaseRadiusBoost = 5 * 8f;
            sides = 16;
            shieldHealth = 500f;
            phaseShieldBoost = 400f;
            itemConsumer = consumeItem(TItems.shieldGen).boost();
        }};
        
        radar = new Radar("radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(TItems.hematite, 40, TItems.tin, 15));
            researchCost = with(TItems.hematite, 90, TItems.tin, 30);
            size = 1;
            health = 210;
            consumePower(40 / 60f);
            fogRadius = 18;
            outlineColor = darkerOutline;
        }};

        longRangeRadar = new Radar("long-range-radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(TItems.darkMetal, 35, TItems.tin, 15, TItems.lithium, 10));
            size = 2;
            health = 340;
            consumePower(88 / 60f);
            fogRadius = 40;
            outlineColor = darkerOutline;
        }};

        //endregion

        //region logic

        switchBlock = new SwitchBlock("switch") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 5, TItems.lithium, 5));
            health = 80;
        }};

        message = new MessageBlock("message") {{
            requirements(Category.logic, with(TItems.darkMetal, 10, TItems.tin, 10));
            maxTextLength = 600;
            maxNewlines = 200;
            health = 80;
        }};

        cell = new MemoryBlock("cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 30, TItems.tin, 25, TItems.memoryCard, 10));
            memoryCapacity = 128;
            health = 250;
        }};

        bank = new MemoryBlock("bank") {{
            requirements(Category.logic, with(TItems.darkMetal, 90, TItems.tin, 70, TItems.lithium, 55, TItems.memoryCard, 50));
            size = 2;
            memoryCapacity = 1024;
            health = 700;
        }};

        energeticProcessor = new LogicBlock("energetic-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 35, TItems.lithium, 25, TItems.bioprocessor, 5));
            instructionsPerTick = 10;
            hasPower = true;
            squareSprite = false;
            range = 16 * 8;
            health = 240;
            consumePower(35 / 60f);
        }};

        plasmaProcessor = new LogicBlock("plasma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 60, TItems.aluminium, 80, TItems.lithium, 30, TItems.bioprocessor, 15, TItems.memoryCard, 10));
            instructionsPerTick = 24;
            size = 2;
            hasPower = true;
            squareSprite = false;
            range = 48 * 8;
            health = 550;
            consumePower(100 / 60f);
        }};

        gammaProcessor = new LogicBlock("gamma-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 95, TItems.aluminium, 65, TItems.rethium, 5, TItems.lithium, 50, TItems.advBioprocessor, 20, TItems.memoryCard, 25));
            instructionsPerTick = 50;
            size = 3;
            hasPower = true;
            squareSprite = false;
            range = 108 * 8;
            health = 820;
            consumePower(250 / 60f);
        }};

        omegaProcessor = new LogicBlock("omega-processor") {{
            requirements(Category.logic, with(TItems.darkMetal, 100, TItems.aluminium, 85, TItems.rethium, 75, TItems.lithium, 75, TItems.advBioprocessor, 50, TItems.memoryCard, 50));
            instructionsPerTick = 90;
            size = 1;
            hasPower = true;
            squareSprite = false;
            range = 250 * 8;
            health = 1400;
            consumePower(1250 / 60f);
        }};

        borderlessDisplay = new BorderlessDisplay("borderless-display") {{
            requirements(Category.logic, with(TItems.darkMetal, 95, TItems.tin, 45, TItems.lithium, 50, TItems.bioprocessor, 5));
            size = 4;
            displaySize = 200;
            health = 400;
        }};

        borderlessDisplayMini = new BorderlessDisplay("mini-borderless-display") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 35, TItems.lithium, 25, TItems.bioprocessor, 1));
            size = 2;
            displaySize = 100;
            health = 250;
        }};

        stringCell = new StringMemoryBlock("string-cell") {{
            requirements(Category.logic, with(TItems.darkMetal, 40, TItems.tin, 25, TItems.lithium, 20, TItems.memoryCard, 32));
            memoryCapacity = 64;
            health = 250;    
        }};

        projector = new Projector("projector") {{
            requirements(Category.logic, with(TItems.darkMetal, 60, TItems.lithium, 45, TItems.advBioprocessor, 20));
            size = 1;
            health = 580;
            consumePower(0.75f);
        }};
    }
}