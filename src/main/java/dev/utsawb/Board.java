package dev.utsawb;

import java.util.ArrayList;

public class Board {
    private int rows;
    private int cols;

    private ArrayList<Character> board;

    Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new ArrayList<Character>(rows * cols);
        for (int i = 0; i < rows * cols; ++i) {
            board.add('0');
        }
    }

    public char getTile(int row, int col) {
        return board.get(col + row * cols);
    }
    public char getTile(int i) {
        return board.get(i);
    }
    public void setTile(int row, int col, char value) {
        board.set(col + row * cols, value);
    }
    public void setTile(int i, char value) {
        board.set(i, value);
    }

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
}