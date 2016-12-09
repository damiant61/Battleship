package com.battleship.model;

public abstract class Player {

    protected Board offensive;
    protected Board defense;
    
    public Player() {
    }
    
    public void initialize(int sizeX, int sizeY) {
        this.offensive = new Board(sizeX, sizeY);
        this.defense = new Board(sizeX, sizeY);
    }

    public State fire(Player enemy, int x, int y) {
        return this.offensive.fire(enemy.getDefense(), x, y);
    }

    public void randomlyAddShips(int[] shipsToPlace) {
        this.defense.randomlyAddShips(shipsToPlace);
    }

    public boolean isDead() {
        return this.defense.isGameOver();
    }

    public boolean addShip(Ship ship) {
        return this.defense.addShip(ship);
    }

    public Board getDefense() {
        return this.defense;
    }

    public Board getOffensive() {
        return this.offensive;
    }

}
