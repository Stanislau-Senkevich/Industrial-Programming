package Exam.FindFloatAndSort;

public class Data implements Comparable<Data> {
    public float num;
    public int row;

    @Override
    public int compareTo(Data other) {
        return Double.compare(this.num, other.num);
    }

    public Data (float num, int row) {
        this.num = num;
        this.row = row;
    }
}
