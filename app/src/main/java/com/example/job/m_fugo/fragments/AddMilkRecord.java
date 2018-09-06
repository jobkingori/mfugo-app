package com.example.job.m_fugo.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.job.m_fugo.Activity.MainActivity;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.AppController;
import com.example.job.m_fugo.Server.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.example.job.m_fugo.Activity.MainActivity.cow_id;
import static com.example.job.m_fugo.Activity.MainActivity.cow_name;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMilkRecord extends Fragment {

    EditText editmilkRecord;
    Button btnAddMilkRecord;
    TextView textCowName;
    String txtCname,editmilk;
    public AddMilkRecord() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_milk_record, container, false);
        textCowName=(TextView) view.findViewById(R.id.txtCname);
        editmilkRecord = (EditText) view.findViewById(R.id.record);
        btnAddMilkRecord = (Button) view.findViewById(R.id.btnAddRecord);

        //set cow name
        textCowName.setText(cow_name);

        btnAddMilkRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCname=textCowName.getText().toString().trim();
                editmilk=editmilkRecord.getText().toString().trim();

                if(TextUtils.isEmpty(txtCname)){
                    textCowName.setError("Field cannot be Empty");
                }
                if(TextUtils.isEmpty(editmilk)){
                 editmilkRecord.setError("Field cannot be Empty");
                }
                post_milk();
               // Toast.makeText(getContext(), "cow id  "+cow_id, Toast.LENGTH_SHORT).show();
            }
        });
        return  view;


    }
private void post_milk(){
    final String quantity = editmilkRecord.getText().toString().trim();

    Map<String,String> params = new HashMap<String,String>();
   // params.put("txtCname", txtCname);
    params.put("quantity",quantity );
  //  params.put("cow_id",cow_id );

    final ProgressDialog progress = ProgressDialog.show(getActivity(),"Adding Record", "Please wait...");

    //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
    CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "addMilk/"+cow_id, params,
            new Response.Listener<JSONObject>() {

                int success;
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismiss();


                    try {
                        success=response.getInt("id");
                        if(success>=1){
                            Log.d("register successfully",response.toString());


                            Toast.makeText(getActivity(), "success "+response, Toast.LENGTH_LONG).show();


                            Intent i =new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getActivity(), "chech your credentials", Toast.LENGTH_LONG).show();
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
