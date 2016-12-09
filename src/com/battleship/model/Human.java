package com.battleship.model;

public final class Human extends Player {
    
    private static Human instance = new Human();

    private boolean placedShips;
    private int[] shipsToPlace;
    
    private Human() {
    }
    
    public void initialize(int sizeX, int sizeY, boolean placedShips, int[] shipsToPlace) {
        super.initialize(sizeX, sizeY);
        this.placedShips = placedShips;
        this.shipsToPlace = shipsToPlace;
        if (this.placedShips) {
            this.randomlyAddShips(shipsToPlace);
            this.placedShips = true;
        }
    }
    
    public static Human getInstance() {
        return instance;
    }

    public int getShip(int i) {
        return this.shipsToPlace[i];
    }

    public void addShip(int i) {
        this.shipsToPlace[i]--;
        this.placedShips = true;
        for (int k : shipsToPlace) {
            if (k != 0) {
                this.placedShips = false;
                break;
            }
        }
    }

    public boolean getPlacedShips() {
        return this.placedShips;
    }

}
