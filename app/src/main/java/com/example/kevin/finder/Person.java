package com.example.kevin.finder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

/**
 * Created by Kevin on 6/10/2015.
 */
public class Person implements Parcelable{

    @com.google.gson.annotations.SerializedName("id")
    private String Id;

    private String username;
    private String password;
    private String name;
    private int age;
    private String phonenumber;
    private String emailaddress;

    private String interests;

    public Person(){
        this.interests = "";
    }

    public Person(Parcel in){
        readFromParcel(in);
    }

    public String getId(){return this.Id;}
    public void setId(String Id){this.Id = Id; }

    public String getUsername(){return this.username; }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){return this.password; }
    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return this.age;
    }
    public void setAge(int age){
        this.age = age;
    }

    public String getPhoneNumber(){
        return this.phonenumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phonenumber = phoneNumber;
    }

    public String getEmailAddress(){
        return this.emailaddress;
    }
    public void setEmailAddress(String emailAddress){
        this.emailaddress = emailAddress;
    }

    public String getInterests(){
        return interests;
    }
    public void addInterest(String newInterest){
        interests += newInterest + "\n";
    }
    public void deleteInterest(String interest){
        String fullDelete = interest+"\n";
        interests.replace(fullDelete, "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.phonenumber);
        dest.writeString(this.emailaddress);
        dest.writeString(this.interests);
    }

    public void readFromParcel(Parcel in){
        this.Id = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.age = in.readInt();
        this.phonenumber = in.readString();
        this.emailaddress = in.readString();
        this.interests = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}