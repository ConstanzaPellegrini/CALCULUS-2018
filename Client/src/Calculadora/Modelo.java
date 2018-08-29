package Calculadora;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class Modelo extends Observable{
    private ArrayList<String> instrucciones = new ArrayList<String>();
    
    public Modelo() {
    }


    public ArrayList<String> getInstrucciones() {
        return instrucciones;
    }

    public void agregaInstruccion(String instruccion){
        this.instrucciones.add(instruccion);
    }
    
    public void muestraInstrucciones(){
        String aux = "";
        Iterator<String> it = this.instrucciones.iterator();
        while (it.hasNext()){
            aux = it.next();
            System.out.println(aux);
        }
    }
    
    public void borrarArrayInstrucciones(){
        this.instrucciones.clear();
    }
    
    public boolean dispositivoCorrecto(String dispo){
    
        return (dispo.compareToIgnoreCase("p") == 0 || dispo.compareToIgnoreCase("a") == 0 || dispo.compareToIgnoreCase("pa") == 0);
    }
    
    public String accionDispo(Matriz A, String dispo, String nombre) throws IOException {
        String retorno;
        if (dispo.compareToIgnoreCase("p") == 0){
            retorno = A.convertirMatrizAStringPantalla();
        }
        else{                
            Serializador.guardarMatriz(A,nombre);
            retorno = "Se ha guardado exitosamente";
            if (dispo.compareToIgnoreCase("pa") == 0){
                retorno = " y la matriz a mostrar es: \n" + A.convertirMatrizAStringPantalla();
            }
        }
        return retorno;
    }
    
    
    public String accionDispo(double det, String dispo, String nombre, String nombreMat) throws IOException, Error006 {
        String retorno;
        if (dispo.compareToIgnoreCase("p") == 0){
            retorno = "El determinante de la matriz es: " + det + ".";
        }
        else{
            Matriz A = new Matriz(nombre,"double",1,1);
            A.addElemento(det, 0, 0);
            A.setDescripcion("Determinante de " + nombreMat);
            Serializador.guardarMatriz(A,nombre);
            retorno = "Se ha guardado exitosamente";
            if (dispo.compareToIgnoreCase("pa") == 0){
                retorno = " y su determinante a mostrar es: \n" + A.convertirMatrizAStringPantalla();
            }
        }
        return retorno;
    }
    
    public String invocaInstruccion(String instruccion) throws Error001, Error000, IOException, Error003, Error006,
                                                             Error002, Error004 {
        String dispo, op, A,B,C, pantalla;
        Matriz AMat, BMat, Aux;
        String[] arreglo = instruccion.split(" ");
        op = arreglo[0];
        
        if (op.compareToIgnoreCase("trasp")== 0 || op.compareToIgnoreCase("det") == 0){
            if(arreglo.length == 3){
                A = arreglo[1] + ".mat";
                AMat = Serializador.recuperaMatriz(A);
                dispo = arreglo[2];
                if (dispo.compareToIgnoreCase("p") == 0){
                    if (op.compareToIgnoreCase("trasp") == 0){
                        pantalla = accionDispo(AMat.traspuesta(),dispo,null);
                    }
                    else{
                        pantalla = accionDispo(AMat.determinante(),dispo,null,AMat.getNombre());
                    }
                }
                else{
                    if (dispositivoCorrecto(dispo))
                        throw new Error000("no existe matriz de salida.");
                    else
                        throw new Error002(dispo); 
                }
                    
            }
            else{
                if (arreglo.length == 4){
                    A = arreglo[1] + ".mat";
                    AMat = Serializador.recuperaMatriz(A);
                    B = arreglo[2] + ".mat";
                    dispo = arreglo[3];
                    if (dispositivoCorrecto(dispo)){
                        if (op.compareToIgnoreCase("trasp") == 0){
                            Aux = AMat.traspuesta();
                            Aux.setNombre(arreglo[2]);
                            pantalla = accionDispo(Aux,dispo,B);
                        }
                        else{
                            pantalla = accionDispo(AMat.determinante(),dispo,B,AMat.getNombre());
                        }
                    }
                    else
                        throw new Error002(dispo);                             
                }
                else
                    throw new Error000("cantidad erronea de parametros");
            }
        }
        else{
            if(op.compareToIgnoreCase("suma") == 0 || op.compareToIgnoreCase("resta") == 0 || op.compareToIgnoreCase("mult") ==0 ){
                if(arreglo.length == 4){
                    A = arreglo[1] + ".mat";
                    AMat = Serializador.recuperaMatriz(A);
                    B = arreglo[2] + ".mat";
                    BMat = Serializador.recuperaMatriz(B);
                    dispo = arreglo[3];
                    if (dispo.compareToIgnoreCase("p") == 0){
                        if (op.compareToIgnoreCase("suma") == 0){
                            pantalla = accionDispo(AMat.suma(BMat),dispo,null);
                        }
                        else{
                            if (op.compareToIgnoreCase("resta") == 0)
                                pantalla = accionDispo(AMat.resta(BMat), dispo, null);
                            else{
                                pantalla = accionDispo(AMat.multiplicacion(BMat),dispo,null);
                            }
                        }
                    }
                    else
                        throw new Error002(dispo); 
                }
                else{
                    if (arreglo.length == 5){
                        A = arreglo[1] + ".mat";
                        AMat = Serializador.recuperaMatriz(A);
                        B = arreglo[2] + ".mat";
                        BMat = Serializador.recuperaMatriz(B);
                        C = arreglo[3] + ".mat";
                        dispo = arreglo[4];
                        if (dispositivoCorrecto(dispo)){
                            if (op.compareToIgnoreCase("suma") == 0){
                                Aux = AMat.suma(BMat);
                                Aux.setNombre(arreglo[3]);
                                pantalla = accionDispo(Aux,dispo,C);
                            }
                            else{
                                if (op.compareToIgnoreCase("resta") == 0){
                                    Aux = AMat.resta(BMat);
                                    Aux.setNombre(arreglo[3]);
                                    pantalla = accionDispo(Aux,dispo,C);
                                }
                                else{
                                    Aux = AMat.multiplicacion(BMat);
                                    Aux.setNombre(arreglo[3]);
                                    pantalla = accionDispo(Aux,dispo,C);
                                }
                            }
                        }
                        else
                            throw new Error002(dispo);                             
                    }
                    else
                        throw new Error000("cantidad erronea de parametros");    
                }
            }
            else{
                throw new Error001(op);                
            }
        }
    
        return pantalla;
    }
}
