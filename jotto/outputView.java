
import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/*
Output View displays the number of direct and indirect matches
of the archived guesses
*/
public class outputView extends JPanel implements View{
	private Model model;	
	private JTable hints = new JTable(11,2) {	//set the table to not-editable
		public boolean isCellEditable(int row, int column){			
			return false;
		}
	};	

	public outputView(Model aModel){
		super();
		this.model = aModel;
		hints.setValueAt("DIRECT",0,0);
		hints.setValueAt("INDIRECT",0,1);
		this.layoutView();

		this.model.addView(this);
	}

	private void layoutView() {
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("--Matches--");
		this.add(new layoutHelper(title), BorderLayout.NORTH);
		this.add(new layoutHelper(hints), BorderLayout.WEST);
	}
	
	public void updateView(){
		Vector <Integer> directVector = new Vector <Integer>();
		Vector <Integer> indirectVector = new Vector <Integer>();
		directVector = model.getDirectMatches();
		indirectVector = model.getIndirectMatches();
		int i;

		for (i=0;i<directVector.size();i++){
			hints.setValueAt(directVector.get(i), i+1,0);
			hints.setValueAt(indirectVector.get(i), i+1,1);
		}
		for (i=directVector.size();i<10;i++){
			hints.setValueAt("",i+1,0);
			hints.setValueAt("",i+1,1);
		}
	}
}
