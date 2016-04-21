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
    private List<Palabra> listasUsadas;
    private List<Palabra> palabrasHorizontales;
    private List<Palabra> palabrasVerticales;

    public Generador() {
        dimension = 0;
        tablero = null;
        palabras = new ArrayList<>();
        listasUsadas = new ArrayList<>();
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

    public void escribirPalabra(Palabra palabra, int x, int y, int direccion) {
        int largo = palabra.getRespuesta().length();
        palabra.setDireccion(direccion);
        palabra.setX(x);
        palabra.setY(y);
        if (direccion == HORIZONTAL) {
            if (x > 0) {
                tablero[y][x - 1] = '*';
            }
            if ((x + largo) < tablero.length) {
                tablero[y][x + largo] = '*';
            }
            for (int i = 0; i < largo; i++) {
                tablero[y][x + i] = palabra.getRespuesta().charAt(i);
            }
        } else {
            if (y > 0) {
                tablero[y - 1][x] = '*';
            }
            if ((y + largo) < tablero.length) {
                tablero[y + largo][x] = '*';
            }
            for (int i = 0; i < largo; i++) {
                tablero[y + i][x] = palabra.getRespuesta().charAt(i);
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

    public boolean colocarPalabra(Palabra palabra, int x, int y, int direccion, List<Palabra> lista) {
        // Valido que no haya un caracter antes del comienzo
        int largo = palabra.getRespuesta().length();
        if (direccion == HORIZONTAL) {
            if (x < 0 || (x + largo) > tablero.length) {
                return false;
            }
            if (x > 0 && !libre(x - 1, y)) {
                return false;
            }
            if ((x + largo) < tablero.length && !libre(x + largo, y)) {
                return false;
            }
        } else {
            if (y < 0 || (y + largo) > (tablero[0].length)) {
                return false;
            }
            if (y > 0 && !libre(x, y - 1)) {
                return false;
            }
            if ((y + largo) < (tablero.length) && !libre(x, y + largo)) {
                return false;
            }
        }
        // Pruebo si la palabra entra.
        for (int i = 0; i < largo; i++) {
            char letra = ' ';
            if (direccion == HORIZONTAL) {
                letra = tablero[y][x + i];
            } else {
                letra = tablero[y + i][x];
            }
            // No se puede poner una palabra en los casilleros negros
            if (letra == '*') {
                return false;
            }
            // Tampoco se puede poner si ya hay otra letra
            if (letra != ' ' && letra != palabra.getRespuesta().charAt(i)) {
                return false;
            }
        }
        // Si entra, la escribo y la saco de la lista:
        escribirPalabra(palabra, x, y, direccion);
        lista.remove(palabra);
        listasUsadas.add(palabra);//agregado usuario
        // Validacion, recursiva
        //boolean todomal = false;
        int ant[] = new int[2];
        int pos[] = new int[2];
        int sig[] = new int[2];
        for (int i = 0; i < palabra.getRespuesta().length(); i++) {
            if (direccion == HORIZONTAL) {
                ant[0] = (x + i);
                ant[1] = (y - 1);
                pos[0] = (x + i);
                pos[1] = (y);
                sig[0] = (x + i);
                sig[1] = (y + 1);
            } else {
                ant[0] = (x - 1);
                ant[1] = (y + i);
                pos[0] = (x);
                pos[1] = (y + i);
                sig[0] = (x + 1);
                sig[1] = (y + i);
            }
            String pisada = tomarPalabra(pos[0], pos[1], direccion * -1);

            // Valida si hay otras letras pero no una palabra
            if (pisada.isEmpty()) // Chequeo si alguno de los dos tiene una letra.
            {
                if (!libre(sig[0], sig[1]) || !libre(ant[0], ant[1])) {
                    //				todomal = True
                    //				break

                    // Defino las variables para no estar buscandolas
                    char actual = tablero[pos[1]][pos[0]];
                    char anterior = ' ';
                    char siguiente = ' ';
                    if (ant[0] > 0 && ant[1] > 0) {
                        anterior = tablero[ant[1]][ant[0]];
                    } else {
                        anterior = ' ';
                    }
                    if (sig[0] < tablero[y].length && sig[1] < tablero.length) {
                        siguiente = tablero[sig[1]][sig[0]];
                    } else {
                        siguiente = ' ';
                    }

                    boolean colocada = false;
                    // Hay que poner otra palabra.
                    for (Palabra nueva : lista) {
                        int par[] = new int[2];
                        int ubi = 0;
                        if (!libre(ant[0], ant[1])) {
                            par[0] = anterior;
                            par[1] = actual;
                            ubi = 0;
                        } else {
                            par[0] = actual;
                            par[1] = siguiente;
                            ubi = 1;
                            // Trato de ubicarla en algún lugar
                            for (int l = 0; l < nueva.getRespuesta().length() - 1; l++) {
                                // Llamada recursiva
                                if (nueva.getRespuesta().charAt(l) == par[0] && nueva.getRespuesta().charAt(l + 1) == par[1]) {
                                    if (direccion == HORIZONTAL) {
                                        if (colocarPalabra(nueva, pos[0], pos[1] - l - ubi, direccion * -1, lista)) {
                                            colocada = true;
                                            break;
                                        }
                                    } else if (colocarPalabra(nueva, pos[0] - l - ubi, pos[1], direccion * -1, lista)) {
                                        colocada = true;
                                        break;
                                    }
                                    // Si esta palabra anduvo, salgo del bucle
                                    if (colocada) {
                                        break;
                                    }
                                }
                                // Si ninguna palabra pudo ser colocada, hay que volver
                                // esta para atras.
                                if (!colocada) {
                                    lista.add(palabra);
                                    listasUsadas.remove(palabra);
                                    borrarPalabra(x, y, direccion, palabra.getRespuesta().length(), lista);
                                    return false;
                                }
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public List<Integer> eliminarRepetidos(List<Integer> lista) {
        List<Integer> listaNueva = new ArrayList<Integer>();
        for (int numero : lista) {
            if (!listaNueva.contains(numero)) {
                listaNueva.add(numero);
            }
        }
        return listaNueva;
    }
}
