package com.example.job.m_fugo.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.job.m_fugo.Adapter.RecyclerMilkAdapter;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.RecyclerVetAdapter;
import com.example.job.m_fugo.vetModels.Milk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.job.m_fugo.Activity.Login.MyPREF;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class MilkingCows extends Fragment {
    List<Milk> milk1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter RecyclerVetAdapter;

    ProgressBar progressBar;
    
    int userid;
    RequestQueue requestQueue ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_milk_record, container, false);

        SharedPreferences editor = getActivity().getSharedPreferences(MyPREF, MODE_PRIVATE);
        userid = editor.getInt("ID", 0);

        milk1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.milkingCows);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        progressBar = (ProgressBar) view.findViewById(R.id.progressMilkingCows);
        
        getCows();
        
        return  view;
    }//go online
    private void getCows() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,api+"milkingCows/"+userid,


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("register successfully",response.toString());


                        progressBar.setVisibility(View.GONE);

                        json_parse_data(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
//                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText("Oops...!")
//                                .setContentText("Something went wrong!PLEASE CHECK YOUR NETWORK CONNECTION")
//                                .show();
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

        RecyclerVetAdapter = new RecyclerMilkAdapter(milk1, getActivity());

        recyclerView.setAdapter(RecyclerVetAdapter);
    }
}

