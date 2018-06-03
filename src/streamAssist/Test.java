package streamAssist;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;



public class Test{
public static void main(String[] args) throws IOException{
//	try {
//		UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");
//		//"com.easynth.lookandfeel.EaSynthLookAndFeel");
//		//"net.infonode.gui.laf.InfoNodeLookAndFeel");
//		//"com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//		//"javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		//"com.seaglasslookandfeel.SeaGlassLookAndFeel");
//		//"javax.swing.plaf.metal.MetalLookAndFeel");
//		//"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//	} catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (InstantiationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IllegalAccessException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (UnsupportedLookAndFeelException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	Desktop desktop = null;
//	if(Desktop.isDesktopSupported()){
//		desktop = Desktop.getDesktop();
//		//JFrame.setDefaultLookAndFeelDecorated("");
//		JFileChooser fc = new JFileChooser();
//		int returnVal = fc.showOpenDialog(new JFrame());
//		if (returnVal == JFileChooser.APPROVE_OPTION){
//            File file = fc.getSelectedFile();
//            desktop.open(file);
//            System.out.println(file.getAbsolutePath());
//        }
	
//	JFrame frame = new JFrame("blink");
//	JPanel panel = new JPanel();
//	JLabel label = new JLabel("Test");
//	frame.setSize(300, 300);
//	panel.add(label);
//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	frame.setLocationRelativeTo(null);
//	frame.add(panel);
//	
//	frame.setVisible(true);
//	label.setBackground(Color.ORANGE);
//	label.setOpaque(true);
	
	PrintWriter pw = new PrintWriter(new File("server.ip"));
	pw.write("192.168.0.109");
	pw.close();
	
	//Utility.flashNewMessage(label);
		
		
//		try {
//			//desktop.open(new File("red_dot.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
	
	
//	try {
//		System.out.println(InetAddress.getLocalHost());
//	} catch (UnknownHostException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	
	
	
}
}
