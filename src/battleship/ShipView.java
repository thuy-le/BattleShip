/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class ShipView extends JPanel implements Observer {

    private List<Ship> ships;
    ShipModel model = new ShipModel();
    private int x;
    private int y;
    private int width;
    private int height;

    ShipView() {
        ships = null;
        model.addObserver(this);
        model.addShips();
        this.x = 0;
        this.y = 0;
        this.width = 2000;
        this.height = 150;
        setOpaque(false);
        init();
    }

    private void init() {
        if (ships == null) {
            return;
        }
        for (Ship ship : ships) {
            ShipController controller = new ShipController(ship, model);
            ship.addMouseListener(controller);
            add(ship);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(MyColor.darkBlue);
        g2d.fillRect(x, y, width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public void update(Observable o, Object arg) {
        ships = (ArrayList<Ship>) arg;
    }
}
