package com.example.teachermanagerfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.teachermanagerfinal.model.Teacher;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import static android.os.Build.ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teacher_manager.db";
    public static final String TABLE_NAME = "teacher_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "CODE";
    public static final String COL3 = "NAME";
    public static final String COL4 = "GENDER";
    public static final String COL5 = "MAJOR_CODE";
    public static final String COL6 = "SALARY";
    public static int VERSION = 2;

    private Context context;
    private String SQLQuery ="CREATE TABLE "+TABLE_NAME+" ("+
            COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL2 +" TEXT,"+
            COL3 +" TEXT, "+
            COL4 +" TEXT, "+
            COL5 +" TEXT NOT NULL CONSTRAINT MAJOR REFERENCES major_table(ID) ON DELETE CASCADE, "+
            COL6 +" TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,CODE TEXT, NAME TEXT, GENDER TEXT, MAJOR TEXT NOT NULL CONSTRAINT MAJOR REFERENCES major_table(ID) ON DELETE CASCADE, SALARY TEXT)");
        sqLiteDatabase.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addTeacher(Teacher teacher){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, teacher.getmCode());
        values.put(COL3, teacher.getmName());
        values.put(COL4, teacher.getmGender());
        values.put(COL5, teacher.getmMajor());
        values.put(COL6, teacher.getmSalary());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public boolean insertTeacher(String code, String name, String gender, String major, String salary) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, code);
        contentValues.put(COL3, name);
        contentValues.put(COL4, gender);
        contentValues.put(COL5, major);
        contentValues.put(COL6, salary);

        long success = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(success == -1) {
            return false;
        }else{
            return true;
        }

        //db.insert(TABLE_NAME, null, contentValues);
        //db.close();
    }

    public ArrayList<String> getAllStringTeacher(){
        ArrayList<String> searchList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                String gvname = new String();
                gvname=cursor.getString(2);
                searchList.add(gvname);

            }while (cursor.moveToNext());
        }
        return searchList;
    }

    public  List<Teacher> searchTeacher(String query){
        List<Teacher> listTeacher = new ArrayList<>();

        //String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From teacher_table Where name LIKE '%"+query+"%'", null);
        System.out.println("Select * From teacher_table Where name LIKE '"+query+"'");
        if(cursor.moveToFirst()){
                do{
                    Teacher teacher = new Teacher();
                    teacher.setmID(cursor.getInt(0));
                    teacher.setmCode(cursor.getString(1));
                    teacher.setmName(cursor.getString(2));
                    teacher.setmGender(cursor.getString(3));
                    teacher.setmMajor(cursor.getString(4));
                    teacher.setmSalary(cursor.getString(5));
                    listTeacher.add(teacher);
                }while (cursor.moveToNext());

        }
        return listTeacher;
    }

    public List<Teacher> getAllTeacher(){
        List<Teacher> listTeacher = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher();
                teacher.setmID(cursor.getInt(0));
                teacher.setmCode(cursor.getString(1));
                teacher.setmName(cursor.getString(2));
                teacher.setmGender(cursor.getString(3));
                teacher.setmMajor(cursor.getString(4));
                teacher.setmSalary(cursor.getString(5));
                listTeacher.add(teacher);

            }while (cursor.moveToNext());
        }
        return listTeacher;
    }

    public boolean updateTeacher(Teacher teacher){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL2, teacher.getmCode());
        contentValues2.put(COL3, teacher.getmName());
        contentValues2.put(COL4, teacher.getmGender());
        contentValues2.put(COL5, teacher.getmMajor());
        contentValues2.put(COL6, teacher.getmSalary());

        //return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(teacher.getmID())});
        long success2 = db.update(TABLE_NAME, contentValues2, "ID ="+teacher.getmID(),null);
        db.close();
        if(success2 == -1) {
            return false;
        }else{
            return true;
        }
    }

    public int deteleTeacher(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }

    public void deleteMajorTeacher(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "MAJOR_CODE = ?", new String[]{query});
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return cursor;
    }
}
