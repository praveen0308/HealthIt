package com.jmm.healthit.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String fullName;
    private String email;
    private String password;
    private int age;
    private String gender;
    private String city;
    private String profilePic;
    private List<String> watchList = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(String fullName, String email, String password, int age, String gender, String city, String profilePic) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.profilePic = profilePic;
    }

    public List<String> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<String> watchList) {
        this.watchList = watchList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
