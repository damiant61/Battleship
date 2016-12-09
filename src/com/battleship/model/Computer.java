package com.battleship.model;

public final class Computer extends Player {
    
    private static Computer instance = new Computer();
    
    private Computer() {
    }
    
    public void initialize(int sizeX, int sizeY, int[] shipsToPlace) {
        super.initialize(sizeX, sizeY);
        this.randomlyAddShips(shipsToPlace);
    }
    
    public static Computer getInstance() {
        return instance;
    }

    public Object[] makeMove(Player enemy) {
        int[][] probability = new int[offensive.getSizeX()][offensive.getSizeY()];
        Cell[][] board = offensive.getBoard();
        int max = 0, x = 0, y = 0;

        for (int i = 0; i < offensive.getSizeX(); i++) {
            for (int j = 0; j < offensive.getSizeY(); j++) {
                if (board[i][j].getState() != State.OCEAN || (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1].getState() == State.SHIP_HIT) || (i - 1 >= 0 && j + 1 < offensive.getSizeY() && board[i - 1][j + 1].getState() == State.SHIP_HIT)
                        || (i + 1 < offensive.getSizeX() && j - 1 >= 0 && board[i + 1][j - 1].getState() == State.SHIP_HIT) || (i + 1 < offensive.getSizeX() && j + 1 < offensive.getSizeY() && board[i + 1][j + 1].getState() == State.SHIP_HIT)) {
                    continue;
                }
                // Jeśli trafiono w sąsiednie pole
                if (i - 1 >= 0 && board[i - 1][j].getState() == State.SHIP_HIT) {
                    probability[i][j] += 100;
                }
                if (i + 1 < offensive.getSizeX() && board[i + 1][j].getState() == State.SHIP_HIT) {
                    probability[i][j] += 100;
                }
                if (j - 1 >= 0 && board[i][j - 1].getState() == State.SHIP_HIT) {
                    probability[i][j] += 100;
                }
                if (j + 1 < offensive.getSizeY() && board[i][j + 1].getState() == State.SHIP_HIT) {
                    probability[i][j] += 100;
                }
                // Możliwość ustawienia statku na polu (i, j)
                for (int k = 1; k <= 5; k++) {
                    if (offensive.isSpace(new Ship(k, i, j, true))) // Pionowo
                    {
                        for (int l = 0; l < k; l++) {
                            probability[i][j + l]++;
                        }
                    }
                    if (offensive.isSpace(new Ship(k, i, j, false))) // Poziomo
                    {
                        for (int l = 0; l < k; l++) {
                            probability[i + l][j]++;
                        }
                    }
                }
                // Wybieranie pola z największym prawdopodobieństwem
                if (max < probability[i][j]) {
                    max = probability[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        Object[] result = { fire(enemy, x, y), x, y };
        return result;
    }
}
