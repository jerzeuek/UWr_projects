package obliczenia;
import java.text.DecimalFormat;
import wyjatki.*;

public class Wymierna implements Comparable<Wymierna>{
    private int licznik, mianownik = 1;

    public Wymierna(){
        this(0, 1);
    }

    public Wymierna(int n){
        this(n, 1);
    }

    public Wymierna(int k, int m){
        if(m == 0){
            throw new ZeroDenominatorException("Mianownik nie może być zerem!");
        }

        if(m < 0){
            k*=-1;
            m*=-1;
        }
        
        int gcd = NWD(Math.abs(k), Math.abs(m));
        
        this.licznik = k/gcd;
        this.mianownik = m/gcd;
    }

    private int NWD(int a, int b){
        if (a == 0)
            return b;
 
        return NWD(b % a, a);

    }

    public int getLicznik(){
        return licznik;
    }

    public int getMianownik(){
        return mianownik;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format((double) licznik/ (double) mianownik);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof Wymierna)) {
            return false;
        }

        Wymierna w = (Wymierna) o;

        return(w.licznik == licznik && w.mianownik == mianownik);
    }

    @Override
    public int compareTo(Wymierna w){
        int product1 = this.licznik * w.mianownik;
        int product2 = this.mianownik * w.licznik;

        return Integer.compare(product1, product2);
    }

    public static Wymierna add(Wymierna x, Wymierna y){
        int k = (x.licznik * y.mianownik) + (x.mianownik * y.licznik);
        int m = x.mianownik * y.mianownik;
        
        return new Wymierna(k, m);
    }

    public static Wymierna sub(Wymierna x, Wymierna y){
        int k = (x.licznik * y.mianownik) - (x.mianownik * y.licznik);
        int m = x.mianownik * y.mianownik;

        return new Wymierna(k, m);
    }

    public static Wymierna mult(Wymierna x, Wymierna y){
        int k = x.licznik * y.licznik;
        int m = x.mianownik * y.mianownik;

        return new Wymierna(k, m);
    }

    public static Wymierna div(Wymierna x, Wymierna y){
        if(y.licznik == 0){
            throw new ArithmeticException("Nie można dzielić przez zero!");
        }

        int k = x.licznik * y.mianownik;
        int m = x.mianownik * y.licznik;

        return new Wymierna(k, m);
    }
}
