package com.company;

import java.util.Random;
/**
 * Clase que representa al casillero Carta del Monopoly.
 */
public class Card extends Box {

    private static final long serialVersionUID = 1L;
    /**
     * Enumerativo nested con los valores de las cartas de la suerte, y con la acción asociada de aplicarle
     * la carta al jugador.
     */
    public enum Luck {

        PAY1(-150),
        PAY2(-200),
        PAY3(-300),
        PAY4(-450),
        TAKE1(500),
        TAKE2(300),
        TAKE3(150),
        TAKE4(200);
        /**
         * Accion de aplicarle la carta de suerte al jugador
         * @param player Jugador al que se le aplica la carta
         * @throws InvalidPaymentException Si al tocarle una carta de substraccion de dinero
         *  el jugador no tiene el dinero suficiente.
         */
        public void action(Player player) throws InvalidPaymentException {
            if (money < 0) {
                try {
                    player.subtractMoney(-money);
                } catch (InvalidPaymentException exception) {
                    player.setMoney(0);
                }
            } else {
                player.addMoney(money);
            }
        }  /**
         * Constructor del enumerativo
         * @param money Dinero actual del jugador.
         */
        private Luck(int money) {
            this.money = money;
        }
        /**
         * Retorna el dinero de la carta.
         * @return  dinero actual de la carta.
         */
        public int getMoney() {
            return money;
        }

        private int money;
    }
    /**
     * Constructor de la clase Card.
     */
    public Card(){
        super("Lucky card");
    }

    @Override
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        Random i = new Random();
        int number = i.nextInt(7) + 1;
        Luck luckCard = Luck.values()[number];
        if (luckCard.getMoney() < 0) {
            luckCard.action(player);
            return super.OnLandedAction(player) + ", picks a lucky card, " + " pays " + Math.abs(luckCard.getMoney());
        } else {
            luckCard.action(player);
            return super.OnLandedAction(player) + ", picks a lucky card, " + " takes " + Math.abs(luckCard.getMoney());
        }
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Card))
            return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
