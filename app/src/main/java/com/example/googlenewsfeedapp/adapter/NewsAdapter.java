package com.example.googlenewsfeedapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.googlenewsfeedapp.R;
import com.example.googlenewsfeedapp.Util;
import com.example.googlenewsfeedapp.model.ArticleModelClass;

import java.util.List;

import butterknife.internal.Utils;

public class NewsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ArticleModelClass> articleModelClassList;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<ArticleModelClass> articleModelClassList) {
        this.context = context;
        this.articleModelClassList = articleModelClassList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new ArticleViewHolder(view, onItemClickListener);
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
        ArticleModelClass articleModelClass = articleModelClassList.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Util.getRandomDrawbleColor());
        requestOptions.error(Util.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context).load(articleModelClass.getUrlToImage()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).into(((ArticleViewHolder) holder).newsImageView);

        ((ArticleViewHolder) holder).authorTextView.setText(articleModelClass.getAuthor());
        ((ArticleViewHolder) holder).publishedAtTextView.setText(Util.DateFormat(articleModelClass.getPublishedAt()));
        ((ArticleViewHolder) holder).titleTextView.setText(articleModelClass.getTitle());
        ((ArticleViewHolder) holder).descriptionTextView.setText(articleModelClass.getDescription());
        ((ArticleViewHolder) holder).sourceTextView.setText(articleModelClass.getSource().getClass().getName());
        ((ArticleViewHolder) holder).titleTextView.setText(" \u2022 " + Util.DateToTimeFormat(articleModelClass.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return articleModelClassList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView newsImageView;
        TextView authorTextView, publishedAtTextView, titleTextView, descriptionTextView, sourceTextView, timeTextView;
        OnItemClickListener onItemClickListener;

        public ArticleViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            newsImageView = itemView.findViewById(R.id.newsImageView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            sourceTextView = itemView.findViewById(R.id.sourceTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
