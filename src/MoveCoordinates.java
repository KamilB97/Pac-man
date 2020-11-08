
public class MoveCoordinates {
	static double xChanger = 0, yChanger = 0;
	public static float pacmanX = 10;
	public static float pacmanY = 14;

	public static void changeCoordinate() {
		pacmanX += xChanger;
		pacmanY += yChanger;
	}

	public static void up() {
		xChanger = 0;
		yChanger = -0.125;

	}

	public static void down() {
		xChanger = 0;
		yChanger = 0.125;

	}

	public static void right() {

		xChanger = 0.125;
		yChanger = 0;
	}

	public static void left() {
		xChanger = -0.125;
		yChanger = 0;

	}
}
