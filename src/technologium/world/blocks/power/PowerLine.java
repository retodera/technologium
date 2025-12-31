package technologium.world.blocks.power;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.core.Renderer;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

import static mindustry.Vars.*;
import static technologium.world.draw.DrawElevated.*;

/**power node but with drawer and modified & elevated laser connection point */
public class PowerLine extends DrawerPowerNode {
    /**elevation of the laser connection point */
    public float elevation = 1f;
    /**layer of the power laser. set to -1 to disable */
    public float powerLayer = -1;
    /**radius, around which the lasers will connect */
    public float connectRadius = 1;

    public PowerLine(String name) {
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Tile tile = world.tile(x, y);

        if(tile == null || !autolink) return;

        Lines.stroke(1f);
        Draw.color(Pal.placing);
        Drawf.circles(x * tilesize + offset, y * tilesize + offset, laserRange * tilesize);

        getPotentialLinks(tile, player.team(), other -> {
            Draw.color(laserColor1, Renderer.laserOpacity * 0.5f);
            drawLaser(x * tilesize + offset, y * tilesize + offset, size, connectRadius, elevation, other);
            Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
        });

        Draw.reset();
    }

    public void drawLaser(Building b1, Building b2){
        drawLaser(b1.x, b1.y, b1.block.size, ((PowerLine)b1.block).connectRadius, ((PowerLine)b1.block).elevation, b2);
    }

    public void drawLaser(float x, float y, int size, float connectRadius, float elevation, Building other){
        if(other.block instanceof PowerLine p) {
            float angle1 = Angles.angle(x, y, other.x, other.y),
            vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1);
            float x1 = elevatedPos(x, Core.camera.position.x, elevation), y1 = elevatedPos(y, Core.camera.position.y, elevation),
            x2 = elevatedPos(other.x, Core.camera.position.x, p.elevation), y2 = elevatedPos(other.y, Core.camera.position.y, p.elevation);
            float r1 = connectRadius*2, r2 = p.connectRadius*2;

            Drawf.laser(laser, laserEnd, x1 + vx*r1, y1 + vy*r1, x2 - vx*r2, y2 - vy*r2, laserScale);
        }
        else if(!(other.block instanceof PowerNode)) {
            float angle1 = Angles.angle(x, y, other.x, other.y),
            vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1);
            float x1 = elevatedPos(x, Core.camera.position.x, elevation), y1 = elevatedPos(y, Core.camera.position.y, elevation), r1 = connectRadius*2;
            float len = other.block.size * tilesize / 2f - 1.5f;

            Drawf.laser(laser, laserEnd, x1 + vx*r1, y1 + vy*r1, other.x - vx*len, other.y - vy*len, laserScale);
        }
        else {
            float angle1 = Angles.angle(x, y, other.x, other.y),
            vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1),
            len1 = size * tilesize / 2f - 1.5f, len2 = other.block.size * tilesize / 2f - 1.5f;

            Drawf.laser(laser, laserEnd, x + vx*len1, y + vy*len1, other.x - vx*len2, other.y - vy*len2, laserScale);
        }
    }

    public class PowerLineBuild extends DrawerPowerNodeBuild {
        @Override
        public void draw() {
            drawer.draw(this);    

            if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            if(powerLayer >= 0) Draw.z(powerLayer);
            else Draw.z(Layer.power);
            setupColor(power.graph.getSatisfaction());

            for(int i = 0; i < power.links.size; i++){
                Building link = world.build(power.links.get(i));
                if(linkValid(this, link) && !((link.block instanceof PowerNode || link.block instanceof PowerLine) && link.id >= id)) drawLaser(this, link);
            }

            Draw.reset();
        }
    }
}
