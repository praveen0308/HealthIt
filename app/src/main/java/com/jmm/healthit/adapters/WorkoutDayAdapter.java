package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateNewsItemBinding;
import com.jmm.healthit.databinding.TemplateWorkoutDayItemBinding;
import com.jmm.healthit.model.news.ArticlesItem;
import com.jmm.healthit.model.workout.WorkoutDayModel;
import com.jmm.healthit.model.workout.WorkoutModel;
import com.jmm.healthit.ui.dashboard.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDayAdapter extends RecyclerView.Adapter<WorkoutDayAdapter.WorkoutDayViewHolder> {

    private static ArrayList<WorkoutDayModel> mList = new ArrayList<>();
    private WorkoutDayAdapterInterface workoutAdapterInterface;

    public WorkoutDayAdapter(WorkoutDayAdapterInterface workoutAdapterInterface) {
        this.workoutAdapterInterface = workoutAdapterInterface;
    }

    @NonNull
    @Override
    public WorkoutDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutDayViewHolder(
                TemplateWorkoutDayItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),workoutAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutDayViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItems(List<WorkoutDayModel> items){
        mList.clear();
        mList.addAll(items);
        notifyDataSetChanged();
    }

    static class WorkoutDayViewHolder extends RecyclerView.ViewHolder{

        private final TemplateWorkoutDayItemBinding binding;
        private final WorkoutDayAdapterInterface mListener;

        public WorkoutDayViewHolder(@NonNull TemplateWorkoutDayItemBinding binding,WorkoutDayAdapterInterface workoutDayAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = workoutDayAdapterInterface;

        }

        public void bind(WorkoutDayModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(mList.get(getAdapterPosition())));
            binding.tvDay.setText("Day "+item.getDayNumber());

        }
    }

    public interface WorkoutDayAdapterInterface{
        void onItemClick(WorkoutDayModel item);
    }
}
