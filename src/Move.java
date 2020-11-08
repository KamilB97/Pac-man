import java.awt.event.KeyEvent;

public class Move {
	
	public static void movingControl(int e) {
		int code = e;
		if (code == KeyEvent.VK_W) {
			MoveCoordinates.up();
			 MoveCoordinates.changeCoordinate();
			PacModel.pacdir = PacModel.UP;
		}
		if (code == KeyEvent.VK_S) {
			MoveCoordinates.down();
			 MoveCoordinates.changeCoordinate();
			PacModel.pacdir = PacModel.DOWN;
		}
		if (code == KeyEvent.VK_D) {
			MoveCoordinates.right();
			 MoveCoordinates.changeCoordinate();
			PacModel.pacdir = PacModel.RIGHT;
		}
		if (code == KeyEvent.VK_A) {
			MoveCoordinates.left();
			MoveCoordinates.changeCoordinate();
			PacModel.pacdir = PacModel.LEFT;
		}
	}

}
