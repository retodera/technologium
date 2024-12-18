package technologium.world;

import arc.audio.Music;
import mindustry.Vars;

//stolen from NightScape
public class TMusic {
    public static Music metalstrong;
    public static void load() {
        metalstrong = loadMusic("metalstrong_theme");
    }    

    private static Music loadMusic(String name){
        return Vars.tree.loadMusic(name);
    }
}