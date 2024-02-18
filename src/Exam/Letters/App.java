package Exam.Letters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends JComponent implements ActionListener {
    private Timer timer;
    private int delay = 4;
    public static int width = 600;
    public static int height = 600;

    private ArrayList<Letter> letters;
    private String phrase;

    private int currentLetterIndex = 0;

    App(String phrase) {
        this.phrase = phrase;
        timer = new Timer(delay, this);
        letters = new ArrayList<>();
        for (int i = 0; i < phrase.length(); i++) {
            letters.add(new Letter(phrase.substring(i,i+1), i, phrase.length()));
        }
        setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.RED);

        for (Letter l : letters) {
            g.drawString(l.letter, (int)Math.floor(l.x), (int)Math.floor(l.y));
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (currentLetterIndex >= letters.size()) {
            timer.stop();
            return;
        }
        Letter l = letters.get(currentLetterIndex);
        l.moveAction();
        if (Math.abs(l.centerX-l.x) < 2 && Math.abs(l.centerY-l.y) < 2) {
            l.x = l.centerX;
            l.y = l.centerY;
            currentLetterIndex++;
        }
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
                JFrame frame = new JFrame("Letters");
                JPanel panel = new JPanel();

                Scanner in = new Scanner(System.in);
                System.out.println("Enter phrase");
                String phrase = in.nextLine();

                App myApp = new App(phrase);
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
