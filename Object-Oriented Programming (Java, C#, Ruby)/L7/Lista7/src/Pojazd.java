// Ksawery Plis
// Lista 7
// javac 17.0.8.1

import java.io.Serializable;

public class Pojazd implements Serializable {

    private String marka;
    private double maxPredkosc;
    private int liczbaPasazerow;

    public Pojazd(){
        this.marka = null;
        this.maxPredkosc = 0;
        this.liczbaPasazerow = 0;
    }

    public Pojazd(String marka, double maxPredkosc, int liczbaPasazerow) {
        this.marka = marka;
        this.maxPredkosc = maxPredkosc;
        this.liczbaPasazerow = liczbaPasazerow;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public double getMaxPredkosc() {
        return maxPredkosc;
    }

    public void setMaxPredkosc(double maxPredkosc) {
        this.maxPredkosc = maxPredkosc;
    }

    public int getLiczbaPasazerow() {
        return liczbaPasazerow;
    }

    public void setLiczbaPasazerow(int liczbaPasazerow) {
        this.liczbaPasazerow = liczbaPasazerow;
    }

    public String toString() {
        return "Pojazd marki " + marka + ", osiąga " + maxPredkosc + "km/h, może pomieścić " + liczbaPasazerow + " pasażerów";
    }
}