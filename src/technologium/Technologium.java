package technologium;

import technologium.content.*;
import mindustry.content.Liquids;
import arc.func.*;
import arc.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.mod.*;
import technologium.audio.*;
import technologium.graphics.*;
import technologium.logic.*;
import technologium.world.meta.TAttributes;

import java.util.Arrays;

import static mindustry.Vars.*;

public class Technologium extends Mod {
    public static Mods.LoadedMod tmod;

    public Technologium() {
        maxSchematicSize = 999;
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
        UnitSchematics.load();
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

        TTeams.addLogicVars();
    }
}