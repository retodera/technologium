package technologium.world.blocks.production;

import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import multicraft.*;
import arc.struct.Seq;
import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.entities.Effect;
import mindustry.world.draw.*;

import static mindustry.Vars.*;

// such a simple thing has caused more problems than anything, from the start of the mod creation
public class ItemConstructor extends MultiCrafter {
    public int armCount = 4;
    /** self-explanatory multiplier */
    public float armRotateSpeed = 1f, armMoveSpeed = 1f;
    /** offset of the arms */
    public float armsX = 0, armsY = 0;
    /** distance between the center of the block/offset and the arms */
    public float armsOffset = 8f;
    /** arms movement distance towards the center multiplier */
    public float armMove = 4f;
    /** effects used by arms */
    public Effect[] armFx = {};
    /** offset of the effect */
    public float fxOffsetX = 0f, fxOffsetY = 8f;    
    private TextureRegion[] armTex;

    public ItemConstructor(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        armTex = new TextureRegion[Mathf.clamp(armCount, 1, 16)];
        for(int i = 0; i < Mathf.clamp(armCount, 1, 16); i++)
            armTex[i] = Core.atlas.find(name + "-arm" + (i+1), 
            Core.atlas.find(name + "-arm1", name + "-arm"));
    }

    public class ItemConstructorBuild extends MultiCrafterBuild {
        private int armC = Mathf.clamp(armCount, 1, 16);
        private Seq<AssembleArm> arms = new Seq<>(armC);
        private Rand rand = new Rand();

        @Override
        public void created() {
            for(int i = 0; i < armC; i++) arms.add(new AssembleArm());
        }

        public AssembleArm getArm(int i) {
            return arms.get(i);
        }

        @Override
        public void draw() {
            if(drawer instanceof DrawMulti) {
                DrawMulti draw = (DrawMulti)drawer;
                int last = 1;
                //DrawDefault is only for icon & plan, cause it's the arms, and they're drawn separately
                for(int i = 0; i < draw.drawers.length && !(draw.drawers[i] instanceof DrawDefault); i++, last++) 
                    draw.drawers[i].draw(this);
                    
                if(last == draw.drawers.length) return;

                drawArms();

                //continue drawing drawers after the DrawDefault
                for(int i = last; i < draw.drawers.length; i++) draw.drawers[i].draw(this);
            }
            else drawArms();
        }

        private void drawArms() {
            //used this instead of just totalProgress() because the arms will stop and will have to continue moving normally
            float prog = state.isPaused() ? 0 : edelta();
    
            for(int i = 0; i < armC; i++) {
                AssembleArm a = arms.get(i);
                float r = a.r + 360 / armC * i;
    
                //randomly cause the assembly arms to stop & move forward
                if(rand.nextInt() % 200 / (i + 1) == 1 && a.dir != 0 && warmup != 0 && !state.isPaused()) {
                    a.ndir = -a.dir;
                    a.dir = 0;
                }
    
                //rotation
                if(a.dir != 0) {
                    a.r = (a.r + prog * armRotateSpeed * 5 * a.dir * warmup) % 360;
                    Draw.rect(armTex[i], x + Mathf.cosDeg(r) * armsOffset + armsX, y + Mathf.sinDeg(r) * armsOffset + armsY, r + 90);
                }
    
                //movement forwards
                else {
                    a.m += prog * 10 * armMoveSpeed * warmup;
                    Draw.rect(armTex[i],
                        x + Mathf.cosDeg(r) * armsOffset - Mathf.cosDeg(r) * Mathf.sinDeg(a.m) * armMove + armsX,
                        y + Mathf.sinDeg(r) * armsOffset - Mathf.sinDeg(r) * Mathf.sinDeg(a.m) * armMove + armsY,
                        r + 90);
                    if(a.m > 88 && a.m < 92 && i <= armFx.length && armFx[i] != null && a.fx)  {
                            armFx[i].at(x + armsX + Mathf.cosDeg(r) * (armsOffset - fxOffsetY) + Mathf.sinDeg(r) * fxOffsetX,
                                y + armsY + Mathf.sinDeg(r) * (armsOffset - fxOffsetY) - Mathf.cosDeg(r) * fxOffsetX);
                                a.fx = false;
                        }
                            
                    if(a.m >= 180) {
                        a.dir = a.ndir;
                        a.fx = true;
                        a.m = 0;
                    }
                }
            }
        }
    }

    public class AssembleArm {
        private float r = 0, m = 0;
        private int dir = 1, ndir = -1;
        private boolean fx = true;
        public AssembleArm(){}
        
        public float r() {
            return r;
        }

        public float m() {
            return m;
        }

        public int dir() {
            return dir;
        }

        public boolean fx() {
            return fx;
        }
    }
}
