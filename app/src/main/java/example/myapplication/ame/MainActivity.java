package example.myapplication.ame;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
/*import android.support.annotation.NonNull;
import android.support.annotation.Nullable;*/
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//AppCompat
public class MainActivity extends Activity {

    private static final String TAG = "drive-AME";
    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;


    private Button button_photo;// = findViewById(R.id.but_choice_photo);выбрать снимок
    private Button button_description;//= findViewById(R.id.but_add_descript);переход к активити с описанием фотографии
    private Button button_further;//= findViewById(R.id.but_further);завершение добавление фотографии, переход к основному альбому
    private Button button_open_redactor;
    private Bitmap mBitmap;
    private ImageView imgV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start_work_google_drive();

        setContentView(R.layout.activity_main);
        button_photo = findViewById(R.id.but_choice_photo);//выбрать снимок
        button_description = findViewById(R.id.but_add_descript);//переход к активити с описанием фотографии
        button_further = findViewById(R.id.but_further);//завершение добавление фотографии, переход к основному альбому
        button_open_redactor = findViewById(R.id.but_redactor);
        imgV = findViewById(R.id.img_choice_photo);
    }

    private void start_work_google_drive() {
        Log.i(TAG, "Start sign in");
        mGoogleSignInClient = buildGoogleSignInClient(); //авторизация или регистрация пользователя
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN); //окошко для выбора аккаунта
    }

    //функция для авторизации пользователя
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    private void saveFileToDrive() {
        // Start by creating a new contents, and setting a callback.
        Log.i(TAG, "Creating new contents.");
        final Bitmap image =mBitmap;
        mDriveResourceClient
                .createContents()
                .continueWithTask(
                        new Continuation<DriveContents, Task<Void>>() {
                            @Override
                            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                return createFileIntentSender(task.getResult(), image);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Failed to create new contents.", e);
                            }
                        });
    }

    private Task<Void> createFileIntentSender(DriveContents driveContents, Bitmap image) {
        Log.i(TAG, "New contents created.");
        // Get an output stream for the contents.
        OutputStream outputStream = driveContents.getOutputStream();
        // Write the bitmap data from it.
        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, bitmapStream);
        try {
            outputStream.write(bitmapStream.toByteArray());
        } catch (IOException e) {
            Log.w(TAG, "Unable to write file contents.", e);
        }
        MetadataChangeSet metadataChangeSet =
                new MetadataChangeSet.Builder()
                        .setMimeType("image/jpeg")
                        .setTitle("Android Photo.png")
                        .setIndexableText("meow")
                        .build();
        // Set up options to configure and display the create file activity.
        CreateFileActivityOptions createFileActivityOptions = new CreateFileActivityOptions.Builder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(driveContents)
                        .build();

        return mDriveClient.newCreateFileActivityIntentSender(createFileActivityOptions)
                .continueWith(
                        new Continuation<IntentSender, Void>() {
                            @Override
                            public Void then(@NonNull Task<IntentSender> task) throws Exception {
                                startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
                                return null;
                            }
                        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            //КНОПКА ГОТОВО;
            case R.id.but_choice_photo:
                add_photoOnButton();
                break;
            case R.id.but_add_descript:
                add_descpriptionOnButton();
                break;
            case R.id.but_further:
                break;
            case R.id.but_redactor:
                sendmessage(imgV,activity_check_one_idea.class);
                break;
        }
    }

    //выбор фотографии
    private void add_photoOnButton() {
        Intent photogal = new Intent(Intent.ACTION_PICK);
        photogal.setType("image/*");
        startActivityForResult(photogal, REQUEST_CODE_CAPTURE_IMAGE); //gal
    }

    private void add_descpriptionOnButton() {
        button_photo.setText("Выбрать другой снимок");
        sendmessage(imgV,activity_add_description.class);
    }

    //работа с галереей
    @Override
    protected void onActivityResult(final int requestCode,final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode) {
            case REQUEST_CODE_CAPTURE_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgV.setImageBitmap(bitmap);
                    mBitmap=bitmap;
                    /*mBitmap = (Bitmap) data.getExtras().get("data");
                    Bundle extras = getIntent().getExtras();
                    byte[] bytArr = extras.getByteArray("data");
                    mBitmap = BitmapFactory.decodeByteArray(bytArr, 0, bytArr.length);*/
                    saveFileToDrive();
                }
                break;
            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Signed in successfully.");
                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    // Build a drive resource client.
                    mDriveResourceClient = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    // Start camera.
                    //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
                }
                break;
            case REQUEST_CODE_CREATOR:
                Log.i(TAG, "creator request code");
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Image successfully saved.");
                    //mBitmap = null;
                    // Just start the camera again for another photo.
                    //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
                }
                break;
        }
    }

    //перевод imageview  в массив баййтов
    private byte[] image_in_arr_byte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        return baos.toByteArray();
    }

    //передача массива байтов в другое активити intent
    private void sendmessage(ImageView imageView, Class <?> cls) {
        byte[] imageInByte = image_in_arr_byte(imageView);
        //Intent intent = new Intent(this, activity_add_description.class);
        Intent intent= new Intent(this, cls);
        intent.putExtra("name", imageInByte);
        startActivity(intent);
    }

}


