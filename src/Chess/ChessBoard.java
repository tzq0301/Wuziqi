package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class makes a chess broad.
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

public class ChessBoard extends JPanel implements MouseListener {
    /**
     * row 是棋盘的行数，默认值为 15
     * column 是棋盘的列数，默认值为 15
     * MARGIN 是棋盘的边距
     * GRIN_SPAN 是棋盘的网格间距
     * chessNumber 是设置的获胜所需的棋子数量，默认值为 5
     */
    public static int row = 15;
    public static int column = 15;
    public static final int MARGIN = 30;
    public static final int GRID_SPAN = 35;
    public static int chessNumberRequiredToWin = 5;


    //初始每个数组元素为null
    public ChessPiece[] chessList = new ChessPiece[(row + 1) * (column + 1)];
    //默认开始是黑棋先
    public boolean isBlack = true;
    //游戏是否结束
    public boolean isGameOver = false;
    //当前棋盘棋子的个数
    public int chessCount;
    //当前刚下棋子的索引
    public int xIndex, yIndex;

    Color colorTemp;

    // 使用“sdf”作为时间显示的格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    // 加入图片作为背景
    Image backgroundImage = Background.getInstance().getBackgroundImage();

    public ChessBoard() {
        addMouseListener(this);
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            /**
             * Does something if the mouse moves
             *
             * <p>
             *     Change the coordinate of the point where the mouse clicks to the index of the grid
             * </p>
             * <p>
             *     The chess piece can not be placed here if:
             * </p>
             * <ol>
             *     <li>The game is over.</li>
             *     <li>The point is out of the chess broad.</li>
             *     <li>A chess piece has already been placed here.</li>
             * </ol>
             *
             * @param e
             */
            public void mouseMoved(MouseEvent e) {
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                //将鼠标点击的坐标位置转成网格索引
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                //游戏已经结束不能下
                //落在棋盘外不能下
                //x，y位置已经有棋子存在，不能下
                if (x1 < 0 || x1 > row || y1 < 0 || y1 > column || isGameOver || isHasChess(x1, y1))
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //设置成默认状态
                else
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

    }

    /**
     * Checks if this point already has a chess
     *
     * @param xIndex the x-coordinate of the chess broad
     * @param yIndex the y-coordinate of the chess broad
     * @return <code> true </code> if this point has a chess piece
     */
    public boolean isHasChess(int xIndex, int yIndex) {
        for (ChessPiece c : chessList) {
            if (c != null && c.getX() == xIndex && c.getY() == yIndex)
                return true;
        }
        return false;
    }

    /**
     * Returns the chess piece at the point of (xIndex, yIndex)
     *
     * @param xIndex the x-coordinate of the chess piece
     * @param yIndex yIndex the y-coordinate of the chess piece
     * @param color  color of the chess piece
     * @return the chess piece of the color of <code>color</code> at the point of (<code>xIndex</code>, <code>yIndex</code>)
     */
    public ChessPiece getChessPiece(int xIndex, int yIndex, Color color) {
        for (ChessPiece p : chessList) {
            if (p != null && p.getX() == xIndex && p.getY() == yIndex && p.getColor() == color)
                return p;
        }

        return null;
    }

    /**
     * Returns whether the player has won
     *
     * @return <code>true</code> if win
     */
    public boolean isWin() {
        //连续棋子的个数
        int continueCount = 1;

        //横向向西寻找
        for (int x = xIndex - 1; x >= 0; x--) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, yIndex, c) != null)
                continueCount++;
            else
                break;
        }
        //横向向东寻找
        for (int x = xIndex + 1; x <= column; x++) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, yIndex, c) != null)
                continueCount++;
            else
                break;
        }
        // 判断是否满足获胜条件
        if (continueCount >= chessNumberRequiredToWin)
            return true;
        else
            continueCount = 1;

        //继续另一种搜索纵向
        //向上搜索
        for (int y = yIndex - 1; y >= 0; y--) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(xIndex, y, c) != null)
                continueCount++;
            else
                break;
        }
        //纵向向下寻找
        for (int y = yIndex + 1; y <= row; y++) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(xIndex, y, c) != null)
                continueCount++;
            else
                break;
        }
        if (continueCount >= chessNumberRequiredToWin)
            return true;
        else
            continueCount = 1;


        //继续另一种情况的搜索：斜向
        //东北寻找
        for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= column; x++, y--) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, y, c) != null)
                continueCount++;
            else
                break;
        }
        //西南寻找
        for (int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= row; x--, y++) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, y, c) != null)
                continueCount++;
            else
                break;
        }
        if (continueCount >= chessNumberRequiredToWin)
            return true;
        else
            continueCount = 1;


        //继续另一种情况的搜索：斜向
        //西北寻找
        for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, y, c) != null)
                continueCount++;
            else
                break;
        }
        //东南寻找
        for (int x = xIndex + 1, y = yIndex + 1; x <= column && y <= row; x++, y++) {
            Color c = isBlack ? Color.black : Color.white;

            if (getChessPiece(x, y, c) != null)
                continueCount++;
            else
                break;
        }
        if (continueCount >= chessNumberRequiredToWin)
            return true;
//        else continueCount=1;

        return false;
    }



    /**
     * Restarts the game
     */
    public void restart() {
        for (int i = 0; i < chessList.length; i++)
            chessList[i] = null;

        //恢复游戏相关的变量值
        isBlack = true;
        isGameOver = false; //游戏是否结束
        chessCount = 0; //当前棋盘棋子个数
        this.repaint();

    }

    /**
     * Retract the action.
     */
    public void retract() {
        //已经有棋子在棋盘
        if (chessCount > 0) {
            chessList[chessCount - 1] = null;
            chessCount--;
        }
        //如果悔棋后还有棋子存在棋盘
        if (chessCount > 0) {
            xIndex = chessList[chessCount - 1].getX();
            yIndex = chessList[chessCount - 1].getY();
        }
        isBlack = !isBlack;
        this.repaint();

        System.out.println("悔棋于 第 " + (yIndex + 1) + " 行 第 " + (xIndex + 1) + " 列\t" + sdf.format(new Date()));

    }

    public boolean canPlaceHere(int xIndex, int yIndex) {
        return !(xIndex < 0 || xIndex > row || yIndex < 0 || yIndex > column) && !(isHasChess(xIndex, yIndex));
    }


    /**
     * Paints the components
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Sets a proper size for the chess broad
     *
     * @return a dimension of preferred size
     */
    public Dimension getPreferredSize() {
        return new Dimension(MARGIN * 2 + GRID_SPAN * row, MARGIN * 2 + GRID_SPAN * column);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Modifies the row of the chess broad.
     *
     * @param row
     */
    public static void setRow(int row) {
        ChessBoard.row = row;
    }

    /**
     * Modifies the column of the chess broad.
     *
     * @param column
     */
    public static void setColumn(int column) {
        ChessBoard.column = column;
    }

    /**
     * Modifies winning condition of the game
     *
     * @param chessNumberRequiredToWin
     */
    public static void setChessNumberRequiredToWin(int chessNumberRequiredToWin) {
        ChessBoard.chessNumberRequiredToWin = chessNumberRequiredToWin;
    }

}




