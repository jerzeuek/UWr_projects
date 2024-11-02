// Ksawery Plis
// Lista 2 Zadanie 1
// csc version 4.8.9032.0

using System;

namespace Zadanie1
{
    class Program
    {
        static void Main(string[] args)
        {
            var intStream = new IntStream();
            var fibStream = new FibStream();
            var randomInt = new RandomInt();
            var randomWordStream = new RandomWordStream();

            Console.WriteLine("10 pierwszych liczb naturalnych:");
            for (int i = 0; i < 10; i++)
            {
                Console.WriteLine(intStream.Next());
            }
            intStream.Reset();
            Console.WriteLine("Pierwsza wartość po resecie: " + intStream.Next());

            Console.WriteLine("\n10 pierwszych liczb Fibonnaciego:");
            for (int i = 0; i < 10; i++)
            {
                Console.WriteLine(fibStream.Next());
            }
            fibStream.Reset();
            Console.WriteLine("Pierwsza wartość po resecie: " + fibStream.Next());

            Console.WriteLine("\n10 losowych intów:");
            for (int i = 0; i < 10; i++)
            {
                Console.WriteLine(randomInt.Next());
            }
            randomInt.Reset();
            Console.WriteLine("Pierwsza wartość po resecie: " + randomInt.Next());

            Console.WriteLine("\n10 losowych stringów o długościach równych kolejnym liczbom Fibonnaciego:");
            for (int i = 0; i < 10; i++)
            {
                Console.WriteLine(randomWordStream.Next());
            }
            randomWordStream.Reset();
            Console.WriteLine("Pierwsza wartość po resecie: " + randomWordStream.Next());
        }
    }
    public class IntStream
    {
        public int current;

        public IntStream()
        {
            current = 0;
        }
        virtual public int Next()
        {
            if (!Eos())
            {
                return current++;
            }
            else
            {
                Console.WriteLine("Koniec strumienia!");
                return current;
            }
        }

        virtual public bool Eos()
        {
            return Int32.MaxValue <= current;
        }
        virtual public void Reset()
        {
            current = 0;
        }
    }

    public class FibStream : IntStream
    {
        private int previous;
        private int new_fib;

        public FibStream()
        {
            previous = 1;
            current = 0;
        }

        public override int Next()
        {
            if (!Eos())
            {
                new_fib = current + previous;
                previous = current;
                current = new_fib;
                return current;
            }
            else
            {
                Console.WriteLine("Koniec strumienia!");
                return current;
            }
        }

        public override bool Eos()
        {
            return Int32.MaxValue <= current + previous;
        }

        public override void Reset()
        {
            previous = 1;
            current = 0;
        }
    }

    public class RandomInt : IntStream
    {
        private Random rand;

        public RandomInt()
        {
            rand = new Random();
        }

        public override int Next()
        {
            return rand.Next();
        }

        public override bool Eos()
        {
            return false;
        }

        public override void Reset()
        {
            rand = new Random();
        }
    }

    public class RandomWordStream
    {
        private FibStream fibStream;
        private RandomInt randomInt;
        public RandomWordStream()
        {
            fibStream = new FibStream();
            randomInt = new RandomInt();
        }

        public string Next()
        {
            int next_fib = fibStream.Next();
            string result = "";

            for (int i = 0; i < next_fib; i++)
            {
                result += RandomChar();
            }

            return result;

        }
        private char RandomChar()
        {
            int rand = randomInt.Next() % 26;
            return (char)('a' + rand);
        }

        public void Reset()
        {
            fibStream = new FibStream();
        }
    }
}