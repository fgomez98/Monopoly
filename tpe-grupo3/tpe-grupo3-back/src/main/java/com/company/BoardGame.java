package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Clase que representa el tablero del Monopoly junto con los dados. Contiene la lista con los
 * casilleros junto con una lista de jugadores.
 */
public class BoardGame implements Serializable  {

    private final static Integer[] CARD_POSITIONS = {2, 7, 10, 17, 22, 33, 38};
    public final static Integer[] PROPERTY_POSITIONS = {1, 3, 4, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 36, 37, 39};
    public static int JAIL_BOX = 10;
    private static int GO_MONEY = 200;
    private static int GOTO_JAIL_BOX = 30;
    public static int BOXES = 40;
    private List<Player> players = new ArrayList<Player>();
    private List<Box> boxes = new ArrayList<Box>(BOXES);
    private int turn = 0;
    private Dice dice = new Dice();
    /**
     * Constructor de la clase BoardGame. Setea todas los casilleros del tablero.
     */
    public BoardGame() {
        for (int i = 0; i < BOXES ; i++) {
            boxes.add(i, null);
        }
        boxes.set(0, new StartBox());
        boxes.set(20, new Holiday());
        for (Integer i: CARD_POSITIONS) {
            boxes.set(i , new Card());
        }
        boxes.set(JAIL_BOX ,new Jail());
        boxes.set(GOTO_JAIL_BOX, new GoToJail());
        boxes.set(5, new Train("Metrobus del Bajo"));
        boxes.set(15, new Train("Estacion L.Alem"));
        boxes.set(25, new Train("Estacion Retiro"));
        boxes.set(35, new Train("Terminal de Combies Madero"));
        boxes.set(12,new Industry("Fotocopiadora la Copia", 1000, 500));
        boxes.set(28, new Industry("EatBar", 2500, 1000));
        int j = 0;
        for (DefaultProperties p: DefaultProperties.values()) {
            boxes.set(PROPERTY_POSITIONS[j], p.getProperty());
            j++;
        }
    }
    /**
     * Agrega los jugadores al juego.
     * @param amount Cantidad de jugadores.
     * @param initial_money Cantidad inicial de dinero.
     */
    public void addPlayers(int amount, int initial_money) {
        for (int i = 1; i <=amount  ; i++) {
            players.add(new Player(initial_money, "Player " +i));
        }
    }
    /**
     * MÈtodo para obtener el jugador que le toca jugar.
     * @return Jugador que debe jugar.
     */
    public Player nowPlaying() {
        return players.get(turn);
    }
    /**
     * MÈtodo para obtener la lista de jugadores.
     * @return Lista de jugadores
     */
    public List<Player> getPlayers() {
        return players;
    }
    /**
     * MÈtodo para obtener la lista de casilleros, es decir, el tablero.
     * @return La lista de casilleros.
     */
    public List<Box> getBoxes() {
        return boxes;
    }
    /**
     * Avanza al siguiente turno.
     * @return Numero de jugador al que le toca jugar.
     */
    public void nextTurn() {
        turn = (turn + 1) % players.size();
        if (this.nowPlaying().isJailTime()) {
            nowPlaying().spendTurnInJail();
            this.nextTurn();
        } else if (this.nowPlaying().isBroke()) {
            this.nextTurn();
        }
    }
    /**
     * Verifica si hay un ganador.
     * @return Verdadero si hay un ganador, falso sino.
     */
    public boolean isWinner() {
        int i = 0;
        for (Player player : players) {
            if (!player.isBroke()) {
                i++;
            }
        }
        return i <= 1;
    }
    /**
     * MÈtodo para obtener el jugador que gano el juego.
     * @return Jugador ganador.
     */
    public Player getWinner() {
        if (isWinner()) {
            for (Player player : players) {
                if (!player.isBroke()) {
                    return player;
                }
            }
        }
        return null;
    }
    /**
     * Mueve al jugador a una casilla determinada por el resultado de los dados.
     * @param player Jugador a mover.
     * @return Casilla donde cayÛ el jugador.
     */
    public Box movePlayer(Player player) {
        int finalLocation = player.getPosition() + dice.getNumber();
        if(finalLocation >= BOXES) {
            finalLocation = finalLocation % BOXES;
            player.addMoney(GO_MONEY);
        }
        player.setPosition(finalLocation);
        return boxes.get(finalLocation);
    }
    /**
     * MÈtodo para obtener el n˙mero del jugador al que le toca jugar.
     * @return N˙mero del jugador.
     */
    public int getTurn() {
        return turn;
    }
    /**
     * Guarda el objeto en un archivo. Se utiliza en la serializaciÛn.
     * @param out Stream por donde se escribir·n las variables del objeto.
     * @throws IOException En el caso de que haya un error al escribir en el archivo.
     */
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(players);
        out.writeObject(boxes);
        out.writeInt(turn);
    }
    /**
     * Lee el objeto serializado desde un archivo.
     *
     * @param ois Stream por donde se lee el archivo,
     * @throws IOException            En el caso que haya un error al leer el archivo.
     * @throws ClassNotFoundException En el caso que no se encuentre la clase en el archivo.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        players = (ArrayList<Player>)ois.readObject();
        boxes = (ArrayList<Box>)ois.readObject();
        turn = ois.readInt();
    }
    @Override
    public String toString() {
        return players.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof BoardGame))
            return false;
        BoardGame other = (BoardGame)obj;
        return (players.equals(other.players)
                && boxes.equals(other.boxes));
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*31 + players.hashCode();
        hash = hash*31 + boxes.hashCode();
        return hash;
    }


}