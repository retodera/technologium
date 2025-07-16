package technologium.entities.pattern;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootAlternate;

public class TShootAlternate extends ShootAlternate {
    public float offsetY = 0;

    public TShootAlternate(float spread){
        this.spread = spread;
    }

    public TShootAlternate(){
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int i = 0; i < shots; i++){
            float index = ((totalShots + i + barrelOffset) % barrels) - (barrels-1)/2f;
            handler.shoot(index * spread, offsetY, 0f, firstShotDelay + shotDelay * i);
            if(barrelIncrementer != null) barrelIncrementer.run();
        }
    }
}
