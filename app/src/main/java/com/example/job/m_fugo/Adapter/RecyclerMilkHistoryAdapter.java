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

public class RecyclerMilkHistoryAdapter extends RecyclerView.Adapter<RecyclerMilkHistoryAdapter.ViewHolder> {
    Context context;
    List<Milk> getvets;

    public RecyclerMilkHistoryAdapter(List<Milk> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerMilkHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_milk_history, parent, false);

        RecyclerMilkHistoryAdapter.ViewHolder viewHolder = new RecyclerMilkHistoryAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerMilkHistoryAdapter.ViewHolder holder, int position) {

        Milk getTicketAdapter1 = getvets.get(position);

        //holder.id.setText(getTicketAdapter1.getId());
        holder.history_date.setText(getTicketAdapter1.getDate());
        holder.history_quantity.setText(getTicketAdapter1.getQuantity()+" Litres");


    }

    //get counts
    @Override
    public int getItemCount() {

        return getvets.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView history_date,history_quantity;



        public ViewHolder(View itemView) {

            super(itemView);

            history_quantity = (TextView) itemView.findViewById(R.id.history_quantity);
            history_date = (TextView) itemView.findViewById(R.id.history_date);


        }
    }


}