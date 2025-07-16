package technologium.world.blocks.distribution;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ImageButton;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.world.*;
import mindustry.world.meta.*;
import technologium.world.blocks.liquid.LiquidSorter.LiquidSorterBuild;

import static mindustry.Vars.*;

public class TSorter extends Block{
    public TextureRegion cross, item, inverted;
    public Effect fx = Fx.rotateBlock;

    public TSorter(String name){
        super(name);
        update = false;
        destructible = true;
        underBullets = true;
        instantTransfer = true;
        group = BlockGroup.transportation;
        configurable = true;
        unloadable = false;
        saveConfig = true;
        clearOnDoubleTap = true;

        config(Integer[].class, (TSorterBuild tile, Integer[] vals) -> {
            tile.sortItem = vals[0] == -1 ? null : content.item(vals[0]);
            tile.invert = vals[1] == 1;
        });
        config(Item.class, (LiquidSorterBuild tile, Item item) -> {
            lastConfig = new Integer[] {item == null ? -1 : (int)item.id, tile.invert ? 1 : 0};
        });
        config(Boolean.class, (LiquidSorterBuild tile, Boolean invert) -> {
            lastConfig = new Integer[] {tile.sortItem == null ? -1 : (int)tile.sortItem.id, invert ? 1 : 0};
        });
        configClear((TSorterBuild tile) -> tile.sortItem = null);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, name + "-item", true);
    }

    @Override
    public void drawPlanConfigCenter(BuildPlan plan, Object content, String region, boolean cross){
        Integer[] vals = (Integer[])content;
        if(vals == null) return;
        if(vals[1] == 1) Draw.rect(inverted, plan.drawx(), plan.drawy());
        if(vals[0] == -1){
            if(cross) Draw.rect(this.cross, plan.drawx(), plan.drawy());
            return;
        }
        Color color = Vars.content.item(vals[0]).color;
        if(color == null) return;

        if(vals[1] == 1) Draw.rect(inverted, plan.drawx(), plan.drawy());
        Draw.color(color);
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.color();
    }

    @Override
    public void load() {
        super.load();
        cross = Core.atlas.find(name + "-cross");
        item = Core.atlas.find(name + "-item");
        inverted = Core.atlas.find(name + "-invert");
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    @Override
    public int minimapColor(Tile tile){
        var build = (TSorterBuild)tile.build;
        return build == null || build.sortItem == null ? 0 : build.sortItem.color.rgba();
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, cross};
    }

    public class TSorterBuild extends Building{
        public boolean invert = false;
        public boolean showItem = false;
        public @Nullable Item sortItem;

        @Override
        public void configured(Unit player, Object value){
            super.configured(player, value);

            if(!headless){
                renderer.minimap.update(tile);
            }
        }

        @Override
        public void draw(){
            super.draw();
            if(invert) Draw.rect(inverted, x, y);
            if(sortItem == null) {
                Draw.rect(cross, x, y);
            }
            else {
                Draw.color(sortItem.color);
                Draw.rect(item, x, y);
                Draw.color();
                if(showItem) Draw.rect(sortItem.fullIcon, x, y, 4, 4);
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            Building to = getTileTarget(item, source, false);

            return to != null && to.acceptItem(this, item) && to.team == team;
        }

        @Override
        public void handleItem(Building source, Item item){
            getTileTarget(item, source, true).handleItem(this, item);
        }

        public boolean isSame(Building other){
            return other != null && other.block.instantTransfer;
        }

        public Building getTileTarget(Item item, Building source, boolean flip){
            int dir = source.relativeTo(tile.x, tile.y);
            if(dir == -1) return null;
            Building to;

            if(((item == sortItem) != invert) == enabled){
                if(isSame(source) && isSame(nearby(dir))){
                    return null;
                }
                to = nearby(dir);
            }else{
                Building a = nearby(Mathf.mod(dir - 1, 4));
                Building b = nearby(Mathf.mod(dir + 1, 4));
                boolean ac = a != null && !(a.block.instantTransfer && source.block.instantTransfer) &&
                a.acceptItem(this, item);
                boolean bc = b != null && !(b.block.instantTransfer && source.block.instantTransfer) &&
                b.acceptItem(this, item);

                if(ac && !bc){
                    to = a;
                }else if(bc && !ac){
                    to = b;
                }else if(!bc){
                    return null;
                }else{
                    to = (rotation & (1 << dir)) == 0 ? a : b;
                    if(flip) rotation ^= (1 << dir);
                }
            }

            return to;
        }

        @Override
        public void buildConfiguration(Table table){
            int[] rowCount = {0}, i = {0};
            ButtonGroup<ImageButton> group = new ButtonGroup<>();
            group.setMinCheckCount(0);
            Table cont = new Table().top();
            cont.defaults().size(40);
            Runnable rebuild = () -> {
                group.clear();
                cont.clearChildren();

                for(Item item : Vars.content.items()){
                    if(!item.unlockedNow() || item.isHidden()) continue;

                    ImageButton button = cont.button(Tex.whiteui, Styles.clearNoneTogglei, Mathf.clamp(item.selectionSize, 0f, 40f), () -> control.input.config.hideConfig()).tooltip(item.localizedName).group(group).get();
                    button.changed(() -> configure(new Integer[] {button.isChecked() ? (int)item.id : -1, invert ? 1 : 0}));
                    button.getStyle().imageUp = new TextureRegionDrawable(item.uiIcon);
                    button.update(() -> button.setChecked(sortItem == item));

                    if(i[0]++ % 4 == 3){
                        cont.row();
                        rowCount[0]++;
                    }
                }
            };

            rebuild.run();

            Table main = new Table().background(Styles.black6);

            ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
            pane.setScrollingDisabled(true, false);
            pane.setScrollYForce(block.selectScroll);
            pane.update(() -> {
                block.selectScroll = pane.getScrollY();
            });
            pane.setOverscroll(false, false);
            main.add(pane).maxHeight(40 * 5);
            
            table.top().add(main);
            table.table(Styles.black6, t -> {
                ImageButton button = t.button(Icon.refreshSmall, Styles.clearNoneTogglei, 36f, () -> {
                    configure(new Integer[] {sortItem == null ? -1 : (int)sortItem.id, invert ? 0 : 1});
                    fx.at(x, y, size);
                    Vars.control.input.config.hideConfig();
                }).tooltip(Core.bundle.format("lsorter.sort", invert ? Core.bundle.get("lsorter.inverted") : Core.bundle.get("lsorter.normal"))).get();
                button.update(() -> button.setChecked(invert));
                t.row();
                ImageButton button2 = t.button(Icon.eyeSmall, Styles.clearNoneTogglei, 36f, () -> {
                    showItem = !showItem;
                }).tooltip(Core.bundle.get("lsorter.showitem")).get();
                button2.update(() -> button2.setChecked(showItem));
            }).top();
        }

        @Override
        public Integer[] config(){
            return new Integer[] {sortItem == null ? -1 : (int)sortItem.id, invert ? 1 : 0};
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(sortItem == null ? -1 : sortItem.id);
            write.bool(invert);
            write.bool(showItem);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            sortItem = content.item(read.s());
            invert = read.bool();
            showItem = read.bool();

            if(revision == 1){
                new DirectionalItemBuffer(20).read(read);
            }
        }
    }
}
