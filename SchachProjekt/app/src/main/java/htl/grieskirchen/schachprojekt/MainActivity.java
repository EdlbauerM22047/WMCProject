package htl.grieskirchen.schachprojekt;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import htl.grieskirchen.schachprojekt.fragments.ChessBoard;
import htl.grieskirchen.schachprojekt.fragments.ChessStart;
import htl.grieskirchen.schachprojekt.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.state.observe(this,state->{
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        });

        mainViewModel.state.observe(this, state -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (state == MainViewModel.START) {
                fragmentTransaction.replace(R.id.main, new ChessStart(), "NEW TICKET");

            }
            if (state == MainViewModel.BOARD) {
                fragmentTransaction.replace(R.id.main, new ChessBoard(), "SHOW TICKET");
            }
            if (state == MainViewModel.AFTERGAME) {
                //fragmentTransaction.replace(R.id.main,new AfterGame(),"SETTING");

            }
            if (state == MainViewModel.LEADERBOARD) {
                //fragmentTransaction.replace(R.id.main,new Leaderboard(),"DETAILS");//.addToBackStack("TICKET");
            }
            if (state == MainViewModel.ARCHIEVE) {
                //fragmentTransaction.replace(R.id.main,new Archieve(),"DETAILS");//.addToBackStack("TICKET");
            }

            fragmentTransaction.commit();


        });
    }
}
