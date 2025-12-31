package technologium.world.blocks.units;

import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.Seq;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.ctype.UnlockableContent;
import mindustry.type.*;
import mindustry.world.Tile;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.units.UnitAssembler.YeetData;
import technologium.type.*;
import technologium.world.blocks.production.boost.*;
import technologium.world.blocks.production.boost.ProductionBooster.ProductionBoosterBuild;
import technologium.world.blocks.units.schematic.SchematicBlock;
import technologium.world.blocks.units.schematic.SchematicInjector.SchematicInjectorBuild;
import technologium.world.blocks.units.PlatformModuleBlock.PlatformModuleBuild;

import static mindustry.Vars.tilesize;

public class UnitAssemblyPlatform extends PayloadBlock {
    public UnitAssemblyPlatform(String name) {
        super(name);
        update = true;
        solid = true;
    }

    public class UnitAssemblyPlatformBuild extends PayloadBlockBuild<UnitPayload> implements SchematicBlock, BoostableBlock {
        public SchematicInjectorBuild schematicInjector;
        public UnitSchematic schematic;
        public int step = -1;
        public TItemSeq injectedItems = new TItemSeq();
        public TLiquidSeq injectedLiquids = new TLiquidSeq();
        public TPayloadSeq injectedPayloads = new TPayloadSeq();
        public Seq<PlatformModuleBuild> modules = new Seq<>();
        public Seq<ProductionBoosterBuild> boosters = new Seq<>();
        public float warmup, warmupSpeed = 1/60f;

        @Override
        public void update() {
            warmup = Mathf.approachDelta(warmup, efficiency * (schematic == null ? 0 : 1), warmupSpeed);
            injectedItems.cap(itemCapacity());
            injectedLiquids.cap(liquidCapacity());

            if(hasSchematic()) schematic = schematicInjector.schematic();
            if(schematic == null) step = -1;
            else if(hasSchematic()){
                if(step == -1) step = 0;
                
            }
        }

        public boolean moduleFits(float ox, float oy, int orotation, int osize) {
            if(Tile.relativeTo(ox, oy, x, y) != orotation) return false;
            
            float
            dx = ox + Geometry.d4x(orotation) * (osize/2f + 0.5f) * tilesize,
            dy = oy + Geometry.d4y(orotation) * (osize/2f + 0.5f) * tilesize;
            return Mathf.equal(Math.max(Math.abs(dx - x), Math.abs(dy - y)), tilesize * size/2f - tilesize/2);
        }

        public boolean hasSchematic() {
            return schematicInjector != null && schematicInjector.schematic() != null;
        }

        public TItemSeq requiredItems() {
            if(schematic == null || step < 0) return TItemSeq.with();
            return TItemSeq.with(schematic.steps.get(step).items);
        }

        public TLiquidSeq requiredLiquids() {
            if(schematic == null || step < 0) return TLiquidSeq.with();
            return TLiquidSeq.with(schematic.steps.get(step).liquids);
        }

        public TPayloadSeq requiredPayloads() {
            if(schematic == null || step < 0) return TPayloadSeq.with();
            return TPayloadSeq.with(schematic.steps.get(step).payloads);
        }

        public int injectItem(Item item, int amount) {
            int ii = injectedItems.get(item);
            int add = Math.min(amount, itemCapacity() - ii);
            if(ii < itemCapacity() && ii < schematic.steps.get(step).items.get(item)) injectedItems.add(item, add);
            return add;
        }

        public float injectLiquid(Liquid liquid, float amount) {
            float li = injectedLiquids.get(liquid);
            float add = Math.min(amount, liquidCapacity() - li);
            if(li < liquidCapacity() && li < schematic.steps.get(step).liquids.get(liquid)) injectedLiquids.add(liquid, add);
            return add;
        }
        public void injectPayload(Payload payload) {
            injectedPayloads.add(payload.content(), 1);
            float rot = payload.angleTo(x, y);
            Fx.shootPayloadDriver.at(payload.x(), payload.y(), rot);
            Fx.payloadDeposit.at(payload.x(), payload.y(), rot, new YeetData(new Vec2(x, y), payload.content()));
        }

        public boolean acceptInjectPayloads() {
            if(schematic() == null) return false;
            else {
                var pay = requiredPayloads();
                int count = 0;
                for(UnlockableContent content : pay.values.keys()) if(injectedPayloads.values.get(content) > pay.get(content)) count++;
                if(pay.length() == count) return false;
                return true;
            }
        }

        public int itemCapacity() {
            return itemCapacity;
        }

        public float liquidCapacity() {
            return liquidCapacity;
        }

        public void addInjector(PlatformModuleBuild injector) {
            if(injector instanceof SchematicInjectorBuild) schematicInjector = (SchematicInjectorBuild)injector;
            else modules.addUnique(injector);
        }

        public void removeInjector(PlatformModuleBuild injector) {
            if(injector instanceof SchematicInjectorBuild) schematicInjector = null;
            else modules.remove(injector);
        }

        @Override
        public UnitSchematic schematic() {
            return schematicInjector == null ? null : schematicInjector.schematic();
        }

        @Override
        public void addBoost(ProductionBoosterBuild build) {
            boosters.addUnique(build);
        }

        @Override
        public void removeBoost(ProductionBoosterBuild build) {
            boosters.remove(build);
        }

        @Override
        public void write(Writes write) {
            write.f(warmup);
            injectedItems.write(write);
            injectedLiquids.write(write);
            injectedPayloads.write(write);
            write.str(schematic == null ? "" : schematic.name);
        }

        @Override
        public void read(Reads read, byte revision) {
            warmup = read.f();
            injectedItems.read(read);
            injectedLiquids.read(read);
            injectedPayloads.read(read);
            schematic = UnitSchematic.find(read.str());
        }
    }
}