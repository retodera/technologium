package technologium.content;

import mindustry.content.Fx;
import mindustry.type.*;
import mindustry.content.StatusEffects;

import static technologium.graphics.TPal.*;

public class TStatusEffects {
    public static StatusEffect corrosion, neoplasmCovered, irradiated, veryslow;

    public static void load() {

        corrosion = new StatusEffect("corrosion") {{
            color = dark5;
            damage = 0.1f;
            effect = Fx.none;
            speedMultiplier = 1.4f;
        }};

        neoplasmCovered = new StatusEffect("neoplasm-covered") {{
            color = brown5;
            speedMultiplier = 0.7f;
            effect = Fx.muddy;
            init(() -> {
                affinity(StatusEffects.wet, (unit, result, time) -> unit.damagePierce(1.5f));
                affinity(StatusEffects.burning, (unit, result, time) -> unit.damagePierce(0.9f));
            });
        }};

        veryslow = new StatusEffect("veryslow"){{
            color = dark3;
            speedMultiplier = 0.15f;
        }};
    }
}