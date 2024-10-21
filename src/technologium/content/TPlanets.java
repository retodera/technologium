package technologium.content;

import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class TPlanets {
    public static Planet
    /* stars */ beled,
            /* planets */ kudol, venjer, nobata, itinbu;

    public static void load() {
        // cyan giant star
        beled = new Planet("beled", null, 15f) {
            {
                bloom = true;
                accessible = false;
                meshLoader = () -> new SunMesh(
                        this, 8,
                        5, 0.5, 2, 1.8, 1,
                        1.1f,
                        Color.valueOf("00fff2"),
                        Color.valueOf("05f5e9"),
                        Color.valueOf("09ebdf"),
                        Color.valueOf("0de0d6"),
                        Color.valueOf("11d6cc"),
                        Color.valueOf("14ccc3"));
            }
        };

        // erekir-like planet
        //volcanic and covered in neoplasm
        kudol = new Planet("kudol", beled, 2f, 3) {
            {
                generator = new SerpuloPlanetGenerator(); //maybe i'll change it later, but i think it'll be hard to make a planet generator
                meshLoader = () -> new HexMesh(this, 10);
                cloudMeshLoader = () -> new MultiMesh(
                        new HexSkyMesh(this, 69, 0.1f, 0.14f, 9, Color.valueOf("7a4118").a(0.75f), 2, 0.42f, 1f, 0.43f),
                        new HexSkyMesh(this, 420, 0.3f, 0.15f, 9, Color.valueOf("ad5c23").a(0.75f), 2, 0.42f, 1.2f,
                                0.45f));
                alwaysUnlocked = true;
                accessible = true;
                allowWaveSimulation = false;
                allowLaunchSchematics = false;
                allowLaunchLoadout = false;
                landCloudColor = Color.valueOf("7a4118");
                atmosphereColor = Color.valueOf("ad5c23");
                atmosphereRadIn = 0.05f;
                atmosphereRadOut = 0.5f;
                orbitSpacing = 30f;
                orbitRadius = 100f;
                clearSectorOnLose = true;
                defaultEnv = Env.terrestrial | Env.scorching;
                defaultCore = TBlocks.coreTorch;
                allowLaunchToNumbered = false; // and probably won't be enabled, until i make a KudolPlanetGenerator
                updateLighting = false;
                itemWhitelist = TItems.kudolItems;
                ruleSetter = r -> {
                    r.waveTeam = TTeams.kaut;
                    r.placeRangeCheck = false;
                    r.showSpawns = true;
                    r.fog = true;
                    r.staticFog = true;
                    r.coreDestroyClear = true;
                    r.onlyDepositCore = false;
                };
                unlockedOnLand.add(TBlocks.coreTorch);
            }
        };
    }
}