package technologium.entities.part;

import mindustry.entities.part.RegionPart;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import technologium.entities.part.TDrawPart.TPartParams;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import arc.math.Mathf;
import arc.*;

public class TRegionPart extends RegionPart {
    public TextureRegion cell;
    protected TPartParams tchildParam = new TPartParams();

    public TRegionPart(String region) {
        super(region);
    }

    public TRegionPart(String region, Blending blending, Color color){
        this.suffix = region;
        this.blending = blending;
        this.color = color;
        outline = false;
    }

    public TRegionPart(){
    }

    @Override
    public void draw(PartParams params) {
        draw(params, null);
    }

    public void draw(PartParams params, Unit unit) {
        draw(TDrawPart.params.set(params), unit);
    }

    public void draw(TPartParams params) {
        draw(params, null);
    }

    public void draw(TPartParams params, Unit unit){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(under && turretShading) Draw.z(z - 0.0001f);
        Draw.z(Draw.z() + layerOffset);

        float prevZ = Draw.z();
        float prog = progress.getClamp(params, clampProgress), sclProg = growProgress.getClamp(params, clampProgress);
        float mx = moveX * prog, my = moveY * prog, mr = moveRot * prog + rotation,
            gx = growX * sclProg, gy = growY * sclProg;


        if(moves.size > 0)
            for(int i = 0; i < moves.size; i++){
                var move = moves.get(i);
                float p = move.progress.getClamp(params, clampProgress);
                mx += move.x * p;
                my += move.y * p;
                mr += move.rot * p;
                gx += move.gx * p;
                gy += move.gy * p;
            }  

        int len = mirror && params.sideOverride == -1 ? 2 : 1;
        float preXscl = Draw.xscl, preYscl = Draw.yscl;
        Draw.xscl *= xScl + gx;
        Draw.yscl *= yScl + gy;

        for(int s = 0; s < len; s++){
            int i = params.sideOverride == -1 ? s : params.sideOverride;

            var region = drawRegion ? regions[Math.min(i, regions.length - 1)] : null;
            float sign = (i == 0 ? 1 : -1) * params.sideMultiplier;
            Tmp.v1.set((x + mx) * sign, y + my).rotateRadExact((params.rotation - 90) * Mathf.degRad);

            Draw.xscl *= sign;

            if(originX != 0f || originY != 0f)
                Tmp.v1.sub(Tmp.v2.set(-originX * Draw.xscl, -originY * Draw.yscl).rotate(params.rotation - 90f).add(originX * Draw.xscl, originY * Draw.yscl));

            float
                rx = params.x + Tmp.v1.x,
                ry = params.y + Tmp.v1.y,
                rot = mr * sign + params.rotation - 90;

            if(outline && drawRegion){
                Draw.z(prevZ + outlineLayerOffset);
                rect(outlines[Math.min(i, regions.length - 1)], rx, ry, rot);
                Draw.z(prevZ);
            }

            if(drawRegion && region.found()){
                if(color != null && colorTo != null) Draw.color(color, colorTo, prog);
                else if(color != null) Draw.color(color);
                
                if(mixColor != null && mixColorTo != null) Draw.mixcol(mixColor, mixColorTo, prog);
                else if(mixColor != null) Draw.mixcol(mixColor, mixColor.a);

                Draw.blend(blending);
                rect(region, rx, ry, rot);
                Draw.blend();
                if(color != null) Draw.color();
            }

            if(heat.found()){
                float hprog = heatProgress.getClamp(params, clampProgress);
                heatColor.write(Tmp.c1).a(hprog * heatColor.a);
                additive(heat, Tmp.c1, 1f, rx, ry, rot, originX, originY);
                if(heatLight) Drawf.light(rx, ry, light.found() ? light : heat, rot, Tmp.c1, heatLightOpacity * hprog);
            }

            if(cell.found() && unit != null) {
                Draw.color(unit.type.cellColor(unit));
                Draw.rect(cell, rx, ry, rot);
                Draw.color();
            }

            Draw.xscl *= sign;
        }

        Draw.color();
        Draw.mixcol();

        Draw.z(z);

        if(children.size > 0)
            for(int s = 0; s < len; s++){
                int i = (params.sideOverride == -1 ? s : params.sideOverride);
                float sign = (i == 1 ? -1 : 1) * params.sideMultiplier;
                Tmp.v1.set((x + mx) * sign, y + my).rotateRadExact((params.rotation - 90) * Mathf.degRad);

                childParam.set(params.warmup, params.reload, params.smoothReload, params.heat, params.recoil, params.charge, params.x + Tmp.v1.x, params.y + Tmp.v1.y, mr * sign + params.rotation);
                childParam.sideMultiplier = params.sideMultiplier;
                childParam.life = params.life;
                childParam.sideOverride = i;
                tchildParam.set(childParam);
                tchildParam.recoils = params.recoils;
                for(var child : children)
                    if(child instanceof TRegionPart tpart) {
                        if(tpart.recoilIndex >= 0 && tpart.recoilIndex < params.recoils.length) tchildParam.setRecoil(params.recoils[tpart.recoilIndex]);
                        tpart.draw(tchildParam);
                    }
                    else child.draw(childParam);
            }

        Draw.scl(preXscl, preYscl);
    }

    @SuppressWarnings("all")
    void rect(TextureRegion region, float x, float y, float rotation){
        float w = region.width * region.scl() * Draw.xscl, h = region.height * region.scl() * Draw.yscl;
        Draw.rect(region, x, y, w, h, w / 2f + originX * Draw.xscl, h / 2f + originY * Draw.yscl, rotation);
    }

    //without layers so heat regions won't draw over everything (seriously anuke, how did you mess this up)
    void additive(TextureRegion region, Color color, float alpha, float x, float y, float rotation, float originX, float originY){
        float w = region.width * region.scl() * Draw.xscl, h = region.height * region.scl() * Draw.yscl;
        Draw.color(color, alpha * color.a);
        Draw.blend(Blending.additive);
        Draw.rect(region, x, y, w, h, w / 2f + originX * region.scl() * Draw.xscl, h / 2f + originY * region.scl() * Draw.yscl, rotation);
        Draw.blend();
        Draw.color();
    }

    @Override
    public void load(String name){
        String realName = this.name == null ? name + suffix : this.name;

        if(drawRegion){
            if(mirror && turretShading){
                regions = new TextureRegion[]{
                Core.atlas.find(realName + "-r"),
                Core.atlas.find(realName + "-l")
                };

                outlines = new TextureRegion[]{
                Core.atlas.find(realName + "-r-outline"),
                Core.atlas.find(realName + "-l-outline")
                };
            }else{
                regions = new TextureRegion[]{Core.atlas.find(realName)};
                outlines = new TextureRegion[]{Core.atlas.find(realName + "-outline")};
            }
        }

        heat = Core.atlas.find(realName + "-heat");
        light = Core.atlas.find(realName + "-light");
        cell = Core.atlas.find(realName + "-cell");
        for(var child : children){
            child.turretShading = turretShading;
            child.load(name);
        }
    }
}
