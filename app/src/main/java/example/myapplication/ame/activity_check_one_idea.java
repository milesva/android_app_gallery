package example.myapplication.ame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class activity_check_one_idea extends MainActivity {

    private byte[] message;
    private Bitmap pic;
    private ImageView image;
    private SeekBar seekBar_Contrast, seekBar_Brightness,seekBar_Saturation,seekBar_r,seekBar_g,seekBar_b;
    private Button but_1a, but_2a,but_1b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_one_idea);
        image = (ImageView) findViewById(R.id.imageView);
        getmessage(image);//получаем картинку и выводим в image
        seekBar_Brightness = (SeekBar) findViewById(R.id.seekBar2);
        seekBar_Contrast = (SeekBar) findViewById(R.id.seekBar);
        seekBar_Saturation=(SeekBar)findViewById(R.id.seekBar_saturation);
        seekBar_r=(SeekBar)findViewById(R.id.seekBar_r);
        seekBar_g=(SeekBar)findViewById(R.id.seekBar_g);
        seekBar_b=(SeekBar)findViewById(R.id.seekBar_b);

        seekBar_Brightness.setOnSeekBarChangeListener(seekBarChangeListener2);
        seekBar_Contrast.setOnSeekBarChangeListener(seekBarChangeListener2);
        seekBar_Saturation.setOnSeekBarChangeListener(seekBarChangeListener3);
        seekBar_r.setOnSeekBarChangeListener(seekBarChangeListener4);
        seekBar_g.setOnSeekBarChangeListener(seekBarChangeListener5);
        seekBar_b.setOnSeekBarChangeListener(seekBarChangeListener6);


        but_1a=(Button)findViewById(R.id.but_whiteblack);
        but_2a=(Button)findViewById(R.id.but_whiteblack2);
        but_1b=(Button)findViewById(R.id.but_invert);

    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener2 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapContrastBrightness(seekBar_Contrast.getProgress(), seekBar_Brightness.getProgress()-seekBar_Brightness.getMax()/2);
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    /*private SeekBar.OnSeekBarChangeListener seekBarChangeListener1 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapContrastBrightness(1,seekBar_Contrast.getProgress());
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };*/
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener3 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapRGB(3,seekBar_Saturation.getProgress());
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener4 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapRGB(4,seekBar_r.getProgress());
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener5 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapRGB(5,seekBar_g.getProgress());
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener6 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Bitmap b=changeBitmapRGB(6,seekBar_b.getProgress());
            image.setImageBitmap(b);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    private ColorMatrix MatrixColor(int flag, float value)
    {
        ColorMatrix cm = new ColorMatrix();
        if(flag==1)
        {
            value/=5;
            cm=new ColorMatrix(new float[]
                    {
                            value, 0, 0, 0, 0,
                            0, value, 0, 0, 0,
                            0, 0, value, 0, 0,
                            0, 0, 0, 1, 0
                    });
        }
        if(flag==2)
        {
            value /= 500;
            cm=new ColorMatrix(new float[]
                    {
                            0, 0, 0, 0, value,
                            0, 0, 0, 0, value,
                            0, 0, 0, 0, value,
                            0, 0, 0, 1, 0
                    });
        }
        if(flag==3)
        {
            value/=500;
            cm.setSaturation(value);
        }
        if(flag==4)
        {
            value/=1000;
            cm=new ColorMatrix(new float[]
                    {

                                    value, value, value, value, 1,
                                    0, 1, 0, 0, 1,
                                    0, 0, 1, 0, 1,
                                    0, 0, 0, 1, 0
                    });
        }
        if(flag==5)
        {
            value/=1000;
            cm=new ColorMatrix(new float[]
                    {

                            1, 0, 0, 0, 1,
                            value, value, value, value, 1,
                            0, 0, 1, 0, 1,
                            0, 0, 0, 1, 0
                    });
        }
        if(flag==6)
        {
            value/=1000;
            cm=new ColorMatrix(new float[]
                    {

                            1, 0, 0, 0, 1,
                            0, 1, 0, 0, 1,
                            value, value, value, value, 1,
                            0, 0, 0, 1, 0
                    });
        }
        if(flag==10)
        {
            cm=new ColorMatrix(new float[]
                    {
                            0.3f, 0.59f, 0.11f, 0, 0,
                            0.3f, 0.59f, 0.11f, 0, 0,
                            0.3f, 0.59f, 0.11f, 0, 0,
                            0, 0, 0, 1, 0
                    });
        }
        if(flag==11)
        {
            cm=new ColorMatrix(new float[]
                    {
                            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
                            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
                            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
                            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                    });
        }
        if(flag==12)
        {
            cm=new ColorMatrix(new float[]
                    {
                            -1, 0, 0, 0, 255,
                            0, -1, 0, 0, 255,
                            0, 0, -1, 0, 255,
                            0, 0, 0, 1, 0
                    });
        }
        return cm;

    }
    private Bitmap changeBitmapRGB(int flag,float value)
    {
        Bitmap bmp=((BitmapDrawable)image.getDrawable()).getBitmap();//pic;
        ColorMatrix cm=MatrixColor(flag,value);
        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);
        return ret;
    }
    private Bitmap changeBitmapContrastBrightness(float contrast, float brightness)
    {
        Bitmap bmp=pic;
        contrast/=500;
        brightness/=5;
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });


        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);
        return ret;
    }

    public void onClick(View v) {
        Bitmap b;
        switch (v.getId()) {
            case R.id.but_whiteblack:
                b=changeBitmapRGB(10,0);
                image.setImageBitmap(b);
                break;
            case R.id.but_whiteblack2:
                b=changeBitmapRGB(11,0);
                image.setImageBitmap(b);
                break;
            case R.id.but_invert:
                b=changeBitmapRGB(12,0);
                image.setImageBitmap(b);
                break;
        }
    }
    private void getmessage(ImageView image) {
        Bundle extras = getIntent().getExtras();
        message = extras.getByteArray("name");
        arr_byte_in_image(message, image);
    }

    //преобразовываем массив байтов byteArray в растровое изображение и сохраняем в поле image
    private void arr_byte_in_image(byte[] byteArray, ImageView image) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bmp);
        pic = bmp;
    }



}












