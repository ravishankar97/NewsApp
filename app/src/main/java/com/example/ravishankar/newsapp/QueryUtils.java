package com.example.ravishankar.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<NewsData> fetchData(String urlString) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = CreateUrl(urlString);
        String jsonResponse = null;
        try {
            jsonResponse = MakeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFromJSON(jsonResponse);
    }

    private static URL CreateUrl(String urlString) {
        URL urlToLoad = null;
        try {
            urlToLoad = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return urlToLoad;
    }

    private static String MakeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving json results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<NewsData> extractFromJSON(String individualJSON) {
        if (TextUtils.isEmpty(individualJSON)) {
            return null;
        }
        List<NewsData> newsData = new LinkedList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(individualJSON);
            JSONObject responseObject = baseJSONResponse.getJSONObject("response");
            JSONArray contentArray = responseObject.getJSONArray("results");
            for (int i = 0; i < contentArray.length(); i++) {
                JSONObject currentElement = contentArray.getJSONObject(i);
                String webUrl = currentElement.getString("webUrl");
                String date = currentElement.getString("webPublicationDate");
                String header = currentElement.getString("webTitle");
                JSONArray tagsArray = currentElement.getJSONArray("tags");
                String author = " ";
                if (tagsArray.length() > 0) {
                    JSONObject tagsJSONObject = tagsArray.getJSONObject(0);
                    author = tagsJSONObject.getString("webTitle");
                }
                NewsData getter = new NewsData(header, webUrl, date.substring(0, 10), author);
                newsData.add(getter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsData;
    }
}
