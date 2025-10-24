package com.example.jokefinder.ui.state;

import com.example.jokefinder.data.model.Joke;
import java.util.List;
public class UiState {
    public final boolean loading;
    public final boolean showEmpty;
    public final boolean showError;
    public final boolean showPagination;
    public final boolean showTotalResult;
    public final String errorMessage;
    public final List<Joke> pagedJokes;
    public final int totalResults;
    public final int currentPage;

    public UiState(boolean loading, boolean showEmpty, boolean showError,
                   boolean showPagination, boolean showTotalResult,
                   String errorMessage, List<Joke> pagedJokes, int totalResults, int currentPage) {
        this.loading = loading;
        this.showEmpty = showEmpty;
        this.showError = showError;
        this.showPagination = showPagination;
        this.showTotalResult = showTotalResult;
        this.errorMessage = errorMessage;
        this.pagedJokes = pagedJokes;
        this.totalResults = totalResults;
        this.currentPage = currentPage;
    }
}
