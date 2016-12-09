package com.battleship.model;

import com.battleship.model.ships.AircraftCarrier;
import com.battleship.model.ships.Battleship;
import com.battleship.model.ships.Cruiser;
import com.battleship.model.ships.Destroyer;
import com.battleship.model.ships.Submarine;
import java.util.LinkedList;
import java.util.Random;

public class Board {

    private LinkedList<Ship> ship;
    private Cell[][] board;
    private int sizeX, sizeY;
    private int hit, miss, shipUnit;

    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.hit = 0;
        this.miss = 0;
        this.shipUnit = 0;
        this.board = new Cell[this.sizeX][this.sizeY];
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.board[i][j] = new Cell();
            }
        }
        
        this.ship = new LinkedList<Ship>();
    }

    public boolean isSpace(Ship ship) {
        int[][] mast = ship.getMast();

        // Czy żaden maszt nie wychodzi poza pole bitwy
        if (ship.getVertical() && mast[0][1] + ship.getSize() > sizeY) {
            return false;
        } else if (!ship.getVertical() && mast[0][0] + ship.getSize() > sizeX) {
            return false;
        }

        // Czy jest miejsce na statek + na jedno pole odstępu
        if (ship.getVertical()) {
            for (int i = mast[0][1]; i < mast[0][1] + ship.getSize() && i < sizeY; i++) {
                if (i < 0) {
                    continue;
                }
                for (int j = mast[0][0]; j < mast[0][0] + 1 && j < sizeX; j++) {
                    if (j < 0) {
                        continue;
                    }
                    if (board[j][i].getState() != State.OCEAN) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = mast[0][0]; i < mast[0][0] + ship.getSize() && i < sizeX; i++) {
                if (i < 0) {
                    continue;
                }
                for (int j = mast[0][1]; j < mast[0][1] + 1 && j < sizeY; j++) {
                    if (j < 0) {
                        continue;
                    }
                    if (board[i][j].getState() != State.OCEAN) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean addShip(Ship ship) {
        if (isSpace(ship)) {
            int[][] mast = ship.getMast();
            for (int i = 0; i < ship.getSize(); i++) {
                board[mast[i][0]][mast[i][1]].setState(State.SHIP_UNIT);
                shipUnit++;
                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        if (mast[i][0] + j < sizeX && mast[i][0] + j >= 0 && mast[i][1] + k < sizeY && mast[i][1] + k >= 0 && board[mast[i][0] + j][mast[i][1] + k].getState() == State.OCEAN) {
                            board[mast[i][0] + j][mast[i][1] + k].setState(State.PROTECTED_FIELD);
                        }
                    }
                }
            }
            this.ship.addLast(ship);
        } else {
            return false;
        }

        return true;
    }

    public void randomlyAddShips(int shipsToPlace[]) {
        int x, y;
        boolean vertical;
        Random random = new Random();

        int r[] = new int[shipsToPlace.length];
        for (int i = 0; i < shipsToPlace.length; i++) {
            r[i] = shipsToPlace[i];
        }
        int counter = 0;
        do {
            x = random.nextInt(this.sizeX);
            y = random.nextInt(this.sizeY);
            if (random.nextDouble() > 0.5) {
                vertical = true;
            } else {
                vertical = false;
            }
            if (r[0] > 0 && this.addShip(new AircraftCarrier(x, y, vertical))) {
                r[0]--;
            } else if (r[0] <= 0 && r[1] > 0 && this.addShip(new Battleship(x, y, vertical))) {
                r[1]--;
            } else if (r[0] + r[1] <= 0 && r[2] > 0 && this.addShip(new Cruiser(x, y, vertical))) {
                r[2]--;
            } else if (r[0] + r[1] + r[2] <= 0 && r[3] > 0 && this.addShip(new Destroyer(x, y, vertical))) {
                r[3]--;
            } else if (r[0] + r[1] + r[2] + r[3] <= 0 && r[4] > 0 && this.addShip(new Submarine(x, y, vertical))) {
                r[4]--;
            }
            if (counter++ > 1000) {
                break;
            }
        } while (r[0] + r[1] + r[2] + r[3] + r[4] > 0);
    }

    public State fire(Board enemy, int x, int y) {
        switch (enemy.board[x][y].getState()) {

            case PROTECTED_FIELD:
            case OCEAN:
                this.miss++;
                this.board[x][y].setState(State.SHIP_MISS);
                enemy.board[x][y].setState(State.SHIP_MISS);
                return State.SHIP_MISS;

            case SHIP_UNIT:
                this.hit++;
                // Znajduję statek odpowiadający polu (x, y) na liście.
                int index = 0;
                this.board[x][y].setState(State.SHIP_HIT);
                enemy.board[x][y].setState(State.SHIP_HIT);
                for (int i = 0; i < enemy.ship.size(); i++) {
                    int mast[][] = enemy.ship.get(i).getMast();
                    for (int j = 0; j < enemy.ship.get(i).getSize(); j++) {
                        if (mast[j][0] == x && mast[j][1] == y) {
                            index = i;
                            break;
                        }
                    }
                }

                enemy.ship.get(index).hit();

                // Jeśli statek został zatopiony zmieniam jego reprezentacje w tablicy
                if (enemy.ship.get(index).isSunk()) {
                    int mast[][] = enemy.ship.get(index).getMast();
                    for (int i = 0; i < enemy.ship.get(index).getSize(); i++) {
                        this.board[mast[i][0]][mast[i][1]].setState(State.SHIP_DEAD);
                        enemy.board[mast[i][0]][mast[i][1]].setState(State.SHIP_DEAD);
                        for (int j = -1; j <= 1; j++) {
                            for (int k = -1; k <= 1; k++) {
                                if (mast[i][0] + j < sizeX && mast[i][0] + j >= 0 && mast[i][1] + k < sizeY && mast[i][1] + k >= 0 && board[mast[i][0] + j][mast[i][1] + k].getState() == State.OCEAN) {
                                    board[mast[i][0] + j][mast[i][1] + k].setState(State.PROTECTED_FIELD);
                                }
                            }
                        }
                    }
                    return State.SHIP_DEAD;
                } else {
                    return State.SHIP_HIT;
                }

            default:
                return null;
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < ship.size(); i++) {
            if (!ship.get(i).isSunk()) {
                return false;
            }
        }
        return true;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public int getHit() {
        return this.hit;
    }

    public int getMiss() {
        return this.miss;
    }

    public int getShipUnit() {
        return this.shipUnit;
    }

    public LinkedList<Ship> getShip() {
        return this.ship;
    }

}
