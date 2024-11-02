// Ksawery Plis
// Lista 7
// javac 17.0.8.1

public class Samochod extends Pojazd {

    private String typSilnika;

    public Samochod(){
        super();
        this.typSilnika = null;
    }

    public Samochod(String marka, double maxPredkosc, int liczbaPasazerow, String typSilnika) {
        super(marka, maxPredkosc, liczbaPasazerow);
        this.typSilnika = typSilnika;
    }

    public String getTypSilnika() {
        return typSilnika;
    }

    public void setTypSilnika(String typSilnika) {
        this.typSilnika = typSilnika;
    }

    public String toString() {
        return super.toString() + ", posiada silnik " + typSilnika;
    }
}