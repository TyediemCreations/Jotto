
import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/*
The input view displays the number of turns left in the game,
and acts as the controller for the model
*/
public class inputView extends JPanel implements View{
	private Model model;
	private JTextField guessTF = new JTextField(5);
	private JTextField guessesLeftTF = new JTextField(5);
	private JLabel restart = new JLabel("");
	
	public inputView(Model aModel){
		super();
		this.model = aModel;
		this.layoutView();
		this.registerControllers();

		this.model.addView(this);
	}

	private void layoutView() {
		BorderLayout layout = new BorderLayout();
		JLabel title = new JLabel("JOTTO");
		JLabel inputPrompt = new JLabel("Guess:");
		JLabel duration = new JLabel("Turns Left: ");
		this.guessesLeftTF.setEditable(false);

		this.setLayout(layout);
		this.add(new layoutHelper(title), BorderLayout.NORTH);
		this.add(new layoutHelper(inputPrompt,this.guessTF,duration,this.guessesLeftTF), BorderLayout.CENTER);
		this.add(new layoutHelper(restart),BorderLayout.SOUTH);
	}
	private void registerControllers() {
		this.guessTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String guess = (guessTF.getText()).toUpperCase();
				if (model.isGameOver()){
					if (guess.equals("RESET")) {
						model.reset();
						restart.setText("");
					}
			 		return;
				}
				if (guess.length() == 5){
					model.guessed(guess);
				}
			}
		});
	}
	public void updateView(){
		guessesLeftTF.setText(model.turnsLeft());
		if (model.isGameOver()) restart.setText("Guess 'RESET' to Play Again");
	}
}
