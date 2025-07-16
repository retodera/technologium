package technologium.ui;

import static mindustry.Vars.ui;

import arc.Core;
import arc.math.Interp;
import arc.scene.actions.Actions;
import arc.scene.style.Drawable;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class TUI {
    private long lastToast;

    private void scheduleToast(Runnable run){
        long duration = (int)(3.5 * 1000);
        long since = Time.timeSinceMillis(lastToast);
        if(since > duration){
            lastToast = Time.millis();
            run.run();
        }
        else{
            Time.runTask((duration - since) / 1000f * 60f, run);
            lastToast += duration;
        }
    }

    public void bottomToast(Drawable icon, String text) {
        bottomToast(icon, -1, text);
    }    

    public void bottomToast(Drawable icon, float size, String text){
        if(state.isMenu()) return;

        scheduleToast(() -> {
            Sounds.message.play();

            Table table = new Table(Tex.button);
            table.update(() -> {
                if(state.isMenu() || !ui.hudfrag.shown)
                    table.remove();
            });
            table.margin(12);
            var cell = table.image(icon).pad(3);
            if(size > 0) cell.size(size);
            table.add(text).wrap().width(280f).get().setAlignment(Align.center);
            table.pack();

            Table container = Core.scene.table();
            container.bottom().add(table);
            container.setTranslation(0, -table.getPrefHeight());
            container.actions(Actions.translateBy(0, table.getPrefHeight(), 1f, Interp.fade), Actions.delay(2.5f),

            Actions.run(() -> container.actions(Actions.translateBy(0, -table.getPrefHeight(), 1f, Interp.fade), Actions.remove())));
        });
    }
}
