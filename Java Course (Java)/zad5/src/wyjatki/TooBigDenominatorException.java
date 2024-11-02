package wyjatki;

public class TooBigDenominatorException extends IllegalArgumentException{
    public TooBigDenominatorException(String s) {
        super(s);
    }
}