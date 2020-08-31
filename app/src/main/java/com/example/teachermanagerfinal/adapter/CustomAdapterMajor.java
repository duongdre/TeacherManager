package com.example.teachermanagerfinal.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teachermanagerfinal.R;
import com.example.teachermanagerfinal.model.Major;
import com.example.teachermanagerfinal.model.Teacher;

import java.util.List;

public class CustomAdapterMajor extends ArrayAdapter<Major> {

    public Context context;
    public int resoure;
    public List<Major> listMajor;

    public CustomAdapterMajor(@NonNull Context context, int resource, @NonNull List<Major> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resoure=resource;
        this.listMajor=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_major,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView)convertView.findViewById(R.id.tv_1);
            viewHolder.tv2 = (TextView)convertView.findViewById(R.id.tv_2);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Major major = listMajor.get(position);
        viewHolder.tv1.setText(major.getmCode()+"");
        viewHolder.tv2.setText(major.getmName());

        return convertView;
    }

    public class ViewHolder{
        private TextView tv1;
        private TextView tv2;
    }
}
