package com.example.createpc.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.createpc.R;
import com.example.createpc.databinding.DialogFragmentDeleteElementBinding;

public class DeleteBuildDialogFragment extends DialogFragment {
    private DialogFragmentDeleteElementBinding dialogFragmentDeleteElementBinding;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogFragmentDeleteElementBinding = DialogFragmentDeleteElementBinding.inflate(requireActivity().getLayoutInflater());
        TextView header = dialogFragmentDeleteElementBinding.dialogHeader;
        int position = getArguments().getInt("position");
        String name = getArguments().getString("name");
        String title = getString(R.string.dialog_header) + " " + name + "?";
        header.setText(title);
        builder.setView(dialogFragmentDeleteElementBinding.getRoot())
                .setNegativeButton(R.string.dialog_btn_cancel, (dialog, id) -> DeleteBuildDialogFragment.this.getDialog().cancel())
                .setPositiveButton(R.string.dialog_btn_accept, (dialog, id) -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", position);
                    getParentFragmentManager().setFragmentResult("BuildKey", bundle);
                    DeleteBuildDialogFragment.this.getDialog().dismiss();
                });
        return builder.setView(dialogFragmentDeleteElementBinding.getRoot()).create();
    }

    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        NavController controller = NavHostFragment.findNavController(DeleteBuildDialogFragment.this);
        NavOptions options = new NavOptions(true, false, R.id.buildsFragment, true,
                false, 0, 0, 0 , 0);
        controller.navigate(R.id.buildsFragment, new Bundle(), options);
    }

    public void onDestroyView() {
        super.onDestroyView();
        dialogFragmentDeleteElementBinding = null;
    }
}
