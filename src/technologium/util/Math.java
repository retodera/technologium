package technologium.util;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Math {
    /**
     * greatest common divisor<div>
     * for computation used <a href="https://en.wikipedia.org/wiki/Binary_GCD_algorithm">binary GCD</a>
     */
    public static long gcd(long u, long v){
        if(u == 0)return v;
        if(v == 0)return u;
        int k = min(
                Long.numberOfTrailingZeros(u),
                Long.numberOfTrailingZeros(v)
        );
        long tmp;
        while (true) {
            if(u > v){
                tmp= u;
                u = v;
                v =tmp;
            }
            v -= u;
            if(v == 0)return u << k;
            v >>= Long.numberOfTrailingZeros(v);
        }
    }
    /**
     * greatest common divisor<div>
     * for computation used <a href="https://en.wikipedia.org/wiki/Binary_GCD_algorithm">binary GCD</a>
     */
    public static int gcd(int u, int v){
        if(u == 0)return v;
        if(v == 0)return u;
        int k = min(
                Integer.numberOfTrailingZeros(u),
                Integer.numberOfTrailingZeros(v)
        );
        int tmp;
        while (true) {
            if(u > v){
                tmp= u;
                u = v;
                v =tmp;
            }
            v -= u;
            if(v == 0)return u << k;
            v >>= Long.numberOfTrailingZeros(v);
        }
    }

    /**
     * least common multiple, equals to u*v/gcd(u,v)
     */
    public static long lcm(long u, long v) {
        return abs(u) * abs(v) / gcd(u, v);
    }

    /**
     * least common multiple, equals to u*v/gcd(u,v)
     */
    public static int lcm(int u, int v) {
        return abs(u) * abs(v) / gcd(u, v);
    }
}
