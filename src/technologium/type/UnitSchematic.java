package technologium.type;

import arc.struct.Seq;
import arc.util.ArcRuntimeException;
import mindustry.ctype.*;
import mindustry.type.*;
import technologium.ctype.CustomContent;

public class UnitSchematic extends UnlockableContent implements CustomContent {
    public static final Seq<UnitSchematic> all = new Seq<>();
    public Seq<Step> steps = new Seq<>();
    public UnitType result;

    public UnitSchematic(String name) {
        super(name);
        if(find(name) != null) throw new ArcRuntimeException("there cannot be two UnitSchematics with the same name.");
        all.add(this);
    }

    public static UnitSchematic find(String name) {
        if(name == "") return null;
        return all.find(s -> s.name == name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        try {
            return steps.equals((Seq<Step>)o);
        } catch(Exception e) { return false; }
    }

    public class Step {
        public TItemSeq items = new TItemSeq();
        public TLiquidSeq liquids = new TLiquidSeq();
        public TPayloadSeq payloads = new TPayloadSeq();
        public String region;
        public float x, y, rotation;
        public boolean mirror = false;
        public float time;

        public Step(String region) {
            this.region = region;
        }

        @Override
        public boolean equals(Object o) {
            try {
                Step other = (Step)o;
                return items.equals(other.items) && liquids.equals(other.liquids) && payloads.equals(other.payloads) && region.equals(other.region);
            } catch (Exception e) { return false; }
        }
    }

    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }

    @Override
    public String getCustomContentType() {
        return "schematic";
    }
}
