package technologium.graphics;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.gl.*;
import arc.util.*;
import technologium.Technologium;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Shaders;

import static mindustry.Vars.*;

//uhh, i made it work, dont ask how, i dont know as well
public class TShaders {
    public static TSurfaceShader lava;
    public static CacheLayer lavaCache;

    public static void init() {
        lava = new TSurfaceShader("lava");

        CacheLayer.add(lavaCache = new CacheLayer.ShaderLayer(lava));
    }

    public static class TSurfaceShader extends Shader {
        Texture noiseTex;

        public TSurfaceShader(String frag) {
            super(Shaders.getShaderFi("screenspace.vert"), getShaderFi(frag + ".frag"));
            loadNoise();
        }

        public String textureName() {
            return "noise";
        }

        public void loadNoise() {
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                t.setFilter(TextureFilter.linear);
                t.setWrap(TextureWrap.repeat);
            };
        }

        @Override
        public void apply() {
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if(hasUniform("u_noise")){
                if(noiseTex == null) noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
                noiseTex.bind(1);
                renderer.effectBuffer.getTexture().bind(0);
                setUniformi("u_noise", 1);
            }
        }
    }

    // public class TLoadShader extends Shader {
    //     public TLoadShader(String frag, String vert) {
    //         super(getShaderFi(frag), getShaderFi(vert));
    //     }
    // }

    public static Fi getShaderFi(String file) {
        return Technologium.tmod.root.child("shaders").child(file);
    }
}
