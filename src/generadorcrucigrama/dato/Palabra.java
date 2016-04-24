/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcrucigrama.dato;

import java.util.Objects;

/**
 * Hola como estan todos
 *
 * @author Uwar Navia Cari
 * @version 1.0
 */
public class Palabra {

    private int numero;
    private int x;
    private int y;
    private int direccion;
    private String enunciado;
    private String respuesta;

    /**
     * La clase Palabra es la estructura que definira una palabra dentro del
     * crucigrama
     *
     */
    public Palabra() {
        enunciado="";
        respuesta="";
    }

    /**
     *
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     *
     * @param numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        String dir;
        switch (direccion) {
            case 1:
                dir = "Horizontal";
                break;
            case -1:
                dir = "Vertical";
                break;
            default:
                dir = "Desconocido";
                break;
        }
        return "numero: " + numero + " - x: " + x + " - y: "
                + y + " - Enunciado: " + enunciado + " - Respuesta: "
                + respuesta + " - Direccion: " + dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Palabra) {
            Palabra p = (Palabra) obj;
            return p.enunciado.equalsIgnoreCase(enunciado) && p.respuesta.equalsIgnoreCase(respuesta);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.enunciado);
        hash = 61 * hash + Objects.hashCode(this.respuesta);
        return hash;
    }

}
