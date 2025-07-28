package technologium;

import arc.Core;
import arc.Events;
import arc.func.Func;
import arc.func.Prov;
import arc.util.Log;
import mindustry.content.Liquids;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.LogicIO;
import mindustry.logic.LAssembler;
import mindustry.logic.LStatement;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import mindustry.world.Block;
import mindustry.world.blocks.ConstructBlock;
import technologium.audio.TMusic;
import technologium.audio.TSounds;
import technologium.content.*;
import technologium.entities.unit.DummyUnit;
import technologium.graphics.TEmojis;
import technologium.graphics.TShaders;
import technologium.logic.ConfigProjector;
import technologium.logic.MemoryIO;
//import technologium.util.VerySafe;
import technologium.world.blocks.multi.Patterns;
import technologium.world.blocks.unproportional.Test;
import technologium.world.meta.TAttributes;

import java.util.Arrays;

import static mindustry.Vars.*;


public class Technologium extends Mod {
    public static Mods.LoadedMod tmod;
    static {
        Log.level= Log.LogLevel.debug;
    }

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
        Patterns.load();

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

        // disabled
        //VerySafe.Mindustry.textBuffer(690);
        //VerySafe.Mindustry.maxInstructions(0x400);

        mods.getScripts().runConsole("""
                function int(value) {
                		return java.lang.Integer(value);
                }
                function long(value) {
                		return java.lang.Long(value);
                }
                function float(value) {
                		return java.lang.Float(value);
                }
                function short(value) {
                		return java.lang.Short(value);
                }
                function byte(value) {
                		return java.lang.Byte(value);
                }
                function double(value) {
                		return java.lang.Double(value);
                }
                function char(value) {
                		return java.lang.Character(value);
                }
""");
        DummyUnit.initialize_sdkjafsadfojihpaojgr();
        Events.on(EventType.BlockBuildBeginEvent.class, bbbe->{
            Building build = bbbe.tile.build;
            if(build instanceof ConstructBlock.ConstructBuild cb){
                Block current = cb.current;
                if(current instanceof Test t) {
                    int sx = bbbe.tile.x;
                    int sy= bbbe.tile.y;
                    boolean b=false;
                    for (int dx = sx-t.w; dx <= sx; dx++) {
                        for (int dy = sy-t.h; dy <= sy; dy++) {
                            if(dx==sx&&sy==dy)break;
                            Block block = world.tiles.get(dx, dy).block();
                            if(!(block instanceof Test test))continue;
                            if(test.w+dx>=sx||test.h+dy>=sy){
                                b=true;
                                break;
                            }
                        }
                    }
                    if(b){
                        cb.deconstruct(DummyUnit.getInstance(), cb.core(), 69);
                    }
                }
            }
        });
    }

}