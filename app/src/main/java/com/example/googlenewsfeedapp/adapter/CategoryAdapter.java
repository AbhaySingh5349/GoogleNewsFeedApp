package com.example.googlenewsfeedapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.googlenewsfeedapp.R;
import com.example.googlenewsfeedapp.model.CategoryModelClass;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoryModelClass> categoryModelClassList;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(Context context, List<CategoryModelClass> categoryModelClassList, CategoryClickInterface categoryClickInterface) {
        this.context = context;
        this.categoryModelClassList = categoryModelClassList;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModelClass categoryModelClass = categoryModelClassList.get(position);

        Glide.with(context).load(categoryModelClass.getCategoryImageUrl()).into(holder.categoryImageView);
        holder.categoryNameTextView.setText(categoryModelClass.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelClassList.size();
    }

    // we will be changing our data in main activity on basis of current category
    public interface CategoryClickInterface{
        void onCategoryClicked(int position); // position will be used to get data from List
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImageView;
        TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
        }
    }
}
