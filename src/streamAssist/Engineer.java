package streamAssist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
//import javax.swing.JScrollBar;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import streamAssist.Utility.Message;


public class Engineer {


	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;

	static Scanner scanner = new Scanner(System.in);
	static String input;
	private static JFrame frame;
	private static JLabel frenchLabel = new JLabel("French");
	private static JLabel spanishLabel = new JLabel("Spanish");
	private static JLabel frenchAvailable;
	private static JLabel spanishAvailable;
	private static JLabel label;
	private static JScrollPane frenchScrollPane;
	private static JScrollPane spanishScrollPane;
	private static JScrollBar frenchScrollBar;
	private static JScrollBar spanishScrollBar;
	private static JPanel frenchPanel;
	private static JPanel spanishPanel;
	private static JPanel frenchBottomPanel;
	private static JPanel spanishBottomPanel;
	private static JTextField frenchTF;
	private static JButton frenchBtn;
	private static JTextField spanishTF;
	private static JButton spanishBtn;

	private static JPanel leftContainer;
	private static JPanel rightContainer;

	private static JPanel container;

	public static final String me = "Engineer";
	public static final String FRENCH = "French";
	public static final String SPANISH = "Spanish";

	private static String text;
	
	private static final int FULL_WIDTH = 800;
	private static final int FULL_HEIGHT = 280;
	private static final int PANEL_WIDTH = 390;
	private static final int PANEL_HEIGHT = 260;
	private static final int TF_WIDTH = 25;

	public static List<Message> frenchMssgList = new ArrayList<Message>();
	public static List<Message> spanishMssgList = new ArrayList<Message>();

