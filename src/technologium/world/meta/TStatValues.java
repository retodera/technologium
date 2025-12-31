package technologium.world.meta;

import mindustry.Vars;
import mindustry.maps.Map;
import mindustry.type.ItemStack;
import mindustry.ui.Styles;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import arc.util.*;

import static mindustry.Vars.*;

public class TStatValues {
    /**fixed this shit to really check either floors or walls or both */
    public static StatValue blocks(Attribute attr, boolean floating, float scale, boolean startZero, boolean checkFloors, boolean checkWalls){
        if(!checkFloors && !checkWalls) return table -> {};
        else return table -> table.table(c -> {
            Runnable[] rebuild = {null};
            Map[] lastMap = {null};

            rebuild[0] = () -> {
                c.clearChildren();
                c.left();

                if(state.isGame()){
                    var blocks = Vars.content.blocks()
                    .select(block -> (checkFloors && block instanceof Floor) || !(!checkWalls || block instanceof Floor) && indexer.isBlockPresent(block) && block.attributes.get(attr) != 0 && !((block instanceof Floor f && f.isDeep()) && !floating))
                    .with(s -> s.sort(f -> f.attributes.get(attr)));

                    if(blocks.any()){
                        int i = 0;
                        for(var block : blocks){

                            StatValues.blockEfficiency(block, block.attributes.get(attr) * scale, startZero).display(c);
                            if(++i % 5 == 0){
                                c.row();
                            }
                        }
                    }else{
                        c.add("@none.inmap");
                    }
                }else{
                    c.add("@stat.showinmap");
                }
            };

            rebuild[0].run();

            //rebuild when map changes.
            c.update(() -> {
                Map current = state.isGame() ? state.map : null;

                if(current != lastMap[0]){
                    rebuild[0].run();
                    lastMap[0] = current;
                }
            });
        });
    }

    public static StatValue itemBoosters(String unit, float timePeriod, float speedBoost, float rangeBoost, float damageBoost, ItemStack[] items){
        return table -> {
            table.row();
            table.table(c -> {
                c.table(Styles.grayPanel, b -> {
                    b.table(it -> {
                        for(ItemStack stack : items){
                            if(timePeriod < 0){
                                it.add(StatValues.displayItem(stack.item, stack.amount, true)).pad(10f).padLeft(15f).left();
                            }else{
                                it.add(StatValues.displayItem(stack.item, stack.amount, timePeriod, true)).pad(10f).padLeft(15f).left();
                            }
                            it.row();
                        }
                    }).left();

                    b.table(bt -> {
                        bt.right().defaults().padRight(3).left();
                        if(rangeBoost != 0) bt.add("[lightgray]+[stat]" + Strings.autoFixed(rangeBoost / tilesize, 2) + "[lightgray] " + StatUnit.blocks.localized()).row();
                        if(speedBoost != 0) bt.add("[lightgray]" + unit.replace("{0}", "[stat]" + Strings.autoFixed(speedBoost, 2) + "[lightgray]"));
                        if(damageBoost != 0) bt.add("[lightgray]+[stat]" + Strings.autoFixed(damageBoost, 2) + "[lightgray] " + TStatUnit.timesDamage.localized()).row();
                    }).right().top().grow().pad(10f).padRight(15f);
                }).growX().pad(5).padBottom(-5).row();
            }).growX().colspan(table.getColumns());
            table.row();
        };
    }
}
