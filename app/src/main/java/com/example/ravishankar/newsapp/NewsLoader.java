package com.example.ravishankar.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {
    private String mURLToLoad;

    NewsLoader(Context context, String urlToLoad) {
        super(context);
        mURLToLoad = urlToLoad;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsData> loadInBackground() {
        if (mURLToLoad == null)
            return null;
        else
            return QueryUtils.fetchData(mURLToLoad);
    }
}
