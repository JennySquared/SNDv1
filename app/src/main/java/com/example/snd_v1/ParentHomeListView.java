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

/**
 * Created by jenny on 2018-04-12.
 */

public class ParentHomeListView extends ArrayAdapter<String>{

    private String [] name;
    private String [] description;
    private Integer[] imgid;
    private String [] rating;
    private String [] address;

    private Activity context;

    public ParentHomeListView(@NonNull Activity context, String[] name, String[] description, Integer[] imgid, String[] address, String[] rating) {
        super(context, R.layout.babysitter_profile_list, name);

        this.context = context;
            this.name = name;
            this.rating = rating;
            this.address = address;
            this.description = description;
            this.imgid = imgid;
    }

    public void setName(String n, int index){
        name[index]=n;
    }
    public void setDescription(String n, int index){
        description[index]=n;
    }
    public void setAddress(String n, int index){
        address[index]=n;
    }
    public void setRating(String n, int index){
        rating[index]=n;
    }
    public void setImgid(int n, int index){
        imgid[index]=n;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.babysitter_profile_list,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.ivw.setImageResource(imgid[position]);
        viewHolder.tvw1.setText(name[position]);
        viewHolder.tvw2.setText(description[position]);
        viewHolder.tvw3.setText(rating[position]);
        viewHolder.tvw4.setText(address[position]);
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
            tvw2= (TextView) v.findViewById(R.id.description);
            tvw3= (TextView) v.findViewById(R.id.rating);
            tvw4= (TextView) v.findViewById(R.id.address);
            ivw= (ImageView) v.findViewById(R.id.imgid);
        }
    }
}
