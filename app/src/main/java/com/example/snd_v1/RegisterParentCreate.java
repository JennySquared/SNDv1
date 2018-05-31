/*
Name: Jenny /hua
Date:
Title: Parent Profile Registration
Description:
 */


package com.example.snd_v1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.snd_v1.MainActivity;
import com.example.snd_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterParentCreate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String bio, childGender, childAgeRange;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView profileImageView;
    public EditText bioText;
    public Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_create);

        profileImageView = findViewById(R.id.profileImageView); //get reference to image view that holds image that the user will see

        bioText = findViewById(R.id.bioText);

        Spinner childGenderDrop = findViewById(R.id.genderDrop);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.genderDrop, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childGenderDrop.setAdapter(adapter2);
        childGenderDrop.setPrompt("Select");
        childGenderDrop.setOnItemSelectedListener(this);
        setChildGender(childGenderDrop.getSelectedItem().toString());

        Spinner childAgeDrop = findViewById(R.id.childAgeDrop);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.childAgeDrop, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childAgeDrop.setAdapter(adapter3);
        childAgeDrop.setOnItemSelectedListener(this);
        setChildAge(childAgeDrop.getSelectedItem().toString());
    }

    /**
     * This method will be invoked when the user clicks on the upload button
     * @param v
     */
    public void onImageGalleryClicked(View v){
        //Invoke the image gallery using an implicit intent
        Intent photoPickerIntent= new Intent(Intent.ACTION_PICK);

        //Where to find data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        //Uri representation
        Uri data = Uri.parse(pictureDirectoryPath);

        //Set data and type
        photoPickerIntent.setDataAndType(data, "image/*"); //Allows to get all image types

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode== RESULT_OK){ //Everything processed successfully
            if(requestCode==IMAGE_GALLERY_REQUEST){ //We have heard back from the image gallery
                Uri imageUri = data.getData(); //Address of image
                InputStream inputStream; //Declare a stream to read the image data

                try {
                    inputStream = getContentResolver().openInputStream(imageUri); //Getting an image stream based on URI of image
                    image = BitmapFactory.decodeStream(inputStream);

                    profileImageView.setImageBitmap(image); //Show the image to the user
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setBio(String b){
        bio= b;
    }

    public void setChildGender(String g){
        childGender = g;
    }

    public void setChildAge(String ageRange){
        childAgeRange = ageRange;
    }

    public void submit(View view) {
        setBio(bioText.getText().toString());

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
        final Parent p = new Parent(RegisterAddress.address, RegisterEmail.email, RegisterName.name, RegisterPassword.password, RegisterBirthday.bday, genderNum, childAgeRange+" yr old "+childGender, bio, image, ""+RegisterBirthday.age );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference Ref = database.getReference();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int n = Integer.parseInt(dataSnapshot.child("numParents").getValue(String.class));
                Ref.child("numParents").setValue(n+1+"");
                setParent(n,p);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        startActivity(new Intent(RegisterParentCreate.this, MainActivity.class));
    }

    public void setParent(int n, Parent p){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Users/Parent");
        Ref.child(n+1+"").setValue(p);

    }

}