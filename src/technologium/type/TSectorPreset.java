package technologium.type;

import mindustry.type.*;
import mindustry.gen.Icon;
import arc.Core;

public class TSectorPreset extends SectorPreset {
    public TSectorPreset(String name, Planet planet, int sector) {
        super(name, planet, sector);
    }

    @Override
    public void loadIcon() {
        fullIcon = uiIcon = Core.atlas.find("t-sector-" + name, Icon.terrain == null ? null : Icon.terrain.getRegion());
    }
}