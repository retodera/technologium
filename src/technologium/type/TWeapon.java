package technologium.type;

import mindustry.entities.part.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.graphics.*;
import technologium.entities.part.TRegionPart;

/**draws parts but it supplies the unit so the parts could draw cell regions */
public class TWeapon extends Weapon {

    public TWeapon(String name){
        this.name = name;
    }

    public TWeapon(){
        this("");
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        float z = Draw.z();
        Draw.z(z + layerOffset);

        float
        rotation = unit.rotation - 90,
        realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
        weaponRotation  = rotation + (rotate ? mount.rotation : baseRotation),
        wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
        wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

        if(shadow > 0) Drawf.shadow(wx, wy, shadow);
        if(top) drawOutline(unit, mount);
        if(parts.size > 0){
            DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
            DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(part.under){
                    unit.type.applyColor(unit);
                    if(part instanceof TRegionPart) ((TRegionPart)part).draw(DrawPart.params, unit);
                    else part.draw(DrawPart.params);
                }
            }
        }

        float prev = Draw.xscl;
        Draw.xscl *= -Mathf.sign(flipSprite);

        unit.type.applyColor(unit);
        if(region.found()) Draw.rect(region, wx, wy, weaponRotation);
        if(cellRegion.found()){
            Draw.color(unit.type.cellColor(unit));
            Draw.rect(cellRegion, wx, wy, weaponRotation);
            Draw.color();
        }
        if(heatRegion.found() && mount.heat > 0){
            Draw.color(heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(heatRegion, wx, wy, weaponRotation);
            Draw.blend();
            Draw.color();
        }

        Draw.xscl = prev;
        if(parts.size > 0){
            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(!part.under){
                    unit.type.applyColor(unit);
                    if(part instanceof TRegionPart) ((TRegionPart)part).draw(DrawPart.params, unit);
                    else part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = 1f;
        Draw.z(z);
    }
}
