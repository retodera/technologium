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

public class KudolTechTree {
    public static void load(){
        TPlanets.kudol.techTree = nodeRoot("Kudol", kudol, () -> {
            //blocks
            node(coreTorch, () -> {
                //region production
                node(darkDrill, () -> {
                    node(darkPlasmaBore);
                });
                //endregion
                //region cores
                node(coreBlaze, () -> {          
                });
                //endregion
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
                //endregion
                //region liquids
                node(improvedConduit, () -> {
                    node(improvedLiquidJunction, () -> {
                        node(improvedLiquidRouter);
                        node(improvedBridgeConduit);
                        node(improvedLiquidContainer);
                    });
                    node(thermoConduit);
                });
                //endregion
                //region power
                node(thermalPlate, () -> {
                    node(darkPowerNode, () -> {
                        node(darkPowerNodeLarge);
                        node(lithiumBattery); 
                    });
                });
            });
            //items
            node(hematite, () -> {
                node(tin);
                node(lithium);
                node(darkMetal, () -> {
                    node(cog);
                    node(bauxite, () -> {
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
            //liquids
            node(neoplasm, () -> {
                node(water, () -> {
                    node(liquidNitrogen);
                    node(carbon);
                    node(liquidPlasma);
                });
            });
        });
    }
}
