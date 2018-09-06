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

import java.util.HashMap;
import java.util.Map;

import static com.example.job.m_fugo.Activity.MainActivity.cow_id;
import static com.example.job.m_fugo.Activity.MainActivity.cow_name;
import static com.example.job.m_fugo.R.id.disease;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHealthRecord extends Fragment {


    TextView cow;
    EditText EditTextDisease, EditTextPrescription, EditTextDescription, EditTextComment;
    Button btnHealthRecord;
    String EditDisease, EditPrescription, EditDescription, EditComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_health_record, container, false);

        cow = (TextView) view.findViewById(R.id.addHealthRecordCowName);
        cow.setText("Add health Record for " + cow_name);

        EditTextDisease = (EditText) view.findViewById(disease);
        EditTextPrescription = (EditText) view.findViewById(R.id.prescription);
        EditTextDescription = (EditText) view.findViewById(R.id.add_health_description);
        EditTextComment = (EditText) view.findViewById(R.id.add_health_comment);
        btnHealthRecord = (Button) view.findViewById(R.id.add_health_record);
        btnHealthRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        return view;
    }

    //vlidate
    public void validate() {
        EditDisease = EditTextDisease.getText().toString().trim();
        EditPrescription = EditTextPrescription.getText().toString().trim();
        EditDescription = EditTextDescription.getText().toString().trim();
        EditComment = EditTextComment.getText().toString().trim();

        if (TextUtils.isEmpty(EditDisease)) {
            EditTextDisease.setError("Field Cannot be Empty");
        }
        if (TextUtils.isEmpty(EditPrescription)) {
            EditTextPrescription.setError("Field Cannot be Empty");
        }
        if (TextUtils.isEmpty(EditDescription)) {
            EditTextDescription.setError("Field Cannot be Empty");
        }
        if (TextUtils.isEmpty(EditComment)) {
            EditTextComment.setError("Field Cannot be Empty");
        } else {

            postHealth();
        }
    }

    //post diease
    public void postHealth() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("disease",EditDisease );
        params.put("medicine",EditPrescription );
        params.put("description",EditDescription );
        params.put("comment",EditComment );

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Adding Record", "Please wait...");

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "addHealth/"+cow_id, params,
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