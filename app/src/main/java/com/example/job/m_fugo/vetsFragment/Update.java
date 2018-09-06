package com.example.job.m_fugo.vetsFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.job.m_fugo.Activity.MainActivity.cow_id;
import static com.example.job.m_fugo.URL.api;

/**
 * A simple {@link Fragment} subclass.
 */
public class Update extends Fragment {

    EditText title,descripton;
    ImageView imageView;
    Button update;
    public static  String logged_user_id;

    public int PICK_IMAGE_REQUEST = 1;
    public Bitmap bitmap;

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_update, container, false);

           title= (EditText) view .findViewById(R.id.trendingTitle);
           descripton= (EditText) view.findViewById(R.id.trendingDescription);

           imageView= (ImageView) view.findViewById(R.id.tredingImage);

           update= (Button) view.findViewById(R.id.trendingButton);

           update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   postTrending();
               }
           });

           imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   showFileChooser();
               }
           });


           return view;
    }

    //
    public void postTrending() {

        String title1=title.getText().toString();
        String description1=descripton.getText().toString();
        String image=getStringImage(bitmap);

        Map<String,String> params = new HashMap<String,String>();
        params.put("title",title1 );
        params.put("description",description1 );
        params.put("image",image );
        params.put("vet_id","1" );

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Posting", "Please wait...");

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "postTrending", params,
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
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    /**start choose image from gallery**/
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
                //storeImage.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(),""+storeImage, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

