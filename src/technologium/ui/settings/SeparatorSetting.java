package technologium.ui.settings;

import arc.graphics.Color;
import arc.scene.ui.Label;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable;

//restored from Project: Restoration
public class SeparatorSetting extends SettingsTable.Setting implements NotQuiteSetting {
    float height, fontScale;
    Color textColor;
    Label.LabelStyle textFontStyle;

    public SeparatorSetting(String name) {
        this(name, 1);
    }

    public SeparatorSetting(String name, float fontScale) {
        super(name);
        this.fontScale = fontScale;
        this.textColor = Color.gray;
        this.textFontStyle = Styles.outlineLabel;
    }

    public SeparatorSetting(float height) {
        this("");
        this.height = height;
    }

    public SeparatorSetting(float height, Color textColor) {
        this("");
        this.height = height;
        this.textColor = textColor;
    }

    public SeparatorSetting(float height, Label.LabelStyle textFontStyle) {
        this("");
        this.height = height;
        this.textFontStyle = textFontStyle;
    }

    public SeparatorSetting(float height, Color textColor, Label.LabelStyle textFontStyle) {
        this("");
        this.height = height;
        this.textColor = textColor;
        this.textFontStyle = textFontStyle;
    }

    public void add(SettingsTable table) {
        if (name.isEmpty()) 
            table.image(Tex.clear).height(height).padTop(3);
        else 
            table.table((t) -> 
                t.add(title).color(textColor).style(textFontStyle).padTop(4).get().setFontScale(fontScale)
            ).growX().get().background(Tex.underline);

        table.row();
    }
}
