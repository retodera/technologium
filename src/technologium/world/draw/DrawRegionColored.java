package technologium.world.draw;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Item;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawRegionColored extends DrawBlock {
    public Color color;
    public Item item;
    public TextureRegion region;
    public String suffix = "";
    public boolean spinSprite = true;
    public float rotateSpeed = 0f, x = 0f, y = 0f, rotation = 0f;
    public boolean buildingRotate = false;
    public boolean drawPlan = true;
    public boolean drawIcon = true;
    public float layer = -1;

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite, Color color, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = color;
        this.spinSprite = spinSprite;
        this.drawIcon = drawIcon;
    }
    
    public DrawRegionColored(String suffix, float rotateSpeed, Color color, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = color;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, Item item, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = item.color;
        this.item = item;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite, Item item, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.color = item.color;
        this.spinSprite = spinSprite;
        this.item = item;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(String suffix, float rotateSpeed, boolean spinSprite, boolean drawIcon) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
        this.spinSprite = spinSprite;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(String suffix, boolean drawIcon) {
        this.suffix = suffix;
        this.drawIcon = drawIcon;
    }

    public DrawRegionColored(boolean drawIcon){
        this.drawIcon = drawIcon;
    }

    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        Draw.color(color != null ? color : item != null ? item.color : Color.white);
        if(item != null) Draw.alpha(build.items.get(item) / (float)build.block.itemCapacity);
        if(spinSprite) Drawf.spinSprite(region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation + (buildingRotate ? build.rotdeg() : 0));
        else Draw.rect(region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation + (buildingRotate ? build.rotdeg() : 0));
        Draw.reset();
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
        if (drawIcon) return new TextureRegion[]{region};
        else return new TextureRegion[]{};
    }

    @Override
    public void load(Block block) {
        region = Core.atlas.find(block.name + suffix);
    }
}
