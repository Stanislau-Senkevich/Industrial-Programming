package Exam.BouncingString;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//Сделать строку которая отскакивает от границ и меняет цвет

public class App extends JComponent implements ActionListener {
    private Timer timer;
    private Color color = Color.RED;
    private boolean movingRight = true;
    public int x = 10;
    public int y = 200;

    public String phrase;
    private static int delay = 40;

    private static int letterWidth = 8;

    public static final int width = 500;
    public static final int height = 500;

    App(String phrase)
    {
        this.phrase = phrase;
        timer = new Timer(delay, this);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x > width - letterWidth*phrase.length()) {
            movingRight = false;
            color = Color.RED;
        }
        else if(x < 10) {
            movingRight = true;
            color = Color.BLUE;
        }
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
        g.fillRect(0, 0, width, height);
        g.setColor(color);
        g.drawString(phrase,x,y);
    }

    public void start()
    {
        timer.start();
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter phrase");
        String phrase = in.nextLine();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Circle animation");
                JPanel panel = new JPanel();
                App circle = new App(phrase);
                panel.add(circle);
                circle.start();
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(500,500));
                frame.setVisible(true);
            }
        });
    }
}
