package org.books.example.dbModel;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class DataEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String title;
    private String author;
    private Integer yearOfRelease;

    public DataEntity() {}

    public DataEntity(String title, String author, Integer yearOfRelease) {
        this.title = title;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }
}
