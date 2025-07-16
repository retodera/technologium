package technologium.world.meta;

import mindustry.Vars;
import mindustry.maps.Map;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

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
}
