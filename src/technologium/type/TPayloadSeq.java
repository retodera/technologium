package technologium.type;

import arc.func.Boolf;
import arc.struct.ObjectIntMap;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.type.*;
import arc.util.io.*;

public class TPayloadSeq {
    public ObjectIntMap<UnlockableContent> values = new ObjectIntMap<>();

    public static TPayloadSeq with(Object... stacks) {
        TPayloadSeq seq = new TPayloadSeq();
        for(int i = 0; i < stacks.length; i+=2) seq.add((UnlockableContent)stacks[i], (int)stacks[i+1]);
        return seq;
    }

    public static TPayloadSeq with(TPayloadSeq seq) {
        return new TPayloadSeq().addAll(seq);
    }

    public TPayloadSeq addAll(Object... stacks) {
        for(int i = 0; i < stacks.length; i+=2) add((UnlockableContent)stacks[i], (int)stacks[i+1]);
        return this;
    }

    public TPayloadSeq addAll(TPayloadSeq other) {
        for(UnlockableContent content : other.values.keys()) add(content, other.values.get(content));
        return this;
    }

    public TPayloadSeq addAll(PayloadStack[] stacks) {
        for(PayloadStack stack : stacks) add(stack);
        return this;
    }

    public TPayloadSeq add(PayloadStack stack) {
        return add(stack.item, stack.amount);
    }

    public TPayloadSeq add(UnlockableContent content) {
        return add(content, 1);
    }

    public TPayloadSeq add(UnlockableContent content, int amount) {
        values.increment(content, amount);
        return this;
    }

    public int total() {
        int total = 0;
        for(var key : values.keys()) total += values.get(key);
        return total;
    }

    public int length() {
        int length = 0;
        for(var key : values.keys()) if(values.get(key) > 0) length++;
        return length;
    }

    public TPayloadSeq cap(int max) {
        for(var key : values.keys()) values.put(key, Math.min(values.get(key), max));
        return this;
    }

    public int get(UnlockableContent content) {
        return values.get(content, 0);
    }

    public boolean has(UnlockableContent content) {
        return values.containsKey(content) && values.get(content) > 0;
    }

    public boolean isEmpty() {
        return values.size == 0;
    }

    public void clear() {
        values.clear();
    }

    public void remove(UnlockableContent content) {
        values.remove(content);
    }

    public void remove(UnlockableContent content, int amount) {
        values.increment(content, -amount);
        if(values.get(content) <= 0) remove(content);
    }
    
    public void remove(Boolf<UnlockableContent> filter) {
        values.keys().forEach(v -> {
            if(filter.get(v)) values.remove(v);
        });
    }

    public void write(Writes write) {
        write.i(length());
        for(var entry : values.entries()) {
            if(entry.value == 0) continue;
            write.b(entry.key.getContentType().ordinal());
            write.s(entry.key.id);
            write.i(entry.value);
        }
    }

    public void read(Reads read) {
        int length = read.i();
        for(int i = 0; i < length; i++) 
            add(Vars.content.getByID(ContentType.all[read.ub()], read.s()), read.i());
    }

    @Override
    public String toString() {
        return "TPayloadSeq{values=" + values + '}';
    }
}
