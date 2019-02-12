package com.example.illuminate_me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class UploadTakeImage extends AppCompatActivity {
    private float x1,x2,y1,y2;
    private ImageView image;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private SwipeDetector sd;
    private Bitmap takenPicture ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to make it cover the entire screen,
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();

        setContentView(R.layout.activity_upload_take_image);
        image= findViewById(R.id.imageToUpload);
        // repeatDescription =(Button) findViewById(R.id.repeatDescription);
        // TO OPEN THE GALLARY
        if(MainActivity.chosenButton==MainActivity.UPLOAD_PIC)
            uploadPicture();
        else
            takePicture();


        //Lulu :

        sd = new SwipeDetector(this, new SwipeDetector.OnSwipeListener() {

            @Override
            public void onSwipeUp(float distance, float velocity) {
                savePicture(takenPicture);
                Toast.makeText(getApplicationContext(), "SwipeUP!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight(float distance, float velocity) {
                // the Slide-in share menu
                Toast.makeText(getApplicationContext(), "SwipeRight!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSwipeLeft(float distance, float velocity) {
                Toast.makeText(getApplicationContext(), "SwipeLeft!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (UploadTakeImage.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSwipeDown(float distance, float velocity) {
                //go HOME page
                Toast.makeText(getApplicationContext(), "SwipeDown!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (UploadTakeImage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //to allow user select image from the gallary
    public void uploadPicture() {

        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent,MainActivity.chosenButton);

    }

    public void takePicture() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,MainActivity.chosenButton);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //to upload picture
        if( requestCode==MainActivity.UPLOAD_PIC&&resultCode==RESULT_OK &&data!=null){
            //how to pass this to class user
            //address of the selected image
            Uri selectedImage = data.getData();
            //to display the selected image in the image view (interface)
            image.setImageURI(selectedImage);
        }
        // allow user to take a picture
        if( requestCode==MainActivity.TAKE_PIC&&resultCode==RESULT_OK &&data!=null){
            takenPicture = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            image.setImageBitmap(takenPicture);
            savePicture(takenPicture);


        }


    }

    //Lulu (SAVE PICTURE)
    public void savePicture (Bitmap takenPicture) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        takenPicture.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        try {
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            //5
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                y1=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=event.getX();
                y2=event.getY();
                if(x1<x2){
                    // swipe left
                    // go to home screen
                    Intent intent = new Intent (UploadTakeImage.this, MainActivity.class);
                    startActivity(intent);
                }


                if(x1>x2){
                    //swipe right
                    // the Slide-in share menu
                }

                break;

        }//end switch

        return super.onTouchEvent(event);
    }


/*

        // to create a unique picture name.
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  //prefix
                ".jpg",         //suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

*/

}
