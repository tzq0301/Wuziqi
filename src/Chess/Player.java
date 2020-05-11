package Chess;

import java.awt.event.MouseEvent;

import static Chess.ChessBoard.GRID_SPAN;
import static Chess.ChessBoard.MARGIN;

/**
 * This class models a player.
 */
public class Player {

    int xIndex, yIndex;

    private MouseEvent e;

    public Player(MouseEvent e) {
        this.e = e;
    }

    public void moveChess(MouseEvent e) {
        //将鼠标点击的坐标位置转换成网格索引
        if ((e.getX() - MARGIN) % GRID_SPAN < GRID_SPAN / 2) {
            xIndex = (e.getX() - MARGIN) / GRID_SPAN;
        } else {
            xIndex = (e.getX() - MARGIN) / GRID_SPAN + 1;
        }
        if ((e.getY() - MARGIN) % GRID_SPAN < GRID_SPAN / 2) {
            yIndex = (e.getY() - MARGIN) / GRID_SPAN;
        } else {
            yIndex = (e.getY() - MARGIN) / GRID_SPAN + 1;
        }

    }

    public int getxIndex() {
        return xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }
}
