package com.example.job.m_fugo.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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


public class Register extends AppCompatActivity {
    //declare button edittext texviews etc
    Button register;
    EditText fullname,phonenumber,password;
    String full,phone,pass;
    TextView login;
ProgressDialog pDialog;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
//controls
        //edittexts
        fullname=(EditText)findViewById(R.id.reg_fullname);
        phonenumber=(EditText)findViewById(R.id.reg_phone);
        password=(EditText)findViewById(R.id.reg_password);


                //textview
        login=(TextView)findViewById(R.id.link_to_login);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                    }
                });
        //button
        register=(Button)findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                full=fullname.getText().toString().trim();
                phone=phonenumber.getText().toString().trim();
                pass=password.getText().toString().trim();

                if(TextUtils.isEmpty(full)){
                    fullname.setError("Field cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                   phonenumber.setError("Field cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Field cannot be empty");
                    return;
                }
                //attemp conn
                save();

            }
        });


    }


    public void save(){
      final String  full=fullname.getText().toString().trim();
        final String phone=phonenumber.getText().toString().trim();
        final String pass=password.getText().toString().trim();


        final ProgressDialog progress = ProgressDialog.show(Register.this,"Loading", "Please wait...");


        Map<String,String> params = new HashMap<String,String>();
        params.put("fullname", full);
        params.put("phonenumber",phone );
        params.put("password",pass );


        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "register", params,
                new Response.Listener<JSONObject>() {

                    int success;
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();


                        try {
                            success=response.getInt("id");
                            if(success>=1){
                                Log.d("register successfully",response.toString());


                                Toast.makeText(Register.this, "success ",Toast.LENGTH_LONG).show();


                                Intent i =new Intent(Register.this, Login.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(Register.this, "chech your credentials", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {

        };


        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}



