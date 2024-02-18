package Exam.Letters;

public class Letter {
    public String letter;
    public double x;
    public double y;

    public double centerX;
    public double centerY;

    public double diffX;
    public double diffY;


    public static final int letterWidth = 10;

    public static final double rate = 500;

    Letter(String letter, int index, int phraseSize) {
        this.letter = letter;
        setUpCords(index, phraseSize);
    }

    public void setUpCords(int index, int phraseSize) {
        switch (index % 4) {
            case 0:
               x = 20;
               y = 20;
               break;
            case 1:
                x = App.width-20;
                y = 20;
                break;
            case 2:
                x = App.width-20;
                y = App.height-20;
                break;
            case 3:
                x = 20;
                y = App.height-20;
                break;
        }
        centerY = App.height/2;
        centerX = App.width/2 - (phraseSize/2-index)*letterWidth;
        diffX = (centerX-x)/rate;
        diffY = (centerY-y)/rate;
    }

    public void moveAction() {
        x += diffX;
        y += diffY;
    }

}
