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

        for (Planet planet : Vars.content.planets()) {
            if (planet.name != "kudol")
                planet.hiddenItems.addAll(TItems.kudolItems);
        }
    }
}