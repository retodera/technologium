package technologium.core;

import arc.*;
import arc.scene.ui.Dialog;
import arc.struct.Seq;
import arc.util.*;
import technologium.TVars;
import technologium.audio.TSounds;

import static technologium.game.TEventType.TTrigger.*;
import static mindustry.Vars.*;

public class TFunny {
    private boolean welcoming1 = false, welcoming2 = false;
    private Seq<String> modsToReact = new Seq<>();

    public void update() {
        if(!modsToReact.isEmpty()) {
            modsToReact.each(m -> {
                switch(m) {
                    case "restored-mind" -> {
                        Core.settings.put("t-menumusic", "whiletrue");
                        modsToReact.remove("restored-mind");
                    }
                }
            });
        }
    }

    public void welcomeThePlayer() {
        welcomeThePlayer(0.75f);
    }

    public void welcomeThePlayer(float delay) {
        if(welcoming1) return; // prevent double call, that apparently happens because anuke is the !best coder
        if(Core.settings.getBool("tdebug", false)) {
            Events.fire(welcoming1start);
            welcoming1 = true;
            Timer.schedule(() -> new Dialog(){{
                cont.image(Core.atlas.find("t-welcomer")).width(Core.graphics.getWidth()).height(Core.graphics.getHeight());
                shown(() -> {
                    TSounds.welcomer.play(10);
                    Timer.schedule(() -> {
                        hide();
                        Log.info("player welcomed.");
                        Events.fire(welcoming1end);
                        welcoming1 = false;
                    }, 3);
                });
            }}.show(), delay);
            return;
        }
        Log.info("player not welcomed.");
    }

    public void welcomeThePlayer2() {
        welcomeThePlayer2(0.5f);
    }

    public void welcomeThePlayer2(float delay) {
        if(welcoming2) return; // prevent double call, that apparently happens because anuke is the !best coder
        if(Core.settings.getBool("tdebug", false)) {
            Events.fire(welcoming2start);
            welcoming2 = true;
            Timer.schedule(() -> {
                ui.announce("@t-welcome2", 1);
                TSounds.scary.play(10);
                Timer.schedule(() -> {
                    Log.info("Successfully scared the player.");
                    Events.fire(welcoming2end);
                    welcoming2 = false;
                }, 1);
            }, delay);
            return;
        }
        Log.info("go fuck yourself");
        return;
    }

    public void reactWithMods(Seq<String> mods) {
        mods.remove("t");
        modsToReact.addAll(mods);
        TVars.enabledMods.addAll(mods);
    }
}