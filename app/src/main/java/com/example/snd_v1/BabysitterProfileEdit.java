package com.example.snd_v1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;
//import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BabysitterProfileEdit extends AppCompatActivity {
    Parent parent = new Parent();
    EditText name,addr,bio, otherEdit;
    int h=0;
    Bitmap image;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users/Babysitter");
    CheckBox firstAidCheck, babysittingCertificateCheck, cprCheck, policeCheck, otherCheck;
    String list[] = {"no","no","no","no","no"};

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_profile_edit);
        final int id = (getIntent().getExtras().getInt("id"));
        h=id;

        name = findViewById(R.id.editText);
        addr = findViewById(R.id.editText2);
        bio = findViewById(R.id.editText4);
        profileImageView = findViewById(R.id.imageView3);

        firstAidCheck = findViewById(R.id.fistAidCheck);
        babysittingCertificateCheck = findViewById(R.id.babysittingCertificateCheck);
        cprCheck = findViewById(R.id.cprCheck);
        policeCheck = findViewById(R.id.policeCheck);
        otherCheck = findViewById(R.id.otherCheck);
        otherEdit = findViewById(R.id.otherEdit);

//        try {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference bb = storageReference.child(id+"b.jpg");
//            Glide.with(this.getApplicationContext()).load(bb).into(profileImageView);
//        }
//        catch(Exception e){
//
//        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                parent = dataSnapshot.child(id+"").getValue(Parent.class);
//                setText();

                String name = dataSnapshot.child(id+"").child("name").getValue(String.class);
//                String age = dataSnapshot.child(id+"").child("age").getValue(String.class);
                String addr =dataSnapshot.child(id+"").child("address").getValue(String.class);
                String bio =dataSnapshot.child(id+"").child("bio").getValue(String.class);
 //               String children =dataSnapshot.child(id+"").child("child").getValue(String.class);
                setText(name, addr, bio);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void setText(String n, String a, String b){
        name.setText(n);
        //age.setText("34 yrs old");
        addr.setText(a);
        bio.setText(b);
        //children.setText("Child\n\n"+parent.getChild());
        //Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();
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

                    image = BitmapFactory.decodeStream(inputStream); //Get a bitmap from the stream

                    profileImageView.setImageBitmap(image); //Show the image to the user

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show(); //Shows image to user saying image is unavailable
                }

            }
        }
    }

    public void onCheckboxClicked(View view) {
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
                if(checked) {
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

    public void Search(View view) {
        Intent intent = new Intent(this, BabysitterSearch.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, BabysitterHome.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void Save(View view) {
        Intent intent = new Intent(this, BabysitterProfile.class);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        String qualifications = "";
        for(int i = 0;i < list.length; i++) {
            if(!(list[i].toString().equals("no"))) {
                qualifications = qualifications + list[i] + ", ";
            }
        }
//        try{
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference bb = storageReference.child(id+"b.jpg");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();
//            UploadTask uploadTask = bb.putBytes(data);
//        }
//        catch(Exception e){
//
//        }

        Toast.makeText(this,qualifications, Toast.LENGTH_LONG).show();

        myRef.child(h+"").child("name").setValue(name.getText().toString());
        myRef.child(h+"").child("address").setValue(addr.getText().toString());
        myRef.child(h+"").child("bio").setValue(bio.getText().toString());

        startActivity(intent);
    }
}
