package technologium.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.Item;

public class TItems {
    public static Item hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm,
            trainedNeoplasm, stalinium, steel;

    public static final Seq<Item> kudolItems = new Seq<>(), kudolOnlyItems = new Seq<>();

    public static void load() {
        hematite = new Item("hematite", Color.valueOf("909090")) {
            {
                hardness = 1;
                cost = 0.5f;
            }
        };

        tin = new Item("tin", Color.valueOf("cccccc")) {
            {
                hardness = 1;
                cost = 0.5f;
            }
        };

        darkMetal = new Item("dark-metal", Color.valueOf("5c5c5c")) {
            {
                cost = 0.75f;
            }
        };

        bauxite = new Item("bauxite", Color.valueOf("703a23")) {
            {
                hardness = 2;
                cost = 0.5f;
            }
        };

        aluminium = new Item("aluminium", Color.valueOf("dedede")) {
            {
                cost = 0.75f;
            }
        };

        lithium = new Item("lithium", Color.valueOf("ff0099")) {
            {
                cost = 0.75f;
            }
        };

        gold = new Item("gold", Color.valueOf("dddd00")) {
            {
                cost = 1f;
            }
        };

        goldGlass = new Item("gold-glass", Color.valueOf("3fffbf")) {
            {
                cost = 1.25f;
            }
        };

        cannedNeoplasm = new Item("canned-neoplasm", Color.valueOf("804419")) {
            {
                cost = 2f;
            }
        };

        trainedNeoplasm = new Item("trained-neoplasm", Color.valueOf("bf6626")) {
            {
                cost = 3f;
            }
        };

        stalinium = new Item("stalinium", Color.red) {
            {
                cost = 4f;
            }
        };

        steel = new Item("steel", Color.valueOf("acacac")) {
            {
                cost = 1f;
            }
        };

        kudolItems.addAll(
                hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm, trainedNeoplasm,
                stalinium);
    }
}