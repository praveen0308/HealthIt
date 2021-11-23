package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateTitleImageDescBinding;
import com.jmm.healthit.model.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class FoodTipsAdapter extends RecyclerView.Adapter<FoodTipsAdapter.FoodTipsViewHolder> {

    private static ArrayList<FoodModel> foodTips = new ArrayList<>();
    private FoodTipsAdapterInterface foodTipsAdapterInterface;

    public FoodTipsAdapter(FoodTipsAdapterInterface foodTipsAdapterInterface) {
        this.foodTipsAdapterInterface = foodTipsAdapterInterface;
    }

    @NonNull
    @Override
    public FoodTipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodTipsViewHolder(
                TemplateTitleImageDescBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),foodTipsAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTipsViewHolder holder, int position) {
        holder.bind(foodTips.get(position));
    }

    @Override
    public int getItemCount() {
        return foodTips.size();
    }

    public void setFoodTips(List<FoodModel> items){
        foodTips.clear();
        foodTips.addAll(items);
        notifyDataSetChanged();
    }

    static class FoodTipsViewHolder extends RecyclerView.ViewHolder{

        private final TemplateTitleImageDescBinding binding;
        private final FoodTipsAdapterInterface mListener;

        public FoodTipsViewHolder(@NonNull TemplateTitleImageDescBinding binding,FoodTipsAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(FoodModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(foodTips.get(getAdapterPosition())));
            String title = item.getTitle().replaceAll("\\d","");
            String num = String.valueOf(getAdapterPosition()+1);
            binding.tvTitle.setText(num + ""+title);
            binding.tvDescription.setText(item.getDescription());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_insert_photo_24)
                    .error(R.drawable.ic_baseline_error_24);

            Glide.with(binding.getRoot().getContext()).load(item.getImageUrl()).apply(options).into(binding.ivImage);
        }
    }

    public interface FoodTipsAdapterInterface{
        void onItemClick(FoodModel item);
    }
}
