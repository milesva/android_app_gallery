package example.myapplication.ame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Checkimage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkimage);
        /*int b=getIntent().getIntExtra("name", 0);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(b);*/

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("name");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageBitmap(bmp);

    }

}
