package technologium.ui;

import arc.Core;
import arc.func.Prov;
import arc.math.Mathf;
import arc.scene.ui.TextField.TextFieldFilter;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.ui.Styles;
import mindustry.gen.*;
import mindustry.world.Block;
import mindustry.world.blocks.logic.*;
import technologium.world.blocks.logic.StringMemoryBlock;

/**contains each menu (converted to Java) that yr2 Logic Debugger adds. */
public class Yr2Tables {
    /**Automatically adds/removes yr2 control panels from all MemoryBlocks, StringMemoryBlocks, Messages and Processors. */
    public static void all(boolean add, Block... blocks) {
        for(Block b : blocks) {
            if(b.configurable == add) continue;
            if(b instanceof MemoryBlock) memoryBlock((MemoryBlock)b, add);
            else if(b instanceof StringMemoryBlock) stringMemoryBlock((StringMemoryBlock)b, add);
            else if(b instanceof MessageBlock) messageBlock((MessageBlock)b, add);
            else if(b instanceof LogicBlock) logicBlock((LogicBlock)b, add);
        }
    }

    /**Adds/removes yr2 control panel from a MemoryBlock. */
    public static void memoryBlock(MemoryBlock block, boolean add) {
        if(block.configurable == add) return; //if already added/removed
        block.configurable = add;
        block.buildType = () -> block.new MemoryBuild(){
            public Table conf = new Table();
            public boolean bin, edit = false;
            public int tableCol = 4, tableRow = 16;

            @Override
            public void buildConfiguration(Table table) {
                super.buildConfiguration(table);
                table.top().background(null).row();
                rebuild();
                table.add(conf);
            }

            public void rebuild() {
                conf.clear();
                conf.table(Styles.black6, t -> {
                    t.table(null, tt -> {
                        tt.check("", edit, c -> {
                            edit = c;
                            rebuild();
                        }).size(40).tooltip("@memoryblock.edit");
                        tt.slider(16, 32, 1, tableRow, true, c -> {
                            tableRow = (int)c;
                            rebuild();
                        }).left().width(225).tooltip("@memoryblock.rows");
                        tt.field(""+tableRow, TextFieldFilter.digitsOnly, c -> {
                            int d = Integer.parseInt(c);
                            if(d > 0 && d <= 32) tableRow = d;
                            else rebuild();
                        }).width(75);
                        tt.button(Icon.upload, Styles.cleari, () -> {
                            String[] mem = new String[((MemoryBlock)block).memoryCapacity];
                            for(int i = 0; i < memory.length; i++) 
                                mem[i] = String.valueOf(memory[i]);
                            Core.app.setClipboardText("["+String.join(",",mem)+"]");
                        }).size(40).tooltip("@memoryblock.copy");
                        tt.button(Icon.download, Styles.cleari, () -> {
                            String vars = Core.app.getClipboardText();
                            String[] m = vars.replace("[", "").replace("]", " ").split(",");
                            if(m.length == memory.length){
                                StringBuilder s = new StringBuilder(m[63]);
                                s.replace(s.length()-1, s.length(), "");
                                m[63] = s.toString();
                                for(int i = 0; i < m.length; i++) memory[i] = Integer.parseInt(m[i]);
                            }
                        }).size(40).tooltip("@memoryblock.paste");
                        tt.field(""+tableCol, TextFieldFilter.digitsOnly, c -> {
                            int d = Integer.parseInt(c);
                            if(d > 0 && d <= 16) tableCol = d;
                            else rebuild();
                        }).width(75);
                        tt.slider(4, 16, 1, tableCol, true, c -> {
                            tableCol = (int)c;
                            rebuild();
                        }).left().width(225).tooltip("@memoryblock.cols");
                    }).top().height(50);
                    t.row();
                    if(bin) {
                        
                    }
                    else if(edit) {
                        var o = t.pane(p -> {
                            int count = 0;
                            for(int i = 0; i < memory.length; i++) {
                                p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                int[] a = {i};
                                p.table(null, tt -> {
                                    tt.labelWrap("[accent]#" + a[0]).width(60);
                                    tt.field(String.valueOf(memory[a[0]]), c -> memory[a[0]] = Double.parseDouble(c)).width(120 + (tableCol > 4 ? 0 : (4 - tableCol) * 200 / tableCol)).get().setAlignment(Align.right);
                                }).top();
                                if(count++ % tableCol == tableCol - 1) {
                                    p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                    p.row();
                                }
                            }
                        }).minHeight(Math.min(tableRow, Mathf.ceil(((StringMemoryBlock)block).memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).get();
                        o.setupFadeScrollBars(0.5f, 0.25f);
                        o.setFadeScrollBars(true);
                    }
                    else {
                        var o = t.pane(p -> {
                            int count = 0;
                            for(int i = 0; i < memory.length; i++) {
                                p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                double[] val = {memory[i]};
                                float[] time = {0};
                                int[] a = {i};
                                Prov<String> color = () -> {
                                    if(memory[a[0]] != val[0]) {
                                        val[0] = memory[a[0]];
                                        time[0] = Time.time;
                                    }
                                    if(Time.time < time[0] + 5) return "[green]";
                                    else if (val[0] == 0) return "[gray]";
                                    else return "[accent]";
                                };
                                p.table(null, tt -> {
                                    tt.label(() -> {
                                        return color.get() + "#" + a[0];
                                    }).width(60);
                                    tt.label(() -> {
                                        return color.get() + memory[a[0]];
                                    }).minWidth(110).growX().get().setAlignment(Align.right);
                                }).top().growX().minHeight(30);
                                if(count++ % tableCol == tableCol - 1) { 
                                    p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                    p.row();
                                }
                            }
                        }).minHeight(Math.min(tableRow, Mathf.ceil(((StringMemoryBlock)block).memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).growX().get();
                        o.setupFadeScrollBars(0.5f, 0.25f);
                        o.setFadeScrollBars(true);
                    }
                }).minWidth(Math.max(tableCol * 200 + 20, Math.max(820, edit ? 0 : 1280)));
            }
        };
    }

    /**Adds/removes remade yr2 control panel from a StringMemoryBlock. */
    public static void stringMemoryBlock(StringMemoryBlock block, boolean add) {
        if(block.configurable == add) return; //if already added/removed
        block.configurable = add;
        block.buildType = () -> block.new StringMemoryBuild(){
            public Table conf = new Table();
            public boolean edit = false;
            public int tableCol = 4, tableRow = 16;

            @Override
            public void buildConfiguration(Table table) {
                super.buildConfiguration(table);
                table.top().background(null).row();
                rebuild();
                table.add(conf);
            }

            public void rebuild() {
                conf.clear();
                conf.table(Styles.black6, t -> {
                    t.table(null, tt -> {
                        tt.check("", edit, c -> {
                            edit = c;
                            rebuild();
                        }).size(40).tooltip("@memoryblock.edit");
                        tt.slider(16, 32, 1, tableRow, true, c -> {
                            tableRow = (int)c;
                            rebuild();
                        }).left().width(225).tooltip("@memoryblock.rows");
                        tt.field(""+tableRow, TextFieldFilter.digitsOnly, c -> {
                            int d = Integer.parseInt(c);
                            if(d > 0 && d <= 32) tableRow = d;
                            else rebuild();
                        }).width(75);
                        tt.button(Icon.upload, Styles.cleari, () -> {
                            Core.app.setClipboardText("["+String.join(",",memory)+"]");
                        }).size(40).tooltip("@memoryblock.copy");
                        tt.button(Icon.download, Styles.cleari, () -> {
                            String vars = Core.app.getClipboardText();
                            String[] m = vars.replace("[", "").replace("]", " ").split(",");
                            if(m.length == memory.length){
                                StringBuilder s = new StringBuilder(m[63]);
                                s.replace(s.length()-1, s.length(), "");
                                m[63] = s.toString();
                                for(int i = 0; i < m.length; i++) memory[i] = m[i];
                            }
                        }).size(40).tooltip("@memoryblock.paste");
                        tt.field(""+tableCol, TextFieldFilter.digitsOnly, c -> {
                            int d = Integer.parseInt(c);
                            if(d > 0 && d <= 16) tableCol = d;
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
                                int[] a = {i};
                                p.table(null, tt -> {
                                    tt.labelWrap("[accent]#" + a[0]).width(60);
                                    tt.field(memory[a[0]], c -> memory[a[0]] = c).width(120 + (tableCol > 4 ? 0 : (4 - tableCol) * 200 / tableCol)).get().setAlignment(Align.right);
                                }).top();
                                if(count++ % tableCol == tableCol - 1) {
                                    p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                    p.row();
                                }
                            }
                        }).minHeight(Math.min(tableRow, Mathf.ceil(((StringMemoryBlock)block).memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).get();
                        o.setupFadeScrollBars(0.5f, 0.25f);
                        o.setFadeScrollBars(true);
                    }
                    else {
                        var o = t.pane(p -> {
                            int count = 0;
                            for(int i = 0; i < memory.length; i++) {
                                p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                String[] text = {memory[i]};
                                float[] time = {0};
                                int[] a = {i};
                                Prov<String> color = () -> {
                                    if(!memory[a[0]].equals(text[0])) {
                                        text[0] = memory[a[0]];
                                        time[0] = Time.time;
                                    }
                                    if(Time.time < time[0] + 5) return "[green]";
                                    else if (text[0].equals("") || text[0].replaceAll(" ", "").equals("")) return "[gray]";
                                    else return "[accent]";
                                };
                                p.table(null, tt -> {
                                    tt.label(() -> {
                                        return color.get() + "#" + a[0];
                                    }).width(60);
                                    tt.label(() -> {
                                        return color.get() + memory[a[0]];
                                    }).minWidth(110).growX().get().setAlignment(Align.right);
                                }).top().growX().minHeight(30);
                                if(count++ % tableCol == tableCol - 1) { 
                                    p.labelWrap("[gray]|").width(20).get().setAlignment(Align.center);
                                    p.row();
                                }
                            }
                        }).minHeight(Math.min(tableRow, Mathf.ceil(((StringMemoryBlock)block).memoryCapacity / tableCol)) * 30).maxHeight(tableRow * 30).growX().get();
                        o.setupFadeScrollBars(0.5f, 0.25f);
                        o.setFadeScrollBars(true);
                    }
                }).minWidth(Math.max(tableCol * 200 + 20, Math.max(820, edit ? 0 : 1280)));
            }
        };
    }

    /**Adds/removes yr2 control panel from a MemoryBlock. */
    public static void messageBlock(MessageBlock block, boolean add) {
        if(block.configurable == add) return; //if already added/removed
        block.configurable = add;
        block.buildType = () -> block.new MessageBuild(){
            @Override
            public void buildConfiguration(Table table) {
                if(((MessageBlock)block).accessible()) super.buildConfiguration(table);
                Table conf = new Table(null, t -> {
                    t.button(Icon.copy, Styles.cleari, () -> Core.app.setClipboardText(message.toString())).size(40);
                    if(table.getCells().size > 0) 
                        t.add(table.getCells().get(0).get()).minWidth(40).height(40);
                    t.button(Icon.download, Styles.cleari, () -> configure(Core.app.getClipboardText().replaceAll("\r", ""))).size(40);
                });
                table.clear();
                table.top().background(null);
                table.add(conf);
            }
        };
    }

    /**Adds/removes yr2 control panel from a MemoryBlock. */
    public static void logicBlock(LogicBlock block, boolean add) {
        if(block.configurable == add) return; //if already added/removed
        block.configurable = add;
        block.buildType = () -> block.new LogicBuild(){
            @Override
            public void buildConfiguration(Table table) {
                super.buildConfiguration(table);
                table.top().background(null);
            }
        };
    }
}