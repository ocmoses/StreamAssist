package streamAssist;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


public class Server {
	private static ServerSocket serverSocket;
	private static Socket socket;

	private static Sound s = new Sound(new File("resources/sounds/sound.wav"));

	private static JFrame frame;
	private static JPanel bodyPanel;
	private static JPanel bottomPanel;
	private static JButton clearTBButton;
	private static JButton clearDBButton;
	private static JLabel label;
	private static ScrollPane scrollPane;

	private static final String FRENCH = "French";
	private static final String SPANISH = "Spanish";
	private static final String ENGINEER = "Engineer";

	private static DataOutputStream engineerOut;
	private static DataInputStream engineerIn;
	private static DataOutputStream frenchOut;
	private static DataInputStream frenchIn;
	private static DataOutputStream spanishOut;
	private static DataInputStream spanishIn;

	private static Connection conn;
	private static Statement statement;
	 
	private static boolean engineerOn = false;
	private static boolean frenchOn = false;
	private static boolean spanishOn = false;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	

	public static void main(String[] args){
		
		try {
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frame = new JFrame("Server");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		scrollPane = new ScrollPane();
		scrollPane.add(bodyPanel);

		bottomPanel = new JPanel();
		clearTBButton = new JButton("Clear tables");
		clearDBButton = new JButton("Clear Database");
		
		bottomPanel.add(clearTBButton);
		bottomPanel.add(clearDBButton);
		
//		clearTBButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				try {
//					Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//					
//					//Get a connection
//					if(conn == null){
//					conn = DriverManager.getConnection("jdbc:derby:conversations.db;create=true");
//					
//					}
//					if(statement == null){
//					statement = conn.createStatement();
//					
//					}
//					
//					DatabaseMetaData dbm = conn.getMetaData();
//					// check if "employee" table is there
//					ResultSet tables = dbm.getTables(null, null, "CONVERSATIONS", null);
//					
//					if (tables.next()) {
//						
//						String sql = "DROP TABLE CONVERSATIONS";
//						statement.execute(sql);
//						publishStatus("Conversations table dropped successfully...");
//						
//					}
//					
//				} catch (InstantiationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IllegalAccessException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//			
//		});
//		
//		clearDBButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				try {
//					Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//					
//					//Get a connection
//					if(conn == null){
//						conn = DriverManager.getConnection("jdbc:derby:conversations.db;create=false");
//					}
//					
//					if(statement == null){
//						statement = conn.createStatement();
//					}
//					
//					
//					DatabaseMetaData dbm = conn.getMetaData();
//					// check if "employee" table is there
//					ResultSet tables = dbm.getTables(null, null, "CONVERSATIONS", null);
//					
//					if (tables.next()) {
//						
//						String sql = "DROP DATABASE CONVERSATIONS";
//						statement.execute(sql);
//						publishStatus("Database dropped successfully...");
//						
//					}
//					
//				} catch (InstantiationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IllegalAccessException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//			}
//			
//		});
		
		
		label = new JLabel("Server is up...");
		bodyPanel.add(label);

		try {
			String serverIP = InetAddress.getLocalHost().toString();
			label = new JLabel(serverIP);
			PrintWriter pw = new PrintWriter(new File("server.ip"));
			pw.write(serverIP.split("/")[1]);
			pw.close();
			bodyPanel.add(label);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			label = new JLabel("Couldn't get server ip address...");
			bodyPanel.add(label);
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			//Get a connection
			conn = DriverManager.getConnection("jdbc:derby:conversations.db;create=true");
			statement = conn.createStatement();

			publishStatus("Connection to database established...");

			DatabaseMetaData dbm = conn.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, "CONVERSATIONS", null);

			if (!tables.next()) {
				String query1 = "CREATE TABLE conversations ("
						+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "sender VARCHAR(50), "
						+ "message VARCHAR(250), "
						+ "receiver VARCHAR(50), "
						+ "time VARCHAR(20) NOT NULL, "
						+ "CONSTRAINT primary_key PRIMARY KEY (id))";;


						int createdTable = statement.executeUpdate(query1);

						if(createdTable == 0)
							publishStatus("Table created successfully...");
						else
							publishStatus("Oops, failed to create table...");

			}


//			String query2 = "INSERT INTO conversations (sender, message, receiver, time) VALUES('Engineer', 'Hello French', 'French', '"   /* 1 probably means success */
//					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')";
//			String query3 = "INSERT INTO conversations (sender, message, receiver, time) VALUES('Engineer', 'Hello Spanish', 'Spanish', '"  /* 1 probably means success */
//					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')";
//
//			System.out.println("Entry: " + statement.executeUpdate(query2));
//			System.out.println("Entry: " + statement.executeUpdate(query3));
//
//			String query4 = "SELECT * FROM conversations";
//
//			ResultSet rs = statement.executeQuery(query4);
//
//
//			while(rs.next()){
//				System.out.println("id: " + rs.getInt(1) + " Sender: " + rs.getString(2) + " Message: " + rs.getString(3) 
//						+ " Receiver: " + rs.getString(4) + " Time: " + rs.getString(5));
//			}
//			rs.close();


		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
 
		}



		try{
			serverSocket = new ServerSocket(9000);

			while(true){
				Socket socket = serverSocket.accept();
				new Thread(new ClientHandler(socket)).start();
			}	
		}catch(IOException ex){

		}
	}

