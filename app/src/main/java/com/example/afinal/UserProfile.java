package com.example.afinal;

public class UserProfile {

    public String FirstName;
    public String LastName;
    public String City;
    public String NIC;
    public String DOB;
    public String Contact;
    public String Email;


    public UserProfile(){

    }



    public UserProfile(String firstName, String lastName, String city,String NIC, String DOB, String contact, String email) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.City = city;
        this.NIC = NIC;
        this.DOB = DOB;
        this.Contact = contact;
        this.Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
