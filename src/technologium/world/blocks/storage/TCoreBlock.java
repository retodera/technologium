package technologium.world.blocks.storage;

import arc.graphics.g2d.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.*;

// partially copied from Operation Aziris (Astranium Mod), just so the cores will have normal ui sprite
public class TCoreBlock extends CoreBlock {
	public TCoreBlock(String name) {
		super(name);
	}

	@Override public TextureRegion[] icons() {
		return teamRegion.found() ? new TextureRegion[] { region, teamRegions[Team.sharded.id] } : new TextureRegion[] { region };
	}

	public class TCoreBuild extends CoreBuild {}
}