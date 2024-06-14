import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class MovingBalls extends JFrame implements MouseListener {
    private final int frameWidth = 850;
    private final int frameHeight = 960;
    private JPanel panel, inputPanel, buttonPanel;
    private JButton start, stop, Eric, Aditya;
    private JLabel redTextLabel;
    private Ball ball;
    private ArrayList<Point> clickPoints;


    public MovingBalls() {
        redTextLabel = new JLabel("<html>Left click to add 3 points on the grid, the points should not be on the same X or Y axis and it should make a curve that opens downwards, Right Click will remove.</html>");
        clickPoints = new ArrayList<>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(frameWidth, frameHeight - 150));
        this.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(frameWidth, 70));
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(Color.gray);
        inputPanel.add(redTextLabel); // Add red text label
        redTextLabel.setPreferredSize(new Dimension(frameWidth - 20, 70)); // Slightly smaller than panel width
        redTextLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally
        redTextLabel.setVerticalAlignment(SwingUtilities.CENTER); // Center the text vertically
        redTextLabel.setFont(new Font("Serif", Font.PLAIN, 24)); // Make the font larger
        redTextLabel.setForeground(Color.black); // Make the font larger
        redTextLabel.setBackground(Color.RED); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding around the label
        inputPanel.add(redTextLabel); // Add red text label

        this.add(inputPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(frameWidth, 50));
        buttonPanel.setBackground(Color.gray);

        start = new JButton("Start");
        stop = new JButton("Stop");
        Eric = new JButton("Eric");
        Aditya = new JButton("Aditya");

        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(Eric);
        buttonPanel.add(Aditya);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ball = new Ball(frameWidth, frameHeight - 150);
        panel.add(ball);
        this.revalidate();

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickPoints.size() == 3 ) {

                    Point p1 = clickPoints.get(0);
                    Point p2 = clickPoints.get(1);
                    Point p3 = clickPoints.get(2);
                    ball.gameStart(p1, p2, p3, panel);

                } else {
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
        Eric.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.setCircular(true);
            }
        });
        Aditya.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.setCircular(false);
            }
        });

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
        // na
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // na
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
