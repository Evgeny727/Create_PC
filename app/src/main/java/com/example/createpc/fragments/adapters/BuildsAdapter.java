package com.example.createpc.fragments.adapters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createpc.R;
import com.example.createpc.databinding.PcPartCardItemBinding;
import com.example.createpc.fragments.buildsfragments.BuildsFragmentDirections;
import com.example.createpc.fragments.dataclasses.DatabaseHelper;
import com.example.createpc.fragments.dataclasses.PcCardData;
import com.example.createpc.fragments.dataclasses.StaticBuildDataTemporaryStorage;
import com.example.createpc.fragments.dialogs.DeleteBuildDialogFragment;
import com.example.createpc.fragments.workshopfragments.CreateFragment;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BuildsAdapter extends RecyclerView.Adapter<BuildsAdapter.ViewHolder> {
    private final Fragment fragment;
    private final Cursor cursor;
    private final DatabaseHelper helper;
    private final SQLiteDatabase db;
    private Cursor cursorDB;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final PcPartCardItemBinding binding;
        private final TextView header;
        private final ImageView imageView;
        private final TextView specName1;
        private final TextView specValue1;
        private final TextView specName2;
        private final TextView specValue2;
        private final TextView specName3;
        private final TextView specValue3;
        private final TextView specName4;
        private final TextView specValue4;
        private final TextView specName5;
        private final TextView specValue5;
        private final TextView price;
        private final MaterialButton deleteBtn;
        private final MaterialButton editBtn;

        public ViewHolder(View view) {
            super(view);
            binding = PcPartCardItemBinding.bind(view);
            header = binding.pcPartCardHeader;
            imageView = binding.pcPartCardImg;
            specName1 = binding.pcPartCardSpecName1;
            specValue1 = binding.pcPartCardSpecValue1;
            specName2 = binding.pcPartCardSpecName2;
            specValue2 = binding.pcPartCardSpecValue2;
            specName3 = binding.pcPartCardSpecName3;
            specValue3 = binding.pcPartCardSpecValue3;
            specName4 = binding.pcPartCardSpecName4;
            specValue4 = binding.pcPartCardSpecValue4;
            specName5 = binding.pcPartCardSpecName5;
            specValue5 = binding.pcPartCardSpecValue5;
            price = binding.pcPartCardPrice;
            deleteBtn = binding.pcPartCardDeleteBtn;
            editBtn = binding.pcPartCardAddBtn;
        }

        public TextView getHeader() {
            return header;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getSpecName1() {
            return specName1;
        }

        public TextView getSpecValue1() {
            return specValue1;
        }

        public TextView getSpecName2() {
            return specName2;
        }

        public TextView getSpecValue2() {
            return specValue2;
        }

        public TextView getSpecName3() {
            return specName3;
        }

        public TextView getSpecValue3() {
            return specValue3;
        }

        public TextView getSpecName4() {
            return specName4;
        }

        public TextView getSpecValue4() {
            return specValue4;
        }

        public TextView getSpecName5() {
            return specName5;
        }

        public TextView getSpecValue5() {
            return specValue5;
        }

        public TextView getPrice() {
            return price;
        }

        public MaterialButton getDeleteBtn() {
            return deleteBtn;
        }

        public MaterialButton getEditBtn() {
            return editBtn;
        }
    }

    public BuildsAdapter(Cursor cursor, Fragment fragment) {
        this.cursor = cursor;
        this.fragment = fragment;
        helper = new DatabaseHelper(fragment.getActivity().getApplicationContext());
        helper.create_db();
        db = helper.open();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pc_part_card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        cursor.moveToPosition(position);
        String name = cursor.getString(1);
        if (name.equals("")) name = fragment.getString(R.string.build_name) + position;
        viewHolder.getHeader().setText(name);
        int currentId = cursor.getInt(8);
        String path = "";
        if (currentId > 0) {
            cursorDB = db.rawQuery("select image from base where _id=" + currentId, null);
            cursorDB.moveToFirst();
            path = cursorDB.getString(0);
        }
        if (!path.equals("")) {
            ImageView imageView = viewHolder.getImageView();
            imageView.setVisibility(View.VISIBLE);
            try(InputStream inputStream = fragment.getContext().getApplicationContext().getAssets().open(path)) {
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(drawable);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            catch (IOException e) {
                Log.d("TAGImg", "Image try to load");
                e.printStackTrace();
            }
        }
        else viewHolder.getImageView().setVisibility(View.INVISIBLE);
        String[] specNames = fragment.getResources().getStringArray(R.array.pc_build_spec_names);
        viewHolder.getSpecName1().setText(specNames[0]);
        viewHolder.getSpecName2().setText(specNames[1]);
        viewHolder.getSpecName3().setText(specNames[2]);
        viewHolder.getSpecName4().setText(specNames[3]);
        viewHolder.getSpecName5().setText(specNames[4]);

        String empty_param = fragment.getString(R.string.empty_param);
        currentId = cursor.getInt(2);
        if (currentId > 0) {
            cursorDB = db.rawQuery("select name from cpu where _id=" + currentId, null);
            cursorDB.moveToFirst();
            viewHolder.getSpecValue1().setText(cursorDB.getString(0));
        }
        else viewHolder.getSpecValue1().setText(empty_param);
        currentId = cursor.getInt(3);
        if (currentId > 0) {
            cursorDB = db.rawQuery("select name from gpu where _id=" + currentId, null);
            cursorDB.moveToFirst();
            viewHolder.getSpecValue2().setText(cursorDB.getString(0));
        }
        else viewHolder.getSpecValue2().setText(empty_param);
        currentId = cursor.getInt(4);
        if (currentId > 0) {
            cursorDB = db.rawQuery("select param2 from motherboard where _id=" + currentId, null);
            cursorDB.moveToFirst();
            viewHolder.getSpecValue3().setText(cursorDB.getString(0));
        }
        else viewHolder.getSpecValue3().setText(empty_param);
        currentId = cursor.getInt(6);
        if (currentId > 0) {
            cursorDB = db.rawQuery("select param1 from ram where _id=" + currentId, null);
            cursorDB.moveToFirst();
            viewHolder.getSpecValue4().setText(cursorDB.getString(0));
        }
        else viewHolder.getSpecValue4().setText(empty_param);
        currentId = cursor.getInt(5);
        if (currentId > 0) {
            cursorDB = db.rawQuery("select name from psu where _id=" + currentId, null);
            cursorDB.moveToFirst();
            viewHolder.getSpecValue5().setText(cursorDB.getString(0));
        }
        else viewHolder.getSpecValue5().setText(empty_param);

        int price = 0;
        for (int i = 0; i < 11; i++) {
            currentId = cursor.getInt(i+2);
            if (currentId > 0) {
                cursorDB = db.rawQuery("select price from " + DatabaseHelper.TABLEs[i] + " where _id=" + currentId, null);
                cursorDB.moveToFirst();
                price += cursorDB.getInt(0);
            }
            else price += 0;
        }
        String stringPrice = price + " " + fragment.getString(R.string.currency_icon);
        viewHolder.getPrice().setText(stringPrice);
        int build_id = cursor.getInt(0);
        viewHolder.getDeleteBtn().setOnClickListener(v -> {
            DeleteBuildDialogFragment dialogFragment = new DeleteBuildDialogFragment();
            Bundle args = new Bundle();
            args.putInt("id", build_id);
            args.putString("name", viewHolder.getHeader().getText().toString());
            dialogFragment.setArguments(args);
            dialogFragment.show(fragment.getParentFragmentManager(), "delete");
        });
        viewHolder.getEditBtn().setOnClickListener(v -> {
            CreateFragment.isNeedToSaveId = false;
             BuildsFragmentDirections.ActionBuildsFragmentToCreateFragment action = BuildsFragmentDirections.actionBuildsFragmentToCreateFragment();
             action.setBuildId(build_id);
             action.setBuildName(viewHolder.getHeader().getText().toString());
             List<PcCardData> list = new ArrayList<>();
             String[] typeNames = fragment.getResources().getStringArray(R.array.pc_part_type_names);
             String[] specNames5 = fragment.getResources().getStringArray(R.array.pc_part_spec_names);
             for (int i = 0; i < 11; i++) {
                 int id = cursor.getInt(i+2);
                 if (id > 0) {
                     cursorDB = db.rawQuery("select * from " + DatabaseHelper.TABLEs[i] + " where _id=" + id, null);
                     cursorDB.moveToFirst();
                     String[] specValues = {cursorDB.getString(4), cursorDB.getString(5), cursorDB.getString(6), cursorDB.getString(7), cursorDB.getString(8)};
                     list.add(new PcCardData(cursorDB.getInt(0), typeNames[i], cursorDB.getString(1), cursorDB.getString(2), cursorDB.getInt(3), specNames5, specValues));
                 }
                 else {
                     String[] specValues = {empty_param, empty_param, empty_param, empty_param, empty_param};
                     list.add(new PcCardData(0, typeNames[i], "", "", 0, specNames5, specValues));
                 }
             }
             StaticBuildDataTemporaryStorage.setAllCards(list);
             list.clear();
             NavHostFragment.findNavController(fragment).navigate(action);
        });
        viewHolder.itemView.setOnClickListener(v -> {
            BuildsFragmentDirections.ActionBuildsFragmentToBuildComponentsFragment action = BuildsFragmentDirections.actionBuildsFragmentToBuildComponentsFragment();
            action.setName(viewHolder.getHeader().getText().toString());
            action.setPrice(viewHolder.getPrice().getText().toString());
            action.setId(build_id);
            NavHostFragment.findNavController(fragment).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void closeDB() {
        db.close();
        cursorDB.close();
        cursor.close();
    }
}
