package com.battleship.controller;

import com.battleship.model.GameModel;
import com.battleship.view.EndGameDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class EndGameActionListener extends Observable implements ActionListener{

    private EndGameDialog dialog;
    
    public EndGameActionListener(EndGameDialog dialog) {
        this.dialog = dialog;
        this.addObserver(dialog);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        GameModel.getInstance().initialize(10, 10, false, new int[] { 1, 1, 2, 2, 3 });
        notifyObservers();
    }
    
}
