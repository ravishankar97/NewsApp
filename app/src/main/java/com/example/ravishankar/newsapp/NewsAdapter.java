package com.example.ravishankar.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsData> {

    NewsAdapter(Context context, List<NewsData> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        NewsData newsData = getItem(position);

        TextView sourceName = convertView.findViewById(R.id.sourceName);
        TextView headLine = convertView.findViewById(R.id.headLine);
        TextView dateOfPublication = convertView.findViewById(R.id.date);
        assert newsData != null;

        sourceName.setText(Html.fromHtml("<u>" + newsData.getSourceName() + "</u"));
        sourceName.setTextColor(getColor(position));
        headLine.setText(newsData.getHeadLine());
        dateOfPublication.setText(newsData.getDateOfPublication());
        return convertView;
    }

    private int getColor(int position) {
        int ColorResourceId;
        position = position % 4;
        switch (position) {
            case 0: {
                ColorResourceId = R.color.navy_blue;
                break;
            }
            case 1: {
                ColorResourceId = R.color.green;
                break;
            }
            case 2: {
                ColorResourceId = R.color.red;
                break;
            }
            case 3: {
                ColorResourceId = R.color.pink;
                break;
            }
            default: {
                ColorResourceId = R.color.black;
                break;
            }
        }
        return ContextCompat.getColor(getContext(), ColorResourceId);
    }
}
