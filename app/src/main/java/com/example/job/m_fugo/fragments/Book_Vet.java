package com.example.job.m_fugo.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.GetVet;
import com.example.job.m_fugo.Server.RecyclerVetAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.job.m_fugo.URL.api;


public class Book_Vet extends Fragment {

    List<GetVet> GetVet1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter RecyclerVetAdapter;

    ProgressBar progressBar;

    //testing response
    String JSON_VETID = "id";
    String JSON_VETNAME = "fullname";
    String JSON_LOCATION = "location";
    String JSON_IMAGE = "phonenumber";

    RequestQueue requestQueue ;



    public static Book_Vet newInstance(String param1, String param2) {
        Book_Vet fragment = new Book_Vet();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book__vet, container, false);


        GetVet1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.vets);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarTicket);


        tickets();

        // CardView listOfTickets= (CardView) view.findViewById(R.id.listOfTickets);
//        recyclerView.addOnItemTouchListener(new RecyclerTicketAdapter.R);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void tickets(){

//        SharedPreferences editorget=getActivity().getSharedPreferences(LoginActivity.MYSHAREDPREFS, LoginActivity.MODE_PRIVATE);
//        String user_id= editorget.getString("user_id",null);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,api+"viewvet",


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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

            GetVet GetDataAdapter2 = new GetVet();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


               GetDataAdapter2.setId(json.getString(JSON_VETID));
                GetDataAdapter2.setvetName(json.getString(JSON_VETNAME));
                GetDataAdapter2.setlocation(json.getString(JSON_LOCATION));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetVet1.add(GetDataAdapter2);
        }

        RecyclerVetAdapter = new RecyclerVetAdapter(GetVet1, getActivity());

        recyclerView.setAdapter(RecyclerVetAdapter);
    }
}
