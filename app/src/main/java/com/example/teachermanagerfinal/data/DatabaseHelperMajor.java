package com.example.teachermanagerfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.teachermanagerfinal.model.Major;
import com.example.teachermanagerfinal.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class DatabaseHelperMajor extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teacher_manager.db";
    public static final String TABLE_NAME = "major_table";
    public static final String COL1 = "ID_MAJOR";
    public static final String COL2 = "CODE_MAJOR";
    public static final String COL3 = "NAME_MAJOR";
    public static int VERSION = 2;

    public Context context;
    private String SQLQuery ="CREATE TABLE "+TABLE_NAME+" ("+
            COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL2 +" TEXT,"+
            COL3 +" TEXT)";

    public DatabaseHelperMajor(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , CODE_MAJOR TEXT,NAME_MAJOR TEXT)")
        sqLiteDatabase.execSQL(SQLQuery);
        sqLiteDatabase.execSQL("create table " + "teacher_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,CODE TEXT, NAME TEXT, GENDER TEXT, MAJOR_CODE TEXT NOT NULL CONSTRAINT MAJOR REFERENCES major_table(ID) ON DELETE CASCADE, SALARY TEXT)");
        sqLiteDatabase.execSQL("create table " + "book_talbe" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,CODE TEXT, NAME TEXT, MAJOR_CODE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void hello(){
        Toast.makeText(context, "Hello the Fucking Wolrd", Toast.LENGTH_SHORT).show();
    }

    public void addMajor(Major major){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, major.getmCode());
        contentValues.put(COL3, major.getmName());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public boolean insertMajor(String code, String name) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, code);
        contentValues.put(COL3, name);

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


    public List<String> getAllMajorName(){
        List<String> listMajorName = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Major major = new Major();
                major.setmID(cursor.getInt(0));
                major.setmCode(cursor.getString(1));
                major.setmName(cursor.getString(2));

                listMajorName.add(major.getmName()+"");
            }while (cursor.moveToNext());
        }
        return listMajorName;
    }

    public List<Major> getAllMajor(){
        List<Major> listMajor = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Major major = new Major();
                major.setmID(cursor.getInt(0));
                major.setmCode(cursor.getString(1));
                major.setmName(cursor.getString(2));
                listMajor.add(major);

            }while (cursor.moveToNext());
        }
        return listMajor;
    }

    public int deteleMajor(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID_MAJOR = ?",new String[]{id});
    }

    public boolean updateMajor(Major major){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL2, major.getmCode());
        contentValues2.put(COL3, major.getmName());

        //return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(teacher.getmID())});
        long success2 = db.update(TABLE_NAME, contentValues2, "ID_MAJOR ="+major.getmID(),null);
        db.close();
        if(success2 == -1) {
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return cursor;
    }

    public  List<Major> searchTeacherWithMajor(String query){
        List<Major> listMajor = new ArrayList<>();

        //String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From teacher_table Where MAJOR_CODE LIKE '%"+query+"%'", null);
        System.out.println("Select * From teacher_table Where name LIKE '"+query+"'");
        if(cursor.moveToFirst()){
            do{
                Major major = new Major();
                major.setmID(cursor.getInt(0));
                major.setmCode(cursor.getString(1));
                major.setmName(cursor.getString(2));

                listMajor.add(major);
            }while (cursor.moveToNext());

        }
        return listMajor;
    }
}