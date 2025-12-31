package technologium.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import technologium.world.consumers.*;
import technologium.world.draw.*;
import technologium.world.meta.TAttributes;
import technologium.world.blocks.defense.*;
import technologium.world.blocks.distribution.*;
import technologium.world.blocks.liquid.*;
import technologium.world.blocks.logic.*;
import technologium.world.blocks.storage.*;
import technologium.world.blocks.production.*;
import technologium.world.blocks.power.*;
import technologium.world.blocks.units.*;
import technologium.world.blocks.units.schematic.*;
import technologium.world.blocks.environment.*;
import technologium.entities.part.TRegionPart;
import technologium.graphics.TShaders;
import technologium.type.*;
import multicraft.*;

import static multicraft.RecipeSwitchStyle.*;
import static mindustry.type.ItemStack.*;
import static technologium.graphics.TPal.*;
import static technologium.world.meta.TAttributes.*;
import static technologium.content.TItems.*;
import static technologium.content.TLiquids.*;

public class TBlocks {

    public static Block

    //environment - walls
    volcanicWall, volcanicSandWall, neoplasticWall, neoplasticTree, neoplasticTreeBloom, pegmatiteWall,
    darkWall, oldDarkWall, ashWall, ashTree, hotAshWall, hotAshTree, tuffWall, solidLavaWall,

    leptineTree,

    //environment - floors
    volcanicStone, volcanicSandFloor, volcanicCrater, thermalStone, neoplasticFloor,
    pegmatiteStone, ash, hotAsh, tuffFloor, solidLavaFloor,

    tuffFumarole, solidLavaFumarole,

    ashSlag, sirinVein,

    neoplasticLiquid, shallowNeoplasm, lavaLiquid, clearWaterLiquid, scarletWaterLiquid,

    darkMetalFloor1, darkMetalFloor2, darkMetalFloor3, darkMetalFloor4, darkMetalFloor5,

    //environment - ores
    hematiteOre, tinWallOre, bauxiteOre,

    //environment - props
    volcanicBoulder, volcanicSandBoulder, pegmatiteBoulder, solidLavaBoulder,

    //turrets
    comet, constellation, meteor, strike, needle, squall, discharge,

    supressionProjector,

    //production
    metallicPlasmaBore, advancedPlasmaBore,
    wallCrusher,
    metallicDrill, advancedDrill, extractorDrill,
    pyrolysisDrill, fumaroleTurbine, capillarPump,
    
    pot, agriculturalCrane,

    //distribution
    metallicConveyor, metallicJunction, metallicRouter, metallicDistributor, metallicBridgeConveyor,
    metallicSorter, metallicOverflowGate, metallicUnderflowGate, fusedJunction,

    //liquds
    improvedConduit, improvedLiquidJunction, improvedLiquidRouter, improvedLiquidBridge, improvedLiquidContainer, improvedLiquidTank,
    advancedConduit, advancedLiquidJunction, advancedLiquidRouter, advancedLiquidBridge, advancedLiquidContainer, advancedLiquidTank,
    improvedLiquidSorter, improvedLiquidUnloader, liquidPump,

    //power
    thermalPlate, thermalGenerator, energeticNode, energeticNodeLarge, lithiumBattery, largeLithiumBattery, lithiumCombustionChamber,
    cliffThermalPlate,

    //crafting
    arcFurnace, arcSmelter, trainingCenter, itemConstructor, enricher, filter,
    packer, lavaExtractor, multifunctionalSmelter,

    //defense
    metallicWall, metallicWallLarge, metallicWallHuge, armoredWall, armoredWallLarge, armoredWallHuge,

    //units
    unitFabricator,
    
    metallicPayloadConveyor, largePayloadConveyor,

    schematicCarrier, schematicInjector, unitAssemblyPlatform,
    itemInjector, liquidInjector, payloadInjector,

    //effect
    coreTorch, coreBlaze,
    metallicUnloader, metallicContainer, metallicVault,
    miniMender, miniShieldProjector,
    radar, longRangeRadar,

    //logic
    switchBlock, message, energeticProcessor, plasmaProcessor, gammaProcessor, omegaProcessor, memoryCell, memoryBank, borderlessDisplayMini, borderlessDisplay, stringMemoryCell, projector;

