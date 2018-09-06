package com.example.job.m_fugo.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.AppController;
import com.example.job.m_fugo.Server.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.job.m_fugo.URL.api;

public class Login extends AppCompatActivity {
    Button login;
TextView register;
    EditText loginnumber,loginpassword;
    public String lgn_number, lgn_password;
    public static final String MyPREF ="MyPREF";
    public static  String logged_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //controls
        login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lgn_number=loginnumber.getText().toString().trim();
                lgn_password=loginpassword.getText().toString().trim();

                if (TextUtils.isEmpty(lgn_number)){
                    loginpassword.setError("Field cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(lgn_password)){
                    loginpassword.setError("Field cannot be empty");
                    return;
                }
                login();

            }
        });
        register=(TextView) findViewById(R.id.link_to_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
loginnumber=(EditText)findViewById(R.id.login_number);
        loginpassword=(EditText)findViewById(R.id.login_password);

    }


    public void login(){

        final String lgn_number=loginnumber.getText().toString().trim();
        final String lgn_password=loginpassword.getText().toString().trim();


        final ProgressDialog progress = ProgressDialog.show(Login.this,"Loading", "Please wait...");

        Map<String,String> params = new HashMap<String,String>();
        params.put("number", lgn_number);
        params.put("password",lgn_password );


        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "login", params,
                new Response.Listener<JSONObject>() {

                    int success;
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();


                        try {
                           success=response.getInt("id");
//                            if(success>=1){
//                                Log.d("register successfully",response.toString());




                                int role=response.getInt("role");

                                if (role==1){
                                   // Toast.makeText(Login.this, "success "+response, Toast.LENGTH_LONG).show();

                                    SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                                    editor.putInt("ID",success);
                                    editor.putInt("ROLE",role);
                                    editor.commit();
                                    Intent i =new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                }
                                else if(role==2){
                                   // Toast.makeText(Login.this, "success "+response, Toast.LENGTH_LONG).show();

                                    SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                                    editor.putInt("ID",success);
                                    editor.putInt("ROLE",role);
                                    editor.commit();

                                    logged_user_id=response.getString("id");
                                    Intent i =new Intent(Login.this, VetsActivity.class);
                                    startActivity(i);
                                }
                                else if(success==0){
                                    Toast.makeText(Login.this, "check your credentials", Toast.LENGTH_LONG).show();
                                }


//                            }else {
//                                Toast.makeText(Login.this, "chech your credentials", Toast.LENGTH_LONG).show();
//                            }
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
                        Toast.makeText(Login.this, "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {




        };

//        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
//        requestQueue.add(stringRequest);

        AppController.getInstance().addToRequestQueue(jsObjRequest);


    }
    /**end save to database**/


}
