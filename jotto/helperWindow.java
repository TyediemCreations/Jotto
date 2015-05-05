//package view;

import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.util.Vector;

/*
helperWindow displays all letters of the alphabet in a grid. If a cell is selected, the letter disappears/reappears.
helperWindow also provides 5 editable text fields should they be needed
*/
public class helperWindow extends JPanel{
	private Vector <JLabel> letters = new Vector <JLabel>();
	private Vector <JTextField> freeSpaces = new Vector <JTextField>();	

	public helperWindow(){
		int i;
		for (i=0;i<30;i++){letters.add(new JLabel("", JLabel.CENTER));}
		for (i=0;i<5;i++){freeSpaces.add(new JTextField(""));}

		this.reset();		

		this.layoutView();
		this.registerControllers();
	}

	private void layoutView() {
		this.setLayout(new GridLayout(7,5));
		
		for (int i=0;i<(7*5);i++){
			if (i<30) this.add(letters.get(i));
			else {
				this.add(freeSpaces.get(i-30));
			}			
		}
		
		
	}
	
	private void registerControllers() {
		for (int i=0;i<26;i++){
		final int index = i;
		this.letters.get(i).addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {				
				if (letters.get(index).getText().equals("")){
					int alphabet = (int) 'A';
					alphabet += index;
					letters.get(index).setText(Character.toString((char) alphabet));
				}
				else letters.get(index).setText("");
				
			}//upon click, letter->"", ""->letter
		});
		}
	}
	
	public void reset(){
		int alphabet = (int) 'A';		

		for (int i=0;i<(5*6);i++){
			if (i>=26) letters.get(i).setText("");
			else{
				letters.get(i).setText(Character.toString((char) alphabet));
				alphabet++;
			}
		}
		for (int i=0;i<(5*1);i++){
			freeSpaces.get(i).setText("");
			freeSpaces.get(i).setEditable(true);
		}
	}
	public void victory(){
		freeSpaces.get(0).setText("!!");
		freeSpaces.get(1).setText("Y");
		freeSpaces.get(2).setText("A");
		freeSpaces.get(3).setText("Y");
		freeSpaces.get(4).setText("!!");
		for (int i=0;i<5;i++){
			freeSpaces.get(i).setEditable(false);
		}
	}

	/** On failure, the answer is displayed to the player here */
	public void failure(String answer){
		for (int i=0;i<5;i++){
			freeSpaces.get(i).setText(Character.toString(answer.charAt(i)));
			freeSpaces.get(i).setEditable(false);
		}
	}
}
