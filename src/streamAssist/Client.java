package streamAssist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
//import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import streamAssist.Utility.Message;

public class Client {
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;
	static Scanner scanner = new Scanner(System.in);
	static String input;
	private static JFrame frame;
	private static JLabel label;
	private static JScrollPane scrollPane;
	private static JScrollBar scrollBar;
	private static JPanel bodyPanel;
	private static JPanel bottomPanel;
	private static JButton clientBtn;
	private static JTextField clientTF;

	private static JPanel panel;
	private static ButtonGroup bg;
	private static JButton ok;
	private static String me;
	private static final String NO_SELECTION = "No selection made";
	private static final String OK = "OK";

	private static Sound s = new Sound(new File("resources/sounds/sound.wav"));
	private static Timer timer = new Timer(1000, new ServerListener());
		
	private static final int WIDTH = 400;
	private static final int HEIGHT = 250;
	private static final int TF_WIDTH = 25;
	
	private static List<Message> olderMessages = new ArrayList<Message>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Client is up....");

//		frame = new JFrame("Who are you?");
//		JRadioButton french = new JRadioButton("French");
//		JRadioButton spanish = new JRadioButton("Spanish");
//		french.setActionCommand("French");
//		spanish.setActionCommand("Spanish");
//		bg = new ButtonGroup();
//		bg.add(french);
//		bg.add(spanish);
//		panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		panel.add(french);
//		panel.add(spanish);
//		ok = new JButton(OK);
//		frame.add(panel);
//		frame.add(ok, BorderLayout.SOUTH);
//
//		french.addItemListener(new ItemListener(){
//
//			@Override
//			public void itemStateChanged(ItemEvent event) {
//				if(ok.getText().equals(NO_SELECTION))
//					ok.setText(OK);				
//			}
//
//		});
//
//		spanish.addItemListener(new ItemListener(){
//
//			@Override
//			public void itemStateChanged(ItemEvent event) {
//				if(ok.getText().equals(NO_SELECTION))
//					ok.setText(OK);				
//			}
//
//		});
//
//		ok.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if(ok.getText().equals(NO_SELECTION))
//					ok.setText(OK);
//				else{
//					try{
//						me = bg.getSelection().getActionCommand();
//						int confirm = JOptionPane.showConfirmDialog(null, "You have chosen " + me + ".\nAre you sure");
//						if(confirm == 0){
//							//frame.dispose();
//							frame.setVisible(false);
//							run();	
//						}
//					}catch(Exception e){
//						ok.setText(NO_SELECTION);
//					}
//				}
//			}
//
//		});	
//		
//		
//		frame.setSize(300, 100);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
		
		
		run();

	}

	public static void run(){
		me = "French";
		
		try {
			UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");
			UIManager.put("RootPane.setupButtonVisible", false);
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
		
		frame = new JFrame(me + " Assistant");
		frame.setSize(WIDTH, HEIGHT);
				

		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		
		
		bodyPanel.setMaximumSize(new Dimension(300, 200));
		bodyPanel.setBackground(Color.WHITE);
		bodyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		scrollPane = new JScrollPane(bodyPanel);
		scrollBar = scrollPane.getVerticalScrollBar();
		

		bottomPanel = new JPanel();
		clientTF = new JTextField(TF_WIDTH);
		clientBtn = new JButton("Send");
		bottomPanel.add(clientTF);
		bottomPanel.add(clientBtn);

		frame.add(scrollPane);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent ev) {
//				try {
//					out.writeUTF("gone");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				frame.dispose();
//			}
//		});
		
		frame.addWindowStateListener(new WindowStateListener() {
			   public void windowStateChanged(WindowEvent e) {
				   if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED){
					      System.out.println("minimized");
				   }
			   }
			});
		
		clientTF.addKeyListener(new KeyAdapter(){
			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendMessage();
			}
		});
		
		clientBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();	
			}

		});
		
		
		Utility.alignToBottomRight(frame);
		frame.setVisible(true);
		frame.repaint();

		connect();
	}
	
	public static void connect(){
		try {
			
			socket = new Socket(new Scanner(new File("server.ip")).nextLine(), 9000);
			
//			if(timer.isRunning())
//				timer.stop();

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			out.writeUTF(me);
			populateChat();
			

			while(true){

				new Thread(){
					@SuppressWarnings("static-access")
					public void run(){
						s.play();
					}
				}.start();
				String input = in.readUTF();
				if(input.equals("gone"))
					break;
				else{
					String[] array = input.split("%=%");

					if(array.length == 2){
						String sender = array[0];
						String message = array[1];
					Utility.addMessage(sender, message, null, label, scrollBar, bodyPanel, frame, 1, olderMessages); //if newer = 0, then it is an old message, else, it is new
					scrollToBottom();
					}

				}
				
			}

			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			if(!timer.isRunning())
//				timer.start();
			
		}
	}
	
	static class ServerListener implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			connect();
		}
		
	}
	
	private static void sendMessage(){
		String text = clientTF.getText();				
		
		if(!text.equals(null) && !text.equals("")){

			try {
				
				Utility.addMessage(null, text, null, label, scrollBar, bodyPanel, frame, 1, olderMessages);
				scrollToBottom();
				out.writeUTF(me + "%=%" + text);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clientTF.setText("");
		}			
	}
	
	private static void populateChat(){
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(in.readUTF()));

			Document doc = db.parse(is);
			
			Element de = doc.getDocumentElement();
			
			System.out.println("This is the root element: " + de.getNodeName());
			
			NodeList nNodes = doc.getElementsByTagName("entry");
			String sender;
			String message;
			String receiver;
			String time;
			
			for(int i = 0; i < nNodes.getLength(); i++){
				Node node = nNodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
		            Element eElement = (Element) node;
		            sender = eElement.getElementsByTagName("sender")
                            .item(0).getTextContent();
		            System.out.println("Sender : " + sender);
		            message = eElement.getElementsByTagName("message")
                            .item(0).getTextContent().replace("%=%", "'");
		            System.out.println("Message : " + message);
		            receiver = eElement.getElementsByTagName("receiver")
                            .item(0).getTextContent();
		            System.out.println("Receiver : " + receiver);
		            time = eElement.getElementsByTagName("time")
                            .item(0).getTextContent();
		            System.out.println("time : " + time);
		            
		            		            
		            Utility.addMessage(sender,  message, time, label, scrollBar, bodyPanel, frame, 0, olderMessages);
		            
		            scrollToBottom();
		        }
			}
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void scrollToBottom(){
		scrollBar.setValue( scrollBar.getMaximum() );
	} 
	
	public static JScrollBar getScrollBar(){
		return scrollBar;
	}
}
