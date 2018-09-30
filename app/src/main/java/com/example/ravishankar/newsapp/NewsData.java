package com.example.ravishankar.newsapp;

public class NewsData {
    private String sourceName;
    private String headLine;
    private String webURL;
    private String dateOfPublication;

    NewsData(String sourceName, String headLine, String webURL, String dateOfPublication) {
        this.sourceName = sourceName;
        this.headLine = headLine;
        this.webURL = webURL;
        this.dateOfPublication = dateOfPublication;
    }

    public String getSourceName() {
        return sourceName;
    }

    String getHeadLine() {
        return headLine;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }
}
