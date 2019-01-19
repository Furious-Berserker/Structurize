package com.svatoslavbulgakov.structurize;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by svatoslavbulgakov on 19/01/2019.
 */

public class UserData {

    public static String getUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String name = "name";

        if (!user.getDisplayName().isEmpty()){
            name = user.getDisplayName();
            return name;
        } else
            return name;
    }

    public static String getUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = "email";

        if (!user.getEmail().isEmpty()){
            email = user.getEmail();
            return email;
        } else
            return email;
    }

    public static Uri getUserImage(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Uri image = Uri.EMPTY;

        if (!Uri.EMPTY.equals(user.getPhotoUrl())){
            image = user.getPhotoUrl();
            return image;
        } else
            return image;
    }
}
