package Calculadora;

public class Error002 extends Exception {

    public Error002(String s) {
        super("Dispositivo no conocido: " + s + ".");
    }
}
