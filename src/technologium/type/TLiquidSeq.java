package technologium.type;

import arc.func.Boolf;
import mindustry.Vars;
import mindustry.type.*;
import arc.util.io.*;

public class TLiquidSeq {
    public float[] values = new float[Vars.content.items().size];

    public static TLiquidSeq with(Object... stacks) {
        TLiquidSeq seq = new TLiquidSeq();
        for(int i = 0; i < stacks.length; i+=2) seq.add((Liquid)stacks[i], (float)stacks[i+1]);
        return seq;
    }

    public static TLiquidSeq with(TLiquidSeq seq) {
        return new TLiquidSeq().addAll(seq);
    }

    public TLiquidSeq addAll(Object... stacks) {
        for(int i = 0; i < stacks.length; i+=2) add((Liquid)stacks[i], (float)stacks[i+1]);
        return this;
    }

    public TLiquidSeq addAll(TLiquidSeq other) {
        for(int i = 0; i < values.length; i++) values[i] += other.values[i];
        return this;
    }

    public TLiquidSeq addAll(LiquidStack[] stacks) {
        for(LiquidStack stack : stacks) add(stack);
        return this;
    }

    public TLiquidSeq add(LiquidStack stack) {
        return add(stack.liquid, stack.amount);
    }

    public TLiquidSeq add(Liquid liquid, float amount) {
        values[liquid.id] += amount;
        return this;
    }

    public float total() {
        float total = 0;
        for(float value : values) total += value;
        return total;
    }

    public TLiquidSeq cap(float max) {
        for(int i = 0; i < values.length; i++) values[i] = Math.min(values[i], max);
        return this;
    }

    public float get(Liquid liquid) {
        return values[liquid.id];
    }

    public boolean has(Liquid liquid) {
        return values[liquid.id] > 0.001f;
    }

    public boolean isEmpty() {
        for(float value : values) if(value > 0.001f) return false;
        return true;
    }

    public void clear() {
        for(int i = 0; i < values.length; i++) values[i] = 0;
    }

    public void remove(Liquid liquid) {
        values[liquid.id] = 0;
    }

    public void remove(Liquid liquid, float amount) {
        values[liquid.id] -= amount;
        if(values[liquid.id] < 0) values[liquid.id] = 0;
    }

    public void remove(Boolf<Liquid> filter) {
        for(int i = 0; i < values.length; i++) if(filter.get(Vars.content.liquids().get(i))) values[i] = 0;
    }

    public void write(Writes write) {
        for(float value : values) write.f(value);
    }

    public void read(Reads read) {
        for(int i = 0; i < values.length; i++) values[i] = read.f();
    }

    @Override
    public String toString() {
        String str = "TLiquidSeq{values=[";
        for(int i = 0; i < values.length; i++) str += Vars.content.liquid(i) + ":" + values[i] + ",";
        str += "]}";
        return str;
    }
}
