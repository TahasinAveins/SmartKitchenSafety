package com.example.smartkitchensafty;

public class UserProfile {

    public String nameUser;
    public String userEmail;

    public UserProfile(){

    }

    public UserProfile(String nameUser, String userEmail) {
        this.nameUser = nameUser;
        this.userEmail = userEmail;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
