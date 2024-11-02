// Ksawery Plis
// Lista 5 Zadanie 2
// javac 17.0.8.1

abstract class Expression{
    public abstract int evaluate();
    public abstract String toString(); 
}

class Const extends Expression{
    private int value;

    public Const(int value){
        this.value = value;
    }

    public int evaluate() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}

class Var extends Expression{
    private String name;
    private int value;

    public Var(String name, int value){
        this.name = name;
        this.value = value;
    }

     public int evaluate(){
        return value;
     }

     public String toString() {
        return name;
     }
}

abstract class BinaryOp extends Expression{
    protected Expression left;
    protected Expression right;

    public BinaryOp(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
}

class Add extends BinaryOp{
    public Add(Expression left, Expression right){
        super(left, right);
    }

    public int evaluate(){
        return left.evaluate() + right.evaluate();
    }

    public String toString(){
        return String.format("(%s + %s)", left.toString(), right.toString());
    }
}

class Sub extends BinaryOp{
    public Sub(Expression left, Expression right){
        super(left, right);
    }

    public int evaluate(){
        return left.evaluate() - right.evaluate();
    }

    public String toString(){
        return String.format("(%s - %s)", left.toString(), right.toString());
    }
}

class Mult extends BinaryOp{
    public Mult(Expression left, Expression right){
        super(left, right);
    }

    public int evaluate(){
        return left.evaluate() * right.evaluate();
    }

    public String toString(){
        return String.format("(%s * %s)", left.toString(), right.toString());
    }
}

public class Zadanie2 {
    public static void main(String[] args) throws Exception {
        Expression var = new Var("x", 3);
        Expression cnst = new Const(2);
        Expression add = new Add(var, cnst);
        Expression sub = new Sub(var, cnst);
        Expression mult = new Mult(var, cnst);
        Expression mixed = new Mult(cnst, new Sub(mult,(new Add(add, sub))));
        System.out.println("Wypisywanie wyrażeń:"); 
        System.out.println(var.toString());
        System.out.println(cnst.toString());
        System.out.println(add.toString());
        System.out.println(sub.toString());   
        System.out.println(mult.toString());
        System.out.println(mixed.toString());
        System.out.println();
        System.out.println("Wypisywanie wyników:");
        System.out.println(var.evaluate());
        System.out.println(cnst.evaluate());
        System.out.println(add.evaluate());
        System.out.println(sub.evaluate());
        System.out.println(mult.evaluate());
        System.out.println(mixed.evaluate());

    }
}
