package technologium.content;

import mindustry.content.Fx;
import mindustry.type.*;
import mindustry.content.StatusEffects;
import technologium.graphics.TPal;
import technologium.TVars;

public class TStatusEffects {
    public static StatusEffect corrosion, neoplasmCovered, mitaCorrupted;

    public static void load() {

        corrosion = new StatusEffect("corrosion") {{
            color = TPal.dark1;
            damage = 0.1f;
            effect = Fx.none;
            speedMultiplier = 1.4f;
        }};

        neoplasmCovered = new StatusEffect("neoplasm-covered") {{
            color = TPal.brown5;
            speedMultiplier = 0.7f;
            effect = Fx.muddy;
            init(() -> {
                affinity(StatusEffects.wet, (unit, result, time) -> unit.damagePierce(0.3f));
                affinity(StatusEffects.burning, (unit, result, time) -> unit.damagePierce(0.5f));
            });
        }};

        mitaCorrupted = new StatusEffect("mita-corrupted") {{
            show = TVars.misideRelease;
            color = TPal.purpur1;
            damage = 0.025f;
            effect = Fx.none;
            speedMultiplier = 0f;
            disarm = true;
        }};
    }
}