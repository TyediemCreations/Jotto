
import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/*
Archive View displays all previously guessed strings from the model
*/
public class archiveView extends JPanel implements View{
	private Model model;
	private Vector <JTextField> archivedGuessesTF = new Vector <JTextField>(10);
	private JTable archive = new JTable(11,2) {	//set table to not-editable
		public boolean isCellEditable(int row, int column){			
			return false;
		}
	};
	
	public archiveView(Model aModel){
		super();
		this.model = aModel;
		
		archive.setValueAt("TURN",0,0);
		archive.setValueAt("GUESS",0,1);
		
		for(int i=1;i<11;i++){
			archivedGuessesTF.add(new JTextField());
			if (i<10) archive.setValueAt("  "+i+".",i,0);
			else archive.setValueAt(i+".",i,0);
		}

		this.layoutView();
		this.model.addView(this);
	}

	private void layoutView() {
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("--Previous Guesses--");
		this.add(new layoutHelper(title), BorderLayout.NORTH);
		this.add(new layoutHelper(archive), BorderLayout.EAST);

		for (int i=0;i<10;i++){
			this.archivedGuessesTF.get(i).setEditable(false);
		}
		
	}
	
	public void updateView(){
		Vector <String> tempVector = new Vector <String>();
		tempVector = model.getGuesses();
		int i;
		
		for (i=0;i<tempVector.size();i++){
			archivedGuessesTF.get(i).setText(tempVector.get(i));
		}
		for (i=tempVector.size();i<10;i++){
			archivedGuessesTF.get(i).setText("");
		}
		for (i=0;i<10;i++){
			archive.setValueAt(archivedGuessesTF.get(i).getText(),i+1,1);
		}
	}
}
