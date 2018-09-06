package com.example.job.m_fugo.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.vetModels.Milk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.job.m_fugo.Activity.Login.MyPREF;
import static com.example.job.m_fugo.URL.api;


public class Health extends Fragment {

    List<Milk> milk1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter RecyclerHistoryAdapter;

    RequestQueue requestQueue ;
    int userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_health, container, false);

        SharedPreferences editor = getActivity().getSharedPreferences(MyPREF, MODE_PRIVATE);
        userid = editor.getInt("ID", 0);

        milk1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.healthcows);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        getCows();

        return view;
    }

    //get list of cows
    public void getCows(){
        final ProgressDialog progress = ProgressDialog.show(getActivity()," ", "Please wait...");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,api+"milkingCows/"+userid,

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

            Milk GetDataAdapter2 = new Milk();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setCow(json.getString("tag"));
                GetDataAdapter2.setCow_id(json.getString("id"));
                // GetDataAdapter2.setLast_record(json.getString(""));
                GetDataAdapter2.setDob(json.getString("dob"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            milk1.add(GetDataAdapter2);
        }

        RecyclerHistoryAdapter = new RecyclerHealthAdapter(milk1, getActivity());

        recyclerView.setAdapter(RecyclerHistoryAdapter);
    }
}
