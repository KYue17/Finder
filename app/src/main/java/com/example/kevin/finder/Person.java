package com.example.kevin.finder;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

/**
 * Created by Kevin on 6/10/2015.
 */
public class Person {

    private String Id;
    private final String username;
    private final String password;
    private String name;
    private int age;

    private String interests;

    public Person(String username, String password){
        this.username = username;
        this.password = password;
        this.interests = "";
    }

    public String getUsername(){return this.username; }

    public String getPassword(){return this.password; }

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

    public String getInterests(){
        return interests;
    }

    public void addInterest(String newInterest){
        interests += newInterest + "/n";
    }

}