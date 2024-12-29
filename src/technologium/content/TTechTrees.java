package technologium.content;

import arc.struct.Seq;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.*;
import static mindustry.content.Liquids.*;
import static technologium.content.TBlocks.*;
import static technologium.content.TPlanets.*;
import static technologium.content.TItems.*;
import static technologium.content.TLiquids.*;
import static technologium.content.TUnitTypes.*;

public class TTechTrees {
    public static void load(){
        //region kudol

        TPlanets.kudol.techTree = nodeRoot("kudol", kudol, () -> {

            //region blocks

            node(coreTorch, () -> {

                //region production

                node(darkDrill, () -> {
                    node(darkPlasmaBore, () -> {
                        node(miniPlasmaBore);
                        node(goldExtractor);
                    });
                });

                //region crafting

                node(arcFurnace, () -> {
                    node(enricher);
                    node(constructor, () -> {
                        //node(arcSmelter);
                    });
                });

                //region cores

                node(coreBlaze, () -> {          
                });

                //region distribution

                node(darkConveyor, () -> {
                    node(darkRouter, () -> {
                        node(darkDistributor);
                        node(darkJunction);
                        node(darkBridgeConveyor, () -> {
                            node(plasmaDriver);
                        });
                        node(darkUnloader, () -> {
                            node(darkContainer, () -> {
                                node(darkVault);
                            });
                        });
                    });
                });

                //region liquids

                node(improvedConduit, () -> {
                    node(improvedLiquidJunction, () -> {
                        node(improvedLiquidRouter);
                        node(improvedBridgeConduit);
                        node(improvedLiquidContainer);
                    });
                    node(thermoConduit);
                });

                //region power

                node(thermalPlate, () -> {
                    node(energeticNode, () -> {
                        node(energeticNodeLarge);
                        node(lithiumBattery); 
                    });
                });
            });

            //region items

            node(hematite, () -> {
                node(tin);
                node(pegmatite, () -> {
                    node(lithium);
                });
                node(darkMetal, () -> {
                    node(enrichedMetal);
                    node(cog);
                    node(bauxite, () -> {
                        node(enrichedAluminium);
                        node(aluminium, () -> {
                            node(cannedNeoplasm, () -> {
                                node(trainedNeoplasm); 
                            });
                            node(gold, () -> {
                                node(goldGlass);
                                node(uranium, () -> {
                                    node(uraniumCell);
                                    node(stalinium);
                                });
                            });
                        });
                    });
                });
            });

            //region liquids
            
            node(neoplasm, () -> {
                node(water, () -> {
                    node(liquidNitrogen);
                    node(carbon);
                    node(liquidPlasma);
                });
            });
        });

        //region venjer

        TPlanets.venjer.techTree = nodeRoot("venjer", venjer, () -> {
        });
    }
}
