package com.example.providertest;

public class Student {
    private int id;
    private String name;
    private String class_name;

    public Student() {
    }

    public Student(int id, String name, String class_name) {
        this.id = id;
        this.name = name;
        this.class_name = class_name;
    }

    public Student(String name, String class_name) {
        this.name = name;
        this.class_name = class_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return name + " - " + class_name;
    }
}
