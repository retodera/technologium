package technologium;

import technologium.content.*;
import technologium.world.*;
import technologium.graphics.*;
import mindustry.mod.*;
import mindustry.type.Planet;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.Vars;
import mindustry.gen.LogicIO;
import arc.util.Log;
import technologium.logic.*;
import mindustry.logic.*;
import arc.func.*;
import arc.Core;
import mindustry.gen.Icon;

import java.util.Arrays;

import static mindustry.Vars.*;
import static technologium.TVars.*;

// previous name was "TModLoader", and previously there was a message: "totally NOT a reference to the tModLoader (mod loader for terraria or smth)"
public class Technologium extends Mod {
    public Technologium() {
        Log.info("[loading TECHNOLOGIUM]");

        //yea, i dunno why i added this
        maxSchematicSize = 1000;
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
        TSectors.load();
        TTechTrees.load();
        Log.info("[TECHNOLOGIUM content successfully loaded]");
    }

    @Override @SuppressWarnings(value = {})
    public void init(){
        Log.info("[initiating TECHNOLOGIUM]");
        TEmojis.load();
        TVars.load();
        settings.apply();

        for(Planet planet : Vars.content.planets()) {
            if(planet.name != "kudol") {
                TPlanets.kudol.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.kudolItems);
                planet.hiddenItems.addAll(TItems.kudolItems).removeAll(planet.itemWhitelist);
            }
            if(planet.name != "venjer") {
                TPlanets.venjer.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.venjerItems);
                planet.hiddenItems.addAll(TItems.venjerItems).removeAll(planet.itemWhitelist);
            }
            if(planet.name != "mitaplanet") {
                TPlanets.mita.hiddenItems.addAll(planet.itemWhitelist).removeAll(TItems.mitaItems);
                planet.hiddenItems.addAll(TItems.mitaItems).removeAll(planet.itemWhitelist);
            }
        }

        LAssembler.customParsers.putAll(
            "memoryio", (Func<String[],LStatement>) MemoryIO.MemoryIOStatement::read,
            "configprojector", (Func<String[],LStatement>) ConfigProjector.ConfigProjectorStatement::read
        );

        Arrays.<Prov<LStatement>>asList(
            MemoryIO.MemoryIOStatement::new,
            ConfigProjector.ConfigProjectorStatement::new
        ).forEach(prov -> LogicIO.allStatements.add(prov));

        if(Core.settings.getBool("tdiscord")) new BaseDialog("[][#00afff]TECHNOLOGIUM::DISCORD") {{
            cont.add("@jointdiscord").expandY();
            buttons.button("@openlink", Icon.discord, () -> {
                if(!Core.app.openURI(TVars.tdiscordURL)) {
                    Vars.ui.showErrorMessage("@linkfail");
                    Core.app.setClipboardText(tdiscordURL);
                }
            }).width(260);
            buttons.button("@copylink", Icon.copy, () -> {
                Core.app.setClipboardText(tdiscordURL);
                Vars.ui.showInfoFade("@copied");
            }).width(260);
            buttons.button("@back", Icon.left, () -> hide()).width(260);
            buttons.button("@dontshow", Icon.cancel, () -> {
                Core.settings.put("tdiscord", false);
                hide();  
            }).width(260);
            show();
        }};

        Log.info("[TECHNOLOGIUM initialization complete]");
    }
}