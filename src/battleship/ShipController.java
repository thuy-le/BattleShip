/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author PC
 */
public class ShipController extends MouseAdapter {

    private Ship ship;
    private ShipModel model;
    //private List<Ship> ships;

    public ShipController(final Ship ship, final ShipModel model) {
        this.ship = ship;
        this.model = model;
    //    this.ships = ships;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            model.chooseShip(ship);
        }
        
        else if(e.getButton() == MouseEvent.BUTTON3){
            model.changeDirection(ship);
        }
    }
}
