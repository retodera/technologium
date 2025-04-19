package technologium.content;

import static technologium.TVars.*;

import arc.struct.*;
import mindustry.type.Item;
import technologium.type.Fruit;

import static technologium.graphics.TPal.*;

public class TItems {
    public static Item
    /* standart */ volcanicSand, solidNeoplasm, hematite, darkMetal, tin, bauxite, aluminium, pegmatite, lithium, gold, goldGlass, negativium, cannedNeoplasm,
        trainedNeoplasm, uranium, enrichedUranium, uraniumCell, rethium, rknium, enrichedMetal, enrichedAluminium,
    /* technologies */ cog, armorPlate, rotor, laser, sparkPlug, bioprocessor, advBioprocessor, memoryCard, radiator, heatElement, accumulator,
        advAccumulator, shieldGen, advShieldGen, technologiumKudol, tinCan,
    /* fruits */ leptine, leptineSeed,
    /* mitamitamitamitamitamitamitamitamitamitamitamitamitamitamitamita */
        mitanium, mitalite, milane, cappite,
    /* other */
        theimpossible
    ;

    public static final Seq<Item> kudolItems = new Seq<>(), venjerItems = new Seq<>(), mitaItems = new Seq<>();

    public static void load() {

        // region kudol

        volcanicSand = new Item("volcanic-sand", brown3) {{
            cost = 0.25f;
        }};

        solidNeoplasm = new Item("solid-neoplasm", neoplasm3) {{
            cost = 0.5f;
        }};

        hematite = new Item("hematite", brown2) {{
            hardness = 1;
            cost = 0.5f;
        }};

        darkMetal = new Item("dark-metal", dark4) {{
            cost = 0.75f;
        }};

        tin = new Item("tin", tin3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        bauxite = new Item("bauxite", brown8) {{
            hardness = 2;
            cost = 0.5f;
        }};

        aluminium = new Item("aluminium", wnb9) {{
            cost = 0.75f;
        }};

        pegmatite = new Item("pegmatite", kaut3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        lithium = new Item("lithium", lithium3) {{
            cost = 0.75f;
        }};

        gold = new Item("gold", gold3) {{
            cost = 1f;
        }};

        goldGlass = new Item("gold-glass", acid3) {{
            cost = 1.25f;
        }};

        negativium = new Item("negativium", orange3) {{
            flammability = 5f;
            explosiveness = 5f;
        }};

        cannedNeoplasm = new Item("canned-neoplasm", brown6) {{
            cost = 2f;
            flammability = 0.25f;
        }};

        trainedNeoplasm = new Item("trained-neoplasm", brown7) {{
            cost = 3f;
            flammability = 0.25f;
        }};

        uranium = new Item("uranium", lime1) {{
            cost = 4f;
            radioactivity = 0.4f;
        }};

        enrichedUranium = new Item("enriched-uranium", lime3) {{
            cost = 4f;
            radioactivity = 1.5f;
        }};

        uraniumCell = new Item("uranium-cell", lime2) {{
            cost = 6f;
            radioactivity = 0.94f;
        }};

        rethium = new Item("rethium", red3) {{
            cost = 10f;
            radioactivity = 1f;
        }};

        // endregion

        // region kudol technologies

        rotor = new Item("rotor", wnb3) {{
            cost = 4f;
        }};

        armorPlate = new Item("armor-plate", wnb2) {{
            cost = 4f;
        }};

        cog = new Item("cog", wnb5) {{
            cost = 4f;
        }};

        rknium = new Item("rknium", tin1) {{
            cost = 0f;
        }};

        enrichedMetal = new Item("enriched-metal", wnb4) {{
            cost = 0f;
        }};

        enrichedAluminium = new Item("enriched-aluminium", wnb6) {{
            cost = 0f;
        }};

        bioprocessor = new Item("bioprocessor", brown4) {{
            cost = 6f;
            flammability = 0.25f;
            explosiveness = 0.25f;
        }};

        advBioprocessor = new Item("adv-bioprocessor", brown5) {{
            cost = 10f;
            flammability = 0.5f;
            explosiveness = 0.5f;
        }};

        accumulator = new Item("accumulator", lithium1) {{
            cost = 6f;     
        }};

        advAccumulator = new Item("adv-accumulator", lithium2) {{
            cost = 10f;     
        }};

        memoryCard = new Item("memory-card", wnb5) {{
            cost = 6f;     
        }};

        shieldGen = new Item("shield-gen", gold1) {{
            cost = 6f;     
        }};

        advShieldGen = new Item("adv-shield-gen", gold2) {{
            cost = 10f;     
        }};

        tinCan = new Item("tin-can", tin2) {{
            cost = 6f;     
        }};

        // endregion

        // region fruits

        leptine = new Fruit("leptine", kaut3) {{
            result = null;
            juiciness = 0.75f;
            plantable = false;
            seedChance = 0.5f;
        }};

        leptineSeed = new Fruit("leptine-seed", kaut2) {{
            result = leptine;
            ((Fruit)leptine).seed = this;
            growTimeMin = 1200f;
            growTimeMax = 1800f;
        }};

        // endregion

        // region mita

        mitanium = new Item("mitanium", purpur1) {{
            cost = 1f;
            flammability = 0.25f;
            hardness = 1;
            hidden = !misideRelease;
        }};

        mitalite = new Item("mitalite", purpur2) {{
            cost = 2f;
            hidden = !misideRelease;
        }};

        milane = new Item("milane", lithium1) {{
            cost = 3f;
            hardness = 2;
            explosiveness = 0.5f;
            hidden = !misideRelease;
        }};

        cappite = new Item("cappite", purpur1.cpy().mul(83 / 95f)) {{
            cost = 2f;
            radioactivity = 1.5f;
            hidden = !misideRelease;
        }};

        // endregion

        // region other

        theimpossible = new Item("theimpossible", red3) {{
            cost = flammability = explosiveness = radioactivity = charge = hardness = 2147483647;
            hidden = !debug;
        }};

        // endregion

        kudolItems.addAll(
                hematite, tin, darkMetal, bauxite, aluminium, lithium, gold, goldGlass, cannedNeoplasm, trainedNeoplasm, uranium, uraniumCell,
                rethium, rknium, cog, rotor, armorPlate, pegmatite, enrichedMetal, enrichedAluminium, bioprocessor, accumulator,
                leptine, leptineSeed);
        mitaItems.addAll(
                mitanium, mitalite, milane, cappite
        );
    }
}