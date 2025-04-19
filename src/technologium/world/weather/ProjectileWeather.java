package technologium.world.weather;

import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.WeatherState;
import mindustry.type.weather.RainWeather;

import java.util.concurrent.ThreadLocalRandom;

// taken from NightScape
public class ProjectileWeather extends RainWeather {
    public BulletType projectile;
    public float dropTime;
    public int x, y, i, dropAmount;

    public ProjectileWeather(String name){
        super(name);
    }

    @Override
    public void update(WeatherState state) {
        super.update(state);

        Time.run(dropTime, () -> {
            for (i = 1; i <= dropAmount; i++){
                x = ThreadLocalRandom.current().nextInt(0, 3601);
                y = ThreadLocalRandom.current().nextInt(0, 3601);
                projectile.create(null, Team.neoplastic, x, y, 0f, 0f, 0f);
            }
        });
    }
}