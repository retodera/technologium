package technologium;

import technologium.ui.*;
import java.util.*;
import arc.Core;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;

//the most useless thing for now
public class TVars {

    //thanks to FOS
    public static Calendar cal = new GregorianCalendar();
    public static AtlasRegion origlogo;

    public static SettingsMenuDialog settings;
    public static boolean trequiresReload = false;

    //for event-only campaigns
    public static boolean misideRelease = Core.settings.getBool("tdebug", false) || (cal.get(Calendar.MONTH) == Calendar.AUGUST && cal.get(Calendar.DAY_OF_MONTH) >= 17 && cal.get(Calendar.DAY_OF_MONTH) <= 19);
    public static boolean newYear = Core.settings.getBool("tdebug", false) || ((cal.get(Calendar.MONTH) == Calendar.DECEMBER && cal.get(Calendar.DAY_OF_MONTH) >= 27 && cal.get(Calendar.DAY_OF_MONTH) <= 31));
    public static boolean victoryDay = Core.settings.getBool("tdebug", false) || (cal.get(Calendar.MONTH) == Calendar.MAY && cal.get(Calendar.DAY_OF_MONTH) >= 8 && cal.get(Calendar.DAY_OF_MONTH) <= 10);

    public static void load() {
        origlogo = new AtlasRegion(Core.atlas.find("logo"));
        settings = new SettingsMenuDialog();
    }
}
