package com.example.createpc.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.createpc.R;
import com.example.createpc.databinding.DialogFragmentSaveBuildBinding;

public class SaveBuildDialogFragment extends DialogFragment {
    private DialogFragmentSaveBuildBinding dialogFragmentSaveBuildBinding;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogFragmentSaveBuildBinding = DialogFragmentSaveBuildBinding.inflate(requireActivity().getLayoutInflater());
        EditText input = dialogFragmentSaveBuildBinding.inputName;
        int position = requireArguments().getInt("id");
        String name = requireArguments().getString("name", "");
        if (!name.equals("")) input.setText(name);
        builder.setView(dialogFragmentSaveBuildBinding.getRoot())
                .setNegativeButton(R.string.dialog_save_btn_cancel, (dialog, id) -> SaveBuildDialogFragment.this.requireDialog().cancel())
                .setPositiveButton(R.string.dialog_save_btn_accept, (dialog, id) -> {
                    String result = input.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("build_name", result);
                    bundle.putInt("build_id", position);
                    getParentFragmentManager().setFragmentResult("NameKey", bundle);
                    SaveBuildDialogFragment.this.requireDialog().dismiss();
                });
        return builder.setView(dialogFragmentSaveBuildBinding.getRoot()).create();
    }

    public void onDestroyView() {
        super.onDestroyView();
        dialogFragmentSaveBuildBinding = null;
    }
}
