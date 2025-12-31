package technologium.logic;

import arc.scene.ui.layout.Table;
import mindustry.logic.*;
import mindustry.world.blocks.logic.MemoryBlock.MemoryBuild;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.logic.MessageBlock.MessageBuild;
import technologium.world.blocks.logic.StringMemoryBlock.StringMemoryBuild;

public class MemoryIO {

    public static class MemoryIOInstruction implements LExecutor.LInstruction {
        public LVar ioMode, result, target, id;

        public MemoryIOInstruction() {}
        public MemoryIOInstruction(LVar ioMode, LVar result, LVar target, LVar id) {
            this.ioMode = ioMode;
            this.result = result;
            this.target = target;
            this.id = id;
        }

        @Override
        public void run(LExecutor exec) {
            boolean isWrite = ioMode.bool();
            Object obj = target.building(), res = result.obj();
            int index = id.numi();
            String text;
            if(res instanceof String str) text = str;
            else if(res != null) text = res.toString();
            else text = "";
            if(obj instanceof MemoryBuild b){
                if(!isWrite){
                    if(index < 0 || index >= b.memory.length){
                        result.setnum(0);
                        return;
                    }
                    result.setnum(b.memory[index]);
                }
                else {
                    if(index < 0 || index >= b.memory.length){
                        b.memory[index] = 0;
                        return;
                    }
                    b.memory[index] = result.num();
                }
            }
            else if (obj instanceof StringMemoryBuild b) {
                if(!isWrite){
                    if(index < 0 || index >= b.memory.length){
                        result.setobj("");
                        return;
                    }
                    result.setobj(b.memory[index]);
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
                if(!isWrite) result.setobj(b.config());
                else if(b.message.toString() != text && text.length() <= bl.maxTextLength && bl.accessible()){
                    b.message.ensureCapacity(text.length());
                    b.message.setLength(0);
                    b.message.append(text);    
                }
            }
            else {
                if (!isWrite) result.setobj(null);
                return;
            }
        }
    }

    public static class MemoryIOStatement extends LStatement {
        public String io, value, target, id;

        public MemoryIOStatement(){
            io = "false";
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
            table.add("read ");
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
