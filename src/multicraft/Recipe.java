package multicraft;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;
import technologium.type.ItemTechnology;

public class Recipe {
    public IOEntry input;
    public IOEntry output;
    public float craftTime = 0f;
    @Nullable
    public Prov<TextureRegion> icon;
    @Nullable
    public Color iconColor;

    public Effect craftEffect = Fx.none;
    /**for {@linkplain CatMultiCrafter} */
    public @Nullable RecipeCat category;

    public Recipe() {}

    public Recipe(Item technology) {
        if(!(technology instanceof ItemTechnology)) throw new ArcRuntimeException("The item in the method Recipe(Item) has to be an instance of ItemTechnology.");
        ItemTechnology tech = (ItemTechnology)technology;
        input = new IOEntry() {{
            items = tech.itemReq;
            fluids = tech.liquidReq;
        }};
        output = new IOEntry() {{
            items = ItemStack.with(tech, tech.outputAmount);
        }};
    }

    public void cacheUnique() {
        input.cacheUnique();
        output.cacheUnique();
    }

    public boolean consumesItems() {
        return input.items.length > 0;
    }

    public boolean outputsItems() {
        return output.items.length > 0;
    }

    public boolean consumesLiquids() {
        return input.fluids.length > 0;
    }

    public boolean outputsLiquids() {
        return output.fluids.length > 0;
    }

    public boolean consumesPower() {
        return input.power > 0f;
    }

    public boolean outputsPower() {
        return output.power > 0f;
    }

    public boolean consumesHeat() {
        return input.heat > 0f;
    }

    public boolean outputsHeat() {
        return output.heat > 0f;
    }

    public boolean consumesPayloads() {
        return input.payloads.length > 0;
    }

    public boolean outputsPayloads() {
        return output.payloads.length > 0;
    }

    public boolean hasItems() {
        return consumesItems() || outputsItems();
    }

    public boolean hasLiquids() {
        return consumesLiquids() || outputsLiquids();
    }

    public boolean hasPower() {
        return consumesPower() || outputsPower();
    }

    public boolean hasHeat() {
        return consumesHeat() || outputsHeat();
    }

    public boolean hasPayloads() {
        return consumesPayloads() || outputsPayloads();
    }

    public int maxItemAmount() {
        return Math.max(input.maxItemAmount(), output.maxItemAmount());
    }

    public float maxFluidAmount() {
        return Math.max(input.maxFluidAmount(), output.maxFluidAmount());
    }

    public float maxPower() {
        return Math.max(input.power, output.power);
    }

    public float maxHeat() {
        return Math.max(input.heat, output.heat);
    }

    public int maxPayloadAmount() {
        return Math.max(input.maxPayloadAmount(), output.maxPayloadAmount());
    }

    @Override
    public String toString() {
        return "Recipe{" +
               "input=" + input +
               "output=" + output +
               "craftTime" + craftTime +
               "}";
    }
}
