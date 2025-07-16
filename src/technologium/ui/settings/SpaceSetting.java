package technologium.ui.settings;

import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

public class SpaceSetting extends SettingsTable.Setting implements NotQuiteSetting {
    float height;
    
    public SpaceSetting(float height) {
        super("");
        this.height = height;
    }

    public void add(SettingsTable table) {
        table.add().center().height(height).row();
    }
}