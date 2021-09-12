package com.example.moviemoveapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class CustomAdapter extends SimpleAdapter {
    private JSONArray json;
    private final String preUrl = "https://image.tmdb.org/t/p/w500";

    public CustomAdapter(Context context , List<?extends Map<String , ?>> data , int resource , String[] from , int[] to , JSONArray json){
        super(context , data , resource , from ,  to);
        this.json = json;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        View output = super.getView(position , convertView , parent);
        ImageView imageView = output.findViewById(R.id.imageView);
        try {
            Picasso.get().load(preUrl + json.getJSONObject(position).getString("poster_path")).into(imageView);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Button extraInfo = output.findViewById(R.id.button);
        extraInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                            json.getJSONObject(position).getString("title") ,
                            json.getJSONObject(position).getString("vote_average") ,
                            json.getJSONObject(position).getString("release_date") ,
                            preUrl + json.getJSONObject(position).getString("poster_path") ,
                            json.getJSONObject(position).getString("overview"));
                    Navigation.findNavController(view).navigate(action);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return output;
    }
}
