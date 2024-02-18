package Labs.Lab_8.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import Labs.Lab_8.*;
import Labs.Lab_8.client.*;

// Вариант 7. Трансляция новостей. Сервер хранит новостные сообщения за месяц. Все подключённые клиенты
//получают текущие новостные сообщения. Клиент также может запросить распечатку новостей за
//указанный день.

public class ServerMain {
	
	private static int MAX_USERS = 100;

	public static void main(String[] args) {

		try ( ServerSocket serv = new ServerSocket( Protocol.PORT  )) {
			System.err.println("initialized");
			ServerStopThread tester = new ServerStopThread();
			tester.start();
			while (true) {
				Socket sock = accept( serv );
				if ( sock != null ) {
					if ( ServerMain.getNumUsers() < ServerMain.MAX_USERS )
					{
						System.err.println( sock.getInetAddress().getHostName() + " connected" );
						ServerThread server = new ServerThread(sock);
						server.start();
					}
					else
					{
						System.err.println( sock.getInetAddress().getHostName() + " connection rejected" );
						sock.close();
					}
				} 
				if ( ServerMain.getStopFlag() ) {
					break;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			stopAllUsers();
			System.err.println("stopped");	
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {		
		}
	}
	
	public static Socket accept( ServerSocket serv ) {	
		assert( serv != null );
		try {
			serv.setSoTimeout( 1000 );
			Socket sock = serv.accept();
			return sock;
		} catch (SocketException e) {
		} catch (IOException e) {
		}		
		return null;
	}
	
	private static void stopAllUsers() {
		String[] nic = getUsers();
		for (String user : nic ) {
			ServerThread ut = getUser( user );
			if ( ut != null ) {
				ut.disconnect();
			}
		}
	}
	
	private static Object syncFlags = new Object();
	private static boolean stopFlag = false;
	public static boolean getStopFlag() {
		synchronized ( ServerMain.syncFlags ) {
			return stopFlag;
		}
	}
	public static void setStopFlag( boolean value ) {
		synchronized ( ServerMain.syncFlags ) {
			stopFlag = value;
		}
	}
	
	private static Object syncUsers = new Object();
	private static TreeMap<String, ServerThread> users = 
			new TreeMap<String, ServerThread> ();
	
	public static ServerThread getUser( String userNic ) {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.get( userNic );
		}		
	}

	public static ServerThread registerUser( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncUsers) {
			ServerThread old = ServerMain.users.get( userNic );
			if ( old == null ) {
				ServerMain.users.put( userNic, user );
			}
			return old;
		}		
	}

	public static ServerThread setUser( String userNic, ServerThread user ) {
		synchronized (ServerMain.syncUsers) {
			ServerThread res = ServerMain.users.put( userNic, user );
			if ( user == null ) {
				ServerMain.users.remove(userNic);
			}
			return res;
		}		
	}
	
	public static String[] getUsers() {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.keySet().toArray( new String[0] );
		}		
	}
	
	public static int getNumUsers() {
		synchronized (ServerMain.syncUsers) {
			return ServerMain.users.keySet().size();
		}		
	}
}

class ServerStopThread extends CommandThread {
	
	static final String cmd  = "q";
	static final String cmdL = "quit";
	
	Scanner fin;
	
	public ServerStopThread() {		
		fin = new Scanner( System.in );
		ServerMain.setStopFlag( false );
		putHandler( cmd, cmdL, new CmdHandler() {
			@Override
			public boolean onCommand(int[] errorCode) {	return onCmdQuit(); }				
		});
		this.setDaemon(true);
		System.err.println( "Enter \'" + cmd + "\' or \'" + cmdL + "\' to stop server\n" );
	}
	
	public void run() {
		
		while (true) {			
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				break;
			}
			if ( !fin.hasNextLine() )
				continue;
			String str = fin.nextLine();
			if ( command( str )) {
				break;
			}
		}
	}
	
	public boolean onCmdQuit() {
		System.err.print("stop server...");
		fin.close();
		ServerMain.setStopFlag( true );
		return true;
	}
}

class ServerThread extends Thread {
	
	private Socket              sock;
	private ObjectOutputStream 	os;
	private ObjectInputStream 	is;
	private InetAddress 		addr;
	
	private String userNic = null;
	private String userFullName;
	private static Vector<News> news = null;

	public ServerThread(Socket s) throws IOException {
		sock = s;
		s.setSoTimeout(1000);
		os = new ObjectOutputStream( s.getOutputStream() );
		is = new ObjectInputStream( s.getInputStream());
		addr = s.getInetAddress();
		this.setDaemon(true);
		news = new Vector<>();
		readFromFile();
	}
	
	public void run() {
		try {
			while ( true ) {
				Message msg = null;
				try {
					msg = ( Message ) is.readObject();
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
				if (msg != null) switch ( msg.getID() ) {
			
					case Protocol.CMD_CONNECT:
						if ( !connect( (MessageConnect) msg )) 
							return;
						break;
						
					case Protocol.CMD_DISCONNECT:
						return;
					case Protocol.CMD_ALL_NEWS:
						allNews((MessageAllNews) msg);
						break;
					case Protocol.CMD_NEWS_DAY:
						newsOnDay((MessageNewsDay) msg);
						break;
				}
			}	
		} catch (IOException e) {
			System.err.print("Disconnect...");
		} finally {
			disconnect();
		}
	}

	private void allNews(MessageAllNews msg) throws IOException {
		News[] resNews = news.toArray(new News[0]);
		os.writeObject(new MessageAllNewsResult(resNews));
	}

	private void newsOnDay(MessageNewsDay msg) throws IOException {
		ArrayList<News> res = new ArrayList<>();
		for (News n : news) {
			if (n.getDate().equals(msg.date)) {
				res.add(n);
			}
		}
		News[] resNews = res.toArray(new News[0]);

		os.writeObject(new MessageNewsDayResult(resNews));
	}
	
	boolean connect( MessageConnect msg ) throws IOException {
		
		ServerThread old = register( msg.userNic, msg.userFullName );
		if ( old == null )
		{
			os.writeObject( new MessageConnectResult());
			return true;
		} else {
			os.writeObject( new MessageConnectResult( 
				"User " + old.userFullName + " already connected as " + userNic ));
			return false;
		}
	}
	
	private boolean disconnected = false;
	public void disconnect() {
		if ( ! disconnected )
		try {			
			System.err.println( addr.getHostName() + " disconnected" );
			unregister();
			os.close();
			is.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.interrupt();
			disconnected = true;
		}
	}
	
	private void unregister() {
		if ( userNic != null ) {
			ServerMain.setUser( userNic, null );			
			userNic = null;
		}		
	}
	
	private ServerThread register( String nic, String name ) {
		ServerThread old = ServerMain.registerUser( nic, this );
		if ( old == null ) {
			if ( userNic == null ) {
				userNic = nic;
				userFullName = name;
				System.err.println("User \'"+ name+ "\' registered as \'"+ nic + "\'");
			}
		}
		return old;
	}

	private void readFromFile() {
		// Считывание из файла
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
					news.add(new News(text.toString(), date));
				}
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				System.out.println("Считывание из файла произошло успешно");
			}
		}
	}
}

