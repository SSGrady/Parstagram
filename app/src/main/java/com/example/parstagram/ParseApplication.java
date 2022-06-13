package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7r1pbgBBBvtSXWPHMwntpyZVc6D6zTZz2Ah6z2MR")
                .clientKey("WoGqY4i9WastEnbkBRLc6BwobarURhJEoEaf06Gs")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
