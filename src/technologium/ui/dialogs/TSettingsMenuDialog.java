package technologium.ui.dialogs;

import arc.*;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.core.GameState.State;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.Setting;
import mindustry.world.blocks.distribution.Router;
import technologium.TVars;
import technologium.audio.TSounds;
import technologium.ui.settings.*;
import java.util.HashMap;

import static mindustry.Vars.*;
import static technologium.TVars.*;

public class TSettingsMenuDialog {
    private static final HashMap<String, Object> pastVals = new HashMap<>(), curVals = new HashMap<>();
    private static final HashMap<String,Object> tSettings = new HashMap<>();
    private static final Seq<String> noReload = Seq.with("t-menumusic", "t-logo", "t-discord", "t-distributor", "t-yr2");
    private Table table, funny;

    public TSettingsMenuDialog() {
        TVars.getSettings().forEach((k,v) -> {
            if(v != null) pastVals.put(k,v);
            else pastVals.put(k, Core.settings.getDefault(k));
        });
        funny = new SettingsTable(){{
            pref(new ImageSetting("t-funnylogo"));
            pref(new LabelSetting("@t-funnysettings", 3));
            pref(new TCheckSetting("t-fun", true));
            pref(new SpaceSetting(30));

            pref(new SeparatorSetting("@t-schizosettings", 2));
            pref(new TCheckSetting("t-shuffle", false));
            pref(new ButtonSetting("t-shuffleupd", Icon.eye, 32f, () -> shuffleBundle(), () -> getPast("t-shuffle")));
            pref(new TCheckSetting("t-shufflesprite", false));
            pref(new ButtonSetting("t-shufflespriteupd", Icon.eye, 32f, () -> shuffleSprites(), () -> getPast("t-shufflesprite")));
            pref(new TCheckSetting("t-welcoming1", true));
            pref(new DisableableCheckSetting("t-welcoming1always", false, null, () -> Core.settings.getBool("t-welcoming1")));
            pref(new ButtonSetting("t-welcoming1show", Icon.eye, 32f, () -> TVars.funny.welcomeThePlayer(0)));
            pref(new TCheckSetting("t-welcoming2", true));
            pref(new ButtonSetting("t-welcoming2show", Icon.eye, 32f, () -> TVars.funny.welcomeThePlayer2(0)));

            pref(new SeparatorSetting("@t-rsettings", 2));
            pref(new TCheckSetting("t-lang", false));
            pref(new TCheckSetting("t-reactwithmods", true));
            pref(new SpaceSetting(30));

            pref(new SpaceSetting(30));
            pref(new ImageSetting("t-cat"));
        }};
        ui.settings.hidden(this::checkChanged);
        ui.settings.addCategory("Technologium", "t-technologium", t -> {
            t.pref(new ImageSetting("t-t2logo"));
            t.pref(new LabelSetting("@t-settings", 3));
            t.pref(new SpaceSetting(30));

            t.pref(new SeparatorSetting("@t-graphics", 2));
            Seq<String> logos = Seq.with("current", "orig", "t", "t2", "t3", "tek");
            if(getPast("t-fun") && getPast("t-debug")) logos.add("funny");
            t.pref(new StringSliderSetting("t-logo", "t", logos.toArray(String.class)));
            t.pref(new SpaceSetting(30));

            t.pref(new SeparatorSetting("@t-game", 2));
            t.pref(new TCheckSetting("t-distributor", true));
            t.pref(new TCheckSetting("t-yr2", false));
            t.pref(new SpaceSetting(30));
            
            t.pref(new SeparatorSetting("@t-music", 2));
                Seq<String> musics = Seq.with("current", "t-origmenu", "t-tmenu", "t-aprilmenu", "t-whiletrue");
            if(getPast("t-fun") && getPast("t-debug")) musics.add("t-mistake", "t-mistakee", "t-musicmenu", "t-musicmenuupd");
            t.pref(new StringSliderSetting("t-menumusic", "tmenu", musics.toArray(String.class)));
            t.pref(new ButtonSetting("t-menumusic", () -> {}));
            t.pref(new TCheckSetting("t-muspopup", false));
            t.pref(new SpaceSetting(30));

            t.pref(new SeparatorSetting("@t-links", 2));
            t.pref(new TCheckSetting("t-discord", true));
            t.pref(new ButtonSetting("t-discord.join", Icon.discord, 32f, TVars::showDiscord));
            t.pref(new SpaceSetting(30));

            t.pref(new SeparatorSetting("@t-other", 2));
            t.pref(new TCheckSetting("t-debug", false));

            if(getPast("t-debug") || aprilFools)
                t.pref(new ButtonSetting("t-funnysettings", new TextureRegionDrawable(Core.atlas.find("t-muigolonhcet")), () -> new BaseDialog("@setting.t-funnysettings.name"){{
                    addCloseButton();
                    cont.remove();
                    buttons.remove();
                    Table t = new Table();
                    t.top().margin(14).add(funny);
                    row();
                    pane(t).grow().top();
                    row();
                    add(buttons).fillX();
                }}.show()));

            Seq<String> change = new Seq<>(), noChange = new Seq<>();
            ((SettingsTable)funny).getSettings().each(s -> {
                if(!(s instanceof NotQuiteSetting)) change.add(s.name);
            });
            t.getSettings().each(s -> {
                if(!(s instanceof NotQuiteSetting)) change.add(s.name);
            });
            noChange.addAll("t-logo", "t-menumusic", "t-discord");
            change.removeAll(noChange, false);
            TVars.getSettings().forEach((k,v) -> {
                if(change.contains(k)) tSettings.put(k,v);
            });
            this.table = t;
        });
    }
    
