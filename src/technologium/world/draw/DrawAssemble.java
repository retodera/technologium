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
    public int armCount;
    public Seq<TextureRegion> arms;

    public DrawAssemble(Block block, int armCount) {
        this.armCount = armCount;
        for(int i = 1; i <= armCount; i++)
            arms.add(new TextureRegion(Core.atlas.find(block.name + i)));
    }

    public DrawAssemble(Block block, int armCount, float rotateSpeed, float offset, float move) {
        this.armCount = armCount;
        this.rotateSpeed = rotateSpeed;
        this.offset = offset;
        this.move = move;
        for(int i = 1; i <= armCount; i++)
            this.arms.add(new TextureRegion(Core.atlas.find(block.name + i)));
    }

    @Override

    public void draw(Building build) {

    }
}
