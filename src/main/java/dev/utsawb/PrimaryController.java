package dev.utsawb;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
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

    private EventHandler<MouseEvent> hover_enter = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            VBox v = (VBox) e.getSource();
            v.setStyle("-fx-background-color: #0E79FF;");
        } 
    };

    private EventHandler<MouseEvent> hover_exit = new EventHandler<MouseEvent>() { 
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
                moves.add((char) column);
                update_gui();
                if (g.game_finish() != '0') {
                    App.winner= g.game_finish();
                    title.setText(App.winner == 'Y' ? "Yellow Wins" : "Red Wins");
                }
                g.aiInput();
                update_gui();
                if (g.game_finish() != '0') {
                    App.winner= g.game_finish();
                    title.setText(App.winner == 'Y' ? "Yellow Wins" : "Red Wins");
                }
            }

        }
    };
    
    private EventHandler<MouseEvent> load_pressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            g.clear();
            moves.clear();
            String file_path = "./saved.dat";
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(file_path));
                moves = (ArrayList<Character>) o.readObject();
                for (Character c : moves)
                {
                    g.userInput(0, (char) c);
                    g.aiInput();
                }
            } catch (Exception e1) {
                update_gui();
            }
            update_gui();
        }
    };

    private EventHandler<MouseEvent> reset_pressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            App.winner = '0';
            g.clear();
            moves.clear();
            title.setText("Connect Four");
            update_gui();
        }
    };


    private EventHandler<MouseEvent> save_pressed = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) {
            String file_path = "./saved.dat";
            try {
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file_path));
                o.writeObject(moves);
            } catch (Exception e1) {
            }
        }
    };

    private void update_gui()
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
    Label title;
    
    @FXML
    public void initialize() {
        g = new Game(6, 7, "Yellow", "Red");

        moves = new ArrayList<Character>();

        load.setOnMouseClicked(load_pressed);
        reset.setOnMouseClicked(reset_pressed);
        save.setOnMouseClicked(save_pressed);

        ObservableList<Node> columns = board.getChildren();
        for (Node n : columns)
        {
            n.setOnMouseEntered(hover_enter);
            n.setOnMouseExited(hover_exit);
            n.setOnMouseClicked(clicked);
        }
    }
}