    public void checkChanged() {
        Seq<Setting> set = Seq.with(((SettingsTable)table).getSettings()).addAll(((SettingsTable)funny).getSettings()).removeAll(s -> s instanceof NotQuiteSetting);
        curVals.clear();
        set.each(s -> curVals.put(s.name, Core.settings.get(s.name,null)));

        for(String key : curVals.keySet()) {
            if(wasChanged(key)) {
                Log.info(key);
                new Dialog(""){{
                    getCell(cont).growX();
                    cont.margin(15).image(Core.atlas.find("t-t2logo")).row();
                    cont.add("@t-requirereload").width(400f).wrap().get().setAlignment(Align.center);
                    buttons.button("@ok", this::hide).size(110, 50).pad(4);
                    hidden(() -> Core.app.exit());
                    closeOnBack();
                }}.show();
                return;
            }
        }
        boolean changed = false;
        for(String k : tSettings.keySet()) {
            Object v = Core.settings.get(k,null);
            if(v != tSettings.get(k)) {
                tSettings.put(k,v);
                changed = true;
            }
        }
        if(changed) apply();
    }

    public void apply() {
        if(Core.settings.getBool("t-distributor", true)) 
            Vars.content.blocks().each(b -> {
                if(b instanceof Router) b.buildType = () -> ((Router)b).new RouterBuild() {
                    @Override
                    public boolean canControl() {
                        return true;
                    }
                };
            });
        else
            Vars.content.blocks().each(b -> {
                if(b instanceof Router) b.buildType = () -> ((Router)b).new RouterBuild() {};
            });

        if(Core.settings.getBool("t-debug")) {
            PlanetDialog.debugSelect = true;
            TVars.dev();
        }
        else return;

        if(Core.settings.getBool("t-fun")) {

        }

        shuffleBundle();
        shuffleSprites();

        if(Core.settings.getBool("t-welcoming1", false)) {
            Events.on(ClientLoadEvent.class, e -> TVars.funny.welcomeThePlayer(1.5f));
            if(getPast("t-welcoming1always")) Events.on(StateChangeEvent.class, e -> {
                if(e.to == State.menu && e.from != State.menu) TVars.funny.welcomeThePlayer(0.5f);
            });
        }
        
        if(Core.settings.getBool("t-welcoming2", false)) Events.on(WorldLoadEndEvent.class, e -> {
            if(!state.isEditor()) TVars.funny.welcomeThePlayer2(0.5f);
        });
        
        if(Core.settings.getBool("t-lang", false)) Core.bundle.debug(Core.bundle.get("t-lang"));
        if(Core.settings.getBool("t-reactwithmods")) 
            Events.on(ClientLoadEvent.class, e -> {
                Seq<String> reactmods = new Seq<>();
                mods.eachEnabled(m -> {
                    reactmods.add(m.name);
                });
                TVars.funny.reactWithMods(reactmods);
            });
        
        if(Core.settings.getBool("t-fun") && Core.settings.getBool("t-debug"))
            Events.on(ClientChatEvent.class, e -> TSounds.vineboom.play());
    }

    boolean wasChanged(String key) {
        return noReload.contains(key) ? false : pastVals.get(key) != curVals.get(key);
    }

    static boolean getPast(String key) {
        return pastVals.get(key) == null ? Core.settings.getBool(key, false) : (boolean)pastVals.get(key);
    }

    public static void shuffleBundle() {
        if(Core.settings.getBool("t-shuffle")) {
            var bundle = TVars.getBundle();
            Seq<String> keys = new Seq<>(), values = new Seq<>();
            keys.addAll(bundle.keys());
            values.addAll(bundle.values());
            values.shuffle();
            for(int i = 0; i < keys.size; i++) 
                bundle.put(keys.get(i), values.get(i));
        }
    }

    public static void shuffleSprites() {
        if(Core.settings.getBool("t-shufflesprite")) {
            var regionmap = TVars.getRegionMap();
            Seq<String> keys = new Seq<>();
            Seq<AtlasRegion> values = new Seq<>();
            keys.addAll(regionmap.keys());
            values.addAll(regionmap.values());
            values.shuffle();
            for(int i = 0; i < keys.size; i++) 
                regionmap.put(keys.get(i), values.get(i));
        }
    }
}