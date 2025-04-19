package technologium.world.blocks.production;

import mindustry.world.blocks.ConstructBlock.ConstructBuild;
import mindustry.world.blocks.production.GenericCrafter;
import arc.struct.*;
import technologium.type.Fruit;
import technologium.world.blocks.production.Pot.PotBuild;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.Bar;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.Core;

import static mindustry.Vars.*;
import static technologium.TVars.*;

import java.io.*;
import java.util.zip.*;

// factorio reference
public class ACCrane extends GenericCrafter {
    public int maxPots = 5;
    /** range in units */
    public float range = 32f;
    /** rotate & extention speed multiplier */
    public float rotateSpeed = 1f, extendSpeed = 1f;
    public TextureRegion craneTop, craneArm, craneArm1, craneArm2, craneWeight, craneExtender, preview;
    public Seq<Fruit> whitelist = new Seq<>(), blacklist = new Seq<>();

    public ACCrane(String name) {
        super(name);
        configurable = true;
        rotate = false;

        config(byte[].class, (ACCraneBuild entity, byte[] data) -> {
            entity.readCompressed(data, true);
        });

        config(Integer.class, (ACCraneBuild entity, Integer pos) -> {
            if(!entity.validLink(world.build(pos))) return;
            var build = world.build(pos);
            int x = build.tileX(), y = build.tileY();

            Link link = entity.links.find(l -> l.x == x && l.y == y);
            if(link == null && (((PotBuild)build).crane == entity || ((PotBuild)build).crane == null) && entity.links.size < maxPots) {
                entity.links.remove(l -> world.build(l.x, l.y) == build);
                entity.harvest.remove(o -> build == o);
                entity.links.add(new Link(x, y, true));
            }
        });
    }

    @Override
    public void load() {
        super.load();
        craneTop = Core.atlas.find(name + "-top");
        craneArm = Core.atlas.find(name + "-arm");
        craneArm1 = Core.atlas.find(name + "-arm1");
        craneArm2 = Core.atlas.find(name + "-arm2");
        craneWeight = Core.atlas.find(name + "-weight");
        craneExtender = Core.atlas.find(name + "-extender");
        preview = Core.atlas.find(name + "-preview");
    }

