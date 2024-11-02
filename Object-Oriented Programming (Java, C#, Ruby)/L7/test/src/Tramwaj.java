public class Tramwaj extends Pojazd {

    private double dlugosc;

    public Tramwaj(String marka, double maxPredkosc, int liczbaPasazerow, double dlugosc) {
        super(marka, maxPredkosc, liczbaPasazerow);

        this.dlugosc = dlugosc;
    }

    public double getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(double dlugosc) {
        this.dlugosc = dlugosc;
    }

    public String toString() {
        return super.toString() + ", ma długość " + dlugosc + "m";
    }
}