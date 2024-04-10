package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepo extends JpaRepository<Genres,Long> {
    @Query(value = "select * from genres where genre_name=:genreName",nativeQuery = true)
    Genres findByName(String genreName);
}
