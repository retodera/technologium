package technologium.world.blocks.units;

import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.*;
import technologium.world.blocks.units.UnitAssemblyPlatform.UnitAssemblyPlatformBuild;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;

import static mindustry.Vars.*;

public abstract class PlatformModuleBlock extends Block {
    public TextureRegion sideRegion1, sideRegion2;

    public PlatformModuleBlock(String name) {
        super(name);
        update = true;
        rotate = true;
        regionRotated1 = 0;
        rotateDraw = false;
        solid = true;
    }

    @Override
    public void load() {
        super.load();
        sideRegion1 = Core.atlas.find(name + "-side1");
        sideRegion2 = Core.atlas.find(name + "-side2");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, sideRegion1};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(plan.rotation < 2 ? sideRegion1 : sideRegion2, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return getLink(team, tile.x, tile.y, rotation) != null;
    }

    public @Nullable UnitAssemblyPlatformBuild getLink(Team team, int x, int y, int rotation) {
        var builds = Seq.with(team.data().buildings).retainAll(b -> b instanceof UnitAssemblyPlatformBuild).<UnitAssemblyPlatformBuild>as();
        if(builds.isEmpty()) return null;
        return builds.find(b -> b.moduleFits(x * tilesize + offset, y * tilesize + offset, rotation, size));
    }

    public abstract class PlatformModuleBuild extends Building {
        public UnitAssemblyPlatformBuild link;
        public int lastChange = -2;

        @Override
        public void updateTile() {
            if(lastChange != world.tileChanges){
                lastChange = world.tileChanges;
                findLink();
            }
        }

        public void findLink(){
            if(link != null) link.removeInjector(this);
            link = getLink(team, tile.x, tile.y, rotation);
            if(link != null) link.addInjector(this);
        }

        @Override
        public void onRemoved() {
            if(link != null) link.removeInjector(this);
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.rect(rotation < 2 ? sideRegion1 : sideRegion2, x, y, rotation * 90);
        }
    }
}
