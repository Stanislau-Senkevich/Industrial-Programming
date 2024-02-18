package Labs.Lab_9;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Newspaper {
    public interface RemoteNewspaper extends Remote {
        News[] getAllNews() throws RemoteException;

        News[] getNewsOnDate(String date) throws RemoteException;
    }

    public static class News implements Serializable {
        private static final long serialVersionUID = 1L;
        private String text = "";
        private String date = "";

        public News() {
            text = "example";
            date = "01-01-2000";
        }

        public News(String text, String date) {
            this.text = text;
            this.date = date;
        }

        public void printWithDate() {
            System.out.println(date);
            System.out.println(text);
        }

        public void printWithoutDate() {
            System.out.println(text);
        }

        public String getText() {
            return this.text;
        }

        public String getDate() {
            return this.date;
        }
    }

    public static class Client {
        public static void main(String[] args) {
            try {
                String url = System.getProperty("newspaper", "rmi:///Newspaper");
                RemoteNewspaper newspaper = (RemoteNewspaper) Naming.lookup(url);

                String cmd = args[0].toLowerCase();

                if (cmd.equals("allnews") || cmd.equals("a")) {
                    News[] news = newspaper.getAllNews();
                    for (News n : news) {
                        n.printWithDate();
                    }
                } else if (cmd.equals("newsondate") || cmd.equals("n")) {
                    if (args.length < 2) {
                        System.out.println("Enter date to get the news\n");
                    } else {
                        News[] news = newspaper.getNewsOnDate(args[1]);
                        for (News n : news) {
                            n.printWithDate();
                        }
                    }
                } else {
                    System.out.println("Unknown command");
                }

            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
