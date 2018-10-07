package com.example.ravishankar.newsapp;

public class NewsData {
    private String mHeadLine;
    private String mWebURL;
    private String mDateOfPublication;
    private String mAuthorName;

    NewsData(String headLine, String webURL, String dateOfPublication, String authorName) {
        this.mHeadLine = headLine;
        this.mWebURL = webURL;
        this.mDateOfPublication = dateOfPublication;
        this.mAuthorName = authorName;
    }

    String getHeadLine() {
        return mHeadLine;
    }

    String getWebURL() {
        return mWebURL;
    }

    String getDateOfPublication() {
        return mDateOfPublication;
    }

    String getAuthorName() {
        return mAuthorName;
    }
}
