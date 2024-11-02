import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        
        //pobieranie danych
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj imię:");
        String name = scan.nextLine();
        System.out.println("Podaj rok urodzenia: ");
        int year = scan.nextInt();
        scan.close();

        // wypisywanie danych
        System.out.println("Cześć " + name + "!");
        System.out.println("Urodziłxś się w roku " + rzymska(year) + "!");
        System.out.println("Twoim patronem jest " + animal(year) + "!");
    }

    // tablica z wybranymi liczbami rzymskimi
    private static String[] rzymskie = {
        "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };
    // tablica z wybranymi liczbami arabskimi
    private static int[] arabskie = {
        1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    // tablica z patronami w kalendarzu chińskim
    public static String[] zwierzeta = {
        "Małpa", "Kurczak", "Pies", "Świnia", "Szczur", "Bawół", "Tygrys", "Królik", "Smok", "Wąż", "Koń", "Owca" 
    };

    // konwersja roku na system rzymski
    public static String rzymska(int n) {

        if (n <= 0 || n >= 4000) {
            throw new IllegalArgumentException("liczba " + n + " spoza zakresu 1-3999");
        }
        String roman = "";
            for(int i=0; i<arabskie.length; i++)
            {
                while(n >= arabskie[i])
                {
                    roman += rzymskie[i];
                    n-=arabskie[i];
                }
            }
        return roman;
    }

    // wybór patrona roku
    public static String animal(int n){
        return switch(n%12){
            case 0 -> zwierzeta[0];
            case 1 -> zwierzeta[1];
            case 2 -> zwierzeta[2];
            case 3 -> zwierzeta[3];
            case 4 -> zwierzeta[4];
            case 5 -> zwierzeta[5];
            case 6 -> zwierzeta[6];
            case 7 -> zwierzeta[7];
            case 8 -> zwierzeta[8];
            case 9 -> zwierzeta[9];
            case 10 -> zwierzeta[10];
            case 11 -> zwierzeta[11];
            default -> "???";
        };
    }
}
