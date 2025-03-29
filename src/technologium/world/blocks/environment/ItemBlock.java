package technologium.world.blocks.environment;

import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import technologium.type.Fruit;
import technologium.world.blocks.environment.GrowingTreeBlock.GrowingTreeBuild;
import technologium.world.blocks.production.ACCrane.ACCraneBuild;
import arc.graphics.g2d.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.type.*;
import arc.math.Mathf;

import static mindustry.Vars.*;

public class ItemBlock extends Block {
    /** if item is a fruit */
    public float minPlantTime = 1800, maxPlantTime = 3600;
    public Item item;

    public ItemBlock(String name) {
        this(name, Items.copper);
    }

    public ItemBlock(String name, Item item) {
        super(name);
        this.item = item;
        drawTeamOverlay = drawDisabled = false;
        requirements = ItemStack.with(item, Mathf.ceil(1 / state.rules.deconstructRefundMultiplier));
        buildVisibility = BuildVisibility.editorOnly;
        hasItems = hasLiquids = hasPower = false;
        solid = rotate = configurable = false;
        update = true;
    }

    @Override
    public void setStats() {}

    @Override
    public void setBars() {}

    @Override
    public boolean isAccessible() {
        return state.isEditor();
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{item.fullIcon};
    }

    public class ItemBuild extends Building {
        public @Nullable Building parent;
        protected int parentPos = -1;
        public float plantTime = 0, progress = 0;

        @Override
        public boolean collide(Bullet other) {
            return false;
        }

        @Override
        public boolean collision(Bullet other) {
            return false;
        }

        @Override
        public boolean canPickup() {
            return false;
        }

        @Override
        public void damage(float damage) {}

        @Override
        public void damage(Bullet bullet, Team source, float damage) {}

        @Override
        public void damage(float amount, boolean withEffect) {}

        @Override
        public void damage(Team source, float damage) {}

        @Override
        public void draw() {
            Draw.rect(item.fullIcon, x, y);
        }

        @Override
        public void created() {
            super.created();
            if(item == null) item = Items.copper;
        }
        
        @Override
        public void updateTile() {
            if(team != Team.derelict) {
                indexer.removeIndex(tile);
                team = Team.derelict;
                indexer.addIndex(tile);
            }
            requirements = ItemStack.with(item, Mathf.ceil(1 / state.rules.deconstructRefundMultiplier));
            parent = world.build(parentPos);
            parent = parent instanceof GrowingTreeBuild || parent instanceof ACCraneBuild ? parent : null;
            Fruit fruit = ((Fruit)item);
            if(!(item instanceof Fruit) || (fruit.tree == null && fruit.seed == null && ((Fruit)fruit.seed).tree == null)) return;
            if(plantTime == 0) plantTime = Mathf.random(minPlantTime, maxPlantTime);
            progress += Time.delta;
            if(progress >= plantTime) {
                progress = plantTime = 0;
                tile.setNet(fruit.seed == fruit && fruit.seed != null ? fruit.tree : ((Fruit)fruit.seed).tree);
                ((GrowingTreeBuild)tile.build).parentPos = parentPos;
            }
        }

        @Override
        public void write(Writes write) {
            write.f(plantTime);
            write.f(progress);
            write.i(parent == null ? -1 : parent.pos());
        }

        @Override
        public void read(Reads read, byte revision) {
            plantTime = read.f();
            progress = read.f();
            parentPos = read.i();
        }
    }
}
