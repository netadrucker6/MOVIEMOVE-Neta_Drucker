package com.example.moviemoveapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviemoveapp.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private final String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=e9b316cc8217758cfeb22c8299d2e482&language=en-US";
    private JSONArray json;
    private RequestQueue queue;


    private void startRequestQueue(){
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getContext().getCacheDir() , 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
               queue = new RequestQueue(cache, network);

        // Start the queue
                queue.start();
    }

    private void onResponse(JSONObject object){
        try {
            json = object.getJSONArray("results");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        ArrayList<HashMap<String , String>> list = new ArrayList<>();
        String[] from = {"title"};
        int[] to = {R.id.movie_name};
        for (int i = 0 ; i < json.length() ; i++){
            try {
                HashMap<String , String> currMap = new HashMap<>();
                JSONObject currObject = json.getJSONObject(i);
                currMap.put("title" , currObject.getString("title"));
                list.add(currMap);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CustomAdapter adapter = new CustomAdapter(getContext() , list , R.layout.list_item , from , to , json);
        binding.listView.setAdapter(adapter);
    }
    private void onError(VolleyError error){
        System.out.println("ERROR");
        Toast.makeText(getContext() , "please check your internet connection!", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        startRequestQueue();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET , url , null , this::onResponse , this::onError);
        queue.add(json);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}