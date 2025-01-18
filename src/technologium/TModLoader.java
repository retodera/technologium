package technologium;

import technologium.content.*;
import technologium.world.*;
import technologium.graphics.*;
import mindustry.mod.*;
import mindustry.type.Planet;
import mindustry.Vars;
import mindustry.gen.LogicIO;
import arc.util.Log;
import technologium.logic.*;
import mindustry.logic.*;
import arc.func.*;
import java.util.stream.Stream;

import static mindustry.Vars.maxSchematicSize;
import static technologium.TVars.*;

//totally NOT a reference to the tModLoader (mod loader for terraria or smth)
public class TModLoader extends Mod {

    public TModLoader() {
        //yea, i dunno why i added this
        maxSchematicSize = 1000;
        Log.info("[loading TECHNOLOGIUM]");
    }
    
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
        Log.info("[TECHNOLOGIUM content successfully loaded]");
    }

    @Override @SuppressWarnings(value = {})
    public void init(){
        Log.info("[initiating TECHNOLOGIUM]");
        TEmojis.load();
        TVars.load();
        settings.apply();

        for (Planet planet : Vars.content.planets()) {
            // changed "planet.name == "(planet name)" to "!planet.name.contains("(planet name)")", planning to add asteroids with same items as the planets do and they will have names like "(planet name)-asteroid"
            if (!planet.name.contains("kudol")) {
                planet.hiddenItems.addAll(TItems.kudolItems).removeAll(planet.itemWhitelist);
                TPlanets.kudol.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.kudolItems);
            }
            if (!planet.name.contains("venjer")) {
                planet.hiddenItems.addAll(TItems.venjerItems).removeAll(planet.itemWhitelist);
                TPlanets.venjer.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.venjerItems);
            }
            if (!planet.name.contains("mitaplanet")) {
                planet.hiddenItems.addAll(TItems.mitaItems).removeAll(planet.itemWhitelist);
                TPlanets.mita.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.mitaItems);
            }
        }

        LAssembler.customParsers.putAll(
            "memoryio", (Func<String[],LStatement>) MemoryIOFull.MemoryIOStatement::read,
            "configprojector", (Func<String[],LStatement>) ConfigProjectorFull.ConfigProjectorStatement::read
        );

        Stream.<Prov<LStatement>>of(
            MemoryIOFull.MemoryIOStatement::new,
            ConfigProjectorFull.ConfigProjectorStatement::new
        ).forEach(prov -> LogicIO.allStatements.add(prov));
    }
}