	static class ClientHandler implements Runnable{

		private Socket socket;
		private DataInputStream in;
		private DataOutputStream out;


		public ClientHandler(Socket socket){
			this.socket = socket;
		}

		@Override
		public void run() {
			try{
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				String input = in.readUTF();

				if(input.equals(ENGINEER)){
					
					engineerOn = true;
					label = new JLabel("Engineer just connected..." + new Date().toString());

					engineerIn = new DataInputStream(socket.getInputStream());
					engineerOut = new DataOutputStream(socket.getOutputStream());
					
					populateEngineerChats();
					
				}else if(input.equals(FRENCH)){
					
					frenchOn = true;
					label = new JLabel("French just connected..." + new Date().toString());

					frenchIn = new DataInputStream(socket.getInputStream());
					frenchOut = new DataOutputStream(socket.getOutputStream());	

					engineerOut.writeUTF(Utility.FRENCH_ON);
					
					populateFrenchChat();
					
				}else if(input.equals(SPANISH)){
					
					spanishOn = true;
					label = new JLabel("Spanish just connected..." + new Date().toString());

					spanishIn = new DataInputStream(socket.getInputStream());
					spanishOut = new DataOutputStream(socket.getOutputStream());
					
					engineerOut.writeUTF(Utility.SPANISH_ON);
					
					populateSpanishChat();

				}

				bodyPanel.add(label, BorderLayout.CENTER);
				frame.validate();
				frame.repaint();

				while(true){

					new Thread(){
						@SuppressWarnings("static-access")
						public void run(){
							s.play();

						}
					}.start();
					
					try{
						input = in.readUTF();
					}catch(EOFException ex){
						System.out.println("End of file exception....");
						
						if(spanishOn){
							try{
								
								spanishOut.writeInt(1);
							}catch(SocketException se){
								spanishOn = false;
								try{
									engineerOut.writeUTF(Utility.SPANISH_GONE);
								}catch(SocketException s){
									
								}
							}
						}
						
						if(frenchOn){
							try{
								
								frenchOut.writeInt(1);
							}catch(SocketException se){
								frenchOn = false;
								try{
									engineerOut.writeUTF(Utility.FRENCH_GONE);
								}catch(SocketException e){
									
								}
							}
						}
						
						if(engineerOn){
							
						}
						
						break;
					}

					if(input.equals("gone"))
						break;
					else {
						String[] array = input.split("%=%");

						if(array.length == 2){
							String sender = array[0];
							String message = array[1];

							System.out.println(sender + " " + message);


							if(sender.equals(FRENCH)){								

								engineerOut.writeUTF(input);
								insertNewRecord(FRENCH, message, ENGINEER);

							}else if(sender.equals(SPANISH)){

								engineerOut.writeUTF(input);
								insertNewRecord(SPANISH, message, ENGINEER);

							}


							frame.validate();
							frame.repaint();
						}else if(array.length == 3){

							String sender = array[0];
							String message = array[1];
							String receiver = array[2];

							System.out.println(sender + " " + message + " " + receiver);

							if(receiver.equals(FRENCH)){	

								frenchOut.writeUTF(sender + "%=%" + message);
								insertNewRecord(ENGINEER, message, FRENCH);

							}else if(receiver.equals(SPANISH)){
								
								spanishOut.writeUTF(sender + "%=%" + message);
								insertNewRecord(ENGINEER, message, SPANISH);
							}

						}

						frame.validate();
						frame.repaint();

					}

				}

				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private void populateSpanishChat() {
			// TODO Auto-generated method stub

			//String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String query = "SELECT * FROM conversations WHERE sender = 'Spanish' OR receiver = 'Spanish'";
			//String[] date_time;

			try {
				ResultSet rs = statement.executeQuery(query);

				String xml = "<?xml version=\"1.0\"?>\n";
				xml += "<chat>\n";

				while(rs.next()){
					//System.out.println("sender: " + rs.getString(2) + ", message: " + rs.getString(3) + ", receiver: " + rs.getString(4) + ", time: " + rs.getString(5));
					//date_time = rs.getString(5).split(" ");
					//System.out.println("Date_time[0]: " + date_time[0]);
					//System.out.println("Today: " + today);
					
					//if(date_time[0].contentEquals(today)){

						xml += "<entry>\n";
						xml += "<sender>\n";
						xml += rs.getString(2);
						xml += "\n</sender>\n";
						xml += "<message>\n";
						xml += rs.getString(3);
						xml += "\n</message>\n";
						xml += "<receiver>\n";
						xml += rs.getString(4);
						xml += "\n</receiver>\n";

						xml += "<time>\n";
						xml += rs.getString(5).split(" ")[1];
						xml += "\n</time>\n";						

						xml += "</entry>\n";
					//}
				}

				xml += "</chat>";
				System.out.println(xml);

				try {
					spanishOut.writeUTF(xml);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		private void populateFrenchChat() {
			//String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String query = "SELECT * FROM conversations WHERE sender = 'French' OR receiver = 'French'";
			//String[] date_time;

			try {
				ResultSet rs = statement.executeQuery(query);

				String xml = "<?xml version=\"1.0\"?>\n";
				xml += "<chat>\n";

				while(rs.next()){
					//System.out.println("sender: " + rs.getString(2) + ", message: " + rs.getString(3) + ", receiver: " + rs.getString(4) + ", time: " + rs.getString(5));
					//date_time = rs.getString(5).split(" ");
					//System.out.println("Date_time[0]: " + date_time[0]);
					//System.out.println("Today: " + today);
					
					//if(date_time[0].contentEquals(today)){

						xml += "<entry>\n";
						xml += "<sender>\n";
						xml += rs.getString(2);
						xml += "\n</sender>\n";
						xml += "<message>\n";
						xml += rs.getString(3);
						xml += "\n</message>\n";
						xml += "<receiver>\n";
						xml += rs.getString(4);
						xml += "\n</receiver>\n";

						xml += "<time>\n";
						xml += rs.getString(5).split(" ")[1];
						xml += "\n</time>\n";						

						xml += "</entry>\n";
					//}
				}

				xml += "</chat>";
				System.out.println(xml);

				try {
					frenchOut.writeUTF(xml);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		private void populateEngineerChats() {
			//String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String query = "SELECT * FROM conversations";
			//String[] date_time;

			try {
				ResultSet rs = statement.executeQuery(query);

				String xml = "<?xml version=\"1.0\"?>\n";
				xml += "<chat>\n";

				while(rs.next()){
					//System.out.println("sender: " + rs.getString(2) + ", message: " + rs.getString(3) + ", receiver: " + rs.getString(4) + ", time: " + rs.getString(5));
					//date_time = rs.getString(5).split(" ");
					//System.out.println("Date_time[0]: " + date_time[0]);
					//System.out.println("Today: " + today);
					
					//if(date_time[0].contentEquals(today)){

						xml += "<entry>\n";
						xml += "<sender>\n";
						xml += rs.getString(2);
						xml += "\n</sender>\n";
						xml += "<message>\n";
						xml += rs.getString(3);
						xml += "\n</message>\n";
						xml += "<receiver>\n";
						xml += rs.getString(4);
						xml += "\n</receiver>\n";

						xml += "<time>\n";
						xml += rs.getString(5).split(" ")[1];
						xml += "\n</time>\n";						

						xml += "</entry>\n";
					//}
				}

				xml += "</chat>";
				System.out.println(xml);

				try {
					engineerOut.writeUTF(xml);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static void publishStatus(String s){
		label = new JLabel(s);
		bodyPanel.add(label, BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}

	private static void insertNewRecord(String sender, String message, String receiver){

		message = message.replace("'", "%=%");

		String query = "INSERT INTO conversations (sender, message, receiver, time) VALUES('" + sender + "', '" + message + "', '" + receiver + "', '"   /* 1 probably means success */
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')";

		try {
			statement.executeUpdate(query);
			publishStatus("Entry inserted successfully...");
		} catch (SQLException e) {
			publishStatus("Couldn't insert entry...");
			e.printStackTrace();
		}

	}


}
