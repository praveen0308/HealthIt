package com.jmm.healthit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmm.healthit.databinding.TemplateCalculatorWidgetBinding;
import com.jmm.healthit.databinding.TemplateStarredDiseaseBinding;
import com.jmm.healthit.model.Disease;
import com.jmm.healthit.model.WidgetModel;

import java.util.ArrayList;
import java.util.List;

public class CalculatorItemsAdapter extends RecyclerView.Adapter<CalculatorItemsAdapter.CalculatorItemViewHolder> {
    private static ArrayList<WidgetModel> options = new ArrayList<>();
    private CalculatorItemsAdapterInterface calculatorItemsAdapterInterface;

    public CalculatorItemsAdapter(CalculatorItemsAdapterInterface calculatorItemsAdapterInterface) {
        this.calculatorItemsAdapterInterface = calculatorItemsAdapterInterface;
    }

    @NonNull
    @Override
    public CalculatorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalculatorItemViewHolder(
                TemplateCalculatorWidgetBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false),calculatorItemsAdapterInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CalculatorItemViewHolder holder, int position) {
        holder.bind(options.get(position));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }


    public void setItems(List<WidgetModel> items){
        items.clear();
        items.addAll(items);
        notifyDataSetChanged();
    }
    static class CalculatorItemViewHolder extends RecyclerView.ViewHolder{

        private final TemplateCalculatorWidgetBinding binding;
        private final CalculatorItemsAdapterInterface mListener;

        public CalculatorItemViewHolder(@NonNull TemplateCalculatorWidgetBinding binding, CalculatorItemsAdapterInterface calculatorItemsAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;
            this.mListener = calculatorItemsAdapterInterface;

        }


        public void bind(WidgetModel item){
            binding.getRoot().setOnClickListener(view -> mListener.onItemClick(options.get(getAdapterPosition())));
            binding.tvTitle.setText(item.getTitle());
            binding.ivIcon.setImageResource(item.getIcon());
        }
    }

    public interface CalculatorItemsAdapterInterface{
        void onItemClick(WidgetModel item);
    }
}
