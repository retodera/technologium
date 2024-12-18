package technologium.world;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

//stolen from NightScape
public class StrongAbility extends Ability {
    @Override
    public void update(Unit unit) {
        if(!TMusic.metalstrong.isLooping()) {
            TMusic.metalstrong.play();
            TMusic.metalstrong.setLooping(true);
        }
    }
    public void death(Unit unit) {
        TMusic.metalstrong.setLooping(false);
        TMusic.metalstrong.setPosition(150);
    }
}
