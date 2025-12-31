package technologium.world.blocks.liquid;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.util.pooling.*;
import arc.util.pooling.Pool.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.*;

import java.util.*;

import static mindustry.Vars.*;

public class LiquidUnloader extends Block{
    public TextureRegion centerRegion;

    public float speed = 1f;

    static Liquid[] allLiquids;

    public LiquidUnloader(String name){
        super(name);
        update = true;
        solid = true;
        health = 70;
        hasLiquids = true;
        configurable = true;
        saveConfig = true;
        liquidCapacity = itemCapacity = 0;
        noUpdateDisabled = true;
        clearOnDoubleTap = true;
        unloadable = false;

        config(Liquid.class, (LiquidUnloaderBuild tile, Liquid item) -> tile.sortLiquid = item);
        configClear((LiquidUnloaderBuild tile) -> tile.sortLiquid = null);
    }

    @Override
    public void load() {
        super.load();
        centerRegion = Core.atlas.find(name + "-center");
    }

    @Override
    public void init(){
        super.init();
        allLiquids = content.liquids().toArray(Liquid.class);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.liquidCapacity); //so, there IS a check for itemCapacity <= 0, but no such check for liquidCapacity...
        stats.add(Stat.speed, 60f / speed, StatUnit.liquidSecond);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list) {
        Color color = plan.config instanceof Liquid l ? l.color : null;
        if(color == null) return;
        Draw.color(color);
        Draw.rect(centerRegion, plan.drawx(), plan.drawy(), plan.rotation);
        Draw.color();
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("liquid");
    }

    public static class ContainerStat implements Poolable{
        Building building;
        float loadFactor;
        boolean canLoad;
        boolean canUnload;
        int lastUsed;

        @Override
        public void reset(){
            building = null;
        }
    }

    public class LiquidUnloaderBuild extends Building{
        public float unloadTimer = 0f;
        public int rotations = 0;
        public Liquid sortLiquid = null;
        public ContainerStat dumpingFrom, dumpingTo;
        public final Seq<ContainerStat> possibleBlocks = new Seq<>(ContainerStat.class);

        protected final Comparator<ContainerStat> comparator = (x, y) -> {
            //sort so it gives priority for blocks that can only either receive or give (not both), and then by load, and then by last use
            //highest = unload from, lowest = unload to
            int unloadPriority = Boolean.compare(x.canUnload && !x.canLoad, y.canUnload && !y.canLoad); //priority to receive if it cannot give
            if(unloadPriority != 0) return unloadPriority;
            int loadPriority = Boolean.compare(x.canUnload || !x.canLoad, y.canUnload || !y.canLoad); //priority to give if it cannot receive
            if(loadPriority != 0) return loadPriority;
            int loadFactor = Float.compare(x.loadFactor, y.loadFactor);
            if(loadFactor != 0) return loadFactor;
            return Integer.compare(y.lastUsed, x.lastUsed); //inverted
        };

        private boolean isPossibleLiquid(Liquid liquid){
            boolean hasProvider = false,
            hasReceiver = false,
            isDistinct = false;

            var pbi = possibleBlocks.items;
            for(int i = 0; i < possibleBlocks.size; i++){
                var pb = pbi[i];
                var other = pb.building;

                //set the stats of buildings in possibleBlocks while we are at it
                pb.canLoad = other.acceptLiquid(this, liquid);
                pb.canUnload = other.canUnload() && other.liquids != null && other.liquids.get(liquid) > 0;

                //thats also handling framerate issues and slow conveyor belts, to avoid skipping items if nulloader
                isDistinct |= (hasProvider && pb.canLoad) || (hasReceiver && pb.canUnload);
                hasProvider |= pb.canUnload;
                hasReceiver |= pb.canLoad;
            }
            return isDistinct;
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            Pools.freeAll(possibleBlocks, true);
            possibleBlocks.clear();

            for(int i = 0; i < proximity.size; i++){
                var other = proximity.get(i);
                if(!other.interactable(team)) continue; //avoid blocks of the wrong team

                //partial check
                boolean canLoad = !(other.block instanceof StorageBlock);
                boolean canUnload = other.canUnload() && other.liquids != null;

                if(canLoad || canUnload){ //avoid blocks that can neither give nor receive items
                    var pb = Pools.obtain(ContainerStat.class, ContainerStat::new);
                    pb.building = other;
                    possibleBlocks.add(pb);
                }
            }
        }

        @Override
        public void updateTile(){
            if(((unloadTimer += delta()) < speed) || (possibleBlocks.size < 2)) return;
            Liquid liquid = null;
            boolean any = false;

            if(sortLiquid != null) if(isPossibleLiquid(sortLiquid)) liquid = sortLiquid;
            else for(int i = 0, l = allLiquids.length; i < l; i++){
                int id = (rotations + i + 1) % l;
                var possibleLiquid = allLiquids[id];

                if(isPossibleLiquid(possibleLiquid)){
                    liquid = possibleLiquid;
                    break;
                }
            }
            

            if(liquid != null){
                rotations = liquid.id;
                var pbi = possibleBlocks.items;
                int pbs = possibleBlocks.size;

                for(int i = 0; i < pbs; i++){
                    var pb = pbi[i];
                    var other = pb.building;
                    pb.loadFactor = other.block.liquidCapacity == 0 || other.liquids == null ? 0 : other.liquids.get(liquid) / other.block.liquidCapacity;
                    pb.lastUsed = (pb.lastUsed + 1) % Integer.MAX_VALUE;
                }
                
                possibleBlocks.sort(comparator);

                dumpingTo = dumpingFrom = null;

                for(int i = 0; i < pbs; i++)
                    if(pbi[i].canLoad){
                        dumpingTo = pbi[i];
                        break;
                    }

                for(int i = pbs - 1; i >= 0; i--)
                    if(pbi[i].canUnload){
                        dumpingFrom = pbi[i];
                        break;
                    }
                
                if(dumpingFrom != null && dumpingTo != null) {
                    Building other = dumpingTo.building.getLiquidDestination(this, liquid);
                    if(other != null && (dumpingFrom.loadFactor != dumpingTo.loadFactor || !dumpingFrom.canLoad)){
                        float ofract = other.liquids.get(liquid) / other.block.liquidCapacity, 
                              fract = dumpingTo.building.liquids.get(liquid) / dumpingTo.building.block.liquidCapacity;
                        if(ofract < fract) dumpingFrom.building.transferLiquid(dumpingTo.building, (fract - ofract) * liquidCapacity / 2, liquid);
                        dumpingTo.lastUsed = 0;
                        dumpingFrom.lastUsed = 0;
                        any = true;
                    }
                }
            }

            if(any) unloadTimer %= speed;
            else unloadTimer = Math.min(unloadTimer, speed);
        }

        @Override
        public void draw(){
            super.draw();

            Draw.color(sortLiquid == null ? Color.clear : sortLiquid.color);
            Draw.rect(centerRegion, x, y);
            Draw.color();
        }

        @Override
        public void drawSelect(){
            super.drawSelect();
            drawItemSelection(sortLiquid);
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(LiquidUnloader.this, table, content.liquids(), () -> sortLiquid, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public Liquid config(){
            return sortLiquid;
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(sortLiquid == null ? -1 : sortLiquid.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int id = revision == 1 ? read.s() : read.b();
            sortLiquid = id == -1 ? null : content.liquid(id);
        }
    }
}