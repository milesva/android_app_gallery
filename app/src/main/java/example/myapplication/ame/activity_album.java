package example.myapplication.ame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class activity_album extends AppCompatActivity {
    public DatabaseAlbum db_album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*setContentView(R.layout.activity_album);
        db_album = new DatabaseAlbum(this);
        SQLiteDatabase database = db_album.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        Cursor cursor = database.query(DatabaseAlbum.TABLE_ALBUM, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseAlbum.KEY_ID);
            int photoIndex = cursor.getColumnIndex(DatabaseAlbum.KEY_PHOTO);
            int descriptIndex = cursor.getColumnIndex(DatabaseAlbum.KEY_DESCRIPT);
            int albIndex = cursor.getColumnIndex(DatabaseAlbum.KEY_ALBUM);
            do {
                Toast.makeText(activity_album.this,cursor.getInt(idIndex),Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();*/
    }
}

