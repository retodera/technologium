package technologium.ui.settings;

import arc.func.Boolp;
import arc.scene.style.Drawable;
import arc.scene.ui.ImageButton;
import arc.scene.utils.Elem;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.Setting;

//restored from Project: Restoration
public class ButtonSetting extends Setting implements NotQuiteSetting {
    Drawable icon;
    Runnable listener;
    Boolp condition;
    float iconSize;

    public ButtonSetting(String name, Runnable listener) {
        this(name, null, listener);
    }

    public ButtonSetting(String name, Drawable icon, Runnable listener) {
        this(name, icon, 32f, listener);
    }

    public ButtonSetting(String name, Drawable icon, float iconSize, Runnable listener) {
        this(name, icon, iconSize, listener, null);
    }

    public ButtonSetting(String name, Drawable icon, float iconSize, Runnable listener, Boolp condition) {
        super(name);
        this.icon = icon;
        this.iconSize = iconSize;
        this.listener = listener;
        this.condition = condition;
    }

    public void add(SettingsMenuDialog.SettingsTable table) {
        ImageButton b = Elem.newImageButton(icon, listener);
        b.resizeImage(iconSize);
        b.label(() -> title).padLeft(6).growX();
        b.center();
        if(condition != null) b.update(() -> b.setDisabled(!condition.get()));
        addDesc(table.add(b).left().padTop(3).get());
        table.row();
    }
}