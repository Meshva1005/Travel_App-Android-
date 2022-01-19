package com.example.tempauthentication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class travelAdapter extends RecyclerView.Adapter<travelAdapter.MyViewHolder> {

    private List<travel> mTravel;

    public travelAdapter(List<travel> travel){
        mTravel = travel;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);

    }

    public void onBindViewHolder(MyViewHolder holder,int position){

        travel m = mTravel.get(position);
        holder.imgDisplay.setImageResource(m.getImage());
        holder.txtLocation.setText(m.getLocation());
        holder.txtName.setText(m.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), display.class);
                i.putExtra("imgDisplayCard", m.getImage());
                i.putExtra("txtDisplayLocation",m.getLocation());
                i.putExtra("txtDisplayName",m.getName());
                view.getContext().startActivity(i);
            }
        });

    }


    public int getItemCount() {
        return mTravel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgDisplay;
        TextView txtLocation;
        TextView txtName;

        public MyViewHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.raw_layout,parent,false));
            imgDisplay = itemView.findViewById(R.id.imgDisplay);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtName = itemView.findViewById(R.id.card_name);


        }
    }
}