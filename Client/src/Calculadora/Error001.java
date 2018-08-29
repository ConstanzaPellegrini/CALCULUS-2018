package Calculadora;

public class Error001 extends Exception {

    public Error001(String s) {
        super("Operacion no conocida: " + s + ".");
    }
}
