package com.company;

import java.io.Serializable;
import java.util.Random;
/**
 * Clase que representa un dado.
 */

public class Dice implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int MAX = 6;

    public Dice() {
    }
    /**
     * Representa la tirada de un dado y devuelve un numero entre 1 y el MAX.
     * @return entero con el numero que salio en el dado.
     */
    public int getNumber(){
        Random i = new Random();
        return i.nextInt(MAX) + 1;
    }
}
