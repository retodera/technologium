package technologium.logic;

import arc.scene.ui.layout.Table;
import mindustry.logic.*;
import mindustry.world.blocks.logic.MemoryBlock.MemoryBuild;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.logic.MessageBlock.MessageBuild;
import technologium.world.blocks.logic.StringMemoryBlock.StringMemoryBuild;

public class MemoryIO {

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
            String text;
            if(exec.obj(result) instanceof String) text = (String)exec.obj(result);
            else if(exec.obj(result) != null) text = exec.obj(result).toString();
            else text = "";
            if(obj instanceof MemoryBuild b){
                int index = exec.numi(id);
                if(!isWrite){
                    if(index < 0 || index >= b.memory.length){
                        exec.setnum(result,0);
                        return;
                    }
                    exec.setnum(result, b.memory[index]);
                }
                else {
                    if(index < 0 || index >= b.memory.length){
                        b.memory[index] = 0;
                        return;
                    }
                    b.memory[index] = exec.num(result);
                }
            }
            else if (obj instanceof StringMemoryBuild b) {
                int index = exec.numi(id);
                if(!isWrite){
                    if(index < 0 || index >= b.memory.length){
                        exec.setobj(result,"");
                        return;
                    }
                    exec.setobj(result, b.memory[index]);
                }
                else {
                    if(index < 0 || index >= b.memory.length){
                        b.memory[index] = "";
                        return;
                    }
                    b.memory[index] = text;
                }
            }
            else if (obj instanceof MessageBuild b) {
                MessageBlock bl = (MessageBlock)b.block;
                if(!isWrite) exec.setobj(result, b.config());
                else if(b.message.toString() != text && text.length() <= bl.maxTextLength && bl.accessible()){
                    b.message.ensureCapacity(text.length());
                    b.message.setLength(0);
                    b.message.append(text);    
                }
            }
            else {
                if (!isWrite) exec.setobj(result, null);
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
