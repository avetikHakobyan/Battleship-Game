package ahakobyan_G20_A02_PhaseII;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class HeritageBattleship_HowToPlayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HeritageBattleship_HowToPlayPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("How To Play");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		JTextPane txtpnHowToPlay = new JTextPane();
		txtpnHowToPlay.setEditable(false);
		txtpnHowToPlay.setText(
				"Battleship is a war-themed board game in which the player tries to guess the location of various ships.\r\n\r\nThe game's object is to guess the location of the ships hidden on a two-dimensional grid containing vertical and horizontal space coordinates. The player guesses row and column coordinates in an attempt to identify a square that contains a ship. The goal of the game is to sink all of the ships by correctly guessing their location on the grid.\r\n\r\nThere are five ships of various sizes that the player is attempting to locate:\r\n               Carrier, with five holes\r\n               Battleship, with four holes\r\n               Cruiser, with three holes\r\n               Submarine, with three holes\r\n               Destroyer, with two holes\r\n\r\nTo begin the game:\r\n\r\n-Choose to start a new game or continue a saved game in the \"File\" menu.\r\n\tNew game: Asks for your name and creates an empty board with hidden ships. The program will not begin the game displaying a message if the name field is missing or in case of a damaged game settings file.\r\n\tContinue game: Opens the previously saved game with hidden ships. The program will not begin the previously saved game in case of a damaged file. A message will appear letting you choose to start a new game or to leave.\r\n-Guess a coordinate by clicking on the square of your desired position\r\n-The color of the button will change:\r\n\tRed: There are no ships located in that position\r\n\tGreen: You have hit a position of a ship located in that position\r\n-A button \"Undo\" will appear letting you undo your last move\r\n*NOTE* You cannot undo when you just started a new game or loaded a saved game. You can only undo your last move\r\n-Continue to guess another position until you encounter a message\r\n-A message will appear when you have guessed all the positions of a boat that you have sunk an entire boat with its name.\r\nShips are updated live on the right-hand side of the screen letting you know which ships are sunk and which remain.\r\n-Continue to guess another position until you encounter a message.\r\n-A message will appear when all ships are sunk, congratulating the player and exiting after \"Ok\" is pressed.");
		GridBagConstraints gbc_txtpnHowToPlay = new GridBagConstraints();
		gbc_txtpnHowToPlay.fill = GridBagConstraints.BOTH;
		gbc_txtpnHowToPlay.gridx = 0;
		gbc_txtpnHowToPlay.gridy = 1;
		add(txtpnHowToPlay, gbc_txtpnHowToPlay);

	}
}
