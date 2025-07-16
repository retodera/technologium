package technologium.ui.settings;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.ui.Styles;

import static mindustry.ui.dialogs.SettingsMenuDialog.*;
import static arc.Core.*;

/* a slider that, instead of numeric values, uses strings. still uses numeric value for the slider itself, but puts into settings the string, not the number. */
public class StringSliderSetting extends SettingsTable.Setting {
    int max;
    String def;
    /**values to put into settings */
    Seq<String> vals = new Seq<>();

    public StringSliderSetting(String name, String def, String... vals) {
        super(name);
        this.def = def;
        this.vals.addAll(vals);
        max = vals.length - 1;
    }

    @Override
    public void add(SettingsTable table){
        Slider slider = new Slider(0, max, 1, false);
        int defi = vals.contains(def) ? vals.indexOf(def) : 0;

        try {
            String value = settings.getString(name);
            if(value != null) slider.setValue(vals.contains(value) ? vals.indexOf(value) : defi);
            else slider.setValue(defi);
        }
        catch(Exception fix) {
            settings.put(name, def);
            settings.defaults(name, def);
            slider.setValue(defi);
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
            settings.put(name, vals.get((int)slider.getValue()));
            value.setText(bundle.get(name + "." + vals.get((int)slider.getValue()) + ".name"));
            desc.setText(bundle.get(name + "." + vals.get((int)slider.getValue())+".description"));
        });
        slider.change();

        slider.update(() -> {
            if(bundle.get(name+"."+vals.get((int)slider.getValue()) + ".name") != value.getText().toString() ||
            bundle.get(name+"."+vals.get((int)slider.getValue()) + ".description") != desc.getText().toString())
                slider.change();
        });

        //>>>>>>>>>>>>>>>>>>>> WARNING::total_mess <<<<<<<<<<<<<<<<<<<<
        table.stack(slider, content).width(Math.min(graphics.getWidth() / 1.2f, 550f)).left().padTop(4f).get()
        .addListener(new Tooltip(t -> {
            t.background(Styles.black8).margin(4f).add(desc).color(Color.lightGray).labelAlign(Align.left);
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
