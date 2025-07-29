package technologium.content;

import static technologium.content.TPlanets.*;

import mindustry.type.SectorPreset;

public class TSectors {
    public static SectorPreset
    /* Kudol */ initialization, pegmatiteMountains, noMansLand, goldenCrater, neoplasmResearchCenter;

    public static void load() {

        initialization = new SectorPreset("initialization", kudol, 164) {{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
        }};

        pegmatiteMountains = new SectorPreset("pegmatiteMountains", kudol, 165) {{
            difficulty = 3;
            captureWave = 20;
        }};

        noMansLand = new SectorPreset("noMansLand", kudol, 35) {{
            difficulty = 4;
            captureWave = 30;
        }};

        goldenCrater = new SectorPreset("goldenCrater", kudol, 96) {{
            difficulty = 3;
            captureWave = 25;
        }};

        // neoplasmResearchCenter = new TSectorPreset("neoplasmResearchCenter", kudol, 0)  {{
        //     difficulty = 5;
        //     attackAfterWaves = true;
        //     captureWave = 14;
        // }};
    }
}
