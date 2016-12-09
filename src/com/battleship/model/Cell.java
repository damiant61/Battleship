package com.battleship.model;

public class Cell {
    
    private State state = State.OCEAN;
    
    public Cell(){
    }
    
    public Cell(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
}
