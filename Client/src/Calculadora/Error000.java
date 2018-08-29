package Calculadora;

public class Error000 extends Exception{
    
    public Error000(String s) {
        super("Comando mal formado: No cumple con la sintaxis: " + s);
    }
}
