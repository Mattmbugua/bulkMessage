package com.example.bulkmessage;

public class Userhelperclass {
    String sname,email,password,nationalId;
    public Userhelperclass(){

    }

    public Userhelperclass(String sname, String email, String password, String nationalId) {
        this.sname = sname;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
