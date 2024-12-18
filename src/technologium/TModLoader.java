package technologium;

import technologium.content.*;
import technologium.world.TMusic;
import mindustry.mod.*;
import mindustry.type.Planet;
import mindustry.Vars;
import arc.util.*;

public class TModLoader extends Mod {

    public TModLoader() {
        Log.info("[loading TECHNOLOGIUM]");
    }
    @Override
    public void loadContent() {
        Log.info("[T-music]");
        TMusic.load();
        Log.info("[T-teams]");
        TTeams.load();
        Log.info("[T-status_effects]");
        TStatusEffects.load();
        Log.info("[T-items]");
        TItems.load();
        Log.info("[T-liquids]");
        TLiquids.load();
        Log.info("[T-units]");
        TUnitTypes.load();
        Log.info("[T-blocks]");
        TBlocks.load();
        Log.info("[T-planets]");
        TPlanets.load();
        Log.info("[T-tech_trees]");
        KudolTechTree.load();

        for (Planet planet : Vars.content.planets()) {
            if (planet.name != "kudol") {
                planet.hiddenItems.addAll(TItems.kudolItems).removeAll(planet.itemWhitelist);
                TPlanets.kudol.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.kudolItems);
            }
        }
    }
}