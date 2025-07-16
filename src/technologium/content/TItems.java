package technologium.content;

import arc.graphics.Color;
import arc.struct.*;
import mindustry.type.Item;
import technologium.type.Fruit;
import technologium.type.ItemTechnology;

import static technologium.graphics.TPal.*;
import static mindustry.type.ItemStack.with;
import static technologium.TVars.*;

public class TItems {
    public static Item
    /* standart */
        volcanicSand, solidNeoplasm, hematite, tin,
        bauxite, pegmatite,

        darkMetal, aluminium, lithium, gold, goldGlass,

        enrichedMetal, enrichedAluminium,

    /* advanced */ 
        aerogel, molybdenum, rhenium,
        iron, calcium, silicon, silica,

        cannedNeoplasm, trainedNeoplasm,
        
        necrohexin,
        
    /* technologies */ 
        cog, armorPlate, bioprocessor, advBioprocessor, accumulator,
        advAccumulator, shieldGen, advShieldGen, memoryCard, tinCan,
        
    /* fruits */
        leptine, leptineSeed,
    
    /* other */
        theimpossible, condom
    ;

    public static final Seq<Item> kudolItems = new Seq<>(), venjerItems = new Seq<>();

    public static void load() {

        // region kudol

        volcanicSand = new Item("volcanic-sand", brown3) {{
            cost = 0.25f;
        }};

        solidNeoplasm = new Item("solid-neoplasm", neoplasm3) {{
            cost = 0.5f;
        }};

        hematite = new Item("hematite", brown6) {{
            hardness = 1;
            cost = 0.5f;
        }};

        darkMetal = new Item("dark-metal", dark5) {{
            cost = 0.75f;
        }};

        tin = new Item("tin", tin3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        bauxite = new Item("bauxite", kaut2) {{
            hardness = 2;
            cost = 0.5f;
        }};

        aluminium = new Item("aluminium", wnb8) {{
            cost = 0.75f;
        }};

        pegmatite = new Item("pegmatite", kaut3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        lithium = new Item("lithium", lithium3) {{
            cost = 0.75f;
            charge = 0.5f;
        }};

        enrichedMetal = new Item("enriched-metal", wnb4) {{
            cost = 0f;
        }};

        enrichedAluminium = new Item("enriched-aluminium", wnb6) {{
            cost = 0f;
        }};

        gold = new Item("gold", gold3) {{
            cost = 1f;
        }};

        goldGlass = new Item("gold-glass", acid3) {{
            cost = 2f;
        }};

        cannedNeoplasm = new Item("canned-neoplasm", flesh3) {{
            cost = 2f;
            flammability = 0.25f;
        }};

        trainedNeoplasm = new Item("trained-neoplasm", flesh2) {{
            cost = 2f;
            flammability = 0.25f;
        }};

        // endregion

        // region advanced

        aerogel = new Item("aerogel", Color.valueOf("e0e0e0")) {{
            cost = 2f;
        }};

        molybdenum = new Item("molybdenum", purpur3) {{
            cost = 2.5f;
        }};

        rhenium = new Item("rhenium", purple3) {{
            cost = 3.5f;
            charge = 0.3f;
        }};

        iron = new Item("iron", red3) {{
            cost = 2f;
        }};

        calcium = new Item("calcium", wnb7) {{
            cost = 2f;
        }};

        silicon = new Item("silicon", wnb5) {{
            cost = 2f;
        }}; 

        silica = new Item("silica", wnb8) {{
            cost = 2f;
        }};
                
        necrohexin = new Item("necrohexin", orange3) {{
            flammability = 5f;
            explosiveness = 5f;
        }};

        // endregion

        // region technologies

        cog = new ItemTechnology("cog", wnb6) {{
            cost = 4f;
            itemReq = with(darkMetal, 2, aluminium, 2);
            outputAmount = 6;
        }};

        armorPlate = new ItemTechnology("armor-plate", gold2) {{
            cost = 4f;
            itemReq = with(darkMetal, 4, aluminium, 2);
            outputAmount = 2;
        }};

        bioprocessor = new ItemTechnology("bioprocessor", neoplasm2) {{
            cost = 6f;
            flammability = 0.5f;
            explosiveness = 0.5f;
            itemReq = with(TItems.darkMetal, 2, TItems.aluminium, 1, TItems.lithium, 1, TItems.trainedNeoplasm, 1);
        }};

        advBioprocessor = new ItemTechnology("adv-bioprocessor", neoplasm1) {{
            cost = 10f;
            flammability = 0.5f;
            explosiveness = 0.5f;
        }};

        accumulator = new ItemTechnology("accumulator", lithium2) {{
            cost = 6f;
            explosiveness = 0.75f;
            flammability = 0.75f;
            itemReq = with(TItems.darkMetal, 1, TItems.aluminium, 1, TItems.lithium, 2);
            outputAmount = 2;
        }};

        advAccumulator = new ItemTechnology("adv-accumulator", lithium1) {{
            cost = 10f;
            explosiveness = 0.75f;
            flammability = 0.75f;
        }};

        memoryCard = new ItemTechnology("memory-card", wnb7) {{
            cost = 6f;     
        }};

        shieldGen = new ItemTechnology("shield-gen", gold2) {{
            cost = 6f;     
        }};

        advShieldGen = new ItemTechnology("adv-shield-gen", gold1) {{
            cost = 10f;     
        }};

        tinCan = new ItemTechnology("tin-can", tin2) {{
            cost = 1f;
            itemReq = with(TItems.darkMetal, 1, TItems.tin, 1);
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

        // region other

        theimpossible = new Item("theimpossible", Color.red) {{
            cost = flammability = explosiveness = radioactivity = charge = hardness = 2147483647;
            hidden = !debug;
        }};

        condom = new Item("condom", Color.red) {{
            cost = flammability = explosiveness = radioactivity = charge = hardness = 2147483647;
            hidden = !debug;
        }};

        // endregion

        kudolItems.addAll(
        /* standart */
            volcanicSand, solidNeoplasm, hematite, tin,
            bauxite, pegmatite,

            darkMetal, aluminium, lithium, gold, goldGlass,

            enrichedMetal, enrichedAluminium,

        /* advanced */ 
            aerogel, molybdenum, rhenium,
            iron, calcium, silicon, silica,

            cannedNeoplasm, trainedNeoplasm,
            
            necrohexin,
            
        /* technologies */ 
            cog, armorPlate, bioprocessor, advBioprocessor, accumulator,
            advAccumulator, shieldGen, advShieldGen, memoryCard, tinCan,
            
        /* fruits */
            leptine, leptineSeed
        );
    }
}