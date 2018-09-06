package com.example.job.m_fugo.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.job.m_fugo.Adapter.RecyclerHealthAdapter;
import com.example.job.m_fugo.Adapter.RecyclerTrendingAdapter;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.TrendingModel;
import com.example.job.m_fugo.vetModels.Milk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.job.m_fugo.URL.api;
import static com.example.job.m_fugo.URL.img;

/**
 * A simple {@link Fragment} subclass.
 */
public class Trending extends Fragment {

    List<TrendingModel> milk1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter RecyclerAdapter;

    RequestQueue requestQueue ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trending, container, false);
        milk1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerTrending);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        trending();
        return view;
    }

    private void trending() {

        final ProgressDialog progress = ProgressDialog.show(getActivity()," ", "Please wait...");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,api+"getTrending",

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("sucess history",response.toString());
                        progress.dismiss();

                        json_parse_data(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        //  progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Something went wrong!PLEASE CHECK YOUR NETWORK CONNECTION", Toast.LENGTH_SHORT).show();


                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array){
        for(int i = 0; i<array.length(); i++) {

            TrendingModel GetDataAdapter2 = new TrendingModel();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setTitle(json.getString("title"));
                GetDataAdapter2.setDescription(json.getString("description"));
                // GetDataAdapter2.setLast_record(json.getString(""));
                GetDataAdapter2.setImage(img+json.getString("image"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            milk1.add(GetDataAdapter2);
        }

        RecyclerAdapter = new RecyclerTrendingAdapter(milk1, getActivity());

        recyclerView.setAdapter(RecyclerAdapter);
    }

}
