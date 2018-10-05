package com.example.ravishankar.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        TextView headLine = convertView.findViewById(R.id.headLine);
        TextView dateOfPublication = convertView.findViewById(R.id.date);
        TextView authorName = convertView.findViewById(R.id.author);
        assert newsData != null;

        headLine.setText(newsData.getHeadLine());
        headLine.setTextColor(getColor(position));
        authorName.setText(newsData.getAuthorName());
        String dateFormatted = changeDate(newsData.getDateOfPublication());
        dateOfPublication.setText(dateFormatted);
        return convertView;
    }

    private String changeDate(String dateOfPublication) {
        String dateParts[] = dateOfPublication.split("-");
        return (dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
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
