package com.company;

/**
 * Clase que representa la informacion actual de una propiedad.
 */
public class PropertyOut extends OwnableOut {
    int houses;

    /**
     * Constructor de la clase que contiene la cantidad de casas que se tienen compradas en la propiedad y
     * la posicion.
     * @param houses Cantidad de casas en la propiedad
     * @param position Posicion del casillero.
     */
    public PropertyOut(int houses, int position) {
        super(position);
        this.houses = houses;
    }

    /**
     * Constructor de la clase que contiene la cantidad de casas de la propiedad, la posicion
     * y el actual dueño.
     * @param houses Cantidad de casas en la propiedad.
     * @param position Posicion actual de la propiedad.
     * @param owner Dueño actual de la propiedad
     */
    public PropertyOut(int houses, int position, int owner) {
        super(position, owner);
        this.houses = houses;
    }

    /**
     * Devuelve la cantidad de casas de la propiedad
     * @return la cantidad de casas de la propiedad
     */
    public int getHouses() {
        return houses;
    }
}