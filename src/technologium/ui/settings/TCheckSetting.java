package technologium.ui.settings;

import arc.func.Boolc;
import arc.scene.ui.CheckBox;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.Setting;

import static arc.Core.*;

/**CheckSetting but with just one critical change */
public class TCheckSetting extends Setting{
    boolean def;
    Boolc changed;

    public TCheckSetting(String name, boolean def){
        this(name, def, null);
    }

    public TCheckSetting(String name, boolean def, Boolc changed){
        super(name);
        this.def = def;
        this.changed = changed;
    }

    @Override
    public void add(SettingsTable table){
        settings.defaults(name, def);

        CheckBox box = new CheckBox(title);
        box.update(() -> box.setChecked(settings.getBool(name)));
        box.setChecked(settings.getBool(name));
        box.changed(() -> {
            settings.put(name, box.isChecked());
            if(changed != null){
                changed.get(box.isChecked());
            }
        });
        // ONE LINE OF CODE THAT WAS NOT ADDED BY ANUKE. AND BECAUSE OF IT MOST OF THINGS BROKE.
        box.change();

        box.left();
        addDesc(table.add(box).left().padTop(3f).get());
        table.row();
    }
}