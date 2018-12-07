package com.barcrawlr.gamegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class RoundAdapter extends RealmRecyclerViewAdapter<Picture,RoundAdapter.ViewHolder> {

    private Context context;
    RealmResults<Picture> results;

    //Realm realm = Realm.getDefaultInstance();

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView DrawImage;
        private TextView DrawGuess;
        private CheckBox DrawCheckbox;
        private CheckBox GuessCheckbox;

        public ViewHolder(View cardItemView){
            super(cardItemView);
            DrawImage = cardItemView.findViewById(R.id.draw_image_vote);
            DrawGuess = cardItemView.findViewById(R.id.draw_guess_vote);
            DrawCheckbox = cardItemView.findViewById(R.id.draw_checkbox);
            GuessCheckbox = cardItemView.findViewById(R.id.guess_checkbox);
        }

    }

    public RoundAdapter(RealmResults<Picture> results, Context context) {
        super( results,true);
        this.context = context;
        this.results = results;
        Realm realm = Realm.getDefaultInstance();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.round_vote_card_info,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Picture pic = results.get(position);
        commonFunctions.printImage(pic.getImage(),holder.DrawImage);
        if(pic.getWord() != null) {
            holder.DrawGuess.setText(pic.getWord());
        }
        holder.DrawCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        boolean isChecked = ((CheckBox)v).isChecked();
                        if (isChecked==true){
                            pic.setScore(pic.getScore()+1);
                        }
                        realm.insertOrUpdate(pic);
                    }
                });
            }
        });
        holder.GuessCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        boolean isChecked = ((CheckBox)v).isChecked();
                        if (isChecked==true){
                            pic.setScore(pic.getScore()+1);
                        }
                        realm.insertOrUpdate(pic);
                    }
                });
            }
        });
    }

}
