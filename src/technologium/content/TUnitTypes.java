package technologium.content;

import mindustry.ai.types.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.*;
import technologium.TVars;
import technologium.entities.*;
import technologium.graphics.TPal;
import technologium.type.unit.*;
import mindustry.content.*;
import arc.graphics.*;
import technologium.world.TMusic;

public class TUnitTypes {

    public static UnitType

    // names are ready, units itself aren't
    
    // core units
    quant, lonter, glider, prosecutor, reaper, predator,

    // kudol - sniper
    cobra, python, adder, hail, drencher, thunder, railgun,

    // kudol - air
    mercury, mars, neptune, centauri, phoenix, andromeda, starship,

    // kudol - legs
    blade, saber, impaler, secateurs, trident, incineration, destruction,

    //special
    metalstrong;
    public static void load() {

        //region core units

        quant = new KudolUnitType("quant") {{
            aiController = BuilderAI::new;
            constructor = MechUnit::create;
            isEnemy = false;
            createScorch = false;
            canAttack = false;
            mechSideSway = 0.3f;
            mechStepParticles = true;
            health = 180;
            armor = 1;
            hitSize = 10f;
            itemCapacity = 15;
            drag = 0.09f;
            speed = 0.5f;
            accel = 0.12f;
            flying = false;
            canBoost = true;
            boostMultiplier = 4f;
            fogRadius = 0f;
            engineOffset = 5f;
            buildSpeed = 1.2f;
            buildRange = 100f;
            mineWalls = true;
            mineFloor = true;
            mineSpeed = 6f;
            mineTier = 1;
            mineRange = 42.5f;
            coreUnitDock = true;
            weapons.add(new RepairBeamWeapon() {{
                reload = 20f;
                x = 0f; 
                y = 2f;
                showStatSprite = false;
                shootY = 0;
                reload = 20f;
                rotate = false;
                beamWidth = 0.7f;
                aimDst = 0.5f;
                widthSinMag = 0.11f;
                shootCone = 15f;
                mirror = false;
                repairSpeed = 3f;
                fractionRepairSpeed = 0.04f;
                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = TPal.acid3;
                healColor = TPal.acid3;
                bullet = new BulletType() {{
                    maxRange = 40f;
                }};
            }});
        }};

        lonter = new KudolUnitType("lonter") {{
            constructor = UnitEntity::create;
            isEnemy = false;
            createScorch = false;
            health = 270;
            armor = 2;
            hitSize = 16f;
            itemCapacity = 40;
            drag = 0.15f;
            speed = 3.8f;
            accel = 0.18f;
            aiController = BuilderAI::new;
            flying = true;
            engineOffset = 7f;
            buildSpeed = 1.6f;
            buildRange = 350f;
            mineWalls = true;
            mineFloor = true;
            mineSpeed = 10f;
            mineTier = 2;
            coreUnitDock = true;
            weapons.add(new RepairBeamWeapon("t-lonter-weapon-l"){{
                x = -5f;
                y = -2f;
                rotate = true;
                rotateSpeed = 8f;
                shootSound = Sounds.laserbeam;
                mirror = false;
                continuous = true;
                alwaysContinuous = true;
                controllable = false;
                autoTarget = true;
                targetUnits = true;
                targetBuildings = true;
                repairSpeed = 2.5f;
                fractionRepairSpeed = 0.0375f;
                beamWidth = 0.9f;
                aimDst = 1f;
                bullet = new BulletType(){{
                    maxRange = 75f;
                }};
            }},
            new RepairBeamWeapon("t-lonter-weapon-r"){{
                x = 5f;
                y = -2f;
                rotate = true;
                rotateSpeed = 8f;
                shootSound = Sounds.laserbeam;
                mirror = false;
                continuous = true;
                alwaysContinuous = true;
                controllable = false;
                autoTarget = true;
                targetUnits = true;
                targetBuildings = true;
                repairSpeed = 2.5f;
                fractionRepairSpeed = 0.0375f;
                beamWidth = 0.9f;
                aimDst = 1f;
                noAttack = false;
                bullet = new BulletType(){{
                    maxRange = 75f;
                }};
            }});
        }};

        //endregion

        //region kudol - laser

        blade = new KudolUnitType("blade") {{
            constructor = MechUnit::create;
            speed = 0.7f;
            health = 265;
            hitSize = 12f;
            aiController = GroundAI::new;
            rotateSpeed = 3f;
            mechSideSway = 0.2f;
            mineSpeed = 0f;
            flying = false;
            canBoost = true;
            engineSize = 3.5f;
            engineOffset = 7f;
            itemCapacity = 50;
            weapons.add(new Weapon("t-blade-weapon") {{
                x = y = 0f;
                shootY = 6.5f;
                showStatSprite = false;
                recoil = 0f;
                rotate = false;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 10f;
                    length = 50f;
                    width = 5f;
                    shake = 0.2f;
                    healPercent = 0.05f;
                    collidesTeam = true;
                    colors = new Color[]{TPal.gold3.cpy().a(.2f), TPal.gold2.cpy().a(.5f), TPal.gold1.cpy().a(1.2f), Color.white};
                }};
                shootStatus = StatusEffects.slow;
                shootStatusDuration = 1f;
            }});
            immunities.add(StatusEffects.burning);
        }};

        saber = new KudolUnitType("saber") {{
            constructor = MechUnit::create;
            rotateSpeed = 2f;
            speed = 0.8f;
            health = 540;
            hitSize = 18f;
            aiController = GroundAI::new;
            mechSideSway = 0.2f;
            mineSpeed = 0f;
            flying = false;
            canBoost = true;
            engineSize = 0f;
            setEnginesMirror(new UnitEngine(-3.5f, -7f, 3.5f, -135f));
            itemCapacity = 70;
            immunities.add(StatusEffects.burning);
            weapons.add(
            new Weapon("t-saber-weapon-l") {{
                x = -5f;
                y = 0f;
                shootY = 10.5f;
                recoil = 2f;
                shootCone = 45f;
                recoilTime = 40f;
                rotate = true;
                rotateSpeed = 0.5f;
                rotationLimit = 90;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 25f;
                    length = 60f;
                    width = 4f;
                    shake = 0.2f;
                    healPercent = 0.4f;
                    collidesTeam = true;
                    colors = new Color[]{TPal.gold3.cpy().a(.2f), TPal.gold2.cpy().a(.5f), TPal.gold1.cpy().a(1.2f), Color.white};
                }};
                shootStatus = StatusEffects.slow;
                shootStatusDuration = 1f;
            }},
            new Weapon("t-saber-weapon-r") {{
                x = 5f;
                y = 0f;
                shootY = 10.5f;
                recoil = 2f;
                shootCone = 45f;
                recoilTime = 40f;
                rotate = true;
                rotateSpeed = 0.5f;
                rotationLimit = 90;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 3f;
                    length = 60f;
                    width = 4f;
                    shake = 0.2f;
                    healPercent = 0.4f;
                    collidesTeam = true;
                    colors = new Color[]{TPal.gold3.cpy().a(.2f), TPal.gold2.cpy().a(.5f), TPal.gold1.cpy().a(1.2f), Color.white};
                }};
                shootStatus = StatusEffects.slow;
                shootStatusDuration = 1f;
            }});
        }};

        //endregion

        //region kudol - sniper

        cobra = new KudolUnitType("cobra") {{
            constructor = MechUnit::create;
            speed = 0.8f;
            health = 220;
            hitSize = 12f;
            aiController = GroundAI::new;
            mineSpeed = 0f;
            flying = false;
            canBoost = false;
            buildSpeed = 0;
            itemCapacity = 30;
            faceTarget = false;
            weapons.add(new Weapon("t-sniper-gun") {{
                x = y = 0f;
                shootY = 2f;
                rotate = true;
                rotateSpeed = 1f;
                reload = 120f;
                recoil = 2f;
                shootSound = Sounds.shootAlt;
                mirror = false;
                bullet = new BasicBulletType() {{
                    damage = 30f;
                    speed = 5f;
                    lifetime = 60f;
                    frontColor = TPal.gold3;
                    backColor = TPal.gold1;
                }};
            }});
        }};
        //endregion

        //region kudol - air

        mercury = new KudolUnitType("mercury") {{
            constructor = UnitEntity::create;
            speed = 3.5f;
            drag = 0.15f;
            accel = 0.1f;
            health = 190;
            hitSize = 14f;
            aiController = FlyingAI::new;
            mineSpeed = 0f;
            flying = true;
            buildSpeed = 0;
            itemCapacity = 40;
            engineOffset = 7f;
            setEnginesMirror(new UnitEngine(-3.5f, -7f, 1.75f, -135f));
            engineSize = 3f;
            aimDst = 10f;
            weapons.add(new Weapon("t-mercury-weapon") {{
                x = 0;
                y = 5f;
                layerOffset = -0.01f;
                top = false;
                shootY = 2f;
                recoil = 1.5f;
                rotate = false;
                shootSound = Sounds.blaster;
                mirror = false;
                reload = 30f;
                bullet = new BasicBulletType() {{
                    sprite = "circle-bullet";
                    damage = 8f;
                    speed = 3f;
                    lifetime = 25f;
                    frontColor = TPal.gold3;
                    backColor = hitColor = trailColor = TPal.gold1;
                }};
            }});
        }};

        //region special (cringe)
        metalstrong = new KudolUnitType("metalstrong"){{
            constructor = MechUnit::create;
            hidden = !TVars.debug;
            speed = 1f;
            health = 500000;
            hitSize = 16f;
            mineSpeed = 0f;
            flying = false;
            canBoost = true;
            buildSpeed = 0;
            itemCapacity = 1;
            createScorch = true;
            isEnemy = true;
            range = 24f;
            weapons.add(new Weapon("t-metalstrong-arm"){{
                x = 8f;
                y = 2f;
                recoil = -5f;
                reload = 3f;
                mirror = true;
                alternate = true;
                rotate = false;
                shootSound = Sounds.none;
                ejectEffect = Fx.none;
                bullet = new BasicBulletType(){{
                    damage = 100f;
                    instantDisappear = true;
                    lifetime = 1f;
                    shootEffect = hitEffect = despawnEffect = Fx.none;
                }};
            }});
            abilities.add(new MusicAbility(){{
                music = TMusic.metalstrong;
                end = 150;
            }});
        }};
    }
}
