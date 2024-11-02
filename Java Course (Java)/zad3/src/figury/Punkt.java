package figury;

public class Punkt {

    private double x, y;

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void przesuń(Wektor v) { 
        this.x = x + v.p;
        this.y = y + v.q;
    }

    public void obróć(Punkt p, double kąt) {
        double radians = Math.toRadians(kąt);
        double deltaX = x - p.x;
        double deltaY = y - p.y; 
        this.x = deltaX * Math.cos(radians) - deltaY * Math.sin(radians) + p.x;
        this.y = deltaX * Math.sin(radians) + deltaY * Math.cos(radians) + p.y;
    }

    public void odbij(Prosta p) {
        double d = p.a * p.a + p.b * p.b;
        
        this.x = (x * (p.b * p.b - p.a * p.a) - 2 * p.a * p.b * y - 2 * p.a * p.c) / d;
        this.y = (y * (p.a * p.a - p.b * p.b) - 2 * p.a * p.b * x - 2 * p.b * p.c) / d;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}