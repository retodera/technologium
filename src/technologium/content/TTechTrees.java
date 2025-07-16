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
        TPlanets.kudol.techTree = nodeRoot("kudol", kudol, () -> {

            // region blocks

            node(coreTorch, () -> {

                // region production

                node(metallicDrill, () -> {
                    node(advancedDrill);
                    node(metallicPlasmaBore, () -> {
                        node(miniPlasmaBore, () -> {
                        });
                    });
                });

                // endregion

                // region crafting

                node(arcFurnace, Seq.with(new OnSector(pegmatiteMountains)), () -> {
                    node(enricher, Seq.with(new SectorComplete(noMansLand)), () -> {});
                    node(filter, Seq.with(new OnSector(goldenCrater)), () -> {});
                    node(itemConstructor, Seq.with(new OnSector(noMansLand)), () -> {
                        node(arcSmelter, Seq.with(new OnSector(goldenCrater)), () -> {});
                    });
                });

                // endregion

                // region distribution

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

                // endregion

                // region liquids

                node(improvedConduit, () -> {
                    node(improvedLiquidJunction, () -> {
                        node(improvedLiquidRouter);
                        node(improvedLiquidBridge);
                        node(improvedLiquidSorter);
                        node(improvedLiquidContainer);
                    });
                    node(advancedConduit);
                });

                // endregion

                // region power

                node(thermalPlate, () -> {
                    node(energeticNode, () -> {
                        node(energeticNodeLarge);
                        node(lithiumBattery, () -> {
                            node(largeLithiumBattery);
                        }); 
                    });
                    node(thermalGenerator, () -> {
                        node(lithiumCombustionChamber);
                    });
                });

                // endregion

                // region logic

                node(message, () -> {
                    node(energeticProcessor, () -> {
                        node(plasmaProcessor, () -> {
                            node(gammaProcessor);
                        });
                        node(switchBlock);
                        node(memoryCell, () -> {
                            node(memoryBank);
                            node(stringMemoryCell);
                        }); 
                        node(borderlessDisplayMini, () -> {
                            node(borderlessDisplay);
                            node(projector);
                        });
                    });
                });

                // endregion

                // region effect

                node(radar, () -> {
                    node(longRangeRadar, () -> {});
                });
                node(miniMender, () -> {
                    node(miniShieldProjector);
                });
                node(coreBlaze, () -> {          
                });

                // endregion

                // region defense

                node(metallicWall, () -> {
                    node(metallicWallLarge);
                    node(armoredWall, () -> {
                        node(armoredWallLarge);
                    });
                });

                // endregion

                // region turrets

                node(comet, () -> {
                    node(constellation);
                    node(strike, () -> {
                        node(meteor, () -> {
                        });
                        node(needle, () -> {
                        });
                    });
                });

                // endregion

                // region units

                node(unitFabricator, () -> {
                    node(cobra, () -> {
                    }); 
                    node(blade, () -> {
                        node(saber, Seq.with(new Research(unitRefabricator)), () -> {
                        });
                    });
                    node(mercury, () -> {
                        node(mars, Seq.with(new Research(unitRefabricator)), () -> {
                        });
                    });
                    node(cobra, () -> {
                        node(python, Seq.with(new Research(unitRefabricator)), () -> { 
                        });
                    });
                    node(metallicPayloadConveyor, () -> {
                        node(largePayloadConveyor);
                    });
                    node(unitRefabricator, Seq.with(new Research(armorPlate), new Research(water), new Research(theimpossible)), () -> {
                    });
                });

                // endregion
            });
        
            // endregion

            // region items

            nodeProduce(hematite, () -> {
                nodeProduce(tin, () -> {});
                nodeProduce(pegmatite, () -> {
                    nodeProduce(lithium, () -> {});
                });
                nodeProduce(solidNeoplasm, () -> {});
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
                            nodeProduce(gold, Seq.with(new Research(enricher)), () -> {
                                nodeProduce(goldGlass, () -> {
                                    nodeProduce(molybdenum, () -> { 
                                    });
                                });
                            });
                        });
                    });
                });
            });

            // endregion

            // region liquids
            
            nodeProduce(neoplasm, () -> {
                nodeProduce(water, () -> {
                    nodeProduce(liquidNitrogen, () -> {

                    });
                });
            });

            // endregion

            // region sectors

            node(initialization, () -> {
                node(pegmatiteMountains, 
                Seq.with(new SectorComplete(initialization)), () -> {
                    node(noMansLand, 
                    Seq.with(new SectorComplete(pegmatiteMountains)), () -> {
                        node(goldenCrater, 
                        Seq.with(new SectorComplete(noMansLand)), () -> {
                        });
                    });
                });
            });

            // endregion
        });
        
        TPlanets.venjer.techTree = nodeRoot("venjer", venjer, () -> {
        });
    }
}
