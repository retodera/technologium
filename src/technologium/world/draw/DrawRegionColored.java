package technologium.world.draw;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Item;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.draw.*;

public class DrawRegionColored extends DrawBlock {
    public Color color;
    public TextureRegion region;
    public String suffix = "";
    public boolean spinSprite = true;
    public float rotateSpeed = 0f, x = 0f, y = 0f, rotation = 0f;
    public boolean buildingRotate = false;
    public boolean drawPlan = true;
    public float layer = -1;

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite, Color color) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = color;
        this.spinSprite = spinSprite;
    }
    
    public DrawRegionColored(String suffix, float rotateSpeed, Color color) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = color;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, Item item) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = item.color;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite, Item item) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = item.color;
        this.spinSprite = spinSprite;
    }

    public DrawRegionColored(String suffix, float rotateSpeed) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.spinSprite = spinSprite;
    }

    public DrawRegionColored(String suffix) {
        this.suffix = suffix;
    }

    public DrawRegionColored(){}

    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        Draw.color(color != null ? color : Color.white);
        if(spinSprite) Drawf.spinSprite(region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation + (buildingRotate ? build.rotdeg() : 0));
        else Draw.rect(region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation + (buildingRotate ? build.rotdeg() : 0));
        Draw.z(z);
        Draw.color();
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        if(!drawPlan) return;
        Draw.rect(region, plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{region};
    }

    @Override
    public void load(Block block) {
        region = Core.atlas.find(block.name + suffix);
    }
}
