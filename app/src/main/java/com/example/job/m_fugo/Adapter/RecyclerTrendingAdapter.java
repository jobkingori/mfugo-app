package com.example.job.m_fugo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.Server.CustomVolleyRequest;
import com.example.job.m_fugo.Server.TrendingModel;

import java.util.List;

/**
 * Created by JOB on 8/22/2017.
 */

public class RecyclerTrendingAdapter extends RecyclerView.Adapter<RecyclerTrendingAdapter.ViewHolder> {
    Context context;
    List<TrendingModel> getvets;
    private ImageLoader imageLoader;

    public RecyclerTrendingAdapter(List<TrendingModel> getvets,Context context){
        super();
        this.getvets=getvets;
        this.context=context;
    }


    @Override
    public RecyclerTrendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_trending, parent, false);

        RecyclerTrendingAdapter.ViewHolder viewHolder = new RecyclerTrendingAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerTrendingAdapter.ViewHolder holder, int position) {

        TrendingModel getTicketAdapter1 = getvets.get(position);

        imageLoader = CustomVolleyRequest.getInstance((context).getApplicationContext())
                .getImageLoader();

        //holder.id.setText(getTicketAdapter1.getId());
        holder.title.setText(getTicketAdapter1.getTitle());
        holder.description.setText(getTicketAdapter1.getDescription());
        holder.image.setImageUrl(getTicketAdapter1.getImage(),imageLoader);

         final String i=getTicketAdapter1.getImage();
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
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

        public TextView title,description;
        public NetworkImageView image;



        public ViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.trendViewTitle);
            description = (TextView) itemView.findViewById(R.id.trendViewDescription);
            image = (NetworkImageView) itemView.findViewById(R.id.trendViewImage);



        }
    }


}