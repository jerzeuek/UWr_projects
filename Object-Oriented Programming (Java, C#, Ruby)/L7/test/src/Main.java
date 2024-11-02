import java.io.*;

public class Main {
    public static void main(String[] args) {
        serializationTest();
        editorTest();
    }

    public static void editorTest() {
        Pojazd pojazd = new Pojazd("Kross", 25, 2);
        Samochod samochod = new Samochod("Tesla", 233.35, 5, "elektryczny");
        Tramwaj tramwaj = new Tramwaj("Pesa", 75, 200, 42.8);
        new Editor(pojazd, samochod, tramwaj);
    }

    public static void serializationTest() {
        System.out.println(
                "Test serializacji: wypiszemy trzy obiekty, najpierw przed serializacją, potem po serializacji i deserializacji");

        final Pojazd[] pojazd = { new Pojazd("Kross", 25, 2) };

        System.out.println(pojazd[0]);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pojazd.ser"))) {
            oos.writeObject(pojazd[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pojazd.ser"))) {
            Pojazd ksiazka2 = (Pojazd) ois.readObject();
            System.out.println(ksiazka2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Samochod samochod = new Samochod("Tesla", 233.35, 5, "elektryczny");
        System.out.println(samochod);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("samochod.ser"))) {
            oos.writeObject(samochod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("samochod.ser"))) {
            Samochod samochod2 = (Samochod) ois.readObject();
            System.out.println(samochod2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Tramwaj tramwaj = new Tramwaj("Pesa", 75, 200, 42.8);
        System.out.println(tramwaj);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tramwaj.ser"))) {
            oos.writeObject(tramwaj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tramwaj.ser"))) {
            Tramwaj tramwaj2 = (Tramwaj) ois.readObject();
            System.out.println(tramwaj2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}