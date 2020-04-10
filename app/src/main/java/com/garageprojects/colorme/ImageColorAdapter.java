package com.garageprojects.colorme;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import com.garageprojects.colorme.colorutil.ColorUtils;

import java.util.List;

public class ImageColorAdapter extends RecyclerView.Adapter<ImageColorAdapter.ImageViewHolder> {

    private Context context;
    private List<Integer> images;
    private LayoutInflater inflater;

    private final ColorUtils colorUtils;

    public ImageColorAdapter(Context context, List<Integer> colors) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        images = colors;
        colorUtils = new ColorUtils();

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final int color = images.get(position);
        holder.slot.setBackgroundColor(color);
        holder.hexValue.setText(ColorUtils.toHex(color));
        holder.hexValue.setTextColor(ColorUtils.getTextColor(color));
        String colorName = ColorUtils.getColorNameFromRgb(ColorUtils.toHex(color));
        if (TextUtils.isEmpty(colorName))
            holder.colorName.setVisibility(View.GONE);
        else {
            holder.colorName.setText(colorName);
            holder.colorName.setTextColor(ColorUtils.getTextColor(color));
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }



    static class ImageViewHolder extends RecyclerView.ViewHolder {

        LinearLayout slot;
        TextView hexValue;
        TextView colorName;

        public ImageViewHolder(View itemView) {
            super(itemView);
            slot = itemView.findViewById(R.id.color_slot);
            hexValue = itemView.findViewById(R.id.color_hex);
            colorName = itemView.findViewById(R.id.color_name);
        }
    }
}