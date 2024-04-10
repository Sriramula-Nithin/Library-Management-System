package com.humancloud.librarymanagementsystem.Model.RequestBody;

import lombok.Data;

@Data
public class AddMemberRequest {
    private String memberName;
    private String address;
    private long phone;
    private String email;
}
