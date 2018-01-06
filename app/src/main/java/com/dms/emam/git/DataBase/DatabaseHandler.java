package com.dms.emam.git.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.dms.emam.git.Repo.Repositry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Emam Mahmoud on 12/09/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private   ArrayList<Repositry>repositries=new ArrayList<>();
    Context context;
    public DatabaseHandler(Context context) {
        super(context, Constant.databaseName, null, Constant.databaseVersion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWishesTable="CREATE TABLE "+Constant.tableName+
                "( "+ Constant.keyId +" INTEGER PRIMARY KEY , "
                +Constant.repositryName+" TEXT ,"
                +Constant.repositryDescription+" TEXT ,"
                + Constant.usernameOfOwner+" TEXT , "
                + Constant.repositoryHtml_url+" TEXT , "
                + Constant.fork+" flag INTEGER DEFAULT 0 , "
                +Constant.ownerHtml_url +"  TEXT );";

        Log.i("lolo",createWishesTable);
        Toast.makeText(context,createWishesTable,Toast.LENGTH_LONG).show();
        db.execSQL(createWishesTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Constant.tableName);
        onCreate(db);

    }

public void cleanDatabase()
{

    SQLiteDatabase db=this.getWritableDatabase();
    db.delete(Constant.tableName, null, null);
}

  public void addrRepositry(Repositry repositry)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constant.repositryName,repositry.getRepoName());
        values.put(Constant.repositryDescription, repositry.getDescription());
        values.put(Constant.usernameOfOwner, repositry.getUsernameOfOwner());
        values.put(Constant.repositoryHtml_url, repositry.getRepositoryHtml_url());
        values.put(Constant.ownerHtml_url, repositry.getOwnerHtml_url());
        values.put(Constant.fork, repositry.isFork());


        db.insert(Constant.tableName, null, values);
//        Log.v("Save to database ", " Done");
        db.close();

    }
    public ArrayList<Repositry> getRepositries()
    {
        repositries= new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.query(Constant.tableName,new String[]{ Constant.keyId,Constant.repositryName
        ,Constant.repositryDescription,Constant.usernameOfOwner,Constant.repositoryHtml_url,Constant.ownerHtml_url,Constant.fork}
                ,null,null,null,null,null);


        if (cursor.moveToFirst())
        {

            do {

                Repositry repositry=new Repositry();
                repositry.setRepoName(cursor.getString(cursor.getColumnIndex(Constant.repositryName)));
                repositry.setDescription(cursor.getString(cursor.getColumnIndex(Constant.repositryDescription)));
                repositry.setUsernameOfOwner(cursor.getString(cursor.getColumnIndex(Constant.usernameOfOwner)));
                repositry.setRepositoryHtml_url(cursor.getString(cursor.getColumnIndex(Constant.repositoryHtml_url)));
                repositry.setOwnerHtml_url(cursor.getString(cursor.getColumnIndex(Constant.ownerHtml_url)));
                repositry.setFork(cursor.getInt(cursor.getColumnIndex(Constant.fork))==1);

                repositries.add(repositry);

            }while (cursor.moveToNext());

        }


        return repositries;
    }
}
