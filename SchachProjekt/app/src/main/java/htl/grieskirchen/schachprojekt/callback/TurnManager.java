package htl.grieskirchen.schachprojekt.callback;

public interface TurnManager {
    boolean isWhiteTurn();
    void onTurnFinished();
}
