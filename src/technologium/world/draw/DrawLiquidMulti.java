package technologium.world.draw;

import mindustry.type.Liquid;
import mindustry.world.draw.DrawLiquidTile;
import arc.struct.Seq;
import mindustry.gen.Building;
import static mindustry.world.blocks.liquid.LiquidBlock.drawTiledFrames;
import arc.math.Mathf;

public class DrawLiquidMulti extends DrawLiquidTile {
    public Seq<Liquid> exclude = new Seq<>();

    public DrawLiquidMulti() {}

    public DrawLiquidMulti(float alpha) {
        this.alpha = alpha;
    }

    public DrawLiquidMulti(Liquid... exclude) {
        this.exclude = Seq.with(exclude);
    }

    public DrawLiquidMulti (Seq<Liquid> exclude) {
        this.exclude = exclude;
    }

    public DrawLiquidMulti(Float padding, float alpha) {
        this.padding = padding;
        this.alpha = alpha;
    }

    public DrawLiquidMulti(Float padding, float alpha, Liquid... exclude) {
        this.exclude = Seq.with(exclude);
        this.padding = padding;
        this.alpha = alpha;
    }

    public DrawLiquidMulti (Float padding, float alpha, Seq<Liquid> exclude) {
        this.exclude = exclude;
        this.padding = padding;
        this.alpha = alpha;
    }

    @Override
    public void draw(Building build) {
        if(build.liquids == null) return;
        float sum = build.liquids.sum((l, a) -> exclude.contains(l) ? 0 : a);
        if(Mathf.zero(sum)) return;
        build.liquids.each((l, a) -> {
            if(exclude.contains(l)) return;
            drawTiledFrames(build.block.size, build.x, build.y, padLeft, padRight, padTop, padBottom, l, a / (build.block.liquidCapacity - sum + a) * alpha);
        });
    }
}
