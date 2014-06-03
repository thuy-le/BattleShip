/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import javax.net.ssl.SSLEngineResult;

/**
 *
 * @author PC
 */
public class SeaSquareModel extends Observable {

    private List<SeaSquare> squares = new ArrayList<>();
    private int currentShip;
    private double radian = 90 * (Math.PI / 180);
    private boolean start = false;

    public void addSquares(final String squareSign, final Color colour1, final Color colour2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                SeaSquare square = new SeaSquare(0, 0, 60, 60, colour1, colour2, squareSign, (i + 1), (j + 1));
                squares.add(square);
                System.out.println(square.getSquareName());
            }
        }
        setChanged();
        notifyObservers(squares);
    }

    public void addPlayerShip(SeaSquare square, List<Ship> ships, Status status) {

        switch (square.getSquareSign()) {
            case "P":
                for (Ship ship : ships) {
                    if (ship.getStatus().equals("inuse")) {
                        System.out.println("Drawing ship " + ship.getShipID());
                        ship.repaint();
                        currentShip = ship.getShipID();
                        break;
                    }
                }

                if (currentShip != 0) {
                    addShip(ships, square, status, this.currentShip);

                }
                square.repaint();
                break;

            default:
                break;
        }

        setChanged();
        notifyObservers(squares);

    }

    public void addRandomShips(Status status) {
        List<Ship> ships = new ArrayList<>();
        String shipName[] = new String[4];
        shipName[0] = "MineSwipper";
        shipName[1] = "Submarine";
        shipName[2] = "Frigate";
        shipName[3] = "Aircraftcarrier";
        for (int i = 1; i < 5; i++) {
            String imgLink = "images/ship" + (i + 1) + ".png";
            Ship ship = new Ship(0, 5, 200, 100, (i + 1), imgLink, "active", shipName[i - 1]);
            ships.add(ship);
        }
        Random r = new Random();
        for (Ship ship : ships) {
            int i = r.nextInt(2);
            ship.setDirection(i);
            int hNo = r.nextInt(8) + 1;
            int vNo = r.nextInt(8) + 1;
            SeaSquare square = null;
            for (SeaSquare s : squares) {
                if (s.getSquareSign().equals("C") && s.getHNo() == hNo && s.getVNo() == vNo) {
                    square = s;
                }
            }
            if (square != null) {
                addShip(ships, square, status, ship.getShipID());
            }
            setChanged();
            notifyObservers(squares);
        }
    }

    public void addShip(List<Ship> ships, SeaSquare square, Status status, int currentShip) {
        boolean startOver = false;
        int direction = 0;
        int currentVNo = square.getVNo();
        int currentHNo = square.getHNo();
        for (Ship ship : ships) {
            if (ship.getShipID() == currentShip) {
                direction = ship.getDirection();
                break;
            }
        }

        if (direction == 0) {

            List<SeaSquare> coSquares = new ArrayList<>();

            for (SeaSquare s : squares) {
                System.out.println(s.getSquareName());
                int vNo = s.getVNo();
                if (vNo == currentVNo && !s.isSelected()) {
                    coSquares.add(s);
                    System.out.println("GETANM: " + s.getSquareName());
                }
            }

            List<SeaSquare> nextSquares = new ArrayList<>();
            List<SeaSquare> previousSquares = new ArrayList<>();

            int nSlot = 1;
            int pSlot = 1;
            boolean isAvailable = true;
            if (square.isSelected()) {
                isAvailable = false;
            } else {
                for (SeaSquare s : coSquares) {
                    int hNo = s.getHNo();
                    if (hNo > currentHNo && !s.isSelected()) {
                        nextSquares.add(s);
                    } else if (hNo < currentHNo && !s.isSelected()) {
                        previousSquares.add(s);
                        System.out.println(s.getSquareName());
                    }
                }

                List<SeaSquare> pS = new ArrayList<>();
                for (int i = previousSquares.size() - 1; i >= 0; i--) {
                    pS.add(previousSquares.get(i));
                }
                previousSquares = pS;

                int previousSlot;
                for (previousSlot = currentHNo - 1; previousSlot > currentHNo - currentShip; previousSlot--) {
                    for (SeaSquare s : previousSquares) {
                        int hNo = s.getHNo();
                        if (hNo == previousSlot) {
                            pSlot++;
                        }
                    }
                }

                int nextSlot;
                for (nextSlot = currentHNo + 1; nextSlot < currentHNo + currentShip; nextSlot++) {
                    for (SeaSquare s : nextSquares) {
                        int hNo = s.getHNo();
                        if (hNo == nextSlot) {
                            nSlot++;
                        }
                    }
                }
            }

            if (nSlot >= currentShip) {
                System.out.println("NEXT: " + nSlot);
                if (isAvailable) {
                    square.setShipID(currentShip);
                    square.setsImgNo(1);
                    square.setColour1(square.getColour2());
                    square.setSelected(true);
                    int i = 1;
                    for (SeaSquare s : nextSquares) {
                        if (i == currentShip) {
                            break;
                        }
                        System.out.println(s.getSquareName());
                        s.setShipID(currentShip);
                        s.setsImgNo(i + 1);
                        s.setColour1(s.getColour2());
                        s.repaint();
                        s.setSelected(true);
                        i++;

                    }

                    for (Ship ship : ships) {
                        if (ship.getShipID() == currentShip && square.getSquareSign().equals("P")) {
                            ship.setStatus("inactive");
                            status.setColour(MyColor.lightGreen);
                            status.setContent(ship.getShipName() + " has been deployed");
                        }
                    }
                    currentShip = 0;
                } else {
                    if (square.getSquareSign().equals("P")) {
                        status.setContent("Invalid Position!!! Please choose another one");
                        status.setColour(MyColor.lightRed);
                        status.getParent().repaint();
                    } else {
                        startOver = true;
                    }
                }

            } else if (nSlot < currentShip && pSlot >= currentShip) {
                if (isAvailable) {
                    square.setsImgNo(currentShip);
                    square.setColour1(square.getColour2());
                    square.setShipID(currentShip);
                    square.setSelected(true);
                    int i = 1;
                    for (SeaSquare s : previousSquares) {
                        if (i == currentShip) {
                            break;
                        }
                        System.out.println(s.getSquareName());
                        s.setShipID(currentShip);
                        s.setsImgNo(currentShip - i);
                        s.setColour1(s.getColour2());
                        s.repaint();
                        s.setSelected(true);
                        i++;

                    }

                    for (Ship ship : ships) {
                        if (ship.getShipID() == currentShip && square.getSquareSign().equals("P")) {
                            ship.setStatus("inactive");
                            status.setColour(MyColor.lightGreen);
                            status.setContent(ship.getShipName() + " has been deployed");
                        }
                    }
                    currentShip = 0;
                } else {
                    if (square.getSquareSign().equals("P")) {
                        status.setContent("Invalid Position!!! Please choose another one");
                        status.setColour(MyColor.lightRed);
                        status.getParent().repaint();
                    } else {
                        startOver = true;
                    }
                }
            } else {
                if (square.getSquareSign().equals("P")) {
                    status.setContent("Invalid Position!!! Please choose another one");
                    status.setColour(MyColor.lightRed);
                    status.getParent().repaint();
                } else {
                    startOver = true;
                }
            }
            //PLACE SHIP VERTICALLY
        } else {

            List<SeaSquare> coSquares = new ArrayList<>();

            for (SeaSquare s : squares) {
                int hNo = s.getHNo();
                if (hNo == currentHNo && !s.isSelected()) {
                    coSquares.add(s);
                }
            }

            for (SeaSquare seaSquareView : coSquares) {
                System.out.println("NM: " + seaSquareView.getSquareName());
            }

            List<SeaSquare> nextSquares = new ArrayList<>();
            List<SeaSquare> previousSquares = new ArrayList<>();

            int nSlot = 1;
            int pSlot = 1;
            boolean isAvailable = true;
            if (square.isSelected()) {
                isAvailable = false;
            } else {
                for (SeaSquare s : coSquares) {
                    int vNo = s.getVNo();
                    if (vNo > currentVNo && !s.isSelected()) {
                        nextSquares.add(s);
                    } else if (vNo < currentVNo && !s.isSelected()) {
                        previousSquares.add(s);
                        System.out.println(s.getSquareName());
                    }
                }

                List<SeaSquare> pS = new ArrayList<>();
                for (int i = previousSquares.size() - 1; i >= 0; i--) {
                    pS.add(previousSquares.get(i));
                }
                previousSquares = pS;

                int previousSlot;
                for (previousSlot = currentVNo - 1; previousSlot > currentVNo - currentShip; previousSlot--) {
                    for (SeaSquare s : previousSquares) {
                        int vNo = s.getVNo();
                        if (vNo == previousSlot) {
                            pSlot++;
                        }
                    }
                }

                int nextSlot;
                for (nextSlot = currentVNo + 1; nextSlot < currentVNo + currentShip; nextSlot++) {
                    for (SeaSquare s : nextSquares) {
                        int vNo = s.getVNo();
                        if (vNo == nextSlot) {
                            nSlot++;
                        }
                    }
                }
            }

            if (nSlot >= currentShip) {

                if (isAvailable) {

                    square.setShipID(currentShip);
                    square.setsImgNo(1);
                    square.setColour1(square.getColour2());
                    square.setSelected(true);
                    square.setShipID(currentShip);
                    square.setsImgNo(1);
                    square.setRadian(radian);
                    int i = 1;
                    for (SeaSquare s : nextSquares) {
                        if (i == currentShip) {
                            break;
                        }
                        System.out.println(s.getSquareName());
                        s.setShipID(currentShip);
                        s.setsImgNo(i + 1);
                        s.setRadian(radian);
                        s.setColour1(s.getColour2());
                        s.repaint();
                        s.setSelected(true);
                        i++;

                    }

                    for (Ship ship : ships) {
                        if (ship.getShipID() == currentShip && square.getSquareSign().equals("P")) {
                            ship.setStatus("inactive");
                            status.setColour(MyColor.lightGreen);
                            status.setContent(ship.getShipName() + " has been deployed");
                        }
                    }
                    currentShip = 0;
                } else {
                    if (square.getSquareSign().equals("P")) {
                        status.setContent("Invalid Position!!! Please choose another one");
                        status.setColour(MyColor.lightRed);
                        status.getParent().repaint();
                    } else {
                        startOver = true;
                    }
                }

            } else if (nSlot < currentShip && pSlot >= currentShip) {
                if (isAvailable) {
                    square.setShipID(currentShip);
                    square.setsImgNo(currentShip);
                    square.setColour1(square.getColour2());
                    square.setRadian(radian);
                    square.setSelected(true);
                    int i = 1;
                    for (SeaSquare s : previousSquares) {
                        if (i == currentShip) {
                            break;
                        }
                        System.out.println(s.getSquareName());
                        s.setShipID(currentShip);
                        s.setsImgNo(currentShip - i);
                        s.setRadian(radian);
                        s.setColour1(s.getColour2());
                        s.repaint();
                        s.setSelected(true);
                        i++;

                    }

                    for (Ship ship : ships) {
                        if (ship.getShipID() == currentShip && square.getSquareSign().equals("P")) {
                            ship.setStatus("inactive");
                            status.setColour(MyColor.lightGreen);
                            status.setContent(ship.getShipName() + " has been deployed");
                        }
                    }
                    currentShip = 0;
                } else {
                    if (square.getSquareSign().equals("P")) {
                        status.setContent("Invalid Position!!! Please choose another one");
                        status.setColour(MyColor.lightRed);
                        status.getParent().repaint();
                    } else {
                        startOver = true;
                    }
                }
            } else {
                if (square.getSquareSign().equals("P")) {
                    status.setContent("Invalid Position!!! Please choose another one");
                    status.setColour(MyColor.lightRed);
                    status.getParent().repaint();
                } else {
                    startOver = true;
                }
            }
        }
        if (startOver) {
            Random r = new Random();
            int hNo = r.nextInt(8) + 1;
            int vNo = r.nextInt(8) + 1;
            for (SeaSquare s : squares) {
                if (s.getSquareSign().equals("C") && s.getHNo() == hNo && s.getVNo() == vNo) {
                    square = s;
                }
            }
            if (square != null) {
                addShip(ships, square, status, currentShip);
            }

        }

        if (square.getSquareSign().equals("P")) {
            boolean isDeployAll = false;
            for (Ship ship : ships) {
                if (ship.getStatus().equals("inactive")) {
                    System.out.println(ship.getShipName() + " inactive");
                    isDeployAll = true;
                } else {
                    isDeployAll = false;
                    break;
                }
            }
            if (isDeployAll) {
                status.setContent("All ships are deployed. You may now start the battle");
                status.setColour(MyColor.yellow);
                status.setBegin(true);
                status.setPlayerTurn(true);
            }
        }
    }

    public void destroyShip(SeaSquare square, Status status) {
        if (status.isBegin()) {
            if (status.isPlayerTurn()) {
                if(square.isSelected()){
                    square.setColour1(Color.RED);
                    square.repaint();
                }
            } else {
                status.setContent("It's computer's turn now. Please wait!!!");
                status.setColour(MyColor.lightRed);
            }
        } else {
            status.setContent("You have to deploy all your ships before starting the battle");
            status.setColour(MyColor.lightRed);
        }
    }
}
