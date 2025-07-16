package technologium.ui.settings;

import arc.Core;
import arc.scene.ui.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

public class ImageSetting extends SettingsTable.Setting implements NotQuiteSetting {
    float width;
    boolean grow;

    public ImageSetting(String name) {
        this(name, 0, false);
    }

    public ImageSetting(String name, float width) {
        this(name, width, false);
    }

    public ImageSetting(String name, float width, boolean grow) {
        super(name);
        this.width = width;
        this.grow = grow;
    }

    public void add(SettingsTable table) {
        var i = table.add(new Image(Core.atlas.find(name)));
        i.center();
        if(width > 0) i.width(width);
        else if(grow) i.grow();
        else i.width(i.get().getWidth());
        table.row();
    }
}