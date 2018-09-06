package com.example.job.m_fugo.vetsFragment;

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
import com.example.job.m_fugo.vetModels.GetBookList;
import com.example.job.m_fugo.vetModels.RecyclerBookListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.job.m_fugo.Activity.Login.logged_user_id;
import static com.example.job.m_fugo.URL.api;
import static com.example.job.m_fugo.URL.booking;


public class FragmentVetBookList extends Fragment {
    List<GetBookList> GetList1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recycler;

    ProgressBar progressBar;

    String JSON_ID = "id";
    String JSON_FNAME = "fullname";
    //String JSON_FNAME = "id";
    String JSON_PNUMBER = "phonenumber";
    //String JSON_PNUMBER = "vetid";
    String JSON_LOCATE = "location";
    String JSON_DESCRIPTION = "description";


    RequestQueue requestQueue ;


    public FragmentVetBookList() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_vet_book_list, container, false);

        GetList1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarBookList);

        booklist();

        return view;
    }
    public void booklist(){

//        SharedPreferences editorget=getActivity().getSharedPreferences(LoginActivity.MYSHAREDPREFS, LoginActivity.MODE_PRIVATE);
//        String user_id= editorget.getString("user_id",null);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,api+"viewBookings/"+logged_user_id,


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

            GetBookList GetDataAdapter2 = new GetBookList();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                   GetDataAdapter2.setId(json.getString(JSON_ID));
                GetDataAdapter2.setName(json.getString(JSON_FNAME));
                GetDataAdapter2.setDateBooked(json.getString("created_at"));
                GetDataAdapter2.setPhone(json.getString(JSON_PNUMBER));
                GetDataAdapter2.setDescription(json.getString(JSON_DESCRIPTION));
                GetDataAdapter2.setImage(booking+json.getString("image"));
                GetDataAdapter2.setLat(json.getDouble("lat"));
                GetDataAdapter2.setLongi(json.getDouble("longi"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetList1.add(GetDataAdapter2);
        }

        recycler = new RecyclerBookListAdapter(GetList1, getActivity());

        recyclerView.setAdapter(recycler);
    }
}