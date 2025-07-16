package technologium.ui.settings;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;
import mindustry.ui.dialogs.SettingsMenuDialog.StringProcessor;

import static arc.Core.*;

/**slider with changing tooltip */
public class TSliderSetting extends SettingsTable.Setting {
    int def, min, max, step;
    StringProcessor val;

    public TSliderSetting(String name, int def, int min, int max, StringProcessor s) {
        this(name, def, min, max, 1, s);
    }

    public TSliderSetting(String name, int def, int min, int max, int step, StringProcessor s) {
        super(name);
        this.def = def;
        this.min = min;
        this.max = max;
        this.step = step;
        this.val = s;
    }

    @Override
    public void add(SettingsTable table){
        Slider slider = new Slider(min, max, step, false);

        try {
            slider.setValue(settings.getInt(name));
        }
        catch(Exception fix) {
            settings.put(name, 0);
            settings.defaults(name, 0);
            slider.setValue(def);
        }

        settings.defaults(name, def);

        Label value = new Label("", Styles.outlineLabel);
        Table content = new Table();
        content.add(title, Styles.outlineLabel).left().growX().wrap();
        content.add(value).padLeft(10f).right();
        content.margin(3f, 33f, 3f, 33f);
        content.touchable = Touchable.disabled;

        Label desc = new Label("");
        slider.changed(() -> {
            settings.put(name, (int)slider.getValue());
            value.setText(bundle.get(val.get((int)slider.getValue())));
            desc.setText(bundle.get(val.get((int)slider.getValue())+".description"));
        });
        slider.change();

        //>>>>>>>>>>>>>>>>>>>> WARNING::total_mess <<<<<<<<<<<<<<<<<<<<
        table.stack(slider, content).width(Math.min(graphics.getWidth() / 1.2f, 550f)).left().padTop(4f).get()
        .addListener(new Tooltip(t -> {
            t.background(Styles.black8).margin(4f).add(desc).color(Color.lightGray);
        }){
            {
                allowMobile = true;
            }
            @Override
            protected void setContainerPosition(Element element, float x, float y){
                this.targetActor = element;
                Vec2 pos = element.localToStageCoordinates(Tmp.v1.set(0, 0));
                container.pack();
                container.setPosition(pos.x, pos.y, Align.topLeft);
                container.setOrigin(0, element.getHeight());
            }
        });
        table.row();
    }
}
