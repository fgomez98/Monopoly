package com.company;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Clase que crea la vista del tablero del juego, con la descripción de cada jugador
 * y el dinero que tiene disponible. Tiene una escala para mostrar el tamaño,
 * las dimenciones básicas son 800x797.
 */
public class BoardPanel extends VBox {

	private static final double SCALE = 0.75;
	private static final int ANCHO_FICHA = 50;
	private static final int ALTO_FICHA = 50;
	private static final int ANCHO_CASILLA = 65;
    private static final int CENTER_SPOT = ANCHO_FICHA/3;
	private static final int ALTO_CASAS = 717-690;
	private static final int ANCHO_CASAS = 500-435;
	private static final int ANCHO_TABLERO = 585;
	private static final Image BOARD_IMAGE = new Image("tablero.png");
	private GraphicsContext graphicsContext;
	private Font myFont = new Font(SCALE*16);
	/**
	 * Constructor de la clase
	 * @param playerAmount Cantidad de jugadores en el tablero
	 * @param money Dinero inicial de los jugadores
	 */
	public BoardPanel(int playerAmount, int money) {
		 Canvas canvas = new Canvas(SCALE*800, SCALE*797);
		 graphicsContext = canvas.getGraphicsContext2D();
		 graphicsContext.drawImage(BOARD_IMAGE, 0, 0, SCALE*800, SCALE*797);
		 graphicsContext.setLineWidth(2);
		 this.drawPlayerToken(1, 0, false);
		 this.drawScoreboard(1, money, true);
		 for (int i=2; i <= playerAmount; i++)
         {
		 	this.drawPlayerToken(i, 0, false);
		 	this.drawScoreboard(i, money, false);
		 }
		 getChildren().addAll(canvas);
	}
	/**
	 * Constructor para crear el tablero mediante una data guardada.
	 * @param data Data para iniciar el tablero.
	 */
	public BoardPanel(OutputData data) {
		Canvas canvas = new Canvas(SCALE*800, SCALE*797);
		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.drawImage(BOARD_IMAGE, 0, 0, SCALE*800, SCALE*797);
		graphicsContext.setLineWidth(2);
		drawAllPlayers(data.getPlayerOuts(), data.getCurrentTurn());
		drawAllHouses(data.getPropertyOuts());
		getChildren().addAll(canvas);
	}
	/**
	 * Actualiza la vista del tablero con la nueva data.
	 * @param data Nueva data para actualizar la vista del tablero.
	 */
	public void updateCanvas(OutputData data) {
		graphicsContext.clearRect(0, 0, 800*SCALE, 797*SCALE);
		graphicsContext.drawImage(BOARD_IMAGE, 0, 0, SCALE*800, SCALE*797);
		drawAllPlayers(data.getPlayerOuts(), data.getCurrentTurn());
		drawAllHouses(data.getPropertyOuts());
	}

	private double getX(int playerNumber, int position) {
		position = position % 40;
		double x;
		if (position == 0 || position > 30)
			x = 725;
		else if (position < 10)
			x = 115 + (10 - position%20 -1)*ANCHO_CASILLA;
		else if (position == 10)
		{
			if (playerNumber == 3)
				x = -16.5;
			else
				x = 0;
		}
		else if (position < 20)
			x = 20;
		else if (position == 20)
			x = 30;
		else if (position < 30)
			x = 115 + ((position % 20) -1)*ANCHO_CASILLA;
		else
			x = 57;
		return x;
	}

	private double getY(int playerNumber, int position) {
		position = position %40;
		double y;
		if (position < 10)
			y = 725;
		else if (position == 10)
		{
			if (playerNumber == 3)
				y = 763.5;
			else
				y = 747;
		}
		else if (position < 20)
			y = 115 + (10 - position%10 - 1)*ANCHO_CASILLA;
		else if (position == 20)
			y = 30;
		else if (position < 30)
			y = 20;
		else if(position > 30)
			y = 115 + (position%30 -1)*ANCHO_CASILLA;
		else
			y = 700;
		return y;
	}

