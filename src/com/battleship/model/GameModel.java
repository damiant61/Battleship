package com.battleship.model;

public class GameModel {
    
    private static GameModel instance = new GameModel();
    
    private Human human;
    private Computer computer;
    
    private GameModel(){
    }
    
    public void initialize(int sizeX, int sizeY, boolean PlacedShips, int[] shipsToPlace){
        this.human = Human.getInstance();
        this.human.initialize(sizeX, sizeY, PlacedShips, shipsToPlace);
        this.computer = Computer.getInstance();
        this.computer.initialize(sizeX, sizeY, shipsToPlace);
    }
    
    public static GameModel getInstance() {
        return instance;
    }

    public Human getHuman() {
        return human;
    }

    public Computer getComputer() {
        return computer;
    }
    
}
