using System;

namespace Before
{
    public class ReportPrinter
    {
        public string GetData()
        {
            Console.WriteLine("Receiving data...");
            return "Received data";
        }

        public void FormatDocument() { Console.WriteLine("Formatting document..."); }
        public void PrintReport()
        {
            GetData();
            FormatDocument();
            Console.WriteLine("Printing report...");
        }
    }
}

namespace After
{
    public class ReportPrinter
    {
        public void PrintReport()
        {
            DataGetter collector = new DataGetter();
            collector.GetData();

            DocumentFormatter formatter = new DocumentFormatter();
            formatter.FormatDocument();

            Console.WriteLine("Printing report...");
        }
    }

    public class DataGetter
    {
        public string GetData()
        {
            Console.WriteLine("Receiving data...");
            return "Received data";
        }
    }

    public class DocumentFormatter
    {
        public void FormatDocument()
        {
            Console.WriteLine("Formatting document...");
        }
    }
}

namespace Exercise02
{


    class Program
    {
        static void Main(string[] args)
        {
            var firstPrinter = new Before.ReportPrinter();
            var secondPrinter = new After.ReportPrinter();

            Console.WriteLine("Kod który nie spełnia SRP");
            firstPrinter.PrintReport();

            Console.WriteLine("\nKod który spełnia SRP");
            secondPrinter.PrintReport();
        }
    }
}