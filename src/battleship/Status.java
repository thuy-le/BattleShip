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
public class Status extends JPanel{
    
    private String content;
    private Color colour;
    private Font myFont;
    private int x;
    private int y;
    private float alpha;
    private int width=0;
    private int height=0;
    private boolean playerTurn;
    private boolean begin;
    
    Status(final String content, final int x, final int y){
        this.content = content;
        this.colour = MyColor.lightBlue;
        this.myFont = new Font("Century Gothic", Font.BOLD, 15);
        this.x = x;
        this.y = y;
        this.alpha = 1f;
        this.playerTurn = false;
        this.begin = false;
        setOpaque(false);
    }

    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(colour);
        g2d.setFont(myFont);
        g2d.drawString(content, x, y);
        width += (int)g2d.getFontMetrics().getStringBounds(content, g2d).getWidth();
        height += (int)g2d.getFontMetrics().getStringBounds(content, g2d).getHeight();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(x+width+1000, y+height+10);
    }
    
    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
        repaint();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        repaint();
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    public Font getMyFont() {
        return myFont;
    }

    public void setMyFont(Font myFont) {
        this.myFont = myFont;
        repaint();
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean status) {
        this.playerTurn = status;
    }

    public boolean isBegin() {
        return begin;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
    }
    
}
