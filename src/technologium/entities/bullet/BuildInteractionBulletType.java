package technologium.entities.bullet;

import arc.func.Cons;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

public class BuildInteractionBulletType extends BulletType {
    public Cons<Building> interaction;

    public BuildInteractionBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public BuildInteractionBulletType() {
        super();
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
        if (build != null) interaction.get(build);
        super.hitTile(b, build, x, y, initialHealth, direct);
    }
}
