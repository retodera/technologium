package technologium.maps.planet;

import mindustry.ai.Astar;
import mindustry.content.Blocks;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import technologium.content.TLoadouts;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.util.Tmp;
import arc.util.noise.*;

import static technologium.content.TBlocks.*;
import static mindustry.Vars.*;

public class KudolPlanetGenerator extends PlanetGenerator {
    public float heightScl = 0.9f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 1.6f;

    public static float lavaThresh = 0.25f, lavaScl = 0.8f, solidLavaThresh = 0.2f;
    public static int lavaSeed = 2054, lavaOct = 2;
    public static float neoplasticThresh = 0.4f, neoplasticScl = 0.8f;
    public static int neoplasticSeed = 2056, neoplasticOct = 3;
    public static float sandThresh = 0.7f, sandScl = 14f, hotAshTresh = 0.3f, hotAshScl = 2f;
    public static float airThresh = 0.13f, airScl = 14;
    public static int tuffSeed = 2053, tuffOct = 2;
    public static float tuffScl = 0.9f, tuffMag = 0.2f;

    Block[] terrain = {solidLavaFloor, solidLavaFloor, ash, neoplasticFloor, neoplasticFloor, neoplasticFloor, volcanicSandFloor, volcanicSandFloor, volcanicStone, volcanicStone, pegmatiteStone, pegmatiteStone};

    {
        baseSeed = 2055;
        defaultLoadout = TLoadouts.coreTorch;
    }

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    @Override
    public void getColor(Vec3 position, Color out){
        Block block = getBlock(position);

        out.set(block.mapColor).a(1f - block.albedo);
    }

    @Override
    public float getSizeScl(){
        return 2000 * 1.07f * 6f / 5f;
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 8, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    Block getBlock(Vec3 position) {
        float px = position.x, py = position.y, pz = position.z;

        float temp = rawTemp(position);
        float height = rawHeight(position);

        height *= 1.2f;
        height = Mathf.clamp(height);

        Block result = terrain[Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)];

        if(temp < 0.6 && result == neoplasticFloor)
            return tuffFloor;
        if(temp < 0.9 && result == ash)
            return hotAsh;
        if(temp < 0.6 && temp > 0.1 && Ridged.noise3d(seed + lavaSeed, px + 2f, py + 8f, pz + 1f, lavaOct, lavaScl) < 0.9)
            return pegmatiteStone;
        if(temp < solidLavaThresh && Ridged.noise3d(seed + lavaSeed, px + 2f, py + 8f, pz + 1f, lavaOct, lavaScl) > lavaThresh)
            result = solidLavaFloor;

        
        return result;
    }

