package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmm.healthit.databinding.TemplateWorkoutDayItemBinding;
import com.jmm.healthit.databinding.TemplateWorkoutItemBinding;
import com.jmm.healthit.model.workout.WorkoutDayModel;
import com.jmm.healthit.model.workout.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private static ArrayList<WorkoutModel> mList = new ArrayList<>();
    private WorkoutAdapterInterface workoutAdapterInterface;

    public WorkoutAdapter(WorkoutAdapterInterface workoutAdapterInterface) {
        this.workoutAdapterInterface = workoutAdapterInterface;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutViewHolder(
                TemplateWorkoutItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),workoutAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItems(List<WorkoutModel> items){
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder{

        private final TemplateWorkoutItemBinding binding;
        private final WorkoutAdapterInterface mListener;

        public WorkoutViewHolder(@NonNull TemplateWorkoutItemBinding binding,WorkoutAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(WorkoutModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(mList.get(getAdapterPosition())));
            binding.tvExerciseName.setText(item.getName());
            binding.tvExerciseCount.setText(String.format("x%s", item.getReps()));

        }
    }

    public interface WorkoutAdapterInterface{
        void onItemClick(WorkoutModel item);
    }
}
