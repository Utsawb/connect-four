package dev.utsawb;

import java.util.ArrayList;

public class Board {
    private int rows;
    private int cols;

    private ArrayList<ArrayList<Character>> board;

    Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new ArrayList<ArrayList<Character>>();
        for (int r = 0; r < rows; ++r) {
            board.add(new ArrayList<Character>());
            for (int c = 0; c < cols; ++c) {
                board.get(r).add('0');
            }
        }
    }

    public char getTile(int row, int col) {
        return board.get(row).get(col);
    }
    public void setTile(int row, int col, char value) {
        board.get(row).set(col, value);
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
}