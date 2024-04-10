package com.humancloud.librarymanagementsystem.Model.RequestBody;

import com.humancloud.librarymanagementsystem.Model.Authors;
import com.humancloud.librarymanagementsystem.Model.Genres;
import com.humancloud.librarymanagementsystem.Model.Publishers;
import lombok.Data;


@Data
public class AddBookRequest {
    private String title;
    private Authors author;
    private String isbn;
    private long publicationYear;
    private Genres genres;
    private Publishers publishers;
}
