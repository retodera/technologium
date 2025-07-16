package technologium.entities.pattern;

import arc.util.Nullable;
import arc.util.Time;
import mindustry.entities.pattern.ShootBarrel;

/* fixed the recoils */
public class TShootBarrel extends ShootBarrel {

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int i = 0; i < shots; i++){
            int index = ((i + totalShots + barrelOffset) % (barrels.length / 3)) * 3;
            handler.shoot(barrels[index], barrels[index + 1], barrels[index + 2], firstShotDelay + shotDelay * i);
            if(barrelIncrementer != null) Time.run(firstShotDelay + shotDelay * i, barrelIncrementer::run);
        }
    }
}
