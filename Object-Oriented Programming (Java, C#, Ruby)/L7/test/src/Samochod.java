public class Samochod extends Pojazd {

    private String typSilnika;

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