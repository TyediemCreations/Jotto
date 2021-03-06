
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;


public class Model {
	private ArrayList<View> views = new ArrayList<View>();
	private helperWindow halper = new helperWindow();

	private Word answer;
	private Dictionary dictionary;
	private int numberOfGuesses = 0;
	private boolean gameOver=false;

	private Vector<String> guesses = new Vector<String>();
	private Vector<Integer> dMatches = new Vector<Integer>();
	private Vector<Integer> indMatches = new Vector<Integer>();	

	public Model(Dictionary aDictionary, helperWindow aHalper){
		dictionary = aDictionary;
		answer = dictionary.setAnswer();

		halper = aHalper;	
	}
	public void reset(){
		//call for all views to reset
		
		numberOfGuesses = 0;
		answer = dictionary.setAnswer();
		guesses.clear();
		dMatches.clear();
		indMatches.clear();
		gameOver=false;
		
		halper.reset();
		this.updateAllViews();
	}

	/** Add a new of this model. */
	public void addView(View view) {
		this.views.add(view);
		view.updateView();
	}
	/** Remove a view from this model. */
	public void removeView(View view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this model. */
	private void updateAllViews() {
		for (View view : this.views) {
			view.updateView();
		}
	}
	
	/** determines the number of direct and indirect matches a guessed string shares with the answer string */
	public void guessed(String guess){
		numberOfGuesses++;
		int dMatch, indMatch;
		dMatch = answer.directMatches(guess);
		indMatch = answer.indirectMatches(guess);
		
		guesses.add(guess);
		dMatches.add(dMatch);
		indMatches.add(indMatch);
		
		if (dMatch == 5){			//player wins
			gameOver=true;
			halper.victory();
		}
		else if (numberOfGuesses == 10){	//player loses
			gameOver=true;
			halper.failure(answer.getWord());
		}
		this.updateAllViews();
	}

	public boolean isGameOver(){return gameOver;}
	public String turnsLeft(){
		if (!gameOver) return Integer.toString(10-numberOfGuesses);
		else return "End";
	}
	public Vector<Integer> getDirectMatches(){
		return dMatches;
	}
	public Vector<Integer> getIndirectMatches(){
		return indMatches;
	}
	public Vector<String> getGuesses(){
		return guesses;
	}
}
