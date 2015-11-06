import java.util.*;

public class main {

	public main() {

	}

	class letterIndex {
		public String word;
		public int index;
	}
	class searchQueueItem {
		public int x;
		public int y;
		public char c;
		public int d;
	}
	List<List<letterIndex>> letterIndices = new ArrayList<List<letterIndex>>(26);
	String [] wordArray = {"blah", "application","mama", "annoying"};
	List<searchQueueItem> searchQ;

	public static LinkedList<position> searchQueue = new LinkedList<position>();
	public static boolean crosswordFound = false;

	public static void main(String[] args) {

		//String[] words = {"abate", "babab", "bcde", "fa", "boac", "draft","ep","po","at"};
		//String[] words = {"ab", "bc", "cd", "de", "ef", "fg", "gh", "hi"};
		//		String[] words = {"abc", "cwk", "yzk", "axy"};
//		String[] words = {"fornow", "canine", "inaction", "noes", "ornate", 
//				"fiction", "ringabell", "dillydally", "logical", "alaska", "letalone", 
//				"softspot", "over", "thailand", "pious", "therapy", "snub"};
		String[] words = {"fornow", "canine", "inaction", "noes", "ornate", "fiction", "ringabell", "dillydally", "logical"};
		//String[] words = {"cambodia", "leash", "binder", "android", "max"};
		//		String[] words = {"abcdbfghij", "befeeklmno", "abcdefghij", "abcdffghij"};
		//String[] words = {"abc", "adc","cef", "eyz"}; 
		ArrayList<String> wordList = new ArrayList<String> (Arrays.asList(words));


		//	System.out.println(findMinimumLengthByLetterCount(wordList));


		int length = 0;
		int indexLongest = -1;
		for(int i = 0; i < words.length; i++){
			if(wordList.get(i).length() > length){
				indexLongest = i;
				length = wordList.get(i).length();
			}
		}


		int minLen = findMinimumLengthByLetterCount(wordList);

		if(minLen > length){
			length = minLen;
		}

		//length = 13;
		System.out.println("The current length of the crossword is: " + length);


		String longestWord = wordList.get(indexLongest);

		wordList.remove(longestWord);

		lo = new LetterOccurances(wordList);

		//Adding first word horizontally
		//loop counter
		int counter = 0;
		//boolean crosswordFound = false;
		outer : while(!crosswordFound){

			Crossword test = new Crossword(2 * (length + counter) - 1, 2 * (length + counter) - 1);
			test.addDashes();
			test.firstInsert(longestWord, test.width/2, test.length/2, 0);
			test.size = length + counter;
			test.smallestX = test.width/2;
			test.smallestY = test.length/2;
			test.biggestX = test.width/2 + longestWord.length();
			test.biggestY = test.length/2 + 1;
			searchQueue.clear();
			usedWords = new ArrayList<Boolean>(wordList.size());
			for(int i = 0; i < wordList.size(); ++i) {
				usedWords.add(false);
			}
			for (int i = 0; i < longestWord.length(); ++i) {
				searchQueue.add(new position(test.width/2 + i, test.length/2, 1));
			}
			if(recursiveSearch(test, wordList, 0, wordList.size()) == 1){
				test.printCrossword();;
				break outer;
			}

			counter++;
			System.out.println(counter);
		}
	}


	/*this method takes in a crossword and a list of words and tries to place all the words into the crossword. 
	 * It returns 1 if it was successful and modifies the grid, and returns 0, leaving the grid 
	 * unchanged, if it wasn't. 
	 */


	public static int recursiveSearch(Crossword crossword, ArrayList<String> wordList, int queueIndex, int wordsRemaining) {
		//System.out.println("-----in recursive call-----");
		Crossword CrosswordCopy = new Crossword(crossword);

		if(wordsRemaining == 0){
			//crossword.printCrossword();
			return 1;

		}else{
			for ( ; queueIndex < searchQueue.size(); ++queueIndex) {
				position intersect = searchQueue.get(queueIndex);
				char letter = crossword.grid[intersect.y][intersect.x];
				//for (int currentWordIndex = 0; currentWordIndex < wordsRemaining.size(); ++currentWordIndex) {
				ArrayList<entry> validWords = lo.getListForLetter(letter);
				for(entry e: validWords){
					String currentWord = wordList.get(e.wordindex);
					if(!usedWords.get(e.wordindex).booleanValue()){
						if(intersect.direction == 0){
							int x = intersect.x - e.letterpos;
							int y = intersect.y;
							if(x >= 0 && crossword.insert(currentWord, x, y, 1, 0) == 1) {
								for(int i = 0; i < currentWord.length(); i++ ){
									searchQueue.add(new position(x + i, y, 1));
								}
								usedWords.set(e.wordindex, true);
								if(recursiveSearch(crossword, wordList, queueIndex + 1, wordsRemaining - 1) == 1){
									return 1;
								}
								usedWords.set(e.wordindex, false);
								for(int i = 0; i < currentWord.length(); i++){
									searchQueue.removeLast();
								}
								crossword.copyback(CrosswordCopy, x, y, currentWord.length(), 1, 0); // only revert spaces where word that was added
							}
						}else{
							int x = intersect.x;
							int y = intersect.y - e.letterpos;
							if(y >= 0 && crossword.insert(currentWord, x, y, 0, 1) == 1) {
								for(int i = 0; i < currentWord.length(); i++ ){
									searchQueue.add(new position(x, y + i, 0));
								}
								usedWords.set(e.wordindex, true);
								if(recursiveSearch(crossword, wordList, queueIndex + 1, wordsRemaining - 1) == 1){
									return 1;
								}
								usedWords.set(e.wordindex, false);
								for(int i = 0; i < currentWord.length(); i++){
									searchQueue.removeLast();
								}
								crossword.copyback(CrosswordCopy, x, y, currentWord.length(), 0 , 1);	
							}
						}
					}
				}
			}
		}
		return 0;
	}

	private static LetterOccurances lo;
	public static ArrayList<Boolean> usedWords;

	//Checks to see if there are possibilities 
	public boolean noPossibilities(String word, Crossword crossword){

		for(int i = 0; i < word.length(); i++){
			for(int j = 0; j < crossword.grid.length; j++){
				for(int k = 0; k < crossword.length; k++){
					if(String.valueOf(word.charAt(i)).equals(crossword.grid[j][k])){

						return false;
					}

				}
			}	
		}
		return true;
	}

	/* This method finds the minimum length of the crossword according to the number of characters that need to 
	 * be placed, then returns this minimum length. 
	 */

	public static int findMinimumLengthByLetterCount(ArrayList<String> words){

		int totalCharacterCount = 0;
		for(String s : words){
			totalCharacterCount += s.length();
		}


		int i = 3;
		while(Math.pow(i, 2) - Math.pow(i/2, 2) + Math.pow((i+1)/2, 2) < totalCharacterCount){
			i++;
		}
		return i;
	}


	public static class position{
		public int x;
		public int y;
		public int direction;
		public position(int x, int y, int direction){
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

	}
}
