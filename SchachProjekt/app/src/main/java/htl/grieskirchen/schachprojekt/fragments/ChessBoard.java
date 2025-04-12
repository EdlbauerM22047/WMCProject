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
        initializeBoard(board); // deine Logik, um Figuren zu setzen

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createChessBoard() {
        GridLayout chessGrid = binding.chessBoard;
        chessGrid.removeAllViews();
        chessGrid.setRowCount(BOARD_SIZE);
        chessGrid.setColumnCount(BOARD_SIZE);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ImageView square = new ImageView(requireContext());
                square.setTag(new Position(row,col));
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(col, 1f);
                params.rowSpec = GridLayout.spec(row, 1f);
                params.setMargins(1, 1, 1, 1);
                square.setLayoutParams(params);

                boolean isWhite = (row + col) % 2 == 0;
                square.setBackgroundColor(Color.parseColor(isWhite ? "#EEEED2" : "#769656"));
                square.setOnTouchListener(new PieceTouchListener());


                if (row == 6) {
                    square.setImageResource(R.drawable.whitepawn);
                    square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    square.setTag("whitepawn");

                }
                if(row==1){
                    square.setImageResource(R.drawable.blackpawn);
                    square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    square.setTag("blackpawn");
                }
                if(row==0){
                    if(col==0||col==7){
                        square.setImageResource(R.drawable.blackrook);
                        square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        square.setTag("blackrook");
                    } else if (col==1||col==6) {
                        square.setImageResource(R.drawable.blackknight);
                        square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        square.setTag("blackknight");
                    } else if (col==2||col==5) {
                        square.setImageResource(R.drawable.blackbishop);
                        square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        square.setTag("blackbishop");
                    } else if (col==3) {
                        square.setImageResource(R.drawable.blackking);
                        square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        square.setTag("blackking");
                    } else {
                        square.setImageResource(R.drawable.blackqueen);
                        square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        square.setTag("blackqueen");
                    }
                }
                else {
                    if(row==7){
                        if(col==0||col==7){
                            square.setImageResource(R.drawable.whiterook);
                            square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            square.setTag("whiterook");
                        } else if (col==1||col==6) {
                            square.setImageResource(R.drawable.whiteknight);
                            square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            square.setTag("whiteknight");
                        } else if (col==2||col==5) {
                            square.setImageResource(R.drawable.whitebishop);
                            square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            square.setTag("whitebishop");
                        } else if (col==3) {
                            square.setImageResource(R.drawable.whiteking);
                            square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            square.setTag("whiteking");
                        } else {
                            square.setImageResource(R.drawable.whitequeen);
                            square.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            square.setTag("whitequeen");
                        }
                    }

                }

                SquareDragListener listener = new SquareDragListener(this, board);
                square.setOnDragListener(listener);

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
