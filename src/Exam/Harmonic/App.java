package Exam.Harmonic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

//Гармонические колебания точки вдоль отрезка

public class App extends JComponent implements ActionListener  {
    private Timer timer;
    private int delay = 100;
    private int time;
    private double w = 1;
    Point point;
    Line line = new Line(Color.RED, 100, 800, 1500, 800);
    public static int width = 1600;
    public static int height = 1600;

    private int q;
    App()
    {
        timer = new Timer(delay, this);
        point = new Point(width,height);
        point.x = line.x1;
        point.y = line.y1;
        q = line.x2 - line.x1;
        setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        int q = line.x2 - line.x1;
        g.setColor(Color.RED);
        g.drawLine(line.x1, line.y1, line.x2, line.y2);
        g.setColor(Color.BLUE);
        g.fillOval(point.x, point.y-7, 15, 15);
    }
    public void actionPerformed(ActionEvent e)
    {
        time += delay;
        point.x = (line.x1+line.x2)/2 + (int)(q*cos(toRadians(w*time))/2.0);
        repaint();
    }

    public void start() {
        timer.start();
    }

    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Harmonic");
                JPanel panel = new JPanel();
                App myApp = new App();
                panel.add(myApp);
                myApp.start();
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(App.width, App.height));
                frame.setVisible(true);
            }
        });
    }
}
