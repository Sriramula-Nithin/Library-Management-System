package com.humancloud.librarymanagementsystem.Model.RequestBody;

import lombok.Data;

@Data
public class NewTransactionRequest {
    private long bookId;
    private long memId;
    private String transactionType;
}
