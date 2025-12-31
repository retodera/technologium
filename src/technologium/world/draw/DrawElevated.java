package technologium.world.draw;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.geom.Vec2;
import arc.util.Eachable;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawElevated extends DrawBlock {
    private static final Vec2 temp = new Vec2();
    /**elevation of the bottom point; if less than zero, does not stretch the sprite between 2 points and puts in on the elevationTop */
    public float elevationBottom = -1;
    /**elevation of the top point, or the whole sprite if elevationBottom is less than zero */
    public float elevationTop = 0;
    /**set to 0 to disable */
    public float layer = -1;
    public float offsetX = 0, offsetY = 0, rotationOffset = 0;
    public boolean rotateBuild = false, rotate = false;
    public float rotateSpeed = 1;
    public String suffix = "-top";
    public TextureRegion region;

    public DrawElevated(String suffix, float elevation) {
        this.suffix = suffix;
        this.elevationTop = elevation;
    }
    
    public DrawElevated(String suffix, float elevationBottom, float elevationTop) {
        this.suffix = suffix;
        this.elevationBottom = elevationBottom;
        this.elevationTop = elevationTop;
    }

    public DrawElevated(String suffix, float elevation, float offsetX, float offsetY) {
        this.suffix = suffix;
        this.elevationTop = elevation;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public DrawElevated(String suffix, float elevation, float offsetX, float offsetY, float rotation) {
        this.suffix = suffix;
        this.elevationTop = elevation;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.rotationOffset = rotation;
    }

    @Override
    public void draw(Building build) {
        if(elevationTop <= 0) Draw.rect(region, build.x, build.y);
        else if(elevationBottom < 0) {
            float z = Draw.z();
            if(layer >= 0) Draw.z(layer);
            Vec2 pos = elevatedPos(new Vec2(build.x + offsetX, build.y + offsetY), Core.camera.position, elevationTop);
            Draw.rect(region, pos, (rotateBuild ? build.rotdeg() : 0) + (rotate ? build.totalProgress() * rotateSpeed : 0) + rotationOffset);
            if(layer >= 0) Draw.z(z);
        }
        else {
            Vec2 pos1 = elevatedPos(new Vec2(build.x + offsetX, build.y + offsetY), Core.camera.position, elevationBottom),
                pos2 = elevatedPos(new Vec2(build.x + offsetX, build.y + offsetY), Core.camera.position, elevationTop),
                pos = pos1.cpy().add(pos2).scl(0.5f),
                temp = pos2.cpy().sub(pos1);
            float rotation = Angles.angle(temp.x, temp.y) + 90;
            //no rotation with totalProgress for now. reason: lazy
            float z = Draw.z();
            if(layer >= 0) Draw.z(layer);
            Draw.rect(region, pos, region.width * region.scl() * Draw.xscl, temp.len() * region.scl() * Draw.yscl * 4, rotation);
            if(layer >= 0) Draw.z(z);
        }
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        if(elevationTop <= 0) Draw.rect(region, plan.x, plan.y);
        else if(elevationBottom < 0) {
            float z = Draw.z();
            if(layer >= 0) Draw.z(layer);
            Vec2 pos = elevatedPos(new Vec2(plan.drawx() + offsetX, plan.drawy() + offsetY), Core.camera.position, elevationTop);
            Draw.rect(region, pos, rotationOffset);
            if(layer >= 0) Draw.z(z);
        }
        else {
            Vec2 pos1 = elevatedPos(new Vec2(plan.drawx() + offsetX, plan.drawy() + offsetY), Core.camera.position, elevationBottom),
                pos2 = elevatedPos(new Vec2(plan.drawx() + offsetX, plan.drawy() + offsetY), Core.camera.position, elevationTop),
                pos = pos1.cpy().add(pos2).scl(0.5f),
                temp = pos2.cpy().sub(pos1);
            float rotation = Angles.angle(temp.x, temp.y) + 90;
            float z = Draw.z();
            if(layer >= 0) Draw.z(layer);
            Draw.rect(region, pos, region.width * region.scl() * Draw.xscl, temp.len() * region.scl() * Draw.yscl * 4, rotation);
            if(layer >= 0) Draw.z(z);
        }
    }

    @Override
    public TextureRegion[] icons(Block block) {
        if(elevationBottom < 0) return new TextureRegion[]{region};
        else return new TextureRegion[]{}; //intersections shouldn't be on icons
    }

    @Override
    public void load(Block block) {
        region = Core.atlas.find(block.name + suffix);
    }

    // borrowed from Steam Works

    public static float elevatedPos(float from, float ref, float elevation) {
        return from + (from - ref) * elevation * Vars.renderer.getDisplayScale() / 48;
    }

    public static Vec2 elevatedPos(Vec2 from, Vec2 ref, float elevation) {
        return from.add(temp.set(from).sub(ref).scl(elevation * Vars.renderer.getDisplayScale() / 48));
    }
}
