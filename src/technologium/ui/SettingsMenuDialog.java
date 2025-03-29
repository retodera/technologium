package technologium.ui;

import arc.Core;

import static mindustry.Vars.*;
import static technologium.TVars.*;

public class SettingsMenuDialog {
    public static final boolean pdebug = Core.settings.getBool("tdebug", false);
    public boolean debug = pdebug;

    public SettingsMenuDialog() {
        ui.settings.hidden(this::apply);
        ui.settings.addCategory("Technologium", "t-technologium", table -> {
            table.checkPref("tlogo", true);
            table.checkPref("tdebug", false, d -> debug = d);
            table.checkPref("tdiscord", true);
            if(pdebug) table.checkPref("tlang", false);
            else table.checkPref("smthelse", false);
        });
    }

    public void apply() {
        if(Core.settings.getBool("tlogo", false)) Core.atlas.find("logo").set(Core.atlas.find("t-logo"));
        else if (origlogo != null) Core.atlas.find("logo").set(origlogo);

        if(debug != pdebug) ui.showInfoOnHidden("@t-requirereload", () -> Core.app.exit());

        if(Core.settings.getBool("tlang", false)) Core.bundle.debug("митамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамитамита");
    }
}