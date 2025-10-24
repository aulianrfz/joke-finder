package com.example.jokefinder.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jokefinder.data.model.Joke;
import com.example.jokefinder.data.repository.JokeRepository;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.jokefinder.ui.state.UiState;
import com.example.jokefinder.utils.NetworkLiveData;
public class JokeViewModel extends AndroidViewModel {
    private final JokeRepository repository;
    private final NetworkLiveData networkLiveData;

    private final MutableLiveData<UiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasSearched = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> dialogShown = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isNetworkError = new MutableLiveData<>(false);

    private static final int PAGE_SIZE = 10;
    private List<Joke> fullJokes = null;
    private String lastQuery;

    public JokeViewModel(@NonNull Application application) {
        super(application);
        repository = new JokeRepository();
        networkLiveData = new NetworkLiveData(application);

        uiState.setValue(new UiState(false, true, false, false, false,
                null, new ArrayList<>(), 0, 1));
    }

    public LiveData<UiState> getUiState() { return uiState; }
    public LiveData<Boolean> getNetworkStatus() { return networkLiveData; }
    public LiveData<Boolean> getHasSearched() { return hasSearched; }
    public void setDialogShown(boolean shown) { dialogShown.postValue(shown); }
    public void setNetworkError(boolean v) { isNetworkError.postValue(v); }

    public String getLastQuery() { return lastQuery; }

    public void search(String query) {
        errorClearAndLoading();

        if (query == null) {
            emitError("Masukkan kata kunci terlebih dahulu");
            return;
        }

        String trimmed = query.trim();

        if (trimmed.isEmpty()) {
            emitError("Masukkan kata kunci terlebih dahulu");
            return;
        }

        if (trimmed.length() < 3) {
            emitError("Kata kunci minimal 3 huruf");
            return;
        }

        lastQuery = trimmed;
        hasSearched.postValue(true);

        repository.searchJokes(trimmed, new JokeRepository.JokeSearchCallback() {
            @Override
            public void onSuccess(List<Joke> jokes, int totalFromApi) {
                fullJokes = jokes != null ? jokes : new ArrayList<>();
                int total = totalFromApi;

                if (fullJokes.isEmpty()) {
                    uiState.postValue(new UiState(false, true, false, false, false,
                            null, new ArrayList<>(), total, 1));
                } else {
                    int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);
                    List<Joke> firstPage = makePage(1);
                    uiState.postValue(new UiState(false, false, false,
                            totalPages > 1, true, null, firstPage, total, 1));
                }
            }

            @Override
            public void onError(String message) {
                fullJokes = null;
                emitError(message);
            }
        });
    }


    private void errorClearAndLoading() {
        uiState.postValue(new UiState(true, false, false, false, false, null, new ArrayList<>(), 0, 1));
    }

    private void emitError(String msg) {
        uiState.postValue(new UiState(false, false, true,
                false, false, msg, new ArrayList<>(), 0, 1));
    }

    private List<Joke> makePage(int page) {
        if (fullJokes == null) return new ArrayList<>();
        int total = fullJokes.size();
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, total);
        if (start >= total) return new ArrayList<>();
        return new ArrayList<>(fullJokes.subList(start, end));
    }

    public void nextPage() {
        UiState current = uiState.getValue();
        if (current == null) return;
        int totalPages = (int) Math.ceil((double) current.totalResults / PAGE_SIZE);
        int cur = current.currentPage;
        if (cur < totalPages) {
            goToPage(cur + 1);
        }
    }

    public void prevPage() {
        UiState current = uiState.getValue();
        if (current == null) return;
        int cur = current.currentPage;
        if (cur > 1) {
            goToPage(cur - 1);
        }
    }

    public void goToPage(int page) {
        if (fullJokes == null || fullJokes.isEmpty()) return;
        int total = fullJokes.size();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);
        if (page < 1 || page > totalPages) return;

        List<Joke> pageList = makePage(page);

        uiState.postValue(new UiState(false, false, false,
                totalPages > 1, true, null, pageList, total, page));
    }
}
