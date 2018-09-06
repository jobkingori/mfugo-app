package com.example.job.m_fugo.vetsFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.job.m_fugo.Activity.Login;
import com.example.job.m_fugo.Activity.MainActivity;
import com.example.job.m_fugo.Activity.VetsActivity;
import com.example.job.m_fugo.MapsActivity;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.AppController;
import com.example.job.m_fugo.Server.CustomRequest;
import com.example.job.m_fugo.Server.CustomVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.job.m_fugo.Activity.MainActivity.cow_id;
import static com.example.job.m_fugo.Activity.VetsActivity.book_id;
import static com.example.job.m_fugo.Activity.VetsActivity.description;
import static com.example.job.m_fugo.Activity.VetsActivity.farmer_name;
import static com.example.job.m_fugo.Activity.VetsActivity.farmer_phone;
import static com.example.job.m_fugo.Activity.VetsActivity.image_sign;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookedDetails extends Fragment {

    TextView name,phone,description1;
    ImageView call,locate;
    NetworkImageView image_sign1;
    Button attended;
    private ImageLoader imageLoader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_booked_details, container, false);

        name= (TextView) view.findViewById(R.id.farmerName);
        phone= (TextView) view.findViewById(R.id.farmerPhone);
        description1= (TextView) view.findViewById(R.id.description);

        call= (ImageView) view.findViewById(R.id.callFarmer);

        attended= (Button) view.findViewById(R.id.attended);
        locate= (ImageView) view.findViewById(R.id.locate);

        image_sign1= (NetworkImageView) view.findViewById(R.id.image_sign);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });

        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treated();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "About to call "+farmer_phone, Toast.LENGTH_SHORT).show();
            }
        });
        imageLoader = CustomVolleyRequest.getInstance( getContext()).getImageLoader();

//        imageLoader = CustomVolleyRequest.getInstance((context).getApplicationContext())
//                .getImageLoader();

        name.setText(farmer_name);
        phone.setText(farmer_phone);
        description1.setText(description);

        image_sign1.setImageUrl(image_sign, imageLoader);

        return view;
    }

    //check as treated
    public  void treated(){

        Toast.makeText(getActivity(), "Booked "+book_id, Toast.LENGTH_SHORT).show();
        Map<String,String> params = new HashMap<String,String>();
       params.put("disease","" );

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "treated/"+book_id, params,
                new Response.Listener<JSONObject>() {

                    int success;
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();


                        try {
                            success=response.getInt("id");
                            if(success>=1){
                                Log.d("register successfully",response.toString());

                                Toast.makeText(getActivity(), "success ", Toast.LENGTH_LONG).show();

                                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_vetss, new FragmentVetBookList(), getString(R.string.app_name));
                                ft.commit();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Booking.this, error.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(Booking.this, error.toString(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {




        };

//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);

        AppController.getInstance().addToRequestQueue(jsObjRequest);





    }

}
