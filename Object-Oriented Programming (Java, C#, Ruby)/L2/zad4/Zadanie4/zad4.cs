// Ksawery Plis
// Lista 2 Zadanie 4
// csc version 4.8.9032.0

using System;
using System.Collections.Generic;

namespace Zadanie4
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("Leniwa lista liczb: ");
            var lazyIntList = new LazyIntList();
            Console.WriteLine("Długość nowej listy: " + lazyIntList.Size());
            Console.WriteLine("40. element listy: " + lazyIntList.Element(40));
            Console.WriteLine("Długość listy po sprawdzeniu 40. elementu: " + lazyIntList.Size());
            Console.WriteLine("38. element listy: " + lazyIntList.Element(38));
            Console.WriteLine("Długość listy po sprawdzeniu 38. elementu: " + lazyIntList.Size());

            Console.WriteLine("\nLeniwa lista liczb pierwszych: ");
            var lazyPrimeList = new LazyPrimeList();
            Console.WriteLine("Długość nowej listy: " + lazyPrimeList.Size());
            Console.WriteLine("40. liczba pierwsza: " + lazyPrimeList.Element(40));
            Console.WriteLine("Długość listy po sprawdzeniu 40. liczby pierwszej: " + lazyPrimeList.Size());
            Console.WriteLine("38. liczba pierwsza: " + lazyPrimeList.Element(38));
            Console.WriteLine("Długość listy po sprawdzeniu 38. liczby pierwszej: " + lazyPrimeList.Size());

        }
    }

    public class LazyIntList
    {
        public List<int> list;
        public int list_length;
        public LazyIntList()
        {
            list = new List<int>();
            list_length = 0;
        }

        virtual public int Element(int i)
        {
            if (this.Size() < i)
            {
                for (int j = this.Size() + 1; j <= i; j++)
                {
                    list.Add(j);
                    list_length++;
                }
            }
            return list[i - 1];
        }

        virtual public int Size()
        {
            return this.list_length;
        }
    }

    public class LazyPrimeList : LazyIntList
    {
        int cur_prime;
        public LazyPrimeList()
        {
            cur_prime = 1;
        }

        public override int Element(int i)
        {
            if (this.Size() < i)
            {
                for (int j = this.Size() + 1; j <= i; j++)
                {
                    NextPrime();
                    list.Add(cur_prime);
                    list_length++;
                }
            }
            return list[i - 1];
        }

        private void NextPrime()
        {
            while (0 == 0)
            {
                cur_prime++;
                if (CheckIfPrime(cur_prime))
                {
                    return;
                }
            }
        }

        private bool CheckIfPrime(int n)
        {
            for (int i = 2; i < n; i++)
            {
                if (n % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }
}