/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class SeaSquareController extends MouseAdapter {

    private SeaSquareModel model;
    private SeaSquare square;
    private List<Ship> ships;
    private Status status;

    public SeaSquareController(final SeaSquareModel model, final SeaSquare square, final List<Ship> ships, final Status status) {
        this.model = model;
        this.square = square;
        this.ships = ships;
        this.status = status;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch(square.getSquareSign()){
                case "P":
                    model.addPlayerShip(square, ships, status);
                    break;
                case "C":
                    model.destroyShip(square, status);
                    break;
            }
        }
    }
}
