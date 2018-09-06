package com.example.job.m_fugo.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.job.m_fugo.Activity.Login;
import com.example.job.m_fugo.R;

import static android.content.Context.MODE_PRIVATE;
import static com.example.job.m_fugo.Activity.Login.MyPREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHome extends Fragment {

    LinearLayout bookVet,regCow,mikRecord,healthRecord,trending,logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_user_home, container, false);

        bookVet= (LinearLayout) view.findViewById(R.id.linearBookVet);
        regCow= (LinearLayout) view.findViewById(R.id.linearRegisterCow);
        mikRecord= (LinearLayout) view.findViewById(R.id.linearMilkRecord);
        healthRecord= (LinearLayout) view.findViewById(R.id.linearHealthRecord);
        trending= (LinearLayout) view.findViewById(R.id.linearTrending);
        logout= (LinearLayout) view.findViewById(R.id.linearLogout);

        bookVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new Book_Vet(), getString(R.string.app_name));
                ft.commit();
            }
        });

        regCow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new AddCowFragment(), getString(R.string.app_name));
                ft.commit();
            }
        });

        mikRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new MilkingCows(), getString(R.string.app_name));
                ft.commit();

            }
        });

        healthRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new Health(), getString(R.string.app_name));
                ft.commit();

            }
        });

        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new Trending(), getString(R.string.app_name));
                ft.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(getActivity().getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
        return view;
    }

}
