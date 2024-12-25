package technologium.world.blocks.liquid;

import arc.math.Mathf;
import arc.struct.IntSeq;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.LiquidBridge;
import static mindustry.Vars.*;

//same feature as TempConduit
public class TempLiquidBridge extends LiquidBridge {
    public float maxTemp = 0.65f, baseChance = 0.06f;
    public Effect explodeEffect = Fx.generatespark;


    public TempLiquidBridge(String name) {
        super(name);
        placeableLiquid = true;
    }

    public class TempLiquidBridgeBuild extends LiquidBridgeBuild {

        @Override

        public void updateTransport(Building other){
            if(warmup >= 0.25f){
                moved |= moveLiquid(other, liquids.current()) > 0.05f;
            }
        }
        @Override

        public void updateTile(){
            if(timer(timerCheckMoved, 30f)){
                wasMoved = moved;
                moved = false;
            }

            if(Mathf.chance(this.delta() * baseChance * (liquids.current().temperature - maxTemp) * liquids.currentAmount() / liquidCapacity)) {
                this.damage(4f);
                explodeEffect.at(this.x + Mathf.range(this.block.size * tilesize / 2f), this.y + Mathf.range(this.block.size * tilesize / 2f));
            }

            timeSpeed = Mathf.approachDelta(timeSpeed, wasMoved ? 1f : 0f, 1f / 60f);

            time += timeSpeed * delta();

            checkIncoming();

            Tile other = world.tile(link);
            if(!linkValid(tile, other)){
                doDump();
                warmup = 0f;
            }else{
                IntSeq inc = ((ItemBridgeBuild)other.build).incoming;
                int pos = tile.pos();
                if(!inc.contains(pos)){
                    inc.add(pos);
                }

                warmup = Mathf.approachDelta(warmup, efficiency, 1f / 30f);
                updateTransport(other.build);
            }
        }
    }
}
