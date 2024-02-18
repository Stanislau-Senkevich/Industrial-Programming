package Labs.Lab_8.client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import Labs.Lab_8.*;


public class ClientMain {
	public static void main(String[] args)  {
		if (args.length < 2 || args.length > 3) {
			System.err.println(	"Invalid number of arguments\n" + "Use: nic name [host]" );
			waitKeyToStop();
			return;
		}
		try ( Socket sock = ( args.length == 2 ? 
				new Socket( InetAddress.getLocalHost(), Protocol.PORT ):
				new Socket( args[2], Protocol.PORT ) )) { 		
			System.err.println("initialized");
			session(sock, args[0], args[1] );
		} catch ( Exception e) {
			System.err.println(e);
		} finally {
			System.err.println("bye...");
		}
	}
	
	static void waitKeyToStop() {
		System.err.println("Press a key to stop...");
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
	
	static class Session {
		boolean connected = false;
		String userNic = null;
		String userName = null;
		Session( String nic, String name ) {
			userNic = nic;
			userName = name;
		}
	}
	static void session(Socket s, String nic, String name) {
		try ( Scanner in = new Scanner(System.in);
			  ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			  ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream())) {
			Session ses = new Session(nic, name);
			if ( openSession( ses, is, os, in )) { 
				try {
					while (true) {
						Message msg = getCommand(in);
						if (!processCommand(ses, msg, is, os)) {
							break;
						}				
					}			
				} finally {
					closeSession(ses, os);
				}
			}
		} catch ( Exception e) {
			System.err.println(e);
		}
	}
	
	static boolean openSession(Session ses, ObjectInputStream is, ObjectOutputStream os, Scanner in) 
			throws IOException, ClassNotFoundException {
		os.writeObject( new MessageConnect(ses.userNic, ses.userName));
		MessageConnectResult msg = (MessageConnectResult) is.readObject();
		if (!msg.Error()) {
			System.err.println("connected");
			ses.connected = true;
			String date = getCurrentStringDate();
			MessageNewsDay message = new MessageNewsDay();
			message.date = date;
			os.writeObject(message);
			MessageResult res = (MessageResult) is.readObject();
			printNews((MessageNewsDayResult) res, true);
			return true;
		}
		System.err.println("Unable to connect: "+ msg.getErrorMessage());
		System.err.println("Press <Enter> to continue...");
		if( in.hasNextLine())
			in.nextLine();
		return false;
	}
	
	static void closeSession(Session ses, ObjectOutputStream os) throws IOException {
		if ( ses.connected ) {
			ses.connected = false;
			os.writeObject(new MessageDisconnect());
		}
	}

	static Message getCommand(Scanner in) {
		while (true) {
			printPrompt();
			if (!in.hasNextLine())
				break;
			String str = in.nextLine();
			byte cmd = translateCmd(str);
			switch ( cmd ) {
				case -1:
					return null;
				case Protocol.CMD_NEWS_DAY:
					System.out.println("Введите дату в формате DD-MM-YYYY");
					String date = in.nextLine();
					MessageNewsDay msg = new MessageNewsDay();
					msg.date = date;
					return msg;
				case Protocol.CMD_ALL_NEWS:
					return new MessageAllNews();
				case 0:
					continue;
				default: 
					System.err.println("Неизвестная команда!");
					continue;
			}
		}
		return null;
	}
	
	static TreeMap<String,Byte> commands = new TreeMap<String,Byte>();
	static {
		commands.put("q", new Byte((byte) -1));
		commands.put("quit", new Byte((byte) -1));
		commands.put("a", new Byte(Protocol.CMD_ALL_NEWS));
		commands.put("all", new Byte(Protocol.CMD_ALL_NEWS));
		commands.put("n", new Byte(Protocol.CMD_NEWS_DAY));
		commands.put("news", new Byte(Protocol.CMD_NEWS_DAY));
	}
	
	static byte translateCmd(String str) {
		// returns -1-quit, 0-invalid cmd, Protocol.CMD_XXX
		str = str.trim();
		Byte r = commands.get(str);
		return (r == null ? 0 : r.byteValue());
	}
	
	static void printPrompt() {
		System.out.println();
		System.out.print("(q)uit/(a)ll news/(n)ews on day>");
		System.out.flush();
	}


	static boolean processCommand(Session ses, Message msg, 
			                      ObjectInputStream is, ObjectOutputStream os) 
            throws IOException, ClassNotFoundException {
		if ( msg != null )
		{
			os.writeObject(msg);
			MessageResult res = (MessageResult) is.readObject();
			if ( res.Error()) {
				System.err.println(res.getErrorMessage());
			} else {
				switch (res.getID()) {
					case Protocol.CMD_ALL_NEWS:
						printNews((MessageAllNewsResult) res);
						break;
					case Protocol.CMD_NEWS_DAY:
						printNews((MessageNewsDayResult) res, false);
						break;
					default:
						assert(false);
						break;
				}
			}
			return true;
		}
		return false;
	}

	static void printNews(MessageAllNewsResult res) {
		if (res.news.length == 0) {
			System.out.println("В базе новостей не обнаружено");
			return;
		}
		for (News n : res.news) {
			n.printWithDate();
			System.out.println();
		}
	}

	static void printNews(MessageNewsDayResult res, boolean relevant) {

		if (res.news.length == 0) {
			if (relevant) {
				System.out.println("Актуальных новостей нет");
				return;
			}
			System.out.println("По данной дате новостей не обнаружено");
			return;
		}

		for (int i = 0; i < res.news.length; i++) {
			System.out.println("Новость " + (i + 1));
			res.news[i].printWithoutDate();
			System.out.println();
		}
	}

	private static String getCurrentStringDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateString = currentDate.format(formatter);
		return dateString;
	}
}
