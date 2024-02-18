package Exam.FormulasWithData;

import java.util.ArrayList;
import static java.lang.Math.*;

public class Data implements Comparable<Data> {
    public ArrayList<Double> nums;
    int row;
    public double M;
    public double D;
    public double S;
    public double C;

    public Data(ArrayList<Double> nums, int row) {
        this.nums = nums;
        this.row = row;
    }

    private void computeM() {
        double sum = 0;
        for (Double n : nums) {
            sum += n;
        }
        M = sum/nums.size();
    }

    private void computeD() {
        if (nums.size() == 1) {
            D = 0;
            return;
        }
        for (Double n : nums) {
            D += (n-M)*(n-M);
        }
        D /= (nums.size()-1);
    }

    private void computeS() {
        S = sqrt(D);
    }

    private void computeC() {
        C = S/M*100;
    }

    public void compute() {
        computeM();
        computeD();
        computeS();
        computeC();
    }

    @Override
    public int compareTo(Data other) {
        return Double.compare(this.C, other.C);
    }

    public String print() {
        return String.format(
                "Row %d:\t" +
                "M = %.4f\t" +
                "D = %.4f\t" +
                "S = %.4f\t" +
                "C = %.4f\t",
                row, M, D, S, C);
    }
}
