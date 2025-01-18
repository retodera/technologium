package technologium.content;

import mindustry.ai.types.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.*;
import technologium.entities.*;
import technologium.graphics.TPal;
import technologium.world.*;
import technologium.type.unit.*;
import mindustry.content.*;
import arc.graphics.*;

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
            hitSize = 12f;
            flying = false;
            itemCapacity = 15;
            drag = 0.15f;
            speed = 0.5f;
            canBoost = true;
            boostMultiplier = 1.5f;
            engineOffset = 7f;
            buildSpeed = 1.2f;
            buildRange = 200f;
            mineWalls = true;
            mineFloor = true;
            mineSpeed = 2f;
            mineTier = 1;
            coreUnitDock = true;
            weapons.add(new RepairBeamWeapon() {{
                reload = 20f;
                x = y = 0f;
                rotate = false;
                shootY = 2;
                beamWidth = 0.7f;
                aimDst = 0f;
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

        //endregion

        //region kudol - laser

        blade = new KudolUnitType("blade") {{
            constructor = MechUnit::create;
            speed = 0.6f;
            health = 265;
            hitSize = 12f;
            aiController = GroundAI::new;
            mineSpeed = 0f;
            flying = false;
            canBoost = true;
            engineOffset = 7f;
            buildSpeed = 0.5f;
            buildRange = 150f;
            itemCapacity = 50;
            weapons.add(new Weapon() {{
                x = y = 0f;
                shootY = 10f;
                recoil = 0f;
                rotate = false;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 2f;
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
                    damage = 15f;
                    speed = 15f;
                    lifetime = 20f;
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
            hitSize = 10f;
            aiController = FlyingAI::new;
            mineSpeed = 0f;
            flying = true;
            buildSpeed = 0;
            itemCapacity = 40;
            engineOffset = 4.5f;
            engineSize = 2.5f;
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
                    sprite = "t-sphere-bullet";
                    damage = 4f;
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
                    speed = 0f;
                    lifetime = 1f;
                    shootEffect = hitEffect = despawnEffect = Fx.none;
                }};
            }});
            abilities.add(new StrongAbility());
        }};
    }
}
