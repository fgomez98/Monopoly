package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.control.Alert;

public class Monopoly extends Application {

    private Stage window;
    private GameState gameState;
    private List<Button> buttons = new LinkedList<Button>();
    private Button buyBox = new Button("BUY");
    private Button addHouse = new Button("ADD HOUSE");
    private Button nextPlay = new Button("NEXT PLAYER");
    private Button move = new Button("ROLL DICE AND MOVE");
    private Button startGame = new Button("START GAME");
    private TextField inputPlayersAmountField = new TextField();
    private TextField inputMoneyAmountFiled = new TextField();
    private Label label1_Amount = new Label("Insert amount of players");
    private Label label1_Money = new Label("Insert money");
    private VBox layout1 = new VBox(20);
    private Scene addPlayersScene = new Scene(layout1, 200, 250);
    private VBox layout2 = new VBox(20);
    private SplitPane verticalSplitPane = new SplitPane();
    private Scene gameScene;
    private TextArea outputConsole = new TextArea();
    private BoardPanel board;
    private Button save = new Button("SAVE GAME");
    private Button load = new Button("LOAD GAME");
    private FileChooser fileChooser = new FileChooser();

    public void disableButtons() {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }

    public void enableOperativeButtons() {
        List<ActionTypes> availableButtonsButtons = gameState.availableButtons();
        this.disableButtons();
        for (ActionTypes button : availableButtonsButtons) {
            buttons.get(button.ordinal()).setDisable(false);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        outputConsole.setEditable(false);
        window.setScene(addPlayersScene);
        window.setTitle("Monopoly");
        verticalSplitPane.setOrientation(Orientation.HORIZONTAL);
        inputPlayersAmountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        inputMoneyAmountFiled.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        layout1.getChildren().addAll(label1_Amount, inputPlayersAmountField, label1_Money, inputMoneyAmountFiled, startGame, load);
        layout2.getChildren().addAll(buyBox, addHouse, move, nextPlay, save, outputConsole);
        buttons.add(ActionTypes.ADDHOUSE.ordinal(), addHouse);
        buttons.add(ActionTypes.BUY.ordinal(), buyBox);
        buttons.add(ActionTypes.NEXTPLAY.ordinal(), nextPlay);
        buttons.add(ActionTypes.MOVE.ordinal(), move);
        buttons.add(ActionTypes.STARTGAME.ordinal(), startGame);
        buttons.add(save);
        buttons.add(load);
        outputConsole.setPrefSize(400,400);
        layout2.setAlignment(Pos.CENTER);

        for (Button button : buttons) {
            button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setEffect(new DropShadow());
                }
            });
            button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setEffect(null);
                }
            });
        }

        buttons.remove(save);
        buttons.remove(load);

        addHouse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == addHouse) {
                    try {
                        outputConsole.appendText(gameState.addHouse());
                        board.updateCanvas(gameState.getOutputData());
                    } catch (InvalidPaymentException exception) {
                        new Alert(Alert.AlertType.WARNING, exception.getMessage()).show();
                    }
                    enableOperativeButtons();
                }
            }
        });

        buyBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == buyBox) {
                    try {
                        outputConsole.appendText(gameState.buyBox());
                        board.updateCanvas(gameState.getOutputData());
                    } catch (InvalidPaymentException exception) {
                        new Alert(Alert.AlertType.WARNING, exception.getMessage()).show();
                    }
                    enableOperativeButtons();
                }
            }
        });

        nextPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == nextPlay) {
                    try {
                        disableButtons();
                        gameState.nextPlay();
                        move.setDisable(false);
                        save.setDisable(false);
                        board.updateCanvas(gameState.getOutputData());
                    } catch (GameException exception) {
                        new Alert(Alert.AlertType.INFORMATION, exception.getMessage()).show();
                    }
                }
            }
        });

        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == move) {
                    try {
                        outputConsole.appendText(gameState.rollDice_and_MovePLayer());
                        board.updateCanvas(gameState.getOutputData());
                        enableOperativeButtons();
                        save.setDisable(true);
                    } catch (InvalidPaymentException exception) {
                        new Alert(Alert.AlertType.WARNING, exception.getMessage()).show();
                    }
                }
            }
        });

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == startGame) {
                    try {
                        int playersAmount = Integer.parseInt(inputPlayersAmountField.getText());
                        int moneyAmount = Integer.parseInt(inputMoneyAmountFiled.getText());
                        gameState = new GameState(playersAmount, moneyAmount);
                        board = new BoardPanel(playersAmount, moneyAmount);
                        verticalSplitPane.getItems().addAll(board, layout2);
                        gameScene = new Scene(verticalSplitPane,1200,600);
                        window.setScene(gameScene);
                        disableButtons();
                        move.setDisable(false);
                    } catch (IllegalArgumentException exception) {
                        new Alert(Alert.AlertType.WARNING, exception.getMessage()).show();
                    }
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showSaveDialog(window);
                if (file != null) {
                    try {
                        saveData(file);
                        new Alert(Alert.AlertType.INFORMATION, "Información guardada con éxito.").show();
                    } catch (FileNotFoundException ex) {
                        new Alert(Alert.AlertType.ERROR, "Error al encontrar el archivo.").show();
                    } catch (IOException ex) {
                        new Alert(Alert.AlertType.ERROR, "Error al guardar el archivo.").show();
                    }
                }
            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(window);
                if (file != null) {
                    try {
                        loadData(file);
                        board = new BoardPanel(gameState.getOutputData());
                        verticalSplitPane.getItems().addAll(board, layout2);
                        gameScene = new Scene(verticalSplitPane,1200,600);
                        window.setScene(gameScene);
                        disableButtons();
                        move.setDisable(false);
                        new Alert(Alert.AlertType.INFORMATION, "Información cargada con éxito.").show();
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, "Error al cargar el archivo.").show();
                    }
                }
            }
        });
        window.show();
    }

    private void saveData(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        gameState.saveBoard(out);
    }

    private void loadData(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        gameState = new GameState(ois);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
