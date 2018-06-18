package com.example.snd_v1;


/*
Title: User
Author: Jenny Shen
Date: April 6, 2018
Description: User object, parent class of babysitter and parent
stores attributes like address, password, birthday, email, name,bio, age, and gender
 */

public class User {

    // user attribute
      private String address, password, birthday, email, name,bio, age;
      private int gend;

    //fully parameterized constructor (for creating a new user)
    public User(String a, String e, String n, String p, String b, int g,  String bi, String ag){
        address=a;
        password =p;
        birthday =b;
        email=e;
        name=n;
        gend=g;
        bio= bi;
        age=ag;

    }

    //no parameterized constructor (retrieval from Firebase)
    public User(){

    }

    //assessor methods
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
    public int getGender (){
        return gend;
    }
    public String getName (){
          return name;
      }

    //mutator methods
    public void setAddress(String a){
          address = a;
      }
    public void setName(String a){
          name = a;
      }
    public void setBio(String a) {
        bio = a;
    }
    public void setAge(String a) {
        age = a;
    }
    public void setPassword(String a){
        password = a;
    }
    public void setBirthday(String a){
        birthday = a;
    }
    public void setEmail(String a) {
        email = a;
    }
    public void setGender(int a) {
        gend = a;
    }

}
