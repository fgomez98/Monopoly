package com.company;
/**
 * Clase que representa el casillero que contiene un Tren para comprar.
 */
public class Train extends OwnableBox {

    private static final long serialVersionUID = 1L;
    /**
     * Constructor de la clase
     * @param trainName Nombre del tren del casillero.
     */
    Train(String trainName) {
        super(trainName, 200, 25);
    }

    @Override
    public String buyBox(Player player) throws InvalidPaymentException {
        String out = super.buyBox(player);
        player.addTrain();
        return out;
    }

    @Override
    public int getRentPrice() {
        return super.getRentPrice()*this.getOwner().getTrains();
    }

}
