package com.barcrawlr.gamegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class RoundAdapter extends RealmRecyclerViewAdapter<Picture,RoundAdapter.ViewHolder> {

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView DrawImage;
        private TextView DrawGuess;

        public ViewHolder(View cardItemView){
            super(cardItemView);
            DrawImage = cardItemView.findViewById(R.id.draw_image);
            DrawGuess = cardItemView.findViewById(R.id.draw_guess);
        }

    }

    public RoundAdapter(RealmResults<Picture> data, Activity context) {
        super(data,true);
        this.context = context;
        Realm realm = Realm.getDefaultInstance();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.round_card_info,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture pos = getItem(position);
        Bitmap btmp = BitmapFactory.decodeByteArray(pos.getImage(),0,pos.getImage().length);
        holder.DrawImage.setImageBitmap(btmp);
        holder.DrawGuess.setText(pos.getWord());
    }

}
