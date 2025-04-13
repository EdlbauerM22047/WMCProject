package htl.grieskirchen.schachprojekt.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import htl.grieskirchen.schachprojekt.R;
import htl.grieskirchen.schachprojekt.callback.TurnManager;
import htl.grieskirchen.schachprojekt.databinding.FragmentChessBoardBinding;
import htl.grieskirchen.schachprojekt.listener.PieceTouchListener;
import htl.grieskirchen.schachprojekt.listener.SquareDragListener;
import htl.grieskirchen.schachprojekt.model.Piece;
import htl.grieskirchen.schachprojekt.model.PieceColor;
import htl.grieskirchen.schachprojekt.model.Position;
import htl.grieskirchen.schachprojekt.model.Type;
import htl.grieskirchen.schachprojekt.viewmodel.MainViewModel;


public class ChessBoard extends Fragment implements TurnManager {
    PieceColor color= PieceColor.BLACK;

    private FragmentChessBoardBinding binding;
    private final int BOARD_SIZE = 8;
    Piece[][] board ;
    MainViewModel viewModel;
    private boolean isWhiteTurn=true;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChessBoardBinding.inflate(inflater, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        createChessBoard();
        updateTurnDisplay();
       // dein Spielbrett
        board = new Piece[8][8];
        //initializeBoard(board); // deine Logik, um Figuren zu setzen

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createChessBoard() {
        GridLayout chessGrid = binding.chessBoard;
        chessGrid.removeAllViews();
        chessGrid.setRowCount(BOARD_SIZE);
        chessGrid.setColumnCount(BOARD_SIZE);

        board = new Piece[8][8]; // direkt hier initialisieren

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ImageView square = new ImageView(requireContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(col, 1f);
                params.rowSpec = GridLayout.spec(row, 1f);
                params.setMargins(1, 1, 1, 1);
                square.setLayoutParams(params);

                // Farbgebung
                boolean isWhite = (row + col) % 2 == 0;
                square.setBackgroundColor(Color.parseColor(isWhite ? "#EEEED2" : "#769656"));

                // Setze Position als Tag (sehr wichtig!)
                Position pos = new Position(row, col);
                square.setTag(pos);

                // Figur setzen und im board[][] eintragen
                if (row == 1) {
                    square.setImageResource(R.drawable.blackpawn);
                    board[row][col] = new Piece(Type.PAWN, PieceColor.BLACK);
                } else if (row == 6) {
                    square.setImageResource(R.drawable.whitepawn);
                    board[row][col] = new Piece(Type.PAWN, PieceColor.WHITE);
                } else if (row == 0 || row == 7) {
                    boolean isBlack = row == 0;
                    PieceColor color = isBlack ? PieceColor.BLACK : PieceColor.WHITE;

                    int drawable = 0;
                    Type type = null;

                    switch (col) {
                        case 0: case 7:
                            drawable = isBlack ? R.drawable.blackrook : R.drawable.whiterook;
                            type = Type.ROOK;
                            break;
                        case 1: case 6:
                            drawable = isBlack ? R.drawable.blackknight : R.drawable.whiteknight;
                            type = Type.KNIGHT;
                            break;
                        case 2: case 5:
                            drawable = isBlack ? R.drawable.blackbishop : R.drawable.whitebishop;
                            type = Type.BISHOP;
                            break;
                        case 3:
                            drawable = isBlack ? R.drawable.blackqueen : R.drawable.whitequeen;
                            type = Type.QUEEN;
                            break;
                        case 4:
                            drawable = isBlack ? R.drawable.blackking : R.drawable.whiteking;
                            type = Type.KING;
                            break;
                    }

                    square.setImageResource(drawable);
                    board[row][col] = new Piece(type, color);
                }

                // Touch & Drag Listener setzen
                square.setOnTouchListener(new PieceTouchListener());
                square.setOnDragListener(new SquareDragListener(this, board));

                square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                chessGrid.addView(square);
            }
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    @Override
    public void onTurnFinished() {
        isWhiteTurn = !isWhiteTurn;
        updateTurnDisplay();
    }
    private void updateTurnDisplay() {
        if (binding != null) {
            binding.tvWhosMove.setText(isWhiteTurn ? viewModel.getWhiteName()+"(Weiß) ist am Zug" :viewModel.getBlackName()+ "(Schwarz) ist am Zug");
        }
    }
    private void initializeBoard(Piece[][] board) {
        // Schwarze Figuren
        board[0][0] = new Piece(Type.ROOK, PieceColor.BLACK);
        board[0][1] = new Piece(Type.KNIGHT, PieceColor.BLACK);
        board[0][2] = new Piece(Type.BISHOP, PieceColor.BLACK);
        board[0][3] = new Piece(Type.QUEEN, PieceColor.BLACK);
        board[0][4] = new Piece(Type.KING, PieceColor.BLACK);
        board[0][5] = new Piece(Type.BISHOP, PieceColor.BLACK);
        board[0][6] = new Piece(Type.KNIGHT, PieceColor.BLACK);
        board[0][7] = new Piece(Type.ROOK, PieceColor.BLACK);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(Type.PAWN, PieceColor.BLACK);
        }



        // Weiße Figuren
        board[7][0] = new Piece(Type.ROOK, PieceColor.WHITE);
        board[7][1] = new Piece(Type.KNIGHT, PieceColor.WHITE);
        board[7][2] = new Piece(Type.BISHOP, PieceColor.WHITE);
        board[7][3] = new Piece(Type.QUEEN, PieceColor.WHITE);
        board[7][4] = new Piece(Type.KING, PieceColor.WHITE);
        board[7][5] = new Piece(Type.BISHOP, PieceColor.WHITE);
        board[7][6] = new Piece(Type.KNIGHT, PieceColor.WHITE);
        board[7][7] = new Piece(Type.ROOK, PieceColor.WHITE);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Piece(Type.PAWN, PieceColor.WHITE);
        }

        // Rest mit null (leere Felder)
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }

    }

}
