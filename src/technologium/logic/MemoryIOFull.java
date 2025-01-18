package technologium.logic;

import arc.scene.ui.layout.Table;
import mindustry.logic.*;
import mindustry.world.blocks.logic.*;
import technologium.world.blocks.logic.StringMemoryBlock.StringMemoryBuild;

public class MemoryIOFull {

    public static class MemoryIOInstruction implements LExecutor.LInstruction {
        public int ioMode;
        public int result;
        public int target;
        public int id;

        public MemoryIOInstruction(){}
        public MemoryIOInstruction(int ioMode, int result, int target, int id){
            this.ioMode = ioMode;
            this.result = result;
            this.target = target;
            this.id = id;
        }

        @Override
        public void run(LExecutor exec) {
            boolean isWrite = exec.bool(ioMode);
            Object obj = exec.building(target);
            if(obj instanceof MemoryBlock.MemoryBuild mbuild){
                int index = exec.numi(id);
                if(!isWrite){
                    if(index < 0 || index >= mbuild.memory.length){
                        exec.setnum(result,0);
                        return;
                    }
                    exec.setnum(result, mbuild.memory[index]);
                }
                else {
                    if(index < 0 || index >= mbuild.memory.length){
                        mbuild.memory[index] = 0;
                        return;
                    }
                    mbuild.memory[index] = exec.num(result);
                }
            }
            else if (obj instanceof StringMemoryBuild smbuild) {
                int index = exec.numi(id);
                if(!isWrite){
                    if(index < 0 || index >= smbuild.memory.length){
                        exec.setobj(result,"");
                        return;
                    }
                    exec.setobj(result, smbuild.memory[index]);
                }
                else {
                    if(index < 0 || index >= smbuild.memory.length){
                        smbuild.memory[index] = "";
                        return;
                    }
                    smbuild.memory[index] = exec.obj(result).toString();
                }
            }
            else if (obj instanceof MessageBlock.MessageBuild) {
                if(!isWrite){
                    exec.setobj(result, ((MessageBlock.MessageBuild) obj).config());
                }
                else {
                    ((MessageBlock.MessageBuild) obj).configure(exec.obj(result).toString());
                }
            }
            else {
                if (!isWrite) {
                    exec.setobj(result, null);
                }
                return;
            }
        }
    }

    public static class MemoryIOStatement extends LStatement {
        public String io, value, target, id;

        public MemoryIOStatement(){
            io = "i/o";
            value = "value";
            target = "block1";
            id = "0";
        }

        public LCategory category() {
            return LCategory.io;
        }

        @Override
        public void build(Table table) {
            table.clearChildren();
            table.add("mode ");
            field(table, io, str -> io = str);
            table.add(" -> ");
            field(table, value, str -> value = str);
            table.add(" in/to ");
            field(table, target, str -> target = str);
            table.add(" at ");
            field(table, id, str -> id = str);
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new MemoryIOInstruction(builder.var(io), builder.var(value), builder.var(target), builder.var(id));
        }

        public static MemoryIOStatement read(String[] tokens) {
            MemoryIOStatement statement = new MemoryIOStatement();
            statement.io = tokens[1];
            statement.value = tokens[2];
            statement.target = tokens[3];
            statement.id = tokens[4];
            return statement;
        }

        @Override
        public void write(StringBuilder builder) {
            builder.append("memoryio ").append(io).append(" ").append(value).append(" ").append(target).append(" ").append(id);
        }
    }
}
