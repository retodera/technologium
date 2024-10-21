package technologium.content;

import arc.struct.*;
import mindustry.type.Item;
import technologium.graphics.TPal;

public class TItems {
    public static Item hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm,
            trainedNeoplasm, uranium, uraniumRod, stalinium, steel;

    public static final Seq<Item> kudolItems = new Seq<>();

    public static void load() {
        hematite = new Item("hematite", TPal.brown2) {
            {
                hardness = 1;
                cost = 0.5f;
            }
        };

        tin = new Item("tin", TPal.wnb10) {
            {
                hardness = 1;
                cost = 0.5f;
            }
        };

        darkMetal = new Item("dark-metal", TPal.dark4) {
            {
                cost = 0.75f;
            }
        };

        bauxite = new Item("bauxite", TPal.brown14) {
            {
                hardness = 2;
                cost = 0.5f;
            }
        };

        aluminium = new Item("aluminium", TPal.wnb9) {
            {
                cost = 0.75f;
            }
        };

        lithium = new Item("lithium", TPal.lithium3) {
            {
                cost = 0.75f;
            }
        };

        gold = new Item("gold", TPal.gold3) {
            {
                cost = 1f;
            }
        };

        goldGlass = new Item("gold-glass", TPal.ggl3) {
            {
                cost = 1.25f;
            }
        };

        cannedNeoplasm = new Item("canned-neoplasm", TPal.sgl1) {
            {
                cost = 2f;
            }
        };

        trainedNeoplasm = new Item("trained-neoplasm", TPal.sgl3) {
            {
                cost = 3f;
            }
        };

        uranium = new Item("uranium", TPal.uranium3) {{
            cost = 4f;
        }};

        
        uraniumRod = new Item("uranium-rod", TPal.uranium2) {{
            cost = 6f;
        }};

        stalinium = new Item("stalinium", TPal.red3) {
            {
                cost = 10f;
            }
        };

        steel = new Item("steel", TPal.wnb7) {
            {
                cost = 1f;
            }
        };

        kudolItems.addAll(
                hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm, trainedNeoplasm, uranium, uraniumRod,
                stalinium);
    }
}