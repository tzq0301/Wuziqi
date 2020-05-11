package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.Random;

/**
 * This class extends ChessBoard. It's created for game between player and machine.
 */
public class ChessBoardForHumanVersusMachine extends ChessBoard {

    private static ChessBoardForHumanVersusMachine instance;

    private ChessBoardForHumanVersusMachine() {
        super();
    }

    public static ChessBoardForHumanVersusMachine getInstance() {
        if (instance == null) {
            instance = new ChessBoardForHumanVersusMachine();
        }
        return instance;
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
}
