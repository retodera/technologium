package technologium.content;

import mindustry.game.*;

public class TLoadouts {
    public static Schematic 
    coreTorch,
    coreBlaze;

    public static void load() {
        coreTorch = Schematics.readBase64("bXNjaAF4nGNgYmBiZmDJS8xNZeBMzi9KDckvSs5g4E5JLU4uyiwoyczPY2BgYMtJTErNKWZgio5lZOAp0QUp1C0Bq2RgYGSAAADVOxHm");
        coreBlaze = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZeBMzi9KdcpJrEpl4E5JLU4uyiwoyczPY2BgYMtJTErNKWZgio5lZOAp0QUp1E0Cq2RgYAQhIAEA0HkRxg==");
    }
}
