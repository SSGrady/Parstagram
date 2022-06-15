package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    public List<Comment> mComments = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }

    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View commentView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        ViewHolder viewholder = new ViewHolder(commentView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {

        Comment comment = mComments.get(position);
        holder.tvBody.setText(comment.getBody());
        holder.tvAuthor.setText(comment.getAuthor().getUsername());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
