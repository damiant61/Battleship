package com.battleship.controller;

import com.battleship.model.Computer;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.State;
import com.battleship.view.GamePanel;
import com.battleship.view.PlayerPanel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class FireActionListener extends Observable implements ActionListener {

    private PlayerPanel panel;
    private GameModel model;

    public FireActionListener(PlayerPanel panel, GamePanel gamePanel, GameModel model) {
        this.panel = panel;
        this.model = model;
        this.addObserver(panel);
        this.addObserver(gamePanel);
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int i = Integer.parseInt(e.getActionCommand().split(" ")[0]);
        int j = Integer.parseInt(e.getActionCommand().split(" ")[1]);
       // if (placeShips != null) {
        //    placeShips.setVisible(false);
       // }
        
        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();
        

        if (humanPlayer.fire(computerPlayer, i, j) == State.SHIP_MISS) {
            Object[] result = new Object[3];
            do {
                result = computerPlayer.makeMove(humanPlayer);
             //   update(this.defensiveFields, result[1], result[2], false);
                notifyObservers(new Point((int)result[1], (int)result[2]));
            } while (result[0] == State.SHIP_HIT || result[0] == State.SHIP_DEAD && !humanPlayer.isDead());
        }
     //   update(this.offensiveFields, i, j, true);
        if (humanPlayer.isDead()) {
            //initEndGame(false);
        } else if (computerPlayer.isDead()) {
            //initEndGame(true);
        }
        
        setChanged();
        notifyObservers(new Point(i, j));
    }

}
