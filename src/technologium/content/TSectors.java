package technologium.content;

import static technologium.content.TPlanets.*;

import mindustry.type.SectorPreset;

public class TSectors {
    public static SectorPreset
    /* Kudol */ initialization, pegmatiteMountains, noMansLand, goldenCrater, neoplasmResearchCenter;

    public static void load() {

        initialization = new SectorPreset("initialization", kudol, 0) {{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
        }};

        pegmatiteMountains = new SectorPreset("pegmatiteMountains", kudol, 46) {{
            difficulty = 3;
            captureWave = 20;
        }};

        noMansLand = new SectorPreset("noMansLand", kudol, 202) {{
            difficulty = 4;
            captureWave = 30;
        }};

        goldenCrater = new SectorPreset("goldenCrater", kudol, 113) {{
            difficulty = 3;
            captureWave = 25;
        }};

        // neoplasmResearchCenter = new TSectorPreset("neoplasmResearchCenter", kudol, 69)  {{
        //     difficulty = 5;
        //     attackAfterWaves = true;
        //     captureWave = 14;
        // }};
    }
}
