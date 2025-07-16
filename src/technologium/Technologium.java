package technologium;

import technologium.audio.*;
import technologium.content.*;
import technologium.core.TEventControl;
import technologium.world.meta.*;
import technologium.graphics.*;
import mindustry.mod.*;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import technologium.logic.*;
import mindustry.logic.*;
import arc.func.*;
import arc.util.Timer;
import arc.Core;
import arc.Events;

import java.util.Arrays;

import static mindustry.Vars.*;


public class Technologium extends Mod {
    public static Mods.LoadedMod tmod;

    public Technologium() {
        maxSchematicSize = 1000;
        renderer.minZoom = Math.min(renderer.minZoom, 0.3f);
        renderer.maxZoom = Math.max(renderer.maxZoom, 100);
    }
    
    @Override
    public void loadContent() {
        tmod = mods.getMod(getClass());
        TMusic.load();
        TSounds.load();
        TTeams.load();
        TStatusEffects.load();
        Liquids.neoplasm.effect = TStatusEffects.neoplasmCovered;
        TAttributes.load();
        if(!headless) TShaders.init();
        TWeathers.load();
        TItems.load();
        TLiquids.load();
        TUnitTypes.load();
        TBlocks.load();
        TLoadouts.load();
        TPlanets.load();
        TSectors.load();
        TTechTrees.load();
    }

    @Override
    public void init(){
        TEmojis.load();
        TVars.load();

        Core.app.addListener(new TVars());
        LAssembler.customParsers.putAll(
            "memoryio", (Func<String[],LStatement>) MemoryIO.MemoryIOStatement::read,
            "configprojector", (Func<String[],LStatement>) ConfigProjector.ConfigProjectorStatement::read
        );

        Arrays.<Prov<LStatement>>asList(
            MemoryIO.MemoryIOStatement::new,
            ConfigProjector.ConfigProjectorStatement::new
        ).forEach(prov -> LogicIO.allStatements.add(prov));

        if (Core.settings.getBool("t-discord")) TVars.showDiscord();
    }
}