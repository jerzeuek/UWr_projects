// Ksawery Plis
// Lista 7
// javac 17.0.8.1

public class Tramwaj extends Pojazd {

    private double dlugosc;

    public Tramwaj(){
        super();
        this.dlugosc = 0;
    }

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