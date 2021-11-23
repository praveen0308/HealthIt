package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateNewsItemBinding;
import com.jmm.healthit.model.news.ArticlesItem;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static ArrayList<ArticlesItem> articlesItems = new ArrayList<>();
    private NewsAdapterInterface newsAdapterInterface;

    public NewsAdapter(NewsAdapterInterface newsAdapterInterface) {
        this.newsAdapterInterface = newsAdapterInterface;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                TemplateNewsItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),newsAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(articlesItems.get(position));
    }

    @Override
    public int getItemCount() {
        return articlesItems.size();
    }

    public void setArticlesItems(List<ArticlesItem> items){
        articlesItems.clear();
        articlesItems.addAll(items);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{

        private final TemplateNewsItemBinding binding;
        private final NewsAdapterInterface mListener;

        public NewsViewHolder(@NonNull TemplateNewsItemBinding binding,NewsAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(ArticlesItem item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(articlesItems.get(getAdapterPosition())));
            binding.tvHeadline.setText(item.getTitle());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_insert_photo_24)
                    .error(R.drawable.ic_baseline_error_24);

            Glide.with(binding.getRoot().getContext()).load(item.getUrlToImage()).apply(options).into(binding.imgCover);
        }
    }

    public interface NewsAdapterInterface{
        void onItemClick(ArticlesItem item);
    }
}
