import figury.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        //testowanie punktów
        System.out.println("Punkty: \n");
        
        //przesunięcie
        Punkt A = new Punkt(1, 2);
        Punkt A1 = new Punkt(1, 2);
        Wektor d1 = new Wektor(2, 3);
        A1.przesuń(d1);

        System.out.println("Punkt " + A + " przesunięty o wektor " + d1 +  " to: " +  A1);
        System.out.println(A);

        //obrót
        A1 = new Punkt(1, 2);
        Punkt obrót = new Punkt(7, 5);
        double kąt = 90;
        A1.obróć(obrót, kąt);
        System.out.println("Punkt " +  A +  " obrócony o " + kąt +  " stopni względem punktu " + obrót + " to: " + A1);

        //odbicie
        A1 = new Punkt(1, 2);
        Prosta a = new Prosta(4, 3, 7);
        A1.odbij(a);
        System.out.println("Punkt " + A + " odbity od prostej " + a  + " to: " + A1);
        
        //testowanie odcinków
        System.out.println("\nOdcinki: \n");
        
        //tworzenie odcinków z takimi samymi punktami
        A1 = new Punkt(1, 2);
        Punkt B1 = new Punkt(1, 2);

        try {
            new Odcinek(A1, B1);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        //przesunięcie
        Punkt B = new Punkt(3, 4);
        B1 = new Punkt(3, 4);

        Odcinek AB = new Odcinek(A, B);
        Odcinek A1B1 = new Odcinek(A1, B1);

        A1B1.przesuń(d1);
        System.out.println("Odcinek " + AB + " przesunięty o wektor " + d1 + " to: " + A1B1);

        //obrót
        A1 = new Punkt(1, 2);
        B1 = new Punkt(3, 4);
        A1B1 = new Odcinek(A1, B1);

        A1B1.obróć(obrót, kąt);

        System.out.println("Odcinek " + AB + " obrócony o " + kąt + " stopni względem punktu " + obrót + " to: " + A1B1);

        //odbicie
        A1 = new Punkt(1, 2);
        B1 = new Punkt(3, 4);
        A1B1 = new Odcinek(A1, B1);

        A1B1.odbij(a);
        System.out.println("Odcinek " + AB + " odbity od prostej " + a  + " to: " + A1B1);

        //testowanie trójkątów
        System.out.println("\nTrójkąty: \n");

        //tworzenie trójkątów z punktów współliniowych
        Punkt C = new Punkt(5, 6);
        try {
            new Trojkat(A, B, C);
        }

        catch (Exception e) {
            System.out.println(e);
        }

        //przesunięcie
        C = new Punkt(-7, 2);
        Trojkat ABC = new Trojkat(A, B, C);
        A1 = new Punkt(1, 2);
        B1 = new Punkt(3, 4);
        Punkt C1 = new Punkt(7, -2);
        Trojkat A1B1C1 = new Trojkat(A1, B1, C1);

        A1B1C1.przesuń(d1);

        System.out.println("Trójkąt " + ABC + " przesunięty o wektor " + d1 + " to: " + A1B1C1);

        //obrót

        A1 = new Punkt(1, 2);
        B1 = new Punkt(3, 4);
        C1 = new Punkt(7, -2);
        A1B1C1 = new Trojkat(A1, B1, C1);

        A1B1C1.obróć(obrót, kąt);

        System.out.println("Trójkąt " + ABC + " obrócony o " + kąt + " stopni względem punktu " + obrót + " to: " + A1B1C1);

        //odbicie

        A1 = new Punkt(1, 2);
        B1 = new Punkt(3, 4);
        C1 = new Punkt(7, -2);
        A1B1C1 = new Trojkat(A1, B1, C1);
        A1B1C1.odbij(a);

        System.out.println("Trójkąt " + ABC + " odbity od prostej " + a  + " to: " + A1B1C1);


        // testowanie wektorów
        System.out.println("\nWektory: \n");

        Wektor d2 = new Wektor(6, 7);
        Wektor złożony = Wektor.złożenie(d1, d2);
        System.out.println("Złożenie wektorów " + d1 + " oraz " + d2 + " to wektor: " + złożony);


        // testowanie prostych
        System.out.println("\n Proste: \n");

        //prostopadłe i równoległe
        a = new Prosta(0.5, 1, 7);
        Prosta b = new Prosta(4, 8, 6);
        Prosta c = new Prosta(-1, 0.5, 8);

        System.out.println("Czy proste " + a + " i " + b + " są równoległe? " + Prosta.czyRównoległe(a, b));
        System.out.println("Czy proste " + b + " i " + c + " są równoległe? " + Prosta.czyRównoległe(b, c));
        System.out.println("Czy proste " + a + " i " + b + " są prostopadłe? " + Prosta.czyProstopadłe(a, b));
        System.out.println("Czy proste " + b + " i " + c + " są prostopadłe? " + Prosta.czyProstopadłe(b, c));

        //przesunięcie
        Prosta przesunieta = Prosta.przesun(a, d1);

        System.out.println("Prosta " + a + " przesunięta o wektor " + d1  + " to: " + przesunieta);

        //punkty przecięcia
        try {
            Prosta.punktPrzecięcia(a, b);
        }

        catch(Exception e){
            System.out.println(e);
        }

        Punkt przeciecie = Prosta.punktPrzecięcia(b, c);
        System.out.println("Proste " + b + " i " + c + " przecinają się w punkcie: " + przeciecie);
    }
}
