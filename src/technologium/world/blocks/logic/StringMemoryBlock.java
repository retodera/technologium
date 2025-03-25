package technologium.world.blocks.logic;

import arc.Core;
import arc.math.Mathf;
import arc.scene.ui.TextField.TextFieldFilter;
import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.meta.*;
import mindustry.world.*;
import mindustry.gen.*;
import mindustry.ui.Styles;

import static mindustry.Vars.*;

public class StringMemoryBlock extends Block{
    public int memoryCapacity = 32;

    public StringMemoryBlock(String name) {
        super(name);
        destructible = true;
        solid = true;
        group = BlockGroup.logic;
        drawDisabled = false;
        envEnabled = Env.any;
        canOverdrive = false;
        configurable = true;

        config(String[].class, (StringMemoryBuild build, String[] values) -> {
           build.memory = values; 
        });
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.memoryCapacity, memoryCapacity, StatUnit.none);
    }

    public boolean accessible(){
        return !privileged || state.rules.editor;
    }

    @Override
    public boolean canBreak(Tile tile){
        return accessible();
    }

    public class StringMemoryBuild extends Building{
        public String[] memory = new String[memoryCapacity];
        public Table table = new Table();
        public boolean edit = false;
        public int tableCol = 4, tableRow = 16;

        @Override
        public boolean canPickup(){
            return false;
        }

        @Override
        public boolean collide(Bullet other){
            return !privileged;
        }

        @Override
        public boolean displayable(){
            return accessible();
        }

        @Override
        public void damage(float damage){
            if(privileged) return;
            super.damage(damage);
        }

        @Override
        public void buildConfiguration(Table table) {
            table.top().background(null).row();
            rebuild();
            table.add(this.table);
        }

        //taken from yr2 logic debugger
        public void rebuild() {
            table.clear();
            table.table(Styles.black6, t -> {
                t.table(null, tt -> {
                    tt.check("", edit, c -> {
                        edit = c;
                        rebuild();
                    }).size(40).tooltip("@memoryblock.edit");
                    tt.slider(16, 32, 1, tableRow, true, c -> {
                        tableRow = (int)c;
                        rebuild();
                    }).left().width(225).tooltip("@memoryblock.rows");
                    tt.field(""+tableCol, TextFieldFilter.digitsOnly, c -> {
                        int d = Integer.parseInt(c);
                        if(d > 0) tableCol = d;
                        else rebuild();
                    }).width(75);
                    tt.button(Icon.upload, Styles.cleari, () -> {
                        Core.app.setClipboardText(String.join(" ",memory));
                    }).size(40).tooltip("@memoryblock.copy");
                    tt.button(Icon.download, Styles.cleari, () -> {
                        String[] m = Core.app.getClipboardText().split(" ");
                        if(m.length == memory.length)
                            for(int i = 0; i < m.length; i++) memory[i] = m[i];
                    }).size(40).tooltip("@memoryblock.paste");
                    tt.field(""+tableCol, TextFieldFilter.digitsOnly, c -> {
                        int d = Integer.parseInt(c);
                        if(d > 0) tableCol = d;
                        else rebuild();
                    }).width(75);
                    tt.slider(4, 16, 1, tableCol, true, c -> {
                        tableCol = (int)c;
                        rebuild();
                    }).left().width(225).tooltip("@memoryblock.cols");
                }).top().height(50);
                t.row();
                if(edit) {
                    var o = t.pane(p -> {
                        int count = 0;
                        for(int i = 0; i < memory.length; i++) {
                            p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                            // "local variable i defined in an enclosing scope must be final or effetively final"
                            final int a = i;
                            p.table(null, tt -> {
                                tt.labelWrap("[accent]#" + a).width(60);
                                tt.field(memory[a], c -> memory[a] = c).width(120 + (tableCol > 4 ? 0 : (4 - tableCol) * 200 / tableCol)).get().setAlignment(Align.right);
                            }).top();
                            if(count++ % tableCol == tableCol - 1) {
                                p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                p.row();
                            }
                        }
                    }).minHeight(Math.min(tableRow, Mathf.ceil(memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).get();
                    o.setupFadeScrollBars(0.5f, 0.25f);
                    o.setFadeScrollBars(true);
                }
                else {
                    var o = t.pane(p -> {
                        int count = 0;
                        for(int i = 0; i < memory.length; i++) {
                            p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                            float time = 0;
                            String text = memory[i];
                            final String color;
                            if(memory[i] != text) {
                                text = memory[i];
                                time = Time.time;
                            }
                            if(Time.time < time + 5) color = "[green]";
                            else if (text == "") color = "[gray]";
                            else color = "[accent]";
                            final int a = i;
                            p.table(null, tt -> {
                                tt.label(() -> {
                                    return color + "#" + a;
                                }).width(60);
                                tt.label(() -> {
                                    return color + memory[a];
                                }).minWidth(110).growX().get().setAlignment(Align.right);
                            }).top().growX().minHeight(30);
                            if(count++ % tableCol == tableCol - 1) { 
                                p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                p.row();
                            }
                        }
                    }).minHeight(Math.min(tableRow, Mathf.ceil(memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).growX().get();
                    o.setupFadeScrollBars(0.5f, 0.25f);
                    o.setFadeScrollBars(true);
                }
            }).minWidth(Math.max(tableCol * 200 + 20, Math.max(820, edit ? 0 : 1280)));
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.i(memory.length);
            for(String v : memory){
                write.str(v == null ? "" : v);
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            int amount = read.i();
            for(int i = 0; i < amount; i++){
                String val = read.str();
                if(i < memory.length) memory[i] = val;
            }
        }
    }
}