package com.example.job.m_fugo.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.AppController;
import com.example.job.m_fugo.Server.CustomRequest;
import com.example.job.m_fugo.Server.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.job.m_fugo.Activity.Login.MyPREF;
import static com.example.job.m_fugo.Activity.MainActivity.hold_vet_id;
import static com.example.job.m_fugo.URL.api;


public class Book extends Fragment {
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

 EditText book_date, book_description;
    Button book_save;
    public Bitmap bitmap;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;
    public int PICK_IMAGE_REQUEST = 1;


    public static int userid;
    private OnFragmentInteractionListener mListener;

    public Book() {
        // Required empty public constructor
    }


    public static Book newInstance(String param1, String param2) {
        Book fragment = new Book();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences editor = getActivity().getSharedPreferences(MyPREF, MODE_PRIVATE);
        userid = editor.getInt("ID", 0);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        book_date = (EditText) view.findViewById(R.id.book_date);
        book_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date time picker

                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        book_date.setText(dateFormat.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        book_description = (EditText) view.findViewById(R.id.book_description);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        btnSelect = (Button) view.findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//noi method
                showFileChooser();

            }
        });



        book_save=(Button)view.findViewById(R.id.book_save);
        book_save.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             save();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void save(){
        final String  date=book_date.getText().toString().trim();
        final String description=book_description.getText().toString().trim();
        //final int usserid=userid;
        String image=getStringImage(bitmap);

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        Map<String,String> params = new HashMap<String,String>();
        params.put("daterequired", date);
        params.put("description",description );
        params.put("vet_id",hold_vet_id);
        params.put("image",image);
       // params.put("password",usserid );

        //StringRequest stringRequest = new StringRequest(Request.Method.POST, api+"login",
        CustomRequest jsObjRequest =new CustomRequest(Request.Method.POST, api + "bookvet/"+userid, params,
                new Response.Listener<JSONObject>() {

                    int success;
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();


                        try {
                            success=response.getInt("id");
                            if(success>=1){
                                Log.d("register successfully",response.toString());


                                Toast.makeText(getActivity(), "success ",Toast.LENGTH_LONG).show();

//                                Intent i =new Intent(Register.this, Login.class);
//                                startActivity(i);
                            }else {
                                Toast.makeText(getActivity(), "check your credentials", Toast.LENGTH_LONG).show();
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
                ivImage.setImageBitmap(bitmap);
                //storeImage.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(),""+storeImage, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(userChoosenTask.equals("Take Photo"))
//                        cameraIntent();
//                    else if(userChoosenTask.equals("Choose from Library"))
//                        galleryIntent();
//                } else {
//                    //code for deny
//                }
//                break;
//        }
//    }
//
//    private void selectImage() {
//        final CharSequence[] items = { "Take Photo", "Choose from Library",
//                "Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result= Utility.checkPermission(getActivity());
//
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
//                    if(result)
//                        cameraIntent();
//
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask ="Choose from Library";
//                    if(result)
//                        galleryIntent();
//
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void galleryIntent()
//    {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
//    }
//
//    private void cameraIntent()
//    {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }
//
//    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//       // ivImage.setImageBitmap(thumbnail);
//    }
//
//    @SuppressWarnings("deprecation")
//    private void onSelectFromGalleryResult(Intent data) {
//
//        Bitmap bm=null;
//        if (data != null) {
//            try {
//                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
//                ivImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //ivImage.setImageBitmap(bm);
//    }

}
