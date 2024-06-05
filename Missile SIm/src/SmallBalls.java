
import java.awt.*;
import javax.swing.*;

public class SmallBalls extends JPanel{
    final int radius= 10;
    private int posx;
    private int posy;
    public SmallBalls(int pWidth, int pHeight){

        setPreferredSize(new Dimension(pWidth, pHeight));
    }
    public void setPosition(int x, int y) {
        this.posx = x;
        this.posy = y;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(posx - radius, posy - radius, radius * 2, radius * 2);
    }
}