    @Override
    public void init() {
        super.init();
        if(blacklist.size != 0) whitelist = Seq.with(Vars.content.items().select(i -> i instanceof Fruit && ((Fruit)i).plantable && !blacklist.contains((Fruit)i)).addAll(whitelist.select(i -> i.plantable)).toArray(Fruit.class));
        else whitelist = Seq.with(Vars.content.items().select(i -> i instanceof Fruit && ((Fruit)i).plantable).toArray(Fruit.class));
    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    public static byte[] compress(Seq<Link> links){
        try{
            var baos = new ByteArrayOutputStream();
            var stream = new DataOutputStream(new DeflaterOutputStream(baos));

            stream.writeInt(links.size);
            for(Link link : links){
                stream.writeShort(link.x);
                stream.writeShort(link.y);
            }

            stream.close();

            return baos.toByteArray();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        if(privileged) return;
        Drawf.circles(x*tilesize + offset, y*tilesize + offset, range);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
        Draw.rect(preview, plan.drawx(), plan.drawy(), (rotate ? plan.rotation * 90f : 0));
    }

    @Override
    public TextureRegion[] icons() {
        var out = new Seq<TextureRegion>();
        out.addAll(super.icons()).add(preview);
        return out.toArray(TextureRegion.class);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("links", entity -> new Bar(
            () -> Core.bundle.format("bar.pots", ((ACCraneBuild)entity).links.size, maxPots),
            () -> Pal.items,
            () -> ((ACCraneBuild)entity).links.size / (float)maxPots
        ));
    }

    protected static class Link {
        public boolean valid;
        public int x, y;
        public Building lastBuild;

        public Link(int x, int y, boolean valid) {
            this.x = x;
            this.y = y;
            this.valid = valid;
        }

        public Link copy() {
            return new Link(x, y, valid);
        }
    }

    public class ACCraneBuild extends GenericCrafterBuild {
        private float rot = (rotate ? rotation * 90f : 0), pastRot;
        private float ext = 8f, pastExt;
        public Seq<Link> links = new Seq<>();
        public Seq<PotBuild> harvest = new Seq<>(), plant = new Seq<>();
        public PotBuild target;
        protected int targetPos = -1;

        @Override
        public boolean acceptItem(Building source, Item item) {
            return item instanceof Fruit && ((Fruit)item).plantable && items.get(item) < itemCapacity && whitelist.contains((Fruit)item);
        }

        @Override
        public void dumpOutputs() {
            if(fruits(false) != null && timer(timerDump, dumpTime / timeScale)){
                for(Item item : fruits(false)){
                    dump(item);
                }
            }

            if(outputLiquids != null){
                for(int i = 0; i < outputLiquids.length; i++){
                    int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;
                    dumpLiquid(outputLiquids[i].liquid, 2f, dir);
                }
            }
        }

        void sortByRange(Seq<PotBuild> seq) {
            seq.sort((a, b) -> {
                float armx = x + Mathf.cosDeg(rot) * (ext + (craneArm.height + craneArm.width) / 16);
                float army = y + Mathf.sinDeg(rot) * (ext + (craneArm.height + craneArm.width) / 16);
                return Float.compare(Mathf.len(a.x - armx, a.y - army), Mathf.len(b.x - armx, b.y - army));
            });
        }

        @Override
        public void updateTile() {
            pastRot = rot;
            pastExt = ext;
            for(Link link : links) {
                link.lastBuild = world.build(link.x, link.y);
                PotBuild b = (PotBuild)link.lastBuild;
                if(b == null || !b.within(this, range + b.block.size*tilesize/2f) || (b.crane != this && b.crane != null)) {
                    harvest.remove(b);
                    plant.remove(b);
                    links.remove(link);
                    if(b != null) {
                        b.crane = null;
                        b.cranePos = -1;
                    }
                    continue;
                }
                else if (b.crane == null) b.cranePos = pos();
                if(b.done()){
                    plant.remove(b);
                    harvest.addUnique(b);
                }
                else if(b.fruit() == null) {
                    harvest.remove(b);
                    plant.addUnique(b);
                }
                else {
                    harvest.remove(b);
                    plant.remove(b);
                }
            }

            warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

            if (!links.isEmpty() && (power == null || power.status > 0)) {    
                consPower.trigger(this);
                
                sortByRange(harvest);
                sortByRange(plant);

                if((target == null || world.build(targetPos) == null || !(world.build(targetPos) instanceof PotBuild) || links.find(l -> l.lastBuild == target) == null
                || items.sum((i, c) -> i instanceof Fruit && ((Fruit)i).plantable && c > 0 ? 1 : 0) == 0) && harvest.size > 0) {
                    PotBuild trgt;
                    for(int i = 0; i < harvest.size; i++) {
                        trgt = harvest.get(i);
                        if(items.get(trgt.fruit.result) < itemCapacity) {
                            target = trgt;
                            targetPos = target.pos();
                            break;
                        }
                    }
                }
                if(((target == null || world.build(targetPos) == null || !(world.build(targetPos) instanceof PotBuild) || links.find(l -> l.lastBuild == target) == null)
                  || ((target != null && target.fruit() != null) ? items.get(target.fruit) == itemCapacity : true)) && items.sum((i, c) -> i instanceof Fruit && ((Fruit)i).plantable && c > 0 ? 1 : 0) > 0 && plant.size > 0) {
                    PotBuild trgt;
                    for(int i = 0; i < plant.size; i++) {
                        trgt = plant.get(i);
                        if(trgt.prefere() != null ? items.has(trgt.prefere()) : items.sum((it, c) -> it instanceof Fruit && ((Fruit)it).plantable && c > 0 ? 1 : 0) > 0) {
                            target = trgt;
                            targetPos = target.pos();
                            break;
                        }
                    }
                }

                if(target != null && target.isValid() && world.build(targetPos) instanceof PotBuild && links.find(l -> l.lastBuild == target) != null
                && (target.done() ? items.get(target.fruit.result) < itemCapacity : target.prefere() == null ? items.sum((i, c) -> i instanceof Fruit && ((Fruit)i).plantable && c > 0 ? 1 : 0) > 0 : items.has(target.prefere()))) {
                    float angle = Angles.angle(target.x - x, target.y - y);
                    rot = Angles.moveToward(rot, angle, Mathf.clamp(edelta() * rotateSpeed, 0, Angles.angleDist(rot, angle)));
                    float dist = Mathf.len(target.x - x, target.y - y) - 8;
                    ext = Mathf.approach(ext, dist, Mathf.clamp(edelta() * extendSpeed / 10f, 0, Math.abs(dist - ext)));
                    if((rot == angle || -0.0001 < rot - angle && rot - angle < 0.0001) && ext == dist) {
                        if(target.fruit() == null) {
                            plant.remove(target);
                            if(target.prefere() == null) {
                                Fruit rand = (Fruit)fruits(true).get(new Rand().nextInt(fruits(true).size));
                                target.fruit = rand;
                                items.remove(rand, 1);
                            }
                            else {
                                target.fruit = target.prefere();
                                items.remove(target.prefere(), 1);
                            }
                        }
                        else {
                            harvest.remove(target);
                            offload(target.fruit.result);
                            target.fruit = null;
                            target.curGrowTime = 0f;
                        }
                        target = null;
                        targetPos = -1;
                    }
                }

                if (outputsLiquid) {
                    for (LiquidStack output : outputLiquids) {
                        Liquid fluid = output.liquid;
                        handleLiquid(this, fluid, Math.min(output.amount * getProgressIncrease(1f), liquidCapacity - liquids.get(fluid)));
                    }
                }

                if (wasVisible && Mathf.chanceDelta(updateEffectChance))
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
            }
            else warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);

            totalProgress += warmup * Time.delta;
            rot %= 360;

            for(int i = 0; i < links.size; i++) {
                Link l = links.get(i);
                Building b = world.build(l.x,l.y);
                if(l.lastBuild == null) l.lastBuild = b;
                
                if(l.valid != validLink(b) || l.lastBuild != b) {
                    l.lastBuild = b;
                    l.valid = validLink(b);
                    if(validLink(b)) {
                        links.removeAll(o -> world.build(o.x, o.y) == b && o != l);
                        i = -1;
                    }
                }
            }

            dumpOutputs();
        }

        public boolean isMoving() {
            return pastRot != rot || pastExt != ext;
        }

        @Override
        public boolean shouldConsume() {
            return enabled && (target != null || isMoving() || links.find(l -> (PotBuild)l.lastBuild != null && ((PotBuild)l.lastBuild).fruit != null) != null);
        }

        @Override
        public void draw() {
            drawer.draw(this);

            Draw.z(Layer.power + 1.1f);
            Draw.rect(craneExtender, x, y, ext * 2, craneExtender.height / 4, rot);

            float a = Draw.getColor().a;
            // thats no more a lotta draws
            float drawX = x + Mathf.cosDeg(rot) * (ext + craneArm.width / 8);
            float drawY = y + Mathf.sinDeg(rot) * (ext + craneArm.width / 8);
            Draw.rect(craneArm, drawX, drawY, rot);
            Draw.alpha(Mathf.clamp(Angles.angleDist(rot, 180) / 90 * 1.5f - 1));
            Draw.rect(craneArm1, drawX, drawY, rot);
            Draw.alpha(Mathf.clamp(Angles.angleDist(rot, 270) / 90 * 1.5f - 1));
            Draw.rect(craneArm2, drawX, drawY, rot);
            Draw.alpha(a);

            drawX = x + Mathf.cosDeg(rot-180) * (ext + craneArm.width / 8);
            drawY = y + Mathf.sinDeg(rot-180) * (ext + craneArm.width / 8);
            Draw.rect(craneWeight, drawX, drawY, rot % 90);
            Draw.alpha(rot % 90 / 90);
            Draw.rect(craneWeight, drawX, drawY, rot % 90 - 90);
            Draw.alpha(a);
            Draw.rect(craneTop, x, y);
            Draw.z();
        }

        public float rot() {
            return rot;
        }

        public float ext() {
            return ext;
        }
        
        public int target() {
            return targetPos;
        }

        public Seq<Building> harvest() {
            return Seq.with(harvest.toArray(Building.class));
        }

        public Seq<Building> plant() {
            return Seq.with(plant.toArray(Building.class));
        }

        public boolean validLink(Building other){
            return other != null && other.isValid()
            && other.team == team && other.within(this, range + other.block.size*tilesize/2f)
            && !(other instanceof ConstructBuild) && other instanceof PotBuild;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                return false;
            }
            if(!interactable(player.team())) return false;
            if(validLink(other)){
                if(links.find(l -> l.x == other.tileX() && l.y == other.tileY()) != null) {
                    links.remove(l -> l.x == other.tileX() && l.y == other.tileY());
                    ((PotBuild)other).cranePos = -1;
                    configure(compress(relativeConnections()));
                    return false;
                }
                configure(other.pos());
                return false;
            }
            return super.onConfigureBuildTapped(other);
        }

        public Seq<Link> relativeConnections(){
            var copy = new Seq<Link>(links.size);
            for(var l : links){
                var c = l.copy();
                c.x -= tileX();
                c.y -= tileY();
                copy.add(c);
            }
            return copy;
        }

        @Override
        public void drawConfigure() {
            super.drawConfigure();

            if(!privileged) Drawf.circles(x + offset, y + offset, range);

            for(Link l : links) {
                Building build = world.build(l.x, l.y);
                if(validLink(build)) Drawf.square(build.x, build.y, build.block.size * tilesize / 2f + 1f, Pal.place);
            }
        }

        @Override
        public float warmupTarget() {
            return efficiency;
        }

        @Override
        public byte[] config() {
            return compress(relativeConnections());
        }

        public void readCompressed(byte[] data, boolean relative){
            try(DataInputStream stream = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(data)))){
                links.clear();

                int total = stream.readInt();
                for(int i = 0; i < total; i++){
                    short x = stream.readShort(), y = stream.readShort();
                    if(relative) {
                        x += tileX();
                        y += tileY();
                    }
                    links.add(new Link(x, y, false));
                }
            }
            catch(Exception ignored){}
        }

        @Override
        public void write(Writes write){
            super.write(write);
            byte[] compressed = compress(links);
            write.i(compressed.length);
            write.b(compressed);
            write.f(rot);
            write.f(ext);
            write.i(target != null && !target.dead ? target.pos() : -1);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int compl = read.i();
            byte[] bytes = new byte[compl];
            read.b(bytes);
            readCompressed(bytes, false);
            rot = read.f();
            ext = read.f();
            target = (PotBuild)world.build(read.i());
        }
    }
}
