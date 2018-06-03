package streamAssist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Utility {
	
	public static String FRENCH_ON = "French Online";
	public static String FRENCH_GONE = "French Offline";
	public static String SPANISH_ON = "Spanish Online";
	public static String SPANISH_GONE = "Spanish Offline";
	public static String engineerOnline = "Engineer Online";
	public static String engineerOffline = "Engineer Offline";
	
	private static JPanel panel;
	private static JTextArea textArea;
	public static EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
	static DateFormat df = new SimpleDateFormat("HH:mm:ss");
	static Date dateobj;
	static Rectangle labelBounds;
	private static JScrollBar scrollBar;
	private static Timer timer;
	private static final int DURATION = 3000;
	static long startTime;
	static long currentTime;
	static int timeSpent;
	static Point point;
	static int labelPos;
	
	static boolean shaking;
	static boolean flashing;
	
	private static Sound s = new Sound(new File("resources/sounds/sound.wav"));
	static ShakingFrame shake;


	public static void addMessage(String sender, String message, String time, JLabel label, JScrollBar scrollBar, 
			JPanel bodyPanel, JFrame frame, int newer, List<Message> olderMessages){
		
		new Thread(){
			@SuppressWarnings("static-access")
			public void run(){
				s.play();
			}
		}.start();
		
		shake = new ShakingFrame(frame);
		
		if(shaking)
			shake.stopShake();
		if(flashing)
			stopFlashingMessage();
		
		bodyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		dateobj = new Date();
		//label = new JLabel(sender + "	" + df.format(dateobj));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));


		//create sender label
		label = new JLabel((sender == null)? "Me" : sender);
		label.setFont(new Font("Sans-serif", Font.BOLD, 10));
		label.setForeground(Color.BLACK);
		panel.add(label, BorderLayout.WEST);
		
		//Glue to separate them
		panel.add(Box.createHorizontalGlue());
		
		
		
		//create time label
		if(time == null)
			label = new JLabel(df.format(dateobj));
		else
			label = new JLabel(time);
		
		label.setFont(new Font("Sans-serif", Font.ITALIC, 10));
		label.setForeground(Color.GRAY);
		panel.add(label);//, BorderLayout.EAST);

		bodyPanel.add(panel, BorderLayout.CENTER);


		
		textArea = new JTextArea();
		textArea.setColumns(5);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.append(message);
		
		olderMessages.add(new Message(sender, time, message, textArea));
		
		if(sender == null)
			textArea.setBackground(Color.GREEN);
		else
			textArea.setBackground(Color.ORANGE);
		
		bodyPanel.add(textArea);
		
		frame.validate();
		frame.repaint();

		scrollBar.setValue(scrollBar.getMaximum());
		
		if(newer != 0 && sender != null){
			
			shake.startShake();
			flashNewMessage(textArea);
			
		}
	
	}


	public static void alignToBottomRight(JFrame frame) {
		// TODO Auto-generated method stub
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - frame.getWidth();
		int y = (int) rect.getMaxY() - frame.getHeight();
		frame.setResizable(false);
		frame.setLocation(x, y);
	}
	

	public static void scrollToPosition(JScrollPane scrollPane, JLabel label){
		if(labelPos > 200)
			scrollPane.getVerticalScrollBar().setValue(label.getBounds().y - 50);
		else 
			scrollPane.getVerticalScrollBar().setValue(label.getBounds().y - 50);
	}
	
	
	public static void flashNewMessage(JTextArea textArea){
		timer = new Timer(250, new BlinkAction(textArea));
		startTime = System.currentTimeMillis();		
		timer.start();
		flashing = true;

	}
	
	public static void stopFlashingMessage(){
		
		if(timer.isRunning())
			timer.stop();
		
		flashing = false;

	}
	
	static class ShakingFrame 
	{
	    //variables
	    private JFrame frame;
	    public static final int UPDATE_TIME = 2;
	    public static final int DURATION = 500;
	 
	    private Point primaryLocation;
	    private long startTime;
	    private Timer time;
	 
	    public ShakingFrame(JFrame f)
	    {
	        this.frame = f;
	    }//constructor
	     
	    //used on a 'ShakingFrame' object to shake the jframe
	    public void startShake()
	    {
	        primaryLocation = frame.getLocation();
	        startTime = System.currentTimeMillis();
	        time = new Timer(UPDATE_TIME,timeListener);
	        time.start();
	        shaking = true;
	    }
	     
	    //stops shake/puts back in original place
	    public void stopShake()
	    {
	        //code to stop the screen shaking
	        time.stop();
	        frame.setLocation(primaryLocation);
	        frame.setVisible(true);
	        frame.repaint();
	        shaking = false;
	    }
	     
	    private class ActionTime implements ActionListener
	    {
	         private int xOffset;//, yOffset;
	         //every interval the timer ticks, this is performed
	        @Override
	         public void actionPerformed(ActionEvent e)
	         {
	             //get elapsed time(running time)
	             long elapsedTime = System.currentTimeMillis() - startTime;
	             Random r = new Random();
	             int op = r.nextInt(5);
	              
	             if ( op > 0)
	             {
//	                change x and y offset then reallocate frame
	                xOffset = primaryLocation.x + (r.nextInt(20));
//	              yOffset = primaryLocation.y + (r.nextInt(20));
	                frame.setLocation(xOffset,primaryLocation.y);
	                frame.setVisible(true);                
	                frame.repaint();
	             }
	             else
	             {
	                //change x and y offset then reallocate frame
	                xOffset = primaryLocation.x - (r.nextInt(20));
	                //yOffset = primaryLocation.y - (r.nextInt(20));
	                frame.setLocation(xOffset, primaryLocation.y);
	                frame.setVisible(true);
	                frame.repaint();
	             }
	             //elapsedTime exceed  DURATION, so stop now
	             if(elapsedTime > DURATION)
	             {   
	                stopShake();
	             }
	         }
	    }
	    //listener/instance of ActionTime
	    private ActionTime timeListener = new ActionTime();
	}
	

	static class BlinkAction implements ActionListener{

		private JTextArea textArea;
		public BlinkAction(JTextArea textArea){
			this.textArea = textArea;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			currentTime = System.currentTimeMillis();
			timeSpent = (int) (currentTime - startTime);

			if(timeSpent <= DURATION){
				if(textArea.isOpaque()){
					textArea.setOpaque(false);
				}else{
					textArea.setOpaque(true);
				}
			}else{
				textArea.setOpaque(true);
				timer.stop();
			}
			textArea.repaint();

		}

	}
	
	static class Message{
		
		private String sender;
		private String time;
		private JTextArea textArea;
		private String message;
		
		public Message(String sender, String time, String message, JTextArea textArea){
			this.sender = sender;
			this.time = time;
			this.textArea = textArea;
			this.message = message;
		}
		
		public static void setColor(JPanel bodyPanel, JTextArea textArea, Color color){
			textArea.setBackground(color);
			bodyPanel.repaint();
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public JTextArea getTextArea() {
			return textArea;
		}

		public void setTextArea(JTextArea textArea) {
			this.textArea = textArea;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}
}
