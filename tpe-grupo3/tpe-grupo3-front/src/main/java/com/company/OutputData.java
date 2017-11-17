package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la información de los jugadores, casilleros a mostrar en pantalla.
 */
public class OutputData {

    private List<PlayerOut> playerOuts;
    private List<OwnableOut> boxOuts;
    private int currentTurn;

    /**
     * Constructor de la clase OutputData.
     * @param currentTurn representa el numero de jugador que esta jugando.
     */
    public OutputData(int currentTurn) {
        this.playerOuts = new ArrayList<PlayerOut>();
        this.boxOuts = new ArrayList<OwnableOut>();
        this.currentTurn = currentTurn;
    }
    /**
     * Metodo para modificar la lista de jugadores.
     * @param playerOuts lista con informacion de los jugadores.
     */
    public void setPlayerOuts(List<PlayerOut> playerOuts) {
        this.playerOuts = playerOuts;
    }
    /**
     * Metodo para modificar la lista de casilleros.
     * @param propertyOuts lista con informacion de las propiedades.
     */
    public void setPropertyOuts(List<OwnableOut> propertyOuts) {
        this.boxOuts = propertyOuts;
    }
    /**
     * Método para obtener la lista con informacion de los jugadores.
     * @return lista con informacion de los jugadores
     */
    public List<PlayerOut> getPlayerOuts() {
        return playerOuts;
    }

    public List<OwnableOut> getPropertyOuts() {
        return boxOuts;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}