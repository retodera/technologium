package technologium.ui;

import mindustry.gen.Icon;
import static mindustry.Vars.*;

public class SettingsMenuDialog {

    public SettingsMenuDialog() {
        ui.settings.hidden(this::apply);
        ui.settings.addCategory("Technologium", Icon.host, table -> {
            table.checkPref("@mod.music", true);
            table.checkPref("@mod.logo", true);
        });
    }

    public void apply() {
    }

    private String processor(int value) {
        return value / 4f + "x";
    }
}
