package com.example.createpc.fragments.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createpc.R;
import com.example.createpc.databinding.PcPartCardItemBinding;
import com.example.createpc.fragments.dataclasses.PcCardData;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BuildsAdapter extends RecyclerView.Adapter<BuildsAdapter.ViewHolder> {
    private final Fragment fragment;
    private final List<List<PcCardData>> pcCardDataList;

    private final String[] buildsName;

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

    public BuildsAdapter(List<List<PcCardData>> pcCardDataList, String[] buildsName, Fragment fragment) {
        this.pcCardDataList = pcCardDataList;
        this.buildsName = buildsName;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pc_part_card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        List<PcCardData> cardData = pcCardDataList.get(position);
        viewHolder.getHeader().setText(buildsName[position]);
        String path = cardData.get(6).getPathToImage();
        if (!path.equals("")) {
            ImageView imageView = viewHolder.getImageView();
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
        viewHolder.getSpecName1().setText(cardData.get(0).getTypeName());
        viewHolder.getSpecName2().setText(cardData.get(1).getTypeName());
        viewHolder.getSpecName3().setText(cardData.get(2).getTypeName());
        viewHolder.getSpecName4().setText(cardData.get(4).getTypeName());
        viewHolder.getSpecName5().setText(cardData.get(3).getTypeName());

        viewHolder.getSpecValue1().setText(cardData.get(0).getPcPartName());
        viewHolder.getSpecValue2().setText(cardData.get(1).getPcPartName());
        viewHolder.getSpecValue3().setText(cardData.get(2).getPcPartName());
        String[] values = cardData.get(4).getSpecificationValues();
        String value = values[1] + " " + values[0];
        viewHolder.getSpecValue4().setText(value);
        viewHolder.getSpecValue5().setText(cardData.get(3).getSpecificationValues()[0]);
        int price = 0;
        for (PcCardData card:cardData) {
            price += card.getPrice();
        }
        String stringPrice = price + " " + fragment.getResources().getString(R.string.currency_icon);
        viewHolder.getPrice().setText(stringPrice);
        viewHolder.getDeleteBtn().setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Placeholder for delete :)", Toast.LENGTH_SHORT).show();
        });
        viewHolder.getEditBtn().setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Placeholder for edit :)", Toast.LENGTH_SHORT).show();
            // BuildsFragmentDirections.ActionBuildsFragmentToCreateFragment action = BuildsFragmentDirections.actionBuildsFragmentToCreateFragment();
            // action.setFragmentNameFrom("builds");
            // StaticBuildDataTemporaryStorage.setAllCards(cardData);
            // NavHostFragment.findNavController(fragment).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return pcCardDataList.size();
    }
}
