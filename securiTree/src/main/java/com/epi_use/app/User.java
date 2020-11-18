package com.epi_use.app;

public class User {

    private String username;
    private int passwordHash;
    private String first_name;
    private String surname;

    public User(String username, int passwordHash){
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