    public static void load() {

        // region kudol

        // region environment - walls

        volcanicWall = new StaticWall("volcanic-wall") {{
            variants = 3;
            attributes.set(goldAttr, 0.5f);
        }};

        volcanicSandWall = new StaticWall("volcanic-sand-wall") {{
            variants = 3;
            attributes.set(Attribute.sand, 1f);
        }};

        neoplasticWall = new StaticWall("neoplastic-wall") {{
            attributes.set(TAttributes.neoplasmWall, 0.5f);
            variants = 3;

            emitLight = true;
            lightRadius = 20;
            lightColor = neoplasm3.cpy().a(0.25f);
        }};

        pegmatiteWall = new StaticWall("pegmatite-wall") {{
            attributes.set(TAttributes.pegmatiteWall, 1f);
            variants = 3;
        }};

        neoplasticTree = new TreeBlock("neoplastic-tree") {{
            emitLight = true;
            lightRadius = 35;
            lightColor = neoplasm3.cpy().a(0.35f);
        }};

        neoplasticTreeBloom = new TreeBlock("neoplastic-tree-bloom") {{
            emitLight = true;
            lightRadius = 45;
            lightColor = neoplasm3.cpy().a(0.45f);
        }};

        leptineTree = new GrowingTreeBlock("leptine-tree") {{
            // items are loaded before blocks
            ((Fruit)leptineSeed).tree = this;
            fruit = (Fruit)leptine;
        }};

        darkWall = new StaticWall("dark-wall") {{
            variants = 5; 
        }};

        oldDarkWall = new StaticWall("dark-wall-old") {{
            variants = 5; 
        }};

        ashWall = new StaticWall("ash-wall") {{
            variants = 3;
        }};

        ashTree = new TreeBlock("ash-tree") {{
            variants = 2;
        }};

        hotAshWall = new StaticWall("ash-wall-hot") {{
            variants = 3;
            attributes.set(Attribute.heat, 1);
            emitLight = true;
            lightRadius = 35;
            lightColor = orange3.cpy().a(0.27f);
        }};

        hotAshTree = new TreeBlock("ash-tree-hot") {{
            variants = 2;
            attributes.set(Attribute.heat, 1.25f);
            emitLight = true;
            lightRadius = 40;
            lightColor = orange3.cpy().a(0.35f);
        }};

        tuffWall = new StaticWall("tuff-wall") {{
            variants = 3;
        }};

        solidLavaWall = new StaticWall("solid-lava-wall") {{
            variants = 3;
        }};

        // endregion

        // region environment - floors

        volcanicStone = new Floor("volcanic-stone", 4) {{
            attributes.set(goldAttr, 0.25f);
            wall = volcanicWall;
        }};

        volcanicSandFloor = new Floor("volcanic-sand-floor", 4) {{
            attributes.set(goldAttr, 0.15f);
            wall = volcanicSandWall;
            itemDrop = volcanicSand;
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

            emitLight = true;
            lightRadius = 30;
            lightColor = orange3.cpy().a(0.3f);
        }};

        neoplasticFloor = new Floor("neoplastic-floor", 3) {{
            attributes.set(neoplasmLiquid, 1f);
            wall = neoplasticWall;

            emitLight = true;
            lightRadius = 15;
            lightColor = neoplasm3.cpy().a(0.15f);
        }};

        pegmatiteStone = new Floor("pegmatite-stone", 4) {{
            itemDrop = pegmatite;
            playerUnmineable = true;
            wall = pegmatiteWall;
        }};
        
        neoplasticLiquid = new Floor("neoplastic-liquid", 3) {{
            isLiquid = true;
            liquidDrop = mutatedNeoplasm;
            cacheLayer = CacheLayer.water;
            drownTime = 200f;
            albedo = 0.9f;
            speedMultiplier = 0.2f;
            supportsOverlay = false;
            shallow = false;
            status = TStatusEffects.neoplasmCovered;
            statusDuration = 720f;

            emitLight = true;
            lightRadius = 20;
            lightColor = neoplasm3.cpy().a(0.5f);
        }};

        shallowNeoplasm = new ShallowLiquid("shallow-neoplasm") {{
            speedMultiplier = 0.5f;
            albedo = 0.9f;
            supportsOverlay = true;
            statusDuration = 240f;
            set(neoplasticLiquid, neoplasticFloor);

            emitLight = true;
            lightRadius = 20;
            lightColor = neoplasm3.cpy().a(0.35f);
        }};

        lavaLiquid = new Floor("lava-liquid", 0) {{
            attributes.set(Attribute.heat, 1.75f);
            isLiquid = true;
            liquidDrop = lava;
            cacheLayer = TShaders.lavaCache;
            drownTime = 280f;
            albedo = 0.9f;
            speedMultiplier = 0.1f;
            supportsOverlay = false;
            shallow = false;
            status = StatusEffects.melting;
            statusDuration = 360f;

            emitLight = true;
            lightRadius = 60;
            lightColor = orange3.cpy().a(0.6f);
        }};

        darkMetalFloor1 = new Floor("dark-metal-floor-1", 0){{
            wall = darkWall;
        }};

        darkMetalFloor2 = new Floor("dark-metal-floor-2", 0){{
            wall = darkWall;
            blendGroup = darkMetalFloor1;
        }};

        darkMetalFloor3 = new Floor("dark-metal-floor-3", 0){{
            wall = darkWall;
            blendGroup = darkMetalFloor1;
        }};

        darkMetalFloor4 = new Floor("dark-metal-floor-4", 0){{
            wall = darkWall;
            blendGroup = darkMetalFloor1;
        }};

        darkMetalFloor5 = new Floor("dark-metal-floor-5", 0){{
            wall = darkWall;
            blendGroup = darkMetalFloor1;
        }};

        ash = new Floor("ash-floor", 3) {{
            wall = ashWall;
            attributes.set(carbonAttr, 0.25f);
        }};

        hotAsh = new Floor("ash-floor-hot", 3) {{
            wall = hotAshWall;
            attributes.set(Attribute.heat, 0.75f);
            attributes.set(carbonAttr, 0.5f);

            emitLight = true;
            lightRadius = 30;
            lightColor = orange3.cpy().a(0.22f);
        }};

        ashSlag = new Floor("ash-slag", 3) {{
            attributes.set(carbonAttr, 1f);
        }};

        tuffFloor = new Floor("tuff-floor", 3) {{
            wall = tuffWall;
        }};

        solidLavaFloor = new Floor("solid-lava-floor", 3) {{
            wall = solidLavaWall;
        }};

        tuffFumarole = new SteamVent("tuff-fumarole") {{
            parent = blendGroup = tuffFloor;
            attributes.set(Attribute.steam, 1f);
        }};

        sirinVein = new Floor("sirin-vein", 3) {{
            blendGroup = tuffFloor;
            attributes.set(sirinAttr, 1f);
        }};

        solidLavaFumarole = new SteamVent("solid-lava-fumarole") {{
            parent = blendGroup = solidLavaFloor;
            attributes.set(Attribute.steam, 1f);
        }};

        clearWaterLiquid = new Floor("clear-water-liquid", 0) {{
            isLiquid = true;
            liquidDrop = heavyWater;
            cacheLayer = CacheLayer.water;
            drownTime = 200f;
            albedo = 0.9f;
            speedMultiplier = 0.4f;
            supportsOverlay = false;
            shallow = false;
        }};

        scarletWaterLiquid = new Floor("scarlet-water-liquid", 0) {{
            isLiquid = true;
            liquidDrop = scarletWater;
            cacheLayer = CacheLayer.water;
            drownTime = 200f;
            albedo = 0.9f;
            speedMultiplier = 0.4f;
            supportsOverlay = false;
            shallow = false;
            status = TStatusEffects.neoplasmCovered;
            statusDuration = 120f;

            emitLight = true;
            lightRadius = 15;
            lightColor = neoplasm3.cpy().a(0.25f);
        }};

        // endregion

        // region environment - ores

        hematiteOre = new OreBlock("hematite-ore", hematite);

        tinWallOre = new OreBlock("tin-wall-ore", tin) {{
            wallOre = true;
        }};

        bauxiteOre = new OreBlock("bauxite-ore", bauxite);

        // endregion

        // region environment - props

        volcanicBoulder = new Prop("volcanic-boulder") {{
            variants = 2;
        }};

        volcanicSandBoulder = new Prop("volcanic-sand-boulder") {{
            variants = 2;
        }};

        pegmatiteBoulder = new Prop("pegmatite-boulder") {{
            variants = 2;
        }};

        solidLavaBoulder = new Prop("solid-lava-boulder") {{
            variants = 2;
        }};

        // endregion

        // region turrets

        comet = new PowerTurret("comet") {{
            requirements(Category.turret, with(hematite, 50, tin, 35));
            researchCost = with(hematite, 50, tin, 40);
            envEnabled |= Env.space;
            range = 80f;
            health = 380;
            size = 2;
            recoil = 2f;
            cooldownTime = reload = 60f;
            consumePower(0.25f);
            shootSound = Sounds.shootAvert;
            drawer = new DrawTurret("kudol-");
            outlineColor = darkerOutline;
            shootType = new LaserBulletType(24) {{
                buildingDamageMultiplier = 0.25f;
                length = 80f;
                colors = new Color[] { purple1, purple2, purple3 };
            }};
        }};

        strike = new ItemTurret("strike") {{
            requirements(Category.turret, with(hematite, 65, tin, 45)); 
            researchCost = with(hematite, 70, tin, 50);
            health = 410;
            recoil = 0.5f;
            reload = 20f;
            size = 2;
            range = 160f;
            shootSound = Sounds.shootScatter;
            shootEffect = Fx.shootSmall;
            outlineColor = darkerOutline;
            ammoPerShot = 2;
            maxAmmo = 30;
            shootY = 3f;
            targetAir = false;
            recoils = 2;
            shoot = new ShootAlternate(3.5f);
            drawer = new DrawTurret("kudol-"){{
                for(int i : Mathf.signs) {
                    parts.add(new TRegionPart("-gun-" + (i == 1 ? "r" : "l")){{
                        progress = PartProgress.recoil;
                        recoilIndex = i == 1 ? 1 : 0;
                        moveY = -1f;
                    }});
                }
                parts.add(new TRegionPart("-top"));
            }};
            ammo(
                tin, new BasicBulletType(3f, 5f){{
                    lifetime = 50f;
                    height = 9f;
                    width = 7f;
                    ammoMultiplier = 3f;
                    frontColor = tin3;
                    backColor = hitColor = trailColor = tin2;
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
                        frontColor = tin3;
                        backColor = hitColor = trailColor = tin2;
                        hitEffect = Fx.explosion;
                    }};
                }},
                darkMetal, new BasicBulletType(3f, 15f){{
                    lifetime = 50f;
                    height = 9f;
                    width = 7f;
                    ammoMultiplier = 5f;
                    frontColor = darkAmmoFront;
                    backColor = hitColor = trailColor = darkAmmoBack;
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
                        frontColor = darkAmmoFront;
                        backColor = hitColor = trailColor = darkAmmoBack;
                        hitEffect = Fx.explosion;
                    }};
                }}
            );
        }};

        constellation = new PowerTurret("constellation") {{
            requirements(Category.turret, with(darkMetal, 75, tin, 55, lithium, 30));
            envEnabled |= Env.space;
            health = 650;
            size = 3;
            recoil = 1f;
            reload = 15f;
            range = 175f;
            shootY = 7f;
            consumePower(50 / 60f);
            shootSound = Sounds.shootLaser;
            outlineColor = darkerOutline;
            squareSprite = false; 
            minWarmup = 0.96f;
            shootWarmupSpeed = 0.1f;
            recoils = 2;
            shootY = 7f;
            shoot = new ShootAlternate(7f);
            drawer = new DrawTurret("kudol-"){{
                parts.add(new TRegionPart("-side"){{
                    mirror = true;
                    under = true;
                    moveX = 3.5f;
                    moveY = 1f;
                    moveRot = -45f;
                    layerOffset = -0.02f;
                    turretHeatLayer = Layer.turret - 0.01f;
                    heatProgress = PartProgress.warmup;
                    heatColor = lithium3.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0, -1f, 0));
                }},
                new TRegionPart("-mid"){{
                    moveY = 1f;
                }},
                new TRegionPart("-gun-l"){{
                    under = true;
                    moveX = -2.5f;
                    moveY = -1.5f;
                    recoilIndex = 0;
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = lithium3.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0, -2f, 0));
                }},
                new TRegionPart("-gun-r"){{
                    under = true;
                    moveX = 2.5f;
                    moveY = -1.5f;
                    recoilIndex = 1;
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = lithium3.cpy().a(0.9f);
                    moves.add(new PartMove(PartProgress.recoil, 0, -2f, 0));
                }});
            }};
            shootType = new BasicBulletType(5f, 15f){{
                buildingDamageMultiplier = 0.25f;
                lifetime = 35f;
                frontColor = purple1;
                backColor = hitColor = trailColor = purple2;
                homingPower = 0.05f;
                trailWidth = 1.1f;
                trailLength = 5;
                shootEffect = Fx.lightningShoot;
                hitEffect = Fx.colorSpark;
                trailEffect = Fx.disperseTrail;
            }};
        }};

        meteor = new ItemTurret("meteor") {{
            requirements(Category.turret, with(darkMetal, 60, aluminium, 45, lithium, 40, cog, 30));
            size = 2;
            health = 890;
            recoil = 2f;
            reload = 120f;
            range = 245f;
            shootSound = Sounds.shootArtillery;
            shootEffect = Fx.shootBig;
            outlineColor = darkerOutline;
            squareSprite = false;
            ammoPerShot = 5;
            maxAmmo = 30;
            targetAir = false;
            shake = 2f;
            minWarmup = 0.86f;
            rotateSpeed = 1.8f;
            drawer = new DrawTurret("kudol-"){{
                parts.addAll(
                    new TRegionPart("-mid"){{
                        mirror = false;
                        heatProgress = PartProgress.warmup;
                    }},
                    new TRegionPart("-gun"){{
                        heatProgress = PartProgress.recoil;
                        heatColor = red1;
                        progress = PartProgress.recoil;
                        moveY = -3f;
                        mirror = false;
                    }}
                );
            }};
            ammo(
                lithium, new ArtilleryBulletType(2.5f, 140, "shell") {{
                    lifetime = 98f;
                    height = 9f;
                    width = 7f;
                    splashDamageRadius = 24f;
                    splashDamage = 140f;
                    scaledSplashDamage = true;
                    status = StatusEffects.blasted;
                    smokeEffect = Fx.shootSmallSmoke;
                    frontColor = lithium3;
                    backColor = lithium2;
                    hitEffect = new MultiEffect(Fx.explosion, Fx.smoke);
                    hitSound = Sounds.explosion;
                }}
            );
        }};

        needle = new LiquidTurret("needle") {{
            requirements(Category.turret, with(darkMetal, 65, tin, 40, goldGlass, 30, lithium, 25));
            health = 720;
            size = 2;
            recoil = 1f;
            recoilTime = 30;
            reload = 2f;
            liquidCapacity = 50f;
            range = 130f;
            outlineColor = darkerOutline;
            shootEffect = Fx.shootLiquid;
            shootCone = 50f;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            drawer = new DrawTurret("kudol-"){{
                parts.addAll(
                    new TRegionPart("-gun"){{
                        mirror = false;
                    }},
                    new TRegionPart("-side"){{
                        mirror = true;
                        progress = PartProgress.recoil.curve(Interp.pow3Out);
                        moveX = 1f;
                        moveY = -1.7f;
                        moveRot = -30f;
                    }}
                );
            }};
            ammo(
                mutatedNeoplasm, new LiquidBulletType(mutatedNeoplasm){{
                    knockback = 1f;
                    damage = 0.4f;
                    drag = 0.01f;
                    lifetime = 41;
                }},
                heavyWater, new LiquidBulletType(heavyWater){{
                    knockback = 1f;
                    damage = 0.4f;
                    drag = 0.01f;
                    lifetime = 41;
                }},
                scarletWater, new LiquidBulletType(scarletWater){{
                    knockback = 1f;
                    damage = 0.4f;
                    drag = 0.01f;
                    lifetime = 41;
                }},
                lava, new LiquidBulletType(lava){{
                    knockback = 1f;
                    damage = 6f;
                    drag = 0.01f;
                    lifetime = 41;
                }}
            );
        }};

        squall = new ItemTurret("squall") {{
            requirements(Category.turret, with(darkMetal, 70, tin, 60, armorPlate, 10, cog, 60));
            size = 3;
            health = 1050;
            reload = 5f;
            range = 245f;
            shootY = 6;
            inaccuracy = 10;
            shootSound = Sounds.shootBreach;
            shootEffect = Fx.shootBig;
            outlineColor = darkerOutline;
            squareSprite = false;
            ammoPerShot = 1;
            recoils = 3;
            recoilTime = 10;
            shoot = new ShootAlternate(3.25f){{ barrels = 3; }};
            maxAmmo = 36;
            ammo(
                darkMetal, new BasicBulletType(4, 7){{
                    lifetime = 60;
                    height = 7f;
                    width = 5f;
                    frontColor = darkAmmoFront;
                    backColor = hitColor = trailColor = darkAmmoBack;
                    ammoMultiplier = 6;
                    trailEffect = Fx.disperseTrail;
                    trailRotation = true;
                    trailChance = 0.5f;
                    splashDamage = 20;
                    splashDamageRadius = 1.5f;
                }},
                cog, new BasicBulletType(4, 6, "t-cogbullet"){{
                    lifetime = 90;
                    height = 5f;
                    width = 3f;
                    reloadMultiplier = 1.25f;
                    rangeChange = 120;
                    ammoMultiplier = 4;
                    frontColor = darkAmmoFront;
                    backColor = hitColor = trailColor = darkAmmoBack;
                    trailEffect = Fx.oily;
                    trailChance = 0.25f;
                    splashDamage = 15;
                    splashDamageRadius = 2f;
                }}
            );
            targetGround = false;
            drawer = new TDrawTurret("kudol-"){{
                parts.addAll(
                    new TRegionPart("-barrel-mid"){{
                        heatProgress = progress = PartProgress.recoil;
                        moveY = -1;
                        recoilIndex = 1;
                        moves.add(new PartMove(PartProgress.warmup, 0, -1, 0));
                    }},
                    new TRegionPart("-mid"){{
                        heatProgress = PartProgress.warmup;
                    }}
                );
                for(int i : Mathf.signs)
                    parts.addAll(new TRegionPart("-side" + (i == -1 ? "-l" : "-r")){{ 
                        heatProgress = progress = PartProgress.warmup;
                        moveY = -1f;
                        moveRot = 5f * i;
                        children.add(new TRegionPart("-barrel-side" + (i == -1 ? "-l" : "-r")){{
                            heatProgress = progress = PartProgress.recoil;
                            moveY = -1;
                            under = true;
                            recoilIndex = i == -1 ? 0 : 2;
                        }});
                    }});
            }};
        }};

        supressionProjector = new SupressionProjector("supression-projector") {{
            requirements(Category.turret, with(darkMetal, 100, tin, 80, lithium, 75, goldGlass, 25));
            health = 1400;
            size = 3;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(oxygen, 1),
                new DrawDefault(),
                new DrawGlowRegion(){{
                    color = orange3;
                }},
                new DrawPulseShape(){{
                    layer = Layer.effect;
                    color = orange3;
                }}
            );
            status = StatusEffects.slow;
            statusDuration = 120;
            consumePower(35 / 60f);
            consumeLiquid(oxygen, 6 / 60f);
            consumeItem(accumulator).boost();
        }};

        // endregion

        // region production

        metallicPlasmaBore = new TBeamDrill("metallic-plasma-bore") {{
            requirements(Category.production, with(hematite, 25, tin, 10));
            researchCost = with(hematite, 25, tin, 10);
            consumePower(8 / 60f);
            conductivePower = true;
            health = 120;
            drillTime = 300f;
            size = 2;
            tier = 1;
            range = 6;
            boostHeatColor = heatColor = lithium3;
            consumeLiquid(heavyWater, 0.5f / 60f).boost();
        }};

        advancedPlasmaBore = new TBeamDrill("advanced-plasma-bore") {{
            requirements(Category.production, with(darkMetal, 30, tin, 25, lithium, 5));
            consumePower(30 / 60f);
            conductivePower = true;
            health = 600;
            squareSprite = false;
            drillTime = 150f;
            size = 3;
            tier = 2;
            range = 8;
            boostHeatColor = heatColor = lithium3;
            consumeLiquid(heavyWater, 1f / 60f);
        }};

        wallCrusher = new WallMultiCrafter("wall-crusher") {{
            requirements(Category.production, with(darkMetal, 30, cog, 30, lithium, 15));
            health = 450;
            size = 2;
            drillTime = 90f;
            ambientSound = Sounds.loopDrill;
            ambientSoundVolume = 0.04f;
            addRecipe(TAttributes.neoplasmWall, solidNeoplasm, 1.5f);
            addRecipe(TAttributes.pegmatiteWall, pegmatite, 1);
            consumePower(48 / 60f);
        }};

        metallicDrill = new Drill("metallic-drill") {{
            requirements(Category.production, with(hematite, 10, tin, 8));
            researchCost = with(hematite, 20, tin, 16);
            consumePower(5 / 60f);
            conductivePower = true;
            health = 160;
            tier = 1;
            drillTime = 180f;
            size = 1;
            hasLiquids = false;
            liquidBoostIntensity = 1f;
        }};

        advancedDrill = new Drill("advanced-drill") {{
            requirements(Category.production, with(darkMetal, 25, tin, 15, lithium, 10));
            consumePower(35 / 60f);
            conductivePower = true;
            health = 430;
            tier = 2;
            rotateSpeed = 3.5f;
            drillTime = 120f;
            liquidBoostIntensity = 1.4f;
            size = 2;
            consumeLiquid(heavyWater, 5 / 60f).boost();
        }};

        extractorDrill = new Drill("extractor-drill") {{
            requirements(Category.production, with(darkMetal, 35, aluminium, 25, cog, 50, accumulator, 10));
            consumePower(72 / 60f);
            health = 760;
            tier = 3;
            rotateSpeed = 5;
            drillTime = 120f;
            liquidBoostIntensity = 1.6f;
            size = 3;
            consumeLiquid(heavyWater, 10 / 60f).boost();
            squareSprite = false;
        }};

        pyrolysisDrill = new TAttributeCrafter("pyrolysis-drill") {{
            requirements(Category.production, with(darkMetal, 90, tin, 40, lithium, 40, cog, 25));
            health = 1050;
            squareSprite = false;
            liquidCapacity = 600;
            size = 4;
            baseEfficiency = 0;
            minEfficiency = 0.001f;
            boostScale = 1/16f;
            optionalBoostIntensity = 2;
            attribute = carbonAttr;
            craftEffect = Fx.none;

            craftTime = 60;
            outputLiquid = new LiquidStack(carbon, 10f/60f);

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(lava, 1),
                new DrawLiquidTile(carbon, 12),
                new DrawDefault(),
                new TDrawGlowRegion(){{
                    color = red3;
                }}
            );

            consumePower(140/60f);
            consumeLiquid(lava, 5f/60f).boost();
        }};

        fumaroleTurbine = new ThermalGenerator("fumarole-turbine") {{
            requirements(Category.production, with(darkMetal, 40, goldGlass, 30, tin, 30, lithium, 15));
            health = 600;
            size = 3;
            squareSprite = false;
            attribute = Attribute.steam;
            minEfficiency = 9 - 0.001f;
            powerProduction = 75/9f/60f;
            displayEfficiency = false;
            displayEfficiencyScale = 9;
            outputLiquid = new LiquidStack(sulfur, 15/9/60f);
            liquidCapacity = 100;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(sulfur, 1),
                new DrawDefault(),
                new DrawBlurSpin("-rotator", 5),
                new DrawRegion("-top")
            );
        }};

        capillarPump = new TAttributeCrafter("capillar-pump") {{
            requirements(Category.production, with(darkMetal, 50, tin, 40, lithium, 25));
            health = 630;
            size = 2;
            liquidCapacity = 260;
            squareSprite = false;
            attribute = sirinAttr;
            minEfficiency = 0.001f;
            boostScale = 1/4f;
            baseEfficiency = 0;
            craftEffect = Fx.none;

            craftTime = 60;
            outputLiquid = new LiquidStack(sirin, 5f/60f);

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(sirin, 1),
                new DrawDefault()
            );
        }};

        pot = new Pot("pot") {{
            requirements(Category.production, with(darkMetal, 20, aluminium, 15, tin, 15, volcanicSand, 40));
            health = 120;
            size = 1;
            squareSprite = false;
        }};

        agriculturalCrane = new ACCrane("agricultural-crane") {{
            requirements(Category.production, with(darkMetal, 130, aluminium, 85, tin, 120, lithium, 64, cog, 40));
            health = 850;
            size = 3;
            hasPower = true;
            liquidCapacity = 240f;
            squareSprite = false;
            consumePower(90 / 60f);
            consumeLiquid(heavyWater, 40 / 60f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(heavyWater, 1f),
                new DrawDefault()
            );
        }};

        // endregion

        // region distribution

        metallicConveyor = new TConveyor("metallic-conveyor") {{
            requirements(Category.distribution, with(hematite, 1));
            researchCost = with(hematite, 10);
            drawTop = true;
            health = 60;
            speed = 0.075f;
            displayedSpeed = 10.4f;
        }};

        metallicJunction = new Junction("metallic-junction") {{
            requirements(Category.distribution, with(hematite, 2));
            researchCost = with(hematite, 15);
            health = 90;
            speed = 13;
            capacity = 8;
            ((TConveyor)metallicConveyor).junctionReplacement = this;
        }};

        metallicRouter = new Router("metallic-router") {{
            requirements(Category.distribution, with(hematite, 3));
            researchCost = with(hematite, 25);
            health = 120;
            speed = 14f;
        }};

        metallicDistributor = new Router("metallic-distributor") {{
            requirements(Category.distribution, with(hematite, 12, tin, 8));
            researchCostMultiplier = 0.3f;
            health = 180;
            speed = 14f;
            size = 2;
        }};

        metallicBridgeConveyor = new BufferedItemBridge("metallic-bridge-conveyor") {{
            requirements(Category.distribution, with(hematite, 22, tin, 12));
            researchCostMultiplier = 0.3f;
            health = 160;
            speed = 60f;
            range = 5;
            ((TConveyor)metallicConveyor).bridgeReplacement = this;
        }};

        metallicSorter = new TSorter("metallic-sorter") {{
            requirements(Category.distribution, with(hematite, 5, tin, 5));
            researchCostMultiplier = 0.3f;
            health = 100;
        }};

        metallicOverflowGate = new OverflowGate("metallic-overflow-gate") {{
            requirements(Category.distribution, with(hematite, 8, tin, 8));
            researchCost = with(hematite, 10, tin, 10);
            health = 100;
        }};

        metallicUnderflowGate = new OverflowGate("metallic-underflow-gate") {{
            requirements(Category.distribution, with(hematite, 8, tin, 8));
            researchCost = with(hematite, 10, tin, 10);
            health = 100;
            invert = true;
        }};

        fusedJunction = new OmniJunction("fused-junction") {{ 
            requirements(Category.distribution, with(hematite, 4, tin, 4, goldGlass, 4));
            health = 120;
        }};

        // endregion

        // region liquid

        improvedConduit = new TempConduit("improved-conduit") {{
            requirements(Category.liquid, with(darkMetal, 1, tin, 2, goldGlass, 1));
            health = 110;
        }};

        improvedLiquidJunction = new TempLiquidJunction("improved-liquid-junction") {{
            requirements(Category.liquid, with(darkMetal, 2, tin, 4, goldGlass, 2));
            health = 130;
            ((Conduit)improvedConduit).junctionReplacement = this;
        }};

        improvedLiquidRouter = new TempLiquidRouter("improved-liquid-router") {{
            requirements(Category.liquid, with(darkMetal, 3, tin, 6, goldGlass, 3));
            health = 150;
            liquidCapacity = 30f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        improvedLiquidBridge = new TempLiquidBridge("improved-liquid-bridge") {{
            requirements(Category.liquid, with(darkMetal, 10, tin, 20, goldGlass, 10));
            health = 170;
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
            ((Conduit)improvedConduit).bridgeReplacement = this;
        }};

        improvedLiquidContainer = new TempLiquidRouter("improved-liquid-container") {{
            requirements(Category.liquid, with(darkMetal, 20, tin, 50, goldGlass, 15));
            health = 540;
            liquidCapacity = 800f;
            size = 2;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};

        improvedLiquidTank = new TempLiquidRouter("improved-liquid-tank") {{
            requirements(Category.liquid, with(darkMetal, 30, tin, 75, goldGlass, 35));
            health = 960;
            liquidCapacity = 2400f;
            size = 3;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};
        
        advancedConduit = new TempConduit("advanced-conduit") {{
            requirements(Category.liquid, with(darkMetal, 2, ademarium, 1));
            health = 250;
            maxTemp = 2f;
        }};

        advancedLiquidJunction = new TempLiquidJunction("advanced-liquid-junction") {{
            requirements(Category.liquid, with(darkMetal, 4, ademarium, 2));
            health = 290;
            maxTemp = 2f;
            ((Conduit)advancedConduit).junctionReplacement = this;
        }};

        advancedLiquidRouter = new TempLiquidRouter("advanced-liquid-router") {{
            requirements(Category.liquid, with(darkMetal, 6, ademarium, 3));
            maxTemp = 2f;
            health = 320;
            liquidCapacity = 50f;
            placeableLiquid = true;
            underBullets = true;
            solid = false;
        }};

        advancedLiquidBridge = new TempLiquidBridge("advanced-liquid-bridge") {{
            requirements(Category.liquid, with(darkMetal, 20, ademarium, 10));
            maxTemp = 2f;
            health = 400;
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 8;
            hasPower = false;
            ((Conduit)advancedConduit).bridgeReplacement = this;
        }};

        advancedLiquidContainer = new TempLiquidRouter("advanced-liquid-container") {{
            requirements(Category.liquid, with(darkMetal, 50, ademarium, 25));
            maxTemp = 2f;
            health = 780;
            liquidCapacity = 1200f;
            size = 2;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};

        advancedLiquidTank = new TempLiquidRouter("advanced-liquid-tank") {{
            requirements(Category.liquid, with(darkMetal, 75, ademarium, 60));
            maxTemp = 2f;
            health = 1350;
            liquidCapacity = 4000f;
            size = 3;
            solid = true;
            squareSprite = false;
            liquidPadding = 1f;
        }};
        
        improvedLiquidSorter = new LiquidSorter("improved-liquid-sorter") {{
            requirements(Category.liquid, with(darkMetal, 4, tin, 8, goldGlass, 4));
            health = 160;    
        }};

        improvedLiquidUnloader = new LiquidUnloader("improved-liquid-unloader") {{
            requirements(Category.liquid, with(darkMetal, 20, aluminium, 10, goldGlass, 4));
            health = 160;
        }};

        liquidPump = new Pump("liquid-pump") {{
            requirements(Category.liquid, with(darkMetal, 30, tin, 25, goldGlass, 15, lithium, 10));
            size = 2;
            pumpAmount = 7.5f / 60f;
            squareSprite = false;
            liquidCapacity = 200;
            consumePower(55 / 60f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile() {{
                    padding = 1f;
                }},
                new DrawBlurSpin("-rotator", 7f),
                new DrawDefault()
            );
        }};

        // endregion

        // region power 

        thermalPlate = new ThermalGenerator("thermal-plate") {{
            requirements(Category.power, with(hematite, 10, tin, 10));
            researchCost = with(hematite, 10, tin, 10);
            health = 230;
            powerProduction = 12 / 60f;
            floating = true;
            fogRadius = 2;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion()
            );
        }};

        thermalGenerator = new ThermalGenerator("thermal-generator") {{
            requirements(Category.power, with(darkMetal, 30, tin, 30, lithium, 20));
            size = 2;
            health = 480;
            powerProduction = 35 / 60f;
            floating = true;
            fogRadius = 4;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion()
            );
        }};

        lithiumCombustionChamber = new ConsumeGenerator("lithium-combustion-chamber") {{
            requirements(Category.power, with(darkMetal, 75, armorPlate, 10, tin, 40, goldGlass, 20, lithium, 20));
            size = 2;
            health = 570;
            powerProduction = 450 / 60f;
            itemDuration = 240;
            squareSprite = false;
            liquidCapacity = 100f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(1f, 1f),
                new TDrawCircles(){{
                    radius = 6.5f;
                    radiusOffset = 1.5f;
                    strokeMax = 0.5f;
                }},
                new TDrawBubbles(),
                new DrawDefault()
            );
            generateEffect = Fx.blockExplosionSmoke;
            consumeItem(lithium, 1);
            consume(new ConsumeLiquidList(heavyWater, 1f, scarletWater, 1.1f));
        }};

        energeticNode = new PowerLine("energetic-node") {{
            requirements(Category.power, with(hematite, 15, tin, 10));
            researchCost = with(hematite, 30, tin, 20);
            health = 110;
            maxNodes = 8;
            laserRange = 10;
            elevation = 1;
            connectRadius = 1.75f;
            fogRadius = 1;
            squareSprite = false;
            laserColor1 = lithium3.cpy().mul(1.5f);
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new TDrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }},
                new DrawElevated("-beam", 0, 1),
                new DrawElevated("-top", 1){{
                    layer = Layer.power-0.1f;
                }}
            );
            consumePowerBuffered(400f);
        }};

        energeticNodeLarge = new PowerLine("energetic-node-large") {{
            requirements(Category.power, with(darkMetal, 30, lithium, 12, tin, 25));
            health = 370;
            size = 2;
            maxNodes = 20;
            laserRange = 25;
            elevation = 1.5f;
            connectRadius = 2.75f;
            fogRadius = 3;
            powerLayer = Layer.flyingUnit+0.1f;
            squareSprite = false;
            laserColor1 = lithium3.cpy().mul(1.5f);
            laserColor2 = lithium2;
            drawer = new DrawMulti(
                new TDrawDefault(),
                new DrawPower(){{
                    emptyLightColor = lithium2;
                    fullLightColor = lithium3;
                }},
                new DrawElevated("-beam", 0, 1.5f),
                new DrawElevated("-top", 1.5f){{
                    layer = Layer.flyingUnit+0.1f;
                }}
            );
            consumePowerBuffered(2400f);
        }};

        lithiumBattery = new Battery("lithium-battery") {{
            requirements(Category.power, with(darkMetal, 25, lithium, 10));
            researchCost = with(darkMetal, 250, lithium, 100);
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
            requirements(Category.power, with(darkMetal, 45, lithium, 20, accumulator, 10));
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

        cliffThermalPlate = new WallThermalGenerator("cliff-thermal-plate") {{
            requirements(Category.power, with(TItems.darkMetal, 5, TItems.lithium, 5));
            health = 360;
            powerProduction = 45 / 60f;
            floating = true;
            generateEffect = Fx.redgeneratespark;
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.06f;
            rotateDraw = false;
            drawer = new DrawMulti(
                new TDrawDefault(),
                new DrawRegion("-top"){{
                    buildingRotate = true;
                }},
                new TDrawGlowRegion(){{
                    rotate = true;
                }}
            );
        }};

        // endregion

        // region crafting

        arcFurnace = new MultiCrafter("arc-furnace") {{
            requirements(Category.crafting, with(hematite, 50, tin, 40, pegmatite, 20));
            researchCost = with(hematite, 100, tin, 80, pegmatite, 40);
            health = 320;
            size = 2;
            squareSprite = false;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.4f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDefault(),
                new DrawCrucibleFlame()
            );
            // usually, i (retodera) have the recipes folded, to they're all labeled
            resolvedRecipes = Seq.with(
                // hematite -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            hematite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            darkMetal, 2
                        );
                    }};
                    craftTime = 120f;
                }},
                // bauxite -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            bauxite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            aluminium, 1
                        );
                    }};
                    craftTime = 120f;
                }},
                // pegmatite -> lithium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            pegmatite, 5
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            lithium, 1
                        );    
                    }};
                    craftTime = 120f;
                }},
                // enriched metal -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            enrichedMetal, 2
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            darkMetal, 1
                        );
                    }};
                    craftTime = 120f;
                }},
                // enriched aluminium -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(
                            enrichedAluminium, 2
                        );
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(
                            aluminium, 1
                        );
                    }};
                    craftTime = 120f;
                }}
            );
        }};

        arcSmelter = new MultiCrafter("arc-smelter") {{
            requirements(Category.crafting, with(darkMetal, 60, armorPlate, 50, lithium, 50, cog, 30, gold, 10));
            health = 750;
            size = 4;
            squareSprite = false;
            ambientSound = Sounds.loopSmelter;
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
                        items = with(enrichedMetal, 1);
                        power = 55 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(darkMetal, 2); 
                    }};
                    craftTime = 90f;
                }},
                // enriched aluminium -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(enrichedAluminium, 1);
                        power = 55 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(aluminium, 1); 
                    }};
                    craftTime = 90f;
                }},
                // gold & aluminium -> gold glass
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(aluminium, 2, gold, 1);
                        power = 75 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(goldGlass, 5); 
                    }};
                    craftTime = 180f;
                }},
                // tin & gold & carbon -> molybdenum
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(tin, 2, gold, 1);
                        fluids = LiquidStack.with(carbon, 20);
                        power = 100 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(ademarium, 3); 
                    }};
                    craftTime = 180f;
                }},
                // solid neoplasm -> mutated neoplasm
                new Recipe() {{ 
                    input = new IOEntry() {{
                        items = with(solidNeoplasm, 1);
                        power = 30/60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(mutatedNeoplasm, 15);
                    }};
                    craftTime = 45f;
                }}
            );
            consumeLiquid(lava, 15/60f).boost();
            optionalIntensity = 1.5f;
        }};

        multifunctionalSmelter = new CatMultiCrafter("multifunctional-smelter") {{
            requirements(Category.crafting, with(darkMetal, 180, armorPlate, 100, ademarium, 75, accumulator, 30));
            health = 1700;
            size = 5;
            squareSprite = false;
            itemCapacity = 40;
            liquidCapacity = 100;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 1.1f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(15f, 1f),
                new DrawDefault(),
                new DrawGlowRegion("-heat"){{
                    color = red3;
                }},
                new DrawGlowRegion("-heat-center"){{
                    color = red3;
                    alpha = 0.5f;
                }}
            );
            staticCraftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 12f) {{
                rotationOffset = 45f;
            }};
            categories = Seq.with(
                RecipeCat.smelting, RecipeCat.alloySmelting, RecipeCat.melting
            );
            resolvedRecipes = Seq.with(
                // hematite -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(hematite, 1);
                        power = 50 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(darkMetal, 1);
                    }};
                    craftTime = 60f;
                    category = RecipeCat.smelting;
                }},
                // enriched metal -> dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(enrichedMetal, 3);
                        power = 70 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(darkMetal, 8);
                    }};
                    craftTime = 60f;
                    category = RecipeCat.smelting;
                }},
                // bauxite -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(bauxite, 1);
                        power = 50 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(aluminium, 1);
                    }};
                    craftTime = 60f;
                    category = RecipeCat.smelting;
                }},
                // enriched aluminium -> aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(enrichedAluminium, 3);
                        power = 70 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(aluminium, 8);
                    }};
                    craftTime = 60f;
                    category = RecipeCat.smelting;
                }},
                // dark metal -> molten dark metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(darkMetal, 1);
                        power = 90/60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(moltenDarkMetal, 20/60f);
                    }};
                    craftTime = 30f;
                    category = RecipeCat.melting;
                }},
                // aluminium -> molten aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(aluminium, 1);
                        power = 90/60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(moltenAluminium, 20/60f);
                    }};
                    craftTime = 30f;
                    category = RecipeCat.melting;
                }},
                // tin & gold & carbon -> molybdenum
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(tin, 3, gold, 1);
                        fluids = LiquidStack.with(carbon, 20/60f);
                        power = 90 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(ademarium, 4);
                    }};
                    craftTime = 90f;
                    category = RecipeCat.alloySmelting;
                }},
                // dark metal & lithium & sulfur -> rhenium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(darkMetal, 6, lithium, 4);
                        fluids = LiquidStack.with(sulfur, 30/60f);
                        power = 90 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(pyrolite, 6);
                    }};
                    craftTime = 240f;
                    category = RecipeCat.alloySmelting;
                }}
            );
        }};

        itemConstructor = new ItemConstructor("item-constructor") {{
            requirements(Category.crafting, with(darkMetal, 80, aluminium, 50, tin, 40, lithium, 35));
            health = 730;
            size = 4;
            itemCapacity = 30;
            liquidCapacity = 160;
            squareSprite = false;
            switchStyle = detailed;
            optionalIntensity = 2f;
            consumeLiquid(heavyWater, 15 / 60f).boost();
            setupFx(
                Fx.absorb,
                Fx.generate,
                Fx.generatespark,
                Fx.formsmoke
            );
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(2f, 0.75f),
                new DrawDefault(),
                new DrawRegion("-top")
            );
            
            resolvedRecipes = Seq.with(
                new Recipe(cog)          {{input.power = 50/60f;}},
                new Recipe(armorPlate)   {{input.power = 60/60f;}},
                new Recipe(bioprocessor) {{input.power = 80/60f;}},
                new Recipe(accumulator)  {{input.power = 80/60f;}},
                new Recipe(tinCan)       {{input.power = 50/60f;}}
            );
        }};

        enricher = new MultiCrafter("enricher") {{ 
            requirements(Category.crafting, with(cog, 90, darkMetal, 50, aluminium, 40, lithium, 25));
            health = 530;
            size = 3;
            itemCapacity = 30;
            squareSprite = false;
            rotate = false;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawRecipe() {{
                    drawers = new DrawBlock[] {
                        new DrawRegionColored("-powder", 0.75f, true, hematite, false),
                        new DrawRegionColored("-powder", 0.75f, true, bauxite, false),
                        new DrawRegionColored("-powder", 0.75f, true, pegmatite, false),
                    };
                }},
                new DrawLiquidMulti(1f, 0.5f),
                new DrawDefault(),
                new DrawRegion("-rotator", 1f, true),
                new DrawRegion("-top")
            );
            resolvedRecipes = Seq.with(
                // hematite -> enriched metal
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(hematite, 3);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(enrichedMetal, 6);
                    }};
                    craftTime = 180f;
                }},
                // bauxite -> enriched aluminium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(bauxite, 3);
                        power = 40/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(enrichedAluminium, 6);
                    }};
                    craftTime = 180f;
                }},
                // pegmatite -> lithium
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(pegmatite, 6);
                        power = 50/60f;
                    }};
                    output = new IOEntry() {{
                        items = with(lithium, 3);
                    }};
                    craftTime = 120f;
                }}
            );
        }};

        filter = new MultiCrafter("filter"){{
            requirements(Category.crafting, with(darkMetal, 85, aluminium, 65, cog, 40, lithium, 40));
            health = 460;
            size = 2;
            itemCapacity = 30;
            liquidCapacity = 100;
            rotate = false;
            menu = "detailed";
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidMulti(1f, 1),
                new DrawRegion("-rotator2", 1f),
                new DrawRegion("-rotator", -1.1f),
                new DrawDefault()
            );
            resolvedRecipes = Seq.with(
                // volcanic sand -> gold
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(volcanicSand, 15);
                        power = 35 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(gold, 1);
                    }};
                    craftTime = 480f;
                }},
                // volcanic sand & heavy water -> gold
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(volcanicSand, 15);
                        fluids = LiquidStack.with(heavyWater, 30 / 60f);
                        power = 35 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(gold, 2);
                    }};
                    craftTime = 300f;
                }},
                // lithium & mutated neoplasm -> heavy water
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(lithium, 1);
                        fluids = LiquidStack.with(mutatedNeoplasm, 60 / 60f);
                        power = 50 / 60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(heavyWater, 45 / 60f);
                    }};
                    craftTime = 90f;
                }},
                // lithium & scarlet water -> heavy water
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(lithium, 1);
                        fluids = LiquidStack.with(scarletWater, 60 / 60f);
                        power = 50 / 60f;
                    }};
                    output = new IOEntry() {{
                        fluids = LiquidStack.with(heavyWater, 60 / 60f);
                    }};
                    craftTime = 90f;
                }}
            );
        }};

        packer = new MultiCrafter("packer"){{
            requirements(Category.crafting, with(darkMetal, 50, cog, 50, goldGlass, 35, lithium, 25));
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
                // mutated neoplasm -> canned neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(tinCan, 1);
                        fluids = LiquidStack.with(mutatedNeoplasm, 50 / 60f);
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(cannedNeoplasm, 1);
                    }};
                    craftTime = 60f;
                }},
                // canned neoplasm -> mutated neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(cannedNeoplasm, 1);
                        power = 30 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(tinCan, 1);
                        fluids = LiquidStack.with(mutatedNeoplasm, 50 / 60f);
                    }};
                    craftTime = 60f;
                }}
            );
        }};

        trainingCenter = new MultiCrafter("training-center") {{
            requirements(Category.crafting, with(darkMetal, 210, aluminium, 140, cog, 50, accumulator, 20));
            health = 1560;
            size = 5;
            squareSprite = false;
            liquidCapacity = 250;
            switchStyle = detailed;
            consumeLiquid(heavyWater, 45 / 60f).boost();
            optionalIntensity = 1.5f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new TDrawGlowRegion("-heat"){{
                    layer = 30.1f;
                    color = red3;
                }},
                new DrawLiquidMulti(14f, 1f),
                new DrawRegion("-rotator", 0.25f, true){{
                    layer = 30.2f;
                }},
                new TDrawGlowRegion("-rotator-heat"){{
                    rotate = true;
                    rotateSpeed = 0.25f;
                    color = neoplasm3;
                    layer = 30.3f;
                }},
                new DrawRegion("-top"){{
                    layer = 30.4f;
                }},
                new TDrawGlowRegion("-top-heat"){{
                    layer = 30.5f;
                    color = red3;
                }}
            );
            resolvedRecipes = Seq.with(
                // canned neoplasm -> trained neoplasm
                new Recipe() {{
                    input = new IOEntry() {{
                        items = with(cannedNeoplasm, 1, gold, 1, lithium, 1);
                        power = 75 / 60f;
                    }};
                    output = new IOEntry() {{
                        items = with(trainedNeoplasm, 1);
                    }};
                    craftTime = 900f;
                }} 
            );
        }};
   
        lavaExtractor = new Separator("lava-extractor") {{
            requirements(Category.crafting, with(darkMetal, 160, armorPlate, 35, ademarium, 45, accumulator, 5));
            health = 1205;
            size = 3;
            squareSprite = false;
            craftTime = 90f;
            liquidCapacity = 210;
            consumePower(140 / 60f);
            consumeLiquid(lava, 15/60f);
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(TLiquids.lava, 2f),
                new DrawGlowRegion("-rotator-glow"){{
                    rotate = true;
                    rotateSpeed = 1.5f;
                    color = orange1.cpy().mul(0.75f);
                    layer = -1;
                }},
                new DrawRegion("-rotator", 1.5f, true),
                new DrawDefault()
            );
            results = with(
                iron, 7,
                calcium, 4,
                silicon, 2
            );
        }};

        // endregion

        // region defense

        metallicWall = new Wall("metallic-wall") {{
            requirements(Category.defense, with(hematite, 8, tin, 4));
            health = 440;
            researchCostMultiplier = 0.2f;
        }};

        metallicWallLarge = new Wall("metallic-wall-large") {{
            requirements(Category.defense, with(hematite, 32, tin, 16));
            scaledHealth = 440;
            size = 2;
        }};

        metallicWallHuge = new Wall("metallic-wall-huge") {{
            requirements(Category.defense, with(hematite, 72, tin, 36));
            scaledHealth = 440;
            size = 3;
        }};

        armoredWall = new Wall("armored-wall") {{
            requirements(Category.defense, with(armorPlate, 4, lithium, 2));
            health = 720;
            absorbLasers = true;
        }};

        armoredWallLarge = new Wall("armored-wall-large") {{
            requirements(Category.defense, with(armorPlate, 16, lithium, 8));
            scaledHealth = 720;
            absorbLasers = true;
            size = 2;
        }};

        armoredWallHuge = new Wall("armored-wall-huge") {{
            requirements(Category.defense, with(armorPlate, 36, lithium, 18));
            scaledHealth = 720;
            absorbLasers = true;
            size = 3;
        }};

        // endregion

        // region units

        unitFabricator = new UnitFactory("unit-fabricator") {{
            requirements(Category.units, with(darkMetal, 70, aluminium, 55, lithium, 30));
            size = 3;
            health = 640;
            consumePower(150 / 60f);
            plans = Seq.with(
                new UnitPlan(TUnitTypes.cobra, 25 * 60f, with(darkMetal, 30, tin, 20, bioprocessor, 1)),
                new UnitPlan(TUnitTypes.blade, 45 * 60f, with(darkMetal, 70, aluminium, 60, lithium, 60, accumulator, 20, bioprocessor, 1)),
                new UnitPlan(TUnitTypes.mercury, 15 * 60f, with(darkMetal, 50, aluminium, 50, lithium, 40, bioprocessor, 1))
            );
        }};

        metallicPayloadConveyor = new PayloadConveyor("metallic-payload-conveyor") {{ 
            requirements(Category.units, with(darkMetal, 10, tin, 5, lithium, 5));
            health = 160;
            moveTime = 40f;
            size = 3;
        }};

        largePayloadConveyor = new PayloadConveyor("large-payload-conveyor") {{
            requirements(Category.units, with(darkMetal, 30, tin, 15, lithium, 10));
            health = 160;
            moveTime = 45f;
            payloadLimit = size = 5;
        }};

        schematicCarrier = new SchematicCarrier("schematic-carrier") {{
            requirements(Category.units, with(darkMetal, 30, aluminium, 15, memoryCard, 10));
            size = 2; 
        }};

        schematicInjector = new SchematicInjector("schematic-injector") {{
            requirements(Category.units, with(darkMetal, 50, aluminium, 35, lithium, 20));
            size = 3;
        }};

        unitAssemblyPlatform = new UnitAssemblyPlatform("unit-assembly-platform") {{
            requirements(Category.units, with(armorPlate, 100, aluminium, 75, accumulator, 30));
            size = 5;
        }};

        itemInjector = new ItemInjector("item-injector") {{
            requirements(Category.units, with(armorPlate, 50, aluminium, 35, accumulator, 10));
            size = 2;
        }};

        liquidInjector = new LiquidInjector("liquid-injector") {{
            requirements(Category.units, with(armorPlate, 50, aluminium, 35, accumulator, 10));
            size = 2;
        }};

        payloadInjector = new PayloadInjector("payload-injector") {{
            requirements(Category.units, with(armorPlate, 50, aluminium, 35, accumulator, 10));
            size = 3;
        }};

        // endregion

        // region effect

        coreTorch = new TCoreBlock("core-torch") {{
            requirements(Category.effect, with(hematite, 1200, tin, 900));
            size = 2;
            alwaysUnlocked = true;
            isFirstTier = true;
            health = 2000;
            itemCapacity = 3000;
            unitType = TUnitTypes.quant;
            unitCapModifier = 15;
            squareSprite = false;
        }};

        coreBlaze = new TCoreBlock("core-blaze") {{
            requirements(Category.effect, with(darkMetal, 3000, tin, 2000, aluminium, 1000, gold, 200));
            size = 3;
            health = 5500;
            itemCapacity = 8000;
            unitType = TUnitTypes.lonter;
            unitCapModifier = 25;
            squareSprite = false;
        }};

        metallicContainer = new TStorageBlock("metallic-container") {{
            requirements(Category.effect, with(darkMetal, 50, tin, 30, aluminium, 40));
            size = 2;
            scaledHealth = 80;
            itemCapacity = 400;
            squareSprite = false;
        }};

        metallicVault = new TStorageBlock("metallic-vault") {{
            requirements(Category.effect, with(darkMetal, 120, tin, 80, aluminium, 95, gold, 10));
            size = 3;
            scaledHealth = 80;
            itemCapacity = 1500;
            squareSprite = false;
        }};

        metallicUnloader = new TUnloader("metallic-unloader") {{
            requirements(Category.effect, with(darkMetal, 20, aluminium, 10));
            speed = 60 / 15f;
        }};

        miniMender = new MendProjector("mini-mender") {{
            requirements(Category.effect, with(darkMetal, 20, tin, 15, lithium, 5));
            size = 1;
            health = 110;
            consumePower(48 / 60f);
            range = 4 * 8f;
            phaseRangeBoost = 3 * 8f;
            reload = 180f;
            phaseBoost = 12f;
            healPercent = 8f;
            consumeItem(darkMetal).boost();
        }};

        miniShieldProjector = new DrawerForceProjector("mini-shield-projector") {{
            requirements(Category.effect, with(darkMetal, 90, tin, 60, lithium, 55, accumulator, 30, shieldGen, 10, bioprocessor, 5));
            size = 2;
            health = 310;
            consumePower(96 / 60f);
            radius = 10 * 8f;
            phaseRadiusBoost = 5 * 8f;
            sides = 16;
            shieldHealth = 500f;
            phaseShieldBoost = 400f;
            itemConsumer = consumeItem(shieldGen).boost();
            coolantConsumer = consumeLiquid(heavyWater, 3/60f).boost();
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(heavyWater),
                new DrawDefault()
            );
        }};
        
        radar = new Radar("radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(hematite, 40, tin, 15));
            researchCost = with(hematite, 90, tin, 30);
            size = 1;
            health = 210;
            consumePower(40 / 60f);
            fogRadius = 18;
            outlineColor = darkerOutline;
        }};

        longRangeRadar = new Radar("long-range-radar") {{
            requirements(Category.effect, BuildVisibility.fogOnly, with(darkMetal, 35, tin, 15, lithium, 10));
            size = 2;
            health = 340;
            consumePower(88 / 60f);
            fogRadius = 40;
            outlineColor = darkerOutline;
        }};

        // endregion

        // region logic

        switchBlock = new SwitchBlock("switch") {{
            requirements(Category.logic, with(darkMetal, 10, tin, 5, lithium, 5));
            health = 80;
        }};

        message = new MessageBlock("message") {{
            requirements(Category.logic, with(darkMetal, 10, tin, 10));
            maxTextLength = 600;
            maxNewlines = 200;
            health = 80;
        }};

        memoryCell = new MemoryBlock("memory-cell") {{
            requirements(Category.logic, with(darkMetal, 30, tin, 25, memoryCard, 10));
            memoryCapacity = 128;
            health = 250;
        }};

        memoryBank = new MemoryBlock("memory-bank") {{
            requirements(Category.logic, with(darkMetal, 90, tin, 70, lithium, 55, memoryCard, 50));
            size = 2;
            memoryCapacity = 1024;
            health = 700;
        }};

        energeticProcessor = new LogicBlock("energetic-processor") {{
            requirements(Category.logic, with(darkMetal, 40, tin, 35, lithium, 25, bioprocessor, 5));
            instructionsPerTick = 10;
            hasPower = true;
            squareSprite = false;
            range = 16 * 8;
            health = 240;
            consumePower(35 / 60f);
        }};

        plasmaProcessor = new LogicBlock("plasma-processor") {{
            requirements(Category.logic, with(darkMetal, 60, aluminium, 80, lithium, 30, bioprocessor, 15, memoryCard, 10));
            instructionsPerTick = 24;
            size = 2;
            hasPower = true;
            squareSprite = false;
            range = 48 * 8;
            health = 550;
            consumePower(100 / 60f);
        }};

        gammaProcessor = new LogicBlock("gamma-processor") {{
            requirements(Category.logic, with(darkMetal, 95, aluminium, 65, lithium, 50, advBioprocessor, 20, memoryCard, 25));
            instructionsPerTick = 50;
            size = 3;
            hasPower = true;
            squareSprite = false;
            range = 108 * 8;
            health = 820;
            consumePower(250 / 60f);
        }};

        omegaProcessor = new LogicBlock("omega-processor") {{
            requirements(Category.logic, with(darkMetal, 100, aluminium, 85, lithium, 75, advBioprocessor, 50, memoryCard, 50));
            instructionsPerTick = 90;
            size = 1;
            hasPower = true;
            squareSprite = false;
            range = 250 * 8;
            health = 1400;
            consumePower(1250 / 60f);
        }};

        borderlessDisplay = new BorderlessDisplay("borderless-display") {{
            requirements(Category.logic, with(darkMetal, 95, tin, 45, lithium, 50, bioprocessor, 5));
            size = 4;
            displaySize = 200;
            health = 400;
        }};

        borderlessDisplayMini = new BorderlessDisplay("mini-borderless-display") {{
            requirements(Category.logic, with(darkMetal, 40, tin, 35, lithium, 25, bioprocessor, 1));
            size = 2;
            displaySize = 100;
            health = 250;
        }};

        stringMemoryCell = new StringMemoryBlock("string-memory-cell") {{
            requirements(Category.logic, with(darkMetal, 40, tin, 25, lithium, 20, memoryCard, 32));
            memoryCapacity = 64;
            health = 250;    
        }};

        projector = new Projector("projector") {{
            requirements(Category.logic, with(darkMetal, 60, lithium, 45, advBioprocessor, 20));
            size = 1;
            health = 580;
        }};

        // endregion

        // endregion
    }
}