package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateTitleImageDescBinding;
import com.jmm.healthit.model.MedicineModel;

import java.util.ArrayList;
import java.util.List;

public class MedicineTipsAdapter extends RecyclerView.Adapter<MedicineTipsAdapter.MedicineTipsViewHolder> {

    private static ArrayList<MedicineModel> medicineTips = new ArrayList<>();
    private MedicineTipsAdapterInterface medicineTipsAdapterInterface;

    public MedicineTipsAdapter(MedicineTipsAdapterInterface medicineTipsAdapterInterface) {
        this.medicineTipsAdapterInterface = medicineTipsAdapterInterface;
    }

    @NonNull
    @Override
    public MedicineTipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicineTipsViewHolder(
                TemplateTitleImageDescBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),medicineTipsAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineTipsViewHolder holder, int position) {
        holder.bind(medicineTips.get(position));
    }

    @Override
    public int getItemCount() {
        return medicineTips.size();
    }

    public void setMedicineTips(List<MedicineModel> items){
        medicineTips.clear();
        medicineTips.addAll(items);
        notifyDataSetChanged();
    }

    static class MedicineTipsViewHolder extends RecyclerView.ViewHolder{

        private final TemplateTitleImageDescBinding binding;
        private final MedicineTipsAdapterInterface mListener;

        public MedicineTipsViewHolder(@NonNull TemplateTitleImageDescBinding binding,MedicineTipsAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(MedicineModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(medicineTips.get(getAdapterPosition())));
            binding.tvTitle.setText(item.getTitle());
            binding.tvDescription.setText(item.getDescription());
            binding.ivImage.setVisibility(View.GONE);
        }
    }

    public interface MedicineTipsAdapterInterface{
        void onItemClick(MedicineModel item);
    }
}
