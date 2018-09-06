package com.example.job.m_fugo.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.job.m_fugo.Activity.Login.MyPREF;
import static com.example.job.m_fugo.URL.api;


public class AddCowFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    public static int sharedid;

        ProgressDialog pDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     EditText txttag,txtdob,txtlocation,txtname1,txtphonenumber;
    public Button button;
    public CheckBox check;
    public LinearLayout layout1;

    String tag,dob,location,name1,phonenumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddCowFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddCowFragment newInstance(String param1, String param2) {
        AddCowFragment fragment = new AddCowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cow, container, false);
        // Inflate the layout for this fragment

//pull prefs
        SharedPreferences editor=getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        sharedid=editor.getInt("ID", 0);
        Toast.makeText(getActivity(), "success "+sharedid, Toast.LENGTH_LONG).show();


        txttag = (EditText) view.findViewById(R.id.tag);
        txtdob = (EditText) view.findViewById(R.id.dob);
        txtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date time picker

                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar newCalendar = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            txtdob.setText(dateFormat.format(newDate.getTime()));
                        }

                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();


            }
        });



//        layout1 = (LinearLayout) view.findViewById(R.id.otherDetails);
//
//        check = (CheckBox) view.findViewById(R.id.check);
//        check.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//            layout1.setVisibility(View.VISIBLE);
//            }
//
//        });
//
//        txtlocation=(EditText)view.findViewById(R.id.location);
//        txtname1=(EditText)view.findViewById(R.id.name1);
//        txtphonenumber=(EditText)view.findViewById(R.id.phonenumber);

        button=(Button)view.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tag=txttag.getText().toString().trim();
                dob=txtdob.getText().toString().trim();
//                location=txtlocation.getText().toString().trim();
//                name1=txtname1.getText().toString().trim();
//                phonenumber=txtphonenumber.getText().toString().trim();
                if(TextUtils.isEmpty(tag)){
                    txttag.setError("Field cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(dob)){
                    txtdob.setError("Field cannot be empty");
                    return;
                }
//                if(TextUtils.isEmpty(location)){
//                    txtlocation.setError("Field cannot be empty");
//
//                }
//                if(TextUtils.isEmpty(name1)){
//                    txtname1.setError("Field cannot be empty");
//                }
//                if(TextUtils.isEmpty(phonenumber)){
//                    txtphonenumber.setError("Field cannot be empty");
//                }
                post_cow();
            }
        });
    return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**start save to database**/
    private void post_cow()
    {

        final String tag = txttag.getText().toString().trim();
        final String dob = txtdob.getText().toString().trim();
//        final String location= txtlocation.getText().toString().trim();
//        final String name1=txtname1.getText().toString().trim();
//        final String phonenumber=txtphonenumber.getText().toString().trim();

//        final String ContractorOther = ContractorOthers.getText().toString().trim();
//        final String ContractorjobLocation = ContractorLocation.getText().toString().trim();
//        final String ContractorJobAmount = ContractorAmount.getText().toString().trim();
//        final String ContractorSkill = ContractorSkills.getText().toString().trim();

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");



        Map<String,String> params = new HashMap<String,String>();
        params.put("tag", tag);
        params.put("dob",dob );
//        params.put("location",location);
//        params.put("name1",name1);
//        params.put("phonenumber",phonenumber);


        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "addcow/"+sharedid, params,
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






