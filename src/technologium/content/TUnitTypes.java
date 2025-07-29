package technologium.content;

import mindustry.ai.types.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.BlockFlag;
import mindustry.content.*;
import mindustry.entities.part.*;
import mindustry.entities.part.DrawPart.*;

import technologium.type.unit.*;
import arc.graphics.*;
import arc.math.*;

import static mindustry.ai.UnitCommand.*;
import static technologium.graphics.TPal.*;

public class TUnitTypes {

    public static UnitType
    
    // core units
    quant, lonter, ladon,

    // kudol - sniper
    cobra, python, impaler,

    // kudol - air assault
    mercury, mars, phobos,

    // kudol - legs
    blade, saber, napalm,

    // special
    monopoly, flarerouterlegs;
    
    public static void load() {

        // region core units

        quant = new KudolUnitType("quant") {{
            aiController = BuilderAI::new;
            constructor = MechUnit::create;
            isEnemy = createScorch = targetable = hittable = canAttack = false;
            health = 180;
            armor = 1;

            flying = false;
            canBoost = true;
            engineOffset = 5f;
            boostMultiplier = 4f;
            mechSideSway = 0.3f;
            mechStepParticles = true;
            drag = 0.15f;
            speed = 0.5f;
            accel = 0.12f;
            strafePenalty = 0.75f;

            buildSpeed = 1.2f;
            buildRange = 100f;
            coreUnitDock = true;

            hitSize = 10f;
            fogRadius = 0f;
            itemCapacity = 25;

            mineSpeed = 8f;
            mineRange = 42.5f;
            mineTier = 1;
            mineWalls = true;
            mineFloor = true;

            weapons.add(new RepairBeamWeapon() {{
                x = 0f; 
                y = 2f;
                mirror = false;
                showStatSprite = false;

                shootY = 0;
                reload = 20f;
                rotate = false;

                aimDst = 0.5f;
                widthSinMag = 0.11f;
                shootCone = 15f;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;

                reload = 20f;
                repairSpeed = 3f;
                laserColor = acid3;
                healColor = acid3;
                beamWidth = 0.7f;
                bullet = new BulletType() {{
                    maxRange = 40f;
                }};
            }});
        }};

        lonter = new KudolUnitType("lonter") {{
            constructor = UnitEntity::create;
            aiController = BuilderAI::new;
            isEnemy = createScorch = targetable = hittable = canAttack = false;
            health = 270;
            armor = 2;

            flying = true;
            engineSize = 0;
            setEnginesMirror(new UnitEngine(6.2f, -6.2f, 2.5f, -45), new UnitEngine(6.2f, 6.2f, 2.5f, 45));

            buildSpeed = 1.6f;
            buildRange = 350f;
            coreUnitDock = true;

            drag = 0.07f;
            speed = 4f;
            rotateSpeed = 7.5f;
            accel = 0.18f;
            strafePenalty = 1;

            fogRadius = 0f;
            hitSize = 16f;
            itemCapacity = 40;

            mineSpeed = 14f;
            mineTier = 2;
            mineWalls = true;
            mineFloor = true;

            weapons.add(new RepairBeamWeapon("t-lonter-weapon"){{
                x = 5f;
                y = -2f;
                mirror = alternate = true;

                rotate = true;
                rotateSpeed = 8f;

                shootSound = Sounds.laserbeam;
                continuous = alwaysContinuous = true;
                controllable = false;
                autoTarget = true;
                targetUnits = targetBuildings = true;

                repairSpeed = 0.5f;
                beamWidth = 0.9f;
                bullet = new BulletType(){{
                    maxRange = 75f;
                }};
            }});
        }};

        // endregion

        // region kudol - laser

        blade = new KudolUnitType("blade") {{
            constructor = MechUnit::create;
            speed = 0.7f;
            health = 320;
            armor = 5;
            hitSize = 12f;
            aiController = GroundAI::new;
            rotateSpeed = 3f;
            mechSideSway = 0.2f;
            mineSpeed = 0f;
            flying = false;
            canBoost = false;
            itemCapacity = 50;
            weapons.add(new Weapon("t-blade-weapon") {{
                x = y = 0f;
                shootY = 7.5f;
                showStatSprite = false;
                recoil = 0f;
                rotate = false;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 5f;
                    length = 50f;
                    width = 5f;
                    shake = 0.2f;
                    healPercent = 0.05f;
                    collidesTeam = true;
                    colors = new Color[]{gold3.cpy().a(.2f), gold2.cpy().a(.5f), gold1.cpy().a(1.2f), Color.white};
                }};
                shootStatus = StatusEffects.slow;
                shootStatusDuration = 1f;
            }});
            immunities.add(StatusEffects.burning);
        }};

        saber = new KudolUnitType("saber") {{
            constructor = MechUnit::create;
            aiController = GroundAI::new;
            health = 740;
            armor = 10;
            hitSize = 14f;

            speed = 0.5f;
            rotateSpeed = 2f;
            mechSideSway = 0.2f;

            itemCapacity = 70;
            mineSpeed = 0f;
            
            immunities.add(StatusEffects.burning);
            for(int i : Mathf.signs) {
                weapons.add(
                new Weapon("t-saber-weapon-" + (i == 1 ? "r" : "l")) {{
                    x = 6.25f * i;
                    y = 0f;
                    shootY = 12f;
                    mirror = top = false;

                    recoil = 2f;
                    recoilTime = 40f;

                    rotate = true;
                    rotateSpeed = 0.5f;
                    rotationLimit = 30;

                    shootCone = 15f;
                    shootSound = Sounds.laserbeam;
                    continuous = alwaysContinuous = true;

                    bullet = new ContinuousLaserBulletType() {{
                        damage = 4f;
                        length = 60f;
                        width = 4f;
                        shake = 0.2f;
                        healPercent = 0.1f;
                        collidesTeam = true;
                        colors = new Color[]{gold3.cpy().a(.2f), gold2.cpy().a(.5f), gold1.cpy().a(1.2f), Color.white};
                    }};
                    shootStatus = StatusEffects.slow;
                    shootStatusDuration = 1f;
                }});
            }
        }};

        napalm = new KudolUnitType("napalm") {{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;
            hovering = true;
            health = 1750;
            armor = 20;
            hitSize = 16f;
            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit;

            speed = 0.350f;
            rotateSpeed = 2f;

            legCount = 6;
            legGroupSize = 3;
            legBaseOffset = 2;
            legLength = 18;
            stepShake = 0.5f;
            legForwardScl = 0.3f;

            itemCapacity = 0;
            mineSpeed = 0f;

            range = 265;
            immunities.add(StatusEffects.burning);
            weapons.add(new Weapon("t-napalm-weapon"){{
                x = y = 0;
                shootY = 8;
                showStatSprite = false;
                mirror = false;
                continuous = true;
                parentizeEffects = true;

                shoot.firstShotDelay = 180;
                reload = 720;
                recoilTime = 360;
                cooldownTime = 360;
                
                bullet = new ContinuousLaserBulletType(15){{
                    chargeEffect = TFx.longLaserCharge;
                    healPercent = 0.4f;
                    length = 250;
                    width = 9;
                    lifetime = 240;
                    colors = new Color[] {gold3.cpy().a(0.5f), gold3.cpy().mul(1.25f).a(0.75f), gold3.cpy().mul(1.5f)};
                }};

                PartProgress charge1 = PartProgress.charge.curve(Interp.pow3In).add(PartProgress.recoil),
                    charge2 = PartProgress.charge.mul(2).add(-1).clamp().curve(Interp.pow3).add(PartProgress.recoil);
                
                parts.add(
                    new RegionPart("-bodyblade"){{
                        progress = charge1;
                        moveX = 1;
                        moveY = -1;
                        moveRot = -10;
                        mirror = true;
                    }},
                    new RegionPart("-blade"){{
                        progress = charge1;
                        moveX = 1;
                        moveY = -1;
                        moveRot = -10;
                        mirror = true;
                        under = true;
                        moves.add(new PartMove(charge2, -1, 7, -45));
                    }},
                    new RegionPart("-holder"){{
                        progress = charge1;
                        moveX = 1;
                        moveY = -1;
                        moveRot = -10;
                        mirror = true;
                        under = true;
                        moves.add(new PartMove(charge2, 2, -5, 27.5f));
                    }}
                );
                shootStatus = TStatusEffects.veryslow;
                shootStatusDuration = 420;
            }});
        }};

        // endregion

        // region kudol - sniper

        cobra = new KudolUnitType("cobra") {{
            constructor = MechUnit::create;
            aiController = GroundAI::new;
            flying = false;
            canBoost = false;
            health = 280;
            hitSize = 12f;
            armor = 4;

            speed = 0.8f;
            
            itemCapacity = 30;
            buildSpeed = 0;
            mineSpeed = 0f;

            faceTarget = false;
            weapons.add(new Weapon("t-sniper-gun") {{
                x = y = 0f;
                shootY = 5f;
                mirror = false;

                rotate = true;
                rotateSpeed = 1f;

                recoil = 2f;
                reload = 120f;
                cooldownTime = 90f;
                
                shootSound = Sounds.shootAlt;
                bullet = new ArtilleryBulletType(4.5f, 30) {{
                    lifetime = 40f;
                    frontColor = gold3;
                    backColor = gold1;
                    collidesTiles = false;
                }};
            }});
        }};

        python = new KudolUnitType("python") {{
            constructor = MechUnit::create;
            health = 710;
            armor = 7;
            hitSize = 16f;

            aiController = GroundAI::new;
            speed = 0.6f;

            itemCapacity = 50;
            mineSpeed = 0f;
            buildSpeed = 0;

            faceTarget = false;
            targetAir = false;
            weapons.add(new Weapon("t-python-gun") {{
                x = 5f;
                y = -2f;
                shootY = 4f;
                mirror = alternate = true;

                rotate = true;
                rotateSpeed = 0.8f;
                rotationLimit = 130;

                reload = cooldownTime = 90f;
                recoil = 2f;
                shootSound = Sounds.artillery;
                bullet = new ArtilleryBulletType(3, 45) {{
                    height = 11;
                    width = 7f;
                    splashDamage = 20f;
                    splashDamageRadius = 25f;
                    lifetime = 85f;
                    frontColor = gold3;
                    backColor = hitColor = gold2;
                    despawnEffect = hitEffect = Fx.explosion;
                }};
            }});
        }};

        // endregion

        // region kudol - air

        mercury = new KudolUnitType("mercury") {{
            constructor = UnitEntity::create;
            aiController = FlyingAI::new;
            flying = true;
            health = 250;
            armor = 2;
            hitSize = 10f;

            speed = 3.5f;
            drag = 0.15f;
            accel = 0.1f;

            itemCapacity = 30;
            mineSpeed = 0f;
            buildSpeed = 0;
            
            engineSize = 0f;
            setEnginesMirror(new UnitEngine(-3f, -7f, 2f, -90f));

            aimDst = 10f;
            weapons.add(new Weapon("t-mercury-weapon") {{
                x = 0;
                y = 4.5f;
                shootY = 0f;
                top = false;
                mirror = false;

                rotate = false;

                recoil = 1.5f;
                reload = 30f;
                cooldownTime = 20f;

                shootSound = Sounds.blaster;
                bullet = new BasicBulletType(3, 8, "circle-bullet") {{
                    lifetime = 25f;
                    frontColor = gold3;
                    backColor = hitColor = trailColor = gold2;
                    shootEffect = Fx.shootBig;
                }};
            }});
        }};

        mars = new KudolUnitType("mars") {{
            constructor = UnitEntity::create;
            aiController = FlyingAI::new;
            flying = true;
            health = 650;
            armor = 5;
            hitSize = 16f;

            speed = 3f;
            drag = 0.15f;
            accel = 0.09f;

            itemCapacity = 50;
            mineSpeed = 0f;
  
            engineOffset = 8f;
            engineSize = 4f;

            aimDst = 24f;
            weapons.add(new PointDefenseWeapon("t-air-assault-defense") {{
                x = 6f;
                y = -2f;
                reload = 7f;
                targetInterval = 7f;
                targetSwitchInterval = 10f;
                bullet = new BulletType() {{
                    shootSound = Sounds.lasershoot;
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 90f;
                    damage = 40f;
                }};
            }},
            new Weapon("t-mars-minigun") {{
                x = 0;
                y = 6f;
                shootY = 0.5f;
                layerOffset = -0.01f;
                mirror = false;
                top = false;
                
                rotate = false;
                
                recoil = 1.5f;
                reload = 60f;

                shootSound = Sounds.shootAltLong;
                shoot = new ShootBarrel() {{
                    barrels = new float[] {
                        -1.75f, 0f, 0,
                        0, 0f, 0,
                        1.75f, 0f, 0  
                    };
                    shots = 8;
                    shotDelay = 5f;
                }};
                bullet = new BasicBulletType(4,10) {{
                    lifetime = 20f;
                    frontColor = orange3;
                    backColor = hitColor = trailColor = orange2;
                    hitEffect = despawnEffect = Fx.blastExplosion;
                }};
            }});
        }};

        phobos = new KudolUnitType("phobos") {{
            constructor = UnitEntity::create;
            aiController = FlyingAI::new;
            flying = true;
            lowAltitude = true;
            health = 1060;
            armor = 80;
            hitSize = 18f;

            speed = 2f;
            rotateSpeed = 3.5f;
            drag = 0.15f;
            accel = 0.075f;

            itemCapacity = 80;
            mineSpeed = 0f;
  
            engineSize = 0f;
            setEnginesMirror(new UnitEngine(8, -14, 3, -45), new UnitEngine(12.5f, -8.5f, 3, -45));

            aimDst = 24f;
            range = 225;
            weapons.add(new PointDefenseWeapon("t-air-assault-defense") {{
                x = 7f;
                y = 4.25f;
                reload = 7f;
                targetInterval = 7f;
                targetSwitchInterval = 10f;
                bullet = new BulletType() {{
                    shootSound = Sounds.lasershoot;
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 90f;
                    damage = 40f;
                }};
            }},
            new PointDefenseWeapon("t-air-assault-defense") {{
                x = 6.5f;
                y = -11f;
                reload = 7f;
                targetInterval = 7f;
                targetSwitchInterval = 10f;
                bullet = new BulletType() {{
                    shootSound = Sounds.lasershoot;
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 90f;
                    damage = 40f;
                }};
            }},
            new Weapon("t-air-assault-comet") {{
                x = 7f;
                y = -2f;
                parentizeEffects = true;

                rotate = true;
                rotateSpeed = 4.5f;

                reload = cooldownTime = 60;
                shootSound = Sounds.blaster;
                bullet = new LaserBulletType(24) {{
                    buildingDamageMultiplier = 0.25f;
                    length = 80f;
                    colors = new Color[] { purple1, purple2, purple3 };
                }};
            }},
            new Weapon("t-phobos-rockets") {{
                x = y = 0;
                shootY = 6f;
                layerOffset = -0.01f;
                mirror = false;
                top = false;
                parentizeEffects = true;
                
                rotate = false;
                
                recoil = 0;
                recoils = 3;
                reload = 150f;
                recoilTime = 60f;

                shootSound = Sounds.missile;
                shoot = new ShootBarrel() {{
                    barrels = new float[] {
                        -2.25f, 1, 0,
                        0, 0, 0,
                        2.25f, 1, 0  
                    };
                    shots = 15;
                    shotDelay = 5f;
                }};
                bullet = new MissileBulletType(3, 10) {{
                    keepVelocity = false;
                    lifetime = 75f;
                    frontColor = hitColor = trailColor = orange3;
                    backColor = orange2;
                    trailChance = 100;
                    shootEffect = Fx.colorSparkBig;
                    hitEffect = despawnEffect = TFx.hitSparkColor;
                    homingPower = 0.04f;
                    weaveScale = 10;
                    weaveMag = 1;
                }};
                parts.add(new RegionPart("-mid"){{
                    recoilIndex = 1;
                    progress = heatProgress = PartProgress.recoil;
                    moveY = -2;
                    layerOffset = -0.01f;
                }});
                for(int i : Mathf.signs) {
                    parts.add(new RegionPart(i == 1 ? "-r" : "-l"){{
                        recoilIndex = i == 1 ? 2 : 0;
                        progress = heatProgress = PartProgress.recoil;
                        moveX = 1 * i;
                        moveY = -2;
                        moveRot = 7.5f * i;
                        layerOffset = -0.01f;
                    }});
                }
            }});
        }};

        // endregion

        // region special
    
        monopoly = new UnitType("monopoly") {{
            constructor = UnitEntity::create;
            defaultCommand = rebuildCommand;
            flying = true;
            isEnemy = false;
            lowAltitude = true;
            health = 600;
            hitSize = 12f;

            speed = 3f;
            drag = 0.04f;
            accel = 0.1f;
            rotateSpeed = 12f;
            
            itemCapacity = 50;
            buildSpeed = 0.8f;
            mineTier = 2;
            mineSpeed = 5f;

            engineOffset = 9.5f;
            
            range = 200f;
            ammoType = new PowerAmmoType(900);
            abilities.add(new RepairFieldAbility(8f, 60f * 5, 70f));
            weapons.add(new Weapon("poly-weapon"){{
                y = -5.5f;
                x = 3.75f;
                top = false;
                mirror = alternate = true;
                
                recoil = 2f;
                reload = 15f;
                inaccuracy = 10f;

                shootSound = Sounds.missile;
                ejectEffect = Fx.none;
                velocityRnd = 0.5f;
                
                bullet = new MissileBulletType(4f, 20){{
                    homingPower = 0.2f;
                    weaveMag = 4;
                    weaveScale = 4;
                    lifetime = 100f;
                    keepVelocity = false;
                    shootEffect = Fx.shootHeal;
                    smokeEffect = Fx.hitLaser;
                    hitEffect = despawnEffect = Fx.hitLaser;
                    frontColor = Color.white;
                    hitSound = Sounds.none;

                    healPercent = 8f;
                    collidesTeam = true;
                    reflectable = false;
                    backColor = trailColor = Pal.heal;
                }};
            }});
        }};

        flarerouterlegs = new UnitType("flarerouterlegs") {{
            constructor = LegsUnit::create;
            aiController = GroundAI::new;
            hovering = true;
            health = 70;
            hitSize = 9;
            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit;
            drawCell = false;

            speed = 1f;
            accel = 0.08f;
            drag = 0.04f;

            legCount = 4;
            legLength = 10;
            legForwardScl = 1f;

            itemCapacity = 10;

            targetFlags = new BlockFlag[]{BlockFlag.generator, null};
            weapons.add(new Weapon(){{
                y = 0f;
                x = 2f;
                reload = 20f;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 9){{
                    width = 7f;
                    height = 9f;
                    lifetime = 45f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    ammoMultiplier = 2;
                }};
                shootSound = Sounds.pew;
            }});
        }};

        // endregion

    }
}
