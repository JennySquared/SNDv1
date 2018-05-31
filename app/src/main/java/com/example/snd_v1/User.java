package com.example.snd_v1;

import android.graphics.Bitmap;

/**
 * Created by jenny on 2018-04-06.
 */

public class User {
      private String address, password, birthday, email, name,bio, age;
      Bitmap im;
      private int gend;

    public User(String a, String e, String n, String p, String b, int g, Bitmap i , String bi, String ag){
        address=a;
        password =p;
        birthday =b;
        email=e;
        name=n;
        gend=g;
        bio= bi;
        im=i;
        age=ag;

    }
    public User(){

    }

    public String getAddress (){
          return address;
      }
    public String getAge (){
        return age;
    }

    public String getPassword (){
            return password;
      }

    public String getBirthday (){
            return birthday;
      }
    public String getEmail (){
            return email;
      }
    public String getBio (){
        return bio;
    }
    public Bitmap getImage (){
        return im;
    }
    public int getGender (){
        return gend;
    }
    public String getName (){
          return name;
      }

    public void setAddress(String a){
          address = a;
      }
    public void setName(String a){
          name = a;
      }
    public void setBio(String a) {
        bio = a;
    }

}
