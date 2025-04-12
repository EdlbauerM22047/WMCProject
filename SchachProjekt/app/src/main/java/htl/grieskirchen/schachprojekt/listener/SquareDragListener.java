package htl.grieskirchen.schachprojekt.listener;

import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import htl.grieskirchen.schachprojekt.callback.TurnManager;
import htl.grieskirchen.schachprojekt.logic.ChessEngine;
import htl.grieskirchen.schachprojekt.model.*;

public class SquareDragListener implements View.OnDragListener {

    private final TurnManager turnManager;
    private final Piece[][] board;

    public SquareDragListener(TurnManager turnManager, Piece[][] board) {
        this.turnManager = turnManager;
        this.board = board;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                try {
                    if (!(event.getLocalState() instanceof ImageView) || !(v instanceof ImageView)) {
                        return false;
                    }

                    ImageView dragged = (ImageView) event.getLocalState();
                    ImageView target = (ImageView) v;

                    Object fromTag = dragged.getTag();
                    Object toTag = target.getTag();

                    if (!(fromTag instanceof Position) || !(toTag instanceof Position)) {
                        return false;
                    }

                    Position from = (Position) fromTag;
                    Position to = (Position) toTag;

                    Piece movingPiece = board[from.row][from.col];
                    if (movingPiece == null) return false;

                    // Spieler darf nur seine eigenen Figuren bewegen
                    boolean isWhite = turnManager.isWhiteTurn();
                    if ((isWhite && movingPiece.color != PieceColor.WHITE) ||
                            (!isWhite && movingPiece.color != PieceColor.BLACK)) {
                        return false;
                    }

                    if (ChessEngine.isValidMove(board, from, to)) {
                        // Ziehen erlaubt
                        board[to.row][to.col] = movingPiece;
                        board[from.row][from.col] = null;

                        target.setImageDrawable(dragged.getDrawable());
                        dragged.setImageDrawable(null);

                        turnManager.onTurnFinished();
                        return true;
                    } else {
                        // Zug ungültig → ignorieren
                        return false;
                    }

                } catch (Exception e) {
                    // Fehler vermeiden → nur loggen
                    e.printStackTrace(); // für Debugging in Logcat
                    return false;
                }

            default:
                return true;
        }
    }

}
