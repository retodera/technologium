package technologium.world.blocks.units;

import mindustry.gen.Sounds;
import mindustry.world.blocks.units.UnitBlock;

//factorio reference
public class DroneStation extends UnitBlock {

    public DroneStation(String name) {
        super(name);
        update = true;
        hasPower = true;
        hasItems = true;
        solid = true;
        configurable = true;
        clearOnDoubleTap = true;
        outputsPayload = true;
        rotate = false;
        ambientSound = Sounds.respawning;
    }


}
