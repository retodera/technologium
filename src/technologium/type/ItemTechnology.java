package technologium.type;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;

public class ItemTechnology extends Item {
    /**standart item cost of the technology*/
    public ItemStack[] itemReq = ItemStack.with(Items.copper, 1);
    /**standart liquid cost of the technology*/
    public LiquidStack[] liquidReq = LiquidStack.with();
    /**standart amount of output technologies*/
    public int outputAmount = 1;

    public ItemTechnology(String name) {
        super(name);
    }

    public ItemTechnology(String name, Color color) {
        super(name, color);
    }
}
