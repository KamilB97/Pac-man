import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Rectangle;

public class PacModel {

	public final static int STILL = 0, UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
	public static int pacdir = RIGHT;
	public int cell = 0;
	public int bigCell = 0;
	private int score = 0;
	public Boolean ate = false;

	// ghosts coordinates
	public float ghost1X = 18, ghost1Y = 1; // fioletowy
	public float ghost2X = 1, ghost2Y = 14; // zielony
	public float ghost3X = 15, ghost3Y = 15; // czerwony
	public float ghost4X = 18, ghost4Y = 10; // zolty
	public double x1changer = -0.2, y1changer = 0;
	public double x2changer = 0, y2changer = -0.2;
	public double x3changer = 0.2, y3changer = 0;
	public double x4changer = 0, y4changer = -0.2;

	// muzyczka
	//Sound sound = new Sound();
	//Thread thread = new Thread(sound);

	ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();

	public int time = 0;

	public PacModel() {
		for (String row : Map.getMap().split("\n")) {
			ArrayList<Character> r = new ArrayList<Character>();
			for (int i = 0; i < row.length(); i++) {
				r.add(row.charAt(i));
			}
			board.add(r);
		}

	}

	public int getScore() {
		return score;
	}

	int getWidth() {
		return board.get(0).size();
	}

	private void setCell(int x, int y, char c) {
		board.get(y).set(x, c);
		cell++;
		score += 10;
	}

	private void setBigCell(int x, int y, char c) {
		board.get(y).set(x, c);
		int fluke = (int) (Math.random() * (100) + 100);
		bigCell++;
		score += fluke;
		System.out.println("los: " + fluke);
		System.out.println("wynik: " + score);

	}

	public int getHeight() {
		return board.size();
	}

	// do kropek, wez pozycje
	public char getCell(int x, int y) {

		return board.get(y).get(x);
	}

	public char getBigCell(int x, int y) {

		return board.get(y).get(x);
	}

	// sprawdza za kazdym repaintem
	public void tick() {
		time += 0.4;

		enemyCollision("Purply", Math.round(ghost1X), Math.round(ghost1Y), 30, 21);
		enemyCollision("Greeny", Math.round(ghost2X), Math.round(ghost2Y), 30, 30);
		enemyCollision("Reddy", Math.round(ghost3X), Math.round(ghost3Y), 30, 30);
		enemyCollision("Yellowy", Math.round(ghost4X), Math.round(ghost4Y), 30, 30);

		int x = Math.round(MoveCoordinates.pacmanX);
		int y = Math.round(MoveCoordinates.pacmanY);

		if ((pacdir == RIGHT && getCell(x + 1, y) == '|' && MoveCoordinates.pacmanX >= x)
				|| (pacdir == LEFT && getCell(x - 1, y) == '|' && MoveCoordinates.pacmanX <= x)
				|| (pacdir == UP && getCell(x, y - 1) == '|' && MoveCoordinates.pacmanY <= y)
				|| (pacdir == DOWN && getCell(x, y + 1) == '|' && MoveCoordinates.pacmanY >= y)) {
			pacdir = STILL;
		}
		//

		if (pacdir == RIGHT) {

			PacView.setPacRotate("pacekPrawo.png");
			MoveCoordinates.changeCoordinate();
		}
		if (pacdir == LEFT) {
			PacView.setPacRotate("pacekLewo.png");
			MoveCoordinates.changeCoordinate();
		}
		if (pacdir == UP) {
			PacView.setPacRotate("pacekUp.png");
			MoveCoordinates.changeCoordinate();
		}
		if (pacdir == DOWN) {
			PacView.setPacRotate("pacekDown.png");
			MoveCoordinates.changeCoordinate();
		}
		// duchy
		// %%%%
		int g1X = Math.round(ghost1X);
		int g1Y = Math.round(ghost1Y);

		if (getCell(g1X - 1, g1Y) == '|' && ghost1X <= g1X || getCell(g1X, g1Y) == '|' && ghost1X <= g1X) {
			x1changer = -x1changer;
		}

		int g2X = Math.round(ghost2X);
		int g2Y = Math.round(ghost2Y);

		if (getCell(g2X, g2Y - 1) == '|' && ghost2Y <= y || getCell(g2X, g2Y + 1) == '|' && ghost2Y >= y) {
			y2changer = -y2changer;
		}
		int g3X = Math.round(ghost3X);
		int g3Y = Math.round(ghost3Y);

		if (getCell(g3X - 1, g3Y) == '|' && ghost3X <= g3X || getCell(g3X, g3Y) == '|' && ghost3X <= g3X) {
			x3changer = -x3changer;
		}
		int g4X = Math.round(ghost4X);
		int g4Y = Math.round(ghost4Y);

		if (getCell(g4X, g4Y - 1) == '|' && ghost4Y <= y || getCell(g4X, g4Y + 1) == '|' && ghost4Y >= y) {
			y4changer = -y4changer;
		}
		// %%%%

		changer();

		if (getCell(x, y) == '.') {
			setCell(x, y, ' ');

			Sound s = new Sound();
			s.setMusic("getCell.wav");
			s.setSleep(1);
			Thread thread = new Thread(s);
			thread.start();
		}
		if (getCell(x, y) == 'o') {
			setBigCell(x, y, ' ');

			Sound s = new Sound();
			s.setMusic("bigCoin.wav");
			s.setSleep(1);
			Thread thread = new Thread(s);
			thread.start();

		}
		if (cell >= 169 && bigCell >= 6) { // 169, 6
			sound.setMusic("boo.mp3");
			sound.setSleep(3);
			thread.start();
			JOptionPane.showMessageDialog(null, "CONGRATULATIONS! \nYour score is: " + getScore());
		}
	}

	public boolean gameOver() {
		if (cell >= 169 && bigCell >= 6)
			return true;
		else if (ate == true)
			return true;
		else
			return false;
	}

	public void changer() {
		ghost1X += x1changer;
		ghost1Y += y1changer;

		ghost2X += x2changer;
		ghost2Y += y2changer;

		ghost3X += x3changer;
		ghost3Y += y3changer;

		ghost4X += x4changer;
		ghost4Y += y4changer;

	}

	public void enemyCollision(String name, int x, int y, int height, int width) {

		Rectangle ghost = new Rectangle(x * 40, y * 40, height, width);
		Rectangle pac = new Rectangle(Math.round(MoveCoordinates.pacmanX * 40),
				Math.round(MoveCoordinates.pacmanY * 40), 20, 30);
		if (ghost.intersects(pac)) {
			ate = true;
			System.out.println("Collision " + name);
			Sound eating = new Sound();
			eating.setMusic("smackLips.mp3");
			eating.setSleep(3);
			Thread thread = new Thread(eating);
			thread.start();
			JOptionPane.showMessageDialog(null, "CONGRATULATIONS!\nYou've fed one of ghosts. Now '" + name
					+ "' being very happy <3\n" + "Next time remember about other ghosts, they are hungry too");
		}

	}

}
