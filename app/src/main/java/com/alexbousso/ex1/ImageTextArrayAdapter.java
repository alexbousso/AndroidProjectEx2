package com.alexbousso.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageTextArrayAdapter extends ArrayAdapter<FoodItemContent> {
    private final Context context;
    private final FoodItemContent[] items;

    public ImageTextArrayAdapter(Context context, FoodItemContent[] items) {
        super(context, R.layout.image_text_item_view, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_text_item_view, parent, false);

            holder = new ViewHolderItem();
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.textView.setText(items[position].getFoodName());
        holder.imageView.setImageResource(items[position].getFoodImageId());

        return convertView;
    }

    static class ViewHolderItem {
        TextView textView;
        ImageView imageView;
    }
}
