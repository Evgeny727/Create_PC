package com.example.createpc.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.createpc.R;
import com.example.createpc.databinding.DialogFragmentSaveBuildBinding;

public class SaveBuildDialogFragment extends DialogFragment {
    private DialogFragmentSaveBuildBinding dialogFragmentSaveBuildBinding;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogFragmentSaveBuildBinding = DialogFragmentSaveBuildBinding.inflate(requireActivity().getLayoutInflater());
        EditText input = dialogFragmentSaveBuildBinding.inputName;
        int position = getArguments().getInt("id");
        builder.setView(dialogFragmentSaveBuildBinding.getRoot())
                .setNegativeButton(R.string.dialog_save_btn_cancel, (dialog, id) -> SaveBuildDialogFragment.this.getDialog().cancel())
                .setPositiveButton(R.string.dialog_save_btn_accept, (dialog, id) -> {
                    String result = input.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("build_name", result);
                    bundle.putInt("build_id", position);
                    getParentFragmentManager().setFragmentResult("NameKey", bundle);
                    SaveBuildDialogFragment.this.getDialog().dismiss();
                });
        return builder.setView(dialogFragmentSaveBuildBinding.getRoot()).create();
    }

    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        NavController controller = NavHostFragment.findNavController(SaveBuildDialogFragment.this);
        NavOptions options = new NavOptions(true, false, R.id.createFragment, true,
                false, 0, 0, 0 , 0);
        controller.navigate(R.id.createFragment, new Bundle(), options);
    }

    public void onDestroyView() {
        super.onDestroyView();
        dialogFragmentSaveBuildBinding = null;
    }
}
