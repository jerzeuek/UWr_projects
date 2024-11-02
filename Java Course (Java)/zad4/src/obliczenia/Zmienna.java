package obliczenia;

public class Zmienna extends Wyrazenie {
    private String identyfikator;
    private static final Zbior zmienne = new ZbiorTablicowy();

    public Zmienna(String identyfikator) {
        this.identyfikator = identyfikator;
        zmienne.wstaw(new Para(identyfikator, 0)); // Domyślnie ustawiamy wartość zmiennej na 0
    }

    public static double odczytaj(String identyfikator) {
        Para para = zmienne.szukaj(identyfikator);
        if (para != null) {
            return para.getWartość();
        } else {
            throw new IllegalArgumentException("Zmienna o identyfikatorze " + identyfikator + " nie istnieje.");
        }
    }

    public static void przypisz(String identyfikator, double wartość) {
        zmienne.wstaw(new Para(identyfikator, wartość));
    }

    @Override
    public double oblicz() {
        return odczytaj(identyfikator);
    }

    @Override
    public String toString() {
        return identyfikator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Zmienna zmienna = (Zmienna) obj;
        return identyfikator.equals(zmienna.identyfikator);
    }
}