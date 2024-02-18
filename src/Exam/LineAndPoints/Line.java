package Exam.LineAndPoints;

import java.awt.*;

public class Line {
    Color clr;
    int x1, y1, x2, y2;
    public Line(Color c, int x1, int y1, int x2, int y2) {
        clr = c;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public int compare(Point p) {
        double tg = (double)(y2 - y1) / (x2 - x1);
        int xt = (int)(x1 - (double)(y1 - p.y) / tg);
        if ( p.x < xt )
            return -1;
        else if ( p.x > xt )
            return 1;

        int yt = (int)(y1 - tg * (x1 - p.x));
        if ( p.y < yt )
            return 1;
        else if ( p.y > yt )
            return -1;
        return 0;
    }
}
