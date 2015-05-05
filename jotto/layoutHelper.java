import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class layoutHelper extends JPanel {
	public layoutHelper(JLabel inputPrompt, JTextField guessTF, JLabel duration, JTextField guessesLeftTF){
		this.setLayout(new GridBagLayout());
		this.add(inputPrompt);
		this.add(guessTF);
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.SOUTH;

		this.add(duration, gc);
		
		gc.fill = GridBagConstraints.VERTICAL;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;

		this.add(guessesLeftTF, gc);
	}
	public layoutHelper(JTable table){
		this.setLayout(new GridBagLayout());
		this.add(table);
	}
	public layoutHelper(JLabel title){
		this.setLayout(new GridBagLayout());
		this.add(title);
	}
}
