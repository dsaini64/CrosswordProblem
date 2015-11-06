import java.util.*;


public class LetterOccurances {
	
	public LetterOccurances(ArrayList<String> wordList) {
		
		occuranceList = new ArrayList<ArrayList<entry>>(26);
		for(int i = 0; i < 26; i++){
			occuranceList.add(new ArrayList<entry>());
		}
		for(int i = 0; i < wordList.size(); i++){
			String currentWord = wordList.get(i);
			for(int j = 0; j < currentWord.length(); j++){
				char currentChar =  currentWord.charAt(j);
				entry newEntry = new entry();
				newEntry.wordindex = i;
				newEntry.letterpos = j;
				occuranceList.get(currentChar - 'a').add(newEntry);
				
			}
		}
		
	}
	
	public ArrayList<entry> getListForLetter(char letter){
		return occuranceList.get(letter - 'a');
	}
	
	
	public ArrayList<ArrayList<entry>> occuranceList;
	

}


