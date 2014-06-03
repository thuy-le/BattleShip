/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class GlassPanel extends JPanel {
    
    public int x;
    public int y;
    public int width;
    public int height;
    public float alpha;
    public Color colour;
    
    GlassPanel(final int x, final int y, final int width, final int height, final float alpha, final Color colour){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
        this.colour = colour;
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha));
        if(colour!=null)
        g2d.setColor(colour);
        g2d.fillRect(x, y, width, height);
        //g2d.dispose();
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }
}

