package com.example.afinal;

public class UserProfile {

    public String FirstName;
    public String LastName;
    public String City;
    public String NIC;
    public String DOB;
    public String Contact;
    public String Email;




    public UserProfile(String firstName, String lastName, String city, String NIC, String DOB, String contact, String email) {
        FirstName = firstName;
        LastName = lastName;
        City = city;
        this.NIC = NIC;
        this.DOB = DOB;
        Contact = contact;
        Email = email;
    }


}