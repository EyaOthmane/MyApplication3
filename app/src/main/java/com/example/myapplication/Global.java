package com.example.myapplication;

public class Global {

    private static String email="eyaothmene@yahoo.fr";
    private static String password="thepowerof4";

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Global.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Global.password = password;
    }
}