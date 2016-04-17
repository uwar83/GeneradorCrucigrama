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

    public void borrarPalabra(int x, int y, int direccion, int largo, List<Palabra> lista) {
        int posx = x, posy = y;
        for (int i = 0; i < largo; i++) {
            if (direccion == HORIZONTAL) {
                posx = x + i;
            } else {
                posy = y + i;
            }
            String vieja = tomarPalabra(posx, posy, direccion * -1);
            if (vieja.isEmpty() || vieja.length() < 5) {
                tablero[posy][posx] = ' ';
            }
        }
        // Borrar los *
        if (direccion == HORIZONTAL) {
            if (x > 0) {
                boolean borrar = true;
                if (y > 0 && !tomarPalabra(x - 1, y - 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (y < (tablero.length - 1) && !tomarPalabra(x - 1, y + 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (x > 1 && !tomarPalabra(x - 2, y, direccion).isEmpty()) {
                    borrar = false;
                }
                if (borrar) {
                    tablero[y][x - 1] = ' ';
                }
            }
            if ((x + largo) < tablero[y].length) {
                boolean borrar = true;
                if (y > 0 && !tomarPalabra(x + largo, y - 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (y < (tablero.length - 1) && !tomarPalabra(x + largo, y + 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if ((x + largo) < (tablero[y].length - 1) && !tomarPalabra(x + largo + 1, y, direccion).isEmpty()) {
                    borrar = false;
                }
                if (borrar) {
                    tablero[y][x + largo] = ' ';
                }
            }
        } else {
            if (y > 0) {
                boolean borrar = true;
                if (x > 0 && !tomarPalabra(x - 1, y - 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (x < (tablero.length - 1) && !tomarPalabra(x + 1, y - 1, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (y > 1 && !tomarPalabra(x, y - 2, direccion).isEmpty()) {
                    borrar = false;
                }
                if (borrar) {
                    tablero[y - 1][x] = ' ';
                }
            }
            if ((y + largo) < tablero.length) {
                boolean borrar = true;
                if (x > 0 && !tomarPalabra(x - 1, y + largo, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if (x < (tablero[y].length - 1) && !tomarPalabra(x + 1, y + largo, direccion * -1).isEmpty()) {
                    borrar = false;
                } else if ((y + largo) < (tablero.length - 1) && !tomarPalabra(x, y + largo + 1, direccion).isEmpty()) {
                    borrar = false;
                }
                if (borrar) {
                    tablero[y + largo][x] = ' ';
                }
            }
        }
    }

    /**
     * Funcion que concatena un array de caracateres desde la posicion inicio
     * hasta la posicion (fin - 1) y lo devuelve como una cadena de tipo String
     *
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
     * Funcion que concatena un array de caracateres y lo devuelve como una
     * cadena
     *
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

    public boolean libre(int x, int y) {
        if (y < 0 || y >= tablero.length) {
            return true;
        }
        if (x < 0 || x >= tablero[y].length) {
            return true;
        }
        return (tablero[y][x] == ' ' || tablero[y][x] == '*');

    }

}
