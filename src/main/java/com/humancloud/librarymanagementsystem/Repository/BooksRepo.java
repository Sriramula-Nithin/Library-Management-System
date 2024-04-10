package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface BooksRepo extends JpaRepository<Books,Long> {
    @Query(value = "select * from books where title=:bookName",nativeQuery = true)
    List<Books> findByName(String bookName);

    @Query(value = "select * from books where author_name= :authorName",nativeQuery = true)
    List<Books> findByAuthor(String authorName);

    @Query(value = "select * from books where title=:bookName and author_name=:authorName",nativeQuery = true)
    List<Books> findBook(String bookName,String authorName);
}
