package technologium.entities;

import mindustry.gen.Unit;
import technologium.world.TMusic;

//partially stolen from NightScape
public class StrongAbility extends TAbility {
    boolean loop = true;

    @Override
    public void update(Unit unit) {
        if(loop) TMusic.metalstrong.play();
        TMusic.metalstrong.setLooping(loop);
    }

    public void death(Unit unit) {
        loop = false;
        TMusic.metalstrong.setLooping(loop);
        TMusic.metalstrong.setPosition(150);
    }
}
