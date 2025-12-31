package technologium.content;

import mindustry.content.*;
import mindustry.type.*;
import technologium.type.TCellLiquid;
import arc.graphics.Color;

import static mindustry.content.Liquids.*;
import static technologium.graphics.TPal.*;

public class TLiquids {
    public static Liquid
    freon, liquidPlasma,
    lava, moltenDarkMetal, moltenAluminium,
    carbon, sulfur, oxygen,
    heavyWater, scarletWater, mutatedNeoplasm,
    sirin, destabilizedSirin, combatSirin, inhibitedSirin;

    public static void load() {
        freon = new Liquid("freon", purple3) {{
            coolant = true;
            heatCapacity = 5f;
            temperature = -0.63f;
            gasColor = color;
            viscosity = 0.1f;
            flammability = 0f;
            boilPoint = -1.9f;
            incinerable = false;
        }};

        liquidPlasma = new Liquid("liquid-plasma", cyan3) {{
            lightColor = cyan3;
            temperature = 2.8f;
            boilPoint = 0.1f;
            gasColor = color;
            incinerable = false;
        }};

        lava = new Liquid("lava", lava2) {{
            lightColor = lava3;
            temperature = 1.4f;
            boilPoint = 3f;
            gasColor = color;
            incinerable = false;
        }};

        oxygen = new Liquid("oxygen", oxygen3) {{
            gas = true;
            gasColor = color;
            incinerable = false;
        }};

        moltenDarkMetal = new Liquid("molten-dark-metal", lava1) {{
            lightColor = lava3;
            temperature = 1.4f;
            boilPoint = 3f;
            gasColor = color;
            incinerable = false;
        }};

        moltenAluminium = new Liquid("molten-aluminium", Color.valueOf("f4bd89")) {{
            lightColor = Color.valueOf("ffd7b1");
            temperature = 1.4f;
            boilPoint = 3f;
            gasColor = color;
            incinerable = false;
        }};
        
        carbon = new Liquid("carbon", dark3) {{
            gas = true;
            flammability = 1;
        }};

        sulfur = new Liquid("sulfur", hematite2) {{
            gas = true;
            explosiveness = 0.55f;
            flammability = 0.55f;
        }};

        heavyWater = new Liquid("heavy-water", Color.valueOf("927bdf")) {{
            heatCapacity = 0.4f;
            effect = StatusEffects.wet;
            boilPoint = 0.5f;
            gasColor = Color.valueOf("927bdf");
        }};

        scarletWater = new Liquid("scarlet-water", Color.valueOf("b8688c")) {{
            heatCapacity = 0.4f;
            effect = StatusEffects.wet;
            boilPoint = 0.5f;
            gasColor = Color.valueOf("b8688c");
        }};

        mutatedNeoplasm = new TCellLiquid("mutated-neoplasm", Color.valueOf("c33e2b")){{
            heatCapacity = 0.4f;
            temperature = 0.54f;
            viscosity = 0.85f;
            flammability = 0f;
            capPuddles = false;
            spreadTargets.addAll(Liquids.water, heavyWater, scarletWater);
            moveThroughBlocks = true;
            incinerable = false;
            blockReactive = false;
            canStayOn.addAll(water, heavyWater, scarletWater, oil, cryofluid);

            colorFrom = Color.valueOf("e8803f");
            colorTo = Color.valueOf("8c1225");
        }};

        sirin = new Liquid("sirin", purple3){{
            coolant = false;
            temperature = 0.4f;
            viscosity = 0.7f;
            boilPoint = 0.8f;
            flammability = 0.1f;
            explosiveness = 0.1f;
            gasColor = color;
        }};

        destabilizedSirin = new Liquid("destabilized-sirin", oxygen3){{
            coolant = false;
            temperature = 0.8f;
            viscosity = 0.5f;
            boilPoint = 0.4f;
            flammability = 0.6f;
            explosiveness = 0.6f;
            gasColor = color;
        }};

        combatSirin = new Liquid("combat-sirin", oxygen3){{
            coolant = false;
            temperature = 0.6f;
            viscosity = 0.5f;
            boilPoint = 0.8f;
            flammability = 0.3f;
            explosiveness = 0.3f;
            gasColor = color;
        }};

        inhibitedSirin = new Liquid("inhibited-sirin", purple3){{
            temperature = 0.3f;
            heatCapacity = 0.8f;
            viscosity = 0.6f;
            boilPoint = 1f;
            gasColor = color;
        }};
    }
}