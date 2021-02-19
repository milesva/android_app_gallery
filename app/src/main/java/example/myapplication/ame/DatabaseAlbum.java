package example.myapplication.ame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAlbum extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String NAME_DB = "ALBUMS";
    public static final String TABLE_ALBUM = "STRUCT_ALBUM";
    //public static final String TABLE_PHOTO = "DESCRIPTION";
    //public static final String TABLE_ALB = "DESCRIPTION";

    public static final String KEY_ID = "_id";
    public static final String KEY_DESCRIPT = "DESCRIPTION";
    public static final String KEY_PHOTO = "IMAGE";
    public static final String KEY_ALBUM = "NUMB_ALBUM";


    public  DatabaseAlbum(Context context){
        super(context,NAME_DB,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_ALBUM
                + "("+KEY_ID+" INTEGER PRIMARY KEY," +KEY_ALBUM+ "INTEGER"+KEY_PHOTO+" BLOB,"+
                KEY_DESCRIPT+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALBUM);
        onCreate(db);
    }


}
