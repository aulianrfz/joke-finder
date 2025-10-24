package com.example.jokefinder.ui.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.jokefinder.databinding.ErrorDialogBinding;

public class ErrorDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    public static ErrorDialogFragment newInstance(String title, String message) {
        ErrorDialogFragment frag = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ErrorDialogBinding binding = ErrorDialogBinding.inflate(getLayoutInflater());

        assert getArguments() != null;
        String title = getArguments().getString(ARG_TITLE, "Terjadi Kesalahan");
        String message = getArguments().getString(ARG_MESSAGE, "Unknown error");

        binding.dialogTitle.setText(title);
        binding.dialogMessage.setText(message);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();

        binding.okButton.setOnClickListener(v -> dialog.dismiss());
        binding.closeButton.setOnClickListener(v -> dialog.dismiss());

        return dialog;
    }
}
