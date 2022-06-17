package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser { // underscore is here since User is a built-in class

    public static final String KEY_PROFILE_PHOTO = "profilePhoto"; // name of parsefile column

    public ParseFile getProfilePhoto() { return getParseFile(KEY_PROFILE_PHOTO); }

    public void setProfilePhoto(ParseFile parseFile) { put(KEY_PROFILE_PHOTO, parseFile); }
}
