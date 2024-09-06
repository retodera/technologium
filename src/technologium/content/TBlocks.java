//fun fact: the mod was originally made on .hjson, but then i decided to add one block type and now i make the mod in java. btw VSCode is the best coding app

package technologium.content;

import arc.graphics.*;
import arc.math.*;
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
import mindustry.world.blocks.legacy.*;
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
import mindustry.content.Liquids;

import technologium.world.blocks.*;

import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;

public class TBlocks {

    public static Block

    // environment - floor
    volcanicStone, thermalStone, acidFloor, neoplasticFloor, neoplasticTree, neoplasticLiquid, hydrochloricAcidLiquid,

            // environment - ore
            hematiteOre, tinWallOre, bauxiteOre,

            // environment - wall
            volcanicWall, acidWall, neoplasticWall,

            // turrets - kudol
            spite,

            // production - kudol
            darkPlasmaBore, darkDrill, goldExtractor, neoplasmCleaningStation,

            // distribution - kudol
            darkConveyor, darkJunction, darkRouter, darkDistributor, darkBridgeConveyor, plasmDriver,

            // liquds - kudol
            improvedConduit, improvedJunction, improvedRouter, improvedBridgeConduit, improvedLiquidContainer,

            // power - kudol
            thermalPlate, darkEnergyNode, lithiumBattery,

            // crafting - kudol
            smallArcFurnace, miniPress, atmosphericCondensator, trainingCenter, acidElectrolyzer,

            // defense - kudol
            darkWall, largeDarkWall,

            // units - kudol
            unitFabricator,

            // effect - kudol
            coreTorch, darkUnloader, darkContainer, darkVault, miniMender, miniShieldProjector, buildTurret,

            // logic - kudol
            // i added "switch", "cell", "nessage" and "bank" to the end of names so their
            // logical links will have that name and the programming with processors would
            // be easier even with the different names of the blocks
            buttonSwitch, communicationBlockMessage, energeticProcessor, plasmProcessor, memoryDeviceCell,
            largeMemoryDeviceBank, stringMemoryDevice, miniatureDigitalDisplay, digitalDisplay

    ;

    public static final Attribute gold = Attribute.add("gold"),
            neoplasm = Attribute.add("neoplasm"),
            neoplasmliquid = Attribute.add("neoplasmliquid");

    // now i'll tell you the story of me creating this

    // most of this is copied from other mods, but mostly from the mindustry itself
    // (as if i didn't do that while the mod was on .hjson)
    // i never coded something in java until now, but i do know a little bit of
    // basics of javascript
    // i always thought that js and java are the same thing. i was horribly wrong.

