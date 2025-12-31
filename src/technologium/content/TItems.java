package technologium.content;

import arc.Events;
import arc.graphics.Color;
import arc.struct.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.type.Item;
import technologium.type.*;

import static technologium.graphics.TPal.*;
import static mindustry.type.ItemStack.with;
import static technologium.TVars.*;

public class TItems {
    public static Item
    // standart
        volcanicSand, solidNeoplasm,
        hematite, tin, pegmatite, bauxite,

        enrichedMetal, enrichedAluminium,
        darkMetal, lithium, aluminium, gold, goldGlass,

    // advanced
        ademarium, pyrolite,
        iron, calcium, silicon,

        cannedNeoplasm, trainedNeoplasm,

        resonanceCrystal,
        
    // technologies
        cog, armorPlate, tinCan,
        bioprocessor, advBioprocessor,
        accumulator, advAccumulator,
        shieldGen, advShieldGen,
        memoryCard,
        
    // fruits
        leptine, leptineSeed,
    
    // other
        theimpossible
    ;

    public static final Seq<Item> kudolItems = new Seq<>();

    public static void load() {

        // region kudol

        // some space so the "kudol" label will be seen on the minimap
        
        // region standart

        volcanicSand = new Item("volcanic-sand", volcsand3) {{
            cost = 0.25f;
        }};

        solidNeoplasm = new Item("solid-neoplasm", neoplasm3) {{
            cost = 0.5f;
        }};

        hematite = new Item("hematite", hematite3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        tin = new Item("tin", tin3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        pegmatite = new Item("pegmatite", kaut3) {{
            hardness = 1;
            cost = 0.5f;
        }};

        bauxite = new Item("bauxite", kaut2) {{
            hardness = 2;
            cost = 0.5f;
        }};

        enrichedMetal = new Item("enriched-metal", wnb4) {{
            cost = 0f;
        }};

        enrichedAluminium = new Item("enriched-aluminium", wnb6) {{
            cost = 0f;
        }};

        darkMetal = new Item("dark-metal", dark6) {{
            cost = 0.75f;
        }};

        lithium = new Item("lithium", lithium3) {{
            cost = 0.75f;
            charge = 0.5f;
        }};

        aluminium = new Item("aluminium", wnb8) {{
            cost = 0.75f;
        }};

        gold = new Item("gold", gold3) {{
            cost = 1f;
        }};

        goldGlass = new Item("gold-glass", gglass3) {{
            cost = 2f;
        }};

        // endregion

        // region advanced

        ademarium = new Item("ademarium", ademarium3) {{
            cost = 2.5f;
        }};

        pyrolite = new Item("pyrolite", purple3) {{
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

        cannedNeoplasm = new Item("canned-neoplasm", flesh3) {{
            cost = 2f;
            flammability = 0.25f;
            frames = 7;
            frameTime = 15f;
        }};

        trainedNeoplasm = new Item("trained-neoplasm", flesh2) {{
            cost = 2f;
            flammability = 0.25f;
            frames = 7;
            frameTime = 15f;
        }};

        resonanceCrystal = new Item("resonance-crystal", purple3) {{
            cost = 3f;
            flammability = 0.22f;
            explosiveness = 0.22f;
            charge = 0.5f;
        }};

        // endregion

        // region technologies

        cog = new ItemTechnology("cog", wnb6) {{
            cost = 4f;
            itemReq = with(darkMetal, 1, aluminium, 4);
            outputAmount = 6;
        }};

        armorPlate = new ItemTechnology("armor-plate", gold2) {{
            cost = 4f;
            itemReq = with(darkMetal, 3, aluminium, 3);
            outputAmount = 4;
        }};

        tinCan = new ItemTechnology("tin-can", tin2) {{
            cost = 1f;
            itemReq = with(TItems.darkMetal, 1, TItems.tin, 1);
        }};

        bioprocessor = new ItemTechnology("bioprocessor", neoplasm2) {{
            cost = 6f;
            flammability = 0.5f;
            explosiveness = 0.5f;
            itemReq = with(TItems.darkMetal, 1, TItems.aluminium, 2, TItems.lithium, 1, TItems.trainedNeoplasm, 1);
            frames = 7;
            frameTime = 15f;
        }};

        advBioprocessor = new ItemTechnology("adv-bioprocessor", neoplasm1) {{
            cost = 10f;
            flammability = 0.25f;
            explosiveness = 0.25f;
            frames = 7;
            frameTime = 15f;
        }};

        accumulator = new ItemTechnology("accumulator", lithium2) {{
            cost = 6f;
            explosiveness = 0.75f;
            flammability = 0.75f;
            itemReq = with(TItems.darkMetal, 4, TItems.aluminium, 2, TItems.lithium, 4);
            outputAmount = 2;
        }};

        advAccumulator = new ItemTechnology("adv-accumulator", lithium1) {{
            cost = 10f;
            explosiveness = 0.75f;
            flammability = 0.75f;
        }};

        shieldGen = new ItemTechnology("shield-gen", gold2) {{
            cost = 6f;     
        }};

        advShieldGen = new ItemTechnology("adv-shield-gen", gold1) {{
            cost = 10f;     
        }};

        memoryCard = new ItemTechnology("memory-card", wnb7) {{
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

        // region other

        theimpossible = new Item("theimpossible", Color.red) {{
            cost = flammability = explosiveness = radioactivity = charge = hardness = 2147483647;
            hidden = !debug;
        }};

        // endregion

        // endregion

        kudolItems.addAll(
        /* standart */
            volcanicSand, solidNeoplasm, hematite, tin,
            bauxite, pegmatite,

            darkMetal, aluminium, lithium, gold, goldGlass,

            enrichedMetal, enrichedAluminium,

        /* advanced */ 
            ademarium, pyrolite, iron, calcium,
            silicon,

            cannedNeoplasm, trainedNeoplasm,
            
        /* technologies */ 
            cog, armorPlate, bioprocessor, advBioprocessor, accumulator,
            advAccumulator, shieldGen, advShieldGen, memoryCard, tinCan,
            
        /* fruits */
            leptine, leptineSeed
        );

        new Item("random"){
            {
                Events.run(EventType.Trigger.beforeGameUpdate,()->{
                    if(Math.random()>.25)return;
                    Item r=Vars.content.items().random();
                    explosiveness=r.explosiveness;
                    flammability=r.flammability;
                    radioactivity=r.radioactivity;
                    charge=r.charge;
                    hidden=r.hidden;
                    color=r.color;
                });
                cost=-1.0F;
            }
        };
    }
}