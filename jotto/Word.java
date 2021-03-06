
/*
Word saves a string and its difficulty. 
Word also contains methods to determine the number of direct and indirect
matches a guessed word contains
*/
public class Word {
	private String word;
	private int difficulty;
	private boolean [] directMatch = new boolean[5];
	private boolean [] indirectMatch = new boolean[5];
    
	public Word(String aWord, int aDifficulty){
		word = aWord;
		difficulty = aDifficulty;

		for (int i=0;i<5;i++){
			directMatch[i] = false;
			indirectMatch[i] = false;
		}
	}
	public String getWord(){
		return word;
	}
	public int directMatches(String guess){
		int direct = 0;
		for (int i=0;i<5;i++){
			if (word.charAt(i) == guess.charAt(i)){
				direct++;
				directMatch[i] = true;
			}
		}
		return direct;
	}
	public int indirectMatches(String guess){
		int indirect = 0,i,j;
		for (i=0;i<5;i++){
			for (j=0;j<5;j++){
				if (directMatch[i] || directMatch[j] || indirectMatch[j]) {}
				else if (guess.charAt(i) == word.charAt(j)) {
					indirect++;
					indirectMatch[j] = true;
					break;
				}
			}
		}

		for (i=0;i<5;i++){
			directMatch[i] = false;
			indirectMatch[i] = false;
		}
		return indirect;
	}
}
