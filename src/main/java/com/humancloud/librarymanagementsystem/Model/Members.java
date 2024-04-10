package com.humancloud.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Members{
    @Id
    private long memberId;

    private String memberName;
    private String address;
    private long phone;
    private String email;

    @OneToMany(mappedBy = "members")
    private List<Transactions> transactions;
}
