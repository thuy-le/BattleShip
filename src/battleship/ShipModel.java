/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author PC
 */
public class ShipModel extends Observable {

    private List<Ship> ships = new ArrayList<>();

    public ShipModel() {
    }

    private String shipName[] = new String[4];
    
    public void addShips() {
        
        shipName[0] = "MineSwipper";
        shipName[1] = "Submarine";
        shipName[2] = "Frigate";
        shipName[3] = "Aircraftcarrier"; 
        for (int i = 1; i < 5; i++) {
            String imgLink = "images/ship" + (i + 1) + ".png";
            Ship ship = new Ship(0, 5, 200, 100, (i + 1), imgLink, "active", shipName[i-1]);
            ships.add(ship);
        }
        setChanged();
        notifyObservers(ships);
    }

    public void chooseShip(Ship ship) {
        switch (ship.getStatus()) {
            case "active":
                for (Ship s : ships) {
                    if (s.getShipID() != ship.getShipID() && s.getStatus().equals("inuse")) {
                        s.setStatus("active");
                        s.repaint();
                    }
                }
                ship.setStatus("inuse");
                ship.repaint();
                break;
            case "inuse":
                ship.setStatus("active");
                ship.repaint();
                break;
            case "inactive":
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(ships);
    }

    public void changeDirection(Ship ship) {
        if (ship.getDirection() == 0) {
            ship.setDirection(1);
        } else {
            ship.setDirection(0);
        }
        ship.repaint();
        setChanged();
        notifyObservers(ships);
    }
}
