package com.company;
/**
 * Clase que representa el casillero de ir a la carcel.
 */
public class GoToJail extends Box {

    private static final long serialVersionUID = 1L;

    public GoToJail() {
        super("Go to Jail");
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        player.setPosition(BoardGame.JAIL_BOX);
        player.setJailTime();
        return player.toString() + " goes to jail, misses 3 turns";
    }

}
