package htl.grieskirchen.schachprojekt.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class MainViewModel extends ViewModel {

    public static final int START =1;
    public static final int BOARD=2;
    public static final int  AFTERGAME=3;

    public final static int LEADERBOARD=4;
    public final static int ARCHIEVE=5;

    private final MutableLiveData<Boolean> isWhiteTurn=new MutableLiveData<>(true);

    private final MutableLiveData<Integer> _state=new MutableLiveData<>(BOARD);
    public final LiveData<Integer>state=_state;

    private final MutableLiveData<String>whitePlayerName=new MutableLiveData<>();
    private final MutableLiveData<String>blackPlayerName=new MutableLiveData<>();

    public boolean getIsWhiteTurn(){
        return Boolean.TRUE.equals(isWhiteTurn.getValue());
    }
    public void setIsWhiteTurn(boolean isWhiteTurn){
        this.isWhiteTurn.postValue(isWhiteTurn);
    }

    public void setNames(String white, String black){
        whitePlayerName.postValue(white);
        blackPlayerName.postValue(black);
    }
    public String getWhiteName(){
        return whitePlayerName.getValue();
    }
    public String getBlackName(){
        return blackPlayerName.getValue();
    }


    public void showStart(){
        _state.postValue(START);
    }

    public void showBoard(){
        _state.postValue(BOARD);
    }
    public void showAfterGame(){
        _state.postValue(AFTERGAME);
    }

    public void showLeaderboard(){
        _state.postValue(LEADERBOARD);
    }

    public void showArchieve(){_state.postValue(ARCHIEVE);}



}
