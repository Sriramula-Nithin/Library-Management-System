package com.humancloud.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookAuthors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    //one bookId can have only one authorId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_Id")
    private Books books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private Authors authors;

}
