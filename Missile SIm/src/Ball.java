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
    private int pWidth, pHeight;
    private Image rocket, interceptionImage;
    private final int rocketWidth = 75;
    private final int rocketHeight = 150;
    private final int missileWidth = 13;
    private final int missileHeight = 75;
    private final int radius = 15;

    private Timer timer;

   private int currentIndex = 0;
   private int currentIndex2 = 0;
    
    private ArrayList<Point> points; // To store clicked points
    private ArrayList<CalculatedPoints> CalculatedPoints;
    private ArrayList<Point> circlePositions, linearPositions;

    private CalculatedPoints lowestYPoint;
    private CircularCurveCalculator circleCalculator;
    private LinearMotionCalculator linearMotionCalculator;
    
    private boolean setCircular= true;
    private boolean running = false;


    

    public Ball(int pWidth, int pHeight) {
        try{ 
            rocket = new ImageIcon(getClass().getResource("LeRocket.png")).getImage().getScaledInstance( rocketWidth, rocketHeight, Image.SCALE_SMOOTH);
            interceptionImage= new ImageIcon(getClass().getResource("LeStop!.png")).getImage().getScaledInstance( rocketHeight/12, rocketHeight/2, Image.SCALE_SMOOTH);
        }catch(Exception e) {
            System.err.println("Error loading rocket images: ");
            e.printStackTrace();
        }

        this.pWidth = pWidth;
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
    public void setCircular (boolean setCircular) {
        this.setCircular=setCircular;
    }

    

    public void gameStart(Point p1, Point p2, Point p3, JPanel panel) {

    
        if (!running) {
            running = true;
            timer.start();
            currentIndex = 0;
            currentIndex2 = 0;
            this.CalculatedPoints = ParabolicCalculator.calculateParabolaPoints(p1, p2, p3);
            if (this.CalculatedPoints == null || this.CalculatedPoints.isEmpty()) {
                System.err.println("CalculatedPoints is null or empty after calculation!");
                return; // Or handle this situation appropriately
            }
            CalculatedPoints RandomPos = (CalculatedPoints) RandomPoint.findRandomPos(this.CalculatedPoints);
            if (RandomPos == null) {
                System.err.println("lowestYPoint is null!");
                return; // Or handle this situation appropriately
            }
            
                circleCalculator = new CircularCurveCalculator(new Point(350, 1200), new Point(RandomPos.getX(), RandomPos.getY()), RandomPos.getZ());
                linearMotionCalculator = new LinearMotionCalculator(new Point(600, 1200), new Point(RandomPos.getX(), RandomPos.getY()), RandomPos.getZ());
                linearPositions = linearMotionCalculator.generateMotion();
                circlePositions = circleCalculator.generateCurve();

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

    private void drawGrid(Graphics2D g2d) {
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
    }
    private void drawParabolicMotion(Graphics2D g2d) {
        if (CalculatedPoints != null && currentIndex2 < CalculatedPoints.size()) {
            CalculatedPoints newposition = CalculatedPoints.get(currentIndex2);
            double slope = 2 * ParabolicCalculator.a * newposition.getX() + ParabolicCalculator.b;
        double angle = Math.atan(slope);
    
        AffineTransform oldTransform = g2d.getTransform();

            g2d.setColor(Color.BLACK);
            g2d.drawString("Coordinates: (" + newposition.getX() + ", " + newposition.getY() + ")", 10, 20);
            g2d.setColor(Color.RED);
    
            g2d.rotate(angle+Math.toRadians(90), newposition.getX(), newposition.getY());
            g2d.drawImage(rocket, newposition.getX()-rocketWidth/2, newposition.getY()-rocketHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex2++; // Move to the next point
        }
    }
    private void drawLinearMotion(Graphics2D g2d) {
        if (linearPositions != null && currentIndex < linearPositions.size()) {
        
            Point Postioning = linearPositions.get(currentIndex);
    
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(90)+Math.atan(linearMotionCalculator.deltaY/linearMotionCalculator.deltaX), Postioning.x-missileWidth/2, Postioning.y-Postioning.y-missileHeight/2);
            g2d.setColor(Color.RED);
            g2d.drawImage(interceptionImage, Postioning.x-missileWidth/2, Postioning.y-missileHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex++;
        }else{
            gameStop();
        }
    }
    private void drawCircularMotion(Graphics2D g2d) {
        if (circlePositions != null && currentIndex < circlePositions.size()) {
        
            Point Postioning = circlePositions.get(currentIndex);
            double angle= AngleCalculator.calculateAngle(circleCalculator.center.x, circleCalculator.center.y,Postioning.x-rocketHeight/24, Postioning.y-rocketHeight/4);
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(angle+Math.toRadians(180), Postioning.x-missileWidth/2, Postioning.y-missileHeight/2);
            g2d.setColor(Color.RED);
            g2d.drawImage(interceptionImage, Postioning.x-missileWidth/2, Postioning.y-missileHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex++; // Move to the next point
        }else{
            gameStop();
        }
    }
    private void drawPoints(Graphics g2d){
    
    for (Point point : points) {
        g2d.setColor(Color.RED);
        g2d.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
        g2d.setColor(Color.GRAY);
        g2d.drawString(point.toString(), point.x+10, point.y);
    }
}

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    drawGrid(g2d);
    drawParabolicMotion(g2d);
    if (setCircular){
        drawCircularMotion(g2d);
    }else{
        drawLinearMotion(g2d);
    }
    drawPoints(g2d);

    g2d.dispose();
}
}