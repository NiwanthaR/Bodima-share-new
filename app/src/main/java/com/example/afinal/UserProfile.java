package com.example.afinal;

public class UserProfile {

    public String userupname;
    public String userupemai;
    public String userupnic;

    public UserProfile(){

    }

    public UserProfile(String userupname, String userupemai, String userupnic) {
        this.userupname = userupname;
        this.userupemai = userupemai;
        this.userupnic = userupnic;
    }

    public String getUserupname() {
        return userupname;
    }

    public void setUserupname(String userupname) {
        this.userupname = userupname;
    }

    public String getUserupemai() {
        return userupemai;
    }

    public void setUserupemai(String userupemai) {
        this.userupemai = userupemai;
    }

    public String getUserupnic() {
        return userupnic;
    }

    public void setUserupnic(String userupnic) {
        this.userupnic = userupnic;
    }
}
