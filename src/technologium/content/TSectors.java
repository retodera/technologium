package technologium.content;

import technologium.type.TSectorPreset;
import static technologium.content.TPlanets.*;

public class TSectors {
    public static TSectorPreset
    /* Kudol */ initialization, pegmatiteMountains, noMansLand, goldenCrater;

    public static void load() {

        initialization = new TSectorPreset("initialization", kudol, 0) {{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
        }};

        pegmatiteMountains = new TSectorPreset("pegmatiteMountains", kudol, 46) {{
            difficulty = 3;
            captureWave = 20;
        }};

        noMansLand = new TSectorPreset("noMansLand", kudol, 202) {{
            difficulty = 4;
            captureWave = 30;
        }};

        goldenCrater = new TSectorPreset("goldenCrater", kudol, 113) {{
            difficulty = 3;
            captureWave = 25;
        }};
    }
}
