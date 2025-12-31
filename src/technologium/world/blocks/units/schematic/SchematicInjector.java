package technologium.world.blocks.units.schematic;

import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.blocks.payloads.*;
import technologium.type.UnitSchematic;
import technologium.world.blocks.units.PlatformModuleBlock;
import technologium.world.blocks.units.schematic.SchematicCarrier.SchematicCarrierBuild;

public class SchematicInjector extends PlatformModuleBlock {
    public SchematicInjector(String name) {
        super(name);
        rotate = true;
        regionRotated1 = 0;
        acceptsPayload = acceptsUnitPayloads = false;
    }

    public class SchematicInjectorBuild extends PlatformModuleBuild implements SchematicBlock {
        public BuildPayload payload;

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return source == this && payload instanceof BuildPayload && ((BuildPayload)payload).block() instanceof SchematicCarrier && ((BuildPayload)payload).block().size < size;
        }

        @Override
        public BuildPayload getPayload() {
            return payload;
        }

        @Override
        public BuildPayload takePayload() {
            BuildPayload p = payload;
            payload = null;
            return p;
        }

        @Override
        public void handlePayload(Building source, Payload payload) {
            this.payload = (BuildPayload)payload;
        }

        @Override
        public UnitSchematic schematic() {
            if(payload == null || !(payload instanceof BuildPayload) || !(((BuildPayload)payload).build instanceof SchematicCarrierBuild)) return null;
            return ((SchematicCarrierBuild)((BuildPayload)payload).build).schematic();
        }

        @Override
        public void draw() {
            super.draw();
            if(payload != null) {
                payload.build.x = x;
                payload.build.y = y;
                payload.draw();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            Payload.write(payload, write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            payload = Payload.read(read);
        }
    }
}