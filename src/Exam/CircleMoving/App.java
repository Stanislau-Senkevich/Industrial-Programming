package Exam.CircleMoving;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//Отскок шарика от стенок

public class App extends JComponent implements ActionListener {
    private Timer timer;
    private Color color;
    private boolean movingRight = true;
    public int x = 40;
    public int y = 200;
    App(int delay, Color col)
    {
        color = col;
        timer = new Timer(delay, this);
        setPreferredSize(new Dimension(500, 500));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(x > 4 * this.getSize().height/5 - 15)
            movingRight = false;
        else if(x < 10)
            movingRight = true;
        if(movingRight)
            x+=5;
        else
            x-=5;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        int width = 500;
        int height = 500;
        g.fillRect(0, 0, width, height);
        g.setColor(Color.RED);
        g.fillOval(x, y, this.getSize().height / 10, this.getSize().height / 10);
    }

    public void start()
    {
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Circle animation");
                JPanel panel = new JPanel();
                App circle = new App(20, Color.RED);
                panel.add(circle);
                frame.getContentPane().add(panel);
                circle.start();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(500,500));
                frame.setVisible(true);
            }
        });
    }
}
