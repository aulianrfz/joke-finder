package com.example.jokefinder.ui.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokefinder.data.model.Joke;
import com.example.jokefinder.databinding.ItemJokeBinding;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {

    private String searchQuery = "";

    private final DiffUtil.ItemCallback<Joke> diffCallback = new DiffUtil.ItemCallback<Joke>() {
        @Override
        public boolean areItemsTheSame(@NonNull Joke oldItem, @NonNull Joke newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Joke oldItem, @NonNull Joke newItem) {
            return oldItem.getValue().equals(newItem.getValue());
        }
    };

    private final AsyncListDiffer<Joke> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitList(List<Joke> newItems) {
        differ.submitList(newItems);
    }

    @NonNull
    @Override
    public JokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJokeBinding binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JokeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeViewHolder holder, int position) {
        Joke j = differ.getCurrentList().get(position);
        String value = j.getValue();

        if (!searchQuery.isEmpty()) {
            String lowerText = value.toLowerCase();
            int start = lowerText.indexOf(searchQuery);

            if (start >= 0) {
                int end = start + searchQuery.length();
                SpannableString spannable = new SpannableString(value);
                spannable.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                holder.binding.textViewJoke.setText(spannable);
            } else {
                holder.binding.textViewJoke.setText(value);
            }
        } else {
            holder.binding.textViewJoke.setText(value);
        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    static class JokeViewHolder extends RecyclerView.ViewHolder {
        ItemJokeBinding binding;

        public JokeViewHolder(@NonNull ItemJokeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query.toLowerCase() : "";
    }
}
