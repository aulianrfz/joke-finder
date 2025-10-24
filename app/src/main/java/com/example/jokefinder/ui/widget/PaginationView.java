package com.example.jokefinder.ui.widget;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.jokefinder.R;

public class PaginationView {

    public interface OnPageChangeListener {
        void onPageSelected(int page);
        void onPrev();
        void onNext();
    }

    public static void setup(Context context, LinearLayout layout, int currentPage, int totalPages, OnPageChangeListener listener) {
        layout.removeAllViews();

        Button backButton = new Button(context);
        backButton.setText("<");
        backButton.setBackgroundResource(R.drawable.pagination_button_bg);

        LinearLayout.LayoutParams backParams = new LinearLayout.LayoutParams(90, 90);
        backParams.setMargins(4, 0, 4, 0);
        backButton.setLayoutParams(backParams);
        backButton.setTextSize(12);

        backButton.setOnClickListener(v -> listener.onPrev());
        backButton.setEnabled(currentPage > 1);
        layout.addView(backButton);

        int visibleMiddle = 3;

        int startPage = Math.max(2, currentPage - 1);
        int endPage = Math.min(totalPages - 1, currentPage + 1);

        if (currentPage <= 2) {
            startPage = 2;
            endPage = Math.min(totalPages - 1, 2 + visibleMiddle - 1);
        }

        if (currentPage >= totalPages - 1) {
            endPage = totalPages - 1;
            startPage = Math.max(2, totalPages - visibleMiddle);
        }

        addPageButton(context, layout, 1, currentPage, listener);

        if (startPage > 2) addDots(context, layout);

        for (int i = startPage; i <= endPage; i++) {
            addPageButton(context, layout, i, currentPage, listener);
        }

        if (endPage < totalPages - 1) addDots(context, layout);

        if (totalPages > 1)
            addPageButton(context, layout, totalPages, currentPage, listener);

        Button nextButton = new Button(context);
        nextButton.setText(">");
        nextButton.setBackgroundResource(R.drawable.pagination_button_bg);

        LinearLayout.LayoutParams nextParams = new LinearLayout.LayoutParams(90, 90);
        nextParams.setMargins(4, 0, 4, 0);
        nextButton.setLayoutParams(nextParams);
        nextButton.setTextSize(12);

        nextButton.setOnClickListener(v -> listener.onNext());
        nextButton.setEnabled(currentPage < totalPages);
        layout.addView(nextButton);
    }

    private static void addPageButton(Context context, LinearLayout layout, int page, int currentPage, OnPageChangeListener listener) {
        Button btn = new Button(context);
        btn.setText(String.valueOf(page));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(90, 90);
        params.setMargins(4, 0, 4, 0);
        btn.setLayoutParams(params);

        btn.setTextSize(12);

        if (page == currentPage) {
            btn.setBackgroundResource(R.drawable.pagination_active_bg);
            btn.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        } else {
            btn.setBackgroundResource(R.drawable.pagination_button_bg);
            btn.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            btn.setOnClickListener(v -> listener.onPageSelected(page));
        }

        layout.addView(btn);
    }

    private static void addDots(Context context, LinearLayout layout) {
        TextView dots = new TextView(context);
        dots.setText("...");
        dots.setTextSize(18);
        dots.setPadding(8, 0, 8, 0);
        dots.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
        layout.addView(dots);
    }
}
