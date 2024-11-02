package struktury;

public class Para implements Cloneable, Comparable<Para> {
    public final String klucz;
    private double wartość;

    public Para(String klucz, double wartość) {
        // Sprawdzenie warunków klucza
        if (klucz == null || klucz.isEmpty() || !klucz.matches("[a-z]+")) {
            throw new IllegalArgumentException("Nieprawidłowy klucz");
        }

        this.klucz = klucz;
        this.wartość = wartość;
    }

    public double getWartość() {
        return wartość;
    }

    public void setWartość(double wartość) {
        this.wartość = wartość;
    }

    @Override
    public String toString() {
        return "Para{" +
                "klucz='" + klucz + '\'' +
                ", wartość=" + wartość +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Para para = (Para) o;
        return klucz.equals(para.klucz);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Para o) {
        return this.klucz.compareTo(o.klucz);
    }
}