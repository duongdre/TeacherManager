package com.example.teachermanagerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagerfinal.adapter.CustomAdapter;
import com.example.teachermanagerfinal.adapter.CustomAdapterMajor;
import com.example.teachermanagerfinal.data.DatabaseHelper;
import com.example.teachermanagerfinal.data.DatabaseHelperMajor;
import com.example.teachermanagerfinal.model.Major;
import com.example.teachermanagerfinal.model.Teacher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BMActivity extends AppCompatActivity {

    Button btn_acceptAddMajor, btn_cancleAddMajor, btn_acceptUpdateMajor, btn_cancleUpdateMajor;
    ImageButton ibtn_addMajor, ibtn_showGiaoVien, ibtn_showBoMon;
    EditText textCodeMajor, textNameMajor;

    public DatabaseHelper databaseHelper;
    public DatabaseHelperMajor databaseHelperMajor;
    public CustomAdapterMajor customAdapterMajor;
    public static List<String> majorListName;

    ListView lvMajor;
    List<Major> majorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm);

        databaseHelperMajor = new DatabaseHelperMajor(this);
        databaseHelper = new DatabaseHelper(this);
        inWidget();

        lvMajor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*Major major = majorList.get(position);
                String query = major.getmName();
                majorList.clear();
                majorList.clear();
                List<Major> queryResult = new ArrayList<>();
                queryResult = databaseHelperMajor.searchTeacherWithMajor(query);
                CustomAdapterMajor cm = new CustomAdapterMajor(BMActivity.this, R.layout.item_list_major, queryResult);
                lvMajor.setAdapter(cm);*/

                Major major = majorList.get(position);
                Cursor cur = databaseHelper.getData();
                if (cur.getCount()==0){
                    Toast.makeText(BMActivity.this, "Không tồn tại Bộ môn", Toast.LENGTH_SHORT).show();
                    //showMessage("Danh sách:", "Rỗng");
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (cur.moveToNext()){
                        String query = cur.getString(4);
                        String search = major.getmName();
                        if (query.equalsIgnoreCase(search)){
                            buffer.append("---------------------------------------------\nMã Giáo viên: "+ cur.getString(1)+"\n");
                            buffer.append("Tên: "+ cur.getString(2)+"\n---------------------------------------------\n");
                            Toast.makeText(BMActivity.this, search+"-"+query, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(BMActivity.this, "Danh sách", Toast.LENGTH_SHORT).show();
                        }
                    }
                    showMessage( "Danh sách giáo viên thuộc: \n"+major.getmName(), buffer.toString());
                }
                return false;
            }
        });

        lvMajor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdateMajor(position);
            }
        });

        ibtn_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMajor();
            }
        });

    }

    public void showUpdateMajor(final int position){
        final Dialog dialogx = new Dialog(this);
        dialogx.setContentView(R.layout.dialog_long_click_major);
        dialogx.setCanceledOnTouchOutside(false);

        final Major major = majorList.get(position);

        textCodeMajor = (EditText) dialogx.findViewById(R.id.editText_code);
        textNameMajor = (EditText) dialogx.findViewById(R.id.editText_name);

        textCodeMajor.setText(major.getmCode());
        textNameMajor.setText(major.getmName());

        ImageButton btn_acceptDelete = dialogx.findViewById(R.id.btn_acceptDelete);
        Button btn_acceptUpdate = dialogx.findViewById(R.id.btn_acceptUpdate);
        Button btn_cancleUpdate = dialogx.findViewById(R.id.btn_cancleUpdate);
        ImageButton ibtn_showGiaoVien = dialogx.findViewById(R.id.ibtn_showGV);
        ImageButton ibtn_showBoMon = dialogx.findViewById(R.id.ibtn_showBM);

        ibtn_showGiaoVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major major = majorList.get(position);
                Cursor cur = databaseHelper.getData();
                if (cur.getCount()==0){
                    Toast.makeText(BMActivity.this, "Không tồn tại Bộ môn", Toast.LENGTH_SHORT).show();
                    //showMessage("Danh sách:", "Rỗng");
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (cur.moveToNext()){
                        String query = cur.getString(4);
                        String search = major.getmName();
                        if (query.equalsIgnoreCase(search)){
                            buffer.append("---------------------------------------------\nMã Giáo viên: "+ cur.getString(1)+"\n");
                            buffer.append("Tên: "+ cur.getString(2)+"\n---------------------------------------------\n");
                            Toast.makeText(BMActivity.this, search+"-"+query, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(BMActivity.this, "Danh sách", Toast.LENGTH_SHORT).show();
                        }
                    }
                    StringBuffer checkempty = new StringBuffer();
                        showMessage( "Danh sách giáo viên thuộc: \n"+major.getmName(), buffer.toString());
                }
            }
        });

        btn_acceptDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major major = majorList.get(position);
                int isDelete = databaseHelperMajor.deteleMajor(major.getmID()+"");
                if (isDelete > 0){
                    databaseHelper.deleteMajorTeacher(major.getmName());
                    majorList.clear();
                    majorList.addAll(databaseHelperMajor.getAllMajor());
                    setCustomAdapterMajorNotChange();
                    dialogx.dismiss();
                    Toast.makeText(BMActivity.this, "ĐÃ XÓA BỘ MÔN", Toast.LENGTH_SHORT).show();

                }else{
                    majorList.clear();
                    majorList.addAll(databaseHelperMajor.getAllMajor());
                    setCustomAdapterMajorNotChange();
                    dialogx.dismiss();
                    Toast.makeText(BMActivity.this, "LỖI KHI XÓA BỘ MÔN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_acceptUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major.setmCode(textCodeMajor.getText().toString());
                major.setmName(textNameMajor.getText().toString());

                databaseHelperMajor.updateMajor(major);
                majorList.clear();
                majorList.addAll(databaseHelperMajor.getAllMajor());
                setCustomAdapterMajor();
                dialogx.dismiss();
                Toast.makeText(BMActivity.this, "ĐÃ SỬA BỘ MÔN", Toast.LENGTH_SHORT).show();
                //databaseHelper.updateMajorTeacher(major.getmName());
            }
        });



        btn_cancleUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogx.dismiss();
                Toast.makeText(BMActivity.this, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        dialogx.show();
    }

    public void showAddMajor(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_major);
        dialog.setCanceledOnTouchOutside(false);

        textCodeMajor = (EditText) dialog.findViewById(R.id.editText_code);
        textNameMajor = (EditText) dialog.findViewById(R.id.editText_name);

        btn_acceptAddMajor = dialog.findViewById(R.id.btn_acceptAdd);
        btn_cancleAddMajor = dialog.findViewById(R.id.btn_cancleAdd);

        btn_acceptAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCodeMajor.setText(textCodeMajor.getText().toString());
                textNameMajor.setText(textNameMajor.getText().toString());

                databaseHelperMajor.insertMajor(textCodeMajor.getText().toString(),textNameMajor.getText().toString());

                System.out.println(textCodeMajor.getText().toString());

                majorList.clear();
                majorList.addAll(databaseHelperMajor.getAllMajor());
                setCustomAdapterMajor();
                dialog.dismiss();
                Toast.makeText(BMActivity.this, "ĐÃ THÊM BỘ MÔN", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancleAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(BMActivity.this, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void inWidget(){
        majorListName = new LinkedList<>();
        majorListName = databaseHelperMajor.getAllMajorName();
        /*majorListName.add("Toán Tin");
        majorListName.add("Du Lịch");
        majorListName.add("Marketing");
        majorListName.add("Ngôn ngữ");*/

        ibtn_addMajor = (ImageButton) findViewById(R.id.ibtn_addMajor);
        lvMajor = (ListView) findViewById(R.id.lv_major);
        majorList = databaseHelperMajor.getAllMajor();

        setCustomAdapterMajor();
    }

    public void setCustomAdapterMajor(){
        if(customAdapterMajor == null){
            customAdapterMajor = new CustomAdapterMajor(this, R.layout.item_list_teacher,majorList);
            lvMajor.setAdapter(customAdapterMajor);
        }else{
            customAdapterMajor.notifyDataSetChanged();
            lvMajor.setSelection(customAdapterMajor.getCount()-1);
        }
    }

    public void setCustomAdapterMajorNotChange(){
        if(customAdapterMajor == null){
            customAdapterMajor = new CustomAdapterMajor(this, R.layout.item_list_teacher,majorList);
            lvMajor.setAdapter(customAdapterMajor);
        }else{
            customAdapterMajor.notifyDataSetChanged();
        }
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
