package technologium;

import java.lang.reflect.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import arc.*;
import arc.audio.Music;
import arc.graphics.Color;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.Dialog;
import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.*;
import technologium.audio.*;
import technologium.core.TEventControl;
import technologium.core.TFunny;
import technologium.game.TEventType.MusicChangeEvent;
import technologium.type.Fruit;
import technologium.ui.TUI;
import technologium.ui.dialogs.TSettingsMenuDialog;

import static mindustry.Vars.*;
import static technologium.game.TEventType.TTrigger.*;

public class TVars implements ApplicationListener {
    public static Calendar cal = new GregorianCalendar();

    public static AtlasRegion loadedLogo;

    public static TSettingsMenuDialog settings;
    public static TSoundControlReflect sound;
    public static TUI tui;
    public static TFunny funny;
    public static TEventControl events;

    public static boolean requireReload = false;
    public static boolean debug = Core.settings.getBool("tdebug", true);

    public static final String tdiscordURL = "https://discord.gg/x3D2Qbadmb";

    public static boolean aprilFools = (cal.get(Calendar.MONTH) == Calendar.APRIL && cal.get(Calendar.DAY_OF_MONTH) == 1);
    // for future event-only campaigns
    // public static boolean newYear = debug || ((cal.get(Calendar.MONTH) == Calendar.DECEMBER && cal.get(Calendar.DAY_OF_MONTH) >= 27 && cal.get(Calendar.DAY_OF_MONTH) <= 31));
    // public static boolean victoryDay = debug || (cal.get(Calendar.MONTH) == Calendar.MAY && cal.get(Calendar.DAY_OF_MONTH) >= 7 && cal.get(Calendar.DAY_OF_MONTH) <= 9);
    // public static boolean mindustryBday = debug || (cal.get(Calendar.MONTH) == Calendar.OCTOBER && cal.get(Calendar.DAY_OF_MONTH) >= 15 && cal.get(Calendar.DAY_OF_MONTH) <= 17);

    public static Seq<String> enabledMods = new Seq<>();
    private static String modReact = "";

    public static void load() {
        loadedLogo = new AtlasRegion(Core.atlas.find("logo"));
        settings = new TSettingsMenuDialog();
        sound = new TSoundControlReflect();
        tui = new TUI();
        funny = new TFunny();
        events = new TEventControl();

        Events.on(MusicChangeEvent.class, e -> {
            if(state.isPlaying() && e.to != null && Core.settings.getBool("t-muspopup"))
                tui.bottomToast(null, Core.bundle.format("t-muspopup", TMusic.getName(e.to)));
        });

        settings.apply();
        
        if(aprilFools) {
            Core.settings.put("t-menuusic", "aprilmenu");
            Core.settings.put("t-logo", "funny");
        }
    }

    //yay hacking
    @SuppressWarnings("unchecked")
    public static ObjectMap<String, String> getBundle() {
        Field field;
        ObjectMap<String, String> bundle;
        try {
            field = I18NBundle.class.getDeclaredField("properties");
            field.setAccessible(true);
            bundle = (ObjectMap<String,String>)field.get(Core.bundle);
        }
        catch(Exception never) {
            return null;
        }
        return bundle;
    }

