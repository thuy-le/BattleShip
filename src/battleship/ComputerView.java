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
public class ComputerView extends JPanel implements Observer{

    private List<SeaSquare> squares;
    private SeaSquareModel model = new SeaSquareModel();
    private Status status;
    private String squareSign = "C";
    private int x;
    private int y;
    private int width;
    private int height;
    private Color colour1 = MyColor.lightBlue;
    private Color colour2 = MyColor.darkBlue;
        


    public ComputerView(final Status status) {
        this.squares = null;
        model.addObserver(this);
        model.addSquares(squareSign, colour1, colour2);
        model.addRandomShips(status);
        this.status = status;
        this.x = 0;
        this.y = 0;
        this.width = 481;
        this.height = 481;
        setOpaque(false);
        init();
    }

    private void init() {

        setLayout(new GridLayout(8, 8));

        if (squares == null) {
            System.out.println("SQUARE NULL");
            return;
        }

        for (SeaSquare square : squares) {
            SeaSquareController controller = new SeaSquareController(model, square, null, status);
            square.addMouseListener(controller);
            add(square);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0f));
        g2d.fillRect(x, y, width, height);
        
        Graphics2D g2d2 = (Graphics2D) g;
        g2d2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d2.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, 0.7f));
        g2d2.setColor(colour2);
        BasicStroke stroke = new BasicStroke(2f);
        g2d2.setStroke(stroke);
        g2d2.drawRect(x+1, y+2, width, height);
        
        //g2d.dispose();
    }

    public Dimension getPreferredSize() {
        return new Dimension(width+4, height+4);
    }

    @Override
    public void update(Observable o, Object arg) {
        squares = (ArrayList<SeaSquare>) arg;
        //repaint();
    }
}
