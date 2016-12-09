package com.battleship.model;

public enum State {
    
    OCEAN(false),
    SHIP_UNIT(false),
    PROTECTED_FIELD(false),
    SHIP_MISS(true),
    SHIP_HIT(true),
    SHIP_DEAD(true);
    
    boolean fired;
    
    private State(boolean fired) {
        this.fired = fired;
    }
    
    public boolean isFired() {
        return fired;
    }
    
}
