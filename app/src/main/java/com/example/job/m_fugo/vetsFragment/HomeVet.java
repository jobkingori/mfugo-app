package com.example.job.m_fugo.vetsFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.job.m_fugo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeVet extends Fragment {


 LinearLayout booking,update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View  view=inflater.inflate(R.layout.fragment_home_vet, container, false);
        booking= (LinearLayout) view.findViewById(R.id.linearCheckBookings);
        update= (LinearLayout) view.findViewById(R.id.linearUpadtes);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_vetss, new FragmentVetBookList(), getString(R.string.app_name));
                ft.commit();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_vetss, new Update(), getString(R.string.app_name));
                ft.commit();
            }
        });
        return view;
    }

}
