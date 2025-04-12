package htl.grieskirchen.schachprojekt.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import htl.grieskirchen.schachprojekt.R;
import htl.grieskirchen.schachprojekt.databinding.FragmentChessStartBinding;
import htl.grieskirchen.schachprojekt.viewmodel.MainViewModel;


public class ChessStart extends Fragment {

    FragmentChessStartBinding binding;
    MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentChessStartBinding.inflate(inflater,container,false);
        viewModel=new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.btPlay.setOnClickListener(v -> {
            if(binding.TIETBlack.getText().toString().isEmpty()||binding.TIETWhite.getText().toString().isEmpty()){
                Toast.makeText(requireContext(),"Sie müssen bei beiden etwas eingeben",Toast.LENGTH_SHORT).show();
            }
            else {
                viewModel.setNames(binding.TIETWhite.getText().toString(),binding.TIETBlack.getText().toString());
                viewModel.showBoard();
            }
        });
        return binding.getRoot();

    }
}