package multicraft;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import technologium.Technologium;

public class RecipeCat implements Comparable<RecipeCat> {
    public static final Seq<RecipeCat> all = new Seq<>();

    public static final RecipeCat

    melting = new RecipeCat("melting", Technologium.tmod.name),
    smelting = new RecipeCat("smelting", Technologium.tmod.name),
    alloySmelting = new RecipeCat("alloy-smelting", Technologium.tmod.name),
    powerGeneration = new RecipeCat("power-generation", Technologium.tmod.name);

    public final String name, modName;
    public final int id;
    
    public RecipeCat(String name, String modName){
        this.name = name;
        this.modName = modName;
        id = all.size;
        all.add(this);
    }

    public String localized(){
        return Core.bundle.get("recipecat." + name);
    }

    public TextureRegion icon(){
        return Core.atlas.find(modName + "-recipecat-" + name);
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public int compareTo(RecipeCat o){
        return id - o.id;
    }
}
