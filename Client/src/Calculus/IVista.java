package Calculus;

import Controlador.Controlador;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

public interface IVista {
    public JTextField getIngresoCmnd();
    public JButton getEjecutar();
    public void comenzar();
    public void setControlador(Controlador controlador);
    public boolean esIngresoCmnd(ActionEvent e);
    public boolean esJBEjecutar(ActionEvent e);
    public String getInstruccion();
    public void agregaInstruccion(String instruccion);

    public void imprimirMatriz(String muestra);
    public void muestraError(String mensaje);
    public void limpiarPantalla();
    public void limpiarSalida();
}