    @SuppressWarnings("unchecked")
    public static ObjectMap<String, AtlasRegion> getRegionMap() {
        Field field;
        ObjectMap<String, AtlasRegion> regionmap;
        try {
            field = TextureAtlas.class.getDeclaredField("regionmap");
            field.setAccessible(true);
            regionmap = (ObjectMap<String,AtlasRegion>)field.get(Core.atlas);
        }
        catch(Exception never) {
            return null;
        }
        return regionmap;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> getSettings() {
        Field field;
        HashMap<String, Object> values;
        try {
            field = Settings.class.getDeclaredField("values");
            field.setAccessible(true);
            values = (HashMap<String, Object>)field.get(Core.settings);
        }
        catch(Exception never) {
            return null;
        }
        return values;
    }

    @Override
    public void update() {
        sound.update();
        funny.update();
        events.update();

        //change logo
        Core.atlas.find("logo").set(logoByName(Core.settings.getString("t-logo")));

        if(musByName(Core.settings.getString("t-menumusic")) == null)
            Core.settings.put("t-menumusic", "tmenu");

        //change menu music
        var newmus = musByName(Core.settings.getString("t-menumusic"));
        if(!TMusic.musEquals(Musics.menu, newmus))
            Musics.menu = newmus;
    }

    AtlasRegion logoByName(String name) {
        if(name == "current") return loadedLogo;
        else {
            if(Core.atlas.find("t-"+name+"logo") == Core.atlas.find("error"))
                Core.settings.put("t-logo", "t");
            
            return Core.atlas.find("t-"+name+"logo");
        }
    }

    Music musByName(String name) {
        if(Vars.headless) return new Music();
        if(name == "current") return TMusic.menu;
        if(name.startsWith("t-")) {
            var fi = Technologium.tmod.root.child("music");
            try {
                return new Music(fi.child(name+".ogg").exists() ? fi.child(name+".ogg") : fi.child(name+".mp3"));
            }
            catch(Exception ignored) {
                return new Music();
            }
        }
        else return Vars.tree.loadMusic(name);
    }
    
    public static Seq<Item> fruits(boolean plantable) {
        return content.items().select(i -> i instanceof Fruit && ((Fruit)i).plantable == plantable);
    }

    public static Seq<Item> fruits() {
        return fruits(false);
    }

    public static Seq<Item> fruitsAll() {
        return content.items().select(i -> i instanceof Fruit);
    }

    public static Seq<String> enabledMods() {
        return Seq.with(enabledMods);
    }

    public static void showDiscord() {
        new Dialog("@t-discord.title"){{
            title.setAlignment(1);
            cont.row();
            cont.image().width(554f).pad(2).colspan(2).height(4f).color(Color.valueOf("aaccdd")).row();
            cont.image(Core.atlas.find("t-icon")).row();
            cont.add("@t-discord.text").width(600f).wrap().get().setAlignment(1, 1);
            cont.row().image().width(554f).pad(2).colspan(2).height(4f).color(Color.valueOf("aaccdd")).row();
            
            buttons.defaults().size(210,50).pad(4);
            buttons.button("@copylink", Icon.copy, () -> {
                Core.app.setClipboardText(tdiscordURL);
                Core.settings.put("t-discord", false);
                hide();
            });
            if(Vars.mobile) buttons.row();
            buttons.button("@openlink", Icon.discord, () -> {
                if(!Core.app.openURI(tdiscordURL)){
                    ui.showErrorMessage("@linkfail");
                    Core.app.setClipboardText(tdiscordURL);
                }
                Core.settings.put("t-discord", false);
                hide();
            });
            if(Vars.mobile) buttons.row();
            buttons.button("@close", Icon.cancel, this::hide).row();
            closeOnBack();
        }}.show();
    }

    // permanently borrowed from Steam Works
    public static void dev() {
		Vars.mods.getScripts().runConsole("importPackage(Packages.rhino)");
		Vars.mods.getScripts().runConsole(
			"""
				function importModClass(name){

				let constr = java.lang.Class.forName("rhino.NativeJavaPackage").getDeclaredConstructor(java.lang.Boolean.TYPE, java.lang.String, java.lang.ClassLoader);
				constr.setAccessible(true);

				let p = constr.newInstance(true, name, Vars.mods.mainLoader());

				let scope = Reflect.get(Vars.mods.getScripts(), "scope");
				Reflect.invoke(ScriptableObject, p, "setParentScope", [scope], [Scriptable]);

				importPackage(p);\s

				}"""
		);
		Vars.mods.getScripts().runConsole("importModClass(\"technologium\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.audio\")");
		Vars.mods.getScripts().runConsole("importModClass(\"technologium.content\")");
		Vars.mods.getScripts().runConsole("importModClass(\"technologium.entities\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.entities.bullet\")");
		Vars.mods.getScripts().runConsole("importModClass(\"technologium.graphics\")");
		Vars.mods.getScripts().runConsole("importModClass(\"technologium.logic\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.type\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.type.unit\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.type.weapons\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.ui\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.ui.dialogs\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.ui.settings\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.distribution\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.environment\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.liquid\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.logic\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.power\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.production\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.storage\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.units\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.draw\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.meta\")");
        Vars.mods.getScripts().runConsole("importModClass(\"technologium.world.weather\")");
        // used a lot
        Vars.mods.getScripts().runConsole("""
            function tekObjectKeys(object){
                return Object.keys(object).forEach(function (item, index, array) { print(item) });
            }
            """);
	}
}
