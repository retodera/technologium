package technologium.type;

import arc.Events;
import arc.util.Time;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Puddle;
import mindustry.type.*;
import mindustry.world.Tile;
import technologium.content.TLiquids;

import static mindustry.entities.Puddles.*;

public class TCellLiquid extends CellLiquid {
    public Seq<Liquid> spreadTargets = new Seq<>();

    public TCellLiquid(String name, Color color) {
        super(name, color);
    }

    public TCellLiquid(String name) {
        super(name);
    }

    @Override
    public void update(Puddle puddle) {
        if(!Vars.state.rules.fire) return;

        if(spreadTargets.any()){
            float scaling = Mathf.pow(Mathf.clamp(puddle.amount / maxLiquid), 2f);
            LocalVariableReactedDefinedInAnEnclosingScopeMustBeFinalOrEffectivelyFinal reacted = new LocalVariableReactedDefinedInAnEnclosingScopeMustBeFinalOrEffectivelyFinal();

            spreadTargets.each(target -> {
                for(var point : Geometry.d4c){
                    Tile tile = puddle.tile.nearby(point);
                    if(tile != null && tile.build != null && tile.build.liquids != null && tile.build.liquids.get(target) > 0.0001f){
                        float amount = Math.min(tile.build.liquids.get(target), maxSpread * Time.delta * scaling);
                        tile.build.liquids.remove(target, amount * removeScaling);
                        deposit(tile, this, amount * spreadConversion);
                        reacted.val = true;
                    }
                }
    
                if(spreadDamage > 0 && puddle.tile.build != null && puddle.tile.build.liquids != null && puddle.tile.build.liquids.get(target) > 0.0001f){
                    reacted.val = true;
    
                    float amountSpread = Math.min(puddle.tile.build.liquids.get(target) * spreadConversion, maxSpread * Time.delta) / 2f;
                    for(var dir : Geometry.d4){
                        Tile other = puddle.tile.nearby(dir);
                        if(other != null){
                            deposit(puddle.tile, other, puddle.liquid, amountSpread);
                        }
                    }
    
                    puddle.tile.build.damage(spreadDamage * Time.delta * scaling);
                }
    
                for(var point : Geometry.d4){
                    Tile tile = puddle.tile.nearby(point);
                    if(tile != null){
                        var other = get(tile);
                        if(other != null && other.liquid == target){
                            float amount = Math.min(other.amount, Math.max(maxSpread * Time.delta * scaling, other.amount * 0.25f * scaling));
                            other.amount -= amount;
                            puddle.amount += amount;
                            reacted.val = true;
                            if(other.amount <= maxLiquid / 3f){
                                other.remove();
                                deposit(tile, puddle.tile, this, Math.max(amount, maxLiquid / 3f));
                            }
                        }
                    }
                }
            });

            if(reacted.val && this == Liquids.neoplasm || this == TLiquids.mutatedNeoplasm)
                Events.fire(Trigger.neoplasmReact);
        }
    }

    private class LocalVariableReactedDefinedInAnEnclosingScopeMustBeFinalOrEffectivelyFinal {
        public boolean val;
    }
}
