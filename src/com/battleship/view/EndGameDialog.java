package com.battleship.view;

import com.battleship.controller.EndGameActionListener;
import com.battleship.model.GameModel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class EndGameDialog extends JDialog implements Observer {
    
    private GamePanel panel;

    public EndGameDialog(GamePanel panel, boolean win) {
        this.panel = panel;
        //endGame = new JDialog(SwingUtilities.windowForComponent(this));
        this.setModal(true);
        this.setSize(480, 300);
        this.setResizable(false);
       // this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../resource/img/favicon.png")));
        this.setLayout(null);

        JButton again = new JButton(new ImageIcon(this.getClass().getResource("../resource/img/again.jpg")));
        again.setRolloverIcon(new ImageIcon(this.getClass().getResource("../resource/img/againHover.jpg")));
        again.setCursor(new Cursor(Cursor.HAND_CURSOR));
        again.setActionCommand("AGAIN");
        again.setBounds(90, 200, 156, 40);
        again.setBorder(null);
        again.addActionListener(new EndGameActionListener(this));
        this.add(again);

        JButton changeSettings = new JButton(new ImageIcon(this.getClass().getResource("../resource/img/changeSettings.jpg")));
        changeSettings.setRolloverIcon(new ImageIcon(this.getClass().getResource("../resource/img/changeSettingsHover.jpg")));
        changeSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeSettings.setActionCommand("CHANGESETTINGS");
        changeSettings.setBounds(246, 200, 148, 40);
        changeSettings.setBorder(null);
        //changeSettings.addActionListener(this);
        this.add(changeSettings);

        JLabel bg;
        if (win) {
            this.setTitle("Battleship2015 | Jesteś zwycięzcą!");
            bg = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/win.jpg")));
        } else {
            this.setTitle("Battleship2015 | Jesteś przegranym!");
            bg = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/lose.jpg")));
        }
        bg.setBounds(0, 0, 480, 300);
        this.add(bg);

        this.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setVisible(false);
    }

}
