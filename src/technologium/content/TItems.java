package technologium.content;

import arc.struct.*;
import mindustry.type.Item;
import technologium.graphics.TPal;

public class TItems {
    public static Item
    /* standart */ hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm,
            trainedNeoplasm, uranium, uraniumCell, stalinium, roskomnadzorium, pegmatite, enrichedMetal, enrichedAluminium,
    /* technologies */ rotor, armorPlate, cog, laser, sparkPlug, bioProcessor, advBioProcessor, memoryCard, radiator, heatElement, accumulator, advAccumulator, shieldGen, advShieldGen, technologiumKudol
            ;

    public static final Seq<Item> kudolItems = new Seq<>();

    public static void load() {

        hematite = new Item("hematite", TPal.brown2) {{
                hardness = 1;
                cost = 0.5f;
        }};

        tin = new Item("tin", TPal.calt1) {{
                hardness = 1;
                cost = 0.5f;
        }};

        darkMetal = new Item("dark-metal", TPal.dark4) {{
                cost = 0.75f;
        }};

        bauxite = new Item("bauxite", TPal.brown8) {{
                hardness = 2;
                cost = 0.5f;
        }};

        aluminium = new Item("aluminium", TPal.wnb9) {{
                cost = 0.75f;
        }};

        lithium = new Item("lithium", TPal.lithium3) {{
                cost = 0.75f;
        }};

        gold = new Item("gold", TPal.gold3) {{
                cost = 1f;
        }};

        goldGlass = new Item("gold-glass", TPal.ggl3) {{
                cost = 1.25f;
        }};

        cannedNeoplasm = new Item("canned-neoplasm", TPal.sgl1) {{
                cost = 2f;
                flammability = 0.25f;
        }};

        trainedNeoplasm = new Item("trained-neoplasm", TPal.sgl3) {{
                cost = 3f;
                flammability = 0.25f;
        }};

        uranium = new Item("uranium", TPal.green1) {{
            cost = 4f;
            radioactivity = 1f;
        }};

        uraniumCell = new Item("uranium-cell", TPal.green2) {{
            cost = 6f;
            radioactivity = 2f;
        }};

        stalinium = new Item("stalinium", TPal.red3) {{
                cost = 10f;
                radioactivity = 1.5f;
        }};

        rotor = new Item("rotor", TPal.wnb3) {{
            cost = 4f;
        }};

        armorPlate = new Item("armor-plate", TPal.wnb2) {{
            cost = 4f;
        }};

        cog = new Item("cog", TPal.wnb5) {{
            cost = 4f;
        }};

        roskomnadzorium = new Item("roskomnadzorium", TPal.calt2) {{
            cost = 0f;
        }};

        pegmatite = new Item("pegmatite", TPal.kaut1) {{
            hardness = 1;
            cost = 0.5f;
        }};

        enrichedMetal = new Item("enriched-metal", TPal.wnb4) {{
            cost = 0f;
        }};

        enrichedAluminium = new Item("enriched-aluminium", TPal.wnb6) {{
            cost = 0f;
        }};

        bioProcessor = new Item("bio-processor", TPal.cyan2) {{
            cost = 6f;
            flammability = 0.25f;
            explosiveness = 0.25f;
        }};

        kudolItems.addAll(
                hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm, trainedNeoplasm, uranium, uraniumCell,
                stalinium, roskomnadzorium, cog, rotor, armorPlate, pegmatite, enrichedMetal, enrichedAluminium);
    }
}