package technologium.world.draw;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.world.draw.DrawCircles;
import arc.util.Time;

public class TDrawCircles extends DrawCircles {
    @Override
    public void draw(Building build){
        if(build.liquids == null || build.warmup() <= 0.001f) return;
        float sum = build.liquids.sum((l, a) -> a);
        if(Mathf.zero(sum)) return;
        int ls = (int)build.liquids.sum((l, a) -> Mathf.zero(a) ? 0 : 1);
        Color color = new Color();
        build.liquids.each((l, a) -> {
            if(color.a == 0) color.set(l.color); 
            else color.lerp(l.color, a / sum / ls);
        });
        
        Draw.color(color, build.warmup() * color.a);

        for(int i = 0; i < amount; i++){
            float life = ((Time.time / timeScl + i/(float)amount) % 1f);

            Lines.stroke(build.warmup() * strokeInterp.apply(strokeMax, strokeMin, life));
            Lines.poly(build.x + x, build.y + y, sides, radiusOffset + life * radius);
        }

        Draw.reset();
    }
}
