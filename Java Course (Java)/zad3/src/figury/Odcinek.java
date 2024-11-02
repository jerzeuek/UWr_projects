package figury;

public class Odcinek {
    private Punkt punkt1, punkt2;

    public Odcinek(Punkt punkt1, Punkt punkt2) {
        if (Math.abs(punkt1.getX() - punkt2.getX()) < 1e-6 && 
            Math.abs(punkt1.getY() - punkt2.getY()) < 1e-6) {
            throw new IllegalArgumentException("Punkty są takie same!");
        }

        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
    }

    public Punkt getPunkt1() {
        return punkt1;
    }

    public Punkt getPunkt2() {
        return punkt2;
    }

    public void przesuń(Wektor v) {
        punkt1.przesuń(v);
        punkt2.przesuń(v);
    }

    public void obróć(Punkt p, double kąt) {
        punkt1.obróć(p, kąt);
        punkt2.obróć(p, kąt);
    }

    public void odbij(Prosta p) {
        punkt1.odbij(p);
        punkt2.odbij(p);
    }

    @Override
    public String toString(){
        return punkt1 + ", " + punkt2;
    }
}
