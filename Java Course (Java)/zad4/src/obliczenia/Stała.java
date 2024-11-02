package obliczenia;

public class Stała extends Wyrazenie {
    public static final Stała PI = new Stała("π", 3.1415);
    public static final Stała E = new Stała("e", 2.718);

    private String symbol;
    private double wartość;

    private Stała(String symbol, double wartość) {
        this.symbol = symbol;
        this.wartość = wartość;
    }

    @Override
    public double oblicz() {
        return wartość;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stała stała = (Stała) obj;
        return Double.compare(stała.wartość, wartość) == 0 && symbol.equals(stała.symbol);
    }
}
