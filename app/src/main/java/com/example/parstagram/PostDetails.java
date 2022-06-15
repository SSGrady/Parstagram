package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.parceler.Parcels;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvDate = findViewById(R.id.tvDate);
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        ImageButton ibHeart = findViewById(R.id.ibHeart);
        ImageButton ibComment = findViewById(R.id.ibComment);
        TextView tvLikes = findViewById(R.id.tvLikes);

        post = getIntent().getParcelableExtra("post");

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

       //  Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvUsername.setText(post.getUser().getUsername());
        tvDate.setText(post.getCreatedAt().toString());
        Glide.with(this).load(post.getImage().getUrl()).into(ivPhoto);

        refreshComments();


    }
}