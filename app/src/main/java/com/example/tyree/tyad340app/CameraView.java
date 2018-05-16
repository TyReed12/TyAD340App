package com.example.tyree.tyad340app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CameraView extends RecyclerView.Adapter<CameraView.CameraViewHolder> {

    private Context mContext;
    private ArrayList<Camera> mcameraList;


    public CameraView(Context mContext, ArrayList<Camera> mcameraList) {
        this.mContext = mContext;
        this.mcameraList = mcameraList;
    }


    @Override
    public CameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.camera, parent, false);
        return new CameraViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolder holder, int position) {
        Camera currentCamera = mcameraList.get(position);

        String imageUrl = currentCamera.getImageurl();
        String description = currentCamera.getDescription();

        holder.description.setText(description);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
       return mcameraList.size();
    }

    public class CameraViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView description;

        public CameraViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            description = itemView.findViewById(R.id.text_view_description);
        }
    }

}

