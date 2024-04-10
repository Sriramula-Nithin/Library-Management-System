package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PublisherRepo extends JpaRepository<Publishers,Long> {
    @Query(value = "select * from publishers where publisher_name=:publisherName",nativeQuery = true)
    Publishers findByName(String publisherName);
}
