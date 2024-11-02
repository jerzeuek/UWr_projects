// Ksawery Plis
// Lista 3 Zadanie 1
// Microsoft Visual Studio Community 2022 Wersja 17.8.3

using Zadanie1;

class Program
{
    static void Main(string[] args)
    {
        Lista<int> testList = new Lista<int>();
        Console.WriteLine("Czy nowa lista jest pusta?");
        Console.WriteLine(testList.is_empty());

        testList.push_back(6);
        testList.push_back(7);
        testList.push_back(8);
        testList.push_back(9);
        testList.push_back(10);

        testList.push_front(5);
        testList.push_front(4);
        testList.push_front(3);
        testList.push_front(2);
        testList.push_front(1); // po takich dodaniach powinny wyjść liczby naturalne 1-10 w odpowiedniej kolejności

        Console.WriteLine("Lista po dodaniu elementów:");
        testList.printList();
        Console.WriteLine("Czy lista z elementami jest pusta?");
        Console.WriteLine(testList.is_empty());

        testList.pop_front();
        testList.pop_back();
        testList.pop_front();
        testList.pop_back();
        testList.pop_front();
        testList.pop_back();

        Console.WriteLine("Lista po usunięciu kilku przednich i tylnich wartości: ");
        testList.printList();

        testList.pop_front();
        testList.pop_back();
        testList.pop_front();
        testList.pop_back();


        Console.WriteLine("Po usunięciu wszystkich wartości: ");
        testList.printList();

        Console.WriteLine("Co jeśli będę chciał usunąć jeszcze jeden element?");
        try
        {
            testList.pop_front();
        }

        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
        }
    }
}