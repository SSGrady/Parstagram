package com.example.parstagram;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostDetails extends AppCompatActivity {
   // TODO: field signature declarations

    public static RecyclerView rvComments;
    public CommentsAdapter adapter;
    Post post;

    @Override
    protected void onRestart() {
        // fires when we come back form future activities
        super.onRestart();
        refreshComments();
    }


    void refreshComments() {
        // load all comments for this post
        ParseQuery <Comment> query =  ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.orderByDescending("createdAt");
        query.include(Comment.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Comment>() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if(e != null) {
                    Log.e("Failed to get comments", e.getMessage());
                    return;
                }
                adapter.mComments.clear();
                adapter.mComments.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvDate = findViewById(R.id.tvDate);
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        ImageView ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        ImageButton ibHeart = findViewById(R.id.ibHeart);
        ImageButton ibComment = findViewById(R.id.ibComment);
        TextView tvLikes = findViewById(R.id.tvLikes);

        post = getIntent().getParcelableExtra("post");

        ibHeart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<ParseUser> likedBy = post.getLikedBy();
                if (post.isLikedByCurrentUser()) {
                    // unlike
                    post.unlike();
                    ibHeart.setBackgroundResource(R.drawable.ufi_heart);
                } else {
                    ibHeart.setBackgroundResource(R.drawable.ufi_heart_active);
                    // like
                    post.like();
                }
               // post.setLikedBy(likedBy);
               // post.saveInBackground();
                tvLikes.setText(post.getLikesCount());
            }
        });

        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostDetails.this, ComposeCommentActivity.class);
                i.putExtra("post", post);
                startActivity(i);
            }
        });

        rvComments = findViewById(R.id.rvComments);
        adapter = new CommentsAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        if (post.isLikedByCurrentUser()) {
            ibHeart.setBackgroundResource(R.drawable.ufi_heart_active);
        }  else {
            ibHeart.setBackgroundResource(R.drawable.ufi_heart);
        }

        tvLikes.setText(post.getLikesCount());

       //  Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvUsername.setText(post.getUser().getUsername());
        tvDate.setText(calculateTimeAgo(post.getCreatedAt()));
        Glide.with(this).load(post.getImage().getUrl()).into(ivPhoto);

        refreshComments();

    }
    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }
}