package technologium.world.blocks.production;

import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import multicraft.*;
import arc.struct.*;
import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import arc.util.Time;
import arc.util.io.*;
import mindustry.world.draw.*;

// such a simple thing has caused more problems than anything, from the start of the mod creation
public class ItemConstructor extends MultiCrafter {
    public int armCount = 4;
    /** self-explanatory multiplier */
    public float armRotateSpeed = 5f;
    /** offset of the arms */
    public float armsX = 0, armsY = 0;
    /** distance between the center of the block/offset and the arms */
    public float armsOffset = 8f;
    /** arms movement distance towards the center multiplier */
    public float armMove = 4f;
    /** effects used by arms */
    private Effect[] armFx; /* made private because of crash caused by: https://github.com/TheSerjio/AdvancedDataBase/blob/master/src/advanced/Intelligence.java#L105 */
    public Effect craftEffect = Fx.smokeCloud;
    /** offset of the arm effect (relative to the arm) */
    public float armFxOffsetX = 0f, armFxOffsetY = 8f;
    /** construction speed; in ticks */
    public float makeSpeed = 60f;
    private TextureRegion[] armTex;
    public float itemSize = 8f;

    public ItemConstructor(String name) {
        super(name);
    }

    public void setupFx(Effect... fx) {
        armFx = fx;
    }

    @Override
    public void load() {
        super.load();
        armTex = new TextureRegion[Mathf.clamp(armCount, 1, 16)];
        for(int i = 0; i < Mathf.clamp(armCount, 1, 16); i++)
            armTex[i] = Core.atlas.find(name + "-arm" + (i+1), 
            Core.atlas.find(name + "-arm1", name + "-arm"));
    }

    @Override
    public void init() {
        super.init();
        resolvedRecipes.each(r -> {
            r.craftTime = Seq.with(r.input.items).sum(i -> i.amount) * makeSpeed;
            r.craftEffect = craftEffect;
        });
        if(armFx == null) armFx = new Effect[0];
    }

    public class ItemConstructorBuild extends MultiCrafterBuild {
        private int armC = Mathf.clamp(armCount, 1, 16);
        private Seq<AssembleArm> arms = new Seq<>(armC);
        private IntMap<Boolean> chosen = new IntMap<>();
        private Rand rand = new Rand();

        @Override
        public void created() {
            for(int i = 0; i < armC; i++) {
                int[] a = {i};  
                arms.add(new AssembleArm(){{
                    r = 360 / armC * a[0];
                }});
                chosen.put(i, false);
            };
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
                
                float a = Draw.getColor().a;
                Draw.alpha(Mathf.floor(craftingTime / curRecipe().craftTime * (curRecipe().craftTime / makeSpeed)) / (curRecipe().craftTime / makeSpeed));
                Draw.rect(curRecipe().output.items[0].item.fullIcon, x, y, itemSize, itemSize);
                Draw.alpha(a);

                //continue drawing drawers after the DrawDefault
                for(int i = last; i < draw.drawers.length; i++) draw.drawers[i].draw(this);
            }
            else drawArms();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            for(int i = 0; i < armC; i++) {
                AssembleArm a = arms.get(i);

                if(!chosen.containsValue(true, false)) {
                    float b = craftingTime % makeSpeed;
                    if(b > 48 && b < 54) {
                        chosen.put(rand.random(armC - 1), true);
                        chosen.put(rand.random(armC - 1), true);
                    }
                }
    
                if(chosen.get(i) && a.dir != 0 && warmup != 0) {
                    a.ndir = rand.random(1) * 2 - 1;
                    a.dir = 0;
                }
    
                //movement forwards
                if(a.dir == 0) {
                    a.m += 7.5 * warmup * Time.delta;
                    if(a.m > 84 && a.m < 96 && i <= armFx.length && armFx[i] != null && a.fx)  {
                        armFx[i].at(x + armsX + Mathf.cosDeg(a.r) * (armsOffset - armFxOffsetY) + Mathf.sinDeg(a.r) * armFxOffsetX,
                        y + armsY + Mathf.sinDeg(a.r) * (armsOffset - armFxOffsetY) - Mathf.cosDeg(a.r) * armFxOffsetX);
                        a.fx = false;
                    }
                    if(a.m >= 180) {
                        a.dir = a.ndir;
                        a.fx = true;
                        a.m = 0;
                        chosen.put(i, false);
                    }
                }
                //rotation
                else a.r = (a.r + armRotateSpeed * a.dir * warmup * Time.delta) % 360;
            }
        }

        private void drawArms() {
            for(int i = 0; i < armC; i++) {
                AssembleArm a = arms.get(i);
                Draw.rect(armTex[i],
                x + Mathf.cosDeg(a.r) * armsOffset + armsX - Mathf.cosDeg(a.r) * (Math.abs(Mathf.cosDeg(a.m)) * -1 + 1) * armMove,
                y + Mathf.sinDeg(a.r) * armsOffset + armsY - Mathf.sinDeg(a.r) * (Math.abs(Mathf.cosDeg(a.m)) * -1 + 1) * armMove,
                a.r + 90);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(armC);
            for(int i = 0; i < armC; i++) {
                AssembleArm a = arms.get(i);
                write.f(a.r);
                write.f(a.m);
                write.i(a.dir);
                write.i(a.ndir);
                write.bool(a.fx);
                write.bool(chosen.get(i));
            };
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            armC = read.i();
            arms = new Seq<>();
            for(int i = 0; i < armC; i++) arms.add(new AssembleArm());
            for(int i = 0; i < armC; i++) {
                AssembleArm a = arms.get(i);
                a.r = read.f();
                a.m = read.f();
                a.dir = read.i();
                a.ndir = read.i();
                a.fx = read.bool();
                chosen.put(i, read.bool());
            };
        }

        public class AssembleArm {
            protected float r = 0, m = 0;
            protected int dir = 1, ndir = -1;
            protected boolean fx = true;
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
}
