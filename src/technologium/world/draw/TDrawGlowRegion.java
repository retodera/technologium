package technologium.world.draw;

import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.world.Block;
import mindustry.gen.Building;
import mindustry.world.draw.DrawGlowRegion;

public class TDrawGlowRegion extends DrawGlowRegion {
    public TDrawGlowRegion() {}
    
    public TDrawGlowRegion(float layer){
        this.layer = layer;
    }

    public TDrawGlowRegion(boolean rotate){
        this.rotate = rotate;
    }

    public TDrawGlowRegion(String suffix){
        this.suffix = suffix;
    }

    @Override
    public void draw(Building build){
        float warmup = Mathf.clamp(build.warmup());
        if(warmup <= 0.001f) return;

        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        Draw.blend(blending);
        Draw.color(color);
        Draw.alpha((Mathf.absin(build.totalProgress(), glowScale, alpha) * glowIntensity + 1f - glowIntensity) * warmup * alpha);
        Draw.rect(region, build.x, build.y, build.totalProgress() * rotateSpeed + (rotate ? build.rotdeg() : 0f));
        Draw.reset();
        Draw.blend();
        Draw.z(z);
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{};
    }
}
