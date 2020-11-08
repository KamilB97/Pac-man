import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PacView extends Canvas {
	// do duszka
	BufferedImage ghost1, ghost2, diabol, yellowy, pacman;
	BufferedImage bigCell, smallCell, block;
	private static String pacRotate = "pacekPrawo.png";
	BufferedImage bi = new BufferedImage(200, 180, BufferedImage.TYPE_INT_ARGB);

	private static final long serialVersionUID = 1L;
	private PacModel world;
	public static final int PIXELS = 40;

	public static void setPacRotate(String pac) {
		pacRotate = pac;
	}

	public PacView(PacModel world) {
		this.world = world;
		setSize(world.getWidth() * PIXELS, world.getHeight() * PIXELS);
		// duszki
		try {
			ghost1 = ImageIO.read(new File("ghost.png"));
			ghost2 = ImageIO.read(new File("ghost2.png"));
			diabol = ImageIO.read(new File("diabol.png"));
			yellowy = ImageIO.read(new File("yellowGhost.png"));
			// pacman = ImageIO.read(new File(pacRotate));
			bigCell = ImageIO.read(new File("bigCell.png"));
			smallCell = ImageIO.read(new File("smallCoin.png"));
			block = ImageIO.read(new File("bloczek.png"));

		} catch (IOException e) {
		}
	}

	///// @@@@@@@@
	public void update(Graphics g) {
		Image im = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		paint(im.getGraphics());
		g.drawImage(im, 0, 0, null);

	}

	public void paint(Graphics g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
// ******************************obsluga mapy*****************************
		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				char c = world.getCell(x, y);
				switch (c) {
				case '.':
					g.setColor(Color.GREEN);
					if ((world.time / 8) % 2 == 0) {

						g.drawImage(smallCell, x * PIXELS + PIXELS / 3, y * PIXELS + PIXELS / 3, null);
					}

					break;
				case '|':
					g.drawImage(block, x * PIXELS, y * PIXELS, null);
					break;
				case 'o':
					g.drawImage(bigCell, x * PIXELS + 8, y * PIXELS + 8, null);
					break;
				case '<':
					g.setColor(Color.RED);
					g.drawRect(x * PIXELS, y * PIXELS, PIXELS, PIXELS / 2);
					break;
				case ' ':
					break;
				case '>':
					g.setColor(Color.RED);
					g.drawRect(x * PIXELS, y * PIXELS + 19, PIXELS, PIXELS / 2);
					break;
				default:
					break;
				}

			}
		}

		try {
			pacman = ImageIO.read(new File(pacRotate));

		} catch (IOException e) {
		}
		// pacek;
		g.drawImage(pacman, Math.round(MoveCoordinates.pacmanX * PIXELS), Math.round(MoveCoordinates.pacmanY * PIXELS), null);
		// do duszka
		g.drawImage(ghost1, Math.round(world.ghost1X * PIXELS), Math.round(world.ghost1Y * PIXELS), null);
		g.drawImage(ghost2, Math.round (world.ghost2X * PIXELS), Math.round (world.ghost2Y * PIXELS), null);
		g.drawImage(diabol, Math.round(world.ghost3X * PIXELS), Math.round(world.ghost3Y * PIXELS), null);
		g.drawImage(yellowy, Math.round (world.ghost4X * PIXELS), Math.round (world.ghost4Y * PIXELS), null);
		g.setColor(Color.RED);

		g.setFont(new Font("Caibri", Font.BOLD, 26));
		g.drawString("Score: " + world.getScore(), 8 * 41, 8 * 39);
	}
}
