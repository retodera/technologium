package technologium.type;

import arc.graphics.*;
import arc.math.Rand;
import arc.math.geom.*;
import arc.struct.Seq;
import arc.math.geom.Mat3D;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.type.*;
import mindustry.world.Block;

public class AsteroidField extends TPlanet {
    public int asteroidCount = 200;
    public float asteroidScale = 1;
    public float fieldRadius = 1.5f;
    public float fieldTilt = 90;
    public Block base, tint;
    public float tintTresh = 0.5f;

    public AsteroidField(String name, Planet parent, float fieldRadius) {
        super(name, parent, 0.01f);
        this.fieldRadius = fieldRadius;

        generator = new AsteroidGenerator();
        orbitRadius = 0;
        accessible = hasAtmosphere = false;
    }

    {
        meshLoader = () -> {
            Seq<GenericMesh> seq = new Seq<>();
            Color col = base.mapColor,
                 tcol = tint.mapColor;
            Rand rand = new Rand();
            for(int i = 0; i < asteroidCount; i++) {
                Vec2 vec = new Vec2(), vec2 = new Vec2();
                vec.setToRandomDirection().setLength(rand.random(1.45f, 1.9f));
                vec2.set(vec.y, rand.random(-0.05f, 0.05f)).rotate(fieldTilt);
                seq.add(new MatMesh(
                    new NoiseMesh(this, i+1, 1, 0.022f + rand.random(0.039f) * asteroidScale, 2, 0.6f, 0.38f, 20, col, tcol, 3, 0.6f, 0.38f, tintTresh),
                    new Mat3D().setTranslation(new Vec3(vec.x, vec2.x, vec2.y).scl(fieldRadius))
                ));
            }

            return new GenericMesh() {
                GenericMesh[] meshes = seq.toArray(GenericMesh.class);

                @Override
                public void render(PlanetParams params, Mat3D projection, Mat3D transform) {
                    for(GenericMesh mesh : meshes) mesh.render(params, projection, transform);
                }

                @Override
                public void dispose() {
                    for(GenericMesh mesh : meshes) mesh.dispose();
                }
            };
        };
    }
}
