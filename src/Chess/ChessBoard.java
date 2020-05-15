package Chess;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This interface will make a class which makes a chess broad.
 *
 * <p>
 * The following information is maintained:
 * </p>
 * <ol>
 *     <li>rows of the chess broad, an <code>int</code></li>
 *     <li>columns of the chess broad, an <code>int</code></li>
 *     <li>margin of the chess broad, an <code>int</code></li>
 *     <li>the grin span of the chess broad, an <code>int</code></li>
 *     <li>the default chess number of winning condition, an <code>int</code></li>
 * </ol>
 *
 * @Version 1.0
 * @see Chess.ChessPiece
 */

public interface ChessBoard extends MouseListener {

    /**
     * row 是棋盘的行数，默认值为 15
     * column 是棋盘的列数，默认值为 15
     * MARGIN 是棋盘的边距
     * GRIN_SPAN 是棋盘的网格间距
     * chessNumber 是设置的获胜所需的棋子数量，默认值为 5
     */
    int MARGIN = 30;
    int GRID_SPAN = 35;

    /**
     * Checks if this point already has a chess
     *
     * @param xIndex the x-coordinate of the chess broad
     * @param yIndex the y-coordinate of the chess broad
     * @return <code> true </code> if this point has a chess piece
     */
    boolean isHasChess(int xIndex, int yIndex);

    /**
     * Returns the chess piece at the point of (xIndex, yIndex)
     *
     * @param xIndex the x-coordinate of the chess piece
     * @param yIndex yIndex the y-coordinate of the chess piece
     * @param color  color of the chess piece
     * @return the chess piece of the color of <code>color</code> at the point of (<code>xIndex</code>, <code>yIndex</code>)
     */
    ChessPiece getChessPiece(int xIndex, int yIndex, Color color);

    /**
     * Returns whether the player has won
     *
     * @return <code>true</code> if win
     */
    boolean isWin();

    /**
     * Restarts the game
     */
    void restart();

    /**
     * Retract the action.
     */
    void retract();

    boolean canPlaceHere(int xIndex, int yIndex);

    @Override
    void mouseClicked(MouseEvent e);

    @Override
    void mousePressed(MouseEvent e);

    @Override
    void mouseReleased(MouseEvent e);

    @Override
    void mouseEntered(MouseEvent e);

    @Override
    void mouseExited(MouseEvent e);

    /**
     * Modifies the row of the chess broad.
     *
     * @param row
     */
    void setRow(int row);

    /**
     * Modifies the column of the chess broad.
     *
     * @param column
     */
    void setColumn(int column);

    /**
     * Modifies winning condition of the game
     *
     * @param chessNumberRequiredToWin
     */
    void setChessNumberRequiredToWin(int chessNumberRequiredToWin);

}




