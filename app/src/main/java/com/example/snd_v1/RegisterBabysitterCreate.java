/*
Name: Jenny Hua
Date: April 3, 2018
Title: Register Babysitter Create
Description: Allows babysitter to input the required information in order to complete their babysitting profile
 */

package com.example.snd_v1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.datatype.Duration;

public class RegisterBabysitterCreate extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView profileImageView;
    public static Bitmap image;
    Uri data;
    InputStream inputStream;
    public EditText babyBioTextBox, experienceEdit, otherEdit;
    public static String babyBioText, experience;
    CheckBox firstAidCheck, babysittingCertificateCheck, cprCheck, policeCheck, otherCheck;
    String list[] = {"no","no","no","no","no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_create);

        //Instantiate biography textField
        babyBioTextBox = findViewById(R.id.bioText);

        //Instantiate check boxes
        firstAidCheck = findViewById(R.id.fistAidCheck);
        babysittingCertificateCheck = findViewById(R.id.babysittingCertificateCheck);
        cprCheck = findViewById(R.id.cprCheck);
        policeCheck = findViewById(R.id.policeCheck);
        otherCheck = findViewById(R.id.otherCheck);
        otherEdit = findViewById(R.id.otherEdit);

        profileImageView = findViewById(R.id.profileImageView); //Get a reference to the imageView that holds the image that the user will see
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
        data = Uri.parse(pictureDirectoryPath);

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
                 //Declare a stream to read the image data

                //Set up a try catch in case user has removed SD card or file cannot open
                try {
                    inputStream = getContentResolver().openInputStream(imageUri); //Getting an image stream based on URI of image

                    image = BitmapFactory.decodeStream(inputStream); //Get a bitmap from the stream

                    profileImageView.setImageBitmap(image); //Show the image to the user

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show(); //Shows image to user saying image is unavailable
                }

            }
        }
    }

    public void setBabyBioText(String babyBio) {
        babyBioText = babyBio;
    } //Mutator method for biography text

    public void onCheckboxClicked(View view) { //This method uses a switch statement to add the information from the checked checkboxes into an array of the babysitter's qualifications
        // Is the view now checked?
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()) {
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
                if(checked) { //If the user checked the box labeled other and the textField below it is blank, a message on the textField will pop up prompting the user to type in the qualification name
                    if (!otherCheck(otherEdit.getText().toString())) {
                        otherEdit.setError("Please input your qualification");
                    }
                    list[4] = otherEdit.getText().toString();
                }
                else
                    list[4] = "no";
        }
    }

    public boolean otherCheck(String entry){
        if(entry.matches("")){
            return false;
        }
        else{
            return true;
        }
    }

    public void submit(View view) {

        //After the user presses the submit button, all the qualifications that the user checked will be added to an array and then each element in the array will be printed into a string called qualifications
        String qualifications = "";
        for(int i = 0;i < list.length; i++) {
            if(!(list[i].toString().equals("no"))) {
                qualifications = qualifications + list[i] + ", ";
            }
        }

        setBabyBioText(babyBioTextBox.getText().toString());

        //Toast.makeText(this,qualifications, Toast.LENGTH_LONG).show();
        int genderNum=-1;

        String gender = RegisterGender.gender;
        if(gender.compareTo("Female")==0){
            genderNum =0;
        }
        else if (gender.compareTo("Male")==0){
            genderNum =1;
        }
        else{
            genderNum =2;
        }

        final Babysitter b = new Babysitter(RegisterAddress.address, RegisterEmail.email, RegisterName.name, RegisterPassword.password, RegisterBirthday.bday, genderNum, babyBioText, qualifications, experience, RegisterBirthday.age+"" );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference Ref = database.getReference();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   int n = Integer.parseInt(dataSnapshot.child("numBabysitters").getValue(String.class));
                   Ref.child("numBabysitters").setValue(n+1+"");
                   setBabysitter(n,b);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        startActivity(new Intent(RegisterBabysitterCreate.this, MainActivity.class));
    }

    public void setBabysitter(int n, Babysitter b){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Users/Babysitter");
        Ref.child(n+1+"").setValue(b);
//        try{
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            //StorageReference bb = storageReference.child(n+"b.jpg");
////            profileImageView.setDrawingCacheEnabled(true);
////            profileImageView.buildDrawingCache();
////            Bitmap bit = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
////            ByteArrayOutputStream baos = new ByteArrayOutputStream();
////            bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////            byte[] data = baos.toByteArray();
////            UploadTask uploadTask = bb.putBytes(data);
//
//            StorageReference bb = storageReference.child(n+"b.jpg");
//            bb.putFile(data);
//        }
//        catch(Exception e){
//
//        }


    }
}