package com.company;
/**
 * Clase que representa el casillero de Vacaciones.
 */
public class Holiday extends Box {

    private static final long serialVersionUID = 1L;

    public Holiday() {
        super("Holiday");
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        return super.OnLandedAction(player) + ", takes a holiday";
    }

}
