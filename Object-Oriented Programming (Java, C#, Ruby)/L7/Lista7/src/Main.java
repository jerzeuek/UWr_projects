// Ksawery Plis
// Lista 7
// javac 17.0.8.1

// JAK URUCHOMIĆ:
    // skompilować używając "javac ./Main.java"
    // uruchomić:
        // poleceniem "java Main" aby uruchomić menu główne do edytorów obiektów różnych klas (wtedy używanymi plikami są pojazd.ser, samochod.ser i tramwaj.ser)
        // poleceniem "java Main [nazwa pliku] [nazwa klasy]"" aby uruchomić edytor obiektu konkretnej klasy (używanym plikiem jest plik o nazwie podanej przez użytkownika) 

import java.io.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        if (args.length == 0) {
            EditorNoArgs();
        } else if (args.length != 2) {
            throw new IllegalArgumentException("Zła ilość argumentów!");
        } else {
            editorArgs(args[0], args[1]);
        }
    };

    public static void EditorNoArgs() {
        Pojazd pojazd = new Pojazd();
        Samochod samochod = new Samochod();
        Tramwaj tramwaj = new Tramwaj();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pojazd.ser"))) {
            pojazd = (Pojazd) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pojazd.ser"))) {
                oos.writeObject(pojazd);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("samochod.ser"))) {
            samochod = (Samochod) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("samochod.ser"))) {
                oos.writeObject(samochod);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tramwaj.ser"))) {
            tramwaj = (Tramwaj) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tramwaj.ser"))) {
                oos.writeObject(tramwaj);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        new Editor(pojazd, samochod, tramwaj);
    };

    public static void editorArgs(String fileName, String className) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            switch (className) {
                case "Pojazd":
                    Pojazd pojazd = (Pojazd) ois.readObject();
                    PojazdEditor.callEditor(pojazd, fileName);
                    break;
                case "Samochod":
                    Samochod samochod = (Samochod) ois.readObject();
                    SamochodEditor.callEditor(samochod, fileName);
                    break;
                case "Tramwaj":
                    Tramwaj tramwaj = (Tramwaj) ois.readObject();
                    TramwajEditor.callEditor(tramwaj, fileName);
                    break;
                default:
                    throw new IllegalArgumentException("Nie ma takiej klasy!");
            }
        } catch (IOException | ClassNotFoundException e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                switch (className) {
                    case "Pojazd":
                        Pojazd pojazd = new Pojazd();
                        oos.writeObject(pojazd);
                        PojazdEditor.callEditor(pojazd, fileName);
                        break;
                    case "Samochod":
                        Samochod samochod = new Samochod();
                        oos.writeObject(samochod);
                        SamochodEditor.callEditor(samochod, fileName);
                        break;
                    case "Tramwaj":
                        Tramwaj tramwaj = new Tramwaj();
                        oos.writeObject(tramwaj);
                        TramwajEditor.callEditor(tramwaj, fileName);
                        break;
                    default:
                        throw new IllegalArgumentException("Nie ma takiej klasy!");
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    };
}