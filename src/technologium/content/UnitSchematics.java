package technologium.content;

import technologium.type.*;
import technologium.type.UnitSchematic;

import static technologium.content.TItems.*;
import static technologium.content.TLiquids.*;
import static technologium.content.TUnitTypes.*;
import static technologium.content.TBlocks.*;

public class UnitSchematics {
    public static UnitSchematic

    // t2
    saberSchematic, marsSchematic, pythonSchematic,

    //t3
    napalmSchematic, phobosSchematic, impalerSchematic;

    public static void load() {
        // region t2
        saberSchematic = new UnitSchematic("saber"){{
            steps.addAll(
                new Step("saber"){{
                    items = TItemSeq.with(armorPlate, 55);
                    payloads = TPayloadSeq.with(blade, 1);
                    time = 30f;
                }},
                new Step("saber-weapon-l"){{
                    items = TItemSeq.with(lithium, 25, accumulator, 10);
                    time = 12f;
                }},
                new Step("saber-weapon-r"){{
                    items = TItemSeq.with(lithium, 25, accumulator, 10);
                    time = 12f;
                }}
            );
            result = saber;
        }};

        marsSchematic = new UnitSchematic("mars"){{
            steps.addAll(
                new Step("mars"){{
                    items = TItemSeq.with(aluminium, 40, armorPlate, 25);
                    payloads = TPayloadSeq.with(mercury, 1);
                    time = 40f;
                }},
                new Step("mars-minigun"){{
                    items = TItemSeq.with(armorPlate, 25, cog, 25, lithium, 10);
                    time = 25f;
                }},
                new Step("air-assault-defense"){{
                    items = TItemSeq.with(darkMetal, 15, lithium, 5);
                    time = 10f;
                }},
                new Step("air-assault-defense"){{
                    items = TItemSeq.with(darkMetal, 15, lithium, 5);
                    time = 10f;
                }}
            );
            result = mars;
        }};

        pythonSchematic = new UnitSchematic("python"){{
            steps.addAll(
                new Step("python"){{
                    items = TItemSeq.with(armorPlate, 30);
                    payloads = TPayloadSeq.with(cobra, 1);
                    time = 35f;
                }},
                new Step("python-weapon"){{
                    items = TItemSeq.with(darkMetal, 40, armorPlate, 25);
                    time = 25f;
                }},
                new Step("python-weapon"){{
                    items = TItemSeq.with(darkMetal, 40, armorPlate, 25);
                    mirror = true;
                    time = 25f;
                }}
            );
            result = python;
        }};

        // endregion

        // region t3
    }
}
