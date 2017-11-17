package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Clase que representa un jugador de Monopoly.
 */
public class Player implements Serializable {

    private int trains;
    private int position;
    private String playerName;
    private int money;
    private int jailTime;
    private HashMap<Colour, List<Property>> properties;
    /**
     * Constructor de la clase Player.
     *
     * @param money      Dinero con el que comienza.
     * @param playerNumber Nombre de jugador.
     */
    public Player(int money, String playerNumber) {
        this.position = 0;
        this.playerName = playerNumber;
        this.money = money;
        this.jailTime = 0;
        this.trains = 0;
        properties = new HashMap<Colour, List<Property>>();
    }
    /**
     * Método para obtener el número de trenes.
     *
     * @return Número de trenes que posee el jugador.
     */
    public int getTrains() {
        return trains;
    }
    /**
     * Método para agregarle al jugador un tren.
     */
    public void addTrain() {
        trains++;
    }
    /**
     * Método para obtener la posición del jugador en el tablero.
     *
     * @return La posición del jugador.
     */
    public int getPosition() {
        return position;
    }
    /**
     * Setea la posición del jugador en el tablero.
     *
     * @param position La nueva posición del jugador.
     */
    public void setPosition(int position) {
        this.position = position;
    }
    /**
     * Método para saber si el jugador está en bancarrota.
     *
     * @return Estado de bancarrota del jugador. True si está en bancarrota, false sino.
     */
    public boolean isBroke() {
        return money <= 0;
    }
    /**
     * Método para obtener el dinero que posee el jugador.
     *
     * @return Dinero del jugador.
     */
    public int getMoney() {
        return money;
    }
    /**
     * Agrega al jugador una determinada cantidad de dinero.
     *
     * @param money Cantidad de dinero a agregar.
     */
    public void addMoney(int money) {
        this.money += money;
    }
    /**
     * Quita una determinada cantidad de dinero al jugador.
     *
     * @param money Dinero a quitar del jugador.
     * @throws InvalidPaymentException Se arroja cuando el jugador no tiene dinero suficiente para extraerle.
     */
    public void subtractMoney(int money) throws InvalidPaymentException {
        if (this.money < money) { // no le alcanza la plata
            throw new InvalidPaymentException("Not enough money");
        }
        this.money -= money;
    }
    /**
     * Método para determinar si el jugador está en la carcel o no.
     *
     * @return Devuelve true si el jugador está en la cárcel, false sino.
     */
    public boolean isJailTime() {
        return jailTime > 0;
    }
    /**
     * Envía a la carcel al jugador por 3 turnos.
     */
    public void setJailTime() {
        this.jailTime = 3;
    }
    /**
     * Agrega una propiedad al jugador.
     *
     * @param property Propiedad que se agregará al jugador.
     */
    public void addProperty(Property property) {
        if (properties.containsKey(property.getColour())) {
            properties.get(property.getColour()).add(property);
        } else {
            ArrayList<Property> aux = new ArrayList<Property>();
            aux.add(property);
            properties.put(property.getColour(), aux);
        }
    }
    /**
     * Método para obtener la lista de propiedades en posesión del jugador.
     *
     * @return Lista de propiedades.
     */
    public HashMap<Colour, List<Property>> getProperties() {
        return properties;
    }
    /**
     * Guarda el objeto en un archivo. Se utiliza en la serialización.
     *
     * @param out Stream por donde se escribirán las variables del objeto.
     * @throws IOException En el caso de que haya un error al escribir en el archivo.
     */
    private void writeObject(ObjectOutputStream out) throws IOException { // falta guardar la plata
        out.defaultWriteObject();
        out.writeInt(trains);
        out.writeInt(position);
        out.writeUTF(playerName);
        out.writeInt(jailTime);
        out.writeObject(properties);
    }
    /**
     * Lee el objeto serializado desde un archivo.
     *
     * @param ois Stream por donde se lee el archivo,
     * @throws IOException            En el caso que haya un error al leer el archivo.
     * @throws ClassNotFoundException En el caso que no se encuentre la clase en el archivo.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException { // falta leer la plata
        ois.defaultReadObject();
        trains = ois.readInt();
        position = ois.readInt();
        playerName = ois.readUTF();
        jailTime = ois.readInt();
        properties = (HashMap<Colour, List<Property>>) ois.readObject();
    }

    @Override
    public String toString() {
        return this.getPlayerName();
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void spendTurnInJail() {
        jailTime--;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        if (trains != player.trains) {
            return false;
        } if (position != player.position) {
            return false;
        } if (money != player.money) {
            return false;
        } if (jailTime != player.jailTime){
            return false;
        } if (playerName != null ? !playerName.equals(player.playerName) : player.playerName != null) {
            return false;
        }
        return (properties != null ? properties.equals(player.properties) : player.properties == null);
    }

    @Override
    public int hashCode() {
        int result = trains;
        result = 31 * result + position;
        result = 31 * result + (playerName != null ? playerName.hashCode() : 0);
        result = 31 * result + money;
        result = 31 * result + jailTime;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
