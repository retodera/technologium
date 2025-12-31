package technologium.type;

import arc.func.Boolf;
import mindustry.Vars;
import mindustry.type.*;
import arc.util.io.*;

public class TItemSeq {
    public int[] values = new int[Vars.content.items().size];

    public static TItemSeq with(Object... stacks) {
        TItemSeq seq = new TItemSeq();
        for(int i = 0; i < stacks.length; i+=2) seq.add((Item)stacks[i], (int)stacks[i+1]);
        return seq;
    }

    public static TItemSeq with(TItemSeq seq) {
        return new TItemSeq().addAll(seq);
    }

    public TItemSeq addAll(Object... stacks) {
        for(int i = 0; i < stacks.length; i+=2) add((Item)stacks[i], (int)stacks[i+1]);
        return this;
    }

    public TItemSeq addAll(TItemSeq other) {
        for(int i = 0; i < values.length; i++) values[i] += other.values[i];
        return this;
    }

    public TItemSeq addAll(ItemStack[] stacks) {
        for(ItemStack stack : stacks) add(stack);
        return this;
    }

    public TItemSeq add(ItemStack stack) {
        return add(stack.item, stack.amount);
    }

    public TItemSeq add(Item item) {
        return add(item, 1);
    }

    public TItemSeq add(Item item, int amount) {
        values[item.id] += amount;
        return this;
    }

    public int total() {
        int total = 0;
        for(int value : values) total += value;
        return total;
    }

    public TItemSeq cap(int max) {
        for(int i = 0; i < values.length; i++) values[i] = Math.min(values[i], max);
        return this;
    }

    public int get(Item item) {
        return values[item.id];
    }
    
    public boolean has(Item item) {
        return values[item.id] > 0;
    }

    public boolean isEmpty() {
        for(int value : values) if(value > 0) return false;
        return true;
    }

    public void clear() {
        for(int i = 0; i < values.length; i++) values[i] = 0;
    }

    public void remove(Item item) {
        values[item.id] = 0;
    }

    public void remove(Item item, int amount) {
        values[item.id] -= amount;
        if(values[item.id] < 0) values[item.id] = 0;
    }

    public void remove(Boolf<Item> filter) {
        for(int i = 0; i < values.length; i++) if(filter.get(Vars.content.items().get(i))) values[i] = 0;
    }

    public void write(Writes write) {
        for(int value : values) write.i(value);
    }

    public void read(Reads read) {
        for(int i = 0; i < values.length; i++) values[i] = read.i();
    }

    @Override
    public String toString() {
        String str = "TItemSeq{values=[";
        for(int i = 0; i < values.length; i++) str += Vars.content.item(i) + ":" + values[i] + ",";
        str += "]}";
        return str;
    }
}
