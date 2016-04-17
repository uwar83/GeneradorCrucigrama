/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcrucigrama.negocio;

import generadorcrucigrama.dato.Palabra;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CENTRAL
 */
public class Generador {

    public static int HORIZONTAL = 1;
    public static int VERTICAL = -1;

    private int dimension;
    private char tablero[][];
    private List<Palabra> palabras;
    private List<Palabra> palabrasNoUsadas;
    private List<Palabra> palabrasHorizontales;
    private List<Palabra> palabrasVerticales;

    public Generador() {
        dimension = 0;
        tablero = null;
        palabras = new ArrayList<>();
        palabrasNoUsadas = new ArrayList<>();
        palabrasHorizontales = new ArrayList<>();
        palabrasVerticales = new ArrayList<>();

    }

    public void inicializarTablero(int dimension) {
        if (this.dimension != dimension) {
            tablero = new char[dimension][dimension];
            this.dimension = dimension;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tablero[i][j] = ' ';
            }
        }
    }
    /**
     * Funcion que concatena un array de caracateres desde la posicion inicio 
     * hasta la posicion (fin - 1) y lo devuelve como una cadena de tipo String
     * @param dato array de caracteres
     * @param inicio posicion inicial
     * @param fin posicion final
     * @return cadena de tipo String formada por el array de caraceteres
     */
    public String join(char dato[], int inicio, int fin) {
        String palabra = "";
        for (int i = inicio; i < fin; i++) {
            palabra = palabra + dato[i];
        }
        return palabra;
    }


    /**
     * Funcion que concatena un array de caracateres y lo devuelve como una cadena
     * @param dato array de caracteres
     * @return cadena de tipo String formada por el array de caraceteres
     */
    public String join(char dato[]) {
        return new String(dato);
    }

    public String tomarPalabra(int x, int y, int direccion) {
        if (direccion == HORIZONTAL) {
            int inicio = x;
            while (inicio > 0 && tablero[y][inicio] != '*') {
                if (tablero[y][inicio] == ' ') {
                    return "";
                }
                inicio = inicio - 1;
            }
            int fin = x;
            while (fin < tablero[y].length && tablero[y][fin] != '*') {
                if (tablero[y][fin] == ' ') {
                    return "";
                }
                fin = fin + 1;
            }
            if (inicio == 0 && fin == tablero[y].length) {
                return "";
            }
            if ((fin - inicio) < 5) {
                return "";
            }
            return join(tablero[y], inicio + 1, fin);
        } else {
            int inicio = y;
            while (inicio > 0 && tablero[inicio][x] != '*') {
                if (tablero[inicio][x] == ' ') {
                    return "";
                }
                inicio = inicio - 1;
            }
            int fin = y;
            while (fin < tablero.length && tablero[fin][x] != '*') {
                if (tablero[fin][x] == ' ') {
                    return "";
                }
                fin = fin + 1;
            }
            if (inicio == 0 && fin == tablero[y].length) {
                return "";
            }
            if ((fin - inicio) < 5) {
                return "";
            }

            char palabra[] = new char[inicio + 1 - fin];
            for (int i = inicio + 1; i < fin; i++) {
                palabra[i - (inicio + 1)] = tablero[i][x];
            }
            return join(palabra);
        }
    }

}
