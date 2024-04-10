package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<Authors,Long>{
    @Query(value = "select * from authors where author_name=:authorName",nativeQuery = true)
    Authors findByName(String authorName);
}