    public static void load() {

        // region environment - floor
        volcanicStone = new Floor("volcanic-stone") {
            {
                attributes.set(gold, 0.25f);
            }
        };

        thermalStone = new Floor("thermal-stone") {
            {
                attributes.set(Attribute.heat, 1f);
            }
        };

        acidFloor = new Floor("acid-floor");

        neoplasticFloor = new Floor("neoplastic-floor") {
            {
                attributes.set(neoplasm, 0.25f);
            }
        };

        neoplasticTree = new TreeBlock("neoplastic-tree");

        neoplasticLiquid = new Floor("neoplastic-liquid") {
            {
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
                attributes.set(neoplasmliquid, 1f / 9f);
            }
        };

        hydrochloricAcidLiquid = new Floor("hydrochloric-acid-liquid") {
            {
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
            }
        };

        // endregion environment - floor
        // region environment - ore

        hematiteOre = new OreBlock("hematite-ore", TItems.hematite);

        tinWallOre = new OreBlock("tin-wall-ore", TItems.tin) {
            {
                wallOre = true;
            }
        };

        bauxiteOre = new OreBlock("bauxite-ore", TItems.bauxite);

        // endregion environment - ore
        // region environment - wall

        volcanicWall = new StaticWall("volcanicWall");

        acidWall = new StaticWall("acid-wall");

        neoplasticWall = new StaticWall("neoplastic-wall");

        // endregion environment - wall
        // region turrets - kudol

        spite = new PowerTurret("spite") {
            {
                range = 80f;
                health = 180;
                size = 1;
                recoil = 2f;
                reload = 8f;
                consumePower(0.25f);
                shootSound = Sounds.blaster;
                shoot = new ShootBarrel() {
                    {
                        barrels = new float[] {
                                -2.5f, 0f, 0f,
                                -1f, 0f, 0f,
                                1f, 0f, 0f,
                                2.5f, 0f, 0f,
                        };
                    }
                };
                drawer = new DrawTurret("kudol-");
                shootType = new LaserBulletType(12) {
                    {
                        buildingDamageMultiplier = 0.25f;
                        length = 80f;
                        colors = new Color[] { Color.valueOf("05f5e9"), Color.valueOf("00fff2"), Color.white };
                        ammoMultiplier = 1f;
                    }
                };
            }
        };

        // endregion turrets - kudol
        // region production - kudol

        darkPlasmaBore = new BeamDrill("dark-plasma-bore") {
            {
                requirements(Category.production, with(TItems.hematite, 25, TItems.tin, 10));
                consumePower(0.2f);
                health = 120;
                drillTime = 400f;
                size = 2;
                tier = 1;
                range = 6;
                fogRadius = 3;
                researchCost = with(TItems.hematite, 10);
            }
        };

        darkDrill = new Drill("dark-drill") {
            {
                requirements(Category.production, with(TItems.hematite, 20, TItems.tin, 15));
                consumePower(0.25f);
                health = 110;
                tier = 1;
                drillTime = 720f;
                size = 1;
            }
        };

        goldExtractor = new AttributeCrafter("gold-extractor") {
            {
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
                attribute = gold;
                outputItem = new ItemStack(TItems.gold, 1);
                baseEfficiency = 0;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.water),
                        new DrawRegion("-rotator") {
                            {
                                spinSprite = true;
                                rotateSpeed = 1;
                            }
                        },
                        new DrawDefault());
            }
        };

        neoplasmCleaningStation = new AttributeCrafter("neoplasm-cleaning-station") {
            {
                requirements(Category.production, with(TItems.darkMetal, 40, TItems.lithium, 12, TItems.goldGlass, 20));
                consumePower(1.5f);
                health = 460;
                craftTime = 1f;
                size = 3;
                hasLiquids = true;
                hasItems = false;
                hasPower = true;
                liquidCapacity = 240;
                baseEfficiency = 0;
                attribute = neoplasmliquid;
                outputLiquid = new LiquidStack(Liquids.water, 0.25f / 60f);
                placeableLiquid = true;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.water),
                        new DrawDefault());
            }
        };

        // endregion production - kudol
        // region distribution - kudol

        darkConveyor = new Conveyor("dark-conveyor") {
            {
                requirements(Category.distribution, with(TItems.hematite, 1));
                health = 40;
                speed = 0.075f;
                displayedSpeed = 4.5f;
                junctionReplacement = darkJunction;
                bridgeReplacement = darkBridgeConveyor;
                researchCostMultiplier = 5f;
            }
        };

        darkJunction = new Junction("dark-junction") {
            {
                requirements(Category.distribution, with(TItems.hematite, 2));
                health = 80;
                speed = 13;
                capacity = 8;
                researchCostMultiplier = 5f;
            }
        };

        darkRouter = new Router("dark-router") {
            {
                requirements(Category.distribution, with(TItems.hematite, 3));
                health = 120;
                speed = 14f;
            }
        };

        darkDistributor = new Router("dark-distributor") {
            {
                requirements(Category.distribution, with(TItems.hematite, 12));
                health = 240;
                speed = 14f;
                size = 2;
            }
        };

        darkBridgeConveyor = new BufferedItemBridge("dark-bridge-conveyor") {
            {
                requirements(Category.distribution, with(TItems.hematite, 12));
                health = 180;
                speed = 60f;
                range = 5;
            }
        };

        plasmDriver = new MassDriver("plasm-driver") {
            {
                requirements(Category.distribution,
                        with(TItems.darkMetal, 50, TItems.aluminium, 75, TItems.lithium, 40));
                health = 350;
                size = 2;
                itemCapacity = 60;
                reload = 100f;
                range = 500f;
                consumePower(1.5f);
            }
        };
    }
}