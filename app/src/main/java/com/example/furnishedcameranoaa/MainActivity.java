package com.example.furnishedcameranoaa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQ_CODE = 11;
    private final int GALLERY_REQUEST_CODE = 111;

    Button btnPreview;
    ImageView captureImage, showImage, customImage;
    private String imgPath;
    private Object ContentResolver;
    Uri imageUri, imagePath;
    AlertDialog.Builder alertadd;
    Bitmap phto;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        captureImage = (ImageView) findViewById(R.id.camera_Image);
        showImage = (ImageView) findViewById(R.id.show_Image);
        btnPreview = (Button) findViewById(R.id.Btn_Preview);


//      someActivityResultLauncher = ((AppCompatActivity) this).registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() != Activity.RESULT_OK) {
//
//                            Toast.makeText(MainActivity.this, "Some error occured while capturing the image", Toast.LENGTH_SHORT).show();
//                            // There are no request codes
//
//
//                        }
//                    }
//                });


        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent,GALLERY_REQUEST_CODE);

                //  Show Alert Dialog
                setAlertDialog();

            }
        });
    }

    public void cameraOnClick(View view) {

        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);



        Uri imagePath = createImage();

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imagePath);
        startActivityForResult(cameraIntent, CAMERA_REQ_CODE);


      //  someActivityResultLauncher.launch(cameraIntent);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            if (requestCode == CAMERA_REQ_CODE){

                Toast.makeText(this, "Image captured Successfully", Toast.LENGTH_SHORT).show();
              //  showImage.setImageURI(imageUri);
                showImage.setImageURI(imageUri);

            }

            if (requestCode == GALLERY_REQUEST_CODE){
                // For gallery
              //  Bitmap photo = (Bitmap) data.getExtras().get("data");

                showImage.setImageURI(imageUri);
//                showImage.setImageURI(data.getData());
            }
        }
    }

    public Uri createImage(){
        Uri uri = null;
        ContentResolver = getContentResolver();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }else{
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        String imgName = String.valueOf(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "NOAA/" + "/" );
        Uri finalUri = getContentResolver().insert(uri, contentValues);
        imageUri = finalUri;
        return  finalUri;
    }


    public void setAlertDialog(){


        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        ((ImageView) dialog.findViewById(R.id.customImage)).setImageURI(imageUri);


        dialog.show();


    }
}