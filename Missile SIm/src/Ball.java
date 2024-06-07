import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This is the ball class. This class includes the game loop as well as different movements for the ball as well as colour, and speed.
 */
public class Ball extends JPanel {
    private int pHeight;
    private int pWidth;
    private final int radius = 15;
    private int posx;
    private int posy;
    private int dx = 2;
    private boolean running = false;
    private Timer timer;
    public double a, b, c;
    private ArrayList<Point> points; // To store clicked points

    public Ball(int pWidth, int pHeight) {
        
        this.pWidth = pWidth;
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

        if (posx > pWidth - 2 * radius || posx < radius) {
            dx = -dx;
        }
    }

    private void update() {
        moveParabolic();
    }

    public void gameStart(int x1, int y1, int x2, int y2, int x3, int y3) {
        calculateParabolaParameters(x1, y1, x2, y2, x3, y3);
        posx = radius;
        posy = (int) (a * posx * posx + b * posx + c);
        

        if (!running) {
            running = true;
            timer.start();
            ArrayList<CalculatedPoints> CalculatedPoints = ParabolicCalculator.calculateParabolaPoints(x1, y1, x2, y2, x3, y3);
            System.out.println(CalculatedPoints);

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

        // Draw the grid
        g.setColor(Color.LIGHT_GRAY);
        int gridSize = 50;
        for (int i = 0; i < pWidth; i += gridSize) {
            g.drawLine(i, 0, i, pHeight);
            g.drawString(Integer.toString(i), i, 10);
        }
        for (int i = 0; i < pHeight; i += gridSize) {
            g.drawLine(0, i, pWidth, i);
            g.drawString(Integer.toString(i), 0, i + 10);
        }

        // Draw the ball
        g.setColor(Color.RED);
        g.fillOval(posx - radius, posy - radius, radius * 2, radius * 2);

        // Draw the coordinates of the ball
        g.setColor(Color.BLACK);
        g.drawString("Coordinates: (" + posx + ", " + posy + ")", 10, 20);

        for (Point point : points) {
            g.drawString( "(" + point.x + ", " + point.y + ")", point.x,  point.y+20 );
        }

        // Draw the clicked points
        g.setColor(Color.RED);
        for (Point point : points) {
            g.fillOval(point.x - radius / 2, point.y - radius / 2, radius, radius);
        }
    }
}
