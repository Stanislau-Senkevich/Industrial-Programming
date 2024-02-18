package Exam.SortPalindromesInAString;

import java.util.*;

// Упорядочить в строках входных данных слова-палиндромы по алфавиту

class Palindrome implements Comparable<Palindrome> {
    String str;
    int oldBegPos, oldEndPos;
    public Palindrome(String str, int oldBegPos) {
        this.str = str;
        this.oldBegPos = oldBegPos;
        this.oldEndPos = oldBegPos + str.length() - 1;
    }
    public Palindrome(Palindrome p) {
        str = p.str;
        oldBegPos = p.oldBegPos;
        oldEndPos = p.oldEndPos;
    }
    public int compareTo (Palindrome p) {
        return str.compareTo(p.str);
    }
    public static boolean isPalindrome(String str) {
        String rev = "";
        for (int i = str.length() - 1; i >= 0; i--)
            rev = rev.concat("" + str.charAt(i));
        return rev.equals(str);
    }
}

public class Task4{

    static ArrayList<String> text = new ArrayList();

    static String getDelim (String str) {
        String res = "";
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if ( !Character.isLetter(ch) )
                res = res.concat("" + ch);
        }
        return res;
    }

    static String processLine (String line) {
        StringBuilder res = new StringBuilder(line);
        ArrayList<Palindrome> list = new ArrayList();
        ArrayList<Palindrome> sortList = new ArrayList();
        StringTokenizer stTok = new StringTokenizer(line, getDelim(line));
        int endPos = 0;
        while (stTok.hasMoreElements()) {
            String word = stTok.nextToken();
            endPos = line.indexOf(word, endPos);
            if ( Palindrome.isPalindrome(word) ) {
                Palindrome p = new Palindrome(word, endPos);
                list.add(p);
                sortList.add(p);
            }
            endPos += word.length();
        }
        Collections.sort(sortList);
        for (int i = sortList.size() - 1; i >= 0; --i) {
            res.replace(list.get(i).oldBegPos, list.get(i).oldEndPos + 1, sortList.get(i).str);
        }
        return res.toString();
    }

    public static void main(String[] args) {

        System.out.println("Enter text or Ctrl+Z:");
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if(line.equals("exit"))
                break;
            text.add(processLine(line));
        }
        in.close();
        System.out.println("\nText:");
        if ( text.isEmpty() )
            System.err.println("....");
        for (String line: text)
            System.out.println(line);
        System.out.println("The end."); }
}


