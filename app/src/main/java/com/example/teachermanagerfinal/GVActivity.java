package com.example.teachermanagerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
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
import com.example.teachermanagerfinal.data.DatabaseHelper;
import com.example.teachermanagerfinal.model.Teacher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GVActivity extends AppCompatActivity {

    Button btn_acceptAdd, btn_cancleAdd, btn_acceptUpdate, btn_cancleUpdate, btn_major;
    ImageButton btn_acceptDelete, ibtn_add, ibtn_search;
    EditText textCode, textName, textSalary, textSearch;
    TextView textMajor;
    String textGender;
    CheckBox checkNam, checkNu;

    public ListView lvTeacher;
    public DatabaseHelper databaseHelper;
    public CustomAdapter customAdapter;
    public List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gv);

        databaseHelper = new DatabaseHelper(this);
        inWidget();

        lvTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdate(position);
            }
        });

        ibtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });

        ibtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String query = textSearch.getText().toString();
                teacherList.clear();
                teacherList = databaseHelper.searchTeacher(query);
                setCustomAdapter();*/
                String query = textSearch.getText().toString();
                teacherList.clear();
                List<Teacher> queryResult = new ArrayList<>();
                queryResult = databaseHelper.searchTeacher(query);
                CustomAdapter cm = new CustomAdapter(GVActivity.this, R.layout.item_list_teacher, queryResult);
                lvTeacher.setAdapter(cm);
            }
        });

        ibtn_search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Cursor cur = databaseHelper.getData();
                if (cur.getCount()==0){
                    Toast.makeText(GVActivity.this, "Không tồn tại Giáo viên", Toast.LENGTH_SHORT).show();
                    //showMessage("Danh sách:", "Rỗng");
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (cur.moveToNext()){
                        String query = cur.getString(2);
                        String search = textSearch.getText().toString();
                        if (query.equalsIgnoreCase(search)){
                            buffer.append("-------\nID: "+ cur.getString(0)+"\n");
                            buffer.append("Code: "+ cur.getString(1)+"\n");
                            buffer.append("Name: "+ cur.getString(2)+"\n");
                            buffer.append("Gender: "+ cur.getString(3)+"\n");
                            buffer.append("Major: "+ cur.getString(4)+"\n");
                            buffer.append("Salary: "+ cur.getString(5)+"\n-------\n");
                        }else{
                            Toast.makeText(GVActivity.this, search+"-"+query, Toast.LENGTH_SHORT).show();
                        }
                    }
                    showMessage( "Danh sách: ", buffer.toString());
                }
                return false;
            }
        });
    }

    public void showUpdate(final int position){
        final Dialog dialogx = new Dialog(this);
        dialogx.setContentView(R.layout.dialog_longclick);
        dialogx.setCanceledOnTouchOutside(false);

        final Teacher teacher = teacherList.get(position);

        textCode = (EditText) dialogx.findViewById(R.id.editText_code);
        textName = (EditText) dialogx.findViewById(R.id.editText_name);
        textMajor = (TextView) dialogx.findViewById(R.id.editText_major);
        textSalary = (EditText) dialogx.findViewById(R.id.editText_salary);
        checkNam = (CheckBox) dialogx.findViewById(R.id.checkbox_nam);
        checkNu = (CheckBox) dialogx.findViewById(R.id.checkbox_nu);

        textCode.setText(teacher.getmCode());
        textName.setText(teacher.getmName());
        textMajor.setText(teacher.getmMajor());
        textSalary.setText(teacher.getmSalary());
        checkNam = (CheckBox) dialogx.findViewById(R.id.checkbox_nam);
        checkNu = (CheckBox) dialogx.findViewById(R.id.checkbox_nu);

        btn_acceptDelete = dialogx.findViewById(R.id.btn_acceptDelete);
        btn_acceptUpdate = dialogx.findViewById(R.id.btn_acceptUpdate);
        btn_cancleUpdate = dialogx.findViewById(R.id.btn_cancleUpdate);
        btn_major = dialogx.findViewById(R.id.btn_major);

        btn_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GVActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner_major, null);
                builder.setTitle("Chọn Bộ môn");
                final Spinner mSpinner = mView.findViewById(R.id.spinner_major);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(GVActivity.this, android.R.layout.simple_spinner_item, BMActivity.majorListName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                System.out.println(BMActivity.majorListName);
                mSpinner.setAdapter(adapter);

                builder.setPositiveButton("CHỌN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textMajor.setText(mSpinner.getSelectedItem().toString());
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_acceptDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher teacher = teacherList.get(position);
                int isDelete = databaseHelper.deteleTeacher(teacher.getmID()+"");
                if (isDelete > 0){
                    teacherList.clear();
                    teacherList.addAll(databaseHelper.getAllTeacher());
                    setCustomAdapter();
                    dialogx.dismiss();
                    Toast.makeText(GVActivity.this, "ĐÃ XÓA GIÁO VIÊN", Toast.LENGTH_SHORT).show();
                }else{
                    teacherList.clear();
                    teacherList.addAll(databaseHelper.getAllTeacher());
                    setCustomAdapterNotChange();
                    dialogx.dismiss();
                    Toast.makeText(GVActivity.this, "LỖI KHI XÓA GIÁO VIÊN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_acceptUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher.setmCode(textCode.getText().toString());
                teacher.setmName(textName.getText().toString());
                if((checkNam.isChecked()) && (!checkNu.isChecked())){
                    textGender="Nam";
                }else{
                    if(!checkNam.isChecked() && checkNu.isChecked()){
                        textGender="Nữ";
                    }else{
                        textGender="Không rõ";
                    }
                }
                teacher.setmGender(textGender);
                teacher.setmMajor(textMajor.getText().toString());
                teacher.setmSalary(textSalary.getText().toString());
                databaseHelper.updateTeacher(teacher);
                teacherList.clear();
                teacherList.addAll(databaseHelper.getAllTeacher());
                setCustomAdapterNotChange();
                dialogx.dismiss();
                Toast.makeText(GVActivity.this, "ĐÃ SỬA GIÁO VIÊN", Toast.LENGTH_SHORT).show();
            }
        });



        btn_cancleUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogx.dismiss();
                Toast.makeText(GVActivity.this, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        dialogx.show();
    }
    public void inWidget(){
        ibtn_add = findViewById(R.id.ibtn_add);
        ibtn_search = findViewById(R.id.ibtn_search);
        textSearch = findViewById(R.id.editText_search);
        lvTeacher = (ListView) findViewById(R.id.lv_teacher);
        teacherList = databaseHelper.getAllTeacher();
        setCustomAdapter();

    }

    public void showAdd(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);
        dialog.setCanceledOnTouchOutside(false);

        textCode = (EditText) dialog.findViewById(R.id.editText_code);
        textName = (EditText) dialog.findViewById(R.id.editText_name);
        textMajor = (TextView) dialog.findViewById(R.id.editText_major);
        textSalary = (EditText) dialog.findViewById(R.id.editText_salary);
        checkNam = (CheckBox) dialog.findViewById(R.id.checkbox_nam);
        checkNu = (CheckBox) dialog.findViewById(R.id.checkbox_nu);

        btn_acceptAdd = dialog.findViewById(R.id.btn_acceptAdd);
        btn_cancleAdd = dialog.findViewById(R.id.btn_cancleAdd);
        btn_major = dialog.findViewById(R.id.btn_major);

        btn_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GVActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner_major, null);
                builder.setTitle("Chọn Bộ môn");
                final Spinner mSpinner = mView.findViewById(R.id.spinner_major);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(GVActivity.this, android.R.layout.simple_spinner_item, BMActivity.majorListName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                System.out.println(BMActivity.majorListName);
                mSpinner.setAdapter(adapter);

                builder.setPositiveButton("CHỌN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textMajor.setText(mSpinner.getSelectedItem().toString());
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_acceptAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCode.setText(textCode.getText().toString());
                textName.setText(textName.getText().toString());
                if((checkNam.isChecked()) && (!checkNu.isChecked())){
                    textGender="Nam";
                }else{
                    if(!checkNam.isChecked() && checkNu.isChecked()){
                        textGender="Nữ";
                    }else{
                        textGender="Không rõ";
                    }
                }
                textMajor.setText(textMajor.getText().toString());
                textSalary.setText(textSalary.getText().toString());

                databaseHelper.insertTeacher(textCode.getText().toString(),textName.getText().toString(),textGender,textMajor.getText().toString(),textSalary.getText().toString());
                teacherList.clear();
                teacherList.addAll(databaseHelper.getAllTeacher());
                setCustomAdapter();
                dialog.dismiss();
                Toast.makeText(GVActivity.this, "ĐÃ THÊM GIÁO VIÊN", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(GVActivity.this, "HỦY", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }


    public void setCustomAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_teacher,teacherList);
            lvTeacher.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
            lvTeacher.setSelection(customAdapter.getCount()-1);
        }
    }

    public void setCustomAdapterNotChange(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_teacher,teacherList);
            lvTeacher.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
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
