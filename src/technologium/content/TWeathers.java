package technologium.content;

import arc.graphics.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Puddles;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import technologium.entities.bullet.BuildInteractionBulletType;
import technologium.graphics.TPal;
import mindustry.world.meta.*;
import technologium.world.weather.*;

import static technologium.world.meta.TAttributes.*;

public class TWeathers {
    public static Weather
    volcanicstorm,
    neoplasticrain,
    neoplasticstorm;

    public static void load() {
        volcanicstorm = new ParticleWeather("volcanicstorm") {{
            color = noiseColor = Color.valueOf("663614");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 165f;
            sizeMin = 90f;
            minAlpha = 0.01f;
            maxAlpha = 0.3f;
            density = 2000f;
            baseSpeed = 7.1f;
            attrs.set(Attribute.light, -0.1f);
            attrs.set(Attribute.water, -0.1f);
            opacityMultiplier = 0.3f;
            force = 0.2f;
            sound = Sounds.wind;
            soundVol = 0.8f;
            duration = 7f * Time.toMinutes;
        }};
        
        neoplasticrain = new ProjectileWeather("neoplasticrain") {{
            attrs.set(neoplasmAttr, 0.25f);
            attrs.set(Attribute.light, -0.05f);
            color = TPal.neoplasm2;
            sound = Sounds.rain;
            soundVol = 0.25f;
            status = TStatusEffects.neoplasmCovered;
            statusDuration = 300;
            padding = 25;
            xspeed = 10;
            yspeed = 25;
            sizeMin = 8;
            sizeMax = 15;
            dropTime = 3f;
            dropAmount = 6;
            projectile = new BuildInteractionBulletType() {{
                speed = drag = lifetime = 0;
                damage = 2;
                collidesAir = false;
                shootEffect = hitEffect = despawnEffect = Fx.none;
                interaction = (build) -> {
                    if(build.block.hasLiquids && build.liquids != null && build.liquids.get(Liquids.water) > 0) {
                        Puddles.deposit(build.tile(), Liquids.neoplasm, 8);
                    };
                };
            }};
        }};

        neoplasticstorm = new ParticleWeather("neoplasticstorm") {{
            color = noiseColor = Color.valueOf("f98f4a");
            particleRegion = "circle-small";
            drawNoise = true;
            statusGround = false;
            useWindVector = true;
            sizeMax = 5f;
            sizeMin = 2.5f;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 2500f;
            baseSpeed = 5.5f;
            attrs.set(neoplasmAttr, 1f);
            attrs.set(Attribute.light, -0.35f);
            status = TStatusEffects.neoplasmCovered;
            statusDuration = 480;
            opacityMultiplier = 0.5f;
            force = 0.2f;
            sound = Sounds.wind;
            soundVol = 0.7f;
            duration = 7f * Time.toMinutes;
        }};
        
    }
}
