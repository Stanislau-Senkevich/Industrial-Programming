package Exam.SortDigitWordsInString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Упорядочить в предложениях только те слова, которые состоят из суммы цифр
// (упорядочить по возрастанию суммы цифр в таком слове)

class DigitWord implements Comparable <DigitWord> {
    String str;
    int beginPos, endPos, sum;
    public DigitWord (String str, int beginPos) {
        this.str = str;
        this.beginPos = beginPos;
        endPos = beginPos + str.length() - 1;
        sum = 0;
        for (int i = 0; i < str.length(); ++i)
            sum += str.charAt(i) - '0';
    }
    public int compareTo (DigitWord dw) {
        return Integer.compare(sum, dw.sum);
    }
    public static boolean isDigitWord (String word) {
        String delim = "0123456789";
        StringTokenizer tokenizer = new StringTokenizer(word, delim);
        return !tokenizer.hasMoreElements();
    }
}
public class Task6 {
    static String processLine (String line) {
        StringBuilder res = new StringBuilder(line);
        ArrayList<DigitWord> list = new ArrayList<DigitWord>();
        ArrayList<DigitWord> sortList = new ArrayList<DigitWord>();
        StringTokenizer tokenizer = new StringTokenizer(line, " \t");
        int pos = 0;
        while (tokenizer.hasMoreElements()) {
            String word = tokenizer.nextToken();
            pos = line.indexOf(word, pos);
            if ( DigitWord.isDigitWord(word) ) {
                DigitWord dw = new DigitWord(word, pos);
                list.add(dw);
                sortList.add(dw);
            }
            pos += word.length();
        }
        Collections.sort(sortList);
        for (int i = sortList.size() - 1; i >= 0; --i)
            res.replace(list.get(i).beginPos, list.get(i).endPos + 1, sortList.get(i).str);
        return res.toString();
    }
    static ArrayList<String> list = new ArrayList<String>();
    public static void main(String[] args) {
        System.out.println("Enter text or Ctrl+D:");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if(line.equals("exit"))
                break;
            list.add(processLine(line));
        }
        in.close();
        System.out.println("\nText:");
        if ( list.isEmpty())
            System.err.println("...");
        for (String line: list)
            System.out.println(line);
    } }

