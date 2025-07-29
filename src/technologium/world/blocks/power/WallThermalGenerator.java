package technologium.world.blocks.power;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.*;
import technologium.world.meta.TStatValues;

import static mindustry.Vars.*;

/**"only in ohio :skull:" */
public class WallThermalGenerator extends PowerGenerator{
    public Effect generateEffect = Fx.none;
    public float effectChance = 0.05f;
    public float minEfficiency = 0f;
    public Attribute attribute = Attribute.heat;

    public WallThermalGenerator(String name){
        super(name);
        rotate = true;
        regionRotated1 = 1;

        envEnabled |= Env.space;
    }

    @Override
    public void init() {
        super.init();
        clipSize = Math.max(clipSize, 45f * size * 2f * 2f);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.tiles, TStatValues.blocks(attribute, floating, 1f, true, false, true));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", getEfficiency(x, y, rotation) * 100, 1), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return getEfficiency(tile.x, tile.y, rotation) > minEfficiency;
    }

    @Override
    public TextureRegion getPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        return region;
    }

    float getEfficiency(int tx, int ty, int rotation){
        float eff = 0f;
        int cornerX = tx - (size-1)/2, cornerY = ty - (size-1)/2, s = size;

        for(int i = 0; i < size; i++){
            int rx = 0, ry = 0;

            switch(rotation){
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }

            Tile other = world.tile(rx, ry);
            if(other != null && other.solid())
                eff += other.block().attributes.get(attribute);
        }
        return eff;
    }

    public class WallCrafterBuild extends GeneratorBuild{
        @Override
        public void updateTile(){
            super.updateTile();
            productionEfficiency = getEfficiency(tile.x, tile.y, rotation);

            if(productionEfficiency > 0.1f && Mathf.chanceDelta(effectChance))
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
        }

        @Override
        public void drawLight(){
            Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }
    }
}
