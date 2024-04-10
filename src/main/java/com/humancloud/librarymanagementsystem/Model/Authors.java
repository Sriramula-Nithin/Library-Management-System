package com.humancloud.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Authors {
    @Id
    private long authorId;

    private String authorName;

//    @OneToMany(mappedBy = "authors")
//    private List<BookAuthors> bookAuthors;

}
