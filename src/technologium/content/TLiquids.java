package technologium.content;

import arc.graphics.*;
import mindustry.type.*;

public class TLiquids {
    public static Liquid hydrochloricAcid, liquidNitrogen, sulfur, carbon;

    public static void load() {

        hydrochloricAcid = new Liquid("hydrochloric-acid", Color.valueOf("3fffbf")) {
            {
                effect = TStatusEffects.corrosion;
                boilPoint = 1f;
                gasColor = Color.valueOf("33cc99");
                viscosity = 0.3f;
                temperature = 0.7f;
            }
        };

        liquidNitrogen = new Liquid("liquid-nitrogen", Color.valueOf("ffbfff")) {
            {
                coolant = true;
                heatCapacity = 1.4f;
                temperature = 0f;
                gasColor = Color.valueOf("e6ace6");
                viscosity = 0.1f;
                flammability = 0f;
                boilPoint = 0f;
            }
        };

        sulfur = new Liquid("sulfur", Color.valueOf("703c16")) {
            {
                gas = true;
                explosiveness = 1.4f;
                flammability = 1.4f;
            }
        };

        carbon = new Liquid("carbon", Color.valueOf("2e2e2e")) {
            {
                gas = true;
                flammability = 1.8f;
            }
        };
    }
}