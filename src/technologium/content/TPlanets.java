package technologium.content;

import arc.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import technologium.TVars;

public class TPlanets {
    public static Planet
    /* stars */ beled,
        /* planets */ kudol, venjer;

    public static void load() {

        beled = new Planet("beled", null, 15f) {{
            bloom = true;
            accessible = alwaysUnlocked = TVars.debug;
            icon = "beled";
            meshLoader = () -> new SunMesh(
                this, 9,
                5, 0.5, 2, 1.8, 1, 1.1f,
                Color.valueOf("00fff2"), Color.valueOf("0de0d6"), Color.valueOf("14ccc3"));
        }};

        kudol = new Planet("kudol", beled, 2.1f, 3) {{
            generator = new SerpuloPlanetGenerator(); // TODO make generator
            generator.defaultLoadout = TLoadouts.coreTorch;
            meshLoader = () -> new MultiMesh(
                new SunMesh(this, 6,
                3, 0.5, 2, 1.8, 1, 1.1f,
                Color.valueOf("ffbc7a"), Color.valueOf("eb8c2d")),
                new NoiseMesh(this, 2281337, 6, 1.88f, 7, 0.75f, 0.75f, 1.4f, Color.valueOf("331b0b"), Color.valueOf("a35721"), 7, 0.7f, 0.75f, 0.53f),
                new NoiseMesh(this, 1337228, 6, 1.73f, 7, 0.75f, 1f, 2.3f, Color.valueOf("8f6b48"), Color.valueOf("bf8f60"), 7, 0.7f, 0.75f, 0.53f)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 69, 0.1f, 0.14f, 7, Color.valueOf("85481b").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 420, 0.3f, 0.15f, 7, Color.valueOf("ad5c23").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            bloom = true;
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

        venjer = new Planet("venjer", beled, 1.6f, 2) {{
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
            bloom = true;
            alwaysUnlocked = TVars.debug;
            accessible = TVars.debug;
            allowWaveSimulation = false;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            landCloudColor = Color.valueOf("2ee62e");
            atmosphereColor = Color.valueOf("29cc29");
            atmosphereRadIn = 0.05f;
            atmosphereRadOut = 0.5f;
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
}