package com.example.kevin.finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by Kevin on 6/10/2015.
 */
public class Person {

    private String name;
    private int age;

    private TreeSet<String> interests;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
        this.interests = new TreeSet<>();
    }

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
