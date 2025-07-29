package technologium.audio;

import java.lang.reflect.Field;

import arc.Core;
import arc.audio.Music;
import arc.files.Fi;
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
    /* technologium */ tgame3, tgame9, tboss1, tboss2, tfine,
    roaringknight, womantea, the;

    public static Seq<Music> allMenu = new Seq<>(), allLoaded = new Seq<>(), allOG = new Seq<>(), allT = new Seq<>(), allToverride = new Seq<>();

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
        allLoaded.addAll(game1, game2, game3, game4, game5, game6, game7, game8, game9, boss1, boss2, fine, menu, editor, launch, land);
        
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
        allOG.addAll(oggame1, oggame2, oggame3, oggame4, oggame5, oggame6, oggame7, oggame8, oggame9, ogboss1, ogboss2, ogfine, ogmenu, ogeditor, oglaunch, ogland);

        tgame3 = loadModMusic("game3");
        tgame9 = loadModMusic("game9");
        tboss1 = loadModMusic("boss1");
        tboss2 = loadModMusic("boss2");
        tfine = loadModMusic("fine");
        roaringknight = loadMusic("t-roaringknight");
        womantea = loadMusic("t-womantea");
        the = loadMusic("t-the");
        allToverride.addAll(tgame3, tgame9, tboss1, tboss2, tfine);
        allT.addAll(allMenu).addAll(allToverride).addAll(roaringknight, womantea, the);
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
        try{ 
            var f = Music.class.getDeclaredField("file");
            f.setAccessible(true);
            String name = ((Fi)f.get(music)).nameWithoutExtension();
            return Core.bundle.get("tmusic." + (isOG(music) && !name.startsWith("og") ? "og" : "") + (isT(music) && !name.startsWith("t-") ? "t-" : "") + name + ".name");
        }
        catch(Exception ignored) {
            return Core.bundle.get("tmusic.unknown");
        }
    }

    static boolean isOG(Music music) {
        boolean contains = false;
        for(Music m : allOG) {
            if(musEquals(m, music)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    static boolean isT(Music music) {
        boolean contains = false;
        for(Music m : allT) {
            if(musEquals(m, music)) {
                contains = true;
                break;
            }
        }
        return contains;
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