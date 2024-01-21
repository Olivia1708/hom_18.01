package com.example.demo;

public class User2 extends User {
    private String Password2;
    User2() {
    }
    User2(String name, String password, int age, String password2) {
        super(name, password, age);
        Password2 = password2;
    }
    public String getPassword2() {
        return Password2;
    }
    public void setPassword2(String password2) {
        Password2 = password2;
    }
    public User getUser() {
        return new User(getUsername(), getPassword(), getAge());
    }
    public boolean isSamePassword() {
        return getPassword() == getPassword2();
    }
}
