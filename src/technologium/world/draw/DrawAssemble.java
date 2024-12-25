package technologium.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import arc.struct.*;
import arc.Core;

public class DrawAssemble extends DrawBlock {
    //how fast will arms rotate around
    public float rotateSpeed = 1f,
    //offset relative to the center of the block
    offset = 4f,
    //how far the arms will move towards the center
    move = 4f;
    public int armCount = 4;
    public Seq<TextureRegion> armsTex;

    public DrawAssemble(int armCount) {
        this.armCount = armCount;
    }

    public DrawAssemble(int armCount, float rotateSpeed, float offset, float move) {
        this.armCount = armCount;
        this.rotateSpeed = rotateSpeed;
        this.offset = offset;
        this.move = move;
    }

    @Override

    public void draw(Building build) {
        
    }
    public void load(Block block) {
        for(int i = 0; i < armCount - 1; i++)
            armsTex.add(new TextureRegion(Core.atlas.find(block.name + i)));
    }
}
