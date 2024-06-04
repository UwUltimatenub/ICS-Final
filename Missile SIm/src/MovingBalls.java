import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MovingBalls extends JFrame {
    JPanel panel, inputPanel, buttonPanel;
    JButton start, stop;
    private final int width = 1200;
    private final int height = 1200;
    JTextField inputx1, inputy1, inputx2, inputy2, inputx3, inputy3;

    Ball ball;

    public MovingBalls() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height - 150));
        this.add(panel, BorderLayout.CENTER);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 4, 5, 5));
        inputPanel.setPreferredSize(new Dimension(width, 100));
        inputPanel.setBackground(Color.gray);


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
        panel.setLayout(new BorderLayout());
        panel.add(ball, BorderLayout.CENTER);
        this.revalidate();

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.gameStart(inputx1, inputy1, inputx2, inputy2, inputx3, inputy3);
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.gameStop();
            }
        });
    }

    public static void main(String[] args) {
        new MovingBalls();
    }
}
