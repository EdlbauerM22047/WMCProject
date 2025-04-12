package htl.grieskirchen.schachprojekt.logic;

import htl.grieskirchen.schachprojekt.model.*;

public class ChessEngine {

    public static boolean isValidMove(Piece[][] board, Position from, Position to) {
        Piece piece = board[from.row][from.col];
        if (piece == null) return false;

        switch (piece.type) {
            case PAWN:
                return isValidPawnMove(board, from, to, piece.color);
            case ROOK:
                return isStraightMove(from, to) && isPathClear(board, from, to);
            case BISHOP:
                return isDiagonalMove(from, to) && isPathClear(board, from, to);
            case QUEEN:
                return (isStraightMove(from, to) || isDiagonalMove(from, to)) && isPathClear(board, from, to);
            case KNIGHT:
                return isKnightMove(from, to);
            case KING:
                return isKingMove(from, to);
        }

        return false;
    }

    private static boolean isStraightMove(Position from, Position to) {
        return from.row == to.row || from.col == to.col;
    }

    private static boolean isDiagonalMove(Position from, Position to) {
        return Math.abs(from.row - to.row) == Math.abs(from.col - to.col);
    }

    private static boolean isKnightMove(Position from, Position to) {
        int dx = Math.abs(from.row - to.row);
        int dy = Math.abs(from.col - to.col);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    private static boolean isKingMove(Position from, Position to) {
        int dx = Math.abs(from.row - to.row);
        int dy = Math.abs(from.col - to.col);
        return dx <= 1 && dy <= 1;
    }

    private static boolean isValidPawnMove(Piece[][] board, Position from, Position to, PieceColor color) {
        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        int startRow = (color == PieceColor.WHITE) ? 6 : 1;

        // Einfacher Vorwärtszug
        if (from.col == to.col && board[to.row][to.col] == null) {
            if (to.row - from.row == direction) return true;
            if (from.row == startRow && to.row - from.row == 2 * direction && board[from.row + direction][from.col] == null) return true;
        }

        // Schlagen
        if (Math.abs(from.col - to.col) == 1 && to.row - from.row == direction) {
            return board[to.row][to.col] != null && board[to.row][to.col].color != color;
        }

        return false;
    }

    private static boolean isPathClear(Piece[][] board, Position from, Position to) {
        int dx = Integer.compare(to.row, from.row);
        int dy = Integer.compare(to.col, from.col);

        int x = from.row + dx;
        int y = from.col + dy;

        while (x != to.row || y != to.col) {
            if (board[x][y] != null) return false;
            x += dx;
            y += dy;
        }

        return true;
    }
}
