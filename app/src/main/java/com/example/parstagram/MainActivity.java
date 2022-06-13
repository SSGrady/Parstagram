package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "abc123";
    public Button btnlogOut;
    public ParseUser currentUser;
    public EditText etDescription;
    public Button btnTakePicture;
    public Button btnSubmit;
    public ImageView ivPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogOut = findViewById(R.id.btnlogOut);
        etDescription = findViewById(R.id.etDescription);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                currentUser = ParseUser.getCurrentUser(); // this will now be null
                goLoginActivity();
            }
        });

        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {

            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                Log.e(TAG, "Issue with getting posts", e);
                return;
                }
                for(Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }

        });
    }

    private void goLoginActivity() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
}