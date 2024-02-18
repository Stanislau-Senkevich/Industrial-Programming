package Exam.Harmonic;

import java.awt.*;

public class Point extends Canvas {
    Color clr;
    int x, y;
    Dimension dim = new Dimension(20, 20);
    public Point(int appWidth, int appHeight) {
        super();
        setMaximumSize(dim);
        setBackground(null);
        setBounds(0, 0, dim.width, dim.height);
        clr = Color.RED;
    }
    public void setColor (Color clr) {
        this.clr = clr;
    }
    public Dimension getMinimumSize() { return dim; }
    public Dimension getPreferredSize() { return dim; }
}
