package Calculadora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Serializador{
    
    public Serializador()
    {
        super();
    }
    
    public static void guardarMatriz(Matriz mat,String nombre) throws IOException{
        String ruta = "datos/" + nombre;
        File archivo = new File(ruta);
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        PrintWriter pw = new PrintWriter(bw); 
        pw.write(mat.getNombre() + "\n" + mat.getDescripcion() + "\n" + mat.cantFila + "," + mat.getCantColumna() + "\n" + mat.convertirMatrizAStringGuardar());
        bw.close();
    }

        
    public static Matriz recuperaMatriz(String nombreArch) throws IOException, Error003, Error006 {
          String cadena, nombre, descripcion;
          String[] arreglo;
          int filas, columnas, i, j;
          Matriz nueva = null;
          String archivo = "datos/" + nombreArch;
          FileReader f = new FileReader(archivo);
          try{
              BufferedReader b = new BufferedReader(f);
              if (f != null){
                  nombre = b.readLine();
                  descripcion = b.readLine();
                  cadena = b.readLine();
                  arreglo = cadena.split(",");
                  filas = Integer.parseInt(arreglo[0]);
                  columnas = Integer.parseInt(arreglo[1]);
                  nueva = new Matriz(nombre,descripcion,filas,columnas);
                  cadena = b.readLine();
                  i=0;
                  while (cadena !=null){
                      
                      arreglo = cadena.split(",");
                      for(j=0; j < columnas ; j++){
                         nueva.addElemento(Double.parseDouble(arreglo[j]), i, j);
                         
                      }
                      cadena = b.readLine();
                      i++;
                  }                    
              }
              b.close();
          }
          catch(FileNotFoundException e){
            throw new Error003();
          }
          return nueva;
    }
}