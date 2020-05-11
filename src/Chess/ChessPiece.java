package Chess;

import java.awt.*;

/**
 * This class models a chess piece. The following information is maintained:
 *
 * <ol>
 *     <li>the x-coordinate of the chess piece, an <code>int</code></li>
 *     <li>the y-coordinate of the chess piece, an <code>int</code></li>
 *     <li>the color of the chess piece, an <code>Color</code></li>
 * </ol>
 */
public class ChessPiece {

	public int x;
	public int y;
	public static final int DIAMETER = 30;
	public Color color;

	/**
	 * Creates a <code>ChessPiece</code> object
	 *
	 * @param x the x-coordinate of the chess piece
	 * @param y the y-coordinate of the chess piece
	 * @param color the color of the chess piece
	 */
	public ChessPiece(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Returns the x-coordinate of the chess piece
	 *
	 * @return the x-coordinate of the chess piece
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate of the chess piece
	 *
	 * @return the y-coordinate of the chess piece
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the color of the chess piece
	 *
	 * @return the color of the chess piece
	 */
	public Color getColor() {
		return color;
	}

}
