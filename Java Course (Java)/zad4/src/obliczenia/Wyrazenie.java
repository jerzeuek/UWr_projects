package obliczenia;

public abstract class Wyrazenie implements Obliczalny {
    // ... 

    public static double suma(Wyrazenie... wyr) {
        double wynik = 0;
        for (Wyrazenie wyrazenie : wyr) {
            wynik += wyrazenie.oblicz();
        }
        return wynik;
    }

    public static double iloczyn(Wyrazenie... wyr) {
        double wynik = 1;
        for (Wyrazenie wyrazenie : wyr) {
            wynik *= wyrazenie.oblicz();
        }
        return wynik;
    }
}