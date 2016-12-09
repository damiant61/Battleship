package com.battleship.view;

import com.battleship.model.GameModel;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    
    private GameModel model = GameModel.getInstance();
    
    private GamePanel gamePanel;
    
    public GameFrame() {
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        
        model.initialize(10, 10, true, new int[] { 1, 1, 2, 2, 3 });
        
        gamePanel = new GamePanel(model);
        
        this.setSize(gamePanel.getSize());
        this.add(gamePanel);
    }
    
}
