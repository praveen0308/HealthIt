package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.TemplateNewsItemBinding;
import com.jmm.healthit.databinding.TemplateStarredDiseaseBinding;
import com.jmm.healthit.model.Disease;
import com.jmm.healthit.model.news.ArticlesItem;

import java.util.ArrayList;
import java.util.List;

public class DiseaseWatchListAdapter extends RecyclerView.Adapter<DiseaseWatchListAdapter.DiseaseItemViewHolder> {

    private static ArrayList<Disease> diseases = new ArrayList<>();
    private DiseaseAdapterInterface diseaseAdapterInterface;

    public DiseaseWatchListAdapter(DiseaseAdapterInterface diseaseAdapterInterface) {
        this.diseaseAdapterInterface = diseaseAdapterInterface;
    }

    @NonNull
    @Override
    public DiseaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiseaseItemViewHolder(
                TemplateStarredDiseaseBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),diseaseAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseItemViewHolder holder, int position) {
        holder.bind(diseases.get(position));
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public void setDiseaseItems(List<Disease> items){
        diseases.clear();
        diseases.addAll(items);
        notifyDataSetChanged();
    }

    static class DiseaseItemViewHolder extends RecyclerView.ViewHolder{

        private final TemplateStarredDiseaseBinding binding;
        private final DiseaseAdapterInterface mListener;

        public DiseaseItemViewHolder(@NonNull TemplateStarredDiseaseBinding binding,DiseaseAdapterInterface newsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = newsAdapterInterface;

        }

        public void bind(Disease item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(diseases.get(getAdapterPosition())));
            binding.tvTitle.setText(item.getDiseaseName());
            binding.tvDescription.setText(item.getDiseaseDescription());
        }
    }

    public interface DiseaseAdapterInterface{
        void onItemClick(Disease item);
    }
}
