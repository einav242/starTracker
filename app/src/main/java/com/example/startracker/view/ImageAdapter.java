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
    private ImagesActivityView images;

    public ImageAdapter(Context context, List<Upload> uploads, ImagesActivityView images) {
        mContext = context;
        mUploads = uploads;
        this.images = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get().load(uploadCurrent.getImageUrl()) .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                images.deleteItemView(uploadCurrent.getRealDataId(), uploadCurrent.getStorageId());
            }
        });
        holder.algo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                images.algo(uploadCurrent.getImageUrl() ,uploadCurrent.getStorageId(),uploadCurrent.getRealDataId());
            }
        });

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


        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            delete = itemView.findViewById(R.id.delete_Button);
            algo = itemView.findViewById(R.id.algo_Button);

        }
    }
}
