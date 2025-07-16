package multicraft.ui;

import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.core.*;
import mindustry.type.*;
import mindustry.ui.*;

//v8
public class TItemImage extends Stack{

    public TItemImage(TextureRegion region, int amount){

        add(new Table(o -> {
            o.left();
            o.add(new Image(region)).size(32f).scaling(Scaling.fit);
        }));

        if(amount != 0){
            add(new Table(t -> {
                t.left().bottom();
                t.add(amount >= 1000 ? UI.formatAmount(amount) : amount + "").style(Styles.outlineLabel);
                t.pack();
            }));
        }
    }

    public TItemImage(ItemStack stack){
        this(stack.item.uiIcon, stack.amount);
    }

    public TItemImage(PayloadStack stack){
        this(stack.item.uiIcon, stack.amount);
    }
}
