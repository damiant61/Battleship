package com.battleship.model;

public class Ship {

    protected int size;
    protected int health;
    protected boolean vertical;
    protected int[][] mast;

    public Ship() {
    }

    public Ship(int size, int x, int y, boolean vertical) {
        this.size = size;
        this.health = size;
        this.vertical = vertical;

        this.mast = new int[size][2];

        for (int i = 0; i < size; i++) {
            if (vertical) {
                mast[i][0] = x;
                mast[i][1] = y++;
            } else {
                mast[i][0] = x++;
                mast[i][1] = y;
            }
        }
    }

    public void hit() {
        this.health--;
    }

    public boolean isSunk() {
        if (this.health <= 0) {
            return true;
        }
        return false;
    }

    public int[][] getMast() {
        return this.mast;
    }

    public int getHealth() {
        return this.health;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getVertical() {
        return this.vertical;
    }

}
