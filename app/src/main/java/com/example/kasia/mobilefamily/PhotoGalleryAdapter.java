package com.example.kasia.mobilefamily;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;


public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.ViewHolder> {
    private ArrayList<String> galleryList;
    private final Context context;
    private int eventID;

    public PhotoGalleryAdapter(Context context, ArrayList<String> galleryList, int eventId) {
        this.galleryList = galleryList;
        this.context = context;
        this.eventID=eventId;
    }

    @Override
    public PhotoGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_gallery, viewGroup, false);
        return new ViewHolder(view);
    }

    public void removeImage(int idx){
        SQLiteOpenHelper familyDataBaseHelper = new FamilyDataBaseHelper(context);
        SQLiteDatabase db = familyDataBaseHelper.getReadableDatabase();


        db.delete("photo", "uri = " + "'" +galleryList.get(idx) +"'"+ " and event_id= "+ eventID, null);
        Intent intent = new Intent(context, EventPhotosActivity.class);
        intent.putExtra("eventId",eventID);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(final PhotoGalleryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageURI(Uri.parse(galleryList.get(i)));
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Czy chcesz usunąć to zdjęcie?");
                alertbox.setTitle("Usuwanie");
                final int idx1 =viewHolder.getAdapterPosition();

                alertbox.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               removeImage(idx1);
                            }
                        });

                alertbox.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertbox.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
        }
    }



}