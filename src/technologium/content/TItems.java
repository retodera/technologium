package technologium.content;

import arc.struct.*;
import mindustry.type.Item;
import technologium.graphics.TPal;

public class TItems {
    public static Item
    /* standart */ hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, salt, cannedNeoplasm,
            trainedNeoplasm, uranium, uraniumCell, stalinium, roskomnadzorium,
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

        bauxite = new Item("bauxite", TPal.brown14) {{
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

        salt = new Item("salt", TPal.wnb7) {{
                cost = 1f;
        }};

        cannedNeoplasm = new Item("canned-neoplasm", TPal.sgl1) {{
                cost = 2f;
        }};

        trainedNeoplasm = new Item("trained-neoplasm", TPal.sgl3) {{
                cost = 3f;
        }};

        uranium = new Item("uranium", TPal.uranium3) {{
            cost = 4f;
        }};

        uraniumCell = new Item("uranium-cell", TPal.uranium2) {{
            cost = 6f;
        }};

        stalinium = new Item("stalinium", TPal.red3) {{
                cost = 10f;
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

        kudolItems.addAll(
                hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, salt, cannedNeoplasm, trainedNeoplasm, uranium, uraniumCell,
                stalinium, roskomnadzorium, cog);
    }
}