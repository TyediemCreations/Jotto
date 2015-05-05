import java.util.Vector;
import java.util.Scanner;
import java.io.*;

//Dictionary reads a file containing 5 letter words and related difficulties and converts them into a list of Word.
public class Dictionary {
	private Vector<Word> words = new Vector<Word>();
    
	public Dictionary(String fileSource) throws IOException{
		Scanner in = null;
		String theWord;
		int theDifficulty;

		try {
			in  = new Scanner(new BufferedReader(new FileReader(fileSource)));
			while (in.hasNextLine()){
				theWord = in.next();
				theDifficulty = in.nextInt();	
				words.add(new Word(theWord,theDifficulty));			

				in.nextLine();
			}			
		}finally{
			in.close();
		}			
	}

	/*
	returns an arbitrary word from the dictionary
	*/
	public Word setAnswer(){
		int randIndex = (int) (Math.random() * (words.size()-1));
		Word answer = words.get(randIndex); 

		return answer;
	}
}
