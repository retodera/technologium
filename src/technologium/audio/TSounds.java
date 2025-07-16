package technologium.audio;

import arc.audio.Sound;
import mindustry.Vars;

public class TSounds {
    public static Sound welcomer, scary, vineboom;

    public static void load() {
        welcomer = loadSound("t-welcomer");
        scary = loadSound("t-scary");
        vineboom = loadSound("t-vineboom");
    }

    static Sound loadSound(String name) {
        return Vars.tree.loadSound(name);
    }
}
