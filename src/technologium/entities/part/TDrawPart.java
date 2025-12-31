package technologium.entities.part;

import mindustry.entities.part.DrawPart;
import mindustry.entities.part.DrawPart.PartParams;

public class TDrawPart { //for now just holds the changed PartParams
    public static final TPartParams params = new TPartParams();

    public static class TPartParams extends DrawPart.PartParams {
        public float[] recoils = {};

        public TPartParams set(float warmup, float reload, float smoothReload, float heat, float recoil, float charge, float x, float y, float rotation, float[] recoils){
            super.set(warmup, reload, smoothReload, heat, recoil, charge, x, y, rotation);
            this.recoils = recoils;
            return this;
        }

        public TPartParams set(PartParams params) {
            warmup = params.warmup;
            reload = params.reload;
            smoothReload = params.smoothReload;
            heat = params.heat;
            recoil = params.recoil;
            charge = params.charge;
            x = params.x;
            y = params.y;
            rotation = params.rotation;
            sideOverride = params.sideOverride;
            sideMultiplier = params.sideMultiplier;
            return this;
        }
    }
}