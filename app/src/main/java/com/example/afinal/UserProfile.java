package com.example.afinal;

public class UserProfile {

    public String upload_Fname;
    public String upload_Lname;
    public String upload_City;
    public String upload_Nic;
    public String upload_Dob;
    public String upload_Contact;
    public String upload_Email;
    public String upload_Gender;

    public UserProfile()
    {

    }

    public UserProfile(String upload_Fname, String upload_Lname, String upload_City, String upload_Nic, String upload_Dob, String upload_Contact, String upload_Email, String upload_Gender) {
        this.upload_Fname = upload_Fname;
        this.upload_Lname = upload_Lname;
        this.upload_City = upload_City;
        this.upload_Nic = upload_Nic;
        this.upload_Dob = upload_Dob;
        this.upload_Contact = upload_Contact;
        this.upload_Email = upload_Email;
        this.upload_Gender = upload_Gender;
    }

    public UserProfile(String upload_Fname, String upload_Lname, String upload_City, String upload_Nic, String upload_Dob, String upload_Contact) {
        this.upload_Fname = upload_Fname;
        this.upload_Lname = upload_Lname;
        this.upload_City = upload_City;
        this.upload_Nic = upload_Nic;
        this.upload_Dob = upload_Dob;
        this.upload_Contact = upload_Contact;
    }



    public String getUpload_Fname() {
        return upload_Fname;
    }

    public void setUpload_Fname(String upload_Fname) {
        this.upload_Fname = upload_Fname;
    }

    public String getUpload_Lname() {
        return upload_Lname;
    }

    public void setUpload_Lname(String upload_Lname) {
        this.upload_Lname = upload_Lname;
    }

    public String getUpload_City() {
        return upload_City;
    }

    public void setUpload_City(String upload_City) {
        this.upload_City = upload_City;
    }

    public String getUpload_Nic() {
        return upload_Nic;
    }

    public void setUpload_Nic(String upload_Nic) {
        this.upload_Nic = upload_Nic;
    }

    public String getUpload_Dob() {
        return upload_Dob;
    }

    public void setUpload_Dob(String upload_Dob) {
        this.upload_Dob = upload_Dob;
    }

    public String getUpload_Contact() {
        return upload_Contact;
    }

    public void setUpload_Contact(String upload_Contact) {
        this.upload_Contact = upload_Contact;
    }

    public String getUpload_Email() {
        return upload_Email;
    }

    public void setUpload_Email(String upload_Email) {
        this.upload_Email = upload_Email;
    }
}