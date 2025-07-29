package technologium.entities.unit.impl;

import arc.struct.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.logic.LAccess;
import technologium.entities.unit.*;

import java.util.HashMap;

/**class for unit modifications
 * <div align="center" style="
 *      color:'#7f7fff';
 *      background-color:'#1f001f';
 *      text-align:'center';
 *      height:'40';
 *      width:'300';
 *      padding:'20';
 * ">should and will be replaced with code generation</div>
 * @author nadocd
 */
public class MechUnitM extends MechUnit implements UnitModification {
    //region mod impl
    public HashMap<ModifierType, FloatSeq> modifiers = new HashMap<>(ModifierType.total());
    @Override
    public HashMap<ModifierType, FloatSeq> getModifiers() {
        return modifiers;
    }

    @Override
    public String toString() {
        return super.toString()+"/modifiers:"+modifiers;
    }

    @Override
    public int classId() {
        return Integer.MAX_VALUE-super.classId();
    }

    @Override
    public void read(Reads read) {
        super.read(read);
        modifiers=UnitModification.readModifiers(read);
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        UnitModification.writeModifiers(write,modifiers);
    }

    // implemented
    @Override
    public void damage(float amount) {
        super.damage(amount/this.<Float>compute(ModifierType.toughness));
    }

    // implemented
    @Override
    public void damage(float amount, boolean withEffect) {
        super.damage(amount/this.<Float>compute(ModifierType.toughness), withEffect);
    }

    // implemented
    @Override
    public void damageContinuous(float amount) {
        super.damageContinuous(amount/this.<Float>compute(ModifierType.toughness));
    }

    // implemented
    @Override
    public void damageContinuousPierce(float amount) {
        super.damageContinuousPierce(amount/this.<Float>compute(ModifierType.toughness));
    }

    // implemented
    @Override
    public void damagePierce(float amount) {
        super.damagePierce(amount/this.<Float>compute(ModifierType.toughness));
    }

    // implemented
    @Override
    public void damagePierce(float amount, boolean withEffect) {
        super.damagePierce(amount/this.<Float>compute(ModifierType.toughness), withEffect);
    }

    // implemented
    @Override
    public float armor() {
        return super.armor()*this.<Float>compute(ModifierType.armor);
    }

    // implemented
    @Override
    public float armorOverride() {
        return super.armorOverride()*this.<Float>compute(ModifierType.armor);
    }

    // implemented
    @Override
    public float damageMultiplier() {
        return super.damageMultiplier()*this.<Float>compute(ModifierType.damage);
    }

    // implemented
    @Override
    public float maxHealth() {
        return super.maxHealth()*this.<Float>compute(ModifierType.maxHealth);
    }

    // implemented
    @Override
    public float reloadMultiplier() {
        return super.reloadMultiplier()*this.<Float>compute(ModifierType.reload);
    }

    // implemented
    @Override
    public float speedMultiplier() {
        return super.speedMultiplier()*this.<Float>compute(ModifierType.speed);
    }

    // implemented
    @Override
    public boolean canDrown() {
        return super.canDrown()&&!this.<Boolean>compute(ModifierType.noDrowning);
    }

    // implemented
    @Override
    public boolean canBuild() {
        return super.canBuild()||this.<Boolean>compute(ModifierType.forceCanBuild);
    }

    // implemented
    @Override
    public float buildSpeedMultiplier() {
        return super.canBuild()?
                super.buildSpeedMultiplier():
                this.<Boolean>compute(ModifierType.forceCanBuild)?
                        0.5f:
                        0f;
    }

    // why? i don't know...
    @Override
    public double sense(LAccess la) {
        //noinspection SwitchStatementWithTooFewBranches i do what i want, //rm -rf technologium
        return switch (la) {
            case config -> {
                final int[] modifierCount ={0} ;
                getModifiers().forEach((mt,list)->{
                    // i hope there is no null pointers
                    modifierCount[0]+=list.size;
                });
                yield modifierCount[0];
            }
            default -> super.sense(la);
        };
    }
    //endregion
}
