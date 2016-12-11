package com.battleship.controller;

import com.battleship.model.Computer;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Ship;
import com.battleship.model.State;
import com.battleship.view.GamePanel;
import com.battleship.view.PlayerPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class CellMouseListener extends Observable implements MouseListener {

    private PlayerPanel panel;
    private GamePanel gamePanel;
    private GameModel model;

    private boolean vertical = true;

    public CellMouseListener(PlayerPanel panel, GamePanel gamePanel, GameModel model) {
        this.panel = panel;
        this.model = model;
        this.gamePanel = gamePanel;
        this.addObserver(panel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();

        if (!humanPlayer.getPlacedShips()) {
            // placeShips.setVisible(true);
            if (SwingUtilities.isRightMouseButton(e)) {
                if (vertical) {
                    vertical = false;
                } else {
                    vertical = true;
                }
                mouseExited(e);
                mouseEntered(e);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                int i = Integer.parseInt(e.getComponent().getName().split(" ")[0]);
                int j = Integer.parseInt(e.getComponent().getName().split(" ")[1]);
                for (int k = 0; k < 5; k++) {
                    if (((Human) humanPlayer).getShip(k) > 0) {
                        if (humanPlayer.getDefense().isSpace(new Ship(5 - k, i, j, vertical))) {
                            humanPlayer.addShip(new Ship(5 - k, i, j, vertical));
                        } else {
                            break;
                        }
                        ((Human) humanPlayer).addShip(k);
                        break;
                    }
                }
                for (int k = 0; k < humanPlayer.getDefense().getSizeX(); k++) {
                    for (int l = 0; l < humanPlayer.getDefense().getSizeY(); l++) {
                        if (humanPlayer.getDefense().getBoard()[k][l].getState() == State.PROTECTED_FIELD) {
                            panel.cells[k][l].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                        }
                    }
                }
            }
            // if (((Human) humanPlayer).getPlacedShips()) {
            //     placeShips.setIcon(new ImageIcon(this.getClass().getResource("../resource/img/st.png")));
            //     placeShips.setRolloverEnabled(false);
            //   }
        }
        notifyObservers();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();

        if (!humanPlayer.getPlacedShips()) {
            int i = Integer.parseInt(e.getComponent().getName().split(" ")[0]);
            int j = Integer.parseInt(e.getComponent().getName().split(" ")[1]);
            for (int k = 0; k < 5; k++) {
                if (humanPlayer.getShip(k) > 0) {
                    if (humanPlayer.getDefense().isSpace(new Ship(5 - k, i, j, vertical))) {
                        for (int l = 0; l < 5 - k; l++) {
                            if (vertical) {
                                panel.cells[i][j + l].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_SHIP_UNIT.jpg")));
                            } else {
                                panel.cells[i + l][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_SHIP_UNIT.jpg")));
                            }
                        }
                    } else {
                        for (int l = 0; l < 5 - k; l++) {
                            if (vertical && j + l < humanPlayer.getDefense().getSizeY()) {
                                panel.cells[i][j + l].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_HIT.jpg")));
                            } else if (!vertical && i + l < humanPlayer.getDefense().getSizeX()) {
                                panel.cells[i + l][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_HIT.jpg")));
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();
        
        int i = Integer.parseInt(e.getComponent().getName().split(" ")[0]);
        int j = Integer.parseInt(e.getComponent().getName().split(" ")[1]);
        if (!((Human) humanPlayer).getPlacedShips()) {
            for (int k = 0; k < 5; k++) {
                if (j + k < humanPlayer.getDefense().getSizeY()) {
                    if (humanPlayer.getDefense().getBoard()[i][j + k].getState() == State.OCEAN) {
                        panel.cells[i][j + k].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_OCEAN.jpg")));
                    } else if (humanPlayer.getDefense().getBoard()[i][j + k].getState() == State.SHIP_UNIT) {
                        panel.cells[i][j + k].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_SHIP_UNIT.jpg")));
                    } else if (humanPlayer.getDefense().getBoard()[i][j + k].getState() == State.PROTECTED_FIELD) {
                        panel.cells[i][j + k].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                    }
                }
                if (i + k < humanPlayer.getDefense().getSizeX()) {
                    if (humanPlayer.getDefense().getBoard()[i + k][j].getState() == State.OCEAN) {
                        panel.cells[i + k][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_OCEAN.jpg")));
                    } else if (humanPlayer.getDefense().getBoard()[i + k][j].getState() == State.SHIP_UNIT) {
                        panel.cells[i + k][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_SHIP_UNIT.jpg")));
                    } else if (humanPlayer.getDefense().getBoard()[i + k][j].getState() == State.PROTECTED_FIELD) {
                        panel.cells[i + k][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                    }
                }
            }
            notifyObservers(new Point(i, j));
        }
    }

}
