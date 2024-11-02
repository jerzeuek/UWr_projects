using System;

public interface IShape
{
    int CalculateArea();
}

public class Rectangle : IShape
{
    public int Width { get; set; }
    public int Height { get; set; }

    public virtual int CalculateArea()
    {
        return Width * Height;
    }
}

public class Square : IShape
{
    public int SideLength { get; set; }

    public int CalculateArea()
    {
        return SideLength * SideLength;
    }
}

public class AreaCalculator
{
    public int CalculateArea(IShape shape)
    {
        return shape.CalculateArea();
    }
}

class Program
{
    public static void Main(string[] args)
    {
        int w = 4, h = 5;
        IShape rect = new Rectangle() { Width = w, Height = h };
        AreaCalculator calc = new AreaCalculator();
        Console.WriteLine("prostokąt o wymiarach {0} na {1} ma pole {2}",
                            w, h, calc.CalculateArea(rect));
        IShape sqr = new Square() { SideLength = h };
        Console.WriteLine("kwadrat o boku {0} ma pole {1}", h, calc.CalculateArea(sqr));
    }
}