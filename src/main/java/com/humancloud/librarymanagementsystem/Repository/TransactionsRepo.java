package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Transactions;
import jakarta.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionsRepo extends JpaRepository<Transactions, Long> {

    @Query(value = "select * from transactions where fk_book_id=:bookId and fk_member_id=:memId",nativeQuery = true)
    Optional<Transactions> findByBookIdMemId(long bookId, long memId);

    @Query(value = "select * from transactions where fk_book_id=:bookId",nativeQuery = true)
    List<Transactions> findByBookId(long bookId);

    @Query(value = "select * from transactions where fk_member_id=:memId",nativeQuery = true)
    List<Transactions> findByMemId(long memId);

    @Query(value = "select * from transactions where fk_book_id=:bookId and fk_member_id=:memId",nativeQuery = true)
    List<Transactions> findByBookId_MemId(long bookId, long memId);
}
