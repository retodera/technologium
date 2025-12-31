package technologium.content;

import mindustry.content.Fx;
import mindustry.type.*;
import mindustry.content.StatusEffects;

import static technologium.graphics.TPal.*;

public class TStatusEffects {
    public static StatusEffect neoplasmCovered, veryslow;

    public static void load() {

        neoplasmCovered = new StatusEffect("neoplasm-covered") {{
            color = hematite2;
            speedMultiplier = 0.7f;
            effect = Fx.muddy;
            init(() -> {
                affinity(StatusEffects.wet, (unit, result, time) -> unit.damagePierce(1.5f));
                affinity(StatusEffects.burning, (unit, result, time) -> unit.damagePierce(0.9f));
            });
        }};

        veryslow = new StatusEffect("veryslow"){{
            color = dark4;
            speedMultiplier = 0.15f;
        }};
    }
}