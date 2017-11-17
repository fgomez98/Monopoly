package com.company;

/**
 * Enumerativo con los posibles colores de los casilleros y los precios base de las casas y propiedades.
 */
public enum Colour {

    BROWN(50, 60),
    LIGHT_BLUE(50, 100),
    PINK(100, 140),
    ORANGE(100, 180),
    RED(150, 220),
    YELLOW(150, 260),
    GREEN(200, 300),
    BLUE(200, 350);

    private int housePrice;
    private int propertyPrice;
    /**
     * Constructor del enumerativo que setea las variables del precio de la propiedades y las casas.
     * @param housePrice Precio de la casa
     * @param propertyPrice Precio de las propiedades
     */
    private Colour(int housePrice, int propertyPrice) {
        this.housePrice = housePrice;
        this.propertyPrice = propertyPrice;
    }
    /**
     * Devuelve el precio de la propiedad
     * @return precio de la propiedad
     */
    public int getPropertyPrice() {
        return propertyPrice;
    }
    /**
     * Devuelve el precio de la casa
     * @return precio de la casa.
     */
    public int getHousePrice() {
        return housePrice;
    }

}
