package com.tfg.adoptaunamascota.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tfg.adoptaunamascota.R;

import java.util.List;

public class DrawableImagePickerAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> imageResourceIds;

    public DrawableImagePickerAdapter(Context context, List<Integer> imageResourceIds) {
        this.context = context;
        this.imageResourceIds = imageResourceIds;
    }

    @Override
    public int getCount() {
        return imageResourceIds.size();
    }

    @Override
    public Object getItem(int position) {
        return imageResourceIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int imageSize = (int) context.getResources().getDimension(R.dimen.image_size);

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(imageSize, imageSize));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageResourceIds.get(position));
        return imageView;
    }
}
