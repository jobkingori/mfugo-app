package com.example.job.m_fugo.vetModels;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.job.m_fugo.Activity.MainActivity;
import com.example.job.m_fugo.Activity.VetsActivity;
import com.example.job.m_fugo.R;

import java.util.List;

/**
 * Created by JOB on 6/5/2017.
 */

public class RecyclerBookListAdapter   extends RecyclerView.Adapter<RecyclerBookListAdapter.ViewHolder> {
    Context context;
    List<GetBookList> getvets;

    public RecyclerBookListAdapter(List<GetBookList> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerBookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_book_list, parent, false);

        RecyclerBookListAdapter.ViewHolder viewHolder = new RecyclerBookListAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerBookListAdapter.ViewHolder holder, int position) {

        GetBookList getTicketAdapter1 = getvets.get(position);

        ///holder.id.setText(getTicketAdapter1.getId());
        holder.name.setText(getTicketAdapter1.getName());
        holder.dateBooked.setText(getTicketAdapter1.getDateBooked());
        holder.phone.setText(getTicketAdapter1.getPhone());

       final String description=getTicketAdapter1.getDescription();
       final String image=getTicketAdapter1.getImage();
       final String phone=getTicketAdapter1.getPhone();
       final String name=getTicketAdapter1.getName();
       final String id=getTicketAdapter1.getId();
       final double lat=getTicketAdapter1.getLat();
       final double longi=getTicketAdapter1.getLongi();


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VetsActivity) context).goToViewMore(id,name,phone,image,description,lat,longi);
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

        public TextView id;
        public TextView name;
        public TextView dateBooked;
        public TextView phone;
        public CardView card;


        public ViewHolder(View itemView) {

            super(itemView);

            // id = (TextView) itemView.findViewById(R.id.vetid);
            name = (TextView) itemView.findViewById(R.id.vet_book_list_farmer_name);
            dateBooked = (TextView) itemView.findViewById(R.id.dateBooked);
            phone = (TextView) itemView.findViewById(R.id.vet_book_list_farmer_phone);
            card=(CardView)itemView.findViewById(R.id.vet_book_list);


        }
    }


}