package com.book.test.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookRequest {


    String name;
    String author;
    @JsonProperty("published_year")
    int publishedYear;

    public CreateBookRequest(String name, String author, int publishedYear, String bookSummary) {
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
        this.bookSummary = bookSummary;
    }

    @JsonProperty("book_summary")
    String bookSummary;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    @Override
    public String toString() {
        return "CreateBookRequest{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", bookSummary='" + bookSummary + '\'' +
                '}';
    }
}
