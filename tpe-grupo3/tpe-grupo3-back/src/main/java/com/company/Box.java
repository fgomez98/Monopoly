package com.company;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase que representa un casillero del tablero de Monopoly. Posee un nombre y una lista de botones
 * que dependera de las acciones del casillero.
 */
public class Box implements Serializable{  // es necesaria la descripcion??

    private static final long serialVersionUID = 1L;
    private List<ActionTypes> myButtons;
    private String boxName;

    /**
     * Constructor de la clase Box.
     * @param boxName Nombre del casillero.
     */
    Box(String boxName) {
        this.boxName = boxName;
        myButtons = new ArrayList<ActionTypes>();
        myButtons.add(ActionTypes.NEXTPLAY);
    }
    /**
     * MÈtodo que se activa cuando un jugador cae sobre este casillero.
     * @param player Jugador que cae en el casillero.
     * @return DescripciÛn de la acciÛn realizada por el casillero.
     * @throws InvalidPaymentException En el caso que el jugador deba pagar en el casillero
     * y no tenga dinero suficiente.
     */
    public String OnLandedAction(Player player) throws InvalidPaymentException {
        return player.getPlayerName() + " landed on " + this.getBoxName();
    }
    /**
     * Metodo para obtener el nombre del casillero.
     * @return Nombre del casillero.
     */
    public String getBoxName() {
        return boxName;
    }
    /**
     * Metodo para obtener los botones asociados al casillero.
     * @return Lista de botones asociados al casillero.
     */
    public List<ActionTypes> getMyButtons() {
        return myButtons;
    }
    /**
     * Agrega un boton a la lista de botones del casillero.
     * @param button Boton a agregar.
     */
    public void addButtons(ActionTypes button) {
        this.myButtons.add(button.ordinal(), button);
    }
    /**
     * Guarda el objeto en un archivo. Se utiliza en la serializaci√≥n.
     *
     * @param out Stream por donde se escribir√°n las variables del objeto.
     * @throws IOException En el caso de que haya un error al escribir en el archivo.
     */
    protected void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(myButtons);
        out.writeUTF(boxName);
    }

    protected void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        myButtons = (List<ActionTypes>)ois.readObject();
        boxName = ois.readUTF();
    }

    @Override
    public String toString() {
        return boxName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Box))
            return false;
        Box other = (Box)obj;
        return boxName.equals(other.boxName);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*31 + boxName.hashCode();
        return hash;
    }

}
