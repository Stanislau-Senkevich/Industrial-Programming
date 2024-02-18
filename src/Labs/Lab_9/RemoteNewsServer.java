package Labs.Lab_9;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoteNewsServer extends UnicastRemoteObject implements Newspaper.RemoteNewspaper {
    private static final long serialVersionUID = -5298657303149865024L;

    public RemoteNewsServer() throws RemoteException {
        super();
        news = new ArrayList<>();
        readFromFile();
    }

    private void readFromFile() {
        File inputFile = new File("input.txt");
        if (inputFile.length() != 0 && inputFile.exists()) {
            try (Scanner scanner = new Scanner(inputFile)) {
                String date;
                String line;
                while (scanner.hasNextLine()) {
                    StringBuilder text = new StringBuilder();
                    date = scanner.nextLine();
                    while (true) {
                        line = scanner.nextLine();
                        if (line.equals("{}")) {
                            break;
                        }
                        text.append(line).append("\n");
                    }
                    news.add(new Newspaper.News(text.toString(), date));
                }
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                System.out.println("Считывание из файла произошло успешно");
            }
        }
    }

    public synchronized Newspaper.News[] getAllNews() throws RemoteException {
        return news.toArray(new Newspaper.News[0]);
    }

    public Newspaper.News[] getNewsOnDate(String date) throws RemoteException {
        ArrayList<Newspaper.News> filter = new ArrayList<>();
        for (Newspaper.News n : news) {
            if (n.getDate().equals(date)) {
                filter.add(n);
            }
        }
        return filter.toArray(new Newspaper.News[0]);
    }

    ArrayList<Newspaper.News> news = null;

    public static void main(String[] args) {
        try {
            RemoteNewsServer newsServer = new RemoteNewsServer();

            String name = System.getProperty("newspaper", "Newspaper");

            Naming.rebind(name, newsServer);

            System.out.println(name + " is open and ready for customers.");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}