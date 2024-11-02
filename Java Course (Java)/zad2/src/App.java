import java.util.ArrayList;
import java.lang.Math;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Program musi być wywołany z przynajmniej jednym argumentem.");
            return;
        }

        for (String arg : args) {
            System.out.print(arg + " = ");
            long[] factors = LiczbyPierwsze.naCzynnikiPierwsze(Long.parseLong(arg));
            for (int i = 0; i < factors.length; i++) {
                System.out.print(factors[i]);
                if (i < factors.length - 1) {
                    System.out.print(" * ");
                }
            }
            System.out.println();
        }
    }

    public final class LiczbyPierwsze {
        private final static int POTEGA2 = 21;
        private final static int[] SITO = new int[1 << POTEGA2];

        static {
            for (int i = 2; i < SITO.length; i++) {
                SITO[i] = i;
            }

            for (int i = 2; i * i < SITO.length; i++) {
                if (SITO[i] == i) {
                    for (int j = i * i; j < SITO.length; j += i) {
                        if (SITO[j] == j) {
                            SITO[j] = i;
                        }
                    }
                }
            }
        }

        public static boolean czyPierwsza(long x) {
            if (x < 2) {
                return false;
            }

            if (x < SITO.length) {
                return SITO[(int) x] == x;
            }

            else {
                if (x % 2 == 0) {
                    return false;
                }

                for (long i = 3; i <= Math.sqrt(x); i += 2) {
                    if (x % i == 0) {
                        return false;
                    }
                }
                return true;
            }

        }

        public static long[] naCzynnikiPierwsze(long x) {
            ArrayList<Long> v = new ArrayList<Long>();
            if (x >= -1 && x <= 1) {
                v.add(x);
                x = 1;
            }

            if (x < 0) {
                v.add((long) -1);
                if (x == Long.MIN_VALUE) {
                    v.add((long) 2);
                    x = (Long.MAX_VALUE/2)+1;
                }

                else {
                    x = Math.abs(x);
                }
            }

            if (x < SITO.length) {
                while (x > 1) {
                    v.add((long) SITO[(int) x]);
                    x /= SITO[(int) x];
                }
            }

            else {
                while (x % 2 == 0) {
                    v.add((long) 2);
                    x /= 2;
                }

                for (long i = 3; i <= Math.sqrt(x); i += 2) {
                    if ((i < SITO.length && SITO[(int) i] == i) || i >= SITO.length) {

                        while (x % i == 0) {
                            v.add(i);
                            x /= i;
                        }

                        if (x == 1) {
                            break;
                        }
                    }
                }
                
                if (x > 2) {
                    v.add(x);
                }
            }
            long[] result = v.stream().mapToLong(l -> l).toArray();
            return result;
        }
    }
}