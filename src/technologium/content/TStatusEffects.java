package technologium.content;

import arc.graphics.*;
import mindustry.content.Fx;
import mindustry.type.*;
import mindustry.content.StatusEffects;

public class TStatusEffects {
    public static StatusEffect corrosion, neoplasmCovered;

    public static void load() {
        corrosion = new StatusEffect("corrosion") {
            {
                color = Color.valueOf("1a1a1a");
                damage = 0.3f;
                effect = Fx.none;
                speedMultiplier = 1.4f;
            }
        };
        neoplasmCovered = new StatusEffect("neoplasm-covered") {
            {
                color = Color.valueOf("703c16");
                speedMultiplier = 0.7f;
                effect = Fx.muddy;
                init(() -> {
                    affinity(StatusEffects.wet, (unit, result, time) -> unit.damagePierce(22f));
                    affinity(StatusEffects.burning, (unit, result, time) -> unit.damagePierce(26f));
                });
            }
        };
    }
}