package technologium.world.blocks.multi;

import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.world.Block;

public abstract class BlockPart extends Block {
    public boolean isMain;
    public BlockPart(String name,boolean isMain) {
        super(name);
        this.isMain=isMain;
    }

    public abstract class BuildPart extends Building {
        public boolean isMain() {
            return isMain;
        }
    }
}
