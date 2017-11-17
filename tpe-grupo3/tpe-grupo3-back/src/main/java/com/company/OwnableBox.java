package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Clase que representa un casillero que puede ser comprado. Contiene el dueño del casillero(si tiene), el precio del casillero
 * y el precio de renta si otro jugador cae en el casillero
 */
public class OwnableBox extends Box {

    private static final long serialVersionUID = 1L;

    private Player owner;
    private int price;
    private int rentPrice;
    /**
     * Coonstructor de la clase
     * @param boxName Nombre del casillero
     * @param price Precio del casillero
     * @param rentPrice Precio de renta del casillero, este es el precio que se substrae si otro jugador que no es
     *                  el dueño cae en el casillero.
     */
    public OwnableBox(String boxName, int price, int rentPrice) {
        super(boxName);
        this.owner = null;
        this.price = price;
        this.rentPrice = rentPrice;
        this.addButtons(ActionTypes.BUY);
    }
    /**
     * Devuelve el dueño del casillero
     * @return el jugador que es dueño del casillero
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Devuelve el precio del casillero
     * @return el precio del casillero
     */
    public int getPrice() {
        return price;
    }
    /**
     * Devuelve el precio de renta del casillero
     * @return el precio de renta del casillero
     */
    public int getRentPrice() {
        return rentPrice;
    }
    /**
     * Setea el precio de renta del casillero
     * @param rentPrice El precio de renta del casillero
     */
    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        StringBuffer aux = new StringBuffer(super.OnLandedAction(player));
        if (this.getOwner() == null) {
            aux.append(", " + this.getBoxName() + ", price: $" + this.getPrice());
            return aux.toString();
        } else if (!(this.getOwner().equals(player))) {
            aux.append("\n\t" + player.getPlayerName() + " pays: $" + this.getRentPrice() + " of rent to " + this.getOwner());
            this.payOwner(player);
            return aux.toString();
        } else { // yo soy el dueno
            return aux.toString();
        }
    }
    /**
     * Metodo para que el jugador que es pasado por parametro le pague al dueño de la propiedad.
     * @param player Jugador que paga el precio de renta de la propiedad
     * @throws InvalidPaymentException Si el jugador que paga no tiene dinero suficiente
     */
    protected void payOwner(Player player) throws InvalidPaymentException {
        if (owner.isBroke()){
            return;
        } else {
            try {
                player.subtractMoney(rentPrice);
            } catch (InvalidPaymentException exception) {
                owner.addMoney(player.getMoney()); // al owner le doy la plata que le queda al jugador
                player.subtractMoney(player.getMoney());
                throw new InvalidPaymentException("You are broke");
            }
            owner.addMoney(rentPrice);
        }
    }
    /**
     * Metodo para que el jugador compre el casillero
     * @param player Jugador que va a comprar el casillero
     * @return String que representa que el jugador compro el casillero.
     * @throws InvalidPaymentException Si el jugador no tiene dinero suficiente para comprar el casillero.
     */
    public String buyBox(Player player) throws InvalidPaymentException {
        try {
            player.subtractMoney(price);
            owner = player;
            this.getMyButtons().remove(ActionTypes.BUY);
            return "\t" + player.getPlayerName() + " has bought " + this.getBoxName();
        } catch (InvalidPaymentException exception) {
            throw new InvalidPaymentException("Not enough money to buy " + this.getBoxName());
        }
    }

    @Override
    protected void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(owner);
        out.writeInt(price);
        out.writeInt(rentPrice);
    }
    @Override
    protected void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        owner = (Player)ois.readObject();
        price = ois.readInt();
        rentPrice = ois.readInt();
    }
}
