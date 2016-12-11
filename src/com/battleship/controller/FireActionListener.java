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

    private PlayerPanel computer;
    private PlayerPanel human;
    private GameModel model;
    private GamePanel gamePanel;

    public FireActionListener(PlayerPanel human, PlayerPanel computer, GamePanel gamePanel, GameModel model) {
        this.computer = computer;
        this.human = human;
        this.model = model;
        this.gamePanel = gamePanel;
        this.addObserver(human);
        this.addObserver(computer);
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

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();

        if (humanPlayer.getPlacedShips()) {

            if (humanPlayer.fire(computerPlayer, i, j) == State.SHIP_MISS) {
                Object[] result = new Object[3];
                do {
                    result = computerPlayer.makeMove(humanPlayer);
                    setChanged();
                    notifyObservers(new Object[]{false, new Point((int) result[1], (int) result[2])});
                } while (result[0] == State.SHIP_HIT || result[0] == State.SHIP_DEAD && !humanPlayer.isDead());
            }

            if (humanPlayer.isDead()) {
                //initEndGame(false);
            } else if (computerPlayer.isDead()) {
                //initEndGame(true);
            }

            model.changePlayer();
            setChanged();
            notifyObservers(new Object[]{true, new Point(i, j)});
            model.changePlayer();
        }
    }

}
