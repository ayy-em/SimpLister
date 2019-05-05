package com.example.jtodolister;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpecialAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] data;

    public SpecialAdapter(Context context, String[] items) {
        mInflater = LayoutInflater.from(context);
        this.data = items;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DialogList.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dialog_list_more_items_fragment, null);

            holder = new DialogList.ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.frag_dal_editText_item);
            convertView.setTag(holder);
        } else {
            holder = (DialogList.ViewHolder) convertView.getTag();
        }
        // Bind the data efficiently with the holder.
        holder.text.setText(data[position]);

        convertView.setBackgroundColor(Color.WHITE);

        return convertView;
    }
}
