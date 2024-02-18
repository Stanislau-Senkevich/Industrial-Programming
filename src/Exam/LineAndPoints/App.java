package Exam.LineAndPoints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Нарисовать линию и случайные точки, для каждой точки вывести с какой стороны она от прямой

public class App extends JComponent  {
    ArrayList<Point> points;
    Line line;
    public static int width = 1600;
    public static int height = 1600;
    App(ArrayList<Point> points, Line line)
    {
        this.points = points;
        this.line = line;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, App.width, App.height);
        g.setColor(Color.RED);
        for (Point p : points) {
            g.fillOval(p.x, p.y, 10, 10);
        }
        g.drawLine(line.x1, line.y1, line.x2, line.y2);
    }

    public static void main(String[] args)
    {
        int x1, y1, x2, y2, n;

        if (args.length < 5) {
            System.out.println("Required 5 program arguments in format: 'n x1 y1 x2 y2'");
            return;
        }
        try {
            n = Integer.parseInt(args[0]);
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.out.println("Enter integer cords of points");
            return;
        }

        Line line = new Line(Color.RED, x1,y1,x2,y2);

        System.out.println("Generating points:");
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = new Point(App.width,App.height);
            points.add(p);
            String str;
            if (line.compare(p) == 1) {
                str = "Located on the 1st side";
            } else if (line.compare(p) == -1) {
                str = "Located on the 2nd side";
            } else {
                str = "Located on the line";
            }
            System.out.printf("Point %d: (%d, %d); %s\n",
                    i+1, p.x, p.y, str);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Points and line");
                JPanel panel = new JPanel();
                App myApp = new App(points, line);
                panel.add(myApp);
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(App.width,App.height));
                frame.setVisible(true);
            }
        });
    }
}
