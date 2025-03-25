package technologium;

import technologium.ui.*;
import java.util.*;
import arc.Core;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.struct.Seq;
import mindustry.type.*;
import technologium.type.Fruit;

import static mindustry.Vars.*;

//the most useless thing for now
public class TVars {
    public static Calendar cal = new GregorianCalendar();
    public static AtlasRegion origlogo;

    public static SettingsMenuDialog settings;
    public static boolean requireReload = false;
    public static boolean debug = Core.settings.getBool("tdebug");

    public static final String tdiscordURL = "https://discord.gg/x3D2Qbadmb";

    //for event-only campaigns
    public static boolean misideRelease = debug || (cal.get(Calendar.MONTH) == Calendar.DECEMBER && cal.get(Calendar.DAY_OF_MONTH) >= 9 && cal.get(Calendar.DAY_OF_MONTH) <= 11);
    public static boolean newYear = debug || ((cal.get(Calendar.MONTH) == Calendar.DECEMBER && cal.get(Calendar.DAY_OF_MONTH) >= 27 && cal.get(Calendar.DAY_OF_MONTH) <= 31));
    public static boolean victoryDay = debug || (cal.get(Calendar.MONTH) == Calendar.MAY && cal.get(Calendar.DAY_OF_MONTH) >= 7 && cal.get(Calendar.DAY_OF_MONTH) <= 9);
    public static boolean mindustryBday = debug || (cal.get(Calendar.MONTH) == Calendar.OCTOBER && cal.get(Calendar.DAY_OF_MONTH) >= 15 && cal.get(Calendar.DAY_OF_MONTH) <= 17);

    public static void load() {
        origlogo = new AtlasRegion(Core.atlas.find("logo"));
        settings = new SettingsMenuDialog();
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
}
