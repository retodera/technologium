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
import mindustry.gen.*;
import mindustry.type.*;
import technologium.audio.*;
import technologium.core.*;
import technologium.game.TEventType.MusicChangeEvent;
import technologium.type.Fruit;
import technologium.type.Powder;
import technologium.type.UnitSchematic;
import technologium.ui.TUI;
import technologium.ui.dialogs.TSettingsMenuDialog;

import static mindustry.Vars.*;

public class TVars implements ApplicationListener {
    public static Calendar cal = new GregorianCalendar();
    public static AtlasRegion loadedLogo;

    public static TSettingsMenuDialog settings;
    public static TSoundControlReflect sound;
    public static TUI tui;
    public static TFunny funny;
    public static TEventControl events;

    public static ObjectMap<String, Class<?>> customContentTypes = new ObjectMap<>();

    public static boolean requireReload = false;
    public static boolean debug = Core.settings.getBool("tdebug", true);

    public static final String tdiscordURL = "https://discord.gg/x3D2Qbadmb";

    public static boolean aprilFools = (cal.get(Calendar.MONTH) == Calendar.APRIL && cal.get(Calendar.DAY_OF_MONTH) == 1);

    public static Seq<String> enabledMods = new Seq<>();

    public static void load() {
        loadedLogo = new AtlasRegion(Core.atlas.find("logo"));
        settings = new TSettingsMenuDialog();
        sound = new TSoundControlReflect();
        tui = new TUI();
        funny = new TFunny();
        events = new TEventControl();

        Events.on(MusicChangeEvent.class, e -> {
            if(Core.settings.getBool("t-muspopup") && state.isPlaying() && e.to != null)
                tui.bottomToast(null, Core.bundle.format("t-muspopup", TMusic.getName(e.to)));
        });

        settings.apply();
        
        if(aprilFools) {
            Core.settings.put("t-menuusic", "aprilmenu");
            Core.settings.put("t-logo", "funny");
        }

        customContentTypes.putAll(
            "fruit", Fruit.class,
            "unit_schematic", UnitSchematic.class,
            "powder", Powder.class
        );
    }

    public static void addCustomContentType(String name, Class<?> type) {
        customContentTypes.put(name, type);
    }

    public static Class<?> getCustomContentType(String name) {
        return customContentTypes.get(name);
    }

    // region yay hacking
    @SuppressWarnings("unchecked")
    public static ObjectMap<String, String> getBundle() {
        try {
            Field field = I18NBundle.class.getDeclaredField("properties");
            field.setAccessible(true);
            return (ObjectMap<String,String>)field.get(Core.bundle);
        }
        catch(Exception never) { return null; }
    }

    @SuppressWarnings("unchecked")
    public static ObjectMap<String, AtlasRegion> getRegionMap() {
        try {
            Field field = TextureAtlas.class.getDeclaredField("regionmap");
            field.setAccessible(true);
            return (ObjectMap<String,AtlasRegion>)field.get(Core.atlas);
        }
        catch(Exception never) { return null; }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> getSettings() {
        try {
            Field field = Settings.class.getDeclaredField("values");
            field.setAccessible(true);
            return (HashMap<String, Object>)field.get(Core.settings);
        }
        catch(Exception never) { return null; }
    }

    // endregion

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
        if(headless) return new Music();
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
        else return tree.loadMusic(name);
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
            if(mobile) buttons.row();
            buttons.button("@openlink", Icon.discord, () -> {
                if(!Core.app.openURI(tdiscordURL)){
                    ui.showErrorMessage("@linkfail");
                    Core.app.setClipboardText(tdiscordURL);
                }
                Core.settings.put("t-discord", false);
                hide();
            });
            if(mobile) buttons.row();
            buttons.button("@close", Icon.cancel, this::hide).row();
            closeOnBack();
        }}.show();
    }

    public static Class<?> toClass(Object obj) {
        return (Class<?>)obj;
    }

    // permanently borrowed from Steam Works
    public static void dev() {
		mods.getScripts().runConsole("importPackage(Packages.rhino)");
		mods.getScripts().runConsole(
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
		mods.getScripts().runConsole("importModClass(\"technologium\")");
        mods.getScripts().runConsole("importModClass(\"technologium.audio\")");
		mods.getScripts().runConsole("importModClass(\"technologium.content\")");
		mods.getScripts().runConsole("importModClass(\"technologium.entities\")");
        mods.getScripts().runConsole("importModClass(\"technologium.entities.bullet\")");
		mods.getScripts().runConsole("importModClass(\"technologium.graphics\")");
		mods.getScripts().runConsole("importModClass(\"technologium.logic\")");
        mods.getScripts().runConsole("importModClass(\"technologium.type\")");
        mods.getScripts().runConsole("importModClass(\"technologium.type.unit\")");
        mods.getScripts().runConsole("importModClass(\"technologium.type.weapons\")");
        mods.getScripts().runConsole("importModClass(\"technologium.ui\")");
        mods.getScripts().runConsole("importModClass(\"technologium.ui.dialogs\")");
        mods.getScripts().runConsole("importModClass(\"technologium.ui.settings\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.distribution\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.environment\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.liquid\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.logic\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.power\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.units\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.units.schematic\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.production\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.production.boost\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.storage\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.blocks.units\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.draw\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.meta\")");
        mods.getScripts().runConsole("importModClass(\"technologium.world.weather\")");
        // used a lot for some reason
        mods.getScripts().runConsole("""
            function tekObjectKeys(object){
                return Object.keys(object).forEach(function (item, index, array) { print(item) });
            }
            """);
	}
}
