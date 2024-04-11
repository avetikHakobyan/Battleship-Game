/**
 * Description: This class contains the visual user interface for the battleship game. It has attributes and methods to help the frame visually represent the battleship game. The frame contains the board with buttons in the center and the ships information on the right-hand side
 * @author Avetik Hakobyan
 * Course number: 420-G20
 * Assignment number: A02
 * Last Modification: May 1, 2022
 */
package ahakobyan_G20_A02_PhaseII;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BattleshipFrame extends JFrame implements ActionListener {
	private JPanel boardPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newGameMenuItem = new JMenuItem("New Game");
	private JMenuItem restoreGameMenuItem = new JMenuItem("Restore Game");
	private JMenuItem saveGameMenuItem = new JMenuItem("Save Game");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	private JMenu helpMenu = new JMenu("Help");
	private BattleshipGame bGame = new BattleshipGame();
	private final String HIT = "H";
	private final String MISS = "M";
	private String userName;
	private JButton board[][];
	private JButton rowNumber[];
	private JButton columnLetters[];
	private JButton btnEmpty = new JButton();
	protected static boolean nameIsEntered = false;
	private final JMenuItem InstructionsMenuItem = new JMenuItem("How to Play");
	private final JMenuItem aboutMenuItem = new JMenuItem("About");
	private final JButton btnUndo = new JButton("Undo");
	private final JPanel shipsInfoPanel = new JPanel();
	private final JLabel lblNumberLeft = new JLabel();
	private final JLabel lblCarrier = new JLabel("");
	private final JLabel lblBattleship = new JLabel("");
	private final JLabel lblCruiser = new JLabel("");
	private final JLabel lblSubmarine = new JLabel("");
	private final JLabel lblDestroyer = new JLabel("");
	private int lastShipSunk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleshipFrame frame = new BattleshipFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // main

	/**
	 * Create the frame.
	 */
	public BattleshipFrame() {
		setTitle("Heritage Battleship");
		setBounds(100, 100, 1444, 811);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		boardPanel.setBounds(20, 61, 1302, 678);
		getContentPane().add(boardPanel);
		boardPanel.setLayout(new GridLayout(1, 0, 0, 0));
		btnUndo.setVisible(false);
		btnUndo.setEnabled(false);
		btnUndo.setBounds(10, 28, 89, 23);

		getContentPane().add(btnUndo);
		JLabel lblWelcome = new JLabel("Welcome to Heritage Battleship");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(569, 11, 228, 14);
		getContentPane().add(lblWelcome);
		shipsInfoPanel.setBounds(1322, 61, 106, 678);

		getContentPane().add(shipsInfoPanel);
		shipsInfoPanel.setLayout(null);
		setJMenuBar(menuBar);

		lblNumberLeft.setBounds(9, 24, 77, 14);
		shipsInfoPanel.add(lblNumberLeft);

		lblCarrier.setVisible(false);
		lblCarrier.setIcon(new ImageIcon("Carrier.png"));
		lblCarrier.setBounds(10, 73, 121, 14);
		shipsInfoPanel.add(lblCarrier);

		lblBattleship.setVisible(false);
		lblBattleship.setIcon(new ImageIcon("Battleship.png"));
		lblBattleship.setBounds(9, 98, 86, 14);

		shipsInfoPanel.add(lblBattleship);
		lblCruiser.setVisible(false);
		lblCruiser.setIcon(new ImageIcon("Cruiser.png"));
		lblCruiser.setBounds(9, 123, 77, 14);
		shipsInfoPanel.add(lblCruiser);

		lblSubmarine.setVisible(false);
		lblSubmarine.setIcon(new ImageIcon("Submarine.png"));
		lblSubmarine.setBounds(9, 148, 77, 14);
		shipsInfoPanel.add(lblSubmarine);

		lblDestroyer.setIcon(new ImageIcon("Destroyer.png"));
		lblDestroyer.setBounds(9, 173, 77, 14);
		lblDestroyer.setVisible(false);
		shipsInfoPanel.add(lblDestroyer);

		menuBar.add(fileMenu);

		btnUndo.addActionListener(this);

		newGameMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		fileMenu.add(newGameMenuItem);
		newGameMenuItem.addActionListener(this);
		fileMenu.add(restoreGameMenuItem);
		restoreGameMenuItem.addActionListener(this);
		fileMenu.add(saveGameMenuItem);
		saveGameMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(this);
		menuBar.add(helpMenu);
		helpMenu.add(InstructionsMenuItem);
		helpMenu.add(aboutMenuItem);
		InstructionsMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
	} // BattleshipFrame() constructor

	public char getLastLetter(int col) {
		return bGame.lastLetter(col);
	} // getFinishLetter()

	public void initializeNewGame() {
		try {
			if (bGame.validateGameSettings()) {
				bGame.newGame();
				boardPanel.removeAll();
				boardPanel.setLayout(new GridLayout(bGame.getRow() + 1, bGame.getCol()));
				if (!bGame.validateShips() || !bGame.validateRowColumnGameSettings()) {
					JOptionPane.showMessageDialog(this,
							"Sorry, the game settings file is corrupted, the program will now",
							"Corrupted Game Settings", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				} else {
					do {
						userName = JOptionPane.showInputDialog(this, "Enter your name");
						bGame.setPlayerName(userName);
					} while (userName != null && !BattleshipGame.validateName(userName));
					if (userName == null) {
						;
					} else {
						// TODO
						lblNumberLeft.setText("Ships Left: " + Integer.toString(17 - bGame.shipsLeft));
						lblCarrier.setVisible(true);
						lblBattleship.setVisible(true);
						lblCruiser.setVisible(true);
						lblSubmarine.setVisible(true);
						lblDestroyer.setVisible(true);
						createBoard();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Sorry, the game settings file is corrupted, the program will now",
						"Corrupted Game Settings", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(this,
					"Couldn't read the file " + e.getMessage()
							+ "\nPlease make sure it exists and re-run the program again.",
					"Corrupted Game Settings", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this,
					"Couldn't read the file " + e.getMessage()
							+ "\nPlease make sure it exists and re-run the program again.",
					"Corrupted Game Settings", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	} // initializeNewGame()

	public void restoreGame() throws FileNotFoundException {

		if (bGame.validateGame()) {
			bGame.continueGame();
			boardPanel.setLayout(new GridLayout(bGame.getRow() + 1, bGame.getCol()));
			if (bGame.validateShips() && bGame.validateRowGameFile() && bGame.validateBoard()) {
				bGame.continueGameShipsLeft();
				createBoard();
				analyzeSavedGame();
			} else {
				alternativeResume();
			}
		} else if (!bGame.validateBoard()) {
			alternativeResume();
		} else {
			alternativeResume();
		}
	} // restoreGame()

	private void analyzeSavedGame() {
		for (int row = 0; row < bGame.board.length; ++row) {
			for (int col = 0; col < bGame.board[row].length; ++col) {
				if (bGame.board[row][col].equals(HIT)) {
					markHit(row, col);
					bGame.updateShipNumber();
				} // if (bGame.board[row][col].equals(HIT))
				else if (bGame.board[row][col].equals(MISS)) {
					markMiss(row, col);
				}
			} // for coloumns
		} // for rows
		lblNumberLeft.setText("Ships Left: " + Integer.toString(17 - bGame.shipsLeft));
		analyzeShipsLeft();
	} // analyzeSavedGame()

	public void analyzeShipsLeft() {
		lblCarrier.setVisible(true);
		lblBattleship.setVisible(true);
		lblCruiser.setVisible(true);
		lblSubmarine.setVisible(true);
		lblDestroyer.setVisible(true);
		// TODO: Fix problem when undoing a move, PROBLEM: all ships becomes enabled
		// instead of just one
		if (bGame.carrierLeft == 5) {
			lblCarrier.setEnabled(false);
			bGame.carrierLeft = -1;
		}
		if (bGame.battleshipLeft == 4) {
			lblBattleship.setEnabled(false);
			bGame.battleshipLeft = -1;
		}
		if (bGame.cruiserLeft == 3) {
			lblCruiser.setEnabled(false);
			bGame.cruiserLeft = -1;
		}
		if (bGame.submarineLeft == 3) {
			lblSubmarine.setEnabled(false);
			bGame.submarineLeft = -1;
		}
		if (bGame.destroyerLeft == 2) {
			lblDestroyer.setEnabled(false);
			bGame.destroyerLeft = -1;
		}
		repaint();
	} // analyzeShipsLeft()

	public void undoLastShipSunk() {
		lblCarrier.setVisible(true);
		lblBattleship.setVisible(true);
		lblCruiser.setVisible(true);
		lblSubmarine.setVisible(true);
		lblDestroyer.setVisible(true);
		if (lastShipSunk == 5) {
			bGame.carrierLeft = bGame.lastCarrierLeft;
			lblCarrier.setEnabled(true);
		}
		if (lastShipSunk == 4) {
			bGame.battleshipLeft = bGame.lastBattleshipLeft;
			lblBattleship.setEnabled(true);
		}
		if (lastShipSunk == 31) {
			bGame.cruiserLeft = bGame.lastCruiserLeft;
			lblCruiser.setEnabled(true);
		}
		if (lastShipSunk == 32) {
			bGame.submarineLeft = bGame.lastSubmarineLeft;
			lblSubmarine.setEnabled(true);
		}
		if (lastShipSunk == 2) {
			bGame.destroyerLeft = bGame.lastDestroyerLeft;
			lblDestroyer.setEnabled(true);
		}
		lastShipSunk = 0;
		repaint();
	}

	public void alternativeResume() {
		int answer = JOptionPane.showConfirmDialog(this,
				"Saved game file is corrupted, would you like to start a new game?\n*Note* Choising \"No\" will end the program",
				"Corrupted Save Game", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		if (answer == 0) {
			initializeNewGame();
		} else if (answer == 1) {
			System.exit(0);
		}
	} // alternativeResume()

	public void createBoard() {
		int rowCounter = 1;
		char startLetter = 'A';
		board = new JButton[bGame.getRow()][bGame.getCol()];
		rowNumber = new JButton[bGame.getRow()];
		columnLetters = new JButton[bGame.getCol()];
		btnEmpty.setVisible(false);
		boardPanel.add(btnEmpty);
		for (int i = 0; i < board[0].length; i++) {
			columnLetters[i] = new JButton("");
			boardPanel.add(columnLetters[i]);
			columnLetters[i].setEnabled(false);
			columnLetters[i].setText(Character.toString(startLetter++));
		}
		for (int row = 0; row < board.length; ++row) {
			rowNumber[row] = new JButton("");
			rowNumber[row].setEnabled(false);
			rowNumber[row].setText(Integer.toString(rowCounter++));
			boardPanel.add(rowNumber[row]);
			for (int col = 0; col < board[row].length; ++col) {
				board[row][col] = new JButton("");
				boardPanel.add(board[row][col]);
				board[row][col].setEnabled(true);
				board[row][col].addActionListener(this);
			} // for coloumns
		} // for rows
	} // createBoard()

	public void evaluateMove(int row, int col, String in) {
		bGame.updateLastBoard();
		String guess = bGame.checkGuess(row, col, in);
		if (guess.equals("H")) {
			bGame.updateShipNumber();
			lblNumberLeft.setText("Ships Left: " + Integer.toString(17 - bGame.shipsLeft));
			markHit(row, col);
			if (bGame.carrierLeft == 5) {
				lblCarrier.setEnabled(false);
				JOptionPane.showMessageDialog(this, "You've sunk the Carrier", "Carrier Sunk",
						JOptionPane.INFORMATION_MESSAGE);
				lastShipSunk = 5;
				bGame.carrierLeft = -1;
			} else if (bGame.battleshipLeft == 4) {
				lblBattleship.setEnabled(false);
				JOptionPane.showMessageDialog(this, "You've sunk the Battleship", "Battleship Sunk",
						JOptionPane.INFORMATION_MESSAGE);
				lastShipSunk = 4;
				bGame.battleshipLeft = -1;
			} else if (bGame.cruiserLeft == 3) {
				lblCruiser.setEnabled(false);
				JOptionPane.showMessageDialog(this, "You've sunk the Cruiser", "Cruiser Sunk",
						JOptionPane.INFORMATION_MESSAGE);
				lastShipSunk = 31;
				bGame.cruiserLeft = -1;
			} else if (bGame.submarineLeft == 3) {
				lblSubmarine.setEnabled(false);
				JOptionPane.showMessageDialog(this, "You've sunk the Submarine", "Submarine Sunk",
						JOptionPane.INFORMATION_MESSAGE);
				lastShipSunk = 32;
				bGame.submarineLeft = -1;
			} else if (bGame.destroyerLeft == 2) {
				lblDestroyer.setEnabled(false);
				JOptionPane.showMessageDialog(this, "You've sunk the Destroyer", "Destroyer Sunk",
						JOptionPane.INFORMATION_MESSAGE);
				lastShipSunk = 2;
				bGame.destroyerLeft = -1;
			}
		} else if (guess.equals("M")) {
			markMiss(row, col);
			lastShipSunk = 0;
		}
		if (bGame.isGameOver()) {
			JOptionPane.showMessageDialog(this, "Congratulations! You won !", "Win", JOptionPane.INFORMATION_MESSAGE);
			System.exit(-1);
		}
		bGame.updateBoard(row, col, guess);
	} // checkGuess(int, int, String)

	public void markHit(int row, int col) {
		board[row][col].setEnabled(false);
		board[row][col].setBackground(Color.GREEN);
	} // markHit(int, int)

	public void markMiss(int row, int col) {
		board[row][col].setEnabled(false);
		board[row][col].setBackground(Color.RED);
	} // markMiss(int, int)

	public void saveGame() {
		boolean saved = false;
		try {
			if (board != null) {
				bGame.saveGame();
				saved = true;
				if (saved)
					JOptionPane.showMessageDialog(this, "Game state has been saved successfully!", "Save Game Success",
							JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Unable to save game", "Save Game failed", JOptionPane.ERROR_MESSAGE);
		}
	} // saveGame()

	@Override
	public void actionPerformed(ActionEvent e) {
		btnUndo.setEnabled(false);
		if (e.getSource() == newGameMenuItem) {
			initializeNewGame();
		} else if (e.getSource() == exitMenuItem) {
			System.exit(0);
		} else if (e.getSource() == saveGameMenuItem) {
			saveGame();
		} else if (e.getSource() == InstructionsMenuItem) {
			JOptionPane.showMessageDialog(this, new HeritageBattleship_HowToPlayPanel(), "About",
					JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == restoreGameMenuItem) {
			try {
				restoreGame();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(this, "Couldn't read the current file " + e1.getMessage(),
						"Failed loading save game file", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == aboutMenuItem) {
			JOptionPane.showMessageDialog(this, new HeritageBattleship_AboutPanel(), "About",
					JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == btnUndo) {
			bGame.undoLastMove();
			boardPanel.removeAll();
			createBoard();
			analyzeSavedGame();
			undoLastShipSunk();
			JOptionPane.showMessageDialog(this, "Move undone", "Undo", JOptionPane.INFORMATION_MESSAGE);
		}
		if (board != null) {
			for (int row = 0; row < board.length; ++row) {
				for (int col = 0; col < board[row].length; ++col) {
					if (e.getSource() == board[row][col]) {
						btnUndo.setVisible(true);
						btnUndo.setEnabled(true);
						evaluateMove(row, col, bGame.returnCompleteRowCol(row, bGame.indexToLetter(col)));
					} // if (e.getSource() == button)
				} // for coloumns
			} // for rows
		}
	} // actionPerformed(e)
} // BattleshipFrame class
