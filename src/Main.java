
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {

	private static JButton enterButton, exit;
	private static JTextField nameField;
	private static JLabel title;
	static String name = "";
	public static PacModel world = new PacModel();
	public static JFrame app = new JFrame("Pacman");

	public static FTPController kontroler = new FTPController(); // %%%%%%%%%%%%%%%

	public static ArrayList<String> allScores = new ArrayList<String>();

	public static int[] scoreWinersTab = new int[3];
	public static String[] namesWinersTab = new String[3];

	public static String[] resultsTable = kontroler.download3BestResults(1);
	public static String splited = resultsTable[0];
	public static String[] splitedArray = null;

	public static void main(String[] args) {
		splitResoults();
		PacView pacView = new PacView(world);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pacView.addKeyListener(new pacmanKeyListener(world));
		app.add(pacView);

		JPanel eastPanel = new JPanel(new GridBagLayout());
		app.add(eastPanel, BorderLayout.EAST);
		GridBagConstraints c = new GridBagConstraints();
		eastPanel.setBackground(Color.LIGHT_GRAY);
		eastPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		eastPanel.add(new JLabel("1 "), c);
		c.gridy++;
		eastPanel.add(new JLabel("2 "), c);
		c.gridy++;
		eastPanel.add(new JLabel("3 "), c);

		c.gridx = 1;
		c.gridy = 0;
		eastPanel.add(new JLabel("BEST SCORE"), c);
		c.gridy++;
		eastPanel.add(new JLabel(namesWinersTab[0] + ": "), c);
		c.gridy++;
		eastPanel.add(new JLabel(namesWinersTab[1] + ": "), c);
		c.gridy++;
		eastPanel.add(new JLabel(namesWinersTab[2] + ": "), c);

		 c.gridx = 2;
		 c.gridy = 1;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[0])), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[1])), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[2])), c);

		app.pack();
		app.requestFocus();
		app.setState(Frame.NORMAL);

		app.setVisible(true);
		while (!world.gameOver()) {
			world.tick();
			pacView.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (world.gameOver()) {

			createView();
			app.remove(eastPanel);
			app.remove(pacView);
			app.setSize(500, 170);
			// app.add(button);
			app.setBackground(Color.GREEN);
			app.setMinimumSize(new Dimension(500, 170));
			app.setLocationRelativeTo(null);
			app.setResizable(true);

		}
	}

	private static void createView() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setLayout(new BorderLayout());
		app.getContentPane().add(panel);

		// South
		JPanel southPanel = new JPanel(new BorderLayout());
		panel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(new JLabel("Set your name: "), BorderLayout.WEST);

		nameField = new JTextField("enter your name");
		nameField.setSelectedTextColor(Color.BLUE);
		nameField.setFocusable(true);

		southPanel.add(nameField, BorderLayout.CENTER);
		enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			// get name $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ and score

			@Override
			public void actionPerformed(ActionEvent arg0) {
				name = nameField.getText();
				// name=(toString());
				System.out.println("imie to: " + name);
				kontroler.saveGame(name, world.getScore());

				System.exit(0);
			}
		});

		southPanel.add(enterButton, BorderLayout.EAST);
		// East

		JPanel eastPanel = new JPanel(new GridBagLayout());
		panel.add(eastPanel, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		 c.anchor = GridBagConstraints.LINE_END;
		 eastPanel.add(new JLabel(namesWinersTab[0]), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(namesWinersTab[1]), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(namesWinersTab[2]), c);
		
		 c.gridx = 1;
		 c.gridy = 0;
		 eastPanel.add(new JLabel("BEST SCORE"), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[0])), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[1])), c);
		 c.gridy++;
		 eastPanel.add(new JLabel(Integer.toString(scoreWinersTab[2])), c);

		// Center
		JPanel centerPanel = new JPanel(new GridBagLayout());
		panel.add(centerPanel, BorderLayout.WEST);

		exit = new JButton("EXIT");
		exit.setPreferredSize(new Dimension(120, 35));
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("koniec");

				System.exit(0);

			}
		});
		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 1;
		b.gridy = 1;

		centerPanel.add(exit, b);
		// North
		JPanel northPanel = new JPanel(new BorderLayout());
		panel.add(northPanel, BorderLayout.NORTH);
		title = new JLabel("Your currently score: " + world.getScore());
		title.setFont(new Font("Caibri", Font.BOLD, 30));
		northPanel.add(title, BorderLayout.WEST);

	}

	public static void splitResoults() {

		String[] array = null;
		splited = splited.replace(";", ",");
		splited = splited.replace("\n", "");
		splited=splited.replace(" ", "");
		array = splited.split(",");
		System.out.println("size of table " + array.length);
		for (int i = 0; i < array.length - 1; i++) {
			System.out.println(array[i]);
			allScores.add(array[i]);
		}
		for (int i = 0; i < 3; i++) {
			namesWinersTab[i] = "-";
			scoreWinersTab[i] = 0;
		}
		if (array.length - 1 == 2) {
			namesWinersTab[0] = array[0];
			scoreWinersTab[0] = Integer.parseInt(array[1]);

		}
		if (array.length - 1 == 4) {
			int maximum = Integer.parseInt(array[1]);
			if (maximum < Integer.parseInt(array[3])) {
				namesWinersTab[0] = array[2];
				scoreWinersTab[0] = Integer.parseInt(array[3]);
				namesWinersTab[1] = array[0];
				scoreWinersTab[1] = Integer.parseInt(array[1]);

			} else {
				namesWinersTab[0] = array[0];
				scoreWinersTab[0] = Integer.parseInt(array[1]);
				namesWinersTab[1] = array[2];
				scoreWinersTab[1] = Integer.parseInt(array[3]);

			}
		}
		if (array.length - 1 > 4) {
			

			for (int j = 0; j < 3; j++) {
				int ktory = 0;

				int max = Integer.parseInt(array[1]);
				System.out.println(max+" max");
				for (int e = 1; e < (allScores.size() ); e += 2) {
					System.out.println("e wynosi "+ array[e]);

					if (max < Integer.parseInt(array[e])) {
						// zdarzenie score1 = 663 & score2 = 663 ? 
						ktory = e - 1;
						max = Integer.parseInt(array[e]);
						System.out.println("dokonalo sie: "+ max);
					}

				}
				// przypisz wartosc, usun 
				scoreWinersTab[j] = max;
				namesWinersTab[j] = array[ktory];
				allScores.remove(ktory);
				allScores.remove(ktory);
				for (int i = 0; i < array.length-1; i++) {
					System.out.println("j= "+j+" "+array[i]);
				}
			}
		} 
	}

	public void newMethod() {

	}

}
