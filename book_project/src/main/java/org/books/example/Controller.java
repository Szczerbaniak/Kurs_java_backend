package org.books.example;

import org.books.example.dbModel.DataEntity;
import org.books.example.model.Book;
import org.books.example.model.Count;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@RestController
public class Controller {

    @Autowired
    BookDb bookDb;


    @ResponseBody
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = "books/create")
    public Book create(@RequestBody Book givenBook) {
        Book book = new Book();

        long maxId = 0;

        for (Book book1 : bookDb.getBooks()) {
            if (book1.getId() > maxId) {
                maxId = book1.getId();
            }
        }

        storeData(givenBook.getAuthor(), givenBook.getTitle(), givenBook.getYearOfRelease());

        book.setId(maxId + 1);
        book.setAuthor(givenBook.getAuthor());
        book.setTitle(givenBook.getTitle());
        book.setYearOfRelease(givenBook.getYearOfRelease());

        bookDb.getBooks().add(book);

        return book;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = "books/readById")
    public Book readById(@RequestBody Book givenBook) {
        for (Book book : bookDb.getBooks()) {
            if (book.getId() == givenBook.getId()) return book;
        }
        return null;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "books/read")
    public ArrayList<Book> read() {
        return bookDb.getBooks();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "books/getCount")
    public Count getCount() {
        Count count = new Count();
        count.setNumOfRecords(getC());
        return count;

    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = "books/update")
    public Book updateBook(@RequestBody Book givenBook) {
        Long id = givenBook.getId();
        String author = givenBook.getAuthor();
        String title = givenBook.getTitle();
        Integer yearOfRelease = givenBook.getYearOfRelease();

        if (id != null) {

            Book bookToUpdate = null;
            for (Book book : bookDb.getBooks()) {
                if (book.getId() == id) {
                    bookToUpdate = book;
                }
            }

            if (author != null) {
                bookToUpdate.setAuthor(author);
            }

            if (title != null) {
                bookToUpdate.setTitle(title);
            }
            if (yearOfRelease != null) {
                bookToUpdate.setYearOfRelease(yearOfRelease);
            }
        }

        return givenBook;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", path = "books/delete")
    public Book delete(@RequestBody Book givenBook) {
        Long id = givenBook.getId();


        Book bookToRemove = null;

        for (Book book : bookDb.getBooks()) {
            if (book.getId() == id) {
                bookToRemove = book;
            }
            ;
        }

        bookDb.getBooks().remove(bookToRemove);

        return bookToRemove;
    }

    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        Properties properties = configuration.getProperties();

        ServiceRegistry serviceRegistry;
        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }

    private void storeData(String title, String author, Integer yearOfRelease) {
        Transaction tx = null;
        Session session = null;

        try {
            session = configureSessionFactory().openSession();
            tx = session.beginTransaction();
            DataEntity data = new DataEntity(title, author, yearOfRelease);
            session.save(data);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error !!: " + e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
private Long getC() {
        Transaction tx = null;
        Session session = null;
        Long count = null;

        try {
            session = configureSessionFactory().openSession();
            tx = session.beginTransaction();
            count = (Long) session.createQuery("select count(e) from DataEntity e").uniqueResult();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error !!: " + e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }




}
