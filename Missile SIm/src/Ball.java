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
    private Image rocket, interceptionImage, explosionImage;

    private final int rocketWidth = 75;
    private final int rocketHeight = 150;
    private final int missileWidth = 13;
    private final int missileHeight = 75;
    private final int radius = 15;
    private boolean interceptOccurred = false;

    float explosionAlpha= 1;

    private Timer timer, explosionTimer;

   private int currentIndex = 0;
   private int currentIndex2 = 0;
    
    private ArrayList<Point> points, circlePositions, linearPositions; // To store clicked points
    private ArrayList<CalculatedPoints> CalculatedPoints;


    private CalculatedPoints lowestYPoint;
    private CircularCurveCalculator circleCalculator;
    private LinearMotionCalculator linearMotionCalculator;
    
    private boolean setCircular= true;
    private boolean running = false;

    private Point interceptPoint;

    

    public Ball(int pWidth, int pHeight) {
        
        try{ 
            explosionImage = new ImageIcon(getClass().getResource("explosionfr.png")).getImage().getScaledInstance( 300, 300, Image.SCALE_SMOOTH);
            rocket = new ImageIcon(getClass().getResource("LeRocket.png")).getImage().getScaledInstance( rocketWidth, rocketHeight, Image.SCALE_SMOOTH);
            interceptionImage= new ImageIcon(getClass().getResource("LeStop!.png")).getImage().getScaledInstance( missileWidth, missileHeight, Image.SCALE_SMOOTH);
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
        // Reset explosion state
        interceptOccurred = false;
        explosionAlpha = 1.0f;
    
        if (!running) {
            running = true;
            timer.start();
    
            currentIndex = 0;
            currentIndex2 = 0;
            CalculatedPoints = ParabolicCalculator.calculateParabolaPoints(p1, p2, p3);
            lowestYPoint = (CalculatedPoints) VertexFinder.findLowestY(CalculatedPoints);
            if (CalculatedPoints == null || CalculatedPoints.isEmpty()) {
                System.err.println("CalculatedPoints is null or empty after calculation!");
                return; // Or handle this situation appropriately
            }
            CalculatedPoints RandomPos = (CalculatedPoints) RandomPoint.findRandomPos(this.CalculatedPoints);
            if (RandomPos == null) {
                System.err.println("lowestYPoint is null!");
                return; // Or handle this situation appropriately
            }
    
            circleCalculator = new CircularCurveCalculator(new Point(200, 800), new Point(RandomPos.x, RandomPos.y), RandomPos.z);
            circlePositions = circleCalculator.generateCurve();
            linearMotionCalculator = new LinearMotionCalculator(new Point(lowestYPoint.x, 800), new Point(RandomPos.x, RandomPos.y), RandomPos.z);
            linearPositions = linearMotionCalculator.generateMotion();
    
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
            double slope = 2 * ParabolicCalculator.a * newposition.x+ ParabolicCalculator.b;
        double angle = Math.atan(slope);
    
        AffineTransform oldTransform = g2d.getTransform();

            g2d.setColor(Color.BLACK);
            g2d.drawString("Coordinates: (" + newposition.x + ", " + newposition.y + ")", 10, 20);
            g2d.setColor(Color.RED);
    
            g2d.rotate(angle+Math.toRadians(90), newposition.x, newposition.y);
            g2d.drawImage(rocket, newposition.x-rocketWidth/2, newposition.y-rocketHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex2++; // Move to the next point
        }
    }
    private void drawLinearMotion(Graphics2D g2d) {
        if (linearPositions != null && currentIndex < linearPositions.size()) {
        
            Point Positioning = linearPositions.get(currentIndex);

            double slope=-Math.abs(linearMotionCalculator.deltaY/linearMotionCalculator.deltaX);
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(90)+Math.atan(slope), Positioning.x-missileWidth/2, Positioning.y-missileHeight/2);
            g2d.setColor(Color.RED);
            g2d.drawImage(interceptionImage, Positioning.x-missileWidth/2, Positioning.y-missileHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex++;
            interceptPoint=linearPositions.get(linearPositions.size()-1);
        }else if (running){
 
            interceptOccurred= true;
            boomtimer();
            gameStop();
            
            System.out.println("game stopped");
        }
    }
    
    private void drawCircularMotion(Graphics2D g2d) {
        if (circlePositions != null && currentIndex < circlePositions.size()) {
        
            Point Positioning = circlePositions.get(currentIndex);
            double angle= AngleCalculator.calculateAngle(circleCalculator.center.x, circleCalculator.center.y,Positioning.x-rocketHeight/24, Positioning.y-rocketHeight/4);
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(angle+Math.toRadians(180), Positioning.x-missileWidth/2, Positioning.y-missileHeight/2);
            g2d.setColor(Color.RED);
            g2d.drawImage(interceptionImage, Positioning.x-missileWidth/2, Positioning.y-missileHeight/2, null);
            g2d.setTransform(oldTransform);
            currentIndex++; // Move to the next point
            interceptPoint=new Point(circlePositions.get(circlePositions.size()-1).x,circlePositions.get(circlePositions.size()-1).y );
        }else if (running){
            interceptOccurred= true;
            gameStop();
            System.out.println("game stopped");
            boomtimer();
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

    // Draw the grid
    drawGrid(g2d);

    // Draw the parabolic motion, circular motion, and points
    if (!interceptOccurred) {
        drawParabolicMotion(g2d);
        if (setCircular) {
            drawCircularMotion(g2d);
        } else {
            drawLinearMotion(g2d);
        }
    }
    
    // Draw the red points
    drawPoints(g2d);

    // Draw the explosion if intercept occurred
    if (interceptOccurred) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, explosionAlpha));
        g2d.drawImage(explosionImage, interceptPoint.x - explosionImage.getWidth(this) / 2,
                interceptPoint.y - explosionImage.getHeight(this) / 2, this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    g2d.dispose();
}


private void boomtimer(){
    explosionTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            explosionAlpha -= 0.03f;
            if (explosionAlpha <= 0.0f) {
                explosionTimer.stop();
                explosionAlpha = 0.0f;
                
            }
            repaint();
            
        }
        
    });
    explosionTimer.start();
}
}