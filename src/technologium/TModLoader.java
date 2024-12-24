package technologium;

import technologium.content.*;
import technologium.world.*;
import technologium.graphics.*;
import mindustry.mod.*;
import mindustry.type.Planet;
import mindustry.Vars;

import static technologium.TVars.*;

//totally NOT a reference to the tModLoader (mod loader for terraria or smth)
public class TModLoader extends Mod {

    public TModLoader() {}
    
    @Override

    public void loadContent() {
        TMusic.load();
        TTeams.load();
        TStatusEffects.load();
        TItems.load();
        TLiquids.load();
        TUnitTypes.load();
        TBlocks.load();
        TPlanets.load();
        TTechTrees.load();
    }

    @Override
    
    public void init(){
        TEmojis.load();
        TVars.load();
        settings.apply();

        for (Planet planet : Vars.content.planets()) {
            if (planet.name != "kudol") {
                planet.hiddenItems.addAll(TItems.kudolItems).removeAll(planet.itemWhitelist);
                TPlanets.kudol.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.kudolItems);
            }
        }
    }
}