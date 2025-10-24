package com.example.jokefinder.ui.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.jokefinder.databinding.ActivityMainBinding;
import com.example.jokefinder.ui.adapter.JokeAdapter;
import com.example.jokefinder.ui.viewmodel.JokeViewModel;
import com.example.jokefinder.ui.widget.PaginationView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private JokeViewModel viewModel;
    private JokeAdapter adapter;
    private ErrorDialogFragment errorDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new JokeAdapter();
        binding.rvJokes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJokes.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(JokeViewModel.class);

        viewModel.getNetworkStatus().observe(this, isConnected -> {
            if (isConnected != null && isConnected) {
                if (errorDialog != null && errorDialog.isAdded()) {
                    errorDialog.dismissAllowingStateLoss();
                    errorDialog = null;
                }

                viewModel.setNetworkError(false);
                viewModel.setDialogShown(false);

                String lastQuery = viewModel.getLastQuery();
                Boolean hasSearched = viewModel.getHasSearched().getValue();
                if (lastQuery != null && !lastQuery.trim().isEmpty()
                        && Boolean.TRUE.equals(hasSearched)) {
                    viewModel.search(lastQuery);
                }
            }
        });

        viewModel.getUiState().observe(this, state -> {
            binding.progressBar.setVisibility(state.loading ? View.VISIBLE : View.GONE);
            binding.tvEmpty.setVisibility(state.showEmpty ? View.VISIBLE : View.GONE);
            binding.tvTotalResult.setVisibility(state.showTotalResult ? View.VISIBLE : View.GONE);
            binding.paginationLayout.setVisibility(state.showPagination ? View.VISIBLE : View.GONE);

            if (state.showTotalResult) {
                binding.tvTotalResult.setText("Total " + state.totalResults + " Results");
            }

            if (state.showError && state.errorMessage != null) {
                if (state.errorMessage.toLowerCase().contains("network")) {
                    showNetworkErrorDialog(state.errorMessage);
                } else {
                    binding.tvEmpty.setText(state.errorMessage);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            adapter.submitList(state.pagedJokes != null ? state.pagedJokes : new ArrayList<>());

            if (state.showPagination) {
                int totalPages = (int) Math.ceil((double) state.totalResults / 10);
                PaginationView.setup(this, binding.paginationLayout,
                        state.currentPage, totalPages, new PaginationView.OnPageChangeListener() {
                            @Override
                            public void onPageSelected(int p) { viewModel.goToPage(p); }
                            @Override
                            public void onPrev() { viewModel.prevPage(); }
                            @Override
                            public void onNext() { viewModel.nextPage(); }
                        });
            }
        });

        binding.btnSearch.setOnClickListener(v -> {
            String q = binding.etSearch.getText().toString().trim();
            if (q.isEmpty()) {
                binding.etSearch.setError("Must input the key word");
                return;
            }
            binding.tvInitialMessage.setVisibility(View.GONE);
            adapter.setSearchQuery(q);
            viewModel.search(q);
            binding.btnClear.setVisibility(View.VISIBLE);
        });

        binding.btnClear.setOnClickListener(v -> {
            binding.etSearch.setText("");
            binding.btnClear.setVisibility(View.GONE);
            adapter.submitList(new ArrayList<>());
            binding.tvInitialMessage.setVisibility(View.VISIBLE);
        });
    }

    private void showNetworkErrorDialog(String msg) {
        if (errorDialog == null || !errorDialog.isAdded()) {
            errorDialog = ErrorDialogFragment.newInstance("Connection Error", msg);
            errorDialog.show(getSupportFragmentManager(), "error_dialog");
        }
    }
}
