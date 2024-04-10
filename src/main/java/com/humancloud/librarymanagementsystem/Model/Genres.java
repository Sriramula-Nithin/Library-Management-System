package com.humancloud.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Genres {
    @Id
    private long genreId;

    private String genreName;

    @OneToMany(mappedBy = "genres")
    List<Books> books;

    public Genres(long genreId, String genreName) {
        this.genreId=genreId;
        this.genreName=genreName;
    }

    public Genres()
    {

    }
}
