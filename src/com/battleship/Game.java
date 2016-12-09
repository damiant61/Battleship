package com.battleship;

import com.battleship.model.GameModel;
import com.battleship.view.GameFrame;
import javax.swing.SwingUtilities;

public class Game implements Runnable {

    @Override
    public void run() {
        new GameFrame();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
}
