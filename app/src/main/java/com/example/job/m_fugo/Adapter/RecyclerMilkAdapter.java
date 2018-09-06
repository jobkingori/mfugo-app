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
 * Created by JOB on 7/7/2017.
 */

public class RecyclerMilkAdapter extends RecyclerView.Adapter<RecyclerMilkAdapter.ViewHolder> {
    Context context;
    List<Milk> getvets;

    public RecyclerMilkAdapter(List<Milk> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerMilkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_miking_cow, parent, false);

        RecyclerMilkAdapter.ViewHolder viewHolder = new RecyclerMilkAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerMilkAdapter.ViewHolder holder, int position) {

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
              //  Toast.makeText(context, "Cow add record \n"+cow_name, Toast.LENGTH_SHORT).show();
                ((MainActivity) context).addMilkRecord(cow_id,cow_name);

            }
        });

        holder.cowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).milkHistory(cow_id,cow_name);

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

        public TextView cow,dob,lastRecord;
        public Button addRecord,cowHistory;


        public ViewHolder(View itemView) {

            super(itemView);

            cow = (TextView) itemView.findViewById(R.id.milkCowName);
            dob = (TextView) itemView.findViewById(R.id.milkCowDob);
            lastRecord = (TextView) itemView.findViewById(R.id.milkCowRecord);

            addRecord = (Button) itemView.findViewById(R.id.buttonAddMilkReord);
            cowHistory = (Button) itemView.findViewById(R.id.buttonMilkHistory);


        }
    }


}