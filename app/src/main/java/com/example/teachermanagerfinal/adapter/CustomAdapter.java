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
import com.example.teachermanagerfinal.model.Teacher;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Teacher> {

    public Context context;
    public int resoure;
    public List<Teacher> listTeacher;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Teacher> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resoure=resource;
        this.listTeacher=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_teacher,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView)convertView.findViewById(R.id.tv_1);
            viewHolder.tv2 = (TextView)convertView.findViewById(R.id.tv_2);
            viewHolder.tv3 = (TextView)convertView.findViewById(R.id.tv_3);
            viewHolder.tv4 = (TextView)convertView.findViewById(R.id.tv_4);
            viewHolder.tv5 = (TextView)convertView.findViewById(R.id.tv_5);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Teacher teacher = listTeacher.get(position);
        viewHolder.tv1.setText(teacher.getmID()+"");
        viewHolder.tv2.setText(teacher.getmName());
        viewHolder.tv3.setText(teacher.getmCode());
        viewHolder.tv4.setText(teacher.getmMajor());
        viewHolder.tv5.setText(teacher.getmGender());

        return convertView;
    }

    public class ViewHolder{
        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private TextView tv4;
        private TextView tv5;
    }
}
