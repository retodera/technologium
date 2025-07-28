package technologium;

import static mindustry.Vars.ui;

public class LoggingUtils {
    public static String[] colorful(String tag, int rgba) {
        int r = rgba >>> 24;
        int g = (rgba >>> 16) & 0xFF;
        int b = (rgba >>> 8) & 0xFF;
        int a = rgba & 0xff;
        String R = Integer.toHexString(r);
        if(R.length()!=2)R="0"+R;
        String G = Integer.toHexString(g);
        if(G.length()!=2)G="0"+G;
        String B = Integer.toHexString(b);
        if(B.length()!=2)B="0"+B;
        String A = Integer.toHexString(a);
        if(A.length()!=2)A="0"+A;
        // alpha simulation
        r = r * a / 255;
        g = g * a / 255;
        b = b * a / 255;
        return new String[]{
                "\033[1m\033[38;2;" + r + ";" + g + ";" + b + "m[" + tag + "]\033[0m ", // out.print type
                "[#"+R+G+B+A+"]["+tag+"][] "// in game type
        };
    }

    /**
     * @param colorRGBA alpha used always
     * @param tag in square brackets (ex. <a style="color:'#00ffff';text-shadow:'1px 1px #00ffff3f, -1px 1px #00ffff3f, 1px -1px #00ffff3f, -1px -1px #00ffff3f'">[Q]</a>)
     * @param msg text to print (or this will make string mutable? (no))
     */
    //sadly intellij idea doesn't render shadows
    public static void um_custom_log_why(int colorRGBA, String tag, String msg){
        String[] colorful = colorful(tag, colorRGBA);
        System.out.println(colorful[0]+msg);
        ui.consolefrag.addMessage(colorful[1]+msg);
    }
}
