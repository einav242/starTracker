package com.example.startracker.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.startracker.R;
import com.example.startracker.entities.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private int flag;

    public ImageAdapter(Context context, List<Upload> uploads, int flag) {
        mContext = context;
        mUploads = uploads;
        this.flag = flag;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(this.flag == 0){
            v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        }else{
            v = LayoutInflater.from(mContext).inflate(R.layout.image_processed_item, parent, false);
        }
        return new ImageViewHolder(v, this.flag);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get().load(uploadCurrent.getImageUrl()) .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        public Button delete;
        public Button algo;


        public ImageViewHolder(View itemView, int flag) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            delete = itemView.findViewById(R.id.delete_Button);
            if(flag == 0){
                algo = itemView.findViewById(R.id.algo_Button);
            }
        }
    }
}
