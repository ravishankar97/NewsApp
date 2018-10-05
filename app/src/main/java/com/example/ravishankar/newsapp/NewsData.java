package com.example.ravishankar.newsapp;

public class NewsData {
    private String headLine;
    private String webURL;
    private String dateOfPublication;
    private String authorName;

    NewsData(String headLine, String webURL, String dateOfPublication, String authorName) {
        this.headLine = headLine;
        this.webURL = webURL;
        this.dateOfPublication = dateOfPublication;
        this.authorName = authorName;
    }

    String getHeadLine() {
        return headLine;
    }

    String getWebURL() {
        return webURL;
    }

    String getDateOfPublication() {
        return dateOfPublication;
    }

    String getAuthorName() {
        return authorName;
    }
}
