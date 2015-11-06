
public class Crossword {

	class WordPos {
		public String word;
		public int x;
		public int y;
	}

	public char[][] grid; //char[][]
	public int size; //12
	public int length; //23
	public int width; //13
	public int smallestY;
	public int biggestY;
	public int smallestX; 
	public int biggestX;

	public Crossword(int length, int width) {

		grid = new char[length][width];


		this.length = length;
		this.width = width;

	}

	public Crossword(Crossword toBeCopied){

		this.length = toBeCopied.length;
		this.width = toBeCopied.width;
		this.biggestX = toBeCopied.biggestX;
		this.biggestY = toBeCopied.biggestY;
		this.smallestX = toBeCopied.smallestX;
		this.smallestY = toBeCopied.smallestY;


		this.grid = new char[length][width];
		for (int i = 0; i < toBeCopied.grid.length; ++i) {
			for (int j = 0; j < toBeCopied.grid[i].length; ++j) {
				this.grid[i][j] = toBeCopied.grid[i][j];
			}
		}

	}

	//length of two crosswords must be EQUAL
	public void copyback(Crossword back, int x, int y, int length, int xMultiplier, int yMultiplier){
		
		for(int i = 0; i < length; i++){
			grid[y + i * yMultiplier][x + i * xMultiplier] = back.grid[y + i * yMultiplier][x + i * xMultiplier];
		}

		biggestX = back.biggestX;
		biggestY = back.biggestY;
		smallestX = back.smallestX;
		smallestY = back.smallestY;
	}


	/*this method takes in a word,a x value, a y value, and a direction (vertical or horizontal) It checks to see if that word
	 * can be placed at that x/y value and in that direction. If it can, it places the word and returns 1. Else, it returns 0,
	 * and it doesn't place the word. 
	 */
	public int insert(String word, int x, int y, int xMultiplier, int yMultiplier) {


		if(x + word.length() * xMultiplier - smallestX > size || y + word.length() * yMultiplier - smallestY> size ||biggestX - x > size
				|| biggestY - y > size){
			return 0;
		}
		if(checkSurroundings(word, x, y, xMultiplier, yMultiplier) == false)
			return 0;
		//word is going off the grid
		if((x + word.length() * xMultiplier > width || y + word.length() * yMultiplier > length)){
			return 0;
		}

		for(int i = 0; i < word.length(); i++){
			//If the next space is not a dash or the same letter
			if(!(word.charAt(i) == grid[y + i * yMultiplier][x + i * xMultiplier]
					|| grid[y + i * yMultiplier][x + i * xMultiplier] == '-')){
				return 0;
			}

			if(grid[y + i * yMultiplier][x + i * xMultiplier] == '-'){
				if(yMultiplier == 0 && y > 0 || xMultiplier == 0 && x > 0) {
					if(!(grid[y + (yMultiplier == 0 ? -1 : i)][x + (xMultiplier == 0 ? -1 : i)] == '-')){	 //check above
						return 0;
					}
				}
				if(yMultiplier == 0 && y < length - 1 || xMultiplier == 0 && x < length - 1) {
					if(!(grid[y + (yMultiplier == 0 ? 1 : i)][x + (xMultiplier == 0 ? 1 : i)] == '-')){ //check below
						return 0;
					}							
				}
			}
		}

		for(int i = 0; i < word.length(); i++){
			grid[y + i * yMultiplier][x + i * xMultiplier] = word.charAt(i); //please finish
		}

		if(xMultiplier == 0){
			if(y < smallestY){
				smallestY = y;
			}if(y + word.length() > biggestY){
				biggestY = y + word.length();
			}
		}
		else{
			if(x < smallestX){
				smallestX = x;
			}if(x + word.length() > biggestX){
				biggestX = x + word.length();
			}
		}


		return 1;
	}

	public boolean checkSurroundings(String word, int x, int y, int xMultiplier, int yMultiplier){
		//checks to see if the space that precedes where we want to put the word is blank or not (blank is good)
		if(y - yMultiplier >= 0 && x - xMultiplier >= 0)
			if(!(grid[y - yMultiplier][x - xMultiplier] == '-'))
				return false;
		//checks to see if the space that is at the end of where we want to put the word is blank or not (blank is good)
		if(y + word.length() * yMultiplier < grid.length && x + word.length() * xMultiplier < grid.length)
			if(!(grid[y + word.length() * yMultiplier][x + word.length() * xMultiplier] == '-'))
				return false;

		//TODO
		/*for(int i = 0; i < word.length();i++){
			if(!grid.get(y - yMultiplier).get(x - xMultiplier).equals("-"))

		}*/

		return true;
	}

	public String getLengthLongestString(String[] array)
	{
		int maxLength = 0;
		String longestString = null;
		for (String s : array)
		{
			if (s.length() > maxLength)
			{
				maxLength = s.length();
				longestString = s;
			}
		}
		return longestString;
	}




	public void firstInsert(String word, int x, int y, int d){

		if(d == 0){

			for(int i = 0; i < word.length(); i++){
				grid[y][x + i] = word.charAt(i);
			}
			WordPos firstWord = new WordPos();
			firstWord.word = word;
			firstWord.x = x;
			firstWord.y = y;
			//horizontalWords.add(firstWord);

		}	

	}

	public void printCrossword(){



		for(int i = smallestY; i < biggestY; i++){
			for(int j = smallestX; j < biggestX; j++){
				System.out.print(grid[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println(smallestX);
		System.out.println(smallestY);
		System.out.println(biggestX);
		System.out.println(biggestY);
	}

	public void addDashes(){


		for(int i = 0; i < length; i++){
			for(int j = 0; j < width; j++){
				grid[i][j] = '-';
			}
		}
	}
}
