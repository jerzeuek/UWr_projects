// Ksawery Plis
// Lista 3 Zadanie 2
// Microsoft Visual Studio Community 2022 Wersja 17.8.3

using Zadanie2;

class Program
{
    static void Main(string[] args)
    {
        MyDictionary<int, string> testDictionary = new MyDictionary<int, string>();
        Console.WriteLine("Dodajmy do słownika 5 pierwszych liter alfabetu z kluczami 1-5:");
        testDictionary.Add(1, "a");
        testDictionary.Add(2, "b");
        testDictionary.Add(3, "c");
        testDictionary.Add(4, "d");
        testDictionary.Add(5, "e");
        Console.WriteLine("Zobaczmy czy mogę znaleźć dodaje wartości i wartość z kluczem którego nie dodałem:");
        Console.WriteLine(testDictionary.Search(1));
        Console.WriteLine(testDictionary.Search(2));
        Console.WriteLine(testDictionary.Search(3));
        Console.WriteLine(testDictionary.Search(4));
        Console.WriteLine(testDictionary.Search(5));
        Console.WriteLine(testDictionary.Search(42));

        Console.WriteLine("Usunę wartości z kluczami 4 i 5, zobaczmy co się stanie jak będę mógł je znaleźć:");
        testDictionary.Delete(5);
        testDictionary.Delete(4);
        Console.WriteLine(testDictionary.Search(5));
        Console.WriteLine(testDictionary.Search(4));
    }
}