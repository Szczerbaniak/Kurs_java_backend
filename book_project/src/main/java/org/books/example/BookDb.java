package org.books.example;

import org.books.example.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BookDb {

    private ArrayList<Book> books = new ArrayList<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
