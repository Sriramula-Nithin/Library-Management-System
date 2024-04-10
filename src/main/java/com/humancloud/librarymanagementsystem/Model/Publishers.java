package com.humancloud.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Publishers{
    @Id
    private long publisherId;

    private String publisherName;

    @OneToMany(mappedBy = "publishers")
    private List<Books> books;

    public Publishers(long publisherId, String publisherName) {
        this.publisherId=publisherId;
        this.publisherName=publisherName;
    }

    public Publishers()
    {

    }
}
