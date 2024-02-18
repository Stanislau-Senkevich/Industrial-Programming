package Exam.Parabola;

import Exam.Harmonic.Line;
import Exam.Harmonic.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

//Нарисовать параболу y=x^2-1 на промежутке [-1;2]
public class App extends JComponent {
    public static int width = 800;
    public static int height = 800;

    private double left = -3;

    private double right = 2;

    private double step = 0.005;

    App() {
        setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.drawLine(width/2, 0, width/2, height);
        g.drawLine(0, height/2, width, height/2);
        g.drawLine(width/2,0, width/2+10, 20);
        g.drawLine(width/2,0, width/2-10, 20);
        g.drawLine(width, height/2, width-20, height/2+10);
        g.drawLine(width, height/2, width-20, height/2-10);
        Font axes = new Font("Arial", Font.BOLD, 25);
        g.setFont(axes);
        g.drawString("Y", width/2 + 20, 30);
        g.drawString("X", width - 30, height/2 + 40);

        Font numbs = new Font("Arial", Font.PLAIN, 13);
        g.setFont(numbs);

        for (int i = -9; i <= 9; i++) {
            g.drawLine(width/2 + (i*40), height/2 - 10, width/2 + (i*40), height/2 + 10);
            g.drawString(Integer.toString(i), width/2 + (i*40) - 5, height/2 + 25);
        }

        for (int i = -9; i <= 9; i++) {
            if (i == 0) {
                continue;
            }
            g.drawLine(width/2 - 10, height/2 + (i*40), width/2 + 10, height/2 + (i*40));
            g.drawString(Integer.toString(-i),width/2 + 15, height/2 + (i*40) + 5);
        }

        double y;

        for (double x = left; x <= right; x+= step) {
            y = x*x-1;
            g.setColor(Color.RED);
            g.fillOval((int)(x*40) + width/2, -(int)(y*40) + height/2, 3,3);
        }
    }

    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Parabola");
                JPanel panel = new JPanel();
                App myApp = new App();
                panel.add(myApp);
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(App.width, App.height));
                frame.setVisible(true);
            }
        });
    }
}