	private void drawPlayerToken(int playerNumber, int position, boolean onJail) {
		if (onJail) {
			graphicsContext.drawImage(new Image("Ficha" + playerNumber + ".png"), SCALE * (57), SCALE * (700), SCALE * ANCHO_FICHA, SCALE *  ALTO_FICHA);
		} else {
			double x = getX(playerNumber, position);
			double y = getY(playerNumber, position);
			graphicsContext.drawImage(new Image("Ficha" + playerNumber + ".png"), SCALE * (x), SCALE * (y), SCALE * ANCHO_FICHA, SCALE * ALTO_FICHA);
		}
	}

	private void drawHouses(OwnableOut property) {
		int position = property.getPosition() % 40;
		double x, y;
		if(position < 10) {
			x = 108 + (10 - position -1)*ANCHO_CASILLA;
			y = 693;
		} else if(position < 20) {
			x = 84;
			y = 106 + (10 -position%10 -1)*ANCHO_CASILLA;
		} else if(position < 30) {
			x = 108 + (position % 20 -1)*ANCHO_CASILLA;
			y = 83;
		} else {
			x = 694;
			y = 106 + (position % 20 -11)*ANCHO_CASILLA;
		} if (property instanceof PropertyOut) {
			if(position % 20 < 10)
				graphicsContext.drawImage(new Image(((PropertyOut)property).getHouses() +"HouseH.png"), SCALE*(x), SCALE*(y), SCALE*ANCHO_CASAS, SCALE*ALTO_CASAS);
			else
				graphicsContext.drawImage(new Image(((PropertyOut)property).getHouses() +"HouseV.png"), SCALE*(x), SCALE*(y), SCALE*ALTO_CASAS, SCALE*ANCHO_CASAS);
		}
	}

	private void drawAllHouses(List<OwnableOut> propertyOuts) {
	    for (OwnableOut p: propertyOuts) {
            drawHouses(p);
            graphicsContext.setLineWidth(1);
            drawPropertyOwner(p);

	    }
    }

    private Paint playerColor(int playerNumber) {
        Paint color = Color.BLACK;
        switch(playerNumber) {
            case 1: color = Color.CRIMSON;
                break;
            case 2: color = Color.DEEPSKYBLUE;
                break;
            case 3: color = Color.YELLOW;
                break;
            case 4: color = Color.LIGHTGREEN;
                break;
            case 5: color = Color.DARKGRAY;
                break;
            case 6: color = Color.WHITESMOKE;
                break;
            case 7: color = Color.ORANGE;
                break;
            case 8: color = Color.PINK;
                break;
        }
        return color;
    }

    private void drawScoreboard(int playerNumber, int money, boolean nowPlaying) {
        double x = 123 + (playerNumber-1)%4*146.25;
        x *= SCALE;
        double y = 130;
        if (playerNumber > 4)
            y += 490;
        y *= SCALE;
        Paint color = playerColor(playerNumber);
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, SCALE*120, SCALE*60);
        graphicsContext.strokeRoundRect(x, y, SCALE*120, SCALE*60, 5, 5);
        if (nowPlaying) {
            graphicsContext.setFill(Color.MAGENTA);
        } else {
            graphicsContext.setFill(Color.BLACK);
        }
        graphicsContext.setFont(myFont);
        graphicsContext.fillText("Player " + playerNumber, x+SCALE*32, y+SCALE*20);
        graphicsContext.fillText("$ " + money, x+SCALE*35, y+SCALE*40);
    }

	private void drawAllPlayers(List<PlayerOut> list, int turn) {
		int i = 1;
		for (PlayerOut player: list)
		{
			drawPlayerToken(i, player.getPosition(), player.isOnJail());
			if ( turn == i) {
                drawScoreboard(i, player.getMoney(), true);
            } else {
                drawScoreboard(i, player.getMoney(), false);
            }
			i++;
		}
	}

    private void drawPropertyOwner(OwnableOut p) {
        if(p.getOwner() != 0) {
            graphicsContext.setFill(playerColor(p.getOwner()));
            graphicsContext.fillOval((getX(1, p.getPosition())+CENTER_SPOT+2)*SCALE, (getY(1, p.getPosition())+CENTER_SPOT+2)*SCALE, (CENTER_SPOT-2)*SCALE, (CENTER_SPOT-2)*SCALE);
            graphicsContext.strokeOval((getX(1, p.getPosition())+CENTER_SPOT+2)*SCALE, (getY(1, p.getPosition())+CENTER_SPOT+2)*SCALE, (CENTER_SPOT-2)*SCALE, (CENTER_SPOT-2)*SCALE);
        }
    }

}