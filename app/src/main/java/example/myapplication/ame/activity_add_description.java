package example.myapplication.ame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class activity_add_description extends MainActivity /*AppCompatActivity*/ {

    private EditText edit_descript;//поле для описания снимка
    private Button but_finish,but_back;//кнопка НАЗАД и ГОТОВО
    private ImageView image;// = (ImageView) findViewById(R.id.imageView); поле для картинки, которую выбрал пользователь в предыдущем активити
    private DatabaseAlbum db_album;

    private byte[] message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        //ПОЛЕ ДЛЯ ВВОДА ОПИСАНИЯ ДЛЯ СНИМКА
        edit_descript=(EditText)findViewById(R.id.edit_descript);
        //КНОПКА "ГОТОВО"
        but_finish=(Button)findViewById(R.id.but_finish);
        //КНОПКА "НАЗАД"
        but_back=(Button)findViewById(R.id.but_back);
        //ПОЛЕ ДЛЯ ВЫБРАННОГО СНИМКА
        image = (ImageView) findViewById(R.id.imageView);
        db_album=new DatabaseAlbum(this);
        getmessage(image);//получаем картинку и выводим в image
    }
    public void onClick(View v){
        SQLiteDatabase database=db_album.getWritableDatabase();
        ContentValues contentValue=new ContentValues();
        String descript=null;

        switch(v.getId()){
            //КНОПКА ГОТОВО
            case R.id.but_finish:
                descript=edit_descript.getText().toString();
                contentValue.put(DatabaseAlbum.KEY_DESCRIPT,descript);
                contentValue.put(DatabaseAlbum.KEY_PHOTO,message);
                contentValue.put(DatabaseAlbum.KEY_ALBUM,1);
                database.insert(DatabaseAlbum.TABLE_ALBUM,null,contentValue);
                Toast.makeText(activity_add_description.this,"Добавлено",Toast.LENGTH_SHORT).show();
                Intent intnt=new Intent(this, activity_album.class);
                startActivity(intnt);
                break;
             //КНОПКА НАЗАД
            case R.id.but_back:
                //Toast.makeText(activity_add_description.this,descript,Toast.LENGTH_SHORT).show();
                database.delete(DatabaseAlbum.TABLE_ALBUM,null,null);
                Toast.makeText(activity_add_description.this,descript,Toast.LENGTH_SHORT).show();
                break;


        }
    }
    //получаем картинку и выводим в image
    private void getmessage(ImageView image)
    {
        Bundle extras = getIntent().getExtras();
        /*byte[] byteArray*/ message = extras.getByteArray("name");
        arr_byte_in_image(/*byteArray*/ message,image);
    }
    //преобразовываем массив байтов byteArray в растровое изображение и сохраняем в поле image
    private void arr_byte_in_image(byte[] byteArray, ImageView image)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bmp);
    }
}

