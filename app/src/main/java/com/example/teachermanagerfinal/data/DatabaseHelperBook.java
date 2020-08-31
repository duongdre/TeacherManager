package com.example.teachermanagerfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.teachermanagerfinal.model.Book;
import com.example.teachermanagerfinal.model.Major;
import com.example.teachermanagerfinal.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class DatabaseHelperBook extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "teacher_manager.db";
    public static final String TABLE_NAME = "book_table";
    public static final String COL1 = "ID_BOOK";
    public static final String COL2 = "CODE_BOOK";
    public static final String COL3 = "NAME_BOOK";
    public static final String COL4 = "MAJOR_CODE";
    public static int VERSION = 2;

    public Context context;
    private String SQLQuery ="CREATE TABLE "+TABLE_NAME+" ("+
            COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL2 +" TEXT,"+
            COL3 +" TEXT,"+
            COL4 +" TEXT)";

    public DatabaseHelperBook(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , CODE_MAJOR TEXT,NAME_MAJOR TEXT)")
        sqLiteDatabase.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void hello(){
        Toast.makeText(context, "Hello the Fucking Wolrd", Toast.LENGTH_SHORT).show();
    }

    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, book.getmCode());
        contentValues.put(COL3, book.getmName());
        contentValues.put(COL4, book.getmMajor());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public boolean insertBook(String code, String name, String major) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, code);
        contentValues.put(COL3, name);
        contentValues.put(COL4, major);

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


    public List<String> getAllBookName(){
        List<String> listBookName = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Book book = new Book();
                book.setmID(cursor.getInt(0));
                book.setmCode(cursor.getString(1));
                book.setmName(cursor.getString(2));
                book.setmMajor(cursor.getString(3));


                listBookName.add(book.getmName()+"");
            }while (cursor.moveToNext());
        }
        return listBookName;
    }

    public List<Book> getAllBook(){
        List<Book> listBook = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Book book = new Book();
                book.setmID(cursor.getInt(0));
                book.setmCode(cursor.getString(1));
                book.setmName(cursor.getString(2));
                book.setmMajor(cursor.getString(3));

                listBook.add(book);

            }while (cursor.moveToNext());
        }
        return listBook;
    }

    public int deteleBook(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID_BOOK = ?",new String[]{id});
    }

    public boolean updateBook(Book book){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL2, book.getmCode());
        contentValues2.put(COL3, book.getmName());
        contentValues2.put(COL4, book.getmMajor());

        //return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(teacher.getmID())});
        long success2 = db.update(TABLE_NAME, contentValues2, "ID_MAJOR ="+book.getmID(),null);
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

    public  List<Book> searchBookWithMajor(String query){
        List<Book> listBook = new ArrayList<>();

        //String selectQuery = "SELECT * FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From book_table Where MAJOR_CODE LIKE '%"+query+"%'", null);
        System.out.println("Select * From book Where major_code LIKE '"+query+"'");
        if(cursor.moveToFirst()){
            do{
                Book book = new Book();
                book.setmID(cursor.getInt(0));
                book.setmCode(cursor.getString(1));
                book.setmName(cursor.getString(2));

                listBook.add(book);
            }while (cursor.moveToNext());

        }
        return listBook;
    }
}