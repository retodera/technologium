package technologium.logic;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import mindustry.logic.*;
import technologium.world.blocks.logic.Projector;
import mindustry.gen.*;
import mindustry.Vars;

public class ConfigProjector {

    public static class ConfigProjectorInstruction implements LExecutor.LInstruction {
        public LVar block, projSize, displaySize, x, y;

        public ConfigProjectorInstruction() {}
        public ConfigProjectorInstruction(LVar block, LVar projSize, LVar displaySize, LVar x, LVar y) {
            this.block = block;
            this.projSize = projSize;
            this.displaySize = displaySize;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run(LExecutor exec) {
            Building build = block.building();
            if(build instanceof Projector.ProjectorBuild proj) {
                proj.projSize = Mathf.clamp(projSize.numf(), 1, 16);
                proj.displaySize = Mathf.clamp(displaySize.numi(), 1, 256);
                proj.targetX = Mathf.clamp(x.numf(), proj.projSize / 2, Vars.world.width() - proj.projSize / 2);
                proj.targetY = Mathf.clamp(y.numf(), proj.projSize / 2, Vars.world.height() - proj.projSize / 2);
            }
        }
    }

    public static class ConfigProjectorStatement extends LStatement {
        public String block = "projector1", projSize = "1", displaySize = "128", x = "@thisx", y = "@thisy";

        public LCategory category() {
            return LCategory.block;
        }

        @Override
        public void build(Table table) {
            table.clearChildren();
            table.add("configure ");
            field(table, block, str -> block = str);
            table.add(" to ");
            field(table, projSize, str -> projSize = str);
            table.add(" & ");
            field(table, displaySize, str -> displaySize = str);
            table.add(" & (");
            field(table, x, str -> x = str);
            table.add(",");
            field(table, y, str -> y = str);
            table.add(")");
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new ConfigProjectorInstruction(builder.var(block), builder.var(projSize), builder.var(displaySize), builder.var(x), builder.var(y));
        }

        public static ConfigProjectorStatement read(String[] tokens) {
            ConfigProjectorStatement statement = new ConfigProjectorStatement();
            statement.block = tokens[1];
            statement.projSize = tokens[2];
            statement.displaySize = tokens[3];
            statement.x = tokens[4];
            statement.y = tokens[5];
            return statement;
        }

        @Override
        public void write(StringBuilder builder) {
            builder.append("configprojector ").append(block).append(" ").append(projSize).append(" ").append(displaySize).append(" ").append(x).append(" ").append(y);
        }
    }
}
