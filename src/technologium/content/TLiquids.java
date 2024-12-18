package technologium.content;

import mindustry.type.*;
import technologium.graphics.TPal;

public class TLiquids {
    public static Liquid hydrochloricAcid, liquidNitrogen, carbon, liquidPlasma;

    public static void load() {

        hydrochloricAcid = new Liquid("hydrochloric-acid", TPal.acid3) {
            {
                effect = TStatusEffects.corrosion;
                boilPoint = 2f;
                gasColor = TPal.acid1;
                viscosity = 0.3f;
                temperature = 0.7f;
                incinerable = false;
            }
        };

        liquidNitrogen = new Liquid("liquid-nitrogen", TPal.cyan5) {
            {
                coolant = true;
                heatCapacity = 1.4f;
                temperature = 0f;
                gasColor = TPal.cyan3;
                viscosity = 0.1f;
                flammability = 0f;
                boilPoint = -10f;
            }
        };


        carbon = new Liquid("carbon", TPal.dark2) {
            {
                gas = true;
                flammability = 1.8f;
                boilPoint = -1f;
            }
        };

        liquidPlasma = new Liquid("liquid-plasma", TPal.plasm5) {
            {
                temperature = 4.8f;
                boilPoint = 15f;
                incinerable = false;
            }
        };
    }
}