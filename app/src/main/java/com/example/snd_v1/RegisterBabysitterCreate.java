package com.example.snd_v1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.datatype.Duration;

public class RegisterBabysitterCreate extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView profileImageView;
    public EditText babyBioTextBox, otherEdit;
    String babyBioText;
    CheckBox firstAidCheck, babysittingCertificateCheck, cprCheck, policeCheck, otherCheck;
    String list[] = {"no","no","no","no","no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_create);
        babyBioTextBox = findViewById(R.id.bioText);
        firstAidCheck = findViewById(R.id.fistAidCheck);
        babysittingCertificateCheck = findViewById(R.id.babysittingCertificateCheck);
        cprCheck = findViewById(R.id.cprCheck);
        policeCheck = findViewById(R.id.policeCheck);
        otherCheck = findViewById(R.id.otherCheck);
        otherEdit = findViewById(R.id.otherEdit);
        profileImageView = (ImageView) findViewById(R.id.profileImageView); //Get a reference to the imageView that holds the image that the user will see


    }

    /**
     * This method will be invoked when the user clicks on the upload button
     *
     * @param v
     */
    public void onImageGalleryClicked(View v) {
        //Invoke the image gallery with an implicit intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        //Where to find data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        //Get URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        //Set data and type
        photoPickerIntent.setDataAndType(data, "image/*"); //Allows to get all image types

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //Everything processed successfully
            if (requestCode == IMAGE_GALLERY_REQUEST) { // We have heard back from the gallery
                Uri imageUri = data.getData(); //Address of image
                InputStream inputStream; //Declare a stream to read the image data

                //Set up a try catch in case user has removed SD card or file cannot open
                try {
                    inputStream = getContentResolver().openInputStream(imageUri); //Getting an image stream based on URI of image

                    Bitmap image = BitmapFactory.decodeStream(inputStream); //Get a bitmap from the stream

                    profileImageView.setImageBitmap(image); //Show the image to the user

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show(); //Shows image to user saying image is unavailable
                }

            }
        }
    }

    public void setBabyBioText() {
        babyBioText = babyBioTextBox.getText().toString();
    }

    public void onCheckboxClicked(View view) {

        // Is the view now checked?
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()) {
            default:
                System.out.println("Hi");
            case R.id.fistAidCheck:
                if(checked)
                    list[0] = "First Aid";
                else
                    list[0] = "no";
                break;
            case R.id.babysittingCertificateCheck:
                if(checked)
                    list [1] = "Babysitting Certificate";
                else
                    list[1] = "no";
                break;
            case R.id.cprCheck:
                if(checked)
                    list[2] = "CPR";
                else
                    list[2] = "no";
                break;
            case R.id.policeCheck:
                if(checked)
                    list[3] = "Police Check";
                else
                    list[3] = "no";
                break;
            case R.id.otherCheck:
                if(checked)
                    list[4] = otherEdit.getText().toString();
                else
                    list[4] = "no";
        }
    }

    public void submit(View view) {

        String qualifications = "";
        for(int i = 0;i < list.length; i++) {
            if(!(list[i].toString().equals("no"))) {
                qualifications = qualifications + list[i] + " ";
            }
        }
        Toast.makeText(this,qualifications, Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterBabysitterCreate.this, MainActivity.class));
    }

}