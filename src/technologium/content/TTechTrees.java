package technologium.content;

import arc.struct.Seq;
import mindustry.game.Objectives.*;

import static mindustry.content.TechTree.*;
import static mindustry.content.Liquids.*;
import static technologium.content.TBlocks.*;
import static technologium.content.TPlanets.*;
import static technologium.content.TItems.*;
import static technologium.content.TLiquids.*;
import static technologium.content.TUnitTypes.*;
import static technologium.content.TSectors.*;

public class TTechTrees {
    public static void load(){
        //region kudol

        TPlanets.kudol.techTree = nodeRoot("kudol", kudol, () -> {

            //region blocks

            node(coreTorch, () -> {

                //region production

                node(metallicDrill, () -> {
                    node(advancedDrill);
                    node(metallicPlasmaBore, () -> {
                        node(miniPlasmaBore, () -> {
                        });
                    });
                });

                //region crafting

                node(arcFurnace,
                Seq.with(new OnSector(pegmatiteMountains)), () -> {
                    node(mixer, Seq.with(new Research(theimpossible)), () -> {});
                    node(filter, Seq.with(new Research(theimpossible)), () -> {});
                    node(itemConstructor, Seq.with(new OnSector(noMansLand)), () -> {
                        //node(arcSmelter);
                    });
                });

                //region cores

                node(coreBlaze, () -> {          
                });

                //region distribution

                node(metallicConveyor, () -> {
                    node(metallicRouter, () -> {
                        node(metallicDistributor);
                        node(metallicJunction);
                        node(metallicBridgeConveyor, () -> {
                            node(mechanicalDriver, () -> {
                            });
                        });
                        node(metallicOverflowGate, () -> {
                            node(metallicUnderflowGate);
                        });
                        node(metallicSorter, () -> {});
                        node(metallicContainer, () -> {
                            node(metallicUnloader, () -> {
                                node(metallicVault);
                            });
                        });
                    });
                });

                //region liquids

                node(improvedConduit, () -> {
                    node(improvedLiquidJunction, () -> {
                        node(improvedLiquidRouter);
                        node(improvedLiquidBridge);
                        node(improvedLiquidSorter);
                        node(improvedLiquidContainer);
                    });
                    node(thermoConduit);
                });

                //region power

                node(thermalPlate, () -> {
                    node(energeticNode, () -> {
                        node(energeticNodeLarge);
                        node(lithiumBattery, () -> {}); 
                    });
                });

                //region logic

                node(message, () -> {
                    node(energeticProcessor, () -> {
                        node(plasmaProcessor, () -> {
                            node(gammaProcessor);
                        });
                        node(switchBlock);
                        node(cell, () -> {
                            node(bank);
                            node(stringCell);
                        }); 
                        node(borderlessDisplayMini, () -> {
                            node(borderlessDisplay);
                            node(projector);
                        });
                    });
                });

                //region effect

                node(radar, () -> {
                    node(longRangeRadar, () -> {});
                });

                //region walls

                node(metallicWall, () -> {
                    node(metallicWallLarge);
                    node(armoredWall, () -> {
                        node(armoredWallLarge);
                    });
                });

                //region turrets

                node(comet, () -> {
                    node(constellation);
                });

                //region units

                node(unitFabricator, () -> {
                    node(cobra, () -> {
                    }); 
                    node(blade, () -> {
                        node(saber, () -> {
                        });
                    });
                    node(mercury, () -> {
                    });
                    node(metallicPayloadConveyor, () -> { 
                    });
                });
            });
        
                //region items

            nodeProduce(hematite, () -> {
                nodeProduce(tin, () -> {});
                nodeProduce(pegmatite, () -> {
                    nodeProduce(lithium, () -> {});
                });
                nodeProduce(darkMetal, () -> {
                    nodeProduce(enrichedMetal, () -> {});
                    node(cog, Seq.with(new Research(itemConstructor)), () -> {
                        nodeProduce(armorPlate, () -> {});
                        node(bioprocessor, Seq.with(new Research(trainedNeoplasm)), () -> {
                            nodeProduce(advBioprocessor, () -> {});
                        });
                        nodeProduce(shieldGen, () -> {
                            nodeProduce(advShieldGen, () -> {});
                        });
                        nodeProduce(accumulator, () -> {
                            nodeProduce(advAccumulator, () -> {});
                        });
                        nodeProduce(memoryCard, () -> {});
                    });
                    nodeProduce(bauxite, () -> {
                        nodeProduce(enrichedAluminium, () -> {});
                        nodeProduce(aluminium, () -> {
                            nodeProduce(cannedNeoplasm, () -> {
                                nodeProduce(trainedNeoplasm, () -> {}); 
                            });
                            nodeProduce(gold, Seq.with(new Research(mixer)), () -> {
                                nodeProduce(goldGlass, () -> {});
                                nodeProduce(uranium, () -> {
                                    nodeProduce(enrichedUranium, () -> {
                                        nodeProduce(uraniumCell, () -> {});
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //region liquids
            
            nodeProduce(neoplasm, () -> {
                nodeProduce(water, () -> {
                    nodeProduce(liquidNitrogen, () -> {

                    });
                });
            });

            //region sectors

            node(initialization, () -> {
                node(pegmatiteMountains, 
                Seq.with(new SectorComplete(initialization)), () -> {
                    node(noMansLand, 
                    Seq.with(new SectorComplete(pegmatiteMountains)), () -> {
                    });
                });
            });
        });

        //region venjer

        TPlanets.venjer.techTree = nodeRoot("venjer", venjer, () -> {
        });
    }
}
