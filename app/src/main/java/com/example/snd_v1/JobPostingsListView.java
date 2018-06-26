package com.example.snd_v1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/*
Title: Job Posting List View
Author: Jenny S
Date: May 23, 2018
Description: Custom ListView Adapter that implements a custom format in the list view
used for the babysitter home list view
 */

public class JobPostingsListView extends ArrayAdapter<String> {

    //information for job posting, each element is the information of 1 sitter
    private String [] name;
    private String [] date;
    private Integer[] imgid;
    private int[] id;
    private String [] tEnd;
    private String [] tStart;

    private Activity context;

    //constructor method with all arrays as parameters along with the activity itself
    public JobPostingsListView(@NonNull Activity context, String[] name, String[] date, Integer[] imgid, String[] tStart, String[] tEnd, int id[]) {
        super(context, R.layout.profile_list, name);

        this.context = context;
        this.name = name;
        this.tEnd = tEnd;
        this.tStart = tStart;
        this.date = date;
        this.imgid = imgid;
    }


    //mutator methods (change one index to another value)
    public void setName(String n, int index){
        name[index]=n;
    }
    public void setDate(String n, int index){
        date[index]=n;
    }
    public void setTStart(String n, int index){
        tStart[index]="Time: "+n+"-  ";
    }
    public void setTEnd(String n, int index){
        tEnd[index]= "  "+n ;
    }
    public void setImgid(int n, int index){
        imgid[index]=n;
    }


    //set arrays' information to the textviews and imageview of the listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        JobPostingsListView.ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.profile_list,null,true);
            viewHolder = new JobPostingsListView.ViewHolder(r);
            r.setTag(viewHolder);

        }
        else{
            viewHolder = (JobPostingsListView.ViewHolder) r.getTag();
        }

        //retrieving images from Firebase
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference bb = storageReference.child(id[position]+"p.jpg");
            Glide.with(this.getContext()).load(bb).into(viewHolder.ivw);
        }
        catch(Exception e){
            viewHolder.ivw.setImageResource(imgid[position]);
        }

        viewHolder.tvw1.setText(name[position]);
        viewHolder.tvw2.setText(date[position]);
        viewHolder.tvw3.setText(tEnd[position]);
        viewHolder.tvw4.setText(tStart[position]);
        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;
        ImageView ivw;
        ViewHolder (View v){
            tvw1= (TextView) v.findViewById(R.id.name);
            tvw2= (TextView) v.findViewById(R.id.date);
            tvw3= (TextView) v.findViewById(R.id.tStart);
            tvw4= (TextView) v.findViewById(R.id.address);
            ivw= (ImageView) v.findViewById(R.id.imgid);
        }
    }
}

