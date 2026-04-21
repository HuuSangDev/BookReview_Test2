package com.LeHuuSang.Test2_BookReview.Repository;

import com.LeHuuSang.Test2_BookReview.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

}
