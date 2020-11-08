import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class pacmanKeyListener implements KeyListener {
	PacModel world;

	public pacmanKeyListener(PacModel world) {
		this.world = world;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Move.movingControl(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
