package com.example.job.m_fugo.Server;


        import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.job.m_fugo.Activity.MainActivity;
import com.example.job.m_fugo.R;

import java.util.List;

/**
 * Created by user on 5/13/2017.
 */

public class RecyclerVetAdapter  extends RecyclerView.Adapter<RecyclerVetAdapter.ViewHolder> {
    Context context;
    List<GetVet> getvets;

    public RecyclerVetAdapter(List<GetVet> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerVetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_vets, parent, false);

        RecyclerVetAdapter.ViewHolder viewHolder = new RecyclerVetAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerVetAdapter.ViewHolder holder, int position) {

        GetVet getTicketAdapter1 = getvets.get(position);

        //holder.id.setText(getTicketAdapter1.getId());
        holder.vetName.setText(getTicketAdapter1.getvetName());
        holder.location.setText(getTicketAdapter1.getlocation());
        holder.image.setText(getTicketAdapter1.getimage());

        final String name=getTicketAdapter1.getvetName();
        final String vet_id=getTicketAdapter1.getId();

holder.bookthis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        ((MainActivity) context).book(vet_id);



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
        public TextView vetName;
        public TextView location;
        public TextView image;
        public CardView bookthis;

        public ViewHolder(View itemView) {

            super(itemView);

           // id = (TextView) itemView.findViewById(R.id.vetid);
            vetName = (TextView) itemView.findViewById(R.id.vet_name1);
            location = (TextView) itemView.findViewById(R.id.location1);
            image = (TextView) itemView.findViewById(R.id.image);
            bookthis=(CardView)itemView.findViewById(R.id.cardbook);


        }
    }


}