    @Override
    public void genTile(Vec3 position, TileGen tile) {
        tile.floor = getBlock(position);

        if(tile.floor == solidLavaFloor && rand.chance(0.01))
            tile.floor = solidLavaFumarole;
        else if(tile.floor == tuffFloor && rand.chance(0.01))
            tile.floor = tuffFumarole;
        else if(tile.floor == volcanicStone && rand.chance(0.01))
            tile.floor = volcanicCrater;

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh)
            tile.block = Blocks.air;

        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6)
            tile.floor = solidLavaFloor;
    }

    @Override
    protected void generate() {
        float temp = rawTemp(sector.tile.v);

        if(temp > 0.7)
            pass((x, y) -> {
                float noise = noise(x + 782, y, 7, 0.8f, 280f, 1f);
                if(noise > 0.62f){
                    if(noise > 0.635f)
                        floor = lavaLiquid;
                    else
                        floor = solidLavaFloor;
                    ore = Blocks.air;
                }

                if(noise > 0.55f && floor == neoplasticFloor)
                    floor = tuffFloor;
            });

        cells(4);

        pass((x, y) -> {
            if(floor == volcanicStone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f)
                block = volcanicWall;
        });

        float length = width/2.6f;
        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
        spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
        endX = (int)(-trns.x + width/2f), endY = (int)(-trns.y + height/2f);
        float maxd = Mathf.dst(width/2f, height/2f);

        erase(spawnX, spawnY, 15);
        brush(pathfind(spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 300f : 0f) + maxd - tile.dst(width/2f, height/2f)/10f, Astar.manhattan), 9);
        erase(endX, endY, 15);

        pass((x, y) -> {
            if(floor != neoplasticFloor || nearWall(x, y)) return;

            float noise = noise(x + 300, y - x*1.6f + 100, 4, 0.8f, neoplasticScl, 1f);

            if(noise > neoplasticThresh)
                floor = neoplasticLiquid;
        });

        median(2, 0.6, neoplasticLiquid);

        blend(lavaLiquid, solidLavaFloor, 4);

        blend(lavaLiquid, volcanicStone, 4);

        distort(10f, 12f);
        distort(5f, 7f);
        
        median(2, 0.6, neoplasticLiquid);

        median(3, 0.6, lavaLiquid);

        pass((x, y) -> {
            if(noise(x, y + 600 + x, 5, 0.86f, 60f, 1f) < 0.41f && floor == ash)
                floor = hotAsh;

            if(floor == lavaLiquid && Mathf.within(x, y, spawnX, spawnY, 30f + noise(x, y, 2, 0.8f, 9f, 15f)))
                floor = volcanicStone;

            if((floor == neoplasticLiquid || floor == neoplasticFloor) && block.isStatic())
                block = neoplasticWall;

            float max = 0;
            for(Point2 p : Geometry.d8)
                max = Math.max(max, world.getDarkness(x + p.x, y + p.y));
            if(max > 0){
                block = floor.asFloor().wall;
                if(block == Blocks.air) block = volcanicWall;
            }
        });

        inverseFloodFill(tiles.getn(spawnX, spawnY));

        erase(endX, endY, 6);

        tiles.getn(endX, endY).setOverlay(Blocks.spawn);

        pass((x, y) -> {

            if(block != Blocks.air && nearAir(x, y) && noise(x + 782, y, 4, 0.8f, 38f, 1f) > 0.665f)
                ore = tinWallOre;
            else if(!nearWall(x, y)){
                if(noise(x + 55, y + x*2 + 100, 4, 0.8f, 55f, 1f) > 0.76f)
                    ore = hematiteOre;

                if(noise(x + 150, y + x*2 + 100, 4, 0.8f, 55f, 1f) > 0.76f)
                    ore = bauxiteOre;
            }

            if(noise(x + 999, y + 600 - x, 5, 0.8f, 45f, 1f) < 0.44f && floor == ash){
                floor = hotAsh;
                if(block == ashWall) block = hotAshWall;
                else if(block == ashTree) block = hotAshTree;
            }
        });

        pass((x, y) -> {
            if(ore.asFloor().wallOre || block.itemDrop != null || (block == Blocks.air && ore != Blocks.air))
                removeWall(x, y, 3, b -> b instanceof TallBlock);
        });

        trimDark();

        int minVents = rand.random(6, 9);
        int ventCount = 0;

        outer:
        for(Tile tile : tiles)
            if(tile.floor() == solidLavaFloor && rand.chance(0.002)){
                int radius = 2;
                for(int x = -radius; x <= radius; x++)
                    for(int y = -radius; y <= radius; y++){
                        Tile other = tiles.get(x + tile.x, y + tile.y);
                        if(other == null || other.floor() != solidLavaFloor || other.block().solid)
                            continue outer;
                    }

                ventCount ++;
                for(var pos : SteamVent.offsets)
                    tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1).setFloor(solidLavaFumarole.asFloor());
            }    

        int iterations = 0;
        int maxIterations = 5;

        while(ventCount < minVents && iterations++ < maxIterations){
            outer:
            for(Tile tile : tiles){
                if(rand.chance(0.00018 * (1 + iterations)) && !Mathf.within(tile.x, tile.y, spawnX, spawnY, 5f)){
                    int radius = 1;
                    for(int x = -radius; x <= radius; x++)
                        for(int y = -radius; y <= radius; y++){
                            Tile other = tiles.get(x + tile.x, y + tile.y);
                            if(other == null || other.block().solid || other.floor().attributes.get(Attribute.steam) != 0 || other.floor() == lavaLiquid || other.floor() == neoplasticLiquid)
                                continue outer;
                        }  

                    Block
                    floor = tuffFloor,
                    secondFloor = tuffFloor,
                    vent = tuffFumarole;

                    int xDir = 1;
                    if(tile.floor() == solidLavaFloor){
                        floor = secondFloor = solidLavaFloor;
                        vent = solidLavaFumarole;
                    }

                    ventCount ++;
                    for(var pos : SteamVent.offsets){
                        Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                        other.setFloor(vent.asFloor());
                    }

                    int crad = rand.random(6, 14), crad2 = crad * crad;
                    for(int cx = -crad; cx <= crad; cx++){
                        for(int cy = -crad; cy <= crad; cy++){
                            int rx = cx + tile.x, ry = cy + tile.y;
                            float rcy = cy + cx*0.9f;
                            if(cx*cx + rcy*rcy <= crad2 - noise(rx, ry + rx * 2f * xDir, 2, 0.7f, 8f, crad2 * 1.1f)){
                                Tile dest = tiles.get(rx, ry);
                                if(dest != null && dest.floor().attributes.get(Attribute.steam) == 0 && dest.floor() != neoplasticLiquid && dest.floor() != lavaLiquid){
                                    dest.setFloor(rand.chance(0.08) ? secondFloor.asFloor() : floor.asFloor());

                                    if(dest.block().isStatic())
                                        dest.setBlock(floor.asFloor().wall);
                                }
                            }
                        }
                    }

                }
            }
        }

        for(Tile tile : tiles)
            if(tile.overlay().needsSurface && !tile.floor().hasSurface())
                tile.setOverlay(Blocks.air);

        decoration(0.017f);

        state.rules.env = sector.planet.defaultEnv;
        state.rules.placeRangeCheck = true;

        Schematics.placeLaunchLoadout(spawnX, spawnY);

        state.rules.waves = false;
        state.rules.showSpawns = true;
    }
}
