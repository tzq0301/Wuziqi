package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * This class makes a chess broad for human versus machine.
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
public class ChessBoardForHumanVersusMachine extends JPanel implements ChessBoard {

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

    private static ChessBoardForHumanVersusMachine instance;

    private ChessBoardForHumanVersusMachine() {
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

    public static ChessBoardForHumanVersusMachine getInstance() {
        if (instance == null) {
            instance = new ChessBoardForHumanVersusMachine();
        }
        return instance;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isGameOver) {
            return;
        }

        // 以下进行用户落子
        String colorName = isBlack ? "黑棋" : "白棋";

        Player p1 = new Player(e);
        p1.moveChess(e);
        xIndex = p1.getxIndex();
        yIndex = p1.getyIndex();

        // 不能落子
        if (canPlaceHere(xIndex, yIndex) == false) {
            return;
        }

        //可以进行时的处理
        ChessPiece ch = new ChessPiece(xIndex, yIndex, isBlack ? Color.black : Color.white);
        chessList[chessCount++] = ch;
        repaint();//通知系统重新绘制

        //如果胜出则给出提示信息，不能继续下棋
        if (isWin()) {
            String msg = String.format("恭喜，%s赢了！", colorName);
            JOptionPane.showMessageDialog(this, msg);

            isGameOver = true;
            return;
        }
        isBlack = !isBlack;

        System.out.println("黑棋落子于 第 " + (yIndex + 1) + " 行 第 " + (xIndex + 1) + " 列\t" + sdf.format(new Date()));


        //随后进行简单的电脑落子
        colorName = isBlack ? "黑棋" : "白棋"; //电脑为白棋
        do {
            do {
                if (xIndex >= row || yIndex >= column) {
                    xIndex = xIndex - (int) (Math.random() * 10 % 3);
                    yIndex = yIndex - (int) (Math.random() * 10 % 3);
                } else {
                    int q1 = 0, q2 = 0;
                    while (q1 == 0 && q2 == 0) {
                        q1 = (int)Math.floor(Math.random() * 3 - 2);
                        q2 = (int)Math.floor(Math.random() * 3 - 1);
                    }
                    xIndex = xIndex + (int) (Math.random() * 10 % 2) * q1;
                    yIndex = yIndex + (int) (Math.random() * 10 % 2) * q2;
                }
            } while (xIndex < 0 || xIndex > row || yIndex < 0 || yIndex > column);
        } while (isHasChess(xIndex, yIndex));

        //绘制电脑棋子
        ChessPiece ch2 = new ChessPiece(xIndex, yIndex, isBlack ? Color.black : Color.white);
        chessList[chessCount++] = ch2;
        repaint();
        if (isWin()) {
            String msg = String.format("恭喜，%s赢了！", colorName);
            JOptionPane.showMessageDialog(this, msg);

            isGameOver = true;
        }
        isBlack = !isBlack;

        System.out.println("白棋落子于 第 " + (yIndex + 1) + " 行 第 " + (xIndex + 1) + " 列\t" + sdf.format(new Date()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 设置背景图片
        g.drawImage(backgroundImage, -50, -50, null);

        Graphics2D g2d = (Graphics2D) g;
        //fill white back ground
        g2d.setColor(Color.white);
        //标记Computer为白色棋子
        g2d.drawString("Computer:", MARGIN, MARGIN * 2 / 3);
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds("Computer", g2d);
        int r = MARGIN - 2;
        g2d.drawOval(MARGIN + (int) rect.getWidth() + r, 0, r, r);
        g2d.fillOval(MARGIN + (int) rect.getWidth() + r, 0, r, r);
        //标记User为黑色棋子
        g2d.setColor(Color.black);
        g2d.drawString("Player:", MARGIN * 10, MARGIN * 2 / 3);
        rect = fm.getStringBounds("Player", g2d);
        g2d.drawOval(MARGIN * 10 + (int) rect.getWidth() + r, 0, r, r);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(MARGIN * 10 + (int) rect.getWidth() + r, 0, r, r);

        g2d.setColor(Color.black);//设置画线颜色为黑

        for (int i = 0; i <= row; i++) {
            //画横线
            g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + column * GRID_SPAN, MARGIN + i * GRID_SPAN);
        }
        for (int i = 0; i <= column; i++) {
            //画竖线
            g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + row * GRID_SPAN);
        }
        for (int i = 0; i < chessCount; i++) {
            //网格交叉点x，y坐标
            int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
            int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
            g.setColor(chessList[i].getColor()); //设置颜色
            colorTemp = chessList[i].getColor();
            if (colorTemp == Color.black) {
                RadialGradientPaint paint = new RadialGradientPaint(xPos - ChessPiece.DIAMETER / 2,
                        yPos - ChessPiece.DIAMETER / 2, 20, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                        RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
            } else if (colorTemp == Color.white) {
                RadialGradientPaint paint = new RadialGradientPaint(xPos - ChessPiece.DIAMETER / 2,
                        yPos - ChessPiece.DIAMETER / 2, 70, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                        RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
            }

            Ellipse2D e = new Ellipse2D.Float(xPos - ChessPiece.DIAMETER / 2, yPos - ChessPiece.DIAMETER / 2, 34, 35);
            ((Graphics2D) g).fill(e);
            //标记最后一个棋子的红矩形框

            if (i == chessCount - 1) {//如果是最后一个棋子
                g.setColor(Color.red);
                g.drawRect(xPos - ChessPiece.DIAMETER / 2, yPos - ChessPiece.DIAMETER / 2, 34, 35);
            }

        }
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
    public void setRow(int row) {
        ChessBoardForHumanVersusMachine.row = row;
    }

    /**
     * Modifies the column of the chess broad.
     *
     * @param column
     */
    public void setColumn(int column) {
        ChessBoardForHumanVersusMachine.column = column;
    }

    /**
     * Modifies winning condition of the game
     *
     * @param chessNumberRequiredToWin
     */
    public void setChessNumberRequiredToWin(int chessNumberRequiredToWin) {
        ChessBoardForHumanVersusMachine.chessNumberRequiredToWin = chessNumberRequiredToWin;
    }
}
