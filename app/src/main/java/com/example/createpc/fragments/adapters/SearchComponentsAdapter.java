package com.example.createpc.fragments.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createpc.R;
import com.example.createpc.databinding.PcPartCardItemBinding;
import com.example.createpc.databinding.PcPartSearchCardItemBinding;
import com.example.createpc.fragments.dataclasses.PcCardData;
import com.example.createpc.fragments.dataclasses.StaticBuildDataTemporaryStorage;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SearchComponentsAdapter  extends RecyclerView.Adapter<SearchComponentsAdapter.ViewHolder> {
    private final Fragment fragment;
    private final int partType;
    private final List<PcCardData> pcCardDataList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final PcPartSearchCardItemBinding binding;
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

        public ViewHolder(View view) {
            super(view);
            binding = PcPartSearchCardItemBinding.bind(view);
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
    }

    public SearchComponentsAdapter(List<PcCardData> pcCardDataList, int partType, Fragment fragment) {
        this.pcCardDataList = pcCardDataList;
        this.partType = partType;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pc_part_search_card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        PcCardData cardData = pcCardDataList.get(position);
        viewHolder.getHeader().setText(cardData.getCardName());
        String path = cardData.getPathToImage();
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
        String[] specifications = cardData.getSpecificationNames();
        viewHolder.getSpecName1().setText(specifications[0]);
        viewHolder.getSpecName2().setText(specifications[1]);
        viewHolder.getSpecName3().setText(specifications[2]);
        viewHolder.getSpecName4().setText(specifications[3]);
        viewHolder.getSpecName5().setText(specifications[4]);
        specifications = cardData.getSpecificationValues();
        viewHolder.getSpecValue1().setText(specifications[0]);
        viewHolder.getSpecValue2().setText(specifications[1]);
        viewHolder.getSpecValue3().setText(specifications[2]);
        viewHolder.getSpecValue4().setText(specifications[3]);
        viewHolder.getSpecValue5().setText(specifications[4]);
        viewHolder.getPrice().setText(cardData.getStringPrice());
    }

    @Override
    public int getItemCount() {
        return pcCardDataList.size();
    }
}
