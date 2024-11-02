package obliczenia;

public class Liczba extends Wyrazenie {
    private double wartość;

    public Liczba(double wartość) {
        this.wartość = wartość;
    }

    @Override
    public double oblicz() {
        return wartość;
    }

    @Override
    public String toString() {
        return Double.toString(wartość);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Liczba liczba = (Liczba) obj;
        return Double.compare(liczba.wartość, wartość) == 0;
    }
}
