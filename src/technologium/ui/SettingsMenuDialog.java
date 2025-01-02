package technologium.ui;

import arc.Core;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;   

import static mindustry.Vars.*;
import static technologium.TVars.*;

public class SettingsMenuDialog {
    public static final boolean pastdebugstatus = Core.settings.getBool("tdebug", false);
    public boolean requireReload = false;

    public SettingsMenuDialog() {
        ui.settings.hidden(this::apply);
        ui.settings.addCategory("Technologium", "t-settings-icon", table -> {
            table.checkPref("tmusic", true);
            table.checkPref("tlogo", true);
            table.checkPref("tdebug", false, d -> {
                if(d != pastdebugstatus) {
                    if (d) ui.showCustomConfirm("[#ff][TECHNOLOGIUM_DEBUG_MODE_ACTIVATION]", "@t-debugwarning", "@ok", "@cancel", () -> {
                        Core.settings.put("tdebug", true);
                        requireReload = true;
                    }, () -> {
                        Core.settings.put("tdebug", false);
                        requireReload = false;
                    });
                    else {
                        Core.settings.put("tdebug", false);
                        requireReload = true;
                    }
                }
                else {
                    Core.settings.put("tdebug", d);
                    requireReload = d != pastdebugstatus;
                }
            });
        });
    }

    public void apply() {
        if(Core.settings.getBool("tlogo", false)) {
            Core.atlas.find("logo").set(Core.atlas.find("t-logo"));
        }
        else if (origlogo != null) {
            Core.atlas.find("logo").set(origlogo);
        }
        if(requireReload) ui.showInfoOnHidden("@t-requirereload", () -> {
            Core.app.exit();
        });
    }

    private String processor(int value) {
        return value / 4f + "x";
    }
}