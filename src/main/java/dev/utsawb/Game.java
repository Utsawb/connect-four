package dev.utsawb;

import java.util.Random;

public class Game {
    private Board board;

    private char colorPlayer;
    private char colorAI;

    private char moveCount;

    public static Random r;

    Game(int rows, int cols, String colorPlayer, String colorAI) {
        board = new Board(rows, cols);
        r = new Random();
        this.colorPlayer = colorPlayer.charAt(0);
        this.colorAI = colorAI.charAt(0);
    }

    public void clear() {
        for (int row = 0; row < board.getRows(); ++row) {
            for (int col = 0; col < board.getCols(); ++col) {
                board.setTile(row, col, '0');
            }
        }
        moveCount = 0;
    }

    public char gameFinish() {
        if (moveCount == (board.getRows() * board.getCols())) {
            return 'D';
        }


        for (int col = 0; col < board.getCols(); ++col) {
            for (int row = 0; row <= board.getRows() - 4; ++row) {
                char currentChar = board.getTile(row, col);
                if (currentChar != '0' &&
                    currentChar == board.getTile(row + 1, col) &&
                    currentChar == board.getTile(row + 2, col) &&
                    currentChar == board.getTile(row + 3, col)) {
                    return currentChar;
                }
            }
        }
    
        for (int row = 0; row < board.getRows(); ++row) {
            for (int col = 0; col <= board.getCols() - 4; ++col) {
                char currentChar = board.getTile(row, col);
                if (currentChar != '0' &&
                    currentChar == board.getTile(row, col + 1) &&
                    currentChar == board.getTile(row, col + 2) &&
                    currentChar == board.getTile(row, col + 3)) {
                    return currentChar;
                }
            }
        }
    
        for (int row = 0; row <= board.getRows() - 4; ++row) {
            for (int col = 0; col <= board.getCols() - 4; ++col) {
                char currentChar = board.getTile(row, col);
                if (currentChar != '0' &&
                    currentChar == board.getTile(row + 1, col + 1) &&
                    currentChar == board.getTile(row + 2, col + 2) &&
                    currentChar == board.getTile(row + 3, col + 3)) {
                    return currentChar;
                }
            }
        }
    
        for (int row = 0; row <= board.getRows() - 4; ++row) {
            for (int col = 3; col < board.getCols(); ++col) {
                char currentChar = board.getTile(row, col);
                if (currentChar != '0' &&
                    currentChar == board.getTile(row + 1, col - 1) &&
                    currentChar == board.getTile(row + 2, col - 2) &&
                    currentChar == board.getTile(row + 3, col - 3)) {
                    return currentChar;
                }
            }
        }
    
        return '0';
    }

    private int dropRow(int col) {
        for (int r = board.getRows() - 1; r >= 0; --r) {
            if (board.getTile(r, col) == '0') {
                return r;
            }
        }
        return -1;
    }

    private boolean isValid(int col) {
        return board.getTile(0, col) == '0';
    }

    public boolean userInput(int row, int col) {
        if (!isValid(col)) {
            return false;
        }

        board.setTile(dropRow(col), col, colorPlayer);
        ++moveCount;
        return true;
    }

    public void aiInput(char type) {
        if (type == 'S') {
            int bestScore = 1 << 31;
            int bestCol = -1;

            for (int col = 0; col < board.getCols(); col++) {
                if (isValid(col)) {
                    int row = dropRow(col);
                    board.setTile(row, col, colorAI);
                    int score = minimax(10, 1 << 31, 1 << 31 - 1, false);
                    board.setTile(row, col, '0');

                    if (score > bestScore) {
                        bestScore = score;
                        bestCol = col;
                    }
                }
            }

            board.setTile(dropRow(bestCol), bestCol, colorAI);
            ++moveCount;
        } else if (type == 'R') {
            Random random = new Random();
            int col;
        
            do {
                col = random.nextInt(board.getCols());
            } while (!isValid(col));
        
            board.setTile(dropRow(col), col, colorAI);
            ++moveCount;
        }

    }

    // https://www.youtube.com/watch?v=l-hh51ncgDI
    // This video saved me
    private int minimax(int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        char winner = gameFinish();
        if (winner != '0') {
            return winner == colorAI ? 100 : -100;
        }

        if (depth == 0) {
            return 0;
        }

        if (isMaximizingPlayer) {
            int bestScore = 1 << 31;
            for (int col = 0; col < board.getCols(); col++) {
                if (isValid(col)) {
                    int row = dropRow(col);
                    board.setTile(row, col, colorAI);
                    int score = minimax(depth - 1, alpha, beta, false);
                    alpha = score > alpha ? score : alpha;
                    board.setTile(row, col, '0');
                    bestScore = bestScore > score ? bestScore : score;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1 << 31 - 1;
            for (int col = 0; col < board.getCols(); col++) {
                if (isValid(col)) {
                    int row = dropRow(col);
                    board.setTile(row, col, colorPlayer);
                    int score = minimax(depth - 1, alpha, beta, true);
                    beta = score < beta ? score : beta;
                    board.setTile(row, col, '0');
                    bestScore = bestScore < score ? bestScore : score;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
    }

    public Board getBoard() {
        return board;
    }

    public String toString() {
        return board.toString();
    }
}
