import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MovingBalls extends JFrame implements MouseListener {
    JPanel panel, inputPanel, buttonPanel;
    JButton start, stop;
    private final int width = 1200;
    private final int height = 1200;
    JLabel errorMsg = new JLabel("Nothing to See here!");
    Ball ball;
    ArrayList<Point> clickPoints;

    
    public MovingBalls() {
        clickPoints = new ArrayList<>();
        errorMsg.setForeground(Color.RED);
        errorMsg.setVisible(false); 

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height - 150));
        this.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 4, 5, 5));
        inputPanel.setPreferredSize(new Dimension(width, 50));
        inputPanel.setBackground(Color.gray);
        inputPanel.add(errorMsg);
        this.add(inputPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, 50));
        buttonPanel.setBackground(Color.gray);

        start = new JButton("Start");
        stop = new JButton("Stop");

        buttonPanel.add(start);
        buttonPanel.add(stop);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ball = new Ball(width, height - 150);
        panel.add(ball);
        this.revalidate();

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickPoints.size() == 3 && validateInputs()) {
                    errorMsg.setVisible(false);
                    Point p1 = clickPoints.get(0);
                    Point p2 = clickPoints.get(1);
                    Point p3 = clickPoints.get(2);
                    ball.gameStart(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
                } else {
                    errorMsg.setVisible(true);
                    inputPanel.revalidate();
                    inputPanel.repaint();
                }
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.gameStop();
            }
        });
    }

    private boolean validateInputs() {
        errorMsg.setVisible(true); // Ensure error message is visible
        if (clickPoints.size() < 3) {
            errorMsg.setText("Please select 3 points.");

            inputPanel.repaint();
            return false;
        }
        if (ball.a<0){
            errorMsg.setText("Please select 3 points which form a downward facing parabola");

            inputPanel.repaint();
            return false;
        }
        else {return true;}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (clickPoints.size() < 3) {
                int relativeX = e.getX();
                int relativeY = e.getY();
                clickPoints.add(new Point(relativeX, relativeY));
                ball.addPoint(new Point(relativeX, relativeY));
                System.out.println("Point added: " + relativeX + ", " + relativeY); // Debug statement
                System.out.println("Total Points: " + clickPoints.size()); // Debug statement
                panel.repaint();
            } else {
                System.out.println("Already have 3 points"); // Debug statement
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (!clickPoints.isEmpty()) {
                clickPoints.remove(clickPoints.size() - 1);
                ball.clearPoints();
                for (Point p : clickPoints) {
                    ball.addPoint(p);
                }
                System.out.println("Total Points: " + clickPoints.size()); // Debug statement
                System.out.println("Point removed"); // Debug statement
                panel.repaint();
            } else {
                System.out.println("No points to remove"); // Debug statement
            }
        }
    }

    public static void main(String[] args) {
        new MovingBalls();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Implement the required method
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement the required method
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
