package com.battleship.view;

import com.battleship.controller.CellMouseListener;
import com.battleship.controller.FireActionListener;
import com.battleship.model.Cell;
import com.battleship.model.Computer;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Player;
import com.battleship.model.Ship;
import com.battleship.model.State;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel implements Observer {

    private Player player;
    private boolean human;

    public JButton[][] cells = new JButton[10][10];
    private FireActionListener fireActionListener;
    private CellMouseListener cellMouseListener;

    public PlayerPanel(Player player, boolean human) {
        this.player = player;
        this.human = human;

        this.setSize(300, 300);
        this.setLayout(new GridLayout(10, 10));
    }

    public void initFireActionListener(FireActionListener fireActionListener, CellMouseListener cellMouseListener) {
        this.fireActionListener = fireActionListener;
        this.cellMouseListener = cellMouseListener;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                cells[i][j] = new JButton(); // Pola defensywne
                if (human) {
                    switch (player.getDefense().getBoard()[i][j].getState()) {
                        case OCEAN:
                            cells[i][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_OCEAN.jpg")));
                            break;
                        case SHIP_UNIT:
                            cells[i][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_SHIP_UNIT.jpg")));
                            break;
                        case PROTECTED_FIELD:
                            cells[i][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                            break;
                    }
                    cells[i][j].addMouseListener(cellMouseListener);
                } else {
                    cells[i][j].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_OCEAN.jpg")));
                    cells[i][j].setRolloverIcon(new ImageIcon(this.getClass().getResource("../resource/img/target.jpg")));
                    cells[i][j].setActionCommand(i + " " + j);
                    cells[i][j].addActionListener(fireActionListener);
                }

                cells[i][j].setToolTipText((char) ('A' + j) + "" + (i + 1));
                cells[i][j].setBorder(null);
                cells[i][j].setName(i + " " + j);
                this.add(cells[i][j]);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == fireActionListener || o == cellMouseListener) {
            Cell[][] defense, offensive;

            GameModel model = GameModel.getInstance();

            List<Ship> ships;

            Human humanPlayer = model.getHuman();
            Computer computerPlayer = model.getComputer();

            if (human) {
                defense = humanPlayer.getDefense().getBoard();
                offensive = computerPlayer.getOffensive().getBoard();
                ships = humanPlayer.getDefense().getShip();
            } else {
                defense = computerPlayer.getDefense().getBoard();
                offensive = humanPlayer.getOffensive().getBoard();
                ships = computerPlayer.getDefense().getShip();
            }

            int x = ((Point) arg).x;
            int y = ((Point) arg).y;

            cells[x][y].setRolloverIcon(null);
            switch (defense[x][y].getState()) {
                case SHIP_MISS:
                    cells[x][y].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_MISS.jpg")));
                    break;
                case PROTECTED_FIELD:
                    cells[x][y].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                    break;
                case OCEAN:
                    cells[x][y].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_OCEAN.jpg")));
                    break;
                case SHIP_HIT:
                    cells[x][y].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_HIT.jpg")));
                    break;
                case SHIP_DEAD:
                    int index = 0;
                    cells[x][y].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_HIT.jpg")));
                    for (int i = 0; i < ships.size(); i++) {
                        int mast[][] = ships.get(i).getMast();
                        for (int j = 0; j < ships.get(i).getSize(); j++) {
                            if (mast[j][0] == x && mast[j][1] == y) {
                                index = i;
                                break;
                            }
                        }
                    }
                    if (ships.get(index).isSunk()) {
                        int mast[][] = ships.get(index).getMast();
                        for (int i = 0; i < ships.get(index).getSize(); i++) {
                            for (int j = -1; j <= 1; j++) {
                                for (int k = -1; k <= 1; k++) {
                                    if (mast[i][0] + j < 10 && mast[i][0] + j >= 0 && mast[i][1] + k < 10 && mast[i][1] + k >= 0
                                            && offensive[mast[i][0] + j][mast[i][1] + k].getState() == State.PROTECTED_FIELD) {
                                        cells[mast[i][0] + j][mast[i][1] + k].setIcon(new ImageIcon(this.getClass().getResource("../resource/img/FieldStatus_PROTECTED_FIELD.jpg")));
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

}
