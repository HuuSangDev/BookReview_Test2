package com.LeHuuSang.Test2_BookReview.Repository;

import com.LeHuuSang.Test2_BookReview.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Boolean existsByName (String name);
}
