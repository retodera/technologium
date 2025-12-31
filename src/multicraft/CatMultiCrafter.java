package multicraft;

import arc.math.*;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.world.blocks.payloads.*;
import mindustry.world.modules.*;

import static mindustry.Vars.*;

/**
 * Categorial MultiCrafter.
 * still uses recipes, but unites them into categories
 * to make the recipe selection more compact, and
 * automatically chooses a recipe based on input. 
 */
public class CatMultiCrafter extends MultiCrafter {
    public Seq<RecipeCat> categories = null;
    public Effect changeCatEffect = Fx.rotateBlock;

    public CatMultiCrafter(String name) {
        super(name);
        changeRecipeEffect = Fx.none;
        nullableRecipe = true; //DO NOT CHANGE UNLESS YOU WANT YOUR GAME TO CRASH. (NullPointerException)

        config(Integer.class, CatMultiCrafterBuild::changeCatRemote);
    }

    public class CatMultiCrafterBuild extends MultiCrafterBuild {
        public int catId;

        public void changeCatRemote(int index) {
            int newIndex = Mathf.clamp(index, 0, categories.size - 1);
            if (newIndex != recipeId) {
                catId = newIndex;
                createEffect(changeRecipeEffect);
                craftingTime = 0f;
                if (!Vars.headless) rebuildHoveredInfo();
            }
        }

        public RecipeCat getCatId() {
            return categories.get(catId);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return hasItems && 
                (allAvailableItems().contains(item)
                || consumesItem(item))
                && items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return hasLiquids &&
                (allAvailableLiquids().contains(liquid)
                || consumesLiquid(liquid)) &&
                liquids.get(liquid) < liquidCapacity;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return hasPayloads && this.payload == null &&
                allAvailablePayloads().contains(payload.content()) &&
                payloads.get(payload.content()) < payloadCapacity;
        }

        @Override
        public Seq<Item> allAvailableItems() {
            Seq<Item> allItems = new Seq<>();
            for(Recipe r : curCatRecipes())
                for(Item i : r.input.itemsUnique)
                    allItems.add(i);
            return allItems;
        }

        @Override
        public Seq<Liquid> allAvailableLiquids() {
            Seq<Liquid> allLiquids = new Seq<>();
            for(Recipe r : curCatRecipes())
                for(Liquid l : r.input.fluidsUnique)
                    allLiquids.add(l);
            return allLiquids;
        }

        @Override
        public Seq<UnlockableContent> allAvailablePayloads() {
            Seq<UnlockableContent> allPayloads = new Seq<>();
            for(Recipe r : curCatRecipes())
                for(UnlockableContent c : r.input.payloadsUnique)
                    allPayloads.add(c);
            return allPayloads;
        }

        public Seq<Recipe> curCatRecipes() {
            return Seq.with(resolvedRecipes).retainAll(r -> r.category == getCatId());
        }

        @Override
        public void updateTile() {
            if(items == null) items = new ItemModule();       //I DON'T KNOW
            if(liquids == null) liquids = new LiquidModule(); //WHY THIS HAPPENS.
            setRecipeRemote(findRecipe());
            super.updateTile();
        }

        public int findRecipe() { //almost nobody uses payloads in crafts anyways, no need to program it yet
            var it = convert(items);
            var li = convert(liquids);
            if(it.isEmpty() && li.isEmpty()) return -1;
            Seq<Recipe> recipes = Seq.with(curCatRecipes()).retainAll(r -> {
                var it1 = Seq.with(it);
                it1.removeAll(r.output.itemsUnique);
                var li1 = Seq.with(li);
                li1.removeAll(r.output.fluidsUnique);
                return r.input.itemsUnique.containsAll(it1) && r.input.fluidsUnique.containsAll(li1);
            });
            if(recipes.size > 0) {
                recipes.sort((r1, r2) -> {
                    float p1 = r1.input.items.length + r1.input.fluids.length;
                    float p2 = r2.input.items.length + r2.input.fluids.length;
                    return Float.compare(p1, p2);
                });
                return resolvedRecipes.indexOf(recipes.get(0));
            }
            else return -1;
        }

        public Seq<Item> convert(ItemModule items) {
            Seq<Item> i = new Seq<>();
            for(Item item : content.items()) 
                if(items.get(item) > 0) i.add(item);
            return i;
        }

        public Seq<Liquid> convert(LiquidModule liquids) {
            Seq<Liquid> l = new Seq<>();
            for(Liquid liquid : content.liquids()) 
                if(liquids.get(liquid) > 0) l.add(liquid);
            return l;
        }

        public Seq<Item> recipesOutputItem() {
            Seq<Item> i = new Seq<>();
            for(Recipe r : curCatRecipes())
                for(Item item : r.output.itemsUnique)
                    i.add(item);
            return i;
        }

        public Seq<Liquid> recipesOutputLiquid() {
            Seq<Liquid> l = new Seq<>();
            for(Recipe r : curCatRecipes())
                for(Liquid liquid : r.output.fluidsUnique)
                    l.add(liquid);
            return l;
        }

        @Override
        public void buildConfiguration(Table table) {
            for(int i = 0; i < categories.size; i++) {
                if(i != 0 && i % 2 == 0) table.row();
                int fi = i;
                RecipeCat cat = categories.get(i);
                Table t = new Table();
                t.image(cat.icon());
                t.add(cat.localized());
                ImageButton button = new ImageButton(Styles.clearTogglei);
                button.replaceImage(t);
                button.changed(() -> catId = fi);
                button.update(() -> button.setChecked(catId == fi));
                table.add(button).grow().pad(8f).margin(10f);
            }
        }

        @Override
        public Object config() {
            return catId;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(catId);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            catId = Mathf.clamp(read.i(), 0, categories.size - 1);
        }
    }
}
