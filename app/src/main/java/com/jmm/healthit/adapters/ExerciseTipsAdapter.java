package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateTitleImageDescBinding;
import com.jmm.healthit.model.ExerciseModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseTipsAdapter extends RecyclerView.Adapter<ExerciseTipsAdapter.ExerciseTipsViewHolder> {

    private static ArrayList<ExerciseModel> exerciseTips = new ArrayList<>();
    private ExerciseTipsAdapterInterface exerciseTipsAdapterInterface;

    public ExerciseTipsAdapter(ExerciseTipsAdapterInterface exerciseTipsAdapterInterface) {
        this.exerciseTipsAdapterInterface = exerciseTipsAdapterInterface;
    }

    @NonNull
    @Override
    public ExerciseTipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseTipsViewHolder(
                TemplateTitleImageDescBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),exerciseTipsAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseTipsViewHolder holder, int position) {
        holder.bind(exerciseTips.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseTips.size();
    }

    public void setExerciseTips(List<ExerciseModel> items){
        exerciseTips.clear();
        exerciseTips.addAll(items);
        notifyDataSetChanged();
    }

    static class ExerciseTipsViewHolder extends RecyclerView.ViewHolder{

        private final TemplateTitleImageDescBinding binding;
        private final ExerciseTipsAdapterInterface mListener;

        public ExerciseTipsViewHolder(@NonNull TemplateTitleImageDescBinding binding,ExerciseTipsAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(ExerciseModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(exerciseTips.get(getAdapterPosition())));
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

    public interface ExerciseTipsAdapterInterface{
        void onItemClick(ExerciseModel item);
    }
}
