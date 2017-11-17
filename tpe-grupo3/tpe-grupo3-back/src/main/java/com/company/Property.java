package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa una propiedad del Monopoly. Las propiedades excluyen los trenes y las utilidades, son únicamente las propiedades en las que
 * se pueden construir casas. Cada propiedad pertenece a un determinado color y tiene una lista de precios de renta que depende de la cantidad de casas.
 */
public class Property extends OwnableBox implements Serializable {

    private static final long serialVersionUID = 1L;
    private Colour colour;
    private int houses;
    private static int MAX_HOUSES = 5;
    private ArrayList<Integer> prices;
    /**
     * Constructor de la clase Property.
     * @param prices Arreglo con los precios de la propiedad.
     * @param propertyName Nombre de la propiedad.
     * @param colour Color al que pertenece la propiedad.
     */
    public Property(ArrayList<Integer> prices, String propertyName, Colour colour) {
        super(propertyName, colour.getPropertyPrice() ,prices.get(0));
        this.colour = colour;
        this.prices = new ArrayList<Integer>(5);
        this.prices = prices;
        houses = 0;
    }
    /**
     * Método para obtener el color al que pertenece la propiedad.
     * @return Color de la propiedad.
     */
    public Colour getColour() {
        return colour;
    }
    /**
     * Compra y edifica una casa en la propiedad.
     * @throws InvalidPaymentException Cuando el jugador no tiene dinero suficiente para edificar una casa.
     */
    public String buyHouse(Player player) throws InvalidPaymentException { // compra casa y propiedad a la ves (addHouse cuando no tiene dueno) esta comprando 2 casas
        try {
            getOwner().subtractMoney(colour.getHousePrice());
            houses++;
            super.setRentPrice(prices.get(houses));
            if (houses == MAX_HOUSES) {
                this.getMyButtons().remove(ActionTypes.ADDHOUSE);
            }
            return "\t" + player.toString() + " has bought a house in " + this.getBoxName() + " the rent price now is " + this.getRentPrice();
        } catch (InvalidPaymentException exception) {
            throw new InvalidPaymentException("not enough money to buy house");
        }
    }
    /**
     * Método para checkear si el dueño de la propiedad tiene un monopolio sobre el color de la propiedad, si lo tiene habilita
     * la compra de casas sino, no
     */
    private void checkMonopoly() {
        if (this.partOfMonopoly()) {
            this.getMyButtons().add(ActionTypes.ADDHOUSE);
        }
    }
    /**
     * Método para obtener el número de casas edificadas.
     * @return Número de casas edificadas.
     */
    public int getHouses() {
        return houses;
    }
    /**
     * Guarda el objeto en un archivo. Se utiliza en la serialización.
     * @param out Stream por donde se escribirán las variables del objeto.
     * @throws IOException En el caso de que haya un error al escribir en el archivo.
     */
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(colour);
        out.writeInt(houses);
        out.writeObject(prices);
    }
    /**
     * Lee el objeto serializado desde un archivo.
     * @param ois Stream por donde se lee el archivo,
     * @throws IOException En el caso que haya un error al leer el archivo.
     * @throws ClassNotFoundException En el caso que no se encuentre la clase en el archivo.
     */
    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        colour = (Colour) ois.readObject();
        houses = ois.readInt();
        prices = (ArrayList<Integer>)ois.readObject();
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        StringBuffer aux = new StringBuffer(player.getPlayerName() + " landed in " + this.getBoxName());
        if (this.getOwner() == null) {
            aux.append( "\n\t" + "Price: $" + this.getPrice() + ", Rent price: " + this.getRentPrice() + ", House price: " + this.prices.get(houses));
            return aux.toString();
        } else if ((!this.getOwner().equals(player))) {
            aux.append("\n\t" + player.getPlayerName() + " pays $" + this.getRentPrice() + " of rent to " + this.getOwner());
            this.payOwner(player);
            this.getMyButtons().remove(ActionTypes.ADDHOUSE);
            return aux.toString();
        } else { // soy el dueno
            this.checkMonopoly();
            if (houses == MAX_HOUSES) {
                this.getMyButtons().remove(ActionTypes.ADDHOUSE);
            }
            aux.append("\n\t" + "Rent price: $" + this.getRentPrice() + ", House price: " + colour.getHousePrice());
            return aux.toString();
        }
    }
    @Override
    public String buyBox(Player player) throws InvalidPaymentException {
        String out = super.buyBox(player);
        player.addProperty(this);
        checkMonopoly();
        return out;
    }
    public boolean partOfMonopoly() {
        return getOwner().getProperties().get(colour).size() == 3;
    }
}
