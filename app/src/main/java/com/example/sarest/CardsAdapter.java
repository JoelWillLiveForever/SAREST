package com.example.sarest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {
    private Context mContext;
    private List<Card> mCards;

    public CardsAdapter(Context context, List<Card> mCards) {
        this.mContext = context;
        this.mCards = mCards;
    }

    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
//        Log.d("ADAPTER", "INFLATE" + (v == null ? "NULL" : "NOT NULL"));
        return new CardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {
        Card currentItem = mCards.get(position);

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
        return mCards.size();
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder {
//        public ImageView mImageView;
//        public TextView mTextViewCreator;
        public TextView mFIO;
        public TextView mSubject;
        public TextView mScore;

        public CardsViewHolder(View itemView) {
            super(itemView);

//            mImageView = itemView.findViewById(R.id.image_view);
//            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mFIO = itemView.findViewById(R.id.card_FIO);
            mSubject = itemView.findViewById(R.id.card_predmet);
            mScore = itemView.findViewById(R.id.card_score);
        }
    }
}
