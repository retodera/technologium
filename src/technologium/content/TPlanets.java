package technologium.content;

import arc.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import technologium.TVars;
import mindustry.game.Team;

import static technologium.TVars.*;

public class TPlanets {
    public static Planet
    /* stars */ beled,
        /* planets */ kudol, venjer, nobata, itinbu, mita;

    public static void load() {

        beled = new Planet("beled", null, 15f) {{
            bloom = true;
            accessible = alwaysUnlocked = TVars.debug;
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
        }};

        kudol = new Planet("kudol", beled, 2f, 3) {{
            generator = new SerpuloPlanetGenerator(); // TODO make generator
            meshLoader = () -> new MultiMesh(
                new NoiseMesh(this, 2281337, 6, 1.9f, 7, 0.75f, 0.75f, 1.2f, Color.valueOf("331b0b"), Color.valueOf("a35721"), 7, 0.7f, 0.75f, 0.53f),
                new NoiseMesh(this, 1337228, 6, 1.9f, 7, 0.75f, 0.75f, 1.2f, Color.valueOf("8f6b48"), Color.valueOf("bf8f60"), 7, 0.7f, 0.75f, 0.53f)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 69, 0.1f, 0.14f, 6, Color.valueOf("85481b").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 420, 0.3f, 0.15f, 6, Color.valueOf("ad5c23").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
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
            icon = "kudol";
            clearSectorOnLose = true;
            defaultEnv = Env.terrestrial | Env.scorching | Env.oxygen;
            defaultCore = TBlocks.coreTorch;
            allowLaunchToNumbered = false; //and probably won't be enabled.
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
        }};

        venjer = new Planet("venjer", beled, 1.5f, 2) {{
            generator = new SerpuloPlanetGenerator(); // TODO make generator
            meshLoader = () -> new NoiseMesh(this, 69420, 5, 1.4f, 7, 1f, 0.75f, 1.2f, Color.valueOf("12b312"), Color.valueOf("663614"), 7, 1f, 0.75f, 0.53f);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 228, 0.1f, 0.14f, 6, Color.valueOf("2ee62e").a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 1337, 0.3f, 0.15f, 6, Color.valueOf("29cc29").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            alwaysUnlocked = TVars.debug; // before i'll end the Kudol campaign
            accessible = TVars.debug; // before i'll end the Kudol campaign
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
            itemWhitelist = TItems.kudolItems;
            ruleSetter = r -> {
                r.waveTeam = TTeams.kaut;
                r.placeRangeCheck = true;
                r.showSpawns = true;
                r.coreDestroyClear = true;
                r.onlyDepositCore = true;
            };
        }};

        mita = new Planet("mitaplanet", beled, 1f, 1) {{
            generator = new SerpuloPlanetGenerator();
            meshLoader = () -> new NoiseMesh(this, 42526272, 5, 0.9f, 7, 1f, 0.75f, 1.2f, Team.malis.palette[0], Team.sharded.palette[0], 7, 1f, 0.75f, 0.53f);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 101, 0.1f, 0.14f, 6, Team.malis.palette[0].a(0.75f), 2, 0.42f, 1f, 0.43f),
                new HexSkyMesh(this, 404, 0.3f, 0.15f, 6, Team.malis.palette[1].a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            accessible = visible = alwaysUnlocked = misideRelease;
            updateLighting = true;
            itemWhitelist = TItems.mitaItems;
            allowLaunchToNumbered = false;
            defaultEnv = Env.terrestrial | Env.oxygen;
            allowWaveSimulation = false;
            allowLaunchSchematics = false;
            allowLaunchLoadout = false;
            landCloudColor = Team.malis.palette[2];
            atmosphereColor = Team.malis.palette[1];
            atmosphereRadIn = 0.05f;
            atmosphereRadOut = 0.5f;
            icon = "mitaplanet";
            ruleSetter = r -> {
                r.waveTeam = TTeams.kaut;
                r.placeRangeCheck = false;
                r.showSpawns = false;
                r.coreDestroyClear = true;
                r.onlyDepositCore = true;
            };
        }};
    }
}