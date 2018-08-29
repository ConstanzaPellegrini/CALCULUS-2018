package Calculadora;

import Calculus.IVista;
import Calculus.Ventana;

import Controlador.Controlador;

public class Prueba {
    public Prueba() {
        super();
    }

    public static void main(String[] args) {
        
        IVista vista = new Ventana();        
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(vista, modelo);
        vista.setControlador(controlador);
        vista.comenzar();
    }
}
