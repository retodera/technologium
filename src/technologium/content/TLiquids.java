package technologium.content;

import mindustry.type.*;
import static technologium.graphics.TPal.*;

import arc.graphics.Color;

public class TLiquids {
    public static Liquid
    hydrochloricAcid, liquidNitrogen, carbon, liquidPlasma, lava, ammonia, ammoniaWater, distilledWater;

    public static void load() {
        hydrochloricAcid = new Liquid("hydrochloric-acid", acid3) {{
            effect = TStatusEffects.corrosion;
            boilPoint = 2f;
            gasColor = color;
            viscosity = 0.3f;
            temperature = 0.7f;
            incinerable = false;
        }};

        liquidNitrogen = new Liquid("liquid-nitrogen", purple3) {{
            coolant = true;
            heatCapacity = 1.4f;
            temperature = -2.3f;
            gasColor = color;
            viscosity = 0.1f;
            flammability = 0f;
            boilPoint = -1.9f;
        }};

        carbon = new Liquid("carbon", dark2) {{
            gas = true;
            flammability = 1.8f;
        }};

        liquidPlasma = new Liquid("liquid-plasma", plasma3) {{
            temperature = 2.8f;
            boilPoint = 0.1f;
            gasColor = color;
            incinerable = false;
        }};

        lava = new Liquid("lava", lava2) {{
            temperature = 1.4f;
            boilPoint = 2f;
            gasColor = color;
            incinerable = false;
        }};

        ammonia = new Liquid("ammonia", lime3) {{
            gas = true;
            flammability = 0.1f;
            incinerable = false;
        }};

        ammoniaWater = new Liquid("ammonia-water", Color.valueOf("68a598")){{
            heatCapacity = 0.5f;
            boilPoint = 2.5f;
            gasColor = Color.grays(0.9f);
        }};

        distilledWater = new Liquid("distilled-water", Color.valueOf("8698cd")){{
            heatCapacity = 0.7f;
            boilPoint = 2f;
            gasColor = Color.grays(0.9f);
        }};
    }
}