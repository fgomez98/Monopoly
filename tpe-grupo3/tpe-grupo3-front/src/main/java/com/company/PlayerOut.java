package com.company;

/**
 * Clase que representa la información actual de un jugador
 */
public class PlayerOut {

    private int position;
    private int money;
    private boolean onJail;

    /**
     * Constructor de la clase
     *
     * @param position posicion actual del jugador
     * @param money    dinero actual del jugador
     * @param onJail   boolean para representar si esta en la carcel o no
     */
    public PlayerOut(int position, int money, boolean onJail) {
        this.position = position;
        this.money = money;
        this.onJail = onJail;
    }

    /**
     * Devuelve la posicion del jugador
     *
     * @return posicion actual del jugador
     */
    public int getPosition() {
        return position;
    }

    /**
     * Devuelve el dinero actual del jugador
     *
     * @return dinero actual del jugador
     */
    public int getMoney() {
        return money;
    }

    /**
     * Devuelve un boolean que representa si esta o no en la carcel el jugador
     *
     * @return boolean con la informacion si esta o no en la carcel.
     */
    public boolean isOnJail() {
        return onJail;
    }
}