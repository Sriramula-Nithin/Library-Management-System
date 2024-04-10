package com.humancloud.librarymanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorsRepo extends JpaRepository<com.humancloud.librarymanagementsystem.Model.BookAuthors,Long> {
}
