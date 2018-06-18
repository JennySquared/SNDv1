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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ParentProfileEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Parent parent = new Parent();
    EditText name,addr,bio;
    public String childGender, childAgeRange;
    Bitmap image;
    int h=0;
    DatePicker age;
    //age, addr, bio;
    //children;
    String postalPattern = "[a-zA-Z]+[0-9]+[a-zA-Z]+[0-9]+[a-zA-Z]+[0-9]";
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView profileImageView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users/Parent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_edit);

        final int id = (getIntent().getExtras().getInt("id"));
        h=id;

        name = findViewById(R.id.name);
        addr = findViewById(R.id.address);
        bio = findViewById(R.id.bio);

        profileImageView = findViewById(R.id.profileImageView);
        if(id==1) {
            profileImageView.setImageResource(R.drawable.onep);
        }
        if(id==2){
            profileImageView.setImageResource(R.drawable.twop);
        }
        if(id==3){
            profileImageView.setImageResource(R.drawable.threep);
        }
        if(id==4){
            profileImageView.setImageResource(R.drawable.fourb);
        }
        if(id==5){
            profileImageView.setImageResource(R.drawable.fiveb);
        }

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

//        try {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference pp = storageReference.child(id+"p.jpg");
//            Glide.with(this.getApplicationContext()).load(pp).into(profileImageView);
//        }
//        catch(Exception e){
//            Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
//        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parent = dataSnapshot.child(id+"").getValue(Parent.class);
                setText();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

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

    public boolean postalCheck(String postalTest){
        if(!postalTest.matches(postalPattern)){
            addr.setError("Invalid entry");
            return false;
        }
        return true;
    }

    public void setChildGender(String g){
        childGender = g;
    }

    public void setChildAge(String ageRange){
        childAgeRange = ageRange;
    }

    public void setText(){
        name.setText(parent.getName());
        //age.setText("34 yrs old");
        addr.setText(parent.getAddress());
        bio.setText("Bio\n\n"+parent.getBio());
        //children.setText("Child\n\n"+parent.getChild());
        Toast.makeText(getApplicationContext(),parent.getAge(),Toast.LENGTH_SHORT).show();
    }
    public void Search(View view) {
        Intent intent = new Intent(this, ParentSearch.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void JobPost(View view) {
        Intent intent = new Intent(this, ParentPostJob.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, ParentHome.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);
        int id = (getIntent().getExtras().getInt("id"));
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void Save(View view) {
        Intent intent = new Intent(this, ParentProfile.class);
        String numBB = (getIntent().getExtras().getString("n"));
        intent.putExtra("n",numBB);

        parent.setName(name.getText().toString());
        if(postalCheck(addr.getText().toString())){
            parent.setAddress(addr.getText().toString());
        }
//        int b = bio.getText().toString().indexOf("Bio\n");
        parent.setBio(bio.getText().toString());
        parent.setChild(childAgeRange+" yr old "+childGender);
        myRef.child(h+"").setValue(parent);
//        try{
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//            StorageReference pp = storageReference.child(Integer.parseInt(numBB)+"p.jpg");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();
//            UploadTask uploadTask = pp.putBytes(data);
//        }
//        catch(Exception e){
//
//        }

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
