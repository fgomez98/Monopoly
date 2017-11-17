package com.company;

/**
 * Clase para representar la información actual de un elemento que se puede comprar. Contiene la posicion del
 * elemento en el tablero y el dueño.
 */
public class OwnableOut {

    private int position;
    private int owner;

    /**
     * Constructor de la clase
     * @param position posicion de el elemento
     */
    public OwnableOut(int position) {
        this.position = position;
        this.owner = 0;
    }

    /**
     * Constructor de la clase
     * @param position posicion del elemento
     * @param owner actual dueño del elemento
     */
    public OwnableOut(int position, int owner) {
        this.position = position;
        this.owner = owner;
    }

    /**
     * Devuelve la posicion del elemento
     * @return posicion del elemento
     */
    public int getPosition() {
        return position;
    }

    /**
     * Devuelve el dueño del elemento
     * @return el dueño del elemento
     */
    public int getOwner() {
        return owner;
    }
}