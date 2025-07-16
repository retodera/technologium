package technologium.audio;

import java.lang.reflect.Field;

import arc.Core;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Musics;
import technologium.Technologium;

public class TMusic {
    public static Music
    /* menu */ tMenu, aprilMenu, mistake, mistakee,
    whiletrue, musicMenu, musicMenuUpd,

    /* loaded */ game1, game2, game3, game4, game5, game6, game7,
    game8, game9, boss1, boss2, fine, menu, editor, launch, land,
    /* original */ oggame1, oggame2, oggame3, oggame4, oggame5,
    oggame6, oggame7, oggame8, oggame9, ogboss1, ogboss2, ogfine,
    ogmenu, ogeditor, oglaunch, ogland,
    /* technologium */ tgame3, tgame9, tboss1, tfine;

    public static Seq<Music> allMenu = new Seq<>();

    public static void load() {
        tMenu = loadMusic("t-tmenu");
        aprilMenu = loadMusic("t-aprilmenu");
        mistake = loadMusic("t-mistake");
        mistakee = loadMusic("t-mistakee");
        whiletrue = loadMusic("t-whiletrue");
        musicMenu = loadMusic("t-musicmenu");
        musicMenuUpd = loadMusic("t-musicmenuupd");
        allMenu.addAll(tMenu, aprilMenu, mistake, mistakee, whiletrue, musicMenu, musicMenuUpd);

        game1 = Musics.game1;
        game2 = Musics.game2;
        game3 = Musics.game3;
        game4 = Musics.game4;
        game5 = Musics.game5;
        game6 = Musics.game6;
        game7 = Musics.game7;
        game8 = Musics.game8;
        game9 = Musics.game9;
        boss1 = Musics.boss1;
        boss2 = Musics.boss2;
        fine = Musics.fine;
        menu = Musics.menu;
        editor = Musics.editor;
        launch = Musics.launch;
        land = Musics.land;
        
        oggame1 = loadOGMusic("game1");
        oggame2 = loadOGMusic("game2");
        oggame3 = loadOGMusic("game3");
        oggame4 = loadOGMusic("game4");
        oggame5 = loadOGMusic("game5");
        oggame6 = loadOGMusic("game6");
        oggame7 = loadOGMusic("game7");
        oggame8 = loadOGMusic("game8");
        oggame9 = loadOGMusic("game9");
        ogboss1 = loadOGMusic("boss1");
        ogboss2 = loadOGMusic("boss2");
        ogfine = loadOGMusic("fine");
        ogmenu = loadOGMusic("menu");
        ogeditor = loadOGMusic("editor");
        oglaunch = loadOGMusic("launch");
        ogland = loadOGMusic("land");

        tgame3 = loadModMusic("game3");
        tgame9 = loadModMusic("game9");
        tboss1 = loadModMusic("boss1");
        tfine = loadModMusic("fine");
    }

    static Music loadMusic(String name){
        return Vars.tree.loadMusic(name);
    }

    static Music loadOGMusic(String name){
        try {
            return new Music(Core.files.internal("music/"+name+".ogg"));
        }
        catch(Exception ignored) {
            return new Music();
        }
    }

    static Music loadModMusic(String name){
        try {
            var fi = Technologium.tmod.root.child("music");
            return new Music(fi.child(name + ".ogg").exists() ? fi.child(name + ".ogg") : fi.child(name + ".mp3"));
        }
        catch(Exception ignored) {
            return new Music();
        }
    }

    public static String getName(Music music) {
        return
            musEquals(music, oggame1)      ? "Anuke - Game1" :
            musEquals(music, game1)        ? "Game1" :
            musEquals(music, oggame2)      ? "Anuke - Game2" :
            musEquals(music, game2)        ? "Game2" :
            musEquals(music, oggame3)      ? "Anuke - Game3" :
            musEquals(music, game3)        ? "Game3" :
            musEquals(music, oggame4)      ? "Anuke - Game4" :
            musEquals(music, game4)        ? "Game4" :
            musEquals(music, oggame5)      ? "Anuke - Game5" :
            musEquals(music, game5)        ? "Game5" :
            musEquals(music, oggame6)      ? "Anuke - Game6" :
            musEquals(music, game6)        ? "Game6" :
            musEquals(music, oggame7)      ? "Anuke - Game7" :
            musEquals(music, game7)        ? "Game7" :
            musEquals(music, oggame8)      ? "Anuke - Game8" :
            musEquals(music, game8)        ? "Game8" :
            musEquals(music, oggame9)      ? "Anuke - Game9" :
            musEquals(music, game9)        ? "Game9" :
            musEquals(music, ogboss1)      ? "Anuke - Boss1" :
            musEquals(music, boss1)        ? "Boss1" :
            musEquals(music, ogboss2)      ? "Anuke - Boss2" :
            musEquals(music, boss2)        ? "Boss2" :
            musEquals(music, ogfine)       ? "Anuke - Fine" :
            musEquals(music, fine)         ? "Fine" :
            musEquals(music, ogeditor)     ? "Anuke - Editor" :
            musEquals(music, editor)       ? "Editor" :
            musEquals(music, oglaunch)     ? "Anuke - Launch" :
            musEquals(music, launch)       ? "Launch" :
            musEquals(music, tgame3)       ? "gkugfk3 - Sectors of Failure" :
            musEquals(music, tgame9)       ? "gkugfk3 - Game9 Remix" :
            musEquals(music, tboss1)       ? "gkugfk3 - Correction Tools" :
            musEquals(music, tfine)        ? "gkugfk3 - Fine Remix" :
            musEquals(music, tMenu)        ? "Kasso - I love the piano" :
            musEquals(music, aprilMenu)    ? "Anuke - AprilMenu" :
            musEquals(music, mistake)      ? "You've been rickrolled" :
            musEquals(music, mistakee)     ? "You've been rickrooled" :
            musEquals(music, whiletrue)    ? "A Drop A Day - While(true)" :
            musEquals(music, musicMenu)    ? "MakenCat - MusicMenu" :
            musEquals(music, musicMenuUpd) ? "MakenCat - MusicMenu Update" :
                                             "unknown";
    }

    public static boolean musEquals(Music a, Music b) {
        try {
            Field f = Music.class.getDeclaredField("file");
            f.setAccessible(true);
            return f.get(a).equals(f.get(b));
        }
        catch(Exception ignored) {
            return false;
        }
    }
}