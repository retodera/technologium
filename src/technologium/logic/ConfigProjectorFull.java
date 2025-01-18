package technologium.logic;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import mindustry.logic.*;
import technologium.world.blocks.logic.Projector;
import mindustry.gen.*;
import mindustry.Vars;

public class ConfigProjectorFull {

    public static class ConfigProjectorInstruction implements LExecutor.LInstruction {
        int block, projSize, displaySize, x, y;

        public ConfigProjectorInstruction() {}
        public ConfigProjectorInstruction(int block, int projSize, int displaySize, int x, int y) {
            this.block = block;
            this.projSize = projSize;
            this.displaySize = displaySize;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run(LExecutor exec) {
            Building build = exec.building(block);
            if(build instanceof Projector.ProjectorBuild proj) {
                proj.projSize = Mathf.clamp(exec.numf(projSize), 1, 16);
                proj.displaySize = Mathf.clamp(exec.numi(displaySize), 1, 256);
                proj.targetX = Mathf.clamp(exec.numf(x), proj.projSize / 2, Vars.world.width() - proj.projSize / 2);
                proj.targetY = Mathf.clamp(exec.numf(y), proj.projSize / 2, Vars.world.height() - proj.projSize / 2);
            }
        }
    }

    public static class ConfigProjectorStatement extends LStatement {
        public String block, projSize, displaySize, x, y;

        public ConfigProjectorStatement() {
            block = "projector1";
            projSize = "size";
            displaySize = "pixels";
            x = "x";
            y = "y";
        }

        public LCategory category() {
            return LCategory.block;
        }

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

        public void write(StringBuilder builder) {
            builder.append("configprojector ").append(block).append(" ").append(projSize).append(" ").append(displaySize).append(" ").append(x).append(" ").append(y);
        }
    }
}
