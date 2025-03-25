package technologium.content;

import mindustry.type.SectorPreset;
import static technologium.content.TPlanets.*;

public class TSectors {
    public static SectorPreset
    /* Kudol */ initialization, pegmatiteMountains;

    public static void load() {

        initialization = new SectorPreset("initialization", kudol, 0) {{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
            showSectorLandInfo = true;
        }};

        pegmatiteMountains = new SectorPreset("pegmatiteMountains", kudol, 46) {{
            difficulty = 2;
            captureWave = 20;
            showSectorLandInfo = true;
        }};
    }

}
