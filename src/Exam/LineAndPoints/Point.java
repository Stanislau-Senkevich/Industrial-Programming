package Exam.LineAndPoints;

import java.awt.*;
import java.util.Random;

public class Point extends Canvas {
    Color clr;
    int x, y;
    Dimension dim = new Dimension(20, 20);
    public Point(int appWidth, int appHeight) {
        super();
        setMaximumSize(dim);
        setBackground(null);
        setBounds(0, 0, dim.width, dim.height);
        clr = Color.black;
        x = new Random().nextInt(appWidth - dim.width) + dim.width / 2;
        y = new Random().nextInt(appHeight - dim.height) + dim.height / 2;
    }
    public void paint(Graphics g) {
        g.setColor(clr);
        g.fillOval(0, 0, dim.width, dim.height);
    }
    public void setColor (Color clr) {
        this.clr = clr;
    }
    public Dimension getMinimumSize() { return dim; }
    public Dimension getPreferredSize() { return dim; }
}
