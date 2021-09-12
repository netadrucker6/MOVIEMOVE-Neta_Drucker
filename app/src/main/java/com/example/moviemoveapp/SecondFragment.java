package com.example.moviemoveapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.moviemoveapp.databinding.FragmentSecondBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        @NonNull SecondFragmentArgs args = SecondFragmentArgs.fromBundle(getArguments());
        binding.movieName.setText(args.getMovieName());
        Picasso.get().load(args.getMovieImage()).into(binding.imageView2);
        binding.movieRating.setText(getString(R.string.Rating , args.getMovieRating()));
        binding.movieReleaseDay.setText(getString(R.string.Release_Date , args.getMovieReleaseDay()));
        binding.movieDescription.setText(args.getMovieDescription());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}