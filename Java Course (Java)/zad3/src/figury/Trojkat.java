package figury;

public class Trojkat {
    private Punkt punkt1, punkt2, punkt3;

    public Trojkat(Punkt punkt1, Punkt punkt2, Punkt punkt3) {

        double powierzchnia = punkt1.getX() * (punkt2.getY() - punkt3.getY()) +
                              punkt2.getX() * (punkt3.getY() - punkt1.getY()) +
                              punkt3.getX() * (punkt1.getY() - punkt2.getY());

        if (Math.abs(powierzchnia) < 1e-6) {
            throw new IllegalArgumentException("Nie da się stworzyć takiego trójkąta!");
        }

        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
        this.punkt3 = punkt3;
    }

    public Punkt getPunkt1() {
        return punkt1;
    }

    public Punkt getPunkt2() {
        return punkt2;
    }

    public Punkt getPunkt3() {
        return punkt3;
    }

    public void przesuń(Wektor v) {
        punkt1.przesuń(v);
        punkt2.przesuń(v);
        punkt3.przesuń(v);
    }

    public void obróć(Punkt p, double kąt) {
        punkt1.obróć(p, kąt);
        punkt2.obróć(p, kąt);
        punkt3.obróć(p, kąt);
    }

    public void odbij(Prosta p) {
        punkt1.odbij(p);
        punkt2.odbij(p);
        punkt3.odbij(p);
    }

    @Override
    public String toString(){
        return punkt1 + ", " + punkt2 + ", " + punkt3;
    }
}
