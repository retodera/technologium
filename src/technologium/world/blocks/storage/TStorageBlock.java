package technologium.world.blocks.storage;

import arc.graphics.g2d.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.*;

public class TStorageBlock extends StorageBlock {
    public TStorageBlock(String name) {
        super(name);
    }

    @Override public TextureRegion[] icons() {
		return teamRegion.found() ? new TextureRegion[] { region, teamRegions[Team.sharded.id] } : new TextureRegion[] { region };
	}

    public class TStorageBuild extends StorageBuild {}
}