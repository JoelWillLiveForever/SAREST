package com.example.sarest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Card> mExampleList;

    public ExampleAdapter(Context context, ArrayList<Card> exampleList) {
        this.mContext = context;
        this.mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
//        Log.d("ADAPTER", "INFLATE" + (v == null ? "NULL" : "NOT NULL"));
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Card currentItem = mExampleList.get(position);

//        String imageUrl = currentItem.getImageUrl();
//        String creatorName = currentItem.getCreator();
//        int likeCount = currentItem.getLikeCount();
        String FIO = currentItem.getFIO();
        String Subject = currentItem.getSubject();
        int score = currentItem.getScore();

        holder.mFIO.setText(FIO);
        holder.mSubject.setText(Subject);
        holder.mScore.setText(String.valueOf(score));
//        holder.mTextViewLikes.setText("Likes: " + likeCount);
//        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
//        public ImageView mImageView;
//        public TextView mTextViewCreator;
        public TextView mFIO;
        public TextView mSubject;
        public TextView mScore;

        public ExampleViewHolder(View itemView) {
            super(itemView);

//            mImageView = itemView.findViewById(R.id.image_view);
//            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mFIO = itemView.findViewById(R.id.card_FIO);
            mSubject = itemView.findViewById(R.id.card_predmet);
            mScore = itemView.findViewById(R.id.card_score);
        }
    }
}
