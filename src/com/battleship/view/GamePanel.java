package com.battleship.view;

import com.battleship.controller.FireActionListener;
import com.battleship.model.Cell;
import com.battleship.model.Computer;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Ship;
import com.battleship.model.State;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {

    private GameModel model;

    private PlayerPanel humanPanel;
    private PlayerPanel computerPanel;

    private JButton placeShips;
    private JLabel[][] defensiveStatus, offensiveStatus;
    private JLabel[] boardStatistics;
    private Font Bebas, Lato;

    public GamePanel(GameModel model) {
        this.model = model;
        this.setSize(1002, 562);
        this.setLayout(null);

        humanPanel = new PlayerPanel(model.getHuman(), true);
        humanPanel.initFireActionListener(new FireActionListener(humanPanel, model));
        computerPanel = new PlayerPanel(model.getComputer(), false);
        computerPanel.initFireActionListener(new FireActionListener(computerPanel, model));

        createGUI();
    }

    public void createGUI() {
        placeShips = new JButton(new ImageIcon(this.getClass().getResource("../resource/img/placeShips.png")));
        placeShips.setRolloverIcon(new ImageIcon(this.getClass().getResource("../resource/img/placeShipsHover.png")));
        placeShips.setCursor(new Cursor(Cursor.HAND_CURSOR));
        placeShips.setOpaque(false);
        placeShips.setContentAreaFilled(false);
        placeShips.setBounds(0, 0, 453, 29);
        placeShips.setBorder(null);
        this.add(placeShips);

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();

        int[] shipsToPlace = new int[]{1, 1, 2, 2, 3};

        JLabel defensiveBFtitle = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/defensiveBF.jpg")), SwingConstants.CENTER);
        defensiveBFtitle.setBounds(172, 29, 30 * humanPlayer.getDefense().getSizeX(), 87);
        this.add(defensiveBFtitle);

        JLabel offensiveBFtitle = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/offensiveBF.jpg")), SwingConstants.CENTER);
        offensiveBFtitle.setBounds(259 + humanPlayer.getDefense().getSizeX() * 30, 29, 30 * humanPlayer.getDefense().getSizeX(), 87);
        this.add(offensiveBFtitle);

        try {
            Lato = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../resource/font/Lato.ttf")).deriveFont(11f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Numeracja pól plansz
        for (int i = 0; i < humanPlayer.getDefense().getSizeX(); i++) {
            JLabel defensiveNum = new JLabel((i + 1) + "", SwingConstants.CENTER); // Plansza defensywna cyfry
            defensiveNum.setBounds(172 + i * 30, 150, 30, 30);
            defensiveNum.setForeground(Color.WHITE);
            defensiveNum.setFont(Lato);
            this.add(defensiveNum);

            JLabel offensiveNum = new JLabel((i + 1) + "", SwingConstants.CENTER); // Plansza ofensywna cyfry
            offensiveNum.setBounds(259 + humanPlayer.getDefense().getSizeX() * 30 + i * 30, 150, 30, 30);
            offensiveNum.setForeground(Color.WHITE);
            offensiveNum.setFont(Lato);
            this.add(offensiveNum);
        }

        for (int i = 0; i < humanPlayer.getDefense().getSizeY(); i++) {
            JLabel defensiveNum = new JLabel((char) ('A' + i) + "", SwingConstants.CENTER); // Plansza defensywna litery
            defensiveNum.setBounds(142, 180 + i * 30, 30, 30);
            defensiveNum.setForeground(Color.WHITE);
            defensiveNum.setFont(Lato);
            this.add(defensiveNum);

            JLabel offensiveNum = new JLabel((char) ('A' + i) + "", SwingConstants.CENTER); // Plansza ofensywna litery
            offensiveNum.setBounds(229 + humanPlayer.getDefense().getSizeX() * 30, 180 + i * 30, 30, 30);
            offensiveNum.setForeground(Color.WHITE);
            offensiveNum.setFont(Lato);
            this.add(offensiveNum);
        }

        JLabel yNavy = new JLabel();
        yNavy.setForeground(Color.WHITE);
        yNavy.setBounds(25, 143, 74, 25);
        yNavy.setIcon(new ImageIcon(this.getClass().getResource("../resource/img/yNavy.jpg")));
        this.add(yNavy);

        JLabel eNavy = new JLabel();
        eNavy.setForeground(Color.WHITE);
        eNavy.setBounds(300 + 30 * 2 * humanPlayer.getDefense().getSizeX(), 143, 74, 25);
        eNavy.setIcon(new ImageIcon(this.getClass().getResource("../resource/img/eNavy.jpg")));
        this.add(eNavy);

        String[] shipName = {"Lotniskowiec", "Pancernik", "Krążownik", "Niszczyciel", "Łódź podwodna"};
        int[] margin = {0, shipsToPlace[0], shipsToPlace[0] + shipsToPlace[1], shipsToPlace[0] + shipsToPlace[1] + shipsToPlace[2], shipsToPlace[0] + shipsToPlace[1] + shipsToPlace[2] + shipsToPlace[3]};

        int y = 180;
        // Etykiety (status floty)
        for (int i = 0; i < shipName.length; i++) {
            if (shipsToPlace[i] == 0) {
                y -= 23;
                continue;
            }
            JLabel label = new JLabel(shipName[i]); // Defensywa
            label.setForeground(Color.WHITE);
            label.setBounds(25, y + i * 23 + margin[i] * 20, 110, 15);
            label.setFont(Lato);
            this.add(label);

            JLabel label2 = new JLabel(shipName[i], SwingConstants.RIGHT); // Ofensywa
            label2.setForeground(Color.WHITE);
            label2.setBounds(264 + 30 * 2 * humanPlayer.getDefense().getSizeX(), y + i * 23 + margin[i] * 20, 110, 15);
            label2.setFont(Lato);
            this.add(label2);
        }
        // Ikonki (status floty)
        y = 200;
        if (shipsToPlace[0] == 0) {
            y -= 24;
        }
        for (int i = 0; margin[i + 1] == 0 && i + 1 < 4; i++) {
            y += 24;
        }

        // Statystyka plansz
        boardStatistics = new JLabel[2];

        boardStatistics[0] = new JLabel("Trafienie: " + computerPlayer.getOffensive().getHit() + "   /   Chybienie: " + computerPlayer.getOffensive().getMiss() + "   /   Pole okrętu: " + humanPlayer.getDefense().getShipUnit(), SwingConstants.RIGHT);
        boardStatistics[0].setForeground(new Color(158, 163, 174));
        boardStatistics[0].setBounds(humanPlayer.getDefense().getSizeX() * 30 - 127, 190 + humanPlayer.getDefense().getSizeY() * 30, 300, 15);
        boardStatistics[0].setFont(Lato);
        this.add(boardStatistics[0]);

        boardStatistics[1] = new JLabel("Trafienie: " + humanPlayer.getOffensive().getHit() + "   /   Chybienie: " + humanPlayer.getOffensive().getMiss() + "   /   Pole okrętu: " + computerPlayer.getDefense().getShipUnit(), SwingConstants.RIGHT);
        boardStatistics[1].setForeground(new Color(158, 163, 174));
        boardStatistics[1].setBounds(humanPlayer.getDefense().getSizeX() * 30 * 2 - 40, 190 + humanPlayer.getDefense().getSizeY() * 30, 300, 15);
        boardStatistics[1].setFont(Lato);
        this.add(boardStatistics[1]);

        humanPanel.setLocation(172, 180);
        this.add(humanPanel);

        computerPanel.setLocation(559, 180);
        this.add(computerPanel);

        updateShipStatus();
    }

    private void updateShipStatus() {
        int y = 200;

        int[] shipsToPlace = new int[]{1, 1, 2, 2, 3};
        int[] margin = {0, shipsToPlace[0], shipsToPlace[0] + shipsToPlace[1], shipsToPlace[0] + shipsToPlace[1] + shipsToPlace[2], shipsToPlace[0] + shipsToPlace[1] + shipsToPlace[2] + shipsToPlace[3]};

        if (shipsToPlace[0] == 0) {
            y -= 24;
        }
        for (int i = 0; margin[i + 1] == 0 && i + 1 < 4; i++) {
            y += 24;
        }

        Computer computerPlayer = model.getComputer();
        Human humanPlayer = model.getHuman();

        LinkedList<Ship> ship = computerPlayer.getDefense().getShip();
        defensiveStatus = new JLabel[ship.size()][5];
        offensiveStatus = new JLabel[ship.size()][5];
        for (int i = 0; i < ship.size(); i++) {
            for (int j = 0; j < ship.get(i).getSize(); j++) {
                if (i - 1 >= 0 && j == 0 && ship.get(i - 1).getSize() != ship.get(i).getSize()) {
                    y += 24; // Defensywa
                }
                defensiveStatus[i][j] = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/status1.png")));
                defensiveStatus[i][j].setBounds(j * 15 + 25, i * 20 + y, 12, 12);
                this.add(defensiveStatus[i][j]);

                offensiveStatus[i][j] = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/status1.png"))); // Ofensywa
                offensiveStatus[i][j].setBounds(362 + 30 * 2 * humanPlayer.getDefense().getSizeX() - j * 15, i * 20 + y, 12, 12);
                this.add(offensiveStatus[i][j]);
            }
        }

        JLabel bg = new JLabel(new ImageIcon(this.getClass().getResource("../resource/img/background.jpg")));
        bg.setBounds(0, 0, 1920, 1080);
        this.add(bg);
    }

    private void update(JButton[][] buttons, int x, int y, boolean player) {

        Human humanPlayer = model.getHuman();
        Computer computerPlayer = model.getComputer();

        updateShipStatus();

        // Update board
        boardStatistics[0].setText("Trafienie: " + computerPlayer.getOffensive().getHit() + "   /   Chybienie: " + computerPlayer.getOffensive().getMiss() + "   /   Pole okrętu: " + (humanPlayer.getDefense().getShipUnit() - computerPlayer.getOffensive().getHit()));
        boardStatistics[1].setText("Trafienie: " + humanPlayer.getOffensive().getHit() + "   /   Chybienie: " + humanPlayer.getOffensive().getMiss() + "   /   Pole okrętu: " + (computerPlayer.getDefense().getShipUnit() - humanPlayer.getOffensive().getHit()));

        // Update fields
        
    }

}
