package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Clase utilizada para representar el estado del juego en un momento dado. Contiene el tablero,
 * el jugador que debe jugar y el casillero donde se est· jugando.
 */
public class GameState {

    BoardGame board;
    Player currentPlayer;
    Box currentBox;
    boolean has_play; // si el currentPlayer hizo una jugada o no
    /**
     * Constructor de la clase GameState.
     * @param amount Cantidad de jugadores.
     * @param initial_money	Cantidad inicial de dinero.
     * @throws IllegalArgumentException	En el caso que se ingresen numeros invalidos.
     */
    public GameState(int amount, int initial_money) throws IllegalArgumentException  {
        this.board = new BoardGame();
        if (amount < 2 || initial_money < 2000 || amount > 8 || initial_money > 5000) {
            throw new IllegalArgumentException("Re-enter values:\n2-8 players\t$2000 min, $50000 max");
        }
        this.addPlayer(amount, initial_money);
        has_play = false;
        this.currentPlayer = board.nowPlaying();
        this.currentBox = board.getBoxes().get(currentPlayer.getPosition());
    }
    /**
     * Constructor de la clase GameState a partir de un tablero guardado.
     * @param ois Stream donde se leera el tablero guardado.
     * @throws ClassNotFoundException En el caso que no se encuentre la clase del tablero.
     * @throws IOException En el caso que haya un problema al acceder al archivo.
     */
    public GameState(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        loadBoard(ois);
        this.currentPlayer = board.nowPlaying();
        this.currentBox = board.getBoxes().get(currentPlayer.getPosition());
        has_play=false;
    }
    /**
     * Arroja el dado y mueve al jugador que le toca jugar.
     * @return Descripcion de la accion del casillero donde cayo el jugador.
     * @throws InvalidPaymentException En el caso que el jugador no tenga suficiente dinero para pagar.
     */
    public String rollDice_and_MovePLayer() throws InvalidPaymentException {
        currentBox = board.movePlayer(currentPlayer);
        try {
            has_play = true;
            return (currentBox.OnLandedAction(currentPlayer) + '\n');
        } catch (InvalidPaymentException exception) {
            throw new InvalidPaymentException(exception.getMessage());
        }
    }
    /**
     * Compra el casillero el jugador que esta jugando.
     * @return Descripcion de la compra.
     * @throws InvalidPaymentException En el caso que el jugador no tenga dinero suficiente para comprarla.
     */
    public String buyBox() throws InvalidPaymentException {
        try {
            return (((OwnableBox)currentBox).buyBox(currentPlayer) + '\n');
        } catch (InvalidPaymentException exception) {
            throw new InvalidPaymentException(exception.getMessage());
        }
    }
    /**
     * AÒade una casa al casillero donde se est· jugando.
     * @return Descripcion de la compra.
     * @throws InvalidPaymentException En el caso que el jugador no tenga dinero suficiente para comprarla.
     */
    public String addHouse() throws InvalidPaymentException {
        try {
            return (((Property)currentBox).buyHouse(currentPlayer) + '\n');
        } catch (InvalidPaymentException exception) {
            throw new InvalidPaymentException(exception.getMessage());
        }
    }
    /**
     * Juega el proximo turno.
     * @throws GameException En el caso de que el juego termine a causa de un ganador.
     */
    public void nextPlay() throws GameException {
        if (has_play) {
            if (board.isWinner()) {
                throw new GameException(board.getWinner().toString() + " has win!!");
            }
            board.nextTurn();
            currentPlayer = board.nowPlaying();
            currentBox = board.getBoxes().get(currentPlayer.getPosition());
            has_play = false;
        } else {
            throw new GameException(currentPlayer.toString() + " needs to throw dice first");
        }
    }
    /**
     * Obtiene los datos que se deberan imprimir en pantalla dependiendo del estado del juego.
     * @return Datos a imprimir en pantalla.
     */

    public OutputData getOutputData() {
        OutputData out = new OutputData(board.getTurn()+1);
        out.setPlayerOuts(this.playerStates());
        out.setPropertyOuts(this.propertyState());
        return out;
    }
    /**
     * Obtiene el estado de cada jugador para imprimirlos en pantalla.
     * @return Lista de los estados de cada jugador.
     */

    private List<PlayerOut> playerStates() {
        List<PlayerOut> playerOuts = new LinkedList<PlayerOut>();
        for (Player player : board.getPlayers()) {
            playerOuts.add(new PlayerOut(player.getPosition(), player.getMoney(), player.isJailTime()));
        }
        return playerOuts;
    }
    /**
     * Obtiene el estado de cada casillero Ownable para imprimir en pantalla.
     * @return Lista de los estados de cada casillero.
     */
    private List<OwnableOut> propertyState() {
        List<OwnableOut> propertyOuts = new ArrayList<OwnableOut>();
        for (Integer i : BoardGame.PROPERTY_POSITIONS) {
            propertyOuts.add(new PropertyOut(((Property) board.getBoxes().get(i)).getHouses(), i, board.getPlayers().indexOf(((Property) board.getBoxes().get(i)).getOwner())+1));
        }
        OwnableBox aux;
        for (int i = 5; i < board.BOXES; i+=10) {
            aux = (OwnableBox)board.getBoxes().get(i);
            propertyOuts.add(new OwnableOut(i, board.getPlayers().indexOf(aux.getOwner())+1));
        }
        aux = (OwnableBox)board.getBoxes().get(12);
        propertyOuts.add(new OwnableOut(12, board.getPlayers().indexOf(aux.getOwner())+1));
        aux = (OwnableBox)board.getBoxes().get(28);
        propertyOuts.add(new OwnableOut(28, board.getPlayers().indexOf(aux.getOwner())+1));
        return propertyOuts;
    }
    /**
     * Verifica si hay un ganador.
     * @return Verdadero si hay ganador, Falso sino
     */

    public boolean hasWinner() {
        return board.isWinner();
    }
    /**
     * Obtiene el jugador ganador.
     * @return Jugador ganador.
     */
    public Player getWinner() {
        return board.getWinner();
    }
    /**
     * AÒade jugadores a la partida.
     * @param amount Cantidad de jugadores a agregar.
     * @param initial_money Cantidad inicial de dinero.
     */
    private void addPlayer(int amount, int initial_money) {
        board.addPlayers(amount, initial_money);
    }
    /**
     * Obtiene la lista con los botones asociados al casillero donde se esta jugando.
     * @return Lista con botones del casillero.
     */
    public List<ActionTypes> availableButtons() { // ver lo de sacar buy button en ownable
        return currentBox.getMyButtons();
    }
    /**
     * Guarda el tablero serializado en un archivo.
     * @param out Stream por donde se guardara el archivo.
     * @throws IOException En el caso que haya un error al acceder al archivo.
     */
    public void saveBoard(ObjectOutputStream out) throws IOException {
        out.writeObject(board);
        out.close();
    }
    /**
     * Guarda el tablero serializado en un archivo.
     * @param ois Stream por donde se guardara el archivo.
     * @throws IOException En el caso que haya un error al acceder al archivo.
     */
    public void loadBoard(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        board = (BoardGame) ois.readObject();
        ois.close();
    }

    @Override
    public String toString() {
        return "Current player: " + currentPlayer + "\nCurrent box: " + currentBox;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof GameState))
            return false;
        GameState other = (GameState)obj;
        return (board.equals(other.board)
                && currentPlayer.equals(other.currentPlayer)
                && currentBox.equals(other.currentBox));
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*31 + board.hashCode();
        hash = hash*31 + currentPlayer.hashCode();
        hash = hash*31 + currentBox.hashCode();
        return hash;
    }


}
