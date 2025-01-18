package technologium.entities;

import arc.*;
import arc.scene.ui.layout.*;
import mindustry.entities.abilities.Ability;

//a little bit of ctrl+c ctrl+v a newer version of Ability.java from github
public class TAbility extends Ability {
    protected static final float descriptionWidth = 350f;

    public void addStats(Table t){
        if(Core.bundle.has(getBundle() + ".description")){
            t.add(Core.bundle.get(getBundle() + ".description")).wrap().width(descriptionWidth);
            t.row();
        }
    }

    public String abilityStat(String stat, Object... values){
        return Core.bundle.format("ability.stat." + stat, values);
    }

    /** @return localized ability name; mods should override this. */
    public String localized(){
        return Core.bundle.get(getBundle());
    }

    public String getBundle(){
        var type = getClass();
        return "ability." + (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName().replace("Ability", "").toLowerCase();
    }
}
