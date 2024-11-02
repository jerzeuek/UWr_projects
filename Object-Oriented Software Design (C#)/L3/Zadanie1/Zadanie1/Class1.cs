using System;
using System.Diagnostics;

namespace Exercise01
{
    // Klasy Shape, Circle and Square są przykładami poliformizmu.
    // Circle i Square są 2D kształtami, co oznacza że mają pole
    // które jest brane z bazowej klasy Shape.
    public class Shape
    {
        public virtual double Area() { return 0; }
    }

    public class Circle : Shape
    {
        public double radius { get; set; }
        public Circle() { radius = 5; }
        public override double Area()
        {
            return Math.PI * Math.Pow(radius, 2);
        }
    }

    public class Square : Shape
    {
        public double length { get; set; }
        public Square() { length = 5; }
        public override double Area()
        {
            return Math.Pow(length, 2);
        }
    }

    // Poniżej jest kilka princeples GRASPa
    // Chcemy stworzyć wiele klas które są podobne a potem zrobić kilka testów sprawdzających ich poprawność
    //      * Interfejs łączy je wszystkie, co oznacza że trzyma się Low Coupling 
    //      * Wszystkie klasy testów mają jedno zadanie - robić test. To oznacza że obecne jest High Cohesion
    //      * TestController jest główną klasą - ma wszystkie informacje potrzebne do robienia testów bazując
    //        na nazwie komendy (stringa). Co więcej jak klasą odpowiedzialną za zwracanie wyników testów
    //        więc musi wykonywać te testy it has to make the test - to oznacza że musi je tworzyć
    //        To oznacza że spełnione są Information Expert, Protected Variations i Creator principles

    class TestController
    {
        public void MakeTest(string testName, long testsQuantity)
        {
            ITest test;
            long time;

            switch (testName)
            {
                case "AddIntTest":
                    test = new AddIntTest();
                    time = MeasureTime(test, testsQuantity);
                    Console.WriteLine("Test '" + testName + "' completed in " + time + " ms.");
                    break;
                case "AddInt32Test":
                    test = new AddInt32Test();
                    time = MeasureTime(test, testsQuantity);
                    Console.WriteLine("Test '" + testName + "' completed in " + time + " ms.");
                    break;
                case "MultiplyDoubleTest":
                    test = new MultiplyDoubleTest();
                    time = MeasureTime(test, testsQuantity);
                    Console.WriteLine("Test '" + testName + "' completed in " + time + " ms.");
                    break;
            }
        }

        public long MeasureTime(ITest test, long testsQuantity)
        {
            Stopwatch sw = new Stopwatch();
            sw.Start();
            test.MakeTest(testsQuantity);
            sw.Stop();
            return sw.ElapsedMilliseconds;
        }
    }

    interface ITest
    {
        public void MakeTest(long testsQuantity);
    }

    class AddIntTest : ITest
    {
        private int a = 10;
        private int b = 1;

        public void MakeTest(long testsQuantity)
        {
            for (long i = 0; i < testsQuantity; i++)
                a += b;
        }
    }
    class AddInt32Test : ITest
    {
        private Int32 a = 10;
        private Int32 b = 1;

        public void MakeTest(long testsQuantity)
        {
            for (long i = 0; i < testsQuantity; i++)
                a += b;
        }
    }

    class MultiplyDoubleTest : ITest
    {
        private double a = 3.5;
        private double b = 1.23;

        public void MakeTest(long testsQuantity)
        {
            for (long i = 0; i < testsQuantity; i++)
                a *= b;
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            // Example of polymorhpism principle usage:
            Console.WriteLine("Polymorphism principle - Circle and Square derive from the class Shape:");
            Shape circle = new Circle();
            Shape square = new Square();
            Console.WriteLine("Area of circle: " + circle.Area());
            Console.WriteLine("Area of square: " + square.Area());

            Console.WriteLine("\n\nOther principles presented with tests:");
            TestController testController = new TestController();
            testController.MakeTest("AddIntTest", 1000000000);
            testController.MakeTest("AddInt32Test", 1000000000);
            testController.MakeTest("MultiplyDoubleTest", 1000000000);
        }
    }
}