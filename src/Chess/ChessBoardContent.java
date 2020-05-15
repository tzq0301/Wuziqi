package Chess;

/**
 * @Description 使用了策略模式
 * @Author TZQ
 * @Date 2020/5/15 17:32
 */
public class ChessBoardContent {
    private ChessBoard chessBoard;

    public ChessBoardContent(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}
