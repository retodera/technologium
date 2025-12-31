package technologium.world.draw;

import arc.graphics.g2d.Draw;
import mindustry.entities.part.DrawPart;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import mindustry.world.draw.DrawTurret;
import technologium.entities.part.TDrawPart;
import technologium.entities.part.TRegionPart;

public class TDrawTurret extends DrawTurret {
    public TDrawTurret(String basePrefix) {
        super(basePrefix);
    }

    @Override
    public void draw(Building build){
        Turret turret = (Turret)build.block;
        TurretBuild tb = (TurretBuild)build;

        Draw.rect(base, build.x, build.y);
        Draw.color();

        Draw.z(shadowLayer);

        Drawf.shadow(preview, build.x + tb.recoilOffset.x - turret.elevation, build.y + tb.recoilOffset.y - turret.elevation, tb.drawrot());

        Draw.z(turretLayer);

        drawTurret(turret, tb);
        drawHeat(turret, tb);

        if(parts.size > 0){
            if(outline.found()){
                Draw.z(turretLayer - 0.01f);
                Draw.rect(outline, build.x + tb.recoilOffset.x, build.y + tb.recoilOffset.y, tb.drawrot());
                Draw.z(turretLayer);
            }

            float progress = tb.progress();

            var params = DrawPart.params.set(build.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, tb.x + tb.recoilOffset.x, tb.y + tb.recoilOffset.y, tb.rotation);
            var tparams = TDrawPart.params.set(build.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, tb.x + tb.recoilOffset.x, tb.y + tb.recoilOffset.y, tb.rotation, tb.curRecoils);
            if(tb.curRecoils == null) {
                float[] replace = new float[turret.recoils];
                for(int i = 0; i < turret.recoils; i++) replace[i] = 0;
                tparams.recoils = replace;
            }

            for(var part : parts){
                if(part instanceof TRegionPart tpart) {
                    tparams.setRecoil(tpart.recoilIndex >= 0 && tb.curRecoils != null && tpart.recoilIndex < tb.curRecoils.length ? tb.curRecoils[tpart.recoilIndex] : tb.curRecoil);
                    tpart.draw(tparams);
                }
                else {
                    params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null && part.recoilIndex < tb.curRecoils.length ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
                    part.draw(params);
                }
            }
        }
    }
}
