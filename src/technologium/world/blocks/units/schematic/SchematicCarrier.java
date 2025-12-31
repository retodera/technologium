package technologium.world.blocks.units.schematic;

import arc.util.io.*;
import mindustry.gen.Building;
import mindustry.world.Block;
import technologium.type.UnitSchematic;

public class SchematicCarrier extends Block {
    public SchematicCarrier(String name) {
        super(name);
        destructible = true;
        solid = true;

        config(UnitSchematic.class, (carrier, schematic) -> ((SchematicCarrierBuild)carrier).schematic = schematic);
    }

    public class SchematicCarrierBuild extends Building implements SchematicBlock {
        public UnitSchematic schematic = null;

        @Override
        public UnitSchematic schematic() {
            return schematic;
        }

        @Override
        public UnitSchematic config() {
            return schematic;
        }

        @Override
        public void write(Writes write) {
            write.str(schematic == null ? "" : schematic.name);
        }

        @Override
        public void read(Reads read, byte revision) {
            schematic = UnitSchematic.find(read.str());
        }
    }
}
