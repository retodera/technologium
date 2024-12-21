package technologium.content;

import mindustry.ai.types.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.*;
import technologium.graphics.TPal;
import technologium.world.*;
import technologium.type.unit.*;
import mindustry.content.*;
import arc.graphics.*;

public class TUnitTypes {

    public static UnitType

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
            mechSideSway = 0.3f;
            mechStepParticles = true;
            health = 80;
            armor = 1;
            hitSize = 12f;
            flying = false;
            itemCapacity = 15;
            drag = 0.15f;
            speed = 0.5f;
            canBoost = true;
            boostMultiplier = 1.5f;
            engineOffset = 9f;
            buildSpeed = 1.2f;
            buildRange = 200f;
            canAttack = false;
            mineWalls = true;
            mineFloor = true;
            mineSpeed = 1f;
            mineTier = 1;
            coreUnitDock = true;
            createScorch = false;
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
                    maxRange = 80f;
                }};
            }});
        }};

        //endregion

        //region kudol - laser

        blade = new KudolUnitType("blade") {{
            constructor = MechUnit::create;
            speed = 0.6f;
            health = 145;
            hitSize = 12f;
            createScorch = true;
            isEnemy = true;
            aiController = GroundAI::new;
            mineSpeed = 0f;
            flying = false;
            canBoost = true;
            engineOffset = 9f;
            buildSpeed = 0.5f;
            buildRange = 150f;
            itemCapacity = 50;
            weapons.add(new Weapon() {{
                x = y = 0f;
                shootY = 10f;
                recoil = 0f;
                shake = 0.25f;
                rotate = false;
                shootSound = Sounds.laserbeam;
                continuous = true;
                alwaysContinuous = true;
                mirror = false;
                bullet = new ContinuousLaserBulletType() {{
                    damage = 10f;
                    length = 50f;
                    width = 5f;
                    hitEffect = Fx.hitMeltHeal;
                    healPercent = 1f;
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
            health = 120;
            hitSize = 12f;
            aiController = GroundAI::new;
            isEnemy = true;
            mineSpeed = 0f;
            flying = false;
            canBoost = false;
            buildSpeed = 0;
            createScorch = true;
            itemCapacity = 30;
            faceTarget = false;
            weapons.add(new Weapon("t-sniper-gun") {{
                x = y = 0f;
                shootY = 2f;
                rotate = true;
                rotateSpeed = 1f;
                reload = 80f;
                recoil = 2f;
                shootSound = Sounds.shootAlt;
                mirror = false;
                bullet = new BasicBulletType() {{
                    damage = 20f;
                    speed = 30f;
                    lifetime = 10f;
                    frontColor = TPal.gold3;
                    backColor = TPal.gold1;
                }};
            }});
        }};
        //endregion

        //region special
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
                x = 16f;
                y = 2f;
                recoil = -5f;
                reload = 3f;
                mirror = true;
                alternate = true;
                rotate = false;
                shootSound = Sounds.shotgun;
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
