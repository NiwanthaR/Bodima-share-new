package com.example.afinal;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Validetion {


    public static boolean isValidNic(final String nic) {

        String stringToSearch = nic;

        Pattern p = Pattern.compile("([0-9]{9}[a-z]{1})");
        Matcher m = p.matcher(stringToSearch);


        if (m.find() && nic.length()==10)
            return true;
        else
            return false;

    }

    public static boolean isValidPassword(final String password) {

        String stringToSearch = password;

        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])");
        Matcher m = p.matcher(stringToSearch);


        if (m.find() && password.length()>7)
            return true;
        else
            return false;

    }

    public static boolean issame(final String password , final String repassword )
    {
        if (password.equals(repassword)){
            return true;
        }
        else
            return false;
    }

    public static boolean isValidmail(final String email)
    {
        String StringTosearch = email;

        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(StringTosearch);


        if (m.find())
            return true;
        else
            return false;
    }


    public static boolean iscontact(final String contact)
    {
        String StringTosearch = contact;

        Pattern p = Pattern.compile("(^1300\\d{6}$)|(^0[1|3|7|6|8]{1}[0-9]{8}$)|(^13\\d{4}$)|(^04\\d{2,3}\\d{6}$)");
        Matcher m = p.matcher(StringTosearch);


        if (m.find())
            return true;
        else
            return false;
    }


}

