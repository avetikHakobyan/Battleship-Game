/**
 * Description: This class contains attributes, methods, logic and validation to help the user interface process information about the battleship game.
 * @author Avetik Hakobyan
 * Course number: 420-G20
 * Assignment number: A02
 * Last Modification: April 16, 2022
 */
package ahakobyan_G20_A02_PhaseII;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BattleshipGame {

	protected String userName;
	protected String[] carrier;
	protected String[] battleship;
	protected String[] cruiser;
	protected String[] submarine;
	protected String[] destroyer;
	protected String[][] board;
	protected String[][] lastBoard;
	protected int row;
	protected int col;
	private final String HIT = "H";
	private final String MISS = "M";
	private final String ALREADY_GUESSED = "AB";
	protected String guess;
	private FileWriter gameWriter;
	private FileWriter gameSettingsWriter;
	private File gameSettingsReader;
	private File gameReader;
	protected int carrierLeft = 0;
	protected int battleshipLeft = 0;
	protected int cruiserLeft = 0;
	protected int submarineLeft = 0;
	protected int destroyerLeft = 0;
	protected int lastCarrierLeft;
	protected int lastBattleshipLeft;
	protected int lastCruiserLeft;
	protected int lastSubmarineLeft;
	protected int lastDestroyerLeft;
	protected int shipsLeft = 0;
	protected int lastShipsLeft;

	public BattleshipGame() {
		userName = "Unknown";
	} // BattleshipGame

	public void newGame() throws FileNotFoundException {
		if (validateGameSettings()) {
			setShips(scanGameSettings());
			setBoard(scanGameSettings());
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[row].length; col++) {
					board[row][col] = "E";
				} // for 0 < board[row].length
			} // for 0 < board.length
		}
	} // newGame()

	public void continueGame() throws FileNotFoundException {
		Scanner boardReader = scanCurrentGame();
		String[] line = null;
		if (validateRowColumnGameSettings() || validateGame()) {
			setBoard(scanCurrentGame());
			setShips(boardReader);
			setPlayerName(boardReader.nextLine());
			while (boardReader.hasNextLine()) {
				for (int i = 0; i < board.length; i++) {
					if (boardReader.hasNextLine())
						line = boardReader.nextLine().trim().split(" ");
					for (int j = 0; j < line.length; j++) {
						try {
							if (line[j].equals("E") || line[j].equals("H") || line[j].equals("M")) {
								board[i][j] = line[j];
							}
						} catch (IndexOutOfBoundsException e) {
							continue;
						}
					} // for inner
				} // for outer
			} // while (boardReader.hasNextLine())
		}
	} // continueGame()

	public void saveGame() throws IOException {
		boolean saved = false;
		Scanner reader = scanGameSettings();
		gameReader = new File("currentGame.txt");
		gameSettingsWriter = new FileWriter(gameReader);
		while (reader.hasNextLine()) {
			gameSettingsWriter.write(reader.nextLine() + "\n");
		}
		saved = true;
		gameSettingsWriter.close();
		gameWriter = new FileWriter(gameReader, true);
		gameWriter.write(userName + "\n");
		if (board != null) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					gameWriter.write(board[i][j] + " ");
				}
				gameWriter.write("\n");
			}
		}
		saved = true;
		gameWriter.close();
	} // saveGame()

	public String[][] undoLastMove() {
		shipsLeft = lastShipsLeft;
		return board = lastBoard;
	} // undoLastMove()

	public void updateLastCarrier() {
		lastCarrierLeft = carrierLeft;
	} // updateLastCarrier()

	public void updateLastBattleship() {
		lastBattleshipLeft = battleshipLeft;
	} // updateLastBattleshipLeft()

	public void updateLastCruiser() {
		lastCruiserLeft = cruiserLeft;
	} // updateLastCruiser()

	public void updateLastSubMarine() {
		lastSubmarineLeft = submarineLeft;
	} // updateLastSubMarine()

	public void updateLastDestroyer() {
		lastDestroyerLeft = destroyerLeft;
	}// updateLastDestroyer()

	public boolean isGameOver() {
		boolean isValid = false;
		if (shipsLeft == 17) {
			isValid = true;
		}
		return isValid;
	}

	public int updateShipNumber() {
		return shipsLeft++;
	} // updateShipNumber()

	public void updateLastShipsLeft() {
		lastShipsLeft = shipsLeft;
	} // updateLastShipsLeft()

	public void updateLastBoard() {
		lastBoard = new String[getRow()][getCol()];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				lastBoard[i][j] = board[i][j];
			}
		}
	} // updateLastBoard()

	public void setBoard(Scanner scan) {
		Scanner reader = scan;
		setRow(Integer.parseInt(reader.nextLine()));
		setCol(Integer.parseInt(reader.nextLine()));
		board = new String[getRow()][getCol()];
	}// setBoard()

	public Scanner scanGameSettings() throws FileNotFoundException {
		gameSettingsReader = new File("gameSettings.txt");
		Scanner reader = null;
		reader = new Scanner(gameSettingsReader);
		return reader;
	} // getFileScanner()

	public Scanner scanCurrentGame() throws FileNotFoundException {
		gameReader = new File("currentGame.txt");
		Scanner reader = null;
		reader = new Scanner(gameReader);
		return reader;
	} // scanCurrentGame()

	public void setShips(Scanner scan) throws FileNotFoundException {
		Scanner reader = scan;
		for (int j = 0; j < 2; j++) {
			if (validateGameSettings()) {
				reader.nextLine();
			}
		} // for 0 to 2
		if (reader.hasNextLine()) {
			carrier = reader.nextLine().trim().split(" ");
		}
		if (reader.hasNextLine()) {
			battleship = reader.nextLine().split(" ");
		}
		if (reader.hasNextLine()) {
			cruiser = reader.nextLine().split(" ");

		}
		if (reader.hasNextLine()) {
			submarine = reader.nextLine().split(" ");
		}
		if (reader.hasNextLine()) {
			destroyer = reader.nextLine().split(" ");
		}
	} // setShips()

	public int letterIndex(char a) {
		char start = 'a';
		int numOfLetter = Character.toLowerCase(a) - start;
		return numOfLetter;
	} // getLetterIndex(char)

	public int returnTheRow(String ship) {
		int row;
		String rowStr;
		rowStr = ship.replaceAll("[^0-9]", "");
		row = Integer.parseInt(rowStr);
		return row;
	} // returnTheRow()

	public String indexToLetter(int col) {
		char letter = ' ';
		letter = (char) (col + 65);
		return Character.toString(letter);
	} // indexToLetter

	public String returnCompleteRowCol(int row, String col) {
		String rowStr = Integer.toString(row + 1);
		String totalRowCol = col + rowStr;
		return totalRowCol;
	}

	public int returnTheCol(String ship) {
		int col;
		String colStr;
		char charCol;
		colStr = ship.replaceAll("[0-9]", "");
		charCol = colStr.charAt(0);
		col = letterIndex(charCol);
		return col;
	} // returnTheCol()

	public void continueGameShipsLeft() {
		// for carrier
		for (int i = 0; i < carrier.length; i++) {
			if (board[returnTheRow(carrier[i]) - 1][returnTheCol(carrier[i])].equals(HIT)) {
				carrierLeft++;
			}
		}
		updateLastCarrier();
		// for battleship
		for (int i = 0; i < battleship.length; i++) {
			if (board[returnTheRow(battleship[i]) - 1][returnTheCol(battleship[i])].equals(HIT)) {
				battleshipLeft++;
			}
		}
		updateLastBattleship();
		// for cruiser
		for (int i = 0; i < cruiser.length; i++) {
			if (board[returnTheRow(cruiser[i]) - 1][returnTheCol(cruiser[i])].equals(HIT)) {
				cruiserLeft++;
			}
		}
		updateLastCruiser();
		// for submarine
		for (int i = 0; i < submarine.length; i++) {
			if (board[returnTheRow(submarine[i]) - 1][returnTheCol(submarine[i])].equals(HIT)) {
				submarineLeft++;
			}
		}
		updateLastSubMarine();
		// for destroyer
		for (int i = 0; i < destroyer.length; i++) {
			if (board[returnTheRow(destroyer[i]) - 1][returnTheCol(destroyer[i])].equals(HIT)) {
				destroyerLeft++;
			}
		}
		updateLastDestroyer();
	} // continueGameShipsLeft()

	public String checkGuess(int row, int col, String in) {
		boolean isItHIT = false;
		String output = MISS;
		String guess = in;

		if (alreadyGuessed(row, col)) {

			output = ALREADY_GUESSED;

		} else {

			for (int i = 0; i < carrier.length && !isItHIT; i++) {
				if (carrier[i].equalsIgnoreCase(guess.toUpperCase())) {
					output = HIT;
					isItHIT = true;
					updateLastCarrier();
					carrierLeft++;
				}
			}

			for (int i = 0; i < cruiser.length && !isItHIT; i++) {
				if (cruiser[i].equalsIgnoreCase(guess.toUpperCase())) {
					output = HIT;
					isItHIT = true;
					updateLastCruiser();
					cruiserLeft++;
				}
			}

			for (int i = 0; i < battleship.length && !isItHIT; i++) {
				if (battleship[i].equalsIgnoreCase(guess.toUpperCase())) {
					output = HIT;
					isItHIT = true;
					updateLastBattleship();
					battleshipLeft++;
				}
			}

			for (int i = 0; i < submarine.length && !isItHIT; i++) {
				if (submarine[i].equalsIgnoreCase(guess.toUpperCase())) {
					output = HIT;
					isItHIT = true;
					updateLastSubMarine();
					submarineLeft++;
				}
			}

			for (int i = 0; i < destroyer.length && !isItHIT; i++) {
				if (destroyer[i].equalsIgnoreCase(guess.toUpperCase())) {
					output = HIT;
					isItHIT = true;
					updateLastDestroyer();
					destroyerLeft++;
				}
			}
		}
		return output;
	} // checkGuess()

	public void updateBoard(int row, int col, String in) {
		board[row][col] = in;
	}

	public void setShipNumber(int newShipNumber) {
		shipsLeft = newShipNumber;
	}

	public int getShipNumber() {
		return shipsLeft;
	}

	public void setPlayerName(String name) {
		userName = name;
	} // setPlayerName()

	public String getPlayerName() {
		return userName;
	} // getPlayerName()

	public void setRow(int parRow) {
		row = parRow;
	} // setRow(int)

	public void setCol(int parCol) {
		col = parCol;
	} // setCol(int)

	public String[][] getBoard() {
		return board;
	} // getBoard()

	public int getRow() {
		return row;
	} // getRow()

	public int getCol() {
		return col;
	} // getCol()

	public String[] getCarrier() {
		return carrier;
	} // getCarrier()

	public String[] getCruiser() {
		return cruiser;
	} // getCruiser()

	public String[] getBattleship() {
		return battleship;
	} // battleship()

	public String[] getSubmarine() {
		return submarine;
	} // getSubmarine()

	public String[] getDestroyer() {
		return destroyer;
	} // getDestroyer()

	public char lastLetter(int col) {
		char startLetter = 'A';
		int finish = col - 1;
		int i = 0;
		while (i < finish) {
			startLetter++;
			i++;
		}
		return startLetter;
	} // getFinishLetter()

	public boolean alreadyGuessed(int row, int col) {
		boolean isValid = false;
		if (board[row][col].equals("H") || board[row][col].equals("M"))
			isValid = true;

		return isValid;
	} // alreadyGuessed

	public boolean validateRowColumnGameSettings() {
		boolean isValid = false;
		if (col > 0 && col <= 26 && row > 5) {
			isValid = true;
		}
		return isValid;
	} // validateRowColumnGameSettings()

	public boolean validateBoard() {
		boolean isValid = true;
		if (board != null) {
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[row].length; col++) {
					if (board[row][col] == null || board[row][col].trim().equals("")) {
						isValid = false;
					} // if (board[row][col] != null)
				} // for inner
			} // for outer
		} else {
			isValid = false;
		}

		return isValid;
	} // validateBoard()

	public static boolean validateGameInput(String in) {
		boolean inputIsValid = false;
		if (in.equalsIgnoreCase("n") || in.equalsIgnoreCase("r")) {
			inputIsValid = true;
		}
		return inputIsValid;
	} // validateGameInput(String)

	public static boolean validateName(String in) {
		boolean isThere = false;
		if (in != null) {
			if (!in.isEmpty())
				isThere = true;
		}
		return isThere;
	} // validateName(String)

	public boolean validateGameSettings() throws FileNotFoundException {
		boolean isValid = true;
		Scanner gameSettingsReader = scanGameSettings();
		if (gameSettingsReader.hasNextLine()) {
			while (gameSettingsReader.hasNextLine()) {
				if (gameSettingsReader.nextLine().equals(""))
					isValid = false;
			}
		} else
			isValid = false;
		return isValid;
	} // validateGameSettings()

	public boolean validateColumn(String input, char maxCol) {
		boolean isValid = true;
		char charInput = 0;
		if (!input.equals("")) {
			charInput = input.charAt(0);
			if (charInput > maxCol || charInput < 'A') {
				isValid = false;
			}
		} else {
			isValid = false;
		}
		return isValid;
	} // validateColumn(String, char)

	public boolean validateRow(String rowStr, int maxRow) {
		boolean isValid = false;
		int row = 0;
		try {
			row = Integer.parseInt(rowStr);
		} catch (NumberFormatException e) {
			isValid = false;
		}
		if (row <= maxRow) {
			isValid = true;
		}
		return isValid;
	} // validateRow(String, int)

	public boolean validateGame() throws FileNotFoundException {
		boolean isValid = true;
		Scanner gameScanner = scanCurrentGame();
		try {
			if (gameScanner != null) {
				while (gameScanner.hasNextLine()) {
					if (gameScanner.nextLine().equals("")) {
						isValid = false;
					}
				}
			} else {
				isValid = false;
			}
		} catch (NoSuchElementException e) {
			isValid = false;
		}
		return isValid;
	} // validateGame()

	public boolean validateMove(char col, int row, char maxCol, int minRow, int maxRow, boolean presentRow,
			boolean presentCol) {
		boolean isValid = false;
		if (!Character.isWhitespace(col) && Character.toUpperCase(col) <= maxCol && row <= (maxRow) && row >= minRow
				&& presentRow && presentCol) {
			isValid = true;
		}
		return isValid;
	} // validateMove()

	public boolean validateShips() {
		boolean isValid = true;
		if (!validateCarrier() || !validateCruiser() || !validateBattleship() || !validateSubmarine()
				|| !validateDestroyer()) {
			isValid = false;
		}
		String[] all = new String[17];
		for (int i = 0; i < carrier.length; i++) {
			all[i] = carrier[i];
		}
		for (int i = 0; i < battleship.length; i++) {
			all[i + 5] = battleship[i];
		}
		for (int i = 0; i < cruiser.length; i++) {
			all[i + 9] = cruiser[i];
		}
		for (int i = 0; i < submarine.length; i++) {
			all[i + 12] = submarine[i];
		}
		for (int i = 0; i < destroyer.length; i++) {
			all[(all.length - 1) - i] = destroyer[i];
		}
		for (int i = 0; i < all.length - 1; i++) {
			for (int j = i + 1; j < all.length; j++) {
				if (all[i] != null) {
					if (all[i].equals(all[j])) {
						isValid = false;
					}
				}
			}
		}
		return isValid;
	} // validateShips();

	public boolean validateCarrier() {
		boolean isValid = true;
		String col;
		String row;
		char charCol;
		int rowInt;
		int i;
		for (i = 0; i < carrier.length; i++) {
			col = carrier[i].replaceAll("[0-9]", "");
			row = carrier[i].replaceAll("[^0-9]", "");
			charCol = col.charAt(0);
			rowInt = Integer.parseInt(row);
			if (charCol < 'A' || charCol > lastLetter(getCol()) || rowInt < 1 || rowInt > getRow()) {
				isValid = false;
			}
		}
		if (i != 5) {
			isValid = false;
		}
		return isValid;
	} // validateCarrier()

	public boolean validateCruiser() {
		boolean isValid = true;
		String col;
		String row;
		char charCol;
		int rowInt;
		int i;
		for (i = 0; i < cruiser.length; i++) {
			col = cruiser[i].replaceAll("[0-9]", "");
			row = cruiser[i].replaceAll("[^0-9]", "");
			charCol = col.charAt(0);
			rowInt = Integer.parseInt(row);
			if (charCol < 'A' || charCol > lastLetter(getCol()) || rowInt < 1 || rowInt > getRow()) {
				isValid = false;
			}
		}
		if (i != 3) {
			isValid = false;
		}
		return isValid;
	}

	public boolean validateBattleship() {
		boolean isValid = true;
		String col;
		String row;
		char charCol;
		int rowInt;
		int i;
		for (i = 0; i < battleship.length; i++) {
			col = battleship[i].replaceAll("[0-9]", "");
			row = battleship[i].replaceAll("[^0-9]", "");
			charCol = col.charAt(0);
			rowInt = Integer.parseInt(row);
			if (charCol < 'A' || charCol > lastLetter(getCol()) || rowInt < 1 || rowInt > getRow()) {
				isValid = false;
			}
		}
		if (i != 4) {
			isValid = false;
		}
		return isValid;
	} // validateCruiser()

	public boolean validateSubmarine() {
		boolean isValid = true;
		String col;
		String row;
		char charCol;
		int rowInt;
		int i;
		for (i = 0; i < submarine.length; i++) {
			col = submarine[i].replaceAll("[0-9]", "");
			row = submarine[i].replaceAll("[^0-9]", "");
			charCol = col.charAt(0);
			rowInt = Integer.parseInt(row);
			if (charCol < 'A' || charCol > lastLetter(getCol()) || rowInt < 1 || rowInt > getRow()) {
				isValid = false;
			}
		}
		if (i != 3) {
			isValid = false;
		}
		return isValid;
	}

	public boolean validateDestroyer() {
		boolean isValid = true;
		String col;
		String row;
		char charCol;
		int rowInt;
		int i;
		for (i = 0; i < destroyer.length; i++) {
			col = destroyer[i].replaceAll("[0-9]", "");
			row = destroyer[i].replaceAll("[^0-9]", "");
			charCol = col.charAt(0);
			rowInt = Integer.parseInt(row);
			if (charCol < 'A' || charCol > lastLetter(getCol()) || rowInt < 1 || rowInt > getRow()) {
				isValid = false;
			}
		}
		if (i != 2) {
			isValid = false;
		}
		return isValid;
	}

	public boolean validateRowGameFile() throws FileNotFoundException {
		boolean isValid = true;
		Scanner reader = scanCurrentGame();
		int counter = 0;
		for (int i = 0; i < 8; i++) {
			reader.nextLine();
		} // for 0 to 8
		while (reader.hasNextLine()) {
			reader.nextLine();
			counter++;
		}
		if (counter > getRow() || counter < getRow()) {
			isValid = false;
		}
		return isValid;
	}
}
