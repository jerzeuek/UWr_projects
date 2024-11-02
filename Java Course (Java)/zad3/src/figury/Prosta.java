package figury;

public class Prosta {
    final double a, b, c;

    public Prosta(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA(){
        return a;
    }

    public double getB(){
        return b;
    }

    public double getC(){
        return c;
    }

    public static Prosta przesun(Prosta p, Wektor v){

        return new Prosta(p.a, p.b, (-p.a * v.p) + p.c - (p.b * v.q));
    }

    public static boolean czyRównoległe(Prosta p1, Prosta p2) {
        return ((p1.a * p2.b) - (p1.b * p2.a) < 1e-6);
    }

    public static boolean czyProstopadłe(Prosta p1, Prosta p2) {
        return ((p1.a * p2.a) + (p1.b * p2.b) < 1e-6);
    }

    public static Punkt punktPrzecięcia(Prosta p1, Prosta p2) {
        if (czyRównoległe(p1, p2)) {
            throw new IllegalArgumentException("Proste są równoległe, punkt przecięcia nie istnieje!");
        }

        double w = p1.a * p2.b - p2.a * p1.b;
        double wx = -p1.c * p2.b + p2.c * p1.b;
        double wy = -p1.a * p2.c + p2.a * p1.c;
        return new Punkt(wx / w, wy / w);
    }

    @Override
    public String toString(){
        return a + "x + " + b + "y + " + c + " = 0";
    }
}
