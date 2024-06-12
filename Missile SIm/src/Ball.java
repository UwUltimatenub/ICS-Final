import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This is the ball class. This class includes the game loop as well as different movements for the ball as well as colour, and speed.
 */

public class Ball extends JPanel {
    int currentIndex = 0;
    int currentIndex2 = 0;
    private final int rocketWidth = 75;
    private final int rocketHeight = 150;
    private final int radius = 15;
    private int pWidth, pHeight, posx,  posy;
    CalculatedPoints lowestYPoint;

    
    private int dx = 2;
    private boolean running = false;
    private Timer timer;
    public double a, b, c;
    private ArrayList<Point> points; // To store clicked points
    private ArrayList<CalculatedPoints> CalculatedPoints;
    ArrayList<Point> circlePositions;
    boolean draw=false;
    String motion;

    Image rocket, interceptionImage;
    

    public Ball(int pWidth, int pHeight, String motion, Image img) {
        try{ 
            rocket = new ImageIcon(getClass().getResource("LeRocket.png")).getImage().getScaledInstance( rocketWidth, rocketHeight, Image.SCALE_SMOOTH);
            interceptionImage= new ImageIcon(getClass().getResource("LeStop!.png")).getImage().getScaledInstance( rocketHeight/12, rocketHeight/2, Image.SCALE_SMOOTH);
        }catch(Exception e) {
            System.err.println("Error loading rocket images: ");
            e.printStackTrace();
        }

        this.pWidth = pWidth;
        this.motion = motion;
        this.pHeight = pHeight;
        
        setPreferredSize(new Dimension(pWidth, pHeight));

        points = new ArrayList<>();

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
    }

    public void addPoint(Point point) {
        points.add(point);
        repaint();
    }

    public void clearPoints() {
        points.clear();
        repaint();
    }


    private void moveParabolic() {
        posx += dx;
        posy = (int) (a * posx * posx + b * posx + c);

        if (posx > pWidth - 2 * rocketWidth/2 || posx < rocketWidth/2) {
            dx = -dx;
        }
    }

    private void update() {
        moveParabolic();
    }

    public void gameStart(int x1, int y1, int x2, int y2, int x3, int y3, boolean draw, JPanel panel) {
        posx = rocketWidth / 2;
        posy = (int) (a * posx * posx + b * posx + c);
        this.draw = draw;
    
        if (!running) {
            running = true;
            timer.start();
            currentIndex = 0;
            currentIndex2 = 0;
            this.CalculatedPoints = ParabolicCalculator.calculateParabolaPoints(x1, y1, x2, y2, x3, y3);
            if (this.CalculatedPoints == null || this.CalculatedPoints.isEmpty()) {
                System.err.println("CalculatedPoints is null or empty after calculation!");
                return; // Or handle this situation appropriately
            }
            CalculatedPoints RandomPos = (CalculatedPoints) RandomPoint.findRandomPos(this.CalculatedPoints);
            if (RandomPos == null) {
                System.err.println("lowestYPoint is null!");
                return; // Or handle this situation appropriately
            }
            
            CircularCurveCalculator circleCalculator = new CircularCurveCalculator(new Point(350, 1200), new Point(RandomPos.getX(), RandomPos.getY()), RandomPos.getZ());
            this.circlePositions = circleCalculator.generateCurve();
            if (this.circlePositions == null) {
                System.err.println("circlePositions is null!");
                return; // Or handle this situation appropriately
            }
            
        }
    }

    public void gameStop() {
        running = false;
        timer.stop();
    }

    public void setSpeed(int speed) {
        dx = speed;
    }

    public void setPosition(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.LIGHT_GRAY);
    int gridSize = 50;
    for (int i = 0; i < pWidth; i += gridSize) {
        g2d.drawLine(i, 0, i, pHeight);
        g2d.drawString(Integer.toString(i), i, 10);
    }
    for (int i = 0; i < pHeight; i += gridSize) {
        g2d.drawLine(0, i, pWidth, i);
        g2d.drawString(Integer.toString(i), 0, i + 10);
    }
    double slope = 2 * a * posx + b;
    double angle = Math.atan(slope);

    AffineTransform oldTransform = g2d.getTransform();
    

    

    g2d.setColor(Color.BLACK);
    g2d.drawString("Coordinates: (" + posx + ", " + posy + ")", 10, 20);

    if (CalculatedPoints != null && currentIndex2 < CalculatedPoints.size()) {
        CalculatedPoints newposition = CalculatedPoints.get(currentIndex2);
        Point pointPos = new Point(newposition.getX(), newposition.getY());
        g2d.setColor(Color.RED);

        g2d.rotate(angle+Math.toRadians(105), newposition.getX(), newposition.getY());
        g2d.drawImage(rocket, newposition.getX()-rocketWidth/2, newposition.getY()-rocketHeight/2, null);
        g2d.setTransform(oldTransform);
        currentIndex2++; // Move to the next point
        //System.out.print(pointPos + ", ");
    }
    if (circlePositions != null && currentIndex < circlePositions.size()) {
        Point Postioning = circlePositions.get(currentIndex);
        g2d.setColor(Color.RED);
        g2d.drawImage(interceptionImage, Postioning.x-rocketHeight/24, Postioning.y-rocketHeight/4, null);

        currentIndex++; // Move to the next point
        //System.out.print(circlePosition + ", ");
    }
    g2d.setColor(Color.RED);
    for (Point point : points) {
        g2d.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
    }

    g2d.dispose();
}
}