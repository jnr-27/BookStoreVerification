package com.book.test.context;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDataContext {
    private static int id;
    private static String name;
    private static String author;
    private static int publishedYear;
    private static String bookSummary;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        BookDataContext.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BookDataContext.name = name;
    }

    public static String getAuthor() {
        return author;
    }

    public static void setAuthor(String author) {
        BookDataContext.author = author;
    }

    public static int getPublishedYear() {
        return publishedYear;
    }

    public static void setPublishedYear(int publishedYear) {
        BookDataContext.publishedYear = publishedYear;
    }

    public static String getBookSummary() {
        return bookSummary;
    }

    public static void setBookSummary(String bookSummary) {
        BookDataContext.bookSummary = bookSummary;
    }
}
