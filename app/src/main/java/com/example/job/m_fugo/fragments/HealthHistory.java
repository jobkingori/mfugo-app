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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.job.m_fugo.Adapter.RecyclerHealthHistoryAdapter;
import com.example.job.m_fugo.Adapter.RecyclerMilkHistoryAdapter;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.vetModels.Milk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.job.m_fugo.Activity.MainActivity.cow_id;
import static com.example.job.m_fugo.Activity.MainActivity.cow_name;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthHistory extends Fragment {



    List<Milk> milk1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter RecyclerHistoryAdapter;

    // ProgressBar progressBar;

    TextView history_cowName;

    RequestQueue requestQueue ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_health_history, container, false);

        milk1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerHealthHistory);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        history_cowName= (TextView) view.findViewById(R.id.treatment_cow_name);

        history_cowName.setText("Showing Health record Of "+cow_name);

        getRecord();

        return view;
    }

    ///get history record
    public void getRecord(){

        final ProgressDialog progress = ProgressDialog.show(getActivity()," ", "Please wait...");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,api+"healthHistory/"+cow_id,


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("sucess history",response.toString());


                        //  progressBar.setVisibility(View.GONE);
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

                GetDataAdapter2.setDate(json.getString("date_treated"));
                GetDataAdapter2.setDisease(json.getString("disease"));
                GetDataAdapter2.setMedicine(json.getString("medicine"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            milk1.add(GetDataAdapter2);
        }

        RecyclerHistoryAdapter = new RecyclerHealthHistoryAdapter(milk1, getActivity());

        recyclerView.setAdapter(RecyclerHistoryAdapter);
    }
}

