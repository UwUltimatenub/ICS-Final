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
    private int pWidth, pHeight, posx,  posy, missileX, missileY;
    CalculatedPoints lowestYPoint;
    private static final int TIMER_DELAY = 10;
    
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
            interceptionImage= new ImageIcon(getClass().getResource("LeStop!.png")).getImage().getScaledInstance( rocketHeight/6, rocketHeight, Image.SCALE_SMOOTH);
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
            CalculatedPoints RandomPos = (CalculatedPoints) RandomPoint.findRandomPos(this.CalculatedPoints);
            CircularCurveCalculator circleCalculator = new CircularCurveCalculator(new Point(600, 1100), new Point(RandomPos.getX(), RandomPos.getY()), RandomPos.getZ());
            this.circlePositions = circleCalculator.generateCurve();   
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

    AffineTransform oldTransform = g2d.getTransform();
<<<<<<< HEAD
=======
    g2d.rotate(angle + Math.toRadians(90), posx, posy);

    g2d.setTransform(oldTransform);
>>>>>>> parent of 53e025f (added images)

    g2d.setColor(Color.BLACK);
    g2d.drawString("Coordinates: (" + posx + ", " + posy + ")", 10, 20);

    if (CalculatedPoints != null && currentIndex2 < CalculatedPoints.size()) {
        CalculatedPoints newposition = CalculatedPoints.get(currentIndex2);
        g2d.setColor(Color.RED);
<<<<<<< HEAD
    

        double slope = 2 * a * posx + b;
        double angle = Math.atan(slope);

        AffineTransform transform = new AffineTransform();
        transform.translate(newposition.getX(), newposition.getY());

        transform.rotate(angle + Math.toRadians(105), rocketWidth / 2, rocketHeight / 2);

        g2d.drawImage(rocket, transform, null);
=======
        g2d.fillOval(pointPos.x, pointPos.y, radius, radius);
>>>>>>> parent of 53e025f (added images)
        currentIndex2++; // Move to the next point
    }
    
    if (circlePositions != null && currentIndex < circlePositions.size()) {
        Point positioning = circlePositions.get(currentIndex);
        g2d.setColor(Color.RED);
<<<<<<< HEAD
    

        double slope = 2 * a * posx + b;
        double angle = Math.atan(slope);

        AffineTransform transform = new AffineTransform();
        transform.translate(positioning.getX(), positioning.getY());

        transform.rotate(angle + Math.toRadians(105), rocketHeight / 24, rocketHeight / 4);

        g2d.drawImage(interceptionImage, transform, null);
=======
        g2d.fillOval(Postioning.x, Postioning.y, radius, radius);
>>>>>>> parent of 53e025f (added images)
        currentIndex++; // Move to the next point
    }
    
    g2d.setColor(Color.RED);
    for (Point point : points) {
        g2d.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
    }

    g2d.dispose();
}
}
