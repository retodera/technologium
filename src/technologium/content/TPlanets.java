package technologium.content;

import arc.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import technologium.TVars;
import technologium.maps.planet.KudolPlanetGenerator;
import technologium.type.*;

public class TPlanets {
    public static Planet
    /* stars */ beled,
        /* planets */ kudol, kudolAsteroidField;

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
            campaignRuleDefaults.fog = true;
            campaignRuleDefaults.showSpawns = true;
            campaignRuleDefaults.rtsAI = true;
            unlockedOnLand.add(TBlocks.coreTorch);
        }};

        kudolAsteroidField = new AsteroidField("kudol-asteroid-field", kudol, 2.5f){{
            base = TBlocks.solidLavaWall;
            tint = TBlocks.pegmatiteWall;
            fieldTilt = 80;
        }};
    }

    static Mesh makeAtmosphere(Planet planet) {
        return MeshBuilder.buildHex(Color.white, 2, planet.radius + 0.5f);
    }
}