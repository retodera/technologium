package technologium.ui.settings;

import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

public class LabelSetting extends SettingsTable.Setting implements NotQuiteSetting {
    float fontScale;

    public LabelSetting(String name) {
        this(name, 1);
    }

    public LabelSetting(String name, float fontScale) {
        super(name);
        this.fontScale = fontScale;
    }

    public void add(SettingsTable table) {
        table.add(name).center().get().setFontScale(fontScale);
        table.row();
    }
}