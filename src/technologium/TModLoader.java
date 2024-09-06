package technologium;

import mindustry.content.Planets;
import technologium.content.*;
import mindustry.mod.*;

public class TModLoader extends Mod {

    public TModLoader() {
        // uhhh what to put here, who knows?
    }

    @Override
    public void loadContent() {
        Log.info("Loading Technologium content...");
        TStatusEffects.load();
        TItems.load();
        TLiquids.load();
        TBlocks.load();

        Planets.erekir.hiddenItems.addAll(TItems.kudolItems);
        Planets.serpulo.hiddenItems.addAll(TItems.kudolItems);
    }
}