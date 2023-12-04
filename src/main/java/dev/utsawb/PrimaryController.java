package dev.utsawb;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PrimaryController {
    private static Game g;
    private ArrayList<Character> moves;
    public char ai;

    private EventHandler<MouseEvent> hoverEnter = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            VBox v = (VBox) e.getSource();
            v.setStyle("-fx-background-color: #0E79FF;");
        } 
    };

    private EventHandler<MouseEvent> hoverExit = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            VBox v = (VBox) e.getSource();
            v.setStyle("-fx-background-color: #0E79B2;");
        }
    };

    private EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            String id = e.getSource().toString();
            int column = (int) (id.charAt(id.length() - 2) - '0' - 1);

            if (g.userInput(0, column) == true && App.winner == '0')
            {
                title.setText("Connect Four");
                moves.add((char) column);
                updateGui();
                if (g.gameFinish() != '0') {
                    App.winner = g.gameFinish();
                    if (App.winner == 'Y') {
                        title.setText("Yellow Wins");
                    } else if (App.winner == 'R') {
                        title.setText("Red Wins");
                    } else {
                        title.setText("Draw");
                    }
                }
                g.aiInput(ai);
                updateGui();
                if (g.gameFinish() != '0') {
                    App.winner = g.gameFinish();
                    if (App.winner == 'Y') {
                        title.setText("Yellow Wins");
                    } else if (App.winner == 'R') {
                        title.setText("Red Wins");
                    } else {
                        title.setText("Draw");
                    }
                }
            }
            else {
                title.setText(App.winner != '0' ? "Game has ended please reset!" : "You can't place a token there");
            }

        }
    };
    
    private EventHandler<MouseEvent> loadPressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            g.clear();
            moves.clear();
            String file_path = "./saved.dat";
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(file_path));
                moves = (ArrayList<Character>) o.readObject();
                o.close();
                for (Character c : moves) {
                    g.userInput(0, (char) c);
                    g.aiInput(ai);
                }
            } catch (Exception e1) {
                updateGui();
            }
            updateGui();
        }
    };

    private EventHandler<MouseEvent> resetPressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            App.winner = '0';
            g.clear();
            moves.clear();
            title.setText("Connect Four");
            updateGui();
        }
    };


    private EventHandler<MouseEvent> savePressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            String file_path = "./saved.dat";
            try {
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file_path));
                o.writeObject(moves);
                o.close();
            } catch (Exception e1) {
            }
        }
    };

    private EventHandler<MouseEvent> aiTypePressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            if (ai == 'S') {
                ai = 'R';
                aiType.setText("Random AI");
            } else if (ai == 'R') {
                ai = 'S';
                aiType.setText("Smart AI");
            }
        }
    };

    private void updateGui()
    {
        for (int i = 0; i < board.getChildren().size(); i++)
        {
            VBox v = (VBox) board.getChildren().get(i);
            for (int j = 0; j < v.getChildren().size(); j++)
            {
                char current = g.getBoard().getTile(j, i);
                if (current == 'Y')
                {
                    Circle c = (Circle) v.getChildren().get(j);
                    c.setFill(Paint.valueOf("#F7B32B"));
                }
                else if (current == 'R')
                {
                    Circle c = (Circle) v.getChildren().get(j);
                    c.setFill(Paint.valueOf("#F72C25"));
                }
                else if (current == '0')
                {
                    Circle c = (Circle) v.getChildren().get(j);
                    c.setFill(Paint.valueOf("#FBFEF9"));
                }
            }
        }
    }


    @FXML
    HBox board;

    @FXML
    Button load;

    @FXML
    Button reset;

    @FXML
    Button save;

    @FXML
    Button aiType;

    @FXML
    Label title;
    
    @FXML
    public void initialize() {
        g = new Game(6, 7, "Yellow", "Red");
        moves = new ArrayList<Character>();
        ai = 'S';

        load.setOnMouseClicked(loadPressed);
        reset.setOnMouseClicked(resetPressed);
        save.setOnMouseClicked(savePressed);
        aiType.setOnMouseClicked(aiTypePressed);

        ObservableList<Node> columns = board.getChildren();
        for (Node n : columns)
        {
            n.setOnMouseEntered(hoverEnter);
            n.setOnMouseExited(hoverExit);
            n.setOnMouseClicked(clicked);
        }
    }
}
