package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

public class PostDetails extends AppCompatActivity {
    public static TextView tvUsernameDetails;
    public static ImageView ivImageDetails;
    public static TextView tvDescriptionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsernameDetails = findViewById(R.id.tvUsernameDetails);
        ivImageDetails = findViewById(R.id.ivImageDetails);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        tvDescriptionDetails.setText(post.getDescription());
        tvUsernameDetails.setText(post.getUser().getUsername());
        Glide.with(this).load(post.getImage().getUrl()).into(ivImageDetails);
    }
}