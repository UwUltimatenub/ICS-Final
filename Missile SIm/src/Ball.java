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
    private int pHeight;
    private int pWidth;
    private final int rocketWidth = 75;
    private final int rocketHeight = 150;
    private final int radius = 15;
    private int posx;
    private int posy;
    private int dx = 2;
    private boolean running = false;
    private Timer timer;
    public double a, b, c;
    private ArrayList<Point> points; // To store clicked points
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

    private void calculateParabolaParameters(int x1, int y1, int x2, int y2, int x3, int y3) {
        double[][] matrix = {
            {x1 * x1, x1, 1},
            {x2 * x2, x2, 1},
            {x3 * x3, x3, 1}
        };

        double det = determinant(matrix);

        double[][] matrixA = {
            {y1, x1, 1},
            {y2, x2, 1},
            {y3, x3, 1}
        };

        double[][] matrixB = {
            {x1 * x1, y1, 1},
            {x2 * x2, y2, 1},
            {x3 * x3, y3, 1}
        };

        double[][] matrixC = {
            {x1 * x1, x1, y1},
            {x2 * x2, x2, y2},
            {x3 * x3, x3, y3}
        };

        double detA = determinant(matrixA);
        double detB = determinant(matrixB);
        double detC = determinant(matrixC);

        a = detA / det;
        b = detB / det;
        c = detC / det;
    }

    private double determinant(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
             - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
             + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }

    private void moveParabolic() {
        posx += dx;
        posy = (int) (a * posx * posx + b * posx + c);

        if (posx > pWidth - 2 * rocketWidth/2 || posx < rocketWidth/2) {
            dx = -dx;
        }
    }
    private void moveLinear() {
        posx += dx;
        posy = (int) (posx + c);

        if (posx > pWidth - 2 * rocketWidth/2 || posx < rocketWidth/2) {
            dx = -dx;
        }
    }

    private void update() {
        switch (motion) {
            case "Linear":
                moveLinear();
                break;
                
            case "Parabolic":
                moveParabolic();
                break;
        }
    }

    // Inside Ball class

public void gameStart(int x1, int y1, int x2, int y2, int x3, int y3, boolean draw, JPanel panel) {
    calculateParabolaParameters(x1, y1, x2, y2, x3, y3);
    posx = rocketWidth/2;
    posy = (int) (a * posx * posx + b * posx + c);
    this.draw=draw;

    if (!running) {
        running = true;
        timer.start();

        // Start Bezier curve animation concurrently
        ArrayList<CalculatedPoints> calculatedPoints = ParabolicCalculator.calculateParabolaPoints(x1, y1, x2, y2, x3, y3);
        Point start = new Point(100, 100);
        Point end = new Point(300, 300);
        long durationInMilliseconds = 3000; // 3 seconds

        CircleDrawer circleDrawer = new CircleDrawer(start, end, durationInMilliseconds, panel);
        Thread thread = new Thread(circleDrawer);
        thread.start();
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
    // Calculate the angle in radians
        double angle = Math.atan(slope);
    
        // Set up rotation

        AffineTransform oldTransform = g2d.getTransform();
        g2d.rotate(angle+Math.toRadians(90), posx, posy);


    if (draw){
        g2d.drawImage(rocket, posx - rocketWidth / 2, posy - rocketHeight / 2, null);
        g2d.drawImage(interceptionImage, posx - rocketWidth / 2, posy - rocketHeight / 2, null);

    }
        // Draw the rocket (rotated)
        
    
        // Reset transformation
        g2d.setTransform(oldTransform);
    
        // Draw the coordinates of the ball
        g2d.setColor(Color.BLACK);
        g2d.drawString("Coordinates: (" + posx + ", " + posy + ")", 10, 20);
    
        for (Point point : points) {
            g2d.drawString("(" + point.x + ", " + point.y + ")", point.x, point.y + 20);
        }
    
        // Draw the clicked points
        g2d.setColor(Color.RED);
        for (Point point : points) {
            g2d.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
        }
    
        // Dispose the graphics context to release resources
        g2d.dispose();
    }
}
