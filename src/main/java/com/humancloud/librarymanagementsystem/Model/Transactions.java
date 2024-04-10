package com.humancloud.librarymanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    //one mem can do many transactions
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_member_Id")
    @JsonIgnore
    private Members members;

    private String transactionType;
    private Date dueDate;
    private Date returnDate;

    //many books can have same transaction
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_book_Id")
    @JsonIgnore
    private Books books;

}