	public static void main(String[] args) {
		
		try {
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

		frame = new JFrame("Engineer");
		frame.setSize(FULL_WIDTH, FULL_HEIGHT);
		
		//For French
		frenchPanel = new JPanel();
		frenchPanel.setLayout(new BoxLayout(frenchPanel, BoxLayout.Y_AXIS));
		frenchPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		frenchPanel.setBackground(Color.WHITE);
		frenchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		frenchTF = new JTextField(TF_WIDTH);
		frenchBtn = new JButton("Send");
		frenchBottomPanel = new JPanel();
		frenchBottomPanel.add(frenchTF);
		frenchBottomPanel.add(frenchBtn);

		frenchTF.addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendMessageFrench();
			}

		});

		frenchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessageFrench();

			}

		});

		frenchScrollPane = new JScrollPane(frenchPanel);
		frenchScrollBar = frenchScrollPane.getVerticalScrollBar();
		//frenchScrollPane.add(frenchPanel);

		leftContainer = new JPanel(new BorderLayout());
		JPanel topFrench = new JPanel();
		
		
		topFrench.add(frenchLabel);
		frenchAvailable = new JLabel();
		frenchAvailable.setIcon(new ImageIcon("resources/images/red_dot.png"));
		topFrench.add(frenchAvailable);
		leftContainer.add(topFrench, BorderLayout.NORTH);
		leftContainer.add(frenchScrollPane);		
		leftContainer.add(frenchBottomPanel, BorderLayout.SOUTH);		



		//For Spanish
		spanishPanel = new JPanel();
		spanishPanel.setLayout(new BoxLayout(spanishPanel, BoxLayout.Y_AXIS));
		spanishPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		spanishPanel.setBackground(Color.WHITE);
		spanishPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		spanishTF = new JTextField(TF_WIDTH);
		spanishBtn = new JButton("Send");
		spanishBottomPanel = new JPanel();
		spanishBottomPanel.add(spanishTF);
		spanishBottomPanel.add(spanishBtn);

		spanishTF.addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendMessageSpanish();
			}

		});

		spanishBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessageSpanish();

			}

		});

		spanishScrollPane = new JScrollPane(spanishPanel);
		spanishScrollBar = spanishScrollPane.getVerticalScrollBar();
		
		
		rightContainer = new JPanel(new BorderLayout());
		JPanel topSpanish = new JPanel();
		
		topSpanish.add(spanishLabel);
		spanishAvailable = new JLabel();
		spanishAvailable.setIcon(new ImageIcon("resources/images/red_dot.png"));
		topSpanish.add(spanishAvailable);
		rightContainer.add(topSpanish, BorderLayout.NORTH);
		rightContainer.add(spanishScrollPane);
		rightContainer.add(spanishBottomPanel, BorderLayout.SOUTH);

		container = new JPanel();
		container.setLayout(new GridLayout(1, 2));
		container.add(leftContainer);
		container.add(rightContainer);

		frame.add(container);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent ev) {
//				try {
//					out.writeUTF("gone");
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				frame.dispose();
//			}
//		});
		
		Utility.alignToBottomRight(frame);
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);


		try {
			socket = new Socket(new Scanner(new File("server.ip")).nextLine(), 9000);

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF("Engineer");
			populateChat();

			while(true){
				input = in.readUTF();
				
				if(input.equals(Utility.FRENCH_ON)){
					frenchAvailable.setIcon(new ImageIcon("resources/images/green_dot.png"));
					label = new JLabel("French is online...");
					frenchPanel.add(label, BorderLayout.CENTER);
					frenchPanel.revalidate();
					frenchPanel.repaint();
				}else if(input.equals(Utility.FRENCH_GONE)){
					frenchAvailable.setIcon(new ImageIcon("resources/images/red_dot.png"));
					label = new JLabel("French is offline...");
					frenchPanel.add(label, BorderLayout.CENTER);
					frenchPanel.revalidate();
					frenchPanel.repaint();
				}else if(input.equals(Utility.SPANISH_ON)){
					spanishAvailable.setIcon(new ImageIcon("resources/images/green_dot.png"));
					label = new JLabel("Spanish is online...");
					spanishPanel.add(label, BorderLayout.CENTER);
					spanishPanel.revalidate();
					spanishPanel.repaint();
				}else if(input.equals(Utility.SPANISH_GONE)){
					spanishAvailable.setIcon(new ImageIcon("resources/images/red_dot.png"));
					label = new JLabel("Spanish is offline...");
					spanishPanel.add(label, BorderLayout.CENTER);
					spanishPanel.revalidate();
					spanishPanel.repaint();
				}else{
					String[] array = input.split("%=%");
					String sender = getSender(array);
					String message = getMessage(array);

					if(message != null && sender != null){
						if(sender.equals(FRENCH)){
							Utility.addMessage(sender, message, null, label, frenchScrollBar, frenchPanel, frame, 1, frenchMssgList);		
							scrollToBottom(frenchScrollBar);
						}else if(sender.equals(SPANISH)){
							Utility.addMessage(sender, message, null, label, spanishScrollBar, spanishPanel, frame, 1, spanishMssgList);
							scrollToBottom(spanishScrollBar);
						}
					}else{
						System.out.println("Either sender or message is null");
					}
					
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getSender(String[] array){

		if(array.length > 1){
			return array[0];
		}
		return null;
	}

	private static String getMessage(String[] array){

		if(array.length > 1){
			return array[1];
		}
		return null;
	}

	private static void sendMessageSpanish(){
		String text = spanishTF.getText();				

		if(!text.equals(null) && !text.equals("")){

			try {

				Utility.addMessage(null, text, null, label, spanishScrollBar, spanishPanel, frame, 1, spanishMssgList);
				
				out.writeUTF(me + "%=%" + text + "%=%" + SPANISH);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spanishTF.setText("");
		}
	}

	private static void sendMessageFrench(){
		String text = frenchTF.getText();				

		if(!text.equals(null) && !text.equals("")){

			try {

				Utility.addMessage(null, text, null, label, frenchScrollBar, frenchPanel, frame, 1, frenchMssgList);
				scrollToBottom(frenchScrollBar);
				out.writeUTF(me + "%=%" + text + "%=%" + FRENCH);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frenchTF.setText("");
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

					
					if(sender.equals(FRENCH) || receiver.equals(FRENCH)){
						//if(sender.equals(me))
							//Utility.addMessage(null, message, time, label, frenchScrollBar, frenchPanel, frame);
						//else
							Utility.addMessage(sender, message, time, label, frenchScrollBar, frenchPanel, frame, 0, frenchMssgList);

					}else if(sender.equals(SPANISH) || receiver.equals(SPANISH)){
						//if(sender.equals(me))
							//Utility.addMessage(null, message, time, label, spanishScrollBar, spanishPanel, frame);
						//else
							Utility.addMessage(sender, message, time, label, spanishScrollBar, spanishPanel, frame, 0, spanishMssgList);		            	
					}
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
	
	public static void scrollToBottom(JScrollBar scrollBar){
		scrollBar.setValue( scrollBar.getMaximum() );
	}

}
