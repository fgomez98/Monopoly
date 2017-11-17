package com.company;
/**
 * Clase que representa el casillero de carcel.
 */

public class Jail extends Box {

    private static final long serialVersionUID = 1L;

    public Jail() {
        super("Jail");
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        return player.getPlayerName() + " takes a tour on " + this.getBoxName();
    }
}
