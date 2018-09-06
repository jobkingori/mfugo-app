package com.example.job.m_fugo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.job.m_fugo.Activity.MainActivity;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.vetModels.Milk;

import java.util.List;

/**
 * Created by JOB on 7/25/2017.
 */

public class RecyclerHealthAdapter extends RecyclerView.Adapter<RecyclerHealthAdapter.ViewHolder> {
    Context context;
    List<Milk> getvets;

    public RecyclerHealthAdapter(List<Milk> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerHealthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_health, parent, false);

        RecyclerHealthAdapter.ViewHolder viewHolder = new RecyclerHealthAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerHealthAdapter.ViewHolder holder, int position) {

        Milk getTicketAdapter1 = getvets.get(position);

        //holder.id.setText(getTicketAdapter1.getId());
        holder.cow.setText(getTicketAdapter1.getCow());
        holder.dob.setText(getTicketAdapter1.getDob());
        // holder.lastRecord.setText(getTicketAdapter1.getLast_record());

        final String cow_id=getTicketAdapter1.getCow_id();
        final String cow_name=getTicketAdapter1.getCow();


        holder.addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // Toast.makeText(context, "Cow add record \n"+cow_name, Toast.LENGTH_SHORT).show();
                ((MainActivity) context).addHealthRecord(cow_id,cow_name);

            }
        });

        holder.cowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).getHealthHistory(cow_id,cow_name);
               // Toast.makeText(context, "Cow History \n"+cow_name, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //get counts
    @Override
    public int getItemCount() {

        return getvets.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cow,dob,gender;
        public Button addRecord,cowHistory;


        public ViewHolder(View itemView) {

            super(itemView);

            cow = (TextView) itemView.findViewById(R.id.healthCowName);
            dob = (TextView) itemView.findViewById(R.id.healthCowDob);
            gender = (TextView) itemView.findViewById(R.id.healthCowGender);

            addRecord = (Button) itemView.findViewById(R.id.buttonAddHelathRecord);
            cowHistory = (Button) itemView.findViewById(R.id.buttonHealthHistory);


        }
    }


}