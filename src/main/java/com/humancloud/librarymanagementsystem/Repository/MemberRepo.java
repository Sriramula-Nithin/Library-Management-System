package com.humancloud.librarymanagementsystem.Repository;

import com.humancloud.librarymanagementsystem.Model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Members,Long> {
}
