package Calculadora;

import java.util.ArrayList;

public class Matriz {
    String nombre,descripcion;
    int cantFila, cantColumna;
    double[][] matriz;

    
    public Matriz(int cantFila, int cantColumna) throws Error006 {
        
        if (cantFila > 0 && cantColumna > 0){
            this.cantFila = cantFila;
            this.cantColumna = cantColumna;
            this.matriz = new double[cantFila][cantColumna];
        }
        else
            throw new Error006("Matriz de dimensiones inexistentes.");
    }
    
    public Matriz(String nombre, String descripcion, int cantFila, int cantColumna) throws Error006 {
        
        if (cantFila > 0 && cantColumna > 0){
            this.cantFila = cantFila;
            this.cantColumna = cantColumna;
            this.nombre = nombre;
            this.descripcion = descripcion; 
            this.matriz = new double[cantFila][cantColumna];
        }
        else
            throw new Error006("Matriz de dimensiones inexistentes.");
    }        
    
    public double[][] getMatriz() {
        return matriz;
    }

    public int getCantFila() {
        return cantFila;
    }

    public int getCantColumna() {
        return cantColumna;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void addElemento(double elemento, int i, int j) throws Error006 {
        if (i >= this.cantFila || j >= this.cantColumna)
            throw new Error006("No existe elemento de la matriz.");
        else
            this.matriz[i][j] = elemento;
    }
    
    public double getElemento(int i, int j) throws Error006 {
        double result;
        if (i >= this.cantFila || j >= this.cantColumna)
            throw new Error006("No existe elemento de la matriz.");
        else
            result = this.matriz[i][j];
        return result;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean esCuadrada(){
        return (this.cantFila == this.cantColumna);
    }

    public static Matriz getSubMatriz(double[][] matriz, int filas, int columnas, int columna) throws Error006 {
            Matriz subMatriz = new Matriz(filas-1,columnas-1);
            double[][] m = subMatriz.getMatriz(); 
            int contador=0;
            for (int j=0;j<columnas;j++)
            {
                    if (j==columna) continue;
                    for (int i=1;i<filas;i++)
                            m[i-1][contador]=matriz[i][j];
                    contador++;
            }
            return subMatriz;
    }
    
    //Descripcion puede ser real o entero. 
    //Se invoca ANTES DE GUARDAR con las matrices A y B siendo las que se utilizaron para operar el resultado. 
    //C.actualiza("C",A,B);  
    //ATransp.actualiza("ATransp",A, null);

    
    public double determinante() throws Error004, Error006 {

                    double[][] matriz = this.getMatriz();
                    int filas = this.cantFila;
                    int columnas = this.cantColumna;
                    double determinante = 0.0;
                    
                    if (matriz != null && this.esCuadrada()){
                        // Si la matriz es 1x1, el determinante es el elemento de la matriz
                        if ((filas==1) && (columnas==1))
                                return matriz[0][0];
                        
                        int signo=1;
                        
                        for (int columna=0;columna<columnas;columna++)
                        {
                                // Obtiene el adjunto de fila=0, columna=columna, pero sin el signo.
                                Matriz subMatriz = getSubMatriz(matriz, filas, columnas, columna);
                                determinante = determinante + signo*matriz[0][columna] * subMatriz.determinante();
                                signo *= -1;
                        }
                    }
                    else
                        throw new Error004();

                    return determinante;
            }
    
    public Matriz traspuesta() throws Error006 {
        Matriz nueva = new Matriz(this.cantFila, this.cantColumna);
        for (int i = 0; i < this.cantFila; i++)
            for (int j = 0; j < this.cantColumna; j++)
                nueva.addElemento(this.getElemento(i, j), j, i);
        
        nueva.setDescripcion("Traspuesta de" + this.nombre + ".");
        return nueva;
    }

    public Matriz suma(Matriz b) throws Error006, Error004 {
            Matriz resultado = new Matriz(this.getCantFila(),this.getCantColumna());
            double[][] otra = b.getMatriz();
            if (b.getCantFila() != this.getCantFila() || b.getCantColumna() != this.getCantColumna()) 
                throw new Error004();
            else{
                for (int i = 0; i < this.cantFila; i++)
                    for (int j = 0; j < this.cantColumna; j++)
                        resultado.addElemento(this.matriz[i][j] + otra[i][j], i, j);
            }
            resultado.setDescripcion("Suma entre " + this.nombre + " y " + b.getNombre());
            return resultado;
        }

        public Matriz resta(Matriz b) throws Error004, Error006 {
            Matriz resultado = new Matriz(this.cantFila,this.cantColumna);
            double[][] otra = b.getMatriz();
            if (b.getCantFila() != this.cantFila || b.getCantColumna() != this.cantColumna) 
                throw new Error004();
            else{
                for (int i = 0; i < this.cantFila; i++)
                    for (int j = 0; j < this.cantColumna; j++)
                        resultado.addElemento(this.matriz[i][j] - otra[i][j], i, j);
            }
            resultado.setDescripcion("Resta entre " + this.nombre + " y " + b.getNombre());
            return resultado;
        }

    public Matriz multiplicacion(Matriz b) throws Error006, Error004 {
        Matriz resultado = new Matriz(this.cantFila,b.getCantColumna());
        double[][] otra = b.getMatriz();        
        if (this.cantColumna != b.getCantFila()) 
            throw new Error004();
        else{
            for (int i = 0; i < resultado.getCantFila(); i++)
                for (int j = 0; j < resultado.getCantColumna(); j++)
                    for (int k = 0; k < this.cantColumna; k++)
                        resultado.addElemento(resultado.getElemento(i, j) + (this.matriz[i][j] * otra[i][j]), i, j);
        }
        resultado.setDescripcion("Multiplicacion entre " + this.nombre + " y " + b.getNombre());
        return resultado;
    }
    
    public String convertirMatrizAStringGuardar(){
        String resultado = "";
        for (int i=0; i<this.cantFila; i++){
            for (int j=0; j<this.cantColumna-1; j++){
                resultado += this.matriz[i][j] + ",";
            }
            resultado += this.matriz[i][this.cantColumna - 1] + "\n";
        }
        return resultado;
    }
    
    public String convertirMatrizAStringPantalla(){
        String resultado = "";
        for (int i=0; i<this.cantFila; i++){
            for (int j=0; j<this.cantColumna-1; j++){
                resultado += this.matriz[i][j] + "  ";
            }
            resultado += this.matriz[i][this.cantColumna - 1] + "\n";
        }
        return resultado;
    }
    
}
