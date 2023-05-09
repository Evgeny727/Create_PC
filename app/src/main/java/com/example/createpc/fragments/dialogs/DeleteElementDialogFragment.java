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
import com.example.createpc.fragments.dataclasses.PcCardData;
import com.example.createpc.fragments.dataclasses.StaticBuildDataTemporaryStorage;

import java.util.List;
import java.util.Objects;

public class DeleteElementDialogFragment extends DialogFragment {
    private DialogFragmentDeleteElementBinding dialogFragmentDeleteElementBinding;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialogFragmentDeleteElementBinding = DialogFragmentDeleteElementBinding.inflate(requireActivity().getLayoutInflater());
        TextView header = dialogFragmentDeleteElementBinding.dialogHeader;
        int position = requireArguments().getInt("position");
        String name = requireArguments().getString("name");
        String title = getString(R.string.dialog_header) + " " + name + "?";
        header.setText(title);
        builder.setView(dialogFragmentDeleteElementBinding.getRoot())
                .setNegativeButton(R.string.dialog_btn_cancel, (dialog, id) -> DeleteElementDialogFragment.this.requireDialog().cancel())
                .setPositiveButton(R.string.dialog_btn_accept, (dialog, id) -> {
                    List<PcCardData> list = StaticBuildDataTemporaryStorage.getCardsList();
                    PcCardData cardData1 = list.get(position);
                    cardData1.setDefaultValues();
                    list.set(position, cardData1);
                    StaticBuildDataTemporaryStorage.setAllCards(list);
                    DeleteElementDialogFragment.this.requireDialog().dismiss();
                });
        return builder.setView(dialogFragmentDeleteElementBinding.getRoot()).create();
    }

    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        NavController controller = NavHostFragment.findNavController(DeleteElementDialogFragment.this);
        NavOptions options = new NavOptions(true, false, R.id.createFragment, true,
                false, 0, 0, 0 , 0);
        controller.navigate(R.id.createFragment, new Bundle(), options);
    }

    public void onDestroyView() {
        super.onDestroyView();
        dialogFragmentDeleteElementBinding = null;
    }
}
