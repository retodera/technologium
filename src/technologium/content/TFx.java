package technologium.content;

import arc.math.*;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import technologium.graphics.TPal;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import arc.graphics.g2d.Lines;

public class TFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect

    hitSparkColor = new Effect(40, e -> {
        color(e.color);
        stroke(e.fout() * 1.6f);

        randLenVectors(e.id, 18, e.finpow() * 27f, e.rotation, 360f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 6 + 1f);
        });
    }),

    longLaserCharge = new Effect(180, e -> {
        for(int i = 0; i < 4; i++) {
            color(TPal.gold3.cpy().mul(1.5f));
            stroke(e.finpow() * 3f * Mathf.clamp(e.finpowdown() * 4 - i+1, 0, 999));
            Lines.circle(e.x, e.y, Mathf.clamp(e.foutpowdown() * 25f * i+1 - i * 5, 0, 999));
        }
    }).followParent(true).rotWithParent(true);
}
