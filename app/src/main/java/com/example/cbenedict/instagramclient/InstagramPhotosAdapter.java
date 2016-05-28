package com.example.cbenedict.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cbenedict on 5/12/2016.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
     //What data do we need for the Activity
    //Context data source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

    }
    //What our item look like
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
    //Get the data item for thi position
        InstagramPhoto photo=getItem(position);
        //Check if we are using a recycle view, if not we need to inflate
        if (convertView==null){
            //create a new view from template
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }
        //lookup the view for populating the data (image, caption)
        TextView tvCaption=(TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto=(ImageView) convertView.findViewById(R.id.ivPhoto);
        //insert the data into ezch of the view items
        tvCaption.setText(photo.caption);
        //clear out the image view
        ivPhoto.setImageResource(0);
        //insert the image using Picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        //returns the created items as a view
        return convertView;
    }
}
