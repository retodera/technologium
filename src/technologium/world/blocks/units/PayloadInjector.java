package technologium.world.blocks.units;

import arc.math.Angles;
import arc.math.geom.Vec2;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.Building;
import mindustry.world.blocks.payloads.*;

import static mindustry.Vars.*;

public class PayloadInjector extends PlatformModuleBlock {
    public float payloadLimit = 3;

    public PayloadInjector(String name) {
        super(name);
        acceptsPayload = acceptsUnitPayloads = true;
    }

    public class PayloadInjectorBuild extends PlatformModuleBuild {
        public @Nullable Payload payload;

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return this.payload == null && payload.fits(payloadLimit) && link != null && link.requiredPayloads().has(payload.content()) && link.acceptInjectPayloads();
        }

        @Override
        public Payload takePayload() {
            Payload p = payload;
            payload = null;
            return p;
        }

        @Override
        public void handlePayload(Building source, Payload payload) {
            this.payload = payload;
            int a = size * tilesize/2;
            Vec2 tmp = new Vec2(source.x, source.y).clamp(x-a, y-a, x+a, y+a);
            payload.set(tmp.x, tmp.y, payload.rotation());
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(link != null && payload != null) {
                if(payload instanceof UnitPayload up) up.unit.rotation = Angles.moveToward(up.unit.rotation, Angles.angle(x, y, link.x, link.y), Time.delta);
                Vec2 vec = new Vec2(payload.x(), payload.y()).approachDelta(new Vec2(x, y), delta());
                payload.set(vec.x, vec.y, payload.rotation());
                if(payload.within(x, y, 0.01f) && link.acceptPayload(this, payload)) {
                    link.injectPayload(payload);
                    payload = null;
                }
            }
        }

        @Override
        public void draw() {
            super.draw();
            if(payload != null) payload.draw();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            Payload.write(payload, write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            payload = Payload.read(read);
        }
    }
}
