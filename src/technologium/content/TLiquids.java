package technologium.content;

import mindustry.type.*;
import technologium.graphics.TPal;

public class TLiquids {
    public static Liquid
    hydrochloricAcid, liquidNitrogen, carbon, liquidPlasma, lava;

    public static void load() {

        hydrochloricAcid = new Liquid("hydrochloric-acid", TPal.acid3) {{
            effect = TStatusEffects.corrosion;
            boilPoint = 2f;
            gasColor = TPal.acid3;
            viscosity = 0.3f;
            temperature = 0.7f;
            incinerable = false;
        }};

        liquidNitrogen = new Liquid("liquid-nitrogen", TPal.purple3) {{
            coolant = true;
            heatCapacity = 1.4f;
            temperature = -2.3f;
            gasColor = TPal.purple3;
            viscosity = 0.1f;
            flammability = 0f;
            boilPoint = -1.9f;
        }};

        carbon = new Liquid("carbon", TPal.dark2) {{
            gas = true;
            flammability = 1.8f;
            boilPoint = -1f;
        }};

        liquidPlasma = new Liquid("liquid-plasma", TPal.plasma3) {{
            temperature = 2.8f;
            boilPoint = 0.1f;
            gasColor = TPal.plasma3;
            incinerable = false;
        }};

        lava = new Liquid("lava", TPal.orange3) {{
            temperature = 1.4f;
            boilPoint = 1f;
            gasColor = TPal.orange3;
            incinerable = false;
        }};
    }
}