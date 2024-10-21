package technologium.content;

import arc.struct.Seq;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.*;
import static technologium.content.TBlocks.*;
import static technologium.content.TPlanets.*;
import static technologium.content.TItems.*;
import static technologium.content.TLiquids.*;
import static technologium.content.TUnitTypes.*;

public class TTechTree {
    public static void load(){
        TPlanets.kudol.techTree = nodeRoot("kudol", coreTorch, () -> {
            //region production
            node(darkDrill, () -> {
                node(darkPlasmaBore);
            });
            //endregion
            //region items
            node(hematite, () -> {
                node(tin);
                node(darkMetal, () -> {
                    node(lithium);
                });
                node(bauxite, () -> {
                    node(aluminium);
                });
            });
            //endregion
            //region cores
            node(coreBlaze, () -> {          
            });
            //endregion
            //region distrubution
            node(darkConveyor, () -> {
                node(darkRouter, () -> {
                    node(darkDistributor);
                    node(darkJunction);
                    node(darkBridgeConveyor, () -> {
                        node(plasmDriver);
                    });
                    node(darkUnloader, () -> {
                        node(darkContainer, () -> {
                            node(darkVault);
                        });
                    });
                });
            });
            //endregion
        });
    }
}
