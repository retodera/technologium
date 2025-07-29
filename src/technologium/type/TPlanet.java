package technologium.type;

import arc.graphics.*;
import arc.graphics.g3d.Camera3D;
import mindustry.graphics.Shaders;
import mindustry.type.Planet;

public class TPlanet extends Planet {
    public Mesh atmosphere;

    public TPlanet(String name, Planet parent, float radius) {
        super(name, parent, radius);
    }

    public TPlanet(String name, Planet parent, float radius, int sectorSize) {
        super(name, parent, radius, sectorSize);
    }

    @Override
    public void drawAtmosphere(Mesh atmosphere, Camera3D cam){
        Gl.depthMask(false);

        Blending.additive.apply();

        Shaders.atmosphere.camera = cam;
        Shaders.atmosphere.planet = this;
        Shaders.atmosphere.bind();
        Shaders.atmosphere.apply();

        // usually, atmosphere = MeshBuilder.buildHex(Color.white, 2, false, 1.5f)
        // some planets might experience way bigger radius than 1.5f
        if(this.atmosphere == null) atmosphere.render(Shaders.atmosphere, Gl.triangles);
        else this.atmosphere.render(Shaders.atmosphere, Gl.triangles);

        Blending.normal.apply();

        Gl.depthMask(true);
    }
}
