package Controlador;

import Calculadora.Error000;
import Calculadora.Error001;
import Calculadora.Error002;
import Calculadora.Error003;
import Calculadora.Error004;
import Calculadora.Error006;
import Calculadora.Matriz;
import Calculadora.Modelo;

import Calculus.IVista;
import Calculus.Ventana;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

public class Controlador implements ActionListener{
    
    private Modelo modelo;
    private IVista vista;
    
    public Controlador(IVista vista, Modelo modelo) {
        super();
        this.modelo = modelo;
        this.vista = vista;
    }

    public void ejecutaInstrucciones() throws Error001, Error000, IOException, Error003, Error006, Error002, Error004 {
        String aux = "";
        String muestra;
        ArrayList<String> instrucciones = this.modelo.getInstrucciones();
        Iterator<String> it = instrucciones.iterator();
        while (it.hasNext()){
            aux = it.next();
            muestra = modelo.invocaInstruccion(aux);
            vista.imprimirMatriz(muestra);
        }
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.vista.esIngresoCmnd(e)){
            this.modelo.agregaInstruccion(this.vista.getInstruccion());
            this.vista.limpiarSalida();
        }
        else{
            if (this.vista.esJBEjecutar(e)){
                try{
                    this.ejecutaInstrucciones();
                }
                catch (Exception f){
                    this.vista.muestraError(f.getMessage());
                }
                finally{
                    this.vista.limpiarPantalla();
                    this.modelo.borrarArrayInstrucciones();

                }
            }
        }
    }
}
