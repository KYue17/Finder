package com.example.kevin.finder;

import java.util.TreeSet;

/**
 * Created by Kevin on 6/10/2015.
 */
public class Person {

    private int id;
    private String name;
    private int age;

    private TreeSet<String> interests;

    public Person(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
        this.interests = new TreeSet<>();
    }

    public int getId(){return this.id; }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public String getInterests(){
        String allInterests = "";
        for(String interest: interests){
            allInterests+=interest + "\n";
        }
        return allInterests;
    }

    public void addInterest(String newInterest){
        interests.add(newInterest);
    }

}