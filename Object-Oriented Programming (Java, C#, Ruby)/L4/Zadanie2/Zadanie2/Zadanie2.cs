// Ksawery Plis
// Lista 4 Zadanie 2
// .NET SDK 8.0.100
// Sposób uruchamiania:
//  -wrzucam plik do pustego folderu
//  -w tym folderze w terminalu wywołuję funkcję "dotnet new console"
//  -usuwam plik Program.cs
//  -wywołuję funkcję "dotnet run {nazwapliku}.cs"

using System.Collections;

namespace Zadanie2
{

    class SlowaFibonacciego : IEnumerable<string>
    {
        private int n;
        public SlowaFibonacciego(int n)
        {
            this.n = n;
        }

        public IEnumerator<string> GetEnumerator()
        {
            int i = 0;
            string s1 = "a";
            string s2 = "b";

            string cur;

            while (i < n)
            {
                if (i == 0)
                {
                    cur = s2;
                }
                else if (i == 1)
                {
                    cur = s1;
                }
                else
                {
                    cur = s1 + s2;
                    s2 = s1;
                    s1 = cur;
                }

                i++;
                yield return cur;
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            SlowaFibonacciego sf = new SlowaFibonacciego(6);
            foreach (string s in sf)
                Console.WriteLine(s);
        }
    }
}