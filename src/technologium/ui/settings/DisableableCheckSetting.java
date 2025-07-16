package technologium.ui.settings;

import arc.Core;
import arc.func.*;
import arc.scene.ui.CheckBox;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

public class DisableableCheckSetting extends SettingsTable.Setting {
    boolean def;
    Boolc changed;
    Boolp condition;

    public DisableableCheckSetting(String name, boolean def, Boolc changed, Boolp condition) {
        super(name);
        this.def = def;
        this.changed = changed;
        this.condition = condition;
    }

    @Override
    public void add(SettingsTable table) {
        Core.settings.defaults(name, def);

        CheckBox box = new CheckBox(title);
        box.update(() -> {
            box.setChecked(Core.settings.getBool(name));
            box.setDisabled(!condition.get());
        });
        box.setChecked(Core.settings.getBool(name));
        box.changed(() -> {
            Core.settings.put(name, box.isChecked());
            if(changed != null)
                changed.get(box.isChecked());
        });
        box.change();

        box.left();
        addDesc(table.add(box).left().padTop(3f).get());
        table.row();
    }
}
