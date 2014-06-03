/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class ContainerView extends JFrame {

    public static final String strImagePath = "images/background.jpg";
    ImageIcon background;
    JPanel jpanel;
    private List<Ship> ships;
    private List<SeaSquare> playerSeaSquares = new ArrayList<>();
    SeaSquareModel model = new SeaSquareModel();

    public ContainerView() {

        //set background for the container
        background = new ImageIcon(strImagePath);
        this.setSize(983, 804);
        jpanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background.getImage(),
                            0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        setContentPane(jpanel);
        setLayout(new BorderLayout());
        setTitle("BATTLESHIP");

        GlassPanel top = new GlassPanel(0, 0, this.getWidth(), 120, 0f, null);
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        Status status = new Status("Please place all your ships to begin the battle", 17, 100);
        status.setColour(MyColor.lightBlue2);
        status.setMyFont(new Font("Century Gothic", Font.PLAIN, 20));
        top.add(status);
        getContentPane().add(top, BorderLayout.NORTH);

        ShipView shipView = new ShipView();
        getContentPane().add(shipView, BorderLayout.SOUTH);

        ComputerView computerPlace = new ComputerView(status);
        getContentPane().add(computerPlace, BorderLayout.EAST);

        PlayerView playerPlace = new PlayerView(shipView, status);
        getContentPane().add(playerPlace, BorderLayout.WEST);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
