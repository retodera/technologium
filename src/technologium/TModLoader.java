package technologium;

import technologium.content.*;
import mindustry.mod.*;
import mindustry.type.Planet;

import mindustry.Vars;

public class TModLoader extends Mod {

    @Override
    public void loadContent() {
        TStatusEffects.load();
        TItems.load();
        TLiquids.load();
        TBlocks.load();
        TUnitTypes.load();
        TPlanets.load();
        TTechTree.load();

        for (Planet planet : Vars.content.planets()) {
            if (planet.name != "kudol")
                planet.hiddenItems.addAll(TItems.kudolItems);
                TPlanets.kudol.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.kudolItems);
        }
    }
}