package technologium.type.unit;

import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import technologium.content.TItems;
import technologium.graphics.TPal;
import mindustry.world.meta.Env;

public class KudolUnitType extends UnitType {
    public KudolUnitType(String name) {
        super(name);
        outlineColor = TPal.darkerOutline;
        envDisabled = Env.none;
        ammoType = new ItemAmmoType(TItems.hematite);
        researchCostMultiplier = 8;
        isEnemy = true;
        createScorch = true;
        canAttack = true;
    }
}
