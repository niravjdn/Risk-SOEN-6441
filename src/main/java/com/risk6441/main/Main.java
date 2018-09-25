/**
 * 
 */
package com.risk6441.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Nirav
 *
 */
public class Main extends JFrame{

	JPanel panel;
	JButton btn1;
	JButton btn2;
	JButton btn3;
	JLabel label;
	public Main() {
		
		final JFrame frame = new JFrame("Risk");
		panel = new JPanel();
		btn1 = new JButton("Load Map & Start New Game");
		btn2 = new JButton("Edit Map");
		btn3 = new JButton("Exit");
		
		frame.setSize(300, 370);
		
		final String IMG_PATH = "src/main/resources/risk.jpg";
		try {
			BufferedImage img = ImageIO.read(new File(IMG_PATH));
	        ImageIcon icon = new ImageIcon(img);
	        label= new JLabel(icon);
	        panel.add(label);
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		
		btn3.addActionListener(new ActionListener() {

		    /* (non-Javadoc)
		     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		     */
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
		    }
		});
		
		btn1.setPreferredSize(new Dimension(300, 35));
		btn2.setPreferredSize(new Dimension(300, 35));
		btn3.setPreferredSize(new Dimension(300, 35));
		
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
