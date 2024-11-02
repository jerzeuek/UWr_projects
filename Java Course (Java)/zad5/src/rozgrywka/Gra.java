package rozgrywka;

import obliczenia.Wymierna;

public class Gra {
    private int zakres;
    private Wymierna liczba;
    private int maksIlośćPrób;
    private int licznikPrób;
    private Wymierna maksPrzedzialu;
    private Wymierna minPrzedzialu;

    public void start(int z) {
        if (z < 5 || z > 20)
            throw new IllegalArgumentException("Wybierz zakres między 5 i 20!");
        zakres = z;
        int licz = (int) (Math.random() * zakres) + 1;
        int mian = (int) (Math.random() * zakres) + 1;
        liczba = new Wymierna(licz, mian);
        maksIlośćPrób = (int) Math.ceil(3 * Math.log(zakres));
        licznikPrób = 0;
        maksPrzedzialu = new Wymierna(1, 1);
        minPrzedzialu = new Wymierna(0, 1);
        assert (liczba.getLicznik() > 0 && liczba.getLicznik() < liczba.getMianownik()): "Wylosowana liczba wypada poza zakres (0; 1)";
    }

    public int getZakres(){
        return zakres;
    }

    public Wymierna getLiczba(){
        return liczba;
    }

    public int getMaksProb() {
        return maksIlośćPrób;
    }

    public int getLicznik() {
        return licznikPrób;
    }

    public Wymierna getMinPrzedzialu(){
        return minPrzedzialu;
    }

    public void setMinPrzedzialu(Wymierna w){
        minPrzedzialu = w;
    }

    public Wymierna getMaksPrzedzialu(){
        return maksPrzedzialu;
    }

    public void setMaksPrzedzialu(Wymierna w){
        maksPrzedzialu = w;
    }


    public void incrementLicznik(){
        licznikPrób++;
    }
}
