package com.example.ravishankar.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>>, SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String STRING_URL = "https://content.guardianapis.com/search";
    NewsAdapter newsAdapter;
    TextView EmptyStateView;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmptyStateView = findViewById(R.id.empty_view);
        ListView NewsListView = findViewById(R.id.list_view);

        NewsListView.setEmptyView(EmptyStateView);
        heading = findViewById(R.id.heading);
        heading.setText(getString(R.string.default_section_to_view));
        List<NewsData> newsDataList = new LinkedList<>();
        newsAdapter = new NewsAdapter(this, newsDataList);
        NewsListView.setAdapter(newsAdapter);

        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsData newsData = newsAdapter.getItem(i);
                assert newsData != null;
                Uri newsUri = Uri.parse(newsData.getWebURL());
                startActivity(new Intent(Intent.ACTION_VIEW, newsUri));
            }
        });

        NewsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsData newsData = newsAdapter.getItem(i);
                assert newsData != null;
                Uri newsUri = Uri.parse(newsData.getWebURL());
                Intent shareLink = new Intent(android.content.Intent.ACTION_SEND);
                shareLink.setType("text/plain");
                shareLink.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareLink.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                shareLink.putExtra(Intent.EXTRA_TEXT, newsUri);
                startActivity(Intent.createChooser(shareLink, "Share link!"));
                return true;
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            View LoadingIndicator = findViewById(R.id.progress_bar);
            LoadingIndicator.setVisibility(View.GONE);
            EmptyStateView.setText(R.string.no_internet);
            heading.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String apiKey = getString(R.string.API_KEY);
        String minimumPageSize = sharedPreferences.getString(getString(R.string.page_size_key), getString(R.string.page_size_default_value));
        String orderBy = sharedPreferences.getString(getString(R.string.sections_key), getString(R.string.default_section_to_view));
        heading = findViewById(R.id.heading);
        heading.setText(orderBy);
        Uri baseUri = Uri.parse(STRING_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon().appendQueryParameter("page-size", minimumPageSize)
                .appendQueryParameter("api-key", apiKey)
                .appendQueryParameter("q", orderBy)
                .appendQueryParameter("show-tags", "contributor");
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, final List<NewsData> newsData) {
        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);
        newsAdapter.clear();
        if (newsData != null && !newsData.isEmpty())
            newsAdapter.addAll(newsData);

        EmptyStateView.setText(R.string.no_internet);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        newsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.sections_key)) || s.equals(getString(R.string.page_size_key))) {
            newsAdapter.clear();
            EmptyStateView.setVisibility(View.GONE);

            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(0, null, this);
        }
    }
}

