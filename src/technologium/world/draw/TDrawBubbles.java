package technologium.world.draw;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.draw.DrawBubbles;

public class TDrawBubbles extends DrawBubbles {
    @Override
    public void draw(Building build) {
        if(build.liquids == null || build.warmup() <= 0.001f) return;
        float sum = build.liquids.sum((l, a) -> a);
        if(Mathf.zero(sum)) return;
        int ls = (int)build.liquids.sum((l, a) -> Mathf.zero(a) ? 0 : 1);
        Color color = new Color();
        build.liquids.each((l, a) -> {
            if(color.a == 0) color.set(l.color); 
            else color.lerp(l.color, a / sum / ls);
        });

        Draw.color(color, build.warmup() * sum / ls / build.block.liquidCapacity);

        rand.setSeed(build.id);
        for(int i = 0; i < amount; i++){
            float x = rand.range(spread), y = rand.range(spread);
            float life = 1f - ((Time.time / timeScl + rand.random(recurrence)) % recurrence);

            if(life > 0){
                float rad = (1f - life) * radius;
                if(fill) Fill.circle(build.x + x, build.y + y, rad);
                else{
                    Lines.stroke(build.warmup() * (life + strokeMin));
                    Lines.poly(build.x + x, build.y + y, sides, rad);
                }
            }
        }

        Draw.color();
    }
}
