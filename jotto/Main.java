
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import java.util.Scanner;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException{		
		String fileSource = "words.txt";		
		Dictionary dictionary = new Dictionary (fileSource);

		helperWindow halper = new helperWindow();
		Model model = new Model(dictionary, halper);
		inputView view1 = new inputView(model);
		outputView view3 = new outputView(model);
		archiveView view2 = new archiveView(model);


		JFrame f = new JFrame("Jotto");
		f.getContentPane().add(view1);
		f.getContentPane().add(view2);
		f.getContentPane().add(view3);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 300);
		f.setMinimumSize(new Dimension(500,250));
		f.setVisible(true);

		JFrame halp = new JFrame("Help");
		halp.getContentPane().add(halper);
		halp.getContentPane().setLayout(new GridLayout(1,1));
		halp.setSize(200,300);
		halp.setMinimumSize(new Dimension(100,150));
		halp.setVisible(true);
		halp.setLocation(900,0);

	}
}
