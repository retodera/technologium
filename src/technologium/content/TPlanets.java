package technologium.content;

import arc.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import technologium.TVars;
import technologium.maps.planet.KudolPlanetGenerator;
import technologium.type.TPlanet;

public class TPlanets {
    public static Planet
    /* stars */ beled,
        /* planets */ kudol, venjer, gasora, bergin;

    public static void load() {

        beled = new Planet("beled", null, 7.5f) {{
            bloom = true;
            accessible = alwaysUnlocked = TVars.debug;
            icon = "beled";
            meshLoader = () -> new SunMesh(
                this, 9,
                5, 0.5, 2, 1.8, 1, 1.1f,
                Color.valueOf("00fff2"), Color.valueOf("0de0d6"), Color.valueOf("14ccc3"));
        }};

        kudol = new TPlanet("kudol", beled, 2f, 3) {{
            generator = new KudolPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 69, 0.1f, 0.14f, 7, Color.valueOf("85481b").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 420, 0.3f, 0.15f, 7, Color.valueOf("ad5c23").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            atmosphere = makeAtmosphere(this);
            bloom = true;
            alwaysUnlocked = true;
            accessible = true;
            allowWaveSimulation = false;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            landCloudColor = Color.valueOf("7a4118");
            atmosphereColor = Color.valueOf("ff8934");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            orbitSpacing = 30f;
            orbitRadius = 50f;
            icon = "kudol";
            clearSectorOnLose = true;
            defaultEnv = Env.terrestrial | Env.scorching | Env.oxygen;
            defaultCore = TBlocks.coreTorch;
            allowLaunchToNumbered = false; //and probably won't be enabled.
            updateLighting = false;
            ruleSetter = r -> {
                r.waveTeam = TTeams.kaut;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.coreDestroyClear = true;
                r.coreIncinerates = true;
                r.onlyDepositCore = false;
            };
            unlockedOnLand.add(TBlocks.coreTorch);
        }};

        venjer = new TPlanet("venjer", beled, 1.6f, 3) {{
            generator = new SerpuloPlanetGenerator(); // TODO make generator
            meshLoader = () -> new MultiMesh(
                new NoiseMesh(this, 42069, 6, Color.valueOf("51f2bd"), 1.65f, 7, 0.75f, 1, 0),
                new NoiseMesh(this, 69420, 6, 1.47f, 7, 0.75f, 0.75f, 1.5f, Color.valueOf("41d941"), Color.valueOf("0b8c0b"), 7, 0.7f, 0.75f, 0.53f),
                new NoiseMesh(this, 42069, 6, 1.45f, 7, 0.75f, 1f, 1.2f, Color.valueOf("842e2e"), Color.valueOf("5c1a1a"), 7, 0.7f, 0.75f, 0.53f)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 228, 0.1f, 0.14f, 7, Color.valueOf("45e645").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 1337, 0.3f, 0.15f, 7, Color.valueOf("088208").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            atmosphere = makeAtmosphere(this);
            bloom = true;
            alwaysUnlocked = TVars.debug;
            accessible = TVars.debug;
            allowWaveSimulation = false;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            landCloudColor = Color.valueOf("2ee62e");
            atmosphereColor = Color.valueOf("0b8c0b");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            orbitSpacing = 30f;
            orbitRadius = 65f;
            icon = "venjer";
            clearSectorOnLose = true;
            defaultEnv = Env.terrestrial | Env.oxygen | Env.groundWater;
            defaultCore = TBlocks.coreTorch;
            allowLaunchToNumbered = false; // and probably won't be enabled.
            updateLighting = true;
            ruleSetter = r -> {
                r.waveTeam = TTeams.kaut;
                r.placeRangeCheck = true;
                r.showSpawns = true;
                r.coreDestroyClear = true;
                r.onlyDepositCore = true;
            };
        }};
    }

    static Mesh makeAtmosphere(Planet planet) {
        return MeshBuilder.buildHex(Color.white, 2, planet.radius + 0.5f);
    }
}