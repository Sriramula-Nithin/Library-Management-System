package com.humancloud.librarymanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Books{
    @Id
    private long bookId;

    private String title;
    private String authorName;
    private String ISBN;
    private long publicationYear;
    private int quantity;

    //Many books can have one Genre type
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_genre_Id")
    @JsonIgnore
    private Genres genres;

    //Many books can have one publisher
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_publisher_Id")
    @JsonIgnore
    private Publishers publishers;

    //
    @OneToOne(mappedBy = "books")
    @JsonIgnore
    private BookAuthors bookAuthors;

    @OneToMany(mappedBy = "books" )
    @JsonIgnore
    private List<Transactions> transactions;

    public Books() {

    }

}
