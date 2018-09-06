package com.example.job.m_fugo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.job.m_fugo.R;
import com.example.job.m_fugo.vetModels.Milk;

import java.util.List;

/**
 * Created by JOB on 7/27/2017.
 */

public class RecyclerHealthHistoryAdapter extends RecyclerView.Adapter<RecyclerHealthHistoryAdapter.ViewHolder> {
    Context context;
    List<Milk> getvets;

    public RecyclerHealthHistoryAdapter(List<Milk> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerHealthHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_health_history, parent, false);

        RecyclerHealthHistoryAdapter.ViewHolder viewHolder = new RecyclerHealthHistoryAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerHealthHistoryAdapter.ViewHolder holder, int position) {

        Milk getTicketAdapter1 = getvets.get(position);

        //holder.id.setText(getTicketAdapter1.getId());
        holder.date.setText(getTicketAdapter1.getDate());
        holder.disease.setText(getTicketAdapter1.getDisease());
        holder.medicine.setText(getTicketAdapter1.getMedicine());


    }

    //get counts
    @Override
    public int getItemCount() {

        return getvets.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date,disease,medicine;



        public ViewHolder(View itemView) {

            super(itemView);

            disease = (TextView) itemView.findViewById(R.id.history_health_disease);
            medicine = (TextView) itemView.findViewById(R.id.history_health_medicine);
            date = (TextView) itemView.findViewById(R.id.history_health_date);


        }
    }


}