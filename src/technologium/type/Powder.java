package technologium.type;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.ObjectSet;
import arc.util.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;
import mindustry.type.*;
import mindustry.world.Tile;
import mindustry.world.meta.*;

public class Powder extends UnlockableContent implements Senseable {
    public static final int animationFrames = 50;

    protected static final Rand rand = new Rand();

    /** Color used in pipes and on the ground. */
    public Color color;
    /** Color used in bars. */
    public @Nullable Color barColor;
    /** Color used to draw lights. Note that the alpha channel is used to dictate brightness. */
    public Color lightColor = Color.clear.cpy();
    /** 0-1, 0 is completely not flammable, anything above that may catch fire when exposed to heat, 0.5+ is very flammable. */
    public float flammability;
    /** temperature: 0.5 is 'room' temperature, 0 is very cold, 1 is molten hot */
    public float temperature = 0.5f;
    /** how prone to exploding this powder is, when heated. 0 = nothing, 1 = nuke */
    public float explosiveness;
    /** If true, this liquid is hidden in most UI. */
    public boolean hidden;

    public Powder(String name, Color color){
        super(name);
        this.color = new Color(color);
    }

    public Powder(String name){
        this(name, new Color(Color.black));
    }

    @Override
    public boolean isHidden(){
        return hidden;
    }

    public int getAnimationFrame(){
        return (int)(Time.time / 230 * animationFrames + id*5) % animationFrames;
    }

    public Color barColor(){
        return barColor == null ? color : barColor;
    }

    @Override
    public void setStats(){
        stats.addPercent(Stat.explosiveness, explosiveness);
        stats.addPercent(Stat.flammability, flammability);
        stats.addPercent(Stat.temperature, temperature);
    }

    @Override
    public double sense(LAccess sensor){
        if(sensor == LAccess.color) return color.toDoubleBits();
        if(sensor == LAccess.id) return getLogicId();
        return Double.NaN;
    }

    @Override
    public Object senseObject(LAccess sensor){
        if(sensor == LAccess.name) return name;
        return noSensed;
    }

    @Override
    public String toString(){
        return localizedName;
    }

    @Override
    public ContentType getContentType(){
        return ContentType.liquid;
    }
}
