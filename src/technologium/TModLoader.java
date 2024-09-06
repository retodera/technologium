package technologium;

import mindustry.content.Planets;
import technologium.content.*;
import mindustry.mod.*;

public class TModLoader extends Mod {

    @Override
    public void loadContent() {
        TStatusEffects.load();
        TItems.load();
        TLiquids.load();
        TBlocks.load();

        Planets.erekir.hiddenItems.addAll(TItems.kudolItems);
        Planets.serpulo.hiddenItems.addAll(TItems.kudolItems);
    }
}