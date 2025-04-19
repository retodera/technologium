package technologium.world.blocks.production;

import arc.audio.Sound;
import arc.util.Time;
import mindustry.gen.Sounds;
import mindustry.world.blocks.production.Drill;

public class ManualDrill extends Drill {
    public Sound clickSound = Sounds.click;
    public float speedPerClick = 0.1f;
    public float maxSpeed = 1;
    /**per tick */
    public float speedFall = 0.05f / 60f;

    public ManualDrill(String name) {
        super(name);
        configurable = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("items");
    }

    public class ManualDrillBuild extends DrillBuild {
        public float drillSpeed = 0;

        @Override
        public void pickedUp() {
            super.pickedUp();
            drillSpeed = 0;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            drillSpeed -= speedFall * Time.delta * warmup;
            drillSpeed = Math.max(drillSpeed, 0);
        }

        @Override
        public float efficiencyScale() {
            return drillSpeed;
        }

        @Override
        public boolean configTapped() {
            drillSpeed += speedPerClick;
            drillSpeed = Math.min(drillSpeed, maxSpeed);
            clickSound.at(this);
            return false;
        }
    }
}
