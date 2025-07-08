package com.book.test.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookResponse {


    int id;
    String name;
    String author;
    @JsonProperty("published_year")
    int publishedYear;

    @JsonProperty("book_summary")
    String bookSummary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "CreateBookResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", bookSummary='" + bookSummary + '\'' +
                '}';
    }
}
