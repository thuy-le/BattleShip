/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class Ship extends JPanel {

    private int width;
    private int height;
    private int x;
    private int y;
    private int shipID;
    private String imageLink;
    private BufferedImage image;
    private String status;
    private float alpha;
    private int direction = 0;
    private String shipName;

    public Ship(final int x, final int y, final int width, final int height, final int shipID, final String imageLink, final String status, final String shipName) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.shipID = shipID;
        this.imageLink = imageLink;
        this.status = status;
        this.shipName = shipName;
        setOpaque(false);
    }

    public Ship(final int shipType) {
        if (shipType == 1) {
            width = 80;
            height = 40;
            imageLink = "images/ship1.png";
        } else if (shipType == 2) {
            width = 120;
            height = 40;
            imageLink = "imagas/ship2.png";
        } else if (shipType == 3) {
            width = 160;
            height = 40;
            imageLink = "images/ship3.png";
        } else {
            width = 200;
            height = 40;
            imageLink = "images/ship4.png";
        }
    }

    public void paint(Graphics g) {
        if (status.equals("active")) {
            alpha = 1f;
        } else if (status.equals("inuse")) {
            alpha = 0.7f;
        } else if (status.equals("inactive")) {
            alpha = 0.3f;
        }
        image = null;
            try {
                image = ImageIO.read(new File(imageLink));
            } catch (IOException e) {
            }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image, x, y, width, height, this);
        //g2d.dispose();
        
        g.setFont(new Font("Century Gothic", Font.BOLD, 17));
        g.setColor(MyColor.yellow);
        String s;
        if(direction == 0) s = "Horizontal";
        else s = "Vertical";
        int stringLen = (int)g.getFontMetrics().getStringBounds(s, g).getWidth(); 
        g.drawString(s, width/2 - stringLen/2 , y + height + 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width + x + 10, height + y + 40);
    }

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }
    
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    
    
}
