package figury;

public class Wektor {
    final double p, q;

    public Wektor(double p, double q) {
        this.p = p;
        this.q = q;
    }

    public double getP() {
        return p;
    }

    public double getQ() {
        return q;
    }

    public static Wektor złożenie(Wektor v1, Wektor v2) {
        return new Wektor(v1.p + v2.p, v1.p + v2.q);
    }

    @Override
    public String toString(){
        return "[" + p + ", " + q + "]";
    }